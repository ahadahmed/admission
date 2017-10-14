package com.progoti.surecash.admission.service;

import com.progoti.surecash.admission.converter.ApplicationHistoryConverter;
import com.progoti.surecash.admission.domain.AdmissionPaymentRequest;
import com.progoti.surecash.admission.domain.StudentApplicationHistory;
import com.progoti.surecash.admission.domain.TransactionHistory;
import com.progoti.surecash.admission.domain.University;
import com.progoti.surecash.admission.repository.AdmissionPaymentRequestRepository;
import com.progoti.surecash.admission.repository.StudentApplicationHistoryRepository;
import com.progoti.surecash.admission.repository.TransactionHistoryRepository;
import com.progoti.surecash.admission.repository.UniversityRepository;
import com.progoti.surecash.admission.request.ReconcileRequest;
import com.progoti.surecash.admission.response.PaymentResponse;
import com.progoti.surecash.admission.utility.Constants;
import com.progoti.surecash.admission.utility.RestClient;
import com.progoti.surecash.dto.PaymentRequestDto;
import com.progoti.surecash.dto.SwitchPaymentRequestDto;
import org.apache.http.HttpResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class PaymentServiceImpl implements PaymentService {

    @Autowired
    private StudentApplicationHistoryRepository historyRepository;
    @Autowired
    private TransactionHistoryRepository transactionHistoryRepository;
    @Autowired
    private RestClient restClient;
    @Autowired
    private AdmissionPaymentRequestRepository admissionPaymentRequestRepository;
    @Autowired
    private UniversityRepository universityRepository;

    @Override
    public List<PaymentRequestDto> listPaymentRequests(String userName) {
        List<StudentApplicationHistory> histories = historyRepository.loadActiveHistoryByUserName(userName);
        return ApplicationHistoryConverter.toPaymentRequestDtos(histories);
    }

    @Override
    public PaymentResponse doPayment(AdmissionPaymentRequest paymentRequest) throws IOException, URISyntaxException {
        PaymentResponse paymentResponse = new PaymentResponse();
        University university = universityRepository.findOneByBillerCode(paymentRequest.getBillerCode());

        // if institution not found

        if(university == null){
            paymentResponse.setStatus(Constants.Transaction_Status.ERROR.name());
            paymentResponse.setDescription(Constants.ErrorMessage.INSTITUTION_NOT_FOUND.value);
            System.out.println(Constants.ErrorMessage.INSTITUTION_NOT_FOUND.value);
            return paymentResponse;
        }

        StudentApplicationHistory applicationHistory = historyRepository.findOneByApplicationIdAndUniversity(paymentRequest.getApplicantId(), university);

        // if applicant already paid

        if(applicationHistory.getPaid() != null && applicationHistory.getTranxId() != null){
            paymentResponse.setStatus(Constants.Transaction_Status.ERROR.name());
            paymentResponse.setDescription(Constants.ErrorMessage.NO_DUE.value);
            System.out.println(Constants.ErrorMessage.NO_DUE.value);
            return paymentResponse;
        }

        // if applicant amount is equal to due amount

        if(paymentRequest.getAmount().equals(applicationHistory.getPayableAmount())){
            paymentRequest.setToWallet(university.getWallet());
            AdmissionPaymentRequest request = admissionPaymentRequestRepository.saveAndFlush(paymentRequest);

            // switch doPayment calling
            Map<String, String> responseMap = doSwitchCallForPayment(request, applicationHistory);

            paymentResponse = new PaymentResponse(responseMap);
        } else {
            paymentResponse.setStatus(Constants.Transaction_Status.ERROR.name());
            String errorDescription = Constants.ErrorMessage.INVALID_AMOUNT.value.replace("?", String.valueOf(applicationHistory.getPayableAmount()));
            System.out.println(errorDescription);
            paymentResponse.setDescription(errorDescription);
        }
        return paymentResponse;
    }

    @Override
    public PaymentResponse reconcilePayment(ReconcileRequest reconcileRequest) {
        PaymentResponse paymentResponse = new PaymentResponse();
        University university = universityRepository.findOneByBillerCode(reconcileRequest.getBillerCode());

        // if institution not found

        if(university == null){
            paymentResponse.setStatus(Constants.Transaction_Status.ERROR.name());
            paymentResponse.setDescription(Constants.ErrorMessage.INSTITUTION_NOT_FOUND.value);
            System.out.println(Constants.ErrorMessage.INSTITUTION_NOT_FOUND.value);
            return paymentResponse;
        }

        StudentApplicationHistory applicationHistory = historyRepository.findOneByApplicationIdAndUniversity(reconcileRequest.getApplicantId(), university);

        // if applicant already paid

        if(applicationHistory.getPaid() != null && applicationHistory.getTranxId() != null){
            paymentResponse.setStatus(Constants.Transaction_Status.ERROR.name());
            paymentResponse.setDescription(Constants.ErrorMessage.NO_DUE.value);
            System.out.println(Constants.ErrorMessage.NO_DUE.value);
            return paymentResponse;
        }

        // if applicant amount is equal to due amount

        if(reconcileRequest.getAmount().equals(applicationHistory.getPayableAmount())){
            reconcileRequest.setToWallet(university.getWallet());
            // must set remark to identify it's a reconcile request
            reconcileRequest.setRemark(Constants.Transaction_Status.RECONCILE.name());
            admissionPaymentRequestRepository.saveAndFlush(reconcileRequest);

            Map<String, String> responseMap = doReconcileForPayment(reconcileRequest, applicationHistory);
            paymentResponse = new PaymentResponse(responseMap);
        } else {
            paymentResponse.setStatus(Constants.Transaction_Status.ERROR.name());
            String errorDescription = Constants.ErrorMessage.INVALID_AMOUNT.value.replace("?", String.valueOf(applicationHistory.getPayableAmount()));
            System.out.println(errorDescription);
            paymentResponse.setDescription(errorDescription);
        }
        return paymentResponse;
    }

    @Transactional
    private Map<String, String> doReconcileForPayment(ReconcileRequest request, StudentApplicationHistory applicationHistory){
        TransactionHistory transactionHistory = transactionHistoryRepository.findOneByAdmissionPaymentRequest(request);

        Map<String, String> responseMap = new HashMap<>();
        responseMap.put("status", Constants.Transaction_Status.PROCESSED.name());
        responseMap.put("description", "Transaction Successful");
        responseMap.put("trnxId", request.getProfinoTransactionId());

        // update in transaction history table
        transactionHistory.setStatus(responseMap.get("status"));
        transactionHistory.setTrnxId(responseMap.get("trnxId"));
        transactionHistory.setUpdateDate(new Date());
        transactionHistoryRepository.save(transactionHistory);

        // update in student application history table
        applicationHistory.setPaid(Boolean.TRUE);
        applicationHistory.setTranxId(transactionHistory.getTrnxId());
        applicationHistory.setUpdateDate(new Date());
        historyRepository.saveAndFlush(applicationHistory);

        return responseMap;
    }

    @Transactional
    private Map<String, String> doSwitchCallForPayment(AdmissionPaymentRequest request, StudentApplicationHistory applicationHistory) throws IOException, URISyntaxException {
        TransactionHistory transactionHistory = transactionHistoryRepository.findOneByAdmissionPaymentRequest(request);

        SwitchPaymentRequestDto switchPaymentRequestDto = new SwitchPaymentRequestDto();
        switchPaymentRequestDto.doReflectionUsingAdmissionPaymentRequest(request);

        HttpResponse response = restClient.doSwitchPaymentRequest(switchPaymentRequestDto);
        Map<String, String> responseMap = restClient.parseResponseText(response);

        // update in transaction history table
        transactionHistory.setStatus(responseMap.get("status"));
        transactionHistory.setTrnxId(responseMap.get("trnxId"));
        transactionHistory.setUpdateDate(new Date());
        transactionHistoryRepository.save(transactionHistory);

        // update in student application history table
        applicationHistory.setPaid(Boolean.TRUE);
        applicationHistory.setTranxId(transactionHistory.getTrnxId());
        applicationHistory.setUpdateDate(new Date());
        historyRepository.saveAndFlush(applicationHistory);

        return responseMap;
    }
}
