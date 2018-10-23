package com.axeane.repository;

import com.axeane.domain.Compte;
import com.axeane.domain.Mouvement;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MouvementRepository extends JpaRepository<Mouvement, Long> {
    public List<Mouvement> getAllByCompte(Compte compte);
}
