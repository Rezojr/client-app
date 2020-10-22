package com.myclientapp.client;

import com.myclientapp.bank.Bank;
import com.myclientapp.bank.BankNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
@Slf4j
public class ClientServiceTest {

    @Mock
    private ClientRepository clientRepository;

    @InjectMocks
    private ClientService clientService;

    @Test
    public void shouldReturnFindAllClients() {
        List<Client> clientList = new ArrayList();
        clientList.add(new Client("Json", "Deep", 20, "test@gmail.com", "123123123"));
        clientList.add(new Client("Json", "Deep", 20, "test@gmail.com", "123123123"));
        Page<Client> clientListPage = new PageImpl<>(clientList);

        Pageable pageable = PageRequest.of(0, 2);

        when(clientRepository.findAll(pageable)).thenReturn(clientListPage);
        Page<Client> expected = clientService.findAllClients(pageable);

        assertEquals(expected.getTotalElements(), clientListPage.getTotalElements());
    }

    @Test
    public void shouldReturnFindClientById() {
        final Long id = 1L;
        final Client client = new Client("Json", "Deep", 20, "test@gmail.com", "123123123");
        given(clientRepository.findById(id)).willReturn(Optional.of(client));
        final Client expected = clientService.findClientById(id);
        assertThat(expected).isNotNull();
        assertEquals(expected, client);
    }

    @Test
    public void shouldBeDeleted() {
        final Long id = 1L;
        clientService.delete(id);
        verify(clientRepository, atLeastOnce()).deleteById(id);
    }

    @Test
    public void shouldUpdateClient() {
        final Long id = 1L;
        final Client client = new Client("Json", "Deep", 20, "test@gmail.com", "123123123");
        given(clientRepository.save(client)).willReturn(client);
        given(clientRepository.findById(id)).willReturn(Optional.of(client));
        final Client expected = clientService.updateClient(client, 1L);
        assertThat(expected).isNotNull();
        verify(clientRepository).save(any(Client.class));
    }

    @Test(expected = ClientNotFoundException.class)
    public void shouldThrowExceptionIfClientNotFound() {
        Mockito.when(clientRepository.findById(anyLong())).thenThrow(new ClientNotFoundException(1L));

        clientService.updateClient(new Client(), 1L);

    }

    @Test
    public void shouldCreateNewClient() {
        final Client client = new Client("Json", "Deep", 20, "test@gmail.com", "123123123");
        given(clientRepository.save(client)).willAnswer(invocationOnMock -> invocationOnMock.getArgument(0));
        Client savedClient = clientService.createClient(client);
        assertThat(savedClient).isNotNull();
        verify(clientRepository).save(any(Client.class));
    }


}