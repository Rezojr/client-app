package com.myclientapp.bank;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class BankService {

    private final BankRepository bankRepository;

    public BankService(BankRepository bankRepository) {
        this.bankRepository = bankRepository;
    }

    public Page<Bank> findAll(Pageable pageable) {
        return bankRepository.findAll(pageable);
    }

    public Bank findById(Long id) {
        return bankRepository.findById(id)
                .orElseThrow(() -> new BankNotFoundException(id));
    }

    public Bank create(Bank bank) {
        return bankRepository.save(bank);
    }

    public Bank update(Bank newBank, Long id) {
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
