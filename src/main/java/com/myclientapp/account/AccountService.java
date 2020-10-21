package com.myclientapp.account;

import com.myclientapp.account.dto.AccountDto;
import com.myclientapp.client.ClientNotFoundException;
import com.myclientapp.client.ClientRepository;
import com.myclientapp.client.ClientService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class AccountService {

    private final AccountRepository accountRepository;
    private final ClientRepository clientRepository;
    private final ClientService clientService;

    public AccountService(AccountRepository accountRepository, ClientRepository clientRepository, ClientService clientService) {
        this.accountRepository = accountRepository;
        this.clientRepository = clientRepository;
        this.clientService = clientService;
    }

    public Account findAccountById(Long id) {
        return accountRepository.findById(id)
                .orElseThrow(() -> new AccountNotFoundException(id));
    }

    public Page<Account> getAllAccountsByClientId(Long clientId, Pageable pageable) {
        return accountRepository.findByClientId(clientId, pageable);
    }

    public Page<Account> findAllAccounts(Pageable pageable) {
        return accountRepository.findAll(pageable);
    }

    public Account createAccount(Long clientId, Account newAccount) {
        return clientRepository.findById(clientId).map(
                client -> {
                    newAccount.setClient(client);
                    System.out.println("XD");
                    return accountRepository.save(newAccount);
                }).orElseThrow(() -> new AccountNotFoundException(clientId));
    }

    public Account replaceAccount(Account newAccount, Long id) {
        return accountRepository.findById(id)
                .map(account -> {
                    account.setAccountNumber(newAccount.getAccountNumber());
                    account.setBalance(newAccount.getBalance());
                    return accountRepository.save(account);
                }).orElseGet(() -> {
                    newAccount.setId(id);
                    return accountRepository.save(newAccount);
                });
    }

    void deleteAccount(Long id) {
        accountRepository.deleteById(id);
    }

}