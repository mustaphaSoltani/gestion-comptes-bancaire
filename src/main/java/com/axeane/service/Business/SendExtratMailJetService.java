package com.axeane.service.Business;

import com.axeane.web.util.client.ClientOptions;
import com.axeane.web.util.client.MailjetClient;
import com.axeane.web.util.client.MailjetRequest;
import com.axeane.web.util.client.MailjetResponse;
import com.axeane.web.util.client.errors.MailjetException;
import com.axeane.web.util.client.errors.MailjetRateLimitException;
import com.axeane.web.util.client.errors.MailjetServerException;
import com.axeane.web.util.client.errors.MailjetSocketTimeoutException;
import com.axeane.web.util.client.resource.Emailv31;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class SendExtratMailJetService {
    public void sendExtrait() throws MailjetException, MailjetSocketTimeoutException, MailjetRateLimitException, MailjetServerException, IOException {
        MailjetClient client;
        MailjetRequest email;
        MailjetResponse response;

        // Note how we set the version to v3.1 using ClientOptions
        client = new MailjetClient("cbaf40b17a7417bce03a5df70a163043", "59bd260cb89b775dec4ea67d2287b831", new ClientOptions("v3.1"));

        JSONObject message = new JSONObject();
        message.put(Emailv31.Message.FROM, new JSONObject()
                .put(Emailv31.Message.EMAIL, "komptacloud@psyscertifies.com")
                .put(Emailv31.Message.NAME, "Envoi d'extrait bancaire")
        )
                .put(Emailv31.Message.SUBJECT, "Envoi d'extrait bancaire")
                //.put(Emailv31.Message.SUBJECT, encoded)
                .put(Emailv31.Message.TEXTPART, "Dear passenger, welcome to Mailjet! May the delivery force be with you!")
                .put(Emailv31.Message.HTMLPART, "<h3>Dear passenger, welcome to Mailjet</h3><br/>May the delivery force be with you!")
                .put(Emailv31.Message.ATTACHMENTS, new JSONArray()
                        .put(new JSONObject()
                                .put("ContentType", "pdf/plain")
                                .put("Filename", "C:/Users/User/Desktop/jasper/response.pdf")
                                .put("Base64Content", "VGhpcyBpcyB5b3VyIGF0dGFjaGVkIGZpbGUhISEK")))
                                .put(Emailv31.Message.TO, new JSONArray()
                                        .put(new JSONObject()
                                                .put(Emailv31.Message.EMAIL, "mustaphasoltani@gmail.com")));

        email = new MailjetRequest(Emailv31.resource).property(Emailv31.MESSAGES, (new JSONArray()).put(message));

        response = client.post(email);
        System.out.println(response.getStatus());
        System.out.println(response.getData());
    }
}