package com.progoti.surecash.admission.repository;

import com.progoti.surecash.admission.domain.Role;
import com.progoti.surecash.admission.utility.Constants;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by Shaown on 12:06 PM.
 */
@Repository
public interface RoleRepository extends JpaRepository<Role, Integer>{
    Role findOneByRoleName(Constants.RoleName roleName);
}
