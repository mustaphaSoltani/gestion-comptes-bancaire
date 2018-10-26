package com.axeane.repository;

import com.axeane.domain.Mouvement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MouvementRepository extends JpaRepository<Mouvement, Long> {
}
