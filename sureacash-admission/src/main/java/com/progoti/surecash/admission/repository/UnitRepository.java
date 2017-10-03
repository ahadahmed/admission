package com.progoti.surecash.admission.repository;

import com.progoti.surecash.admission.domain.Unit;
import com.progoti.surecash.admission.domain.University;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by Shaown on 12:06 PM.
 */
@Repository
public interface UnitRepository extends JpaRepository<Unit, Integer>{
    List<Unit> findAllByUniversity(University university);
}
