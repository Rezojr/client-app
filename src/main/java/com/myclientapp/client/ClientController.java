package com.myclientapp.client;

import com.myclientapp.account.Account;
import com.myclientapp.client.dto.ClientDto;
import com.myclientapp.client.dto.ClientMapper;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequiredArgsConstructor
public class ClientController {

    private final ClientRepository clientRepository;
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
    public ClientDto replaceClient(@RequestBody Client newClient, @PathVariable Long id) {

        return clientMapper.toDto(clientService.replaceClient(newClient, id));
    }

    @DeleteMapping("/clients/{id}")
    public void deleteClient(@PathVariable Long id) {

        clientService.deleteClient(id);
    }

}
