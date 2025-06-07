package com.sj.loans.mapper;

import com.sj.loans.dto.LoansDto;
import com.sj.loans.entity.Loans;

public class LoansMapper {
    public static LoansDto mapToLoansDto(Loans loans,LoansDto loansDto){
        loansDto.setLoanNumber(loans.getLoanNumber());
        loansDto.setLoanType(loans.getLoanType());
        loansDto.setMobileNumber(loans.getMobileNumber());
        loansDto.setTotalLoan(loans.getTotalLoan());
        loansDto.setOutstandingAmount(loans.getOutstandingAmount());
        return loansDto;

    }

    public static Loans mapToLoan(LoansDto loansDto,Loans loans){
        loans.setLoanNumber(loansDto.getLoanNumber());
        loans.setLoanType(loansDto.getLoanType());
        loans.setMobileNumber(loansDto.getMobileNumber());
        loans.setTotalLoan(loansDto.getTotalLoan());
        loans.setAmountPaid(loansDto.getAmountPaid());
       loans.setOutstandingAmount(loansDto.getOutstandingAmount());
        return loans;
    }
}
