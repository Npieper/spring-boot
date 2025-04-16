package com.example.spring_boot.service;

import com.example.spring_boot.entity.BankAccount;
import com.example.spring_boot.repository.BankAccountRepository;
import org.iban4j.CountryCode;
import org.iban4j.Iban;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class BankService {

    @Autowired
    private BankAccountRepository bankAccountRepository;

    public BankAccount createBankAccount(String firstName, String lastName) {

        Iban iban = new Iban.Builder()
                .leftPadding(true)
                .countryCode(CountryCode.DE)
                .bankCode("66280099")
                .accountNumber("123456700")
                .build();

        BankAccount bankAccount = BankAccount.builder()
                .balance(0.0)
                .firstName(firstName)
                .lastName(lastName)
                .iban(iban.toString())
                .build();
        return bankAccountRepository.save(bankAccount);
    }

    public void topUpBalance(Integer id, double amount) {

        Optional<BankAccount> bankAccount = bankAccountRepository.findById(id);
        bankAccount.ifPresentOrElse(bankAccount1 -> {
            bankAccount1.setBalance(bankAccount1.getBalance() + amount);
            bankAccountRepository.save(bankAccount1);
            }
        , () -> System.out.println("FEHLER, KEIN BANK ACCOUNT"));

        System.out.println("NEUE BALANCE " + bankAccount.get().getBalance());
    }
}
