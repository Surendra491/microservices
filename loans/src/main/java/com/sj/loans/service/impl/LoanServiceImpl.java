package com.sj.loans.service.impl;

import com.sj.loans.constants.LoansConstants;
import com.sj.loans.dto.LoansDto;
import com.sj.loans.entity.Loans;
import com.sj.loans.exception.LoanAlreadyExistsException;
import com.sj.loans.exception.ResourceNotFoundException;
import com.sj.loans.mapper.LoansMapper;
import com.sj.loans.repository.LoanRepository;
import com.sj.loans.service.ILoansService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Random;

@Service
@AllArgsConstructor
public class LoanServiceImpl implements ILoansService {

    private LoanRepository loanRepository;
    @Override
    public void createLoan(String mobileNumber) {
        Optional<Loans> optionalLoans = loanRepository.findByMobileNumber(mobileNumber);

        if(optionalLoans.isPresent()){
            throw new LoanAlreadyExistsException("Loan already register tithe given mobile number "+mobileNumber);
        }
        Loans loans = createNewLoan(mobileNumber);
        loanRepository.save(loans);

    }

    private Loans createNewLoan(String mobileNumber){
        Loans newLoan = new Loans();
        long randomLoanNumber = 100000000000L + new Random().nextInt(900000000);
        newLoan.setLoanNumber(Long.toString(randomLoanNumber));
        newLoan.setMobileNumber(mobileNumber);
        newLoan.setLoanType(LoansConstants.HOME_LOAN);
        newLoan.setTotalLoan(LoansConstants.NEW_LOAN_LIMIT);
        newLoan.setAmountPaid(0);
        newLoan.setOutstandingAmount(LoansConstants.NEW_LOAN_LIMIT);
        //OUTSTANDING_AMOUNT
        
        return newLoan;
    }

    @Override
    public LoansDto fetchLoan(String mobileNumber) {
        Loans loans = loanRepository.findByMobileNumber(mobileNumber).orElseThrow(
                () -> new ResourceNotFoundException("Loan","MobileNumber", mobileNumber)
        );
        return LoansMapper.mapToLoansDto(loans,new LoansDto());
    }

    @Override
    public boolean updateLoan(LoansDto loansDto) {
            Loans loans =loanRepository.findByMobileNumber(loansDto.getMobileNumber()).orElseThrow(
                    () -> new ResourceNotFoundException("Loan", "LoanNumber", loansDto.getLoanNumber()));
        LoansMapper.mapToLoan(loansDto, loans);
        loanRepository.save(loans);
        return  true;
    }

    @Override
    public boolean deleteLoan(String mobileNumber) {
        Loans loans =loanRepository.findByMobileNumber(mobileNumber).orElseThrow(
                () -> new ResourceNotFoundException("Loan", "mobile number", mobileNumber)
        );
        loanRepository.deleteById(loans.getLoanId());
        return true;
    }
}
