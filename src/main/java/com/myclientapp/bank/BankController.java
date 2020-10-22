
package com.myclientapp.bank;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class BankController {

    private final BankService bankService;
    private final BankMapper bankMapper;

    @GetMapping("/banks")
    public Page<BankDto> findAllBanks(@PageableDefault Pageable pageable) {
        return bankMapper.mapAll(bankService.findAllBanks(pageable));
    }

    @GetMapping("/banks/{id}")
    public BankDto findBankById(@PathVariable Long id) {
        return bankMapper.toDto(bankService.findBankById(id));
    }

    @PostMapping("/banks")
    public BankDto createBank(@RequestBody Bank bank) {
        return bankMapper.toDto(bankService.createBank(bank));
    }

    @PutMapping("/banks/{id}")
    public BankDto updateBank(@RequestBody Bank bank, @PathVariable Long id) {
        return bankMapper.toDto(bankService.updateBank(bank, id));
    }

    @DeleteMapping("/banks/{id}")
    public void delete(@PathVariable Long id) {
        bankService.delete(id);
    }
}