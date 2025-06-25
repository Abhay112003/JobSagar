package com.Sagarjobs.Jobs.Repository;

import com.Sagarjobs.Jobs.Entity.JobSeeker.job_seekerEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;


public interface jobseekerRepositary extends JpaRepository<job_seekerEntity,Integer>{
    @Override
    Optional<job_seekerEntity> findById(Integer integer);
}
