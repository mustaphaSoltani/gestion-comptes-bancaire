package com.axeane.web.util.client.easy;


import com.axeane.web.util.client.MailjetRequest;
import com.axeane.web.util.client.resource.sms.SmsSend;
import com.mailjet.client.errors.MailjetException;

/**
 * Mailjet easy sms sending object.
 *
 * It's a MailjetRequest object wrapper
 */
public class MJEasySms {
    private final MJEasyClient client;
    private final MailjetRequest request;

    protected MJEasySms(MJEasyClient client, MailjetRequest request) {
        this.client = client;
        this.request = request;
    }

    /**
     * Set the sender
     * @param number sms sender name
     * @return Current instance
     */
    public MJEasySms from(String number) {
    	request.property(SmsSend.FROM, number);
    	return this;
    }

    /**
     * Set the recipient
     * @param number Number of the recipient
     * @return Current instance
     */
    public MJEasySms to(String number) {
        request.property(SmsSend.TO, number);
        return this;
    }

    /**
     * Set the text content of the message
     * @param text Text content
     * @return Current instance
     */
    public MJEasySms text(String text) {
        request.property(SmsSend.TEXT, text);
        return this;
    }
    
    /**
     * Send the sms
     * @return a MailjetResponse instance
     * @throws MailjetException
     */
    public com.axeane.web.util.client.MailjetResponse send() throws MailjetException, com.axeane.web.util.client.errors.MailjetSocketTimeoutException, com.mailjet.client.errors.MailjetSocketTimeoutException, com.axeane.web.util.client.errors.MailjetException {
        return client.getClient().post(request);
    }

}
