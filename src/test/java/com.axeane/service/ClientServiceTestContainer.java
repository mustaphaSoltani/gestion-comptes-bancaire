package com.axeane.service;

import com.axeane.GestionCompteBancaireApplication;
import com.axeane.domain.Client;
import com.axeane.repository.ClientRepository;
import org.junit.ClassRule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.util.TestPropertyValues;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.testcontainers.containers.PostgreSQLContainer;

import java.time.Duration;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

@RunWith(SpringRunner.class)
@ContextConfiguration(initializers = {ClientServiceTestContainer.Initializer.class})
@SpringBootTest(classes = GestionCompteBancaireApplication.class)
@TestPropertySource("/application-test-container.properties")
@ComponentScan("com.axeane")
public class ClientServiceTestContainer {
    @Autowired
    private ClientService clientService;
    @Autowired
    private ClientRepository clientRepository;

    @ClassRule
    public static PostgreSQLContainer postgreSQLContainer =
            (PostgreSQLContainer) new PostgreSQLContainer("postgres:9.6.10")
                    .withDatabaseName("spring")
                    .withUsername("postgres")
                    .withPassword("admin")
                    .withStartupTimeout(Duration.ofSeconds(10));

    public static class Initializer implements ApplicationContextInitializer<ConfigurableApplicationContext> {
        public void initialize(ConfigurableApplicationContext configurableApplicationContext) {
            TestPropertyValues.of(
                    "spring.datasource.url=" + postgreSQLContainer.getJdbcUrl(),
                    "spring.datasource.username=" + postgreSQLContainer.getUsername(),
                    "spring.datasource.password=" + postgreSQLContainer.getPassword()
            ).applyTo(configurableApplicationContext.getEnvironment());
        }
    }

    @Test
    public void save() throws Exception {
        System.out.println("start");
        Client client = new Client();
        client.setNom("Bilel");
        client.setAdresse("tunis");
        client.setCin(78978978);
        clientService.createClient(client);
        System.out.println("created");

        Client clientResult = clientRepository.findAll().get(clientRepository.findAll().size() - 1);
        System.out.println("list");

        assertThat(clientResult.getNom(), is("Bilel"));
    }

}
