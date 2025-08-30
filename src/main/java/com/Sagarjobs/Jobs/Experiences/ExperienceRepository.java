package com.Sagarjobs.Jobs.Experiences;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ExperienceRepository extends JpaRepository<ExperienceEntity,Integer> {

    @Query("SELECT e FROM ExperienceEntity e WHERE e.jobId = :jobId And e.isenabled='T'")
    List<ExperienceEntity>findByjobIdAndisenabled(int jobId);
}
