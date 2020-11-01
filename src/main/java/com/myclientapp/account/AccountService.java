package com.myclientapp.account;

import com.myclientapp.client.ClientRepository;
import com.myclientapp.client.ClientService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AccountService {

    private final AccountRepository accountRepository;
    private final ClientRepository clientRepository;

    public Page<Account> findAllAccounts(Pageable pageable) {
        return accountRepository.findAll(pageable);
    }

    public Page<Account> getAllAccountsByClientId(Long clientId, Pageable pageable) {
        return accountRepository.findByClientId(clientId, pageable);
    }

    public Account findAccountById(Long id) {
        return accountRepository.findById(id)
                .orElseThrow(() -> new AccountNotFoundException(id));
    }

    public Account createAccount(Long clientId, Account newAccount) {
        return clientRepository.findById(clientId).map(
                client -> {
                    newAccount.setClient(client);
                    return accountRepository.save(newAccount);
                }).orElseThrow(() -> new AccountNotFoundException(clientId));
    }

    public Account updateAccount(Account newAccount, Long id) {
        return accountRepository.findById(id)
                .map(account -> {
                    account.setAccountNumber(newAccount.getAccountNumber());
                    account.setBalance(newAccount.getBalance());
                    return accountRepository.save(account);
                }).orElseThrow(() -> new AccountNotFoundException(id));
    }

    public void delete(Long id) {
        accountRepository.deleteById(id);
    }

}