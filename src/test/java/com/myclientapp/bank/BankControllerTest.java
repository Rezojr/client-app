package com.myclientapp.bank;

import com.myclientapp.AbstractIntegrationTest;
import com.myclientapp.bank.Bank;
import com.myclientapp.bank.BankDto;
import com.myclientapp.bank.BankMapper;
import com.myclientapp.bank.BankService;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.hamcrest.CoreMatchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class BankControllerTest extends AbstractIntegrationTest {

    @Autowired
    private BankService bankService;

    @Autowired
    private BankMapper bankMapper;

    @Test
    public void shouldFetchAllBanks() throws Exception {
        final Bank bank = new Bank("PKO", 100000);
        Bank simulateExistingBank = bankService.create(bank);

        mockMvc.perform(MockMvcRequestBuilders
                .get("/banks")
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.content").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.content[*].bankName").isNotEmpty())
                .andExpect(MockMvcResultMatchers.jsonPath("$.content[*].bankBalance").isNotEmpty());
    }

    @Test
    public void shouldFetchBankUserById() throws Exception {
        final Bank bank = new Bank("PKO", 100000);
        Bank newBank = bankService.create(bank);

        this.mockMvc.perform(get("/banks/{id}", newBank.getId())
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.bankName", is(bank.getBankName())))
                .andExpect(jsonPath("$.bankBalance", is(bank.getBankBalance())));
    }

    @Test
    public void shouldCreateNewBank() throws Exception {
        final Bank bank = new Bank("PKO", 100000);
        BankDto simulateExistingBank = bankMapper.toDto(bankService.create(bank));

        mockMvc.perform(MockMvcRequestBuilders
                .post("/banks")
                .content(toJson(bankMapper.toDto(bankMapper.toEntity(simulateExistingBank))))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.bankName").exists());
    }

    @Test
    public void shouldDeleteBank() throws Exception {
        final Bank bank = new Bank("PKO", 100000);
        Bank simulateExistingBank = bankService.create(bank);

        this.mockMvc.perform(MockMvcRequestBuilders
                .delete("/banks/{id}", simulateExistingBank.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void shouldUpdateBank() throws Exception {
        final Bank bank = new Bank("PKO", 100000);
        BankDto simulateExistingBank = bankMapper.toDto(bankService.create(bank));

        this.mockMvc.perform(MockMvcRequestBuilders
                .put("/banks/{id}", simulateExistingBank.getId())
                .content(toJson(new BankDto("SKO", 2000)))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.bankBalance").value(2000));
    }
}
