package com.Sagarjobs.Jobs.Repository;

import com.Sagarjobs.Jobs.Entity.CompanyEntities.JobEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface JobsRepositary extends JpaRepository<JobEntity,Integer> {
    Optional<JobEntity> findByEmail(String email);
    Optional<JobEntity> findByEmailAndPassword(String email,String password);

    Optional<JobEntity> findById(Integer company_id);
}
