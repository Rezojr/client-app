package com.myclientapp.client.account;

import com.myclientapp.client.ClientNotFoundException;
import com.myclientapp.client.ClientRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ClientAccountService {

    private final ClientAccountRepository clientAccountRepository;
    private final ClientRepository clientRepository;

    public Page<ClientAccount> getAllByClientId(Long clientId, Pageable pageable) {
        return clientAccountRepository.findAllByClientId(clientId, pageable);
    }

    public ClientAccount findById(Long clientId, Long accountId) {
        return clientAccountRepository.findById(accountId)
                .orElseThrow(() -> new ClientAccountNotFoundException(clientId, accountId));
    }

    public ClientAccount create(ClientAccount newClientAccount, Long clientId) {
        return clientRepository.findById(clientId).map(
                client -> {
                    newClientAccount.setClient(client);
                    return clientAccountRepository.save(newClientAccount);
                }).orElseThrow(() -> new ClientNotFoundException(clientId));
    }

    public ClientAccount update(ClientAccount newClientAccount, Long clientId, Long accountId) {
        return clientAccountRepository.findById(accountId)
                .map(account -> {
                    account.setAccountNumber(newClientAccount.getAccountNumber());
                    account.setAccountBalance(newClientAccount.getAccountBalance());
                    return clientAccountRepository.save(account);
                }).orElseThrow(() -> new ClientAccountNotFoundException(clientId, accountId));
    }

    public ResponseEntity<?> delete(Long clientId, Long accountId) {
        return clientAccountRepository.findById(accountId).map(account -> {
            clientAccountRepository.deleteById(accountId);
            return ResponseEntity.ok().build();
        }).orElseThrow(() -> new ClientAccountNotFoundException(clientId, accountId));
    }

}