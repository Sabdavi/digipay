package com.digipay.smsservice.service;


import com.digipay.smsservice.web.SmsRequest;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.eventuate.tram.messaging.common.Message;
import io.eventuate.tram.messaging.consumer.MessageConsumer;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;

@Service
public class SmsService {

    private final static String url = "http://localhost:8083/messages/send-sms";
    private String subscriberId = "sms_receiver";
    private String destination = "sms_channel";

    private MessageConsumer messageConsumer;
    private RestTemplate restTemplate;

    public SmsService(MessageConsumer messageConsumer,RestTemplate restTemplate) {
        this.messageConsumer = messageConsumer;
        this.restTemplate = restTemplate;
        messageConsumer.subscribe(subscriberId, Collections.singleton(destination), this::handleMessage);
    }
    private void handleMessage(Message message) {
        ObjectMapper mapper = new ObjectMapper();
        SmsRequest smsRequest = null;
        try {
            smsRequest = mapper.readValue(message.getPayload(),SmsRequest.class);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        ResponseEntity responseEntity
                = restTemplate.postForEntity(url,smsRequest,SmsRequest.class);
        int status = responseEntity.getStatusCodeValue();
        System.out.println(status);
    }
}
