package com.progoti.surecash.admission.converter;

import com.progoti.surecash.admission.domain.University;
import com.progoti.surecash.dto.ContactDto;

public class UniversityConverter {

    public static ContactDto toContactDto(University university) {
        ContactDto dto = new ContactDto();
        dto.setUniversityName(university.getName());
        dto.setUniversityAddress(university.getAddress());
        dto.setContactNo(university.getContactNo());
        dto.setContactEmail(university.getEmail());
        dto.setUniversityLogo(university.getLogo());
        return dto;
    }
}
