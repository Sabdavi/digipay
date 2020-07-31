package com.digipay.paymentservice.repository;


import com.digipay.paymentservice.domain.Transaction;
import com.digipay.paymentservice.web.ReportResult;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface TransactionRepository extends CrudRepository<Transaction,Long> {

    @Query("select new com.digipay.paymentservice.web.ReportResult(t.source,count(t.status))from Transaction t where t.status = 1 group by t.source")
    List<ReportResult> getSuccessfulTransactions();
    @Query("select new com.digipay.paymentservice.web.ReportResult(t.source,count(t.status))from Transaction t where t.status = 0 group by t.source")
    List<ReportResult> getFailedTransactions();

}