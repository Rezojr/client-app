package com.myclientapp.client;

import com.myclientapp.client.dto.ClientDto;
import org.modelmapper.ModelMapper;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
public class ClientController {

    private final ClientRepository repository;
    private final ClientModelAssembler assembler;
    private final ClientService service;
    private final ModelMapper modelMapper;

    public ClientController(ClientRepository repository, ClientModelAssembler assembler, ClientService service, ModelMapper modelMapper) {
        this.repository = repository;
        this.assembler = assembler;
        this.service = service;
        this.modelMapper = modelMapper;
    }

    @GetMapping("/clients/{id}")
    public EntityModel<Client> one(@PathVariable Long id) {
        Client client = service.one(id);

        return assembler.toModel(client);
    }

    @GetMapping("/clientsdto/{id}")
    public ClientDto oneDto(@PathVariable Long id) {
        return toDto(service.one(id));
    }

    @GetMapping("/clients")
    public CollectionModel<EntityModel<Client>> all() {

        List<EntityModel<Client>> employees = service.all();

        return CollectionModel.of(employees, linkTo(methodOn(ClientController.class).all()).withSelfRel());
    }

    @PostMapping("/clients")
    public ResponseEntity<?> newClient(@RequestBody Client newClient) {
        return service.newClient(newClient);
    }

    @PutMapping("/clients/{id}")
    public ResponseEntity<?> replaceClient(@RequestBody Client newClient, @PathVariable Long id) {

        return service.replaceClient(newClient, id);
    }

    @DeleteMapping("/clients/{id}")
    public ResponseEntity<?> deleteClient(@PathVariable Long id) {

        return service.deleteClient(id);
    }

    private ClientDto toDto(Client client) {
        ClientDto clientDto = modelMapper.map(client, ClientDto.class);
        return clientDto;
    }

}
