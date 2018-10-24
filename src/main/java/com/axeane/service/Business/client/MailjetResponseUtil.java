package com.axeane.service.Business.client;

import com.axeane.service.Business.client.errors.MailjetRateLimitException;
import com.axeane.service.Business.client.errors.MailjetServerException;
import com.axeane.service.Business.client.errors.MailjetSocketTimeoutException;
import com.turbomanage.httpclient.HttpResponse;
import org.json.JSONObject;

/**
 * 
 * @author y.vanov
 *
 */
public final class MailjetResponseUtil {

	private static final int TOO_MANY_REQUEST_STATUS = 429;
	private static final int INTERNAL_SERVER_ERROR_STATUS = 500;

	private static final String STATUS_PROPERTY = "status";
	private static final String SOCKET_TIMEOUT_EXCEPTION = "Socket Timeout";
	private static final String TOO_MANY_REQUESTS_EXCEPTION = "Too Many Requests";
	private static final String INTERNAL_SERVER_ERROR_GENERAL_EXCEPTION = "Internal Server Error";

	private MailjetResponseUtil() {
	}

	public static void validateMailjetResponse(HttpResponse response)
			throws MailjetSocketTimeoutException, MailjetRateLimitException, MailjetServerException {
		if (response == null) {
			throw new MailjetSocketTimeoutException(SOCKET_TIMEOUT_EXCEPTION);
		} else if (response.getStatus() == TOO_MANY_REQUEST_STATUS) {
			throw new MailjetRateLimitException(TOO_MANY_REQUESTS_EXCEPTION);
		} else if (response.getStatus() >= INTERNAL_SERVER_ERROR_STATUS) {
			if (!isValidJSON(response.getBodyAsString())) {
				throw new MailjetServerException(INTERNAL_SERVER_ERROR_GENERAL_EXCEPTION);
			}
		}
	}

	public static String parseResponseBodyToValidString(HttpResponse response) {
		return (response.getBodyAsString() != null && !response.getBodyAsString().trim().equals("")
				? response.getBodyAsString()
				: new JSONObject().put(STATUS_PROPERTY, response.getStatus()).toString());
	}

	public static boolean isValidJSON(String json) {
		return json != null && json.trim().startsWith("{");
	}

}
