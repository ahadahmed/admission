package com.progoti.surecash.admission.converter;

import com.progoti.surecash.admission.domain.StudentApplicationHistory;
import com.progoti.surecash.admission.utility.Constants;
import com.progoti.surecash.dto.PaymentRequestDto;
import java.util.ArrayList;
import java.util.List;

public class ApplicationHistoryConverter {

    public static PaymentRequestDto toPaymentRequest(StudentApplicationHistory history) {
        PaymentRequestDto dto = new PaymentRequestDto();
        if (history != null) {
            dto.setId(history.getId());
            dto.setApplicationId(history.getApplicationId());
            if (history.getUnit() != null) {
                dto.setUnit(history.getUnit().getCode());
            }
            String fees = Constants.DECIMAL_FORMAT.format(history.getPayableAmount());
            dto.setFormattedFees(fees);
            dto.setShouldPaid(!history.getPaid());
        }
        return dto;
    }

    public static List<PaymentRequestDto> toPaymentRequestDtos(List<StudentApplicationHistory> histories) {
        List<PaymentRequestDto> dtos = new ArrayList<>();
        if (histories != null && histories.size() > 0) {
            histories.forEach(history -> dtos.add(toPaymentRequest(history)));
        }
        return dtos;
    }
}
