package com.axeane.service.business;

import com.axeane.domain.Client;
import com.axeane.domain.Mouvement;
import com.axeane.models.MouvementModel;
import com.axeane.repository.CompteRepository;
import com.axeane.service.ClientService;
import com.axeane.service.MouvementService;
import com.axeane.web.rest.ClientResource;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
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

    public ExtraitCompteBancaireService(ClientService clientService, MouvementService mouvementService) {
        this.clientService = clientService;
        this.mouvementService = mouvementService;
    }

    public void exportextraitBancaireToPdf(HttpServletResponse response, Integer numCompte) {
        try {
            InputStream inputStream = getClass().getResourceAsStream("/reports/extrait_bancaire.jrxml");
            JasperDesign jasperDesign = JRXmlLoader.load(inputStream);
            JasperReport jasperReport = JasperCompileManager.compileReport(jasperDesign);
            Map<String, Object> parametreMap = new HashMap<>();

            Client client = clientService.getClientBynNumCompte(numCompte);

            List<Mouvement> mouvements = mouvementService.findAllMouvementByCompte(numCompte);

            List<MouvementModel> mouvementModels = new ArrayList<>();
            BigDecimal solde = mouvements.iterator().next().getCompte().getSolde();
            for (Mouvement detailsMouvement : mouvements) {
                MouvementModel detailsModel = new MouvementModel();
                detailsModel.setDate(detailsMouvement.getDate());
                detailsModel.setSomme(detailsMouvement.getSomme());
                detailsModel.setTypeMouvement(detailsMouvement.getTypeMouvement());
                BigDecimal resultSolde = BigDecimal.ZERO;
                switch (detailsMouvement.getTypeMouvement().toString()) {
                    case "RETRAIT":
                        resultSolde = solde.add(detailsMouvement.getSomme());
                        break;
                    case "VERSEMENT":
                        resultSolde = solde.add(detailsMouvement.getSomme());
                        break;
                    case "VIREMENT":
                        resultSolde = solde.add(detailsMouvement.getSomme());
                        break;
                }
                mouvementModels.add(detailsModel);
            }
            JRDataSource jrDataSource = new JRBeanCollectionDataSource(mouvementModels);
            //JRDataSource jrDataSource = new JREmptyDataSource();
            parametreMap.put("numCompte", numCompte);
            parametreMap.put("nom", client.getNom());
            parametreMap.put("prenom", client.getPrenom());
            parametreMap.put("adresse", client.getAdresse());
            parametreMap.put("email", client.getEmail());
            parametreMap.put("solde", solde);
            parametreMap.put("datasource", jrDataSource);

            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parametreMap, jrDataSource);

            response.setContentType("application/pdf");
            response.setHeader("Content-disposition", "attachment; filename=report.pdf");
            final OutputStream outputStream = response.getOutputStream();
            JasperExportManager.exportReportToPdfStream(jasperPrint, outputStream);
        } catch (JRException ex) {
            log.info("file extrait_bancaire.jrxml exception");
        } catch (IOException ex) {
            log.info("extraitBancairePdf IOException");
        }

    }
}
