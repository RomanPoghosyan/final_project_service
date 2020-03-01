package com.example.demo.services;

import com.example.demo.dto.responses.FbNotificationResponse;
import com.example.demo.models.Notification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.awt.*;
import java.util.HashMap;
import java.util.Map;

@Service
public class FirebaseMessagingService {
    private RestTemplate restTemplate;

    private static final String API_KEY = "AAAAoKWp3VM:APA91bFMjn87fPEUsUCtxJ4Loz0SsQ3GUb-1Al34ZoxaOP0x18ivCOhOTQhEhbg_YyYO790IOkzxwokjQuVs6AsbXuqD6guURCjEr_Qz8aAylndknxzMoyBnZWks7GR50IUEb0X5fT7L";

    private static final String URL = "https://fcm.googleapis.com/fcm/send";

    private static final MediaType APPLICATION_TYPE = MediaType.APPLICATION_JSON;

    private static final String CLICK_ACTION_URL = "http://localhost:3000/";

    @Autowired
    public FirebaseMessagingService(RestTemplateBuilder restTemplateBuilder) {
        this.restTemplate = restTemplateBuilder.build();
    }

    public void sendInvitationMessage(Notification notification) {
        HttpHeaders headers = new HttpHeaders();
        String notificationBody = notification.getNotifiedBy().getUsername() + " has invited you to the " + notification.getProject().getName() + "project!";
        headers.set("Authorization", "key=" + API_KEY);
        Map<String, Object> map = new HashMap<>();
        map.put("to", notification.getNotifiedTo().getFb_token());
        map.put("notification", new FbNotificationResponse(
                "Invitation to the project!", CLICK_ACTION_URL, notificationBody
        ));
        HttpEntity<Map<String, Object>> entity = new HttpEntity<>(map, headers);
        this.restTemplate.postForEntity(URL, entity, Object.class);
    }

    public RestTemplate getRestTemplate() {
        return restTemplate;
    }

    public void setRestTemplate(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public static String getApiKey() {
        return API_KEY;
    }

    public static String getURL() {
        return URL;
    }

    public static MediaType getApplicationType() {
        return APPLICATION_TYPE;
    }

    public static String getClickActionUrl() {
        return CLICK_ACTION_URL;
    }
}