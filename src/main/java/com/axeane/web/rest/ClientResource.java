package com.axeane.web.rest;

import com.axeane.domain.Client;
import com.axeane.domain.Views;
import com.axeane.domain.util.ResponseUtil;
//import com.axeane.service.Business.JasperCompteBusinessService;
import com.axeane.service.ClientService;
import com.axeane.web.util.HeaderUtil;
import com.fasterxml.jackson.annotation.JsonView;
import net.sf.jasperreports.engine.JRException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;


/**
 * REST controller for managing Client.
 */
@RestController
@RequestMapping("/api/clients")
public class ClientResource {
    private final Logger log = LoggerFactory.getLogger(ClientResource.class);

    private static final String ENTITY_NAME = "client";

    private final ClientService clientService;
    //private final JasperCompteBusinessService jasperCompteBusinessService;
    public ClientResource(ClientService clientService) {
        this.clientService = clientService;
        //this.jasperCompteBusinessService = jasperCompteBusinessService;
    }


    /**
     * POST  /clients : Create a new Client.
     *
     * @param client the client to create
     * @return the ResponseEntity with status 201 (Created) and with body the new client, or with status 400 (Bad Request) if the client has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping
    @JsonView(value = {Views.ClientView.class})
    public ResponseEntity<Client> createClient(@Valid @RequestBody Client client) throws URISyntaxException {
        log.debug("REST request to save Client : {}", client);
        if (client.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new client cannot already have an ID")).body(null);
        }
        Client result = clientService.save(client);
        return ResponseEntity.created(new URI("/api/clients/" + result.getId()))
                .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
                .body(result);
    }

    /**
     * PUT  /clients : Updates an existing client.
     *
     * @param client the client to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated client,
     * or with status 400 (Bad Request) if the client is not valid,
     * or with status 500 (Internal Server Error) if the client couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping
    @JsonView(value = {Views.ClientView.class})
    public ResponseEntity<Client> updateClient(@Valid @RequestBody Client client) throws URISyntaxException {
        log.debug("REST request to update Client : {}", client);
        if (client.getId() == null) {
            return createClient(client);
        }
        Client result = clientService.save(client);
        return ResponseEntity.ok()
                .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, client.getId().toString()))
                .body(result);
    }


    @GetMapping
    @JsonView(value = {Views.ClientView.class})
    public ResponseEntity<List<Client>> getAllClient() {
        log.debug("REST request to get a page of Clients");
        List<Client> page = clientService.findAll();
        return new ResponseEntity<>(page, HttpStatus.OK);
    }

    /**
     * GET  /clients/:id : get the "id" client.
     *
     * @param id the id of the client to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the client, or with status 404 (Not Found)
     */
    @GetMapping("/{id}")
    @JsonView(value = {Views.ClientView.class})
    public ResponseEntity getClientById(@PathVariable Long id) {
        log.debug("REST request to get Client : {}", id);
        Optional<Client> client = Optional.ofNullable(clientService.getClientById(id));
        return ResponseUtil.wrapOrNotFound(client);
    }

    /**
     * GET  /clients/:cin : get the "cin" client.
     *
     * @param cin the id of the client to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the client, or with status 404 (Not Found)
     */
    @GetMapping("cin/{cin}")
    @JsonView(value = {Views.ClientView.class})
    public ResponseEntity getClientByCin(@PathVariable Integer cin) {
        log.debug("REST request to get Client : {}", cin);
        Optional<Client> client = Optional.ofNullable(clientService.getClientBynCin(cin));
        return ResponseUtil.wrapOrNotFound(client);
    }

    /**
     * GET  /clients/:cin : get the "nom" client.
     *
     * @param nom the id of the client to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the client, or with status 404 (Not Found)
     */
    @GetMapping("nom/{nom}")
    @JsonView(value = {Views.ClientView.class})
    public ResponseEntity<List<Client>> getClientByNom(@PathVariable String nom) {
        log.debug("REST request to get Client : {}", nom);
        List<Client> page = clientService.getClientByNom(nom);
        return new ResponseEntity<>(page, HttpStatus.OK);
    }
    /**
     * GET  /clients/numC/:numCompte : get the "numCompte" client.
     *
     * @param numCompte the id of the client to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the client, or with status 404 (Not Found)
     */
    @GetMapping("numCpte/{numCompte}")
    @JsonView(value = {Views.ClientView.class, Views.CompteView.class})
    public ResponseEntity getClientBynumCompte(@PathVariable Integer numCompte) {
        log.debug("REST request to get Client : {}", numCompte);
        Optional<Client> client = Optional.ofNullable(clientService.getClientBynNumCompte(numCompte));
        return ResponseUtil.wrapOrNotFound(client);
    }
    /**
     * DELETE  /clients/:id : delete the "id" client.
     *
     * @param id the id of the client to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteClient(@PathVariable Long id) {
        log.debug("REST request to delete Client : {}", id);
        clientService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
//    @GetMapping("/extraitBancairepdf")
//    public @ResponseBody void extraitBancairePdf(HttpServletResponse response) throws JRException {
//        jasperCompteBusinessService.exportExtraitBancaireToPdf(response);
//    }
}
