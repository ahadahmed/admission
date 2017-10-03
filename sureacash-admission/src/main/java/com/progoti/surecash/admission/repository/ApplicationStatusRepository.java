package com.progoti.surecash.admission.repository;

import java.sql.ResultSet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;

public class ApplicationStatusRepository {
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	private String GET_ALLUNITS_WITH_APPLIED_UNITS = "select * from univ_admission_application.student_application_history as sah right join univ_admission_application.unit as u"
													+" ON sah.unit_id = u.id" 
													+" where sah.student_info_id = 4 or sah.student_info_id is null";
	
	
	public ResultSet getApplicationhistoryWithNonAppliedUnits(Integer studentId){
		// TODO-execute query here
		//jdbcTemplate.e
		return null;
	}

}
