package com.sj.loans.service;

import com.sj.loans.dto.LoansDto;
import org.springframework.stereotype.Service;


@Service
public interface ILoansService {

    void createLoan(String mobileNumber);

    LoansDto fetchLoan(String mobileNumber);

    boolean updateLoan(LoansDto loansDto);

    boolean deleteLoan(String mobileNumber);
}
