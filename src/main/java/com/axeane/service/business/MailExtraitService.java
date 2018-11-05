package com.axeane.service.business;

import com.axeane.domain.Mail;
import com.mailjet.client.ClientOptions;
import com.mailjet.client.MailjetClient;
import com.mailjet.client.MailjetRequest;
import com.mailjet.client.MailjetResponse;
import com.mailjet.client.errors.MailjetException;
import com.mailjet.client.errors.MailjetSocketTimeoutException;
import com.mailjet.client.resource.Emailv31;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class MailExtraitService {

    public void sendExtrait(Mail mail) throws MailjetException, MailjetSocketTimeoutException, IOException {
        MailjetClient client;
        MailjetRequest email;
        MailjetResponse response;

        client = new MailjetClient("cbaf40b17a7417bce03a5df70a163043", "59bd260cb89b775dec4ea67d2287b831", new ClientOptions("v3.1"));

        JSONObject message = new JSONObject();
        message.put(Emailv31.Message.FROM, new JSONObject()
                .put(Emailv31.Message.EMAIL, "komptacloud@psyscertifies.com")
                .put(Emailv31.Message.NAME, mail.getTitre())
        )
                .put(Emailv31.Message.SUBJECT, mail.getObjet())
                .put(Emailv31.Message.TEXTPART, mail.getText())
                .put(Emailv31.Message.HTMLPART, mail.getText())
                .put(Emailv31.Message.ATTACHMENTS, new JSONArray()
                        .put(new JSONObject()
                                .put("ContentType", "application/x-pdf")
                                .put("Filename", mail.getUrlFile())
                                .put("Base64Content", "VGhpcyBpcyB5b3VyIGF0dGFjaGVkIGZpbGUhISEK")))
                .put(Emailv31.Message.TO, new JSONArray()
                        .put(new JSONObject()
                                .put(Emailv31.Message.EMAIL, mail.getEmail())));

        email = new MailjetRequest(Emailv31.resource).property(Emailv31.MESSAGES, (new JSONArray()).put(message));

        response = client.post(email);
        System.out.println(response.getStatus());
        System.out.println(response.getData());
    }
}