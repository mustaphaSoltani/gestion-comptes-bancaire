package com.axeane.service.testH2;

import com.axeane.GestionCompteBancaireApplication;
import com.axeane.domain.Client;
import com.axeane.domain.Compte;
import com.axeane.repository.ClientRepository;
import com.axeane.service.ClientService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.PropertySource;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;


@RunWith(SpringRunner.class)
@SpringBootTest(classes = GestionCompteBancaireApplication.class)
@DataJpaTest
@ComponentScan("com.axeane")
@TestPropertySource("/application.properties")
public class ClientServiceTest {

    @Autowired
    private ClientService clientService;

    @Autowired
    private ClientRepository clientRepository;

    @Test
    public void saveClientTest() throws Exception {
        Client client = new Client();
        client.setNom("Sami");
        client.setAdresse("aloui");
        client.setCin(12312312);
        clientService.createClient(client);
        Client clientResult = clientRepository.findAll().get(clientRepository.findAll().size() - 1);
        assertThat(clientResult.getNom(), is("Sami"));
    }

    @Test
    public void getClientByIdTest() throws Exception {
        Client client = new Client();
        client.setNom("Bilel");
        client.setCin(123654789);
        client.setPrenom("Omrani");
        client.setEmail("bilel@gmail.com");
        client.setNumTel(5525254);
        client.setAdresse("Bardo");

        clientService.createClient(client);
        List<Client> listClientAfterSave = clientService.findAllClient();
        Client clientSaved = clientService.getClientById(listClientAfterSave.get(listClientAfterSave.size() - 1).getId());
        assertThat(clientSaved.getNom(), is("Bilel"));
    }

    @Test
    public void getClientByNomTest() throws Exception {
        Client client = new Client();
        client.setNom("Bilell");
        client.setCin(123456777);
        client.setPrenom("Omrani");
        client.setEmail("bilel@gmail.com");
        client.setNumTel(5525254);
        client.setAdresse("Bardo");

        clientService.createClient(client);
        List<Client> listClientAfterSave = clientService.findAllClient();
        List<Client> clientSaved = clientService.getClientByNom(listClientAfterSave.get(listClientAfterSave.size() - 1).getNom());
        assertThat(clientSaved.get(listClientAfterSave.size() - 1).getNom(), is("Bilell"));
    }

    @Test
    public void getClientBynCinTest() throws Exception {
        Client client = new Client();
        client.setNom("Bilel");
        client.setCin(1234456687);
        client.setPrenom("Omrani");
        client.setEmail("bilel@gmail.com");
        client.setNumTel(5525254);
        client.setAdresse("Bardo");

        clientService.createClient(client);
        List<Client> listClientAfterSave = clientService.findAllClient();
        Client clientSaved = clientService.getClientBynCin(listClientAfterSave.get(listClientAfterSave.size() - 1).getCin());
        assertThat(clientSaved.getNom(), is("Bilel"));
    }

    @Test
    public void getClientBynNumCompteTest() throws Exception {
        Client client = new Client();
        client.setNom("Bilel");
        client.setCin(78564712);
        client.setPrenom("Omrani");
        client.setEmail("bilel@gmail.com");
        client.setNumTel(55252541);
        client.setAdresse("Bardo");
        Compte comtpe = new Compte();
        comtpe.setNumCompte(321);
        Set<Compte> comptes = new HashSet<>();
        comptes.add(comtpe);
        client.setComptes(comptes);
        clientService.createClient(client);
        List<Client> listClientAfterSave = clientService.findAllClient();
        Client clientSaved = clientService.getClientBynNumCompte(listClientAfterSave.get(listClientAfterSave.size() - 1).getComptes().iterator().next().getNumCompte());
        assertThat(clientSaved.getNom(), is("Bilel"));
        assertThat(clientSaved.getComptes().iterator().next().getNumCompte(), is(321));
    }

    @Test
    public void findAllTest() throws Exception {
        int sizeListClientBeforeSave = clientRepository.findAll().size();
        Client client = new Client();
        client.setNom("Soltani");
        client.setAdresse("Bardo");
        client.setCin(11164712);
        List<Client> listClientAfterSave = new ArrayList<>();
        boolean throwException = false;
        try {
            clientRepository.save(client);
            listClientAfterSave = clientService.findAllClient();
        } catch (Exception e) {
            throwException = true;
        }
        assertThat(listClientAfterSave.size(), is(sizeListClientBeforeSave + 1));
        assertThat(throwException, is(false));
    }

    @Test
    public void deleteTest() throws Exception {
        int sizeListClientBeforeDelete = clientService.findAllClient().size();
        Client client = new Client();
        client.setCin(88864712);
        clientRepository.save(client);
        Client client1 = clientRepository.findAll().get(clientRepository.findAll().size() - 1);
        clientService.deleteClient(client1.getId());
        int sizeListClientAfterDelete = clientService.findAllClient().size();
        assertThat(sizeListClientBeforeDelete, is(sizeListClientAfterDelete));
    }
}