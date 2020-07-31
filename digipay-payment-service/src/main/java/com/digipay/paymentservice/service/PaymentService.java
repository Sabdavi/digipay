package com.digipay.paymentservice.service;

import com.digipay.paymentservice.domain.Transaction;
import com.digipay.paymentservice.repository.TransactionRepository;
import com.digipay.paymentservice.web.PaymentRequest;
import com.digipay.paymentservice.web.ReportResponse;
import com.digipay.paymentservice.web.ReportResult;
import com.digipay.paymentservice.web.SmsRequest;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.eventuate.tram.messaging.producer.MessageBuilder;
import io.eventuate.tram.messaging.producer.MessageProducer;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
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
        ResponseEntity responseEntity = provider.pay(paymentRequest);

        int status = responseEntity.getStatusCodeValue();
        status = status == HttpStatus.OK.value() ? SUCCESSFUL : FAILED;
        Transaction transaction = Transaction.createTransaction(paymentRequest.getDest(), paymentRequest.getSource(),
                paymentRequest.getAmount(), null, null, status);
        transactionRepository.save(transaction);

        if (status == SUCCESSFUL) {
            SmsRequest smsRequest = new SmsRequest("Successful", "09124363760");
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

    public List<ReportResponse> getTransactions() {
        List<ReportResult> failedTransactions = transactionRepository.getFailedTransactions();
        List<ReportResult> successfulTransactions = transactionRepository.getSuccessfulTransactions();
        List<ReportResponse> response = new ArrayList<>();

        // TODO: 7/31/20 improver lambdas.it does not work!
        successfulTransactions.forEach(successTran -> {
            ReportResponse response1 = new ReportResponse();
            response1.setSuccessCount(successTran.getCount());
            Optional<ReportResult> failTran = failedTransactions.stream()
                    .filter(failedTransaction -> failedTransaction.getCardNumber().equals(successTran.getCardNumber()))
                    .findAny();
            response1.setFailCount(failTran.get().getCount());
            response.add(response1);
        });
        return response;
    }
}
