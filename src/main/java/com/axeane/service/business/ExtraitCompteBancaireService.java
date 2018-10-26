package com.axeane.service.business;

import com.axeane.domain.Client;
import com.axeane.domain.Compte;
import com.axeane.domain.Mouvement;
import com.axeane.models.CompteModel;
import com.axeane.repository.CompteRepository;
import com.axeane.service.ClientService;
import com.axeane.service.MouvementService;
import com.axeane.web.rest.ClientResource;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.util.*;

@Service
public class ExtraitCompteBancaireService {
    private final Logger log = LoggerFactory.getLogger(ClientResource.class);

    private final ClientService clientService;
    private final MouvementService mouvementService;
    private final CompteRepository compteRepository;

    public ExtraitCompteBancaireService(ClientService clientService, MouvementService mouvementService, CompteRepository compteRepository) {
        this.clientService = clientService;
        this.mouvementService = mouvementService;
        this.compteRepository = compteRepository;

    }

    public void exportextraitBancaireToPdf(HttpServletResponse response, Integer numC) {

        try {
            InputStream inputStream = getClass().getResourceAsStream("/reports/extraitt.jrxml");
            JasperDesign jasperDesign = JRXmlLoader.load(inputStream);
            JasperReport jasperReport = JasperCompileManager.compileReport(jasperDesign);
            Map<String, Object> parametreMap = new HashMap<>();
            Client client = clientService.getClientBynNumCompte(numC);
            Compte compte = compteRepository.findByNumCompte(numC);

            Set<Mouvement> mouvements = compte.getMouvements();
            List<CompteModel> compteModels = new ArrayList<>();
            for (Mouvement detailsMouvement : mouvements) {
                CompteModel detailsModel = new CompteModel();
                detailsModel.setDate(detailsMouvement.getDate());
                detailsModel.setSomme(detailsMouvement.getSomme());
                detailsModel.setTypeMouvement(detailsMouvement.getTypeMouvement());
                BigDecimal resultTotalHt = BigDecimal.ZERO;
                resultTotalHt = detailsMouvement.getCompte().getSolde();
                compteModels.add(detailsModel);
            }
            //JRDataSource jrDataSource = new JRBeanCollectionDataSource(compteModels);
            JRDataSource jrDataSource = new JREmptyDataSource();
            parametreMap.put("nom", client.getNom());
            parametreMap.put("num", numC);
            parametreMap.put("prenom", client.getPrenom());

            parametreMap.put("datasource", jrDataSource);
            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parametreMap, jrDataSource);
            response.setContentType("application/pdf");
            response.setHeader("Content-disposition", "attachment; filename=report.pdf");
            final OutputStream outputStream = response.getOutputStream();
            JasperExportManager.exportReportToPdfStream(jasperPrint, outputStream);
        } catch (JRException ex) {
            log.info("file extraitBancaire.jrxml Exception");
        } catch (IOException ex) {
            log.info("extraitBancairePdf IOException");
        }

    }

}
