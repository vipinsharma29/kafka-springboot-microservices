package com.vipin.paymentsService.repository;

import com.vipin.paymentsService.model.TransferEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TransferRepository extends JpaRepository<TransferEntity, String> {
}