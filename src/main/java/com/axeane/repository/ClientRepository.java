package com.axeane.repository;

import com.axeane.domain.Client;
import com.axeane.domain.Compte;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;


/**
 * Spring Data JPA repository for the Proprietaire entity.
 */
@Repository
public interface ClientRepository extends JpaRepository<Client, Long> {
    public Client getClientByNom(String nom);

    public Client getClientByCin(Integer cin);

    public Client getClientById(Long id);

    public Client getClientByComptes(Compte c);

    public List<Client> getAllByNom(String nom);

    public Client findByComptes(Integer numCompte);

}
