
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
    public Page<BankDto> findAll(@PageableDefault Pageable pageable) {
        return bankMapper.mapAll(bankService.findAll(pageable));
    }

    @GetMapping("/banks/{id}")
    public BankDto findById(@PathVariable Long id) {
        return bankMapper.toDto(bankService.findById(id));
    }

    @PostMapping("/banks")
    public BankDto create(@RequestBody BankDto bankDto) {
        return bankMapper.toDto(bankService.create(bankMapper.toEntity(bankDto)));
    }

    @PutMapping("/banks/{id}")
    public BankDto update(@RequestBody BankDto bankDto, @PathVariable Long id) {
        return bankMapper.toDto(bankService.update(bankMapper.toEntity(bankDto), id));
    }

    @DeleteMapping("/banks/{id}")
    public void delete(@PathVariable Long id) {
        bankService.delete(id);
    }
}