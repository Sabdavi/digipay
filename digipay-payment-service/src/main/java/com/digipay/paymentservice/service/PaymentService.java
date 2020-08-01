package com.digipay.paymentservice.service;

import com.digipay.paymentservice.domain.Transaction;
import com.digipay.paymentservice.repository.TransactionRepository;
import com.digipay.paymentservice.web.*;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.eventuate.tram.messaging.producer.MessageBuilder;
import io.eventuate.tram.messaging.producer.MessageProducer;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class PaymentService {
    public static final int SUCCESSFUL = 1;
    public static final int FAILED = 0;
    private final String channel = "sms_channel";
    private TransactionRepository transactionRepository;
    private MessageProducer producer;

    public PaymentService(TransactionRepository transactionRepository,
                          MessageProducer producer) {
        this.transactionRepository = transactionRepository;
        this.producer = producer;
    }

    public ResponseEntity transfer(PaymentRequest paymentRequest) {
        ProviderFactory factory = new ProviderFactory(paymentRequest.getSource());
        Provider provider = factory.createProvider();
        ResponseEntity responseEntity  = null;
        Transaction transaction = null;
        try {
            responseEntity = provider.pay(paymentRequest);
        }
        catch (RestClientException ex){
            ex.printStackTrace();
            transaction = Transaction.createTransaction(paymentRequest.getDest(), paymentRequest.getSource(),
                    paymentRequest.getAmount(), new Date(), null, FAILED);
            transactionRepository.save(transaction);
            return new ResponseEntity(HttpStatus.REQUEST_TIMEOUT);
        }
        int status = responseEntity.getStatusCodeValue();
        status = status == HttpStatus.OK.value() ? SUCCESSFUL : FAILED;
        transaction = Transaction.createTransaction(paymentRequest.getDest(), paymentRequest.getSource(),
                paymentRequest.getAmount(), new Date(), null, status);
        transactionRepository.save(transaction);

        if (status == SUCCESSFUL) {
            // TODO: 7/31/20 integrate with oauth server
            SmsRequest smsRequest = new SmsRequest("Successful Payment", "09124363760");
            ObjectMapper mapper = new ObjectMapper();
            String message = null;
            try {
                message = mapper.writeValueAsString(smsRequest);
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
            producer.send(channel, MessageBuilder.withPayload(message).build());
        }
        return new ResponseEntity(HttpStatus.OK);
    }

    public List<ReportResponse> getTransactions(ReportData reportData) {


        List<ReportResult> failedTransactions = transactionRepository.getFailedTransactions(reportData.getFrom(), reportData.getTo());
        List<ReportResult> successfulTransactions = transactionRepository.getSuccessfulTransactions(reportData.getFrom(), reportData.getTo());
        List<ReportResponse> response = new ArrayList<>();

        // TODO: 8/1/20 merge to a single lambda
        for (ReportResult result : successfulTransactions) {
            ReportResponse response1 = new ReportResponse();
            response1.setSuccessCount(result.getCount());
            response1.setCardNumber(result.getCardNumber());
            Optional<ReportResult> failTran = failedTransactions.stream()
                    .filter(failedTransaction -> failedTransaction.getCardNumber().equals(result.getCardNumber()))
                    .findAny();
            response1.setFailCount(failTran.get().getCount());
            response.add(response1);
        }
        return response;
    }
}
