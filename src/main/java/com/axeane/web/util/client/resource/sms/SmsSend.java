package com.axeane.web.util.client.resource.sms;

import com.axeane.web.util.client.Resource;

/**
 *
 * @author Ivaylo Ivanov
 */
public class SmsSend {

    public static final Resource resource = new Resource("sms-send", "", true);

	public static final String FROM = "From";
    public static final String TO = "To";
    public static final String TEXT = "Text";
    public static final String MESSAGEID = "MessageID";
    public static final String SMSCOUNT = "SMSCount";
    public static final String CREATIONTS = "CreationTS";
    public static final String SENTTS = "SentTS";
    public static final String COST = "Cost";
    public static final String STATUS = "Status";

}