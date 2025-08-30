package com.Sagarjobs.Jobs.JobSeeker;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;


public interface jobseekerRepositary extends JpaRepository<job_seekerEntity,Integer>{
    @Override
    Optional<job_seekerEntity> findById(Integer integer);

    Optional<job_seekerEntity> findByEmailAndPassword(String email ,String Password);
}
