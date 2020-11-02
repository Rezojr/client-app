package com.myclientapp.client;


import com.myclientapp.AbstractIntegrationTest;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
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

    @Autowired
    private ClientMapper clientMapper;

    @Test
    public void shouldFetchAllClients() throws Exception {
        final Client client = new Client("Json", "Deep", 20, "test@gmail.com", "123123123");
        Client simulateExistingClient = clientService.create(client);

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
    public void shouldFetchClientById() throws Exception {
        final Client client = new Client("Json", "Deep", 20, "test@gmail.com", "123123123");
        Client simulateExistingClient = clientService.create(client);

        this.mockMvc.perform(get("/clients/{id}", simulateExistingClient.getId())
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.firstName", is(client.getFirstName())))
                .andExpect(jsonPath("$.lastName", is(client.getLastName())))
                .andExpect(jsonPath("$.age", is(client.getAge())))
                .andExpect(jsonPath("$.email", is(client.getEmail())))
                .andExpect(jsonPath("$.phoneNumber", is(client.getPhoneNumber())));
    }

    @Test
    public void shouldCreateNewClient() throws Exception {
        final Client client = new Client("Json", "Deep", 20, "test@gmail.com", "123123123");
        ClientDto simulateExistingClient = clientMapper.toDto(clientService.create(client));

        mockMvc.perform(MockMvcRequestBuilders
                .post("/clients")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(toJson(new ClientDto("Json", "Deep", 20, "123123123", "test@gmail.com"))))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.firstName").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.lastName").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.age").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.email").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.phoneNumber").exists());
    }

    @Test
    public void shouldDeleteClient() throws Exception {
        final Client client = new Client("Json", "Deep", 20, "test@gmail.com", "123123123");
        Client simulateExistingClient = clientService.create(client);

        this.mockMvc.perform(MockMvcRequestBuilders
                .delete("/clients/{id}", simulateExistingClient.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void shouldUpdateClient() throws Exception {
        final Client client = new Client("Json", "Deep", 20, "test@gmail.com", "123123123");
        ClientDto simulateExistingClient = clientMapper.toDto(clientService.create(client));

        this.mockMvc.perform(MockMvcRequestBuilders
                .put("/clients/{id}", simulateExistingClient.getId())
                .content(toJson(new ClientDto("Test", "Deep", 20, "123123213", "test@gmail.com")))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.firstName").value("Test"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.lastName").value("Deep"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.age").value(20))
                .andExpect(MockMvcResultMatchers.jsonPath("$.email").value("test@gmail.com"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.phoneNumber").value("123123213"));
    }

}
