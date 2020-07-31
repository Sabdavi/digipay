package com.digipay.cardservice.exception;

public class CardNotFoundException extends RuntimeException {
    public CardNotFoundException(Long cardId) {
        super("CardNotFound::"+cardId);
    }
}
