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

@Service
public class SendExtraitMailJetService {

    public void sendExtrait(Mail sendmail) throws MailjetException, MailjetSocketTimeoutException {
        MailjetClient client;
        MailjetRequest email;
        MailjetResponse response;

        // Note how we set the version to v3.1 using ClientOptions
        client = new MailjetClient("7cae7a30ba4e268aaaf3249232d1c628\n", "17d797b2133762f8f4a93fa474d6ab83\n", new ClientOptions("v3.1"));

        JSONObject message = new JSONObject();
        message.put(Emailv31.Message.FROM, new JSONObject()
                .put(Emailv31.Message.EMAIL, "mustaphasoltani@gmail.com")
                .put(Emailv31.Message.NAME, sendmail.getTitre())
        )
                .put(Emailv31.Message.SUBJECT, sendmail.getObjet())
                .put(Emailv31.Message.TEXTPART, sendmail.getMessage())
                .put(Emailv31.Message.HTMLPART, sendmail.getText())
                .put(Emailv31.Message.ATTACHMENTS, new JSONArray()
                        .put(new JSONObject()
                                .put("ContentType", "pdf/plain")
                                .put("Filename", sendmail.getUrlFile())
                                .put("Base64Content", "VGhpcyBpcyB5b3VyIGF0dGFjaGVkIGZpbGUhISEK")))
                .put(Emailv31.Message.TO, new JSONArray()
                        .put(new JSONObject()
                                .put(Emailv31.Message.EMAIL, sendmail.getEmail())));

        email = new MailjetRequest(Emailv31.resource).property(Emailv31.MESSAGES, (new JSONArray()).put(message));

        response = client.post(email);
        System.out.println(response.getStatus());
        System.out.println(response.getData());
    }
}