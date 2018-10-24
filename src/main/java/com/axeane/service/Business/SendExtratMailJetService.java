package com.axeane.service.Business;

import com.axeane.service.Business.client.ClientOptions;
import com.mailjet.client.MailjetClient;
import com.mailjet.client.MailjetRequest;
import com.mailjet.client.MailjetResponse;
import com.mailjet.client.errors.MailjetException;
import com.mailjet.client.errors.MailjetSocketTimeoutException;
import com.axeane.service.Business.client.resource.Emailv31;
import com.turbomanage.httpclient.RequestHandler;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

@Service
public class SendExtratMailJetService {
    public void sendExtrait() throws MailjetException, MailjetSocketTimeoutException {
        MailjetClient client;
        MailjetRequest email;
        MailjetResponse response;

        // Note how we set the version to v3.1 using ClientOptions
        client = new MailjetClient("cbaf40b17a7417bce03a5df70a163043", "59bd260cb89b775dec4ea67d2287b831", (RequestHandler) new ClientOptions("v3.1"));

        JSONObject message = new JSONObject();
        message.put(Emailv31.Message.FROM, new JSONObject()
                .put(Emailv31.Message.EMAIL, "komptacloud@psyscertifies.com")
                .put(Emailv31.Message.NAME, "Mailjet Pilot")
        )
                .put(Emailv31.Message.SUBJECT, "mustapha test envoi avec mailjet!")
                .put(Emailv31.Message.TEXTPART, "Dear passenger, welcome to Mailjet! May the delivery force be with you!")
                .put(Emailv31.Message.HTMLPART, "<h3>Dear passenger, welcome to Mailjet</h3><br/>May the delivery force be with you!")
                .put(Emailv31.Message.TO, new JSONArray()
                        .put(new JSONObject()
                                .put(Emailv31.Message.EMAIL, "mustaphasoltani@gmail.com")));

        email = new MailjetRequest(Emailv31.resource).property(Emailv31.MESSAGES, (new JSONArray()).put(message));

        response = client.post(email);
    }
}