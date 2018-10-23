package com.axeane.service;

import com.axeane.GestionCompteBancaireApplication;
import com.axeane.domain.Client;
import com.axeane.domain.Mouvement;
import com.axeane.domain.enumuration.TypeMouvementEnum;
import com.axeane.repository.ClientRepository;
import com.axeane.repository.MouvementRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.PropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

//@PropertySource("classpath:resource/application-test.properties")
@RunWith(SpringRunner.class)
@SpringBootTest(classes =GestionCompteBancaireApplication.class)
public class MouvementServiceTest {

    @Autowired
    private MouvementService mouvementService;

    @Autowired
    private MouvementRepository mouvementRepository;

    @Test
    public void save() throws Exception {
        Mouvement mouvement = new Mouvement();
        mouvement.setCompteId(1L);
        mouvement.setSomme(1000D);
        mouvement.setTypeMouvement(TypeMouvementEnum.RETRAIT);

        mouvementService.save(mouvement);
        Mouvement mouvementResult = mouvementRepository.findAll().get(mouvementRepository.findAll().size() - 1);
        assertThat(mouvementResult.getCompteId(), is(2L));

    }

    @Test
    public void getMouvementById() throws Exception {
        Mouvement mouvement=new Mouvement();
        mouvement.setCompteId(1L);
        mouvement.setSomme(1000D);
        mouvement.setTypeMouvement(TypeMouvementEnum.RETRAIT);

        mouvementService.save(mouvement);
        List<Mouvement> listMouvementAfterSave = mouvementService.findAll();
        Mouvement mouvementtSaved = mouvementService.getMouvementById(listMouvementAfterSave.get(listMouvementAfterSave.size() - 1).getId());
        assertThat(mouvementtSaved.getCompteId(), is(3L));
    }


    @Test
    public void findAll() throws Exception {
        int sizeListMouvementBeforeSave = mouvementService.findAll().size();
        Mouvement mouvement=new Mouvement();
        mouvement.setCompteId(3L);
        mouvement.setSomme(1000D);
        mouvement.setTypeMouvement(TypeMouvementEnum.RETRAIT);

        List<Mouvement> listMouvementAfterSave = new ArrayList<>();
        boolean throwException = false;
        try {
            mouvementRepository.save(mouvement);
            listMouvementAfterSave = mouvementService.findAll();
        } catch (Exception e) {
            throwException = true;
        }
        assertThat(listMouvementAfterSave.size(), is(sizeListMouvementBeforeSave + 1));
        assertThat(throwException, is(false));
    }


    @Test
    public void delete() throws Exception {
        int sizeListMouvementBeforeDelete = mouvementService.findAll().size();
        Mouvement mouvement = mouvementService.findAll().get(mouvementService.findAll().size() - 1);
        mouvementService.delete(mouvement.getId());
        int sizeListMouvementAfterDelete = mouvementService.findAll().size();
        assertThat(sizeListMouvementAfterDelete - 1, is(sizeListMouvementAfterDelete));
    }

}
