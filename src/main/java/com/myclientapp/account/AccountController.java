package com.myclientapp.account;

import com.myclientapp.account.dto.AccountDto;
import com.myclientapp.account.dto.AccountMapper;
import com.myclientapp.client.Client;
import com.myclientapp.client.ClientNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class AccountController {

    private final AccountService accountService;
    private final AccountMapper accountMapper;

    @GetMapping("/accounts")
    public Page<AccountDto> findAllAccounts(@PageableDefault Pageable pageable) {
        return accountMapper.mapAll(accountService.findAllAccounts(pageable));
    }

    @GetMapping("/clients/{id}/accounts")
    public Page<Account> findAllAccountsByClientId(@PathVariable Long id,
                                                   @PageableDefault Pageable pageable) {
        return accountService.getAllAccountsByClientId(id, pageable);
    }

    @GetMapping("/accounts/{id}")
    public AccountDto findAccountById(@PathVariable Long id) {
        return accountMapper.toDto(accountService.findAccountById(id));
    }

    @PostMapping("/clients/{clientId}/accounts")
    AccountDto createAccount(@PathVariable Long clientId,
                          @Valid @RequestBody Account newAccount) {


        return accountMapper.toDto(accountService.createAccount(clientId, newAccount));
    }

    @PutMapping("/accounts/{id}")
    Account replaceAccount(@RequestBody Account newAccount, @PathVariable Long id) {
        return accountService.replaceAccount(newAccount, id);
    }


    @DeleteMapping("/accounts/{id}")
    void deleteAccount(@PathVariable Long id) {
        accountService.deleteAccount(id);
    }
}
