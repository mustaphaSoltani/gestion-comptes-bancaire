package com.axeane.repository;

import com.axeane.domain.Compte;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

/**
 * Spring Data JPA repository for the Proprietaire entity.
 */
@Repository
@Transactional
public interface CompteRepository extends JpaRepository<Compte, Long> {
    public Compte getCompteByNumCompte(Integer numCompte);

    public Compte findByNumCompte(Integer numCompte);
}
