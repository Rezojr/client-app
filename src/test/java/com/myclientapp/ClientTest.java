package com.myclientapp;

import com.myclientapp.client.*;
import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.jupiter.api.BeforeEach;
import org.junit.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.MediaType;
import org.springframework.test.context.event.annotation.BeforeTestClass;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.request;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(value = ClientController.class)
public class ClientTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    public ClientService clientService;

    @MockBean
    ClientModelAssembler assembler;

    @MockBean
    ModelMapper modelMapper;

    @MockBean
    ClientRepository clientRepository;

    Client mockClient = new Client(1L, "Dark", "Vader", 66, "Darkside@gmail.com", "333111222", 55);

    @Test
    public void testGetOneClient() throws Exception {
        Mockito.when(clientService.one(Mockito.anyLong())).thenReturn(mockClient);

        System.out.println(clientService.one(1L));

        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .get("/clients/1")
                .accept(MediaType.APPLICATION_JSON);

        MvcResult result = mockMvc.perform(requestBuilder).andReturn();

        System.out.println(result.getResponse());
        String expected = "{id:1,firstName:Dark,lastName:Vader,age:66,email:Darkside@gmail.com,phoneNumber:333111222,accountNumber:55}";

        JSONAssert.assertEquals(expected, result.getResponse().getContentAsString(), false);
    }

}
