package com.myclientapp.client;


import com.myclientapp.AbstractIntegrationTest;
import com.myclientapp.bank.Bank;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.hamcrest.CoreMatchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


public class ClientControllerTest extends AbstractIntegrationTest {

    @Autowired
    private ClientService clientService;

    @Test
    public void shouldFetchAllClients() throws Exception {
        final Client client = new Client("Json", "Deep", 20, "test@gmail.com", "123123123", 123);
        clientService.createClient(client);
        clientService.createClient(client);

        mockMvc.perform(MockMvcRequestBuilders
                .get("/clients")
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.content").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.content[*].firstName").isNotEmpty())
                .andExpect(MockMvcResultMatchers.jsonPath("$.content[*].lastName").isNotEmpty())
                .andExpect(MockMvcResultMatchers.jsonPath("$.content[*].age").isNotEmpty())
                .andExpect(MockMvcResultMatchers.jsonPath("$.content[*].email").isNotEmpty())
                .andExpect(MockMvcResultMatchers.jsonPath("$.content[*].phoneNumber").isNotEmpty());
    }

    @Test
    public void shouldFindClientById() throws Exception {
        final Client client = new Client("Json", "Deep", 20, "test@gmail.com", "123123123", 123);
        Client client1 = clientService.createClient(client);

        this.mockMvc.perform(get("/clients/{id}", client1.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.firstName", is(client.getFirstName())))
                .andExpect(jsonPath("$.lastName", is(client.getLastName())))
                .andExpect(jsonPath("$.age", is(client.getAge())))
                .andExpect(jsonPath("$.email", is(client.getEmail())))
                .andExpect(jsonPath("$.phoneNumber", is(client.getPhoneNumber())));
    }

    @Test
    public void shouldCreateNewBank() throws Exception {
        final Client client = new Client("Json", "Deep", 20, "test@gmail.com", "123123123", 123);
        Client client1 = clientService.createClient(client);

        mockMvc.perform(MockMvcRequestBuilders
                .post("/clients")
                .content(toJson(client1))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.firstName").exists());
    }

    @Test
    public void shouldDeleteClient() throws Exception {
        final Client client = new Client("Json", "Deep", 20, "test@gmail.com", "123123123", 123);
        Client client1 = clientService.createClient(client);

        this.mockMvc.perform(MockMvcRequestBuilders
                .delete("/clients/{id}", client1.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void shouldUpdateClient() throws Exception {
        final Client client = new Client("Json", "Deep", 20, "test@gmail.com", "123123123", 123);
        Client client1 = clientService.createClient(client);

        this.mockMvc.perform(MockMvcRequestBuilders
                .put("/banks/{id}", client1.getId())
                .content(toJson(new Bank("SKO", 2000)))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.bankName").value("SKO"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.bankBalance").value(2000));
    }

}
