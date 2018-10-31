package com.axeane.service.testH2;

import ch.qos.logback.core.util.StringCollectionUtil;
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
import org.thymeleaf.util.StringUtils;

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
        client.setCin(12312312L);
        clientService.createClient(client);
        Client clientResult = clientRepository.findAll().get(clientRepository.findAll().size() - 1);
        assertThat(clientResult.getNom(), is("Sami"));
    }

    @Test
    public void getClientByIdTest() throws Exception {
        Client client = new Client();
        client.setNom("Bilel");
        client.setCin(123654789L);
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
        client.setCin(123456777L);
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
        client.setCin(1234456687L);
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
        client.setCin(78564712L);
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
        client.setCin(11164712L);
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
        client.setCin(88864712L);
        clientRepository.save(client);
        Client client1 = clientRepository.findAll().get(clientRepository.findAll().size() - 1);
        clientService.deleteClient(client1.getId());
        int sizeListClientAfterDelete = clientService.findAllClient().size();
        assertThat(sizeListClientBeforeDelete, is(sizeListClientAfterDelete));
    }
    @Test
    public void checkCinIsLengthThan8() throws Exception {
        int sizeListClientBeforeSave = clientRepository.findAll().size();
        Client client1 = new Client();
        client1.setCin(123124564L);
        List<Client> listClientAfterSave = new ArrayList<>();
        boolean throwException = false;
        try {
            clientService.createClient(client1);
            listClientAfterSave = clientRepository.findAll();
        } catch (Exception e) {
            throwException = true;
        }
        assertThat(listClientAfterSave.size(), is(sizeListClientBeforeSave));
        assertThat(throwException, is(true));
    }

    @Test
    public void checkNomIsLengthMax50() throws Exception {
        int sizeListClientBeforeSave = clientRepository.findAll().size();
        Client client1 = new Client();
        client1.setCin(10000003L);
        client1.setNom(StringUtils.repeat("a",66));
        List<Client> listClientAfterSave = new ArrayList<>();
        boolean throwException = false;
        try {
            clientService.createClient(client1);
            listClientAfterSave = clientRepository.findAll();
        } catch (Exception e) {
            throwException = true;
            ;
        }
        assertThat(listClientAfterSave.size(), is(sizeListClientBeforeSave));
        assertThat(throwException, is(true));
    }

    @Test
    public void checkPrenomIsLengthMax50() throws Exception {
        int sizeListClientBeforeSave = clientRepository.findAll().size();
        Client client1 = new Client();
        client1.setCin(12345678L);
        client1.setPrenom(StringUtils.repeat("a",66));
        List<Client> listClientAfterSave = new ArrayList<>();
        boolean throwException = false;
        try {
            clientService.createClient(client1);
            listClientAfterSave = clientRepository.findAll();
        } catch (Exception e) {
            throwException = true;
        }
        assertThat(listClientAfterSave.size(), is(sizeListClientBeforeSave));
        assertThat(throwException, is(true));
    }

    @Test
    public void checkNumTelIsLengthThan8() throws Exception {
        int sizeListClientBeforeSave = clientRepository.findAll().size();
        Client client1 = new Client();
        client1.setCin(8886411L);
        client1.setNumTel(123);
        List<Client> listClientAfterSave = new ArrayList<>();
        boolean throwException = false;
        try {
            clientService.createClient(client1);
            listClientAfterSave = clientRepository.findAll();
        } catch (Exception e) {
            throwException = true;
        }
        assertThat(listClientAfterSave.size(), is(sizeListClientBeforeSave));
        assertThat(throwException, is(true));
    }

    @Test
    public void checkEmailIsLengthMax60() throws Exception {
        int sizeListClientBeforeSave = clientRepository.findAll().size();
        Client client1 = new Client();
        client1.setCin(10000003L);
        client1.setEmail(StringUtils.repeat("a",66));
        List<Client> listClientAfterSave = new ArrayList<>();
        boolean throwException = false;
        try {
            clientService.createClient(client1);
            listClientAfterSave = clientRepository.findAll();
        } catch (Exception e) {
            throwException = true;
            ;
        }
        assertThat(listClientAfterSave.size(), is(sizeListClientBeforeSave));
        assertThat(throwException, is(true));
    }
    @Test
    public void checkEmailIsLengthMin10() throws Exception {
        int sizeListClientBeforeSave = clientRepository.findAll().size();
        Client client1 = new Client();
        client1.setCin(10000003L);
        client1.setEmail(StringUtils.repeat("a",8));
        List<Client> listClientAfterSave = new ArrayList<>();
        boolean throwException = false;
        try {
            clientService.createClient(client1);
            listClientAfterSave = clientRepository.findAll();
        } catch (Exception e) {
            throwException = true;
            ;
        }
        assertThat(listClientAfterSave.size(), is(sizeListClientBeforeSave));
        assertThat(throwException, is(true));
    }
}