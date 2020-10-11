package com.myclientapp.bank;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BankService {

    private final BankRepository bankRepository;

    public BankService(BankRepository bankRepository) {
        this.bankRepository = bankRepository;
    }

    public List<Bank> getAll() {
        return bankRepository.findAll();
    }

    public Bank findById(Long id) {
        return bankRepository.findById(id)
                .orElseThrow(() -> new BankNotFoundException(id));
    }

    public Bank newBank(Bank bank) {
        return bankRepository.save(bank);
    }

    public Bank updateBank(Bank newBank, Long id) {
        return bankRepository.findById(id)
                .map(bank -> {
                    bank.setBankName(newBank.getBankName());
                    bank.setBankBalance(newBank.getBankBalance());
                    return bankRepository.save(bank);
                }).orElseGet(() -> {
                    newBank.setId(id);
                    return bankRepository.save(newBank);
                });
    }

    public void deleteBank(Long id) {
        bankRepository.deleteById(id);
    }
}
