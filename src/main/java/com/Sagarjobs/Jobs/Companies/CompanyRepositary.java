package com.Sagarjobs.Jobs.Companies;

import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Optional;

public interface CompanyRepositary extends JpaRepository<CompanyEntity,Integer> {
    Optional<CompanyEntity> findByEmail(String email);
    Optional<CompanyEntity> findByEmailAndPassword(String email, String password);
    Optional<CompanyEntity> findById(Integer company_id);

}
