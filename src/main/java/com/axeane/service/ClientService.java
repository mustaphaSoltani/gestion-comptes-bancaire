package com.axeane.service;

import com.axeane.domain.Client;
import com.axeane.domain.Compte;
import com.axeane.repository.ClientRepository;

import com.axeane.repository.CompteRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


/**
 * Service Implementation for managing Client.
 */
@Service
@Transactional
public class ClientService {
    private final Logger log = LoggerFactory.getLogger(ClientService.class);

    private final ClientRepository clientRepository;
    private final CompteRepository compteRepository;

    public ClientService(ClientRepository clientRepository, CompteRepository compteRepository) {
        this.clientRepository = clientRepository;
        this.compteRepository = compteRepository;
    }

    /**
     * Save a client.
     *
     * @param client the entity to save
     * @return the persisted entity
     */
    @Transactional
    public Client createClient(Client client) {
        log.debug("Request to save Client : {}", client);
        client.getComptes().forEach(compte -> {
            compte.setClient(client);
        });
        return clientRepository.save(client);
    }

    /**
     * Get one client by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public Client getClientById(Long id) {
        log.debug("Request to get Client : {}", id);
        return clientRepository.getClientById(id);
    }

    /**
     * Get one client by nom.
     *
     * @param nom the nom of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public List<Client> getClientByNom(String nom) {
        log.debug("Request to get Client : {}", nom);
        return clientRepository.getAllByNom(nom);
    }

    /**
     * Get one client by cin.
     *
     * @param cin the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public Client getClientBynCin(Integer cin) {
        log.debug("Request to get Client : {}", cin);
        return clientRepository.getClientByCin(cin);
    }

    /**
     * Get one client by num compte.
     *
     * @param numCompte the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public Client getClientBynNumCompte(Integer numCompte) {
        log.debug("Request to get Client : {}", numCompte);

        Compte compte = compteRepository.findByNumCompte(numCompte);
        return clientRepository.getClientByComptes(compte);
    }
    /**
     * Get all the Client.
     *
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public List<Client> findAll() {
        log.debug("Request to get all Client");
        return clientRepository.findAll();
    }

    /**
     * Delete the  Client by id.
     *
     * @param id the id of the entity
     */

    public void delete(Long id) {
        log.debug("Request to delete Client : {}", id);
        clientRepository.deleteById(id);
    }
}
