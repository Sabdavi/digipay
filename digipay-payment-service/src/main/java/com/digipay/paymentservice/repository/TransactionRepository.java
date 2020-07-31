package com.digipay.paymentservice.repository;


import com.digipay.paymentservice.domain.Transaction;
import com.digipay.paymentservice.web.ReportResult;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.Date;
import java.util.List;

public interface TransactionRepository extends CrudRepository<Transaction,Long> {


    @Query("select new com.digipay.paymentservice.web.ReportResult(t.source,count(t.status))from Transaction t where t.status=1 and  t.date between ?1 and ?2 group by t.source")
    List<ReportResult> getSuccessfulTransactions(Date from,Date to);
    @Query("select new com.digipay.paymentservice.web.ReportResult(t.source,count(t.status))from Transaction t where t.status = 0 and t.date between ?1 and ?2 group by t.source")
    List<ReportResult> getFailedTransactions(Date from,Date to);

}