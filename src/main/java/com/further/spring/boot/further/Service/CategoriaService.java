package com.further.spring.boot.further.Service;

import com.further.spring.boot.further.Dto.CategoriaDTO;
import com.further.spring.boot.further.Entity.Categoria;
import com.further.spring.boot.further.Mapper.CategoriaMapper;
import com.further.spring.boot.further.Repository.CategoriaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
public class CategoriaService {


    @Autowired
    private CategoriaRepository categoriaRepository;
    @Autowired
    private CategoriaMapper categoriaMapper;

    public List<CategoriaDTO> obtenerCategoria() {
        return categoriaRepository.findAll().stream()
                .map(categoriaMapper::toDTO)
                .collect(Collectors.toList());
    }

    public Optional<CategoriaDTO> obtenerCategoriaPorId(Long id) {
        return categoriaRepository.findById(id)
                .map(categoriaMapper::toDTO);
    }

    public CategoriaDTO crearCategoria(CategoriaDTO categoriaDTO) {
        Categoria categoria = categoriaMapper.toEntity(categoriaDTO);
        Categoria metodoCategoriaGuardado = categoriaRepository.save(categoria);
        return categoriaMapper.toDTO(metodoCategoriaGuardado);
    }

    public CategoriaDTO actualizarCategoria(Long id, CategoriaDTO categoriaDTO) {
        return categoriaRepository.findById(id)
                .map(categoria -> {
                    categoria.setNombre(categoriaDTO.getNombre());
                    categoria.setDescripcion(categoriaDTO.getDescripcion());
                    return categoriaRepository.save(categoria);
                })
                .map(categoriaMapper::toDTO)
                .orElseThrow(() -> new RuntimeException("Método de categoria no encontrado"));
    }

    public void eliminarCategoria(Long id) {
        if (!categoriaRepository.existsById(id)) {
            throw new RuntimeException("categoria no encontrado");
        }
        categoriaRepository.deleteById(id);
    }
}