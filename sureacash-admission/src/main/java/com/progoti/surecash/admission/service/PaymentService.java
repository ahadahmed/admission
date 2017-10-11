package com.progoti.surecash.admission.service;

import com.progoti.surecash.dto.PaymentRequestDto;
import java.util.List;

public interface PaymentService {

    List<PaymentRequestDto> listPaymentRequests(String userName);
}
