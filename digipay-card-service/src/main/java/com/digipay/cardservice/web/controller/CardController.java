package com.digipay.cardservice.web.controller;

import com.digipay.cardservice.domain.Card;
import com.digipay.cardservice.service.CardService;
import com.digipay.cardservice.web.CreateCardRequest;
import com.digipay.cardservice.web.UpdateCardRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(path = "/cards")
public class CardController {
    private CardService cardService;

    public CardController(CardService cardService) {
        this.cardService = cardService;
    }

    @RequestMapping(method = RequestMethod.POST)
    public Card create(@RequestBody CreateCardRequest request){
        Card card = cardService.createCard(request.getOwnerName(), request.getBankName(),
                request.getCardNumber(), request.getExpirationDate());
        return card;
    }
    @RequestMapping(method = RequestMethod.GET)
    public List<Card> getCards(){
        return cardService.getCards();
    }
    @RequestMapping(path = "/{cardId}",method = RequestMethod.DELETE)
    public ResponseEntity<Long> delete(@PathVariable long cardId){
        Optional<Long> id = cardService.deleteCard(cardId);
        return id.map(deletedId -> new ResponseEntity(id, HttpStatus.OK)).orElse(new ResponseEntity(HttpStatus.NOT_FOUND));
    }
    @RequestMapping(method = RequestMethod.PUT)
    public ResponseEntity<Card> update(@RequestBody UpdateCardRequest updateCardRequest){
        return cardService.update(updateCardRequest).map(updatedCard -> new ResponseEntity(updatedCard,HttpStatus.OK)).orElse(new ResponseEntity(HttpStatus.NOT_FOUND));
    }
    @RequestMapping(path = "/{cardId}",method = RequestMethod.GET)
    public ResponseEntity<Card> getCard(@PathVariable Long cardId){
        return cardService.getCard(cardId).map(card -> new ResponseEntity<>(card,HttpStatus.OK)).orElse(new ResponseEntity(HttpStatus.NOT_FOUND));
    }
}
