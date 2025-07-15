package com.sj.cards.service.Impl;

import com.sj.cards.constants.CardsConstants;
import com.sj.cards.dto.CardsDto;
import com.sj.cards.entity.Cards;
import com.sj.cards.exception.CardAlreadyExistsException;
import com.sj.cards.exception.ResourceNotFoundException;
import com.sj.cards.mapper.CardsMapper;
import com.sj.cards.repository.CardsRepository;
import com.sj.cards.service.ICardsService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Random;

@Service
@AllArgsConstructor
public class CardsServiceImpl implements ICardsService {

    private CardsRepository cardsRepository;

    /**
     * @param mobileNumber - Mobile Number of the Customer
     */
    @Override
    public void createCard(String mobileNumber) {
        Optional<Cards> optionalCard = cardsRepository.findByMobileNumber(mobileNumber);

        if (optionalCard.isPresent()){
            throw new CardAlreadyExistsException("Given mobile number already register with card "+ mobileNumber);
        }
        cardsRepository.save(createNewCard(mobileNumber));

    }

    /**
     * @param mobileNumber - Mobile Number of the Customer
     * @return the new card details
     */
    public Cards createNewCard(String mobileNumber){
        Cards newCards = new Cards();
        long randomCardNumber = 10000000000L + new Random().nextInt(900000000);
        newCards.setCardNumber(Long.toString(randomCardNumber));
        newCards.setMobileNumber(mobileNumber);
        newCards.setCardType(CardsConstants.CREDIT_CARD);
        newCards.setTotalLimit(CardsConstants.NEW_CARD_LIMIT);
        newCards.setAmountUsed(0);
        newCards.setAvailableAmount(CardsConstants.NEW_CARD_LIMIT);
        return newCards;
    }

    /**
     *
     * @param mobileNumber - Input mobile Number
     * @return Card Details based on a given mobileNumber
     */
    @Override
    public CardsDto fetchCard(String mobileNumber) {
            Cards card = cardsRepository.findByMobileNumber(mobileNumber)
                .orElseThrow(() -> new ResourceNotFoundException("Card","Mobile number", mobileNumber));
        return CardsMapper.mapToCardDto(card, new CardsDto());
    }

    /**
     *
     * @param cardsDto - CardsDto Object
     * @return boolean indicating if the update of card details is successful or not
     */
    @Override
    public boolean updateCard(CardsDto cardsDto) {

        Cards cards = cardsRepository.findByMobileNumber(cardsDto.getMobileNumber())
                .orElseThrow(()-> new ResourceNotFoundException("Card","CardNumber",cardsDto.getCardNumber()));
        CardsMapper.mapToCards(cardsDto,cards);
        cardsRepository.save(cards);
        return true;
    }

    /**
     * @param mobileNumber - Input MobileNumber
     * @return boolean indicating if the delete of card details is successful or not
     */
    @Override
    public boolean deleteCard(String mobileNumber) {
        Cards card = cardsRepository.findByMobileNumber(mobileNumber).orElseThrow(() -> new ResourceNotFoundException("Card", "Mobile Number",mobileNumber));
        cardsRepository.deleteById(card.getCardId());
        return true;
    }
}
