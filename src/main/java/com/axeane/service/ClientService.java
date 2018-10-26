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

    public Client createClient(Client client) {
        log.debug("Request to save Client : {}", client);
        client.getComptes().forEach(compte -> {
            compte.setClient(client);
        });
        return clientRepository.save(client);
    }

    @Transactional(readOnly = true)
    public Client getClientById(Long id) {
        log.debug("Request to get Client : {}", id);
        return clientRepository.getClientById(id);
    }

    @Transactional(readOnly = true)
    public List<Client> getClientByNom(String nom) {
        log.debug("Request to get Client : {}", nom);
        return clientRepository.getAllByNom(nom);
    }

    @Transactional(readOnly = true)
    public Client getClientBynCin(Integer cin) {
        log.debug("Request to get Client : {}", cin);
        return clientRepository.getClientByCin(cin);
    }

    @Transactional(readOnly = true)
    public Client getClientBynNumCompte(Integer numCompte) {
        log.debug("Request to get Client : {}", numCompte);

        Compte compte = compteRepository.findByNumCompte(numCompte);
        return clientRepository.getClientByComptes(compte);
    }

    @Transactional(readOnly = true)
    public List<Client> findAll() {
        log.debug("Request to get all Client");
        return clientRepository.findAll();
    }

    public void delete(Long id) {
        log.debug("Request to delete Client : {}", id);
        clientRepository.deleteById(id);
    }
}
