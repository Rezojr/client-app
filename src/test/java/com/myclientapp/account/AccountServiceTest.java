package com.myclientapp.account;

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
public class AccountServiceTest {

    @Mock
    private AccountRepository accountRepository;

    @Mock
    private ClientRepository clientRepository;

    @InjectMocks
    private AccountService accountService;

    @Test
    public void shouldReturnFindAll() {
        //Given
        final Client client = new Client("Json", "Deep", 20, "test@gmail.com", "123123123");
        List<Account> accountList = new ArrayList();
        accountList.add(new Account(123, 2500, client));
        accountList.add(new Account(222, 1000, client));
        Page<Account> accountListPage = new PageImpl<>(accountList);
        Pageable pageable = PageRequest.of(0, 2);
        when(accountRepository.findAll(pageable)).thenReturn(accountListPage);

        // When
        Page<Account> expected = accountService.findAllAccounts(pageable);

        // Then
        assertThat(expected).isNotEmpty().hasSize(2);
        assertEquals(expected.getTotalElements(), accountListPage.getTotalElements());
    }

    @Test
    public void shouldReturnFindAllAccountsByClientId() {
        // Given
        final Long clientId = 1L;
        final Client client = new Client("Json", "Deep", 20, "test@gmail.com", "123123123");
        client.setId(clientId);
        List<Account> accountList = new ArrayList();
        accountList.add(new Account(123, 2500, client));
        accountList.add(new Account(222, 1000, client));
        Page<Account> accountListPage = new PageImpl<>(accountList);
        Pageable pageable = PageRequest.of(0, 2);
        when(accountRepository.findByClientId(Optional.of(client).get().getId(), pageable)).thenReturn(accountListPage);

        // When
        Page<Account> expected = accountService.getAllAccountsByClientId(Optional.of(client).get().getId(), pageable);

        // Then
        assertEquals(expected.getTotalElements(), accountListPage.getTotalElements());
    }

    @Test
    public void shouldFindAccountById() {
        // Given
        final Client client = new Client("Json", "Deep", 20, "test@gmail.com", "123123123");
        final Account account = new Account(123, 2500.25, client);
        given(accountRepository.findById(client.getId())).willReturn(Optional.of(account));

        // When
        final Account expected = accountService.findAccountById(account.getId());

        // Then
        assertThat(expected).isNotNull();
        assertEquals(expected, account);
    }

    @Test
    public void shouldBeDeleted() {
        // Given
        final Long id = 1L;

        // When
        accountService.delete(id);

        // Then
        verify(accountRepository, atLeastOnce()).deleteById(id);
    }

    @Test
    public void shouldUpdateAccount() {
        // Given
        final Long id = 1L;
        final Client client = new Client("Json", "Deep", 20, "test@gmail.com", "123123123");
        final Account account = new Account(123, 2500.25, client);
        given(accountRepository.save(account)).willReturn(account);
        given(accountRepository.findById(id)).willReturn(Optional.of(account));

        // When
        final Account expected = accountService.updateAccount(account, id);

        // Then
        assertThat(expected).isNotNull();
        verify(accountRepository).save(any(Account.class));
    }

    @Test(expected = AccountNotFoundException.class)
    public void shouldThrowExceptionIfAccountNotFound() {
        when(accountRepository.findById(anyLong())).thenThrow(new AccountNotFoundException(1L));
        accountService.updateAccount(new Account(), 1L);
    }

    @Test
    public void shouldCreateNewAccount() {
        // Given
        Long clientId = 1L;
        final Client client = new Client("Json", "Deep", 20, "test@gmail.com", "123123123");
        final Account account = new Account(123, 2500.25, client);
        given(clientRepository.findById(clientId)).willReturn(Optional.of(client));
        given(accountRepository.save(account)).willAnswer(invocationOnMock -> invocationOnMock.getArgument(0));

        // When
        Account savedAccount = accountService.createAccount(clientId, account);

        // Then
        assertThat(savedAccount).isNotNull();
        verify(accountRepository).save(any(Account.class));
    }
}
