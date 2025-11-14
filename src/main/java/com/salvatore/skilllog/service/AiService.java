package com.salvatore.skilllog.service;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

@Service
public class AiService {

    private static final String OPENAI_URL = "https://api.openai.com/v1/chat/completions";
    @Value("${openai.api.key}")
    private String apiKey;
    

    public String generateMessage(String prompt) throws IOException {
        OkHttpClient client = new OkHttpClient();

        String body = "{"
        	    + "\"model\": \"gpt-4o-mini\","
        	    + "\"messages\": ["
        	    + "{\"role\": \"system\", \"content\": \"Rispondi sempre in italiano.\"},"
        	    + "{\"role\": \"user\", \"content\": \"" + prompt + "\"}"
        	    + "]"
        	    + "}";


        Request request = new Request.Builder()
            .url(OPENAI_URL)
            .header("Authorization", "Bearer " + apiKey)
            .header("Content-Type", "application/json")
            .post(RequestBody.create(body, MediaType.parse("application/json")))
            .build();

        Response response = client.newCall(request).execute();
        return response.body().string(); // qui puoi estrarre solo il testo
    }
}

