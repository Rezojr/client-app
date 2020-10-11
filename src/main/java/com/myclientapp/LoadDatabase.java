package com.myclientapp;

import com.myclientapp.account.Account;
import com.myclientapp.account.AccountRepository;
import com.myclientapp.bank.Bank;
import com.myclientapp.bank.BankRepository;
import com.myclientapp.client.Client;
import com.myclientapp.client.ClientRepository;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Configuration
public class LoadDatabase {

    private final ClientRepository clientRepository;
    private final AccountRepository accountRepository;

    private static final Logger log = LoggerFactory.getLogger(LoadDatabase.class);

    public LoadDatabase(ClientRepository clientRepository, AccountRepository accountRepository) {
        this.clientRepository = clientRepository;
        this.accountRepository = accountRepository;
    }

    @Bean
    CommandLineRunner initDatabaseBank(BankRepository bankRepository) {
        return args -> {
            bankRepository.save(new Bank(5L, "PKO S.A.", 1000000));
        };
    }



}
