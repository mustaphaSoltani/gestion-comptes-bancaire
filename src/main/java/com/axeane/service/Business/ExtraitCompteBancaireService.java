package com.axeane.service.Business;

import com.axeane.domain.Client;
import com.axeane.service.ClientService;
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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ExtraitCompteBancaireService {
    private final Logger log = LoggerFactory.getLogger(ClientResource.class);

    private final ClientService clientService;

    public ExtraitCompteBancaireService(ClientService clientService) {
        this.clientService = clientService;

    }

    public void exportextraitBancaireToPdf(HttpServletResponse response, Long id) {

        try {
            InputStream inputStream = getClass().getResourceAsStream("/extraitt.jrxml");
            JasperDesign jasperDesign = JRXmlLoader.load(inputStream);
            JasperReport jasperReport = JasperCompileManager.compileReport(jasperDesign);
            Map<String, Object> parametreMap = new HashMap<>();
//
//            Client client = new Client();
//            client.setNom(clientService.getClientById(id).getNom());
//            List<Client> clientModels = new ArrayList<>();
//            clientModels.add(client);

            JRDataSource jrDataSource = new JREmptyDataSource();
//            parametreMap.put("prenom","Mustapha");
//            parametreMap.put("nom","Soltani");
            //parametreMap.put("datasource", jrDataSource);
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
