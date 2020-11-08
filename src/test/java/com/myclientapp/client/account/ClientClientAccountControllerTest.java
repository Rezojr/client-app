package com.myclientapp.client.account;

import com.myclientapp.AbstractIntegrationTest;
import com.myclientapp.client.Client;
import com.myclientapp.client.ClientDto;
import com.myclientapp.client.ClientMapper;
import com.myclientapp.client.ClientService;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class ClientClientAccountControllerTest extends AbstractIntegrationTest {

    @Autowired
    private ClientAccountService clientAccountService;

    @Autowired
    private ClientService clientService;

    @Autowired
    private ClientAccountMapper clientAccountMapper;

    @Autowired
    private ClientMapper clientMapper;

    @Test
    public void shouldFetchAllAccountsByClientId() throws Exception {
        final Client client = new Client("Json", "Deep", 20, "test@gmail.com", "123123123");
        Client simulateExistingClient = clientService.create(client);
        final ClientAccount clientAccount = new ClientAccount(123, 2500.25, client);
        ClientAccount simulateExistingClientAccount = clientAccountService.create(clientAccount, simulateExistingClient.getId());

        mockMvc.perform(MockMvcRequestBuilders
                .get("/clients/{clientId}/accounts", simulateExistingClient.getId())
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    public void shouldFetchAccountById() throws Exception {
        final Client client = new Client("Json", "Deep", 20, "test@gmail.com", "123123123");
        Client simulateExistingClient = clientService.create(client);
        final ClientAccount clientAccount = new ClientAccount(123, 2500.25, client);
        ClientAccount simulateExistingClientAccount = clientAccountService.create(clientAccount, simulateExistingClient.getId());

        this.mockMvc.perform(MockMvcRequestBuilders
                .get("/clients/{clientId}/accounts/{accountId}", simulateExistingClient.getId(), simulateExistingClientAccount.getId())
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.accountNumber", is(clientAccount.getAccountNumber())))
                .andExpect(jsonPath("$.accountBalance", is(clientAccount.getAccountBalance())));
    }

    @Test
    public void shouldCreateNewAccountForClient() throws Exception {
        final Client client = new Client("Json", "Deep", 20, "test@gmail.com", "123123123");
        ClientDto simulateExistingClient = clientMapper.toDto(clientService.create(client));
        final ClientAccountDto account = new ClientAccountDto(123, 2500.25);
        ClientAccountDto simulateExistingAccount = clientAccountMapper.toDto(clientAccountService.create(clientAccountMapper.toEntity(account), simulateExistingClient.getId()));

        mockMvc.perform(MockMvcRequestBuilders
                .post("/clients/{clientId}/accounts", simulateExistingClient.getId())
                .content(toJson(simulateExistingAccount))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.accountNumber").exists());
    }

    @Test
    public void shouldDeleteAccount() throws Exception {
        final Client client = new Client("Json", "Deep", 20, "test@gmail.com", "123123123");
        Client simulateExistingClient = clientService.create(client);
        final ClientAccount clientAccount = new ClientAccount(123, 2500.25, client);
        ClientAccount simulateExistingClientAccount = clientAccountService.create(clientAccount, simulateExistingClient.getId());

        this.mockMvc.perform(MockMvcRequestBuilders
                .delete("/clients/{clientId}/accounts/{id}", simulateExistingClient.getId(), simulateExistingClientAccount.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void shouldUpdateAccount() throws Exception {
        final Client client = new Client("Json", "Deep", 20, "test@gmail.com", "123123123");
        ClientDto simulateExistingClient = clientMapper.toDto(clientService.create(client));
        final ClientAccountDto account = new ClientAccountDto(123, 2500.25);
        ClientAccountDto simulateExistingAccount = clientAccountMapper.toDto(clientAccountService.create(clientAccountMapper.toEntity(account), simulateExistingClient.getId()));

        this.mockMvc.perform(MockMvcRequestBuilders
                .put("/clients/{clientId}/accounts/{accountId}", simulateExistingClient.getId(), simulateExistingAccount.getId())
                .content(toJson(new ClientAccountDto(123, 2500.25)))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.accountNumber").value(123))
                .andExpect(MockMvcResultMatchers.jsonPath("$.accountBalance").value(2500.25));
    }


}
