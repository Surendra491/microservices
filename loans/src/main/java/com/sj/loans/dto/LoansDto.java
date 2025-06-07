package com.sj.loans.dto;


import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Data;

@Schema(
        name = "Loans", description = "Schema to hold Loan information"
)
@Data
public class LoansDto {

    @NotEmpty(message = "Mobile number can not be a empty or null")
    @Pattern(regexp = "(^$|[0-9]{10})",message = "Mobile Number must be 10 digits")
    @Schema(
            description = "Mobile number of customer",example = "3940586954"
    )
    private String mobileNumber;

    @NotEmpty(message = "Loan number can not be a null or empty")
    @Pattern(regexp = "(^$|[0-9]{12})", message = "Loan number must be 12 digits")
    @Schema(
            description = "Loan number of the customer", example = "548732457654"
    )
    private String loanNumber;

    @Schema(
            description = "Type of the loan", example = "Home Loan"
    )
    private String loanType;


    @PositiveOrZero(message = "Total loan amount should be greater than Zero")
    @Schema(
            description = "Total loan amount", example = "100000"
    )
    private int totalLoan;

    @PositiveOrZero(message = "Total loan amount paid should be equal or greater than zero")
    @Schema(
            description = "Total amount paid", example = "13000"
    )
    private int amountPaid;

    @PositiveOrZero(message = "Total outstanding amount should be equal or greater than zero")
    @Schema(
            description = "Total outstanding amount against a loan", example = "99000"
    )
    private int outstandingAmount;
}
