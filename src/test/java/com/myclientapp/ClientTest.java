package com.myclientapp;

        import com.myclientapp.client.*;
        import org.json.JSONException;
        import org.json.JSONObject;
        import org.junit.Before;
        import org.junit.Test;
        import org.junit.jupiter.api.BeforeEach;
        import org.junit.runner.RunWith;
        import org.mockito.MockitoAnnotations;
        import org.modelmapper.ModelMapper;
        import org.skyscreamer.jsonassert.JSONAssert;
        import org.springframework.beans.factory.annotation.Autowired;
        import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
        import org.springframework.boot.test.mock.mockito.MockBean;
        import org.springframework.http.MediaType;
        import org.springframework.security.test.context.support.WithMockUser;
        import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
        import org.springframework.test.web.servlet.MockMvc;
        import org.springframework.test.web.servlet.MvcResult;
        import org.springframework.test.web.servlet.RequestBuilder;
        import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

        import java.util.LinkedList;
        import java.util.List;

        import static org.junit.Assert.*;
        import static org.mockito.ArgumentMatchers.anyLong;
        import static org.mockito.Mockito.*;

@RunWith(SpringJUnit4ClassRunner.class)
@WebMvcTest(value = ClientController.class)
@WithMockUser
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


    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        clientRepository.save(new Client(1L, "Dark", "Vader", 66, "Darkside@gmail.com", "333111222", 55));
        clientRepository.save(new Client(2L, "John", "Ladder", 66, "test@gmail.com", "123456789", 1));

    }


    @Test
    public void testGetOneClient() throws Exception {
        Client mockClient = new Client(1L, "Dark", "Vader", 66, "Darkside@gmail.com", "333111222", 55);
        when(clientService.one(anyLong())).thenReturn(mockClient);
        System.out.println(mockClient);

        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .get("/clients/1")
                .accept(MediaType.APPLICATION_JSON);

        MvcResult result = mockMvc.perform(requestBuilder).andReturn();

        System.out.println(result.getResponse().getContentAsString());
        String expected = "{\"id\":1,\"firstName\":\"Dark\",\"lastName\":\"Vader\",\"age\":66,\"email\":\"Darkside@gmail.com\",\"phoneNumber\":\"333111222\",\"accountNumber\":55}";

        JSONObject json = new JSONObject(expected);

        try{
            System.out.println("Success: json = ");
            System.out.println(json.toString(2));
        } catch (JSONException ex) {
            System.out.println("ERROR: " + ex);
        }
        JSONAssert.assertEquals(result.getResponse().getContentAsString(), json.toString(2), false);

    }

    @Test
    public void testGetAllClients() {
        List<Client> all = new LinkedList<>();
        all.add(new Client(1L, "Dark", "Vader", 66, "Darkside@gmail.com", "333111222", 55));
        all.add(new Client(2L, "John", "Ladder", 66, "test@gmail.com", "123456789", 1));

        when(clientRepository.findAll()).thenReturn(all);

        List<Client> result = clientService.all();

        assertNotNull(result);
        assertFalse(result.isEmpty());
        assertEquals(result, all);

    }


}
