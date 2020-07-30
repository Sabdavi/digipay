package com.digipay.cardservice.repository;

import com.digipay.cardservice.domain.Card;
import com.digipay.cardservice.domain.Transaction;
import com.digipay.cardservice.web.ReportResponse;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import java.util.List;

public interface CardRepository extends CrudRepository<Card,Long> {


    @Query("select new com.digipay.cardservice.web.ReportResponse(c.cardNumber,sum(t.success),sum(t.fail))" + "from  Transaction t join Card c on t.source=c.id group by c.cardNumber")
    List<ReportResponse> findTransactions();




}
