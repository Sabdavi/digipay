package com.digipay.smsservice.service;


import com.digipay.smsservice.SmsRepository;
import com.digipay.smsservice.domain.Sms;
import com.digipay.smsservice.web.SmsRequest;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.eventuate.tram.messaging.common.Message;
import io.eventuate.tram.messaging.consumer.MessageConsumer;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import javax.transaction.Transactional;
import java.util.Collections;

@Service
@Transactional
public class SmsService {

    public static final int SUCCESSFUL = 1;
    public static final int FAILED = 0;

    private final static String url = "http://localhost:8083/messages/send-sms";
    private String subscriberId = "sms_receiver";
    private String destination = "sms_channel";

    private MessageConsumer messageConsumer;
    private RestTemplate restTemplate;
    private SmsRepository smsRepository;

    public SmsService(MessageConsumer messageConsumer,RestTemplate restTemplate, SmsRepository smsRepository) {
        this.messageConsumer = messageConsumer;
        this.restTemplate = restTemplate;
        this.smsRepository = smsRepository;

        messageConsumer.subscribe(subscriberId, Collections.singleton(destination), this::handleMessage);
    }
    private void handleMessage(Message message) {
        ObjectMapper mapper = new ObjectMapper();
        SmsRequest smsRequest = null;
        ResponseEntity responseEntity = null;
        Sms sms = null;
        try {
            smsRequest = mapper.readValue(message.getPayload(),SmsRequest.class);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return;
        }
        try{
            responseEntity
                    = restTemplate.postForEntity(url,smsRequest,SmsRequest.class);
        }
        catch (RestClientException ex){
            ex.printStackTrace();
            sms = new Sms(smsRequest.getMsd(),smsRequest.getTarget(), (byte) FAILED);
            smsRepository.save(sms);
            return;
        }
        int status = responseEntity.getStatusCodeValue();
        status = status== HttpStatus.OK.value() ? SUCCESSFUL :FAILED;
        sms = new Sms(smsRequest.getMsd(),smsRequest.getTarget(), (byte) status);
        smsRepository.save(sms);
    }
}
