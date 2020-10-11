package com.myclientapp.bank;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class BankController {

    private final BankService bankService;

    public BankController(BankService bankService) {
        this.bankService = bankService;
    }

    @GetMapping("/banks")
    public List<Bank> getAll() {
        return bankService.getAll();
    }

    @GetMapping("/banks/{id}")
    public Bank getBankById(@PathVariable Long id) {
        return bankService.findById(id);
    }

    @PostMapping("/banks")
    public Bank newBank(@RequestBody Bank bank) {
        return bankService.newBank(bank);
    }

    @PutMapping("/banks/{id}")
    public Bank updateBank(@RequestBody Bank bank, @PathVariable Long id) {
        return bankService.updateBank(bank, id);
    }

    @DeleteMapping("/banks/{id}")
    public void deleteBank(@PathVariable Long id) {
        bankService.deleteBank(id);
    }
}