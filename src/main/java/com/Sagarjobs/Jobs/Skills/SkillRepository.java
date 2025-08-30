package com.Sagarjobs.Jobs.Skills;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface SkillRepository extends JpaRepository<skills,Integer> {

    @Query("SELECT s FROM skills s WHERE s.jobseekerid = :jobseekerid And s.isenabled='t'")
    List<skills> findByJobseekeridAndisenabled(int jobseekerid);
}
