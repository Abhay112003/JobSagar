package com.Sagarjobs.Jobs.Jobs;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface JobsRepository extends JpaRepository<Jobs,Integer> {
    // This return single jobtitle
    @Query("SELECT DISTINCT j.jobtitle FROM Jobs j WHERE j.companyId = :companyId")
    List<String> findDistinctJobTitlesByCompanyId(@Param("companyId") int companyId);

}
