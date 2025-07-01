package com.Sagarjobs.Jobs.Application;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ApplicationRepository  extends JpaRepository<ApplicationEntity,Integer> {

    List<ApplicationEntity> findByCompanyIdAndTitle(int company_id,String title);

    List<ApplicationEntity> findByCompanyId( int companyId);

    Optional<ApplicationEntity> findByTitle(String title);
}
