package com.digipay.cardservice.repository;

import com.digipay.cardservice.domain.Card;
import org.springframework.data.repository.CrudRepository;

public interface CardRepository extends CrudRepository<Card,Long> {
}
