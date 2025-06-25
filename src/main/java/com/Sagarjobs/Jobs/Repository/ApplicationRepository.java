package com.Sagarjobs.Jobs.Repository;


import com.Sagarjobs.Jobs.Entity.ApplicationEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ApplicationRepository  extends JpaRepository<ApplicationEntity,Integer> {
    List<ApplicationEntity> findByCompanyId(int company_id);

}
