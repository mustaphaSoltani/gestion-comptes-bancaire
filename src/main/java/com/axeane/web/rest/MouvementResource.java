package com.axeane.web.rest;

import com.axeane.domain.Mouvement;
import com.axeane.domain.Views;
import com.axeane.domain.util.ResponseUtil;
import com.axeane.repository.MouvementRepository;
import com.axeane.service.MouvementService;
import com.axeane.web.util.HeaderUtil;
import com.fasterxml.jackson.annotation.JsonView;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;


/**
 * REST controller for managing Mouvement.
 */
@RestController
@RequestMapping("/api/mouvements")
public class MouvementResource {
    private final Logger log = LoggerFactory.getLogger(MouvementResource.class);

    private static final String ENTITY_NAME = "mouvement";

    private final MouvementService mouvementService;
    private final MouvementRepository mouvementRepository;

    public MouvementResource(MouvementService mouvementService, MouvementRepository mouvementRepository) {
        this.mouvementService = mouvementService;
        this.mouvementRepository = mouvementRepository;
    }


    /**
     * POST  /mouvements : Create a new Mouvement.
     *
     * @param mouvement the mouvement to create
     * @return the ResponseEntity with status 201 (Created) and with body the new client, or with status 400 (Bad Request) if the client has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping
    @JsonView(value = {Views.MouvementView.class})
    public ResponseEntity<Mouvement> createMouvement(@Valid @RequestBody Mouvement mouvement) throws URISyntaxException {
        log.debug("REST request to save Mouvement : {}", mouvement);
        if (mouvement.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new mouvement cannot already have an ID")).body(null);
        }
        Mouvement result = mouvementService.save(mouvement);
        return ResponseEntity.created(new URI("/api/mouvements/" + result.getId()))
                .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
                .body(result);
    }

    /**
     * PUT  /mouvements : Updates an existing mouvement.
     *
     * @param mouvement the client to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated mouvement,
     * or with status 400 (Bad Request) if the mouvement is not valid,
     * or with status 500 (Internal Server Error) if the mouvement couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping
    @JsonView(value = {Views.MouvementView.class})
    public ResponseEntity<Mouvement> updateClient(@Valid @RequestBody Mouvement mouvement) throws URISyntaxException {
        log.debug("REST request to update Mouvement : {}", mouvement);
        if (mouvement.getId() == null) {
            return createMouvement(mouvement);
        }
        Mouvement result = mouvementService.save(mouvement);
        return ResponseEntity.ok()
                .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, mouvement.getId().toString()))
                .body(result);
    }


    @GetMapping
    @JsonView(value = {Views.MouvementView.class})
    public ResponseEntity<List<Mouvement>> getAllClient() {
        log.debug("REST request to get a page of mouvements");
        List<Mouvement> page = mouvementService.findAll();
        return new ResponseEntity<>(page, HttpStatus.OK);
    }

    /**
     * GET  /clients/:id : get the "id" mouvement.
     *
     * @param id the id of the mouvement to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the client, or with status 404 (Not Found)
     */
    @GetMapping("/{id}")
    @JsonView(value = {Views.MouvementView.class})
    public ResponseEntity getMouvementById(@PathVariable Long id) {
        log.debug("REST request to get Mouvement : {}", id);
        Optional<Mouvement> mouvement = Optional.ofNullable(mouvementService.getMouvementById(id));
        return ResponseUtil.wrapOrNotFound(mouvement);
    }

    /**
     * DELETE  /mouvements/:id : delete the "id" mouvement.
     *
     * @param id the id of the mouvement to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMouvement(@PathVariable Long id) {
        log.debug("REST request to delete Mouvement : {}", id);
        mouvementService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
