package com.myclientapp.client.account;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
public class ClientAccountController {

    private final ClientAccountService clientAccountService;
    private final ClientAccountMapper clientAccountMapper;

    @GetMapping("/clients/{id}/accounts")
    public Page<ClientAccountDto> findAllByClientId(@PathVariable Long id,
                                                    @PageableDefault Pageable pageable) {
        return clientAccountMapper.mapAll(clientAccountService.getAllByClientId(id, pageable));
    }

    @GetMapping("/clients/{clientId}/accounts/{accountId}")
    public ClientAccountDto findById(@PathVariable Long clientId,
                                     @PathVariable Long accountId) {
        return clientAccountMapper.toDto(clientAccountService.findById(clientId, accountId));
    }

    @PostMapping("/clients/{clientId}/accounts")
    public ClientAccountDto create(@PathVariable Long clientId,
                                   @Valid @RequestBody ClientAccountDto clientAccountDto) {
        return clientAccountMapper.toDto(clientAccountService.create(clientAccountMapper.toEntity(clientAccountDto), clientId));
    }

    @PutMapping("/clients/{clientId}/accounts/{accountId}")
    public ClientAccountDto update(@RequestBody ClientAccountDto clientAccountDto,
                                   @PathVariable Long clientId,
                                   @PathVariable Long accountId) {
        return clientAccountMapper.toDto(clientAccountService.update(clientAccountMapper.toEntity(clientAccountDto), clientId, accountId));
    }

    @DeleteMapping("/clients/{clientId}/accounts/{accountId}")
    public void delete(@PathVariable Long clientId,
                       @PathVariable Long accountId) {
        clientAccountService.delete(clientId, accountId);
    }
}
