package com.myclientapp.account;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

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
    public Page<AccountDto> findAllAccountsByClientId(@PathVariable Long id,
                                                   @PageableDefault Pageable pageable) {
        return accountMapper.mapAll(accountService.getAllAccountsByClientId(id, pageable));
    }

    @GetMapping("/accounts/{id}")
    public AccountDto findAccountById(@PathVariable Long id) {
        return accountMapper.toDto(accountService.findAccountById(id));
    }

    @PostMapping("/clients/{clientId}/accounts")
    public AccountDto createAccount(@PathVariable Long clientId,
                          @Valid @RequestBody Account newAccount) {
        return accountMapper.toDto(accountService.createAccount(clientId, newAccount));
    }

    @PutMapping("/accounts/{id}")
    public AccountDto updateAccount(@RequestBody Account newAccount, @PathVariable Long id) {
        return accountMapper.toDto(accountService.updateAccount(newAccount, id));
    }

    @DeleteMapping("/accounts/{id}")
    public void delete(@PathVariable Long id) {
        accountService.delete(id);
    }
}
