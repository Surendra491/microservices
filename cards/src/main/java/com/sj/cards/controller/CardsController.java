package com.sj.cards.controller;

import com.sj.cards.constants.CardsConstants;
import com.sj.cards.dto.CardsDto;
import com.sj.cards.dto.ErrorResponseDto;
import com.sj.cards.dto.ResponseDto;
import com.sj.cards.service.ICardsService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Tag(
        name = "Card REST APIs for card"
        ,description = "CRUD REST APIs to CREATE, UPDATE, FETCH AND DELETE card details"
)
@RestController
@RequestMapping(path = "/api", produces = {MediaType.APPLICATION_JSON_VALUE})
@AllArgsConstructor
@Validated
public class CardsController {

    private ICardsService iCardsService;

    @PostMapping("/create")
    public ResponseEntity<ResponseDto> createCard(@Valid
                                                  @RequestParam
                                                  @Pattern(regexp = "^$|[0-9]{10}"
                                                          ,message = "Mobile number most be 10 numbers") String mobileNumber){
        iCardsService.createCard(mobileNumber);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new ResponseDto(CardsConstants.STATUS_2001,CardsConstants.MESSAGE_2001));
    }
    @Operation(
            summary = "Fetch card details REST API"
            ,description = "REST API to fetch card details based on a mobile number"
    )
    @ApiResponses(
            {@ApiResponse(
                    responseCode = "200"
                    ,description = "HTTP Status OK"
            ),
                    @ApiResponse(
                            responseCode = "500"
                            ,description = "HTTP Status Internal Server Error"
                            ,content = @Content(
                                    schema = @Schema(implementation = ErrorResponseDto.class)
                    )
                    )
            }
    )
    @GetMapping("/fetch")
    public ResponseEntity<CardsDto> fetchCardDetails(@RequestParam @Pattern(regexp = "^$|[0-9]{10}",message = "Mobile number most be 10 digits")
                                                     String mobileNumber){
        CardsDto cardsDto = iCardsService.fetchCard(mobileNumber);
        return ResponseEntity.status(HttpStatus.OK).body(cardsDto);
    }


    @Operation(
            summary = "Updated Card details REST API"
            ,description = "REST API to update card details based on a card number"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200"
                    ,description = "HTTP Status OK"
            ),
            @ApiResponse(
                    responseCode = "417"
                    ,description = "Expectation Failed"
            ),
            @ApiResponse(
                    responseCode = "500"
                    ,description = "HTTPS Status Internal Server Error"
                    ,content = @Content(
                    schema = @Schema(implementation = ErrorResponseDto.class)
            )
            )
    })
    @PutMapping("/update")
    public ResponseEntity<ResponseDto> updatedCardDetails(@Valid @RequestBody CardsDto cardsDto){

        boolean isUpdated = iCardsService.updateCard(cardsDto);

        if (isUpdated){
            return ResponseEntity.status(HttpStatus.OK)
                    .body(new ResponseDto(CardsConstants.STATUS_200, CardsConstants.MESSAGE_200));
        }else {
            return ResponseEntity
                    .status(HttpStatus.EXPECTATION_FAILED)
                    .body(new ResponseDto(CardsConstants.STATUS_417, CardsConstants.MESSAGE_417_UPDATE));
        }
    }

    @Operation(
            summary = "Delete Card details REST API"
            ,description = "REST API to Delete card details based on a mobile number"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200"
                    ,description = "HTTP Status OK"
            ),
            @ApiResponse(
                    responseCode = "417"
                    ,description = "Expectation Failed"
            ),
            @ApiResponse(
                    responseCode = "500"
                    ,description = "HTTP Status Internal Server Error"
                    ,content = @Content(
                    schema = @Schema(implementation = ErrorResponseDto.class)
            )
            )
    })
    @DeleteMapping("/delete")
    public ResponseEntity<ResponseDto> deleteCarsDetails(@RequestParam
                                                             @Pattern(regexp = "^$|[0-9]{10}",message = "Mobile number most be 10 digits")
                                                             String mobileNumber){
        boolean isDeleted = iCardsService.deleteCard(mobileNumber);

        if(isDeleted){
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(new ResponseDto(CardsConstants.STATUS_200, CardsConstants.MESSAGE_200));
        }else {
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED)
                    .body(new ResponseDto(CardsConstants.STATUS_417,CardsConstants.MESSAGE_417_DELETE));
        }

    }}
