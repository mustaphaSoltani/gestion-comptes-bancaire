package com.axeane.repository;

import com.axeane.domain.Compte;
import com.axeane.domain.Mouvement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

/**
 * Spring Data JPA repository for the Proprietaire entity.
 */
@Repository
public interface MouvementRepository extends JpaRepository<Mouvement, Long> {
    public List<Mouvement> getAllByCompte(Compte compte);
}
