package com.myclientapp.bank;

import com.myclientapp.account.AccountNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BankService {

    private final BankRepository bankRepository;

    public BankService(BankRepository bankRepository) {
        this.bankRepository = bankRepository;
    }

    public Page<Bank> findAllBanks(Pageable pageable) {
        return bankRepository.findAll(pageable);
    }

    public Bank findBankById(Long id) {
        return bankRepository.findById(id)
                .orElseThrow(() -> new BankNotFoundException(id));
    }

    public Bank createBank(Bank bank) {
        System.out.println(bank.getBankBalance());
        return bankRepository.save(bank);
    }

    public Bank updateBank(Bank newBank, Long id) {
        return bankRepository.findById(id)
                .map(bank -> {
                    bank.setBankName(newBank.getBankName());
                    bank.setBankBalance(newBank.getBankBalance());
                    return bankRepository.save(bank);
                }).orElseThrow(() -> new BankNotFoundException(id));
    }

    public void delete(Long id) {
        bankRepository.deleteById(id);
    }
}
