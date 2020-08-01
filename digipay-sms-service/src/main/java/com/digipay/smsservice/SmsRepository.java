package com.digipay.smsservice;

import com.digipay.smsservice.domain.Sms;
import org.springframework.data.repository.CrudRepository;

public interface SmsRepository extends CrudRepository<Sms,Long> {
}
