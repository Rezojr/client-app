package com.myclientapp.bank;

import com.fasterxml.jackson.databind.ObjectMapper;
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

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.BDDMockito.given;
import static org.hamcrest.CoreMatchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@WebMvcTest(controllers = BankController.class)
public class BankControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private BankService bankService;

    @Autowired
    private ObjectMapper objectMapper;

    private List<Bank> banks;

    @Before
    public void setUp() {
        this.banks = new ArrayList<>();
        this.banks.add(new Bank(1L, "PKO", 10000));
        this.banks.add(new Bank(2L, "RBD", 12500));
        this.banks.add(new Bank(3L, "XXD", 25000));
        System.out.println(banks);
    }

    @Test
    public void shouldFetchAllUsers() throws Exception {
        given(bankService.getAll()).willReturn(banks);

        System.out.println(this.mockMvc.perform(get("/banks")).andReturn().getResponse().getContentAsString());

        this.mockMvc.perform(get("/banks"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()", is(banks.size())));
    }

    @Test
    public void shouldFetchUserById() throws Exception {
        final Long bankId = 1L;
        final Bank bank = new Bank(1L, "PKO", 10000);

        given(bankService.findById(bankId)).willReturn(bank);

        this.mockMvc.perform(get("/banks/{id}", bankId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.bankName", is(bank.getBankName())))
                .andExpect(jsonPath("$.bankBalance", is(bank.getBankBalance())));
    }

    @Test
    public void shouldCreateNewBank() throws Exception {
        given(bankService.newBank(any(Bank.class))).willAnswer((invocationOnMock) -> invocationOnMock.getArgument(0));

        Bank bank = new Bank(1L, "PKO", 10000);

        this.mockMvc.perform(post("/banks"))
                //.contentType(MediaType.APPLICATION_JSON)
                //.content(objectMapper.writeValueAsString(bank))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.bankName", is(bank.getBankName())))
                .andExpect(jsonPath("$.bankBalance", is(bank.getBankBalance())));
    }

}
