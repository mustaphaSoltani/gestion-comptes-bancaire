//package com.axeane.service.Business;//package com.axeane.service.Business;
//
//import com.axeane.domain.Compte;
//import com.axeane.domain.Mouvement;
//import com.axeane.models.CompteModel;
//import com.axeane.repository.CompteRepository;
//import com.axeane.service.ClientService;
//import com.axeane.web.rest.ClientResource;
//import net.sf.jasperreports.engine.*;
//import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
//import net.sf.jasperreports.engine.design.JasperDesign;
//import net.sf.jasperreports.engine.xml.JRXmlLoader;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.stereotype.Service;
//
//import java.io.*;
//import java.util.*;
//
//@Service
//public class JasperCompteBusinessService {
//
//    private final Logger log = LoggerFactory.getLogger(ClientResource.class);
//
//    private final ClientService clientService;
//    private final CompteRepository compteRepository;
//
//    public JasperCompteBusinessService(ClientService clientService, CompteRepository compteRepository) {
//        this.clientService = clientService;
//        this.compteRepository = compteRepository;
//    }
//
//    public ByteArrayInputStream exportextraitBancaireToPdf( Integer numCompte) throws JRException {
//
//        try {
//            InputStream inputStream = getClass().getResourceAsStream("/report/report1.jrxml");
//            JasperDesign jasperDesign = JRXmlLoader.load(inputStream);
//            JasperReport jasperReport = JasperCompileManager.compileReport(jasperDesign);
//            Map<String, Object> parametreMap = new HashMap<>();
//
//            Compte compte= compteRepository.findByNumCompte(numCompte);
//            Set<Mouvement> mouvements = compte.getMouvements();
//            CompteModel compteModel=new CompteModel();
//            compteModel.setDate(mouvements.iterator().next().getDate());
//            compteModel.setNumCompte(numCompte);
//            compteModel.setSomme(mouvements.iterator().next().getSomme());
//            compteModel.setTypeMouvement(mouvements.iterator().next().getTypeMouvement());
//            List<DetailsModel> detailsModels = new ArrayList<>();
//            JRDataSource jrDataSource = new JRBeanCollectionDataSource(detailsModels);
//            parametreMap.put("nomSoc", entreprise.getRaisonSocial().toUpperCase());
//            parametreMap.put("adrSoc", (entreprise.getAdresses().iterator().next().getNum()+" "+entreprise.getAdresses().iterator().next().getRue()));
// ;
//
//            parametreMap.put("datasource", jrDataSource);
//            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parametreMap, jrDataSource);
//            byte[] bytes;
//            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
//
//            JasperExportManager.exportReportToPdfStream(jasperPrint, outputStream);
//            bytes = outputStream.toByteArray();
//            return new ByteArrayInputStream(bytes);
//        } catch (JRException ex) {
//            log.info("file listEntreprises.jrxml Exception");
//            throw ex;
//        }
//
//    }
//}
