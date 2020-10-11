package com.myclientapp.account;

import com.myclientapp.client.Client;
import com.myclientapp.client.ClientNotFoundException;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;

import javax.security.auth.login.AccountNotFoundException;
import javax.swing.text.html.parser.Entity;
import java.util.List;

@Service
public class AccountService {

    private final AccountRepository accountRepository;

    public AccountService(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    public Account getOne(Long id) {
        return accountRepository.findById(id)
                .orElseThrow(() -> new ClientNotFoundException(id));
    }

    public List<Account> getAll() {
        return accountRepository.findAll();
    }

    public Account newAccount(Account newAccount) {
        return accountRepository.save(newAccount);
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

    Account replaceOwner(Account newAccount, Long id) {
        return accountRepository.findById(id)
                .map(account -> {
                    account.setClient(newAccount.getClient());
                    return accountRepository.save(account);
                }).orElseGet(() -> {
                    newAccount.setId(id);
                    return accountRepository.save(newAccount);
                });
    }


}