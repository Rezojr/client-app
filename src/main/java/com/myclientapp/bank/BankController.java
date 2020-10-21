
package com.myclientapp.bank;

import com.myclientapp.bank.dto.BankDto;
import com.myclientapp.bank.dto.BankMapper;
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
    public BankDto getBankById(@PathVariable Long id) {
        return bankMapper.toDto(bankService.findBankById(id));
    }

    @PostMapping("/banks")
    public BankDto newBank(@RequestBody Bank bank) {
        return bankMapper.toDto(bankService.createBank(bank));
    }

    @PutMapping("/banks/{id}")
    public BankDto updateBank(@RequestBody Bank bank, @PathVariable Long id) {
        return bankMapper.toDto(bankService.updateBank(bank, id));
    }

    @DeleteMapping("/banks/{id}")
    public void deleteBank(@PathVariable Long id) {
        bankService.deleteBank(id);
    }
}