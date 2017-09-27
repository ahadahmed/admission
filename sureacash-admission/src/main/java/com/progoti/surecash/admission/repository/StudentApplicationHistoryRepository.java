package com.progoti.surecash.admission.repository;

import com.progoti.surecash.admission.domain.StudentApplicationHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by Shaown on 12:03 PM.
 */
@Repository
public interface StudentApplicationHistoryRepository extends JpaRepository<StudentApplicationHistory, Integer>{
}
