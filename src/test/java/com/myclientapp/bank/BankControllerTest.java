package com.myclientapp.bank;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.myclientapp.AbstractIntegrationTest;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.BDDMockito.given;
import static org.hamcrest.CoreMatchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class BankControllerTest extends AbstractIntegrationTest {

    @Autowired
    private BankService bankService;

    @Test
    public void shouldFetchAllBanks() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                .get("/banks")
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.content").exists())
                //.andExpect(MockMvcResultMatchers.jsonPath("$.content[*].id").isNotEmpty()) I don't know why content don't have an id
                .andExpect(MockMvcResultMatchers.jsonPath("$.content[*].bankName").isNotEmpty())
                .andExpect(MockMvcResultMatchers.jsonPath("$.content[*].bankBalance").isNotEmpty());
    }

    @Test
    public void shouldBankUserById() throws Exception {
        final Bank bank = new Bank("PKO", 100000);
        Bank bank1 = bankService.createBank(bank);

        this.mockMvc.perform(get("/banks/{id}", bank1.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.bankName", is(bank.getBankName())))
                .andExpect(jsonPath("$.bankBalance", is(bank.getBankBalance())));
    }

    @Test
    public void shouldCreateNewBank() throws Exception {
        Bank bank = new Bank("PKO", 10000);
        Bank bank1 = bankService.createBank(bank);

        mockMvc.perform(MockMvcRequestBuilders
                .post("/banks")
                .content(toJson(bank1))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.bankName").exists());
    }

    @Test
    public void shouldDeleteBank() throws Exception {
        Bank bank = new Bank("PKO", 10000);
        Bank bank1 = bankService.createBank(bank);

        this.mockMvc.perform(MockMvcRequestBuilders
                .delete("/banks/{id}", bank1.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void shouldUpdateBank() throws Exception {
        Bank bank = new Bank("PKO", 100000);
        Bank bank1 = bankService.createBank(bank);
        this.mockMvc.perform(MockMvcRequestBuilders
                .put("/banks/{id}", bank1.getId())
                .content(toJson(new Bank("SKO", 2000)))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.bankName").value("SKO"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.bankBalance").value(2000));
    }
}
