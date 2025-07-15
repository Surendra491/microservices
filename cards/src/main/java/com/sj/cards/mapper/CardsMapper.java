package com.sj.cards.mapper;

import com.sj.cards.dto.CardsDto;
import com.sj.cards.entity.Cards;


public class CardsMapper {

    public static CardsDto mapToCardDto(Cards cards, CardsDto cardDto){

        cardDto.setCardNumber(cards.getCardNumber());
        cardDto.setCardType(cards.getCardType());
        cardDto.setMobileNumber(cards.getMobileNumber());
        cardDto.setTotalLimit(cards.getTotalLimit());
        cardDto.setAvailableAmount(cards.getAvailableAmount());
        cardDto.setUsedAmount(cards.getAmountUsed());
        return cardDto;
    }

    public static Cards mapToCards(CardsDto cardDto,Cards cards){

        cards.setCardNumber(cardDto.getCardNumber());
        cards.setCardType(cardDto.getCardType());
        cards.setMobileNumber(cardDto.getMobileNumber());
        cards.setTotalLimit(cardDto.getTotalLimit());
        cards.setAvailableAmount(cardDto.getAvailableAmount());
        cards.setAmountUsed(cardDto.getUsedAmount());
        return cards;
    }

}
