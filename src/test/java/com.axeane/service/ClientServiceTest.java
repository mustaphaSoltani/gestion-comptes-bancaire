package com.axeane.service;

import com.axeane.GestionCompteBancaireApplication;
import com.axeane.domain.Client;
import com.axeane.repository.ClientRepository;
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
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;


@RunWith(SpringRunner.class)
@SpringBootTest(classes =GestionCompteBancaireApplication.class)
@DataJpaTest
@ComponentScan("com.axeane")
public class ClientServiceTest {

    @Autowired
    private ClientService clientService;

    @Autowired
    private ClientRepository clientRepository;

  @Test
    public void save() throws Exception {
        Client client = new Client();
        client.setNom("Sami");
        client.setAdresse("aloui");
        clientService.save(client);
        Client clientResult = clientRepository.findAll().get(clientRepository.findAll().size() - 1);
        assertThat(clientResult.getNom(), is("Sami"));
    }

    @Test
    public void getClientById() throws Exception {
        Client client = new Client();
        client.setNom("Bilel");
        client.setCin(123456789);
        client.setPrenom("Omrani");
        client.setEmail("bilel@gmail.com");
        client.setNumTel(5525254);
        client.setAdresse("Bardo");

        clientService.save(client);
        List<Client> listClientAfterSave = clientService.findAll();
        Client clientSaved = clientService.getClientById(listClientAfterSave.get(listClientAfterSave.size() - 1).getId());
        assertThat(clientSaved.getNom(), is("Bilel"));
    }

    @Test
    public void getClientBynom() throws Exception {
        Client client = new Client();
        client.setNom("Bilell");
        client.setCin(123456789);
        client.setPrenom("Omrani");
        client.setEmail("bilel@gmail.com");
        client.setNumTel(5525254);
        client.setAdresse("Bardo");

        clientService.save(client);
        List<Client> listClientAfterSave = clientService.findAll();
        List<Client> clientSaved = clientService.getClientByNom(listClientAfterSave.get(listClientAfterSave.size() - 1).getNom());
        assertThat(clientSaved.get(listClientAfterSave.size() - 1).getNom(), is("Bilell"));
    }

    @Test
    public void getClientBynCin() throws Exception {
        Client client = new Client();
        client.setNom("Bilel");
        client.setCin(1234456789);
        client.setPrenom("Omrani");
        client.setEmail("bilel@gmail.com");
        client.setNumTel(5525254);
        client.setAdresse("Bardo");

        clientService.save(client);
        List<Client> listClientAfterSave = clientService.findAll();
        Client clientSaved = clientService.getClientBynCin(listClientAfterSave.get(listClientAfterSave.size() - 1).getCin());
        assertThat(clientSaved.getNom(), is("Bilel"));
    }

    @Test
    public void findAll() throws Exception {
        int sizeListClientBeforeSave = clientRepository.findAll().size();
        Client client = new Client();
        client.setNom("Soltani");
        client.setAdresse("Bardo");

        List<Client> listClientAfterSave = new ArrayList<>();
        boolean throwException = false;
        try {
            clientRepository.save(client);
            listClientAfterSave = clientService.findAll();
        } catch (Exception e) {
            throwException = true;
        }
        assertThat(listClientAfterSave.size(), is(sizeListClientBeforeSave + 1));
        assertThat(throwException, is(false));
    }

    @Test
    public void delete() throws Exception {
        int sizeListClientBeforeDelete = clientService.findAll().size();
        Client client = clientRepository.findAll().get(clientRepository.findAll().size() - 1);
        clientService.delete(client.getId());
        int sizeListClientAfterDelete = clientService.findAll().size();
        assertThat(sizeListClientAfterDelete, is(sizeListClientBeforeDelete-1));
    }

}