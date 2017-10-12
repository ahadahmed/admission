package com.progoti.surecash.admission.service;

import com.progoti.surecash.admission.converter.ApplicationHistoryConverter;
import com.progoti.surecash.admission.domain.StudentApplicationHistory;
import com.progoti.surecash.admission.repository.StudentApplicationHistoryRepository;
import com.progoti.surecash.dto.PaymentRequestDto;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PaymentServiceImpl implements PaymentService {

    private StudentApplicationHistoryRepository historyRepository;

    @Autowired
    public PaymentServiceImpl(StudentApplicationHistoryRepository historyRepository) {
        this.historyRepository = historyRepository;
    }

    @Override
    public List<PaymentRequestDto> listPaymentRequests(String userName) {
        List<StudentApplicationHistory> histories = historyRepository.loadActiveHistoryByUserName(userName);
        return ApplicationHistoryConverter.toPaymentRequestDtos(histories);
    }
}
