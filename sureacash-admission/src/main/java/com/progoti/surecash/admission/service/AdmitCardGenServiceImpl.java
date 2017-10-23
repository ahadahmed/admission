package com.progoti.surecash.admission.service;

import com.progoti.surecash.admission.domain.AdmitCard;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
public class AdmitCardGenServiceImpl implements AdmitCardGenService {

    public List<AdmitCard> findAll(){
        AdmitCard admitCard1 = new AdmitCard();
        admitCard1.setId(100L);
        admitCard1.setStName("hablu");
        admitCard1.setUnivname("surecash");
        admitCard1.setRollNo("0146565");
        admitCard1.setRegNo("0134656");
        List AdmitCards = Arrays.asList(  admitCard1 ) ;
        return AdmitCards ;
    }
}
