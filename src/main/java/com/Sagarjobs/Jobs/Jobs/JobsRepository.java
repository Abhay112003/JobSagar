package com.Sagarjobs.Jobs.Jobs;

import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface JobsRepository extends JpaRepository<Jobs,Integer> {
    // This return single jobtitle
    @Query("SELECT DISTINCT j.jobtitle FROM Jobs j WHERE j.companyId = :companyId")
    List<String> findDistinctJobTitlesByCompanyId(@Param("companyId") int companyId);

    //AllJobsShowwhere id is true;
    @Query("SELECT j FROM Jobs j WHERE j.companyId = :companyId And j.isenabled=true")
    List<Jobs>findByCompanyIdAndisenabled(int companyId);

//    @Query("SELECT j FROM Jobs j WHERE j.job_id = :job_id")
//    Optional<Jobs>findByJob_id(int id);

    @Transactional
    @Modifying
    @Query("UPDATE Jobs j SET j.isenabled=false where j.job_id=:job_id")
    void disabledJob(@Param("job_id") int job_id);

}
