package com.further.spring.boot.further.Service;


import com.further.spring.boot.further.Dto.ClienteDTO;
import com.further.spring.boot.further.Entity.Cliente;
import com.further.spring.boot.further.Exception.ClienteYaExisteException;
import com.further.spring.boot.further.Mapper.ClienteMapper;
import com.further.spring.boot.further.Repository.ClienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ClienteService {

    @Autowired
    private ClienteRepository clienteRepository;
    @Autowired
    private ClienteMapper clienteMapper;

    public List<ClienteDTO> obtenerTodosClientes() {
        return clienteRepository.findAll().stream()
                .map(clienteMapper::toDTO)
                .collect(Collectors.toList());
    }

    public Optional<ClienteDTO> obtenerClientePorId(Long id) {
        return clienteRepository.findById(id)
                .map(clienteMapper::toDTO);
    }

    public ClienteDTO crearCliente(ClienteDTO clienteDTO) {
        if (clienteDTO.getEmail() == null || clienteDTO.getEmail().isEmpty()) {
            throw new IllegalArgumentException("El email no puede ser nulo o vacío");
        }

        Optional<Cliente> clienteExistente = clienteRepository.findByEmail(clienteDTO.getEmail());
        if (clienteExistente.isPresent()) {
            throw new ClienteYaExisteException("El cliente con el email " + clienteDTO.getEmail() + " ya existe");
        }

        Cliente cliente = clienteMapper.toEntity(clienteDTO);
        Cliente clienteGuardado = clienteRepository.save(cliente);
        return clienteMapper.toDTO(clienteGuardado);
    }


    public ClienteDTO actualizarCliente(Long id, ClienteDTO clienteDTO) {
        return clienteRepository.findById(id)
                .map(cliente -> {
                    cliente.setNombre(clienteDTO.getNombre());
                    cliente.setApellido(clienteDTO.getApellido());
                    cliente.setEmail(clienteDTO.getEmail());
                    cliente.setTelefono(clienteDTO.getTelefono());
                    cliente.setDireccion(clienteDTO.getDireccion());
                    cliente.setCiudad(clienteDTO.getCiudad());
                    cliente.setPais(clienteDTO.getPais());
                    cliente.setDocumento(clienteDTO.getDocumento());
                    cliente.setNumeroDocumento(clienteDTO.getNumeroDocumento());
                    return clienteRepository.save(cliente);
                })
                .map(clienteMapper::toDTO)
                .orElseThrow(() -> new RuntimeException("Cliente no encontrado"));
    }

    public void eliminarCliente(Long id) {
        if (!clienteRepository.existsById(id)) {
            throw new RuntimeException("Cliente no encontrado");
        }
        clienteRepository.deleteById(id);
    }
}
