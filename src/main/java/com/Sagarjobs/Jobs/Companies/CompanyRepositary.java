package com.Sagarjobs.Jobs.Companies;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CompanyRepositary extends JpaRepository<CompanyEntity,Integer> {
    Optional<CompanyEntity> findByEmail(String email);
    Optional<CompanyEntity> findByEmailAndPassword(String email, String password);
    Optional<CompanyEntity> findById(Integer company_id);
}
