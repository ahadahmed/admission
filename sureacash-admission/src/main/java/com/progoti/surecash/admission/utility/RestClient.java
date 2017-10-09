package com.progoti.surecash.admission.utility;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.progoti.surecash.dto.SwitchPaymentRequestDto;
import org.apache.http.HttpHeaders;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.lang.reflect.Type;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Map;

/**
 * Created by Shaown on 3:43 PM.
 */
public class RestClient {

	public static final String SWITCH_PAYMENT_API = "http://192.168.1.55:8080/proSw/rest/apiv2/switch/do/payment";
	private static Gson gson = new Gson();

	public static URL generateRequestURI(String URL) throws Exception {
		return new URIBuilder(URL).build().toURL();
	}

	public static Map<String, String> parseResponseText(HttpResponse response) throws IOException {
		String responseString = EntityUtils.toString(response.getEntity(), "UTF-8");
		System.out.println(responseString);
		EntityUtils.consume(response.getEntity());
		System.out.println(responseString.replaceAll("\n", ""));
		Type type = new TypeToken<Map<String, String>>(){}.getType();

        return gson.fromJson(responseString.replaceAll("\n", ""), type);
	}

	/*public static HttpResponse doGetRequest(URL url) throws IOException, URISyntaxException {
		HttpClient client = HttpClientBuilder.create().build();
		HttpGet request = new HttpGet(url.toURI());

		return client.execute(request);
	}*/

	public static HttpResponse doSwitchPaymentRequest(SwitchPaymentRequestDto request)
			throws URISyntaxException, IOException {
		HttpClient client = HttpClientBuilder.create().build();
		HttpPost post = new HttpPost(new URIBuilder(SWITCH_PAYMENT_API).build().toURL().toURI());

		// add header
		post.setHeader(HttpHeaders.CONTENT_TYPE, "application/json");
		post.setHeader(HttpHeaders.ACCEPT, "application/json");

        String requestString = gson.toJson(request);
        System.out.println("switch payment request:->" + requestString);
        post.setEntity(new StringEntity(requestString));

		return client.execute(post);
	}
}
