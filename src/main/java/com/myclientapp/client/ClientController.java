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
    public Page<ClientDto> findAll(@PageableDefault Pageable pageable) {
        return clientMapper.mapAll(clientService.findAll(pageable));
    }

    @GetMapping("/clients/{id}")
    public ClientDto findById(@PathVariable Long id) {
        return clientMapper.toDto(clientService.findById(id));
    }

    @PostMapping("/clients")
    public ClientDto create(@RequestBody ClientDto clientDto) {
        return clientMapper.toDto(clientService.create(clientMapper.toEntity(clientDto)));
    }


    @PutMapping("/clients/{id}")
    public ClientDto update(@RequestBody ClientDto clientDto, @PathVariable Long id) {
        return clientMapper.toDto(clientService.update(clientMapper.toEntity(clientDto), id));
    }

    @DeleteMapping("/clients/{id}")
    public void delete(@PathVariable Long id) {
        clientService.delete(id);
    }

}
