package com.progoti.surecash.admission.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.progoti.surecash.admission.domain.Unit;

@Service
public class ApplicationStatusService {
	
	private List<Unit> appliedUnits;
	private List<Unit> nonAppliedUnits;
	
	public Map<String, List<Unit>> getAppliedUnitsWithNonAppliedUnits(){
		Map<String,List<Unit>> categorizedUnits = new HashMap<>();
		
		return null;
	}

}
