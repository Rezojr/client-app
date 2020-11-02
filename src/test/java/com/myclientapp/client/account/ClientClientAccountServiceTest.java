package com.myclientapp.client.account;

import com.myclientapp.client.Client;
import com.myclientapp.client.ClientRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
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
public class ClientClientAccountServiceTest {

    @Mock
    private ClientAccountRepository clientAccountRepository;

    @Mock
    private ClientRepository clientRepository;

    @InjectMocks
    private ClientAccountService clientAccountService;

    @Test
    public void shouldReturnFindAllAccountsByClientId() {
        // Given
        final Long clientId = 1L;
        final Client client = new Client("Json", "Deep", 20, "test@gmail.com", "123123123");
        client.setId(clientId);
        List<ClientAccount> clientAccountList = new ArrayList();
        clientAccountList.add(new ClientAccount(123, 2500, client));
        clientAccountList.add(new ClientAccount(222, 1000, client));
        Page<ClientAccount> accountListPage = new PageImpl<>(clientAccountList);
        Pageable pageable = PageRequest.of(0, 2);
        when(clientAccountRepository.findAllByClientId(Optional.of(client).get().getId(), pageable)).thenReturn(accountListPage);

        // When
        Page<ClientAccount> expected = clientAccountService.getAllByClientId(Optional.of(client).get().getId(), pageable);

        // Then
        assertEquals(expected.getTotalElements(), accountListPage.getTotalElements());
    }

    @Test
    public void shouldFindAccountById() {
        // Given
        Long clientId = 1L;
        Long accountId = 2L;
        final Client client = new Client("Json", "Deep", 20, "test@gmail.com", "123123123");
        final ClientAccount clientAccount = new ClientAccount(123, 2500.25, client);
        given(clientAccountRepository.findById(accountId)).willReturn(Optional.of(clientAccount));

        // When
        final ClientAccount expected = clientAccountService.findById(clientId, accountId);

        // Then
        assertThat(expected).isNotNull();
        assertEquals(expected, clientAccount);
    }

    @Test
    public void shouldBeDeleted() {
        // Given
        Long clientId = 1L;
        Long accountId = 2L;
        final Client client = new Client("Json", "Deep", 20, "test@gmail.com", "123123123");
        final ClientAccount clientAccount = new ClientAccount(123, 2500.25, client);
        given(clientAccountRepository.findById(accountId)).willReturn(Optional.of(clientAccount));

        // When
        clientAccountService.delete(clientId, accountId);

        // Then
        verify(clientAccountRepository, atLeastOnce()).deleteById(accountId);
    }

    @Test
    public void shouldUpdateAccount() {
        // Given
        final Long clientId = 1L;
        final Long accountId = 2L;
        final Client client = new Client("Json", "Deep", 20, "test@gmail.com", "123123123");
        final ClientAccount clientAccount = new ClientAccount(123, 2500.25, client);
        given(clientAccountRepository.save(clientAccount)).willReturn(clientAccount);
        given(clientAccountRepository.findById(accountId)).willReturn(Optional.of(clientAccount));

        // When
        final ClientAccount expected = clientAccountService.update(clientAccount, clientId, accountId);

        // Then
        assertThat(expected).isNotNull();
        verify(clientAccountRepository).save(any(ClientAccount.class));
    }

    @Test(expected = ClientAccountNotFoundException.class)
    public void shouldThrowExceptionIfAccountNotFound() {
        when(clientAccountRepository.findById(anyLong())).thenThrow(new ClientAccountNotFoundException(1L, 1L));
        clientAccountService.update(new ClientAccount(), 1L, 1L);
    }

    @Test
    public void shouldCreateNewAccount() {
        // Given
        Long clientId = 1L;
        final Client client = new Client("Json", "Deep", 20, "test@gmail.com", "123123123");
        final ClientAccount clientAccount = new ClientAccount(123, 2500.25, client);
        given(clientRepository.findById(clientId)).willReturn(Optional.of(client));
        given(clientAccountRepository.save(clientAccount)).willAnswer(invocationOnMock -> invocationOnMock.getArgument(0));

        // When
        ClientAccount savedClientAccount = clientAccountService.create(clientAccount, clientId);

        // Then
        assertThat(savedClientAccount).isNotNull();
        verify(clientAccountRepository).save(any(ClientAccount.class));
    }
}
