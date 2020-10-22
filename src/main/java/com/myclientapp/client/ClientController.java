package com.myclientapp.client;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

@RestController
@RequiredArgsConstructor
public class ClientController {

    private final ClientService clientService;
    private final ClientMapper clientMapper;

    @GetMapping("/clients")
    public Page<ClientDto> findAllClients(@PageableDefault Pageable pageable) {
        return clientMapper.mapAll(clientService.findAllClients(pageable));
    }

    @GetMapping("/clients/{id}")
    public ClientDto findClientById(@PathVariable Long id) {
        return clientMapper.toDto(clientService.findClientById(id));
    }

    @PostMapping("/clients")
    public ClientDto createClient(@RequestBody Client newClient) {
        return clientMapper.toDto(clientService.createClient(newClient));
    }


    @PutMapping("/clients/{id}")
    public ClientDto updateClient(@RequestBody Client newClient, @PathVariable Long id) {

        return clientMapper.toDto(clientService.updateClient(newClient, id));
    }

    @DeleteMapping("/clients/{id}")
    public void delete(@PathVariable Long id) {

        clientService.delete(id);
    }

}
