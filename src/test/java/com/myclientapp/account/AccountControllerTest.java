package com.myclientapp.account;

import com.myclientapp.AbstractIntegrationTest;
import com.myclientapp.bank.Bank;
import com.myclientapp.bank.BankNotFoundException;
import com.myclientapp.client.Client;
import com.myclientapp.client.ClientService;
import org.junit.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class AccountControllerTest extends AbstractIntegrationTest {

    @Autowired
    AccountService accountService;

    @Autowired
    ClientService clientService;


    @Test
    public void shouldFetchAllAccounts() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                .get("/accounts")
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.content").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.content[*].accountNumber").isNotEmpty())
                .andExpect(MockMvcResultMatchers.jsonPath("$.content[*].balance").isNotEmpty());
    }

    @Test
    public void shouldFetchAllAccountsByClientId() throws Exception {
        final Client client = new Client("Json", "Deep", 20, "test@gmail.com", "123123123");
        Client client1 = clientService.createClient(client);


        mockMvc.perform(MockMvcRequestBuilders
                .get("/clients/{clientId}/accounts", client1.getId())
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    public void shouldFetchAccountById() throws Exception {
        final Client client = new Client("Json", "Deep", 20, "test@gmail.com", "123123123");
        Client client1 = clientService.createClient(client);
        final Account account = new Account(123, 2500.25, client);
        Account account1 = accountService.createAccount(client1.getId(), account);

        this.mockMvc.perform(get("/accounts/{id}", account1.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.accountNumber", is(account.getAccountNumber())))
                .andExpect(jsonPath("$.balance", is(account.getBalance())));
    }

    @Test
    public void shouldCreateNewAccountForClient() throws Exception {
        final Client client = new Client("Json", "Deep", 20, "test@gmail.com", "123123123");
        Client client1 = clientService.createClient(client);
        final Account account = new Account(123, 2500.25, client);
        Account account1 = accountService.createAccount(client1.getId(), account);

        mockMvc.perform(MockMvcRequestBuilders
                .post("/clients/{clientId}/accounts", client1.getId())
                .content(toJson(account1))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.accountNumber").exists());
    }

    @Test
    public void shouldDeleteAccount() throws Exception {
        final Client client = new Client("Json", "Deep", 20, "test@gmail.com", "123123123");
        Client client1 = clientService.createClient(client);
        final Account account = new Account(123, 2500.25, client);
        Account account1 = accountService.createAccount(client1.getId(), account);

        this.mockMvc.perform(MockMvcRequestBuilders
                .delete("/accounts/{id}", account1.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void shouldUpdateAccount() throws Exception {
        final Client client = new Client("Json", "Deep", 20, "test@gmail.com", "123123123");
        Client client1 = clientService.createClient(client);
        final Account account = new Account(123, 2500.25, client);
        Account account1 = accountService.createAccount(client1.getId(), account);

        this.mockMvc.perform(MockMvcRequestBuilders
                .put("/accounts/{id}", account1.getId())
                .content(toJson(new Account(123, 2500.25, client)))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.accountNumber").value(123))
                .andExpect(MockMvcResultMatchers.jsonPath("$.balance").value(2500.25));
    }




}
