package com.axeane.service.testH2;

import com.axeane.GestionCompteBancaireApplication;
import com.axeane.domain.Client;
import com.axeane.domain.Compte;
import com.axeane.domain.Mouvement;
import com.axeane.domain.enumuration.TypeMouvementEnum;
import com.axeane.repository.ClientRepository;
import com.axeane.repository.CompteRepository;
import com.axeane.repository.MouvementRepository;
import com.axeane.service.MouvementService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.PropertySource;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = GestionCompteBancaireApplication.class)
@DataJpaTest
@ComponentScan("com.axeane")
@TestPropertySource("/application.properties")
public class MouvementServiceTest {

    @Autowired
    private MouvementService mouvementService;

    @Autowired
    private MouvementRepository mouvementRepository;

    @Autowired
    private CompteRepository compteRepository;

    @Test
    public void saveMouvementTest() throws Exception {
        Mouvement mouvement = new Mouvement();
        Compte compte = new Compte();
        compte.setNumCompte(123);
        compteRepository.saveAndFlush(compte);
        mouvement.setCompteId(compte.getId());
        mouvement.setSomme(new BigDecimal(10400));
        mouvement.setTypeMouvement(TypeMouvementEnum.RETRAIT);

        mouvementService.saveMouvement(mouvement);
        Mouvement mouvementResult = mouvementRepository.findAll().get(mouvementRepository.findAll().size() - 1);
        assertThat(mouvementResult.getCompteId(), is(1L));
        assertThat(mouvementResult.getSomme(), is(new BigDecimal(10400)));
    }

    @Test
    public void getMouvementByIdTest() throws Exception {
        Compte compte = new Compte();
        compte.setNumCompte(456);
        compteRepository.saveAndFlush(compte);
        Mouvement mouvement = new Mouvement();
        mouvement.setCompteId(compte.getId());
        mouvement.setSomme(new BigDecimal(10000));
        mouvement.setTypeMouvement(TypeMouvementEnum.RETRAIT);
        mouvementService.saveMouvement(mouvement);
        List<Mouvement> listMouvementAfterSave = mouvementService.findAllMouvementByCompte(456);
        Mouvement mouvementtSaved = mouvementService.getMouvementById(listMouvementAfterSave.get(listMouvementAfterSave.size() - 1).getId());
        assertThat(mouvementtSaved.getCompteId(), is(1L));
        assertThat(mouvementtSaved.getSomme(), is(new BigDecimal(10000)));
    }

    @Test
    public void findAllMouvementTest() throws Exception {
        int sizeListMouvementBeforeSave = mouvementService.findAllMouvementByCompte(456).size();
        Compte compte = new Compte();
        compte.setNumCompte(521);
        compteRepository.saveAndFlush(compte);
        Mouvement mouvement = new Mouvement();
        mouvement.setCompteId(compte.getId());
        mouvement.setSomme(new BigDecimal(1000));
        mouvement.setTypeMouvement(TypeMouvementEnum.RETRAIT);

        List<Mouvement> listMouvementAfterSave = new ArrayList<>();
        boolean throwException = false;
        try {
            mouvementRepository.save(mouvement);
            listMouvementAfterSave = mouvementService.findAllMouvementByCompte(521);
        } catch (Exception e) {
            throwException = true;
        }
        assertThat(listMouvementAfterSave.size(), is(sizeListMouvementBeforeSave ));
        assertThat(throwException, is(false));
    }

    @Test
    public void deleteMouvementTest() throws Exception {
        Compte compte = new Compte();
        compte.setNumCompte(214);
        compteRepository.saveAndFlush(compte);
        int sizeListMouvementBeforeDelete = mouvementService.findAllMouvementByCompte(214).size();
        Mouvement mouvement = new Mouvement();
        mouvement.setCompteId(compte.getId());
        mouvement.setSomme(new BigDecimal(1000));
        mouvement.setTypeMouvement(TypeMouvementEnum.RETRAIT);
        mouvementService.saveMouvement(mouvement);
        Mouvement mouvement1 = mouvementService.findAllMouvementByCompte(214).get(mouvementService.findAllMouvementByCompte(214).size() - 1);
        mouvementService.delete(mouvement1.getId());
        int sizeListMouvementAfterDelete = mouvementService.findAllMouvementByCompte(214).size();
        assertThat(sizeListMouvementBeforeDelete, is(sizeListMouvementAfterDelete));
    }
}
