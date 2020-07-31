package com.digipay.cardservice.service;

import com.digipay.cardservice.domain.Card;
import com.digipay.cardservice.repository.CardRepository;
import com.digipay.cardservice.web.UpdateCardRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Transactional
@Service
public class CardService {

    Logger logger = LoggerFactory.getLogger(CardService.class);
    private CardRepository cardRepository;

    public CardService(CardRepository cardRepository) {
        this.cardRepository = cardRepository;
    }
    public Card createCard(String ownerName,String bankName,String cardNumber,String expirationDate){
        Card card = Card.createCard(ownerName, bankName, cardNumber, expirationDate);
        cardRepository.save(card);
        return card;
    }
    public Optional<Long> deleteCard(Long cardId){
        return cardRepository.findById(cardId).map(card -> {cardRepository.deleteById(card.getId());
                return card.getId();});
    }
    public List<Card> getCards(){
        return (List<Card>) cardRepository.findAll();
    }
    public Optional<Card> update(UpdateCardRequest request){
        return cardRepository.findById(request.getId()).map(updatedCard -> {updatedCard.setBankName(request.getBankName());
        updatedCard.setCardNumber(request.getCardNumber());
        updatedCard.setExpirationDate(request.getExpirationDate());
        updatedCard.setOwnerName(request.getOwnerName());
        return updatedCard;});
    }
    public Optional<Card> getCard(Long id){
        return cardRepository.findById(id);
    }
}
