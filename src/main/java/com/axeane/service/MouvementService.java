package com.axeane.service;

import com.axeane.domain.Compte;
import com.axeane.domain.Mouvement;
import com.axeane.repository.CompteRepository;
import com.axeane.repository.MouvementRepository;
import com.axeane.service.Exception.GestionCteBancaireException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;


/**
 * Service Implementation for managing Mouvement.
 */
@Service
@Transactional
public class MouvementService {
    private final Logger log = LoggerFactory.getLogger(MouvementService.class);

    private final MouvementRepository mouvementRepository;
    private final CompteRepository compteRepository;


    public MouvementService(MouvementRepository mouvementRepository, CompteRepository compteRepository) {
        this.mouvementRepository = mouvementRepository;
        this.compteRepository = compteRepository;
    }

    /**
     * Save a Mouvement.
     *
     * @param mouvement the entity to save
     * @return the persisted entity
     */
    @Transactional
    public Mouvement save(Mouvement mouvement) throws GestionCteBancaireException {
        log.debug("Request to save Mouvement : {}", mouvement);
        if (mouvement.getCompteId() == null) {
            throw new GestionCteBancaireException("vous devez lier le mouvement à un compte");
        }
        Optional<Compte> compte = compteRepository.findById(mouvement.getCompteId());
        if (compte.isPresent()) {
            mouvement.setCompte(compte.get());
        } else throw new GestionCteBancaireException("compte n'existe pas");
        return mouvementRepository.save(mouvement);
    }

    /**
     * Get all the mouvements.
     *
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public List<Mouvement> findAll() {
        log.debug("Request to get all Mouvements");
        return mouvementRepository.findAll();
    }

    /**
     * Get one mouvement by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public Mouvement getMouvementById(Long id) {
        log.debug("Request to get Mouvement : {}", id);
        return mouvementRepository.getOne(id);
    }

    /**
     * Delete the  mouvement by id.
     *
     * @param id the id of the entity
     */

    public void delete(Long id) {
        log.debug("Request to delete mouvement : {}", id);
        mouvementRepository.deleteById(id);
    }
}
