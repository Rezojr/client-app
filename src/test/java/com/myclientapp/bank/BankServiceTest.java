package com.myclientapp.bank;

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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@RunWith(MockitoJUnitRunner.class)
@Slf4j
public class BankServiceTest {

    @Mock
    private BankRepository bankRepository;

    @InjectMocks
    private BankService bankService;

    @Test
    public void shouldReturnFindAll() {
        // Given
        List<Bank> bankList = new ArrayList();
        bankList.add(new Bank("PKO", 10000));
        bankList.add(new Bank("Test", 20000));
        Page<Bank> bankListPage = new PageImpl<>(bankList);
        Pageable pageable = PageRequest.of(0, 2);
        when(bankRepository.findAll(pageable)).thenReturn(bankListPage);

        // When
        Page<Bank> expected = bankService.findAll(pageable);

        // Then
        assertEquals(expected.getTotalElements(), bankListPage.getTotalElements());
    }

    @Test
    public void findBankById() {
        // Given
        final Long id = 1L;
        final Bank bank = new Bank("PKO", 10000);
        given(bankRepository.findById(id)).willReturn(Optional.of(bank));

        // When
        final Bank expected = bankService.findById(id);

        // Then
        assertThat(expected).isNotNull();
        assertEquals(expected, bank);
    }

    @Test
    public void shouldBeDeleted() {
        // Given
        final Long id = 1L;

        // When
        bankService.delete(id);

        // Then
        verify(bankRepository, atLeastOnce()).deleteById(id);
    }

    @Test
    public void updateBank() {
        // Given
        final Long id = 1L;
        final Bank bank = new Bank("PKO", 10000);
        given(bankRepository.save(bank)).willReturn(bank);
        given(bankRepository.findById(id)).willReturn(Optional.of(bank));

        // When
        final Bank expected = bankService.update(bank, id);

        // Then
        assertThat(expected).isNotNull();
        verify(bankRepository).save(any(Bank.class));
    }

    @Test(expected = BankNotFoundException.class)
    public void shouldThrowExceptionIfBankNotFound() {
        Mockito.when(bankRepository.findById(anyLong())).thenThrow(new BankNotFoundException(1L));
        bankService.update(new Bank(), 1L);
    }

    @Test
    public void shouldCreateNewBank() {
        // Given
        final Bank bank = new Bank("PKO", 10000);
        given(bankRepository.save(bank)).willAnswer(invocationOnMock -> invocationOnMock.getArgument(0));

        // When
        Bank expected = bankService.create(bank);

        // Then
        assertThat(expected).isNotNull();
        verify(bankRepository).save(any(Bank.class));
    }

}
