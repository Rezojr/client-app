package com.myclientapp.account;

import com.myclientapp.client.Client;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class AccountController {

    private final AccountService accountService;

    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    @GetMapping("/accounts")
    public List<Account> getAll() {
        return accountService.getAll();
    }

    @GetMapping("/accounts/{id}")
    public Account getOne(@PathVariable Long id) {
        return accountService.getOne(id);
    }

    @PostMapping("/accounts")
    Account newAccount(@RequestBody Account newAccount) {
        return accountService.newAccount(newAccount);
    }

    @PutMapping("/accounts/{id}")
    Account replaceAccount(@RequestBody Account newAccount, @PathVariable Long id) {
        return accountService.replaceAccount(newAccount, id);
    }

    @PutMapping("/accounts/owner/{id}")
    Account newOwner(@RequestBody Account newAccount, @PathVariable Long id) {
        return accountService.replaceOwner(newAccount, id);
    }

    @DeleteMapping("/accounts/{id}")
    void deleteAccount(@PathVariable Long id) {
        accountService.deleteAccount(id);
    }
}
