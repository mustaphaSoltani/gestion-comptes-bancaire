package com.axeane.web.rest;

import com.axeane.GestionCompteBancaireApplication;
import com.axeane.domain.Mouvement;
import com.axeane.domain.enumuration.TypeMouvementEnum;
import com.axeane.repository.MouvementRepository;
import com.axeane.service.MouvementService;
import com.axeane.web.errors.ExceptionTranslator;
import com.axeane.web.rest.config.TestUtil;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.Date;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = GestionCompteBancaireApplication.class)
public class MouvementResourceTest {

    private static final TypeMouvementEnum DEFAULT_TYPE_MOUVEMENT = TypeMouvementEnum.RETRAIT;
    private static final TypeMouvementEnum UPDATED_TYPE_MOUVEMENT= TypeMouvementEnum.RETRAIT;

    private static final Double DEFAULT_SOMME = 200.00;
    private static final Double UPDATED_SOMME = 300.00;

    private static final Date DEFAULT_DATE = new Date(2018-12-12);
    private static final Date UPDATED_DATE = new Date(2018-12-12);

    @Autowired
    private MouvementService mouvementService;

    @Autowired
    private MouvementRepository mouvementRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restMouvementMockMvc;

    private Mouvement mouvement;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        MouvementResource mouvementResource = new MouvementResource(mouvementService, mouvementRepository);
        this.restMouvementMockMvc = MockMvcBuilders.standaloneSetup(mouvementResource)
                .setCustomArgumentResolvers(pageableArgumentResolver)
                .setControllerAdvice(exceptionTranslator)
                .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     * <p>
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public Mouvement createEntity(EntityManager em) {
        Mouvement mouvement = new Mouvement();
        mouvement.setCompteId(1L);
        mouvement.setSomme(DEFAULT_SOMME);
        mouvement.setTypeMouvement(DEFAULT_TYPE_MOUVEMENT);
        mouvement.setDate(DEFAULT_DATE);

        return mouvement;
    }

    @Before
    public void initTest() {
        mouvement = createEntity(em);
    }

    @Test
    @Transactional
    public void createMouvement() throws Exception {
        int databaseSizeBeforeCreate = mouvementRepository.findAll().size();
        restMouvementMockMvc.perform(post("/api/mouvements")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(mouvement)))
                .andExpect(status().isCreated());
        // Validate the Mouvement in the database
        List<Mouvement> mouvementList = mouvementRepository.findAll();
        assertThat(mouvementList).hasSize(databaseSizeBeforeCreate + 1);
        Mouvement testMouvement = mouvementList.get(mouvementList.size() - 1);
        assertThat(testMouvement.getDate()).isEqualTo(DEFAULT_DATE);
        assertThat(testMouvement.getSomme()).isEqualTo(DEFAULT_SOMME);
        assertThat(testMouvement.getTypeMouvement()).isEqualTo(DEFAULT_TYPE_MOUVEMENT);
    }

    @Test
    @Transactional
    public void updateMouvement() throws Exception {
        // Initialize the database
        mouvementRepository.save(mouvement);
        int databaseSizeBeforeUpdate = mouvementRepository.findAll().size();

        // Update the Mouvement
        Mouvement updatedMouvement = mouvementRepository.getOne(mouvement.getId());
        updatedMouvement.setDate(UPDATED_DATE);
        updatedMouvement.setSomme(UPDATED_SOMME);
        updatedMouvement.setTypeMouvement(UPDATED_TYPE_MOUVEMENT);

        restMouvementMockMvc.perform(put("/api/mouvements")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(updatedMouvement)))
                .andExpect(status().isOk());

        // Validate the Client in the database
        List<Mouvement> mouvementList = mouvementRepository.findAll();
        assertThat(mouvementList).hasSize(databaseSizeBeforeUpdate);
        Mouvement testMouvement = mouvementList.get(mouvementList.size() - 1);
        assertThat(testMouvement.getTypeMouvement()).isEqualTo(UPDATED_TYPE_MOUVEMENT);
        assertThat(testMouvement.getDate()).isEqualTo(UPDATED_DATE);
        assertThat(testMouvement.getSomme()).isEqualTo(UPDATED_SOMME);
    }

    @Test
    public void getAllMouvement() throws Exception {
        // Initialize the database
        Mouvement mouvementSaved = mouvementRepository.saveAndFlush(mouvement);
        // Get all the mouvementList
        restMouvementMockMvc.perform(get("/api/mouvements?sort=id,desc", mouvementSaved.getId()))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("$.[*].id").value(hasItem(mouvementSaved.getId().intValue())))
                .andExpect(jsonPath("$.[*].somme").value(hasItem(DEFAULT_SOMME)))
                //.andExpect(jsonPath("$.[*].typeMouvement").value(hasItem(DEFAULT_TYPE_MOUVEMENT)))
                //.andExpect(jsonPath("$.[*].date").value(hasItem(DEFAULT_DATE)))
        ;
    }

    @Test
    public void getMouvementById() throws Exception {
        // Initialize the database
        Mouvement mouvementSaved = mouvementRepository.saveAndFlush(mouvement);
        // Get the clients
        restMouvementMockMvc.perform(get("/api/mouvements/{id}", mouvementSaved.getId()))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("$.id").value(mouvementSaved.getId()))
                .andExpect(jsonPath("$.somme").value(DEFAULT_SOMME))
                .andExpect(jsonPath("$.typeMouvement").value(DEFAULT_TYPE_MOUVEMENT))
                .andExpect(jsonPath("$.date").value(DEFAULT_DATE));
    }

    @Test
    public void getNonExistingMouvement() throws Exception {
        // Get the mouvement
        restMouvementMockMvc.perform(get("/api/mouvements/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void deleteMouvement() throws Exception {
        // Initialize the database
        mouvementRepository.save(mouvement);
        int databaseSizeBeforeDelete = mouvementRepository.findAll().size();

        // Get the mouvement
        restMouvementMockMvc.perform(delete("/api/mouvements/{id}", mouvement.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<Mouvement> mouvementtList = mouvementRepository.findAll();
        assertThat(mouvementtList).hasSize(databaseSizeBeforeDelete - 1);
    }
}