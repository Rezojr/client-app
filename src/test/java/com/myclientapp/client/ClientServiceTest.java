package com.myclientapp.client;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
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
        // Given
        List<Client> clientList = new ArrayList();
        clientList.add(new Client("Json", "Deep", 20, "test@gmail.com", "123123123"));
        clientList.add(new Client("Json", "Deep", 20, "test@gmail.com", "123123123"));
        Page<Client> clientListPage = new PageImpl<>(clientList);
        Pageable pageable = PageRequest.of(0, 2);
        when(clientRepository.findAll(pageable)).thenReturn(clientListPage);

        // When
        Page<Client> expected = clientService.findAll(pageable);

        // Then
        assertEquals(expected.getTotalElements(), clientListPage.getTotalElements());
    }

    @Test
    public void shouldReturnFindClientById() {
        // Given
        final Long id = 1L;
        final Client client = new Client("Json", "Deep", 20, "test@gmail.com", "123123123");
        given(clientRepository.findById(id)).willReturn(Optional.of(client));

        // When
        final Client expected = clientService.findById(id);

        // Then
        assertThat(expected).isNotNull();
        assertEquals(expected, client);
    }

    @Test
    public void shouldBeDeleted() {
        // Given
        final Long id = 1L;

        // When
        clientService.delete(id);

        // Then
        verify(clientRepository, atLeastOnce()).deleteById(id);
    }

    @Test
    public void shouldUpdateClient() {
        // Given
        final Long id = 1L;
        final Client client = new Client("Json", "Deep", 20, "test@gmail.com", "123123123");
        given(clientRepository.save(client)).willReturn(client);
        given(clientRepository.findById(id)).willReturn(Optional.of(client));

        // When
        final Client expected = clientService.update(client, 1L);

        // Then
        assertThat(expected).isNotNull();
        verify(clientRepository).save(any(Client.class));
    }

    @Test(expected = ClientNotFoundException.class)
    public void shouldThrowExceptionIfClientNotFound() {
        Mockito.when(clientRepository.findById(anyLong())).thenThrow(new ClientNotFoundException(1L));
        clientService.update(new Client(), 1L);
    }

    @Test
    public void shouldCreateNewClient() {
        // Given
        final Client client = new Client("Json", "Deep", 20, "test@gmail.com", "123123123");
        given(clientRepository.save(client)).willAnswer(invocationOnMock -> invocationOnMock.getArgument(0));

        // When
        Client savedClient = clientService.create(client);

        // Then
        assertThat(savedClient).isNotNull();
        verify(clientRepository).save(any(Client.class));
    }

}
