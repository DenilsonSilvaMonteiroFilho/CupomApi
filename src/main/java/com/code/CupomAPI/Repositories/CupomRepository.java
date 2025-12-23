package com.code.CupomAPI.Repositories;

import com.code.CupomAPI.Entities.Cupom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface CupomRepository extends JpaRepository<Cupom, UUID> {
    Optional<Cupom> findById(UUID id);
}
