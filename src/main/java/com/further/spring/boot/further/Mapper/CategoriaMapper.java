package com.further.spring.boot.further.Mapper;


import com.further.spring.boot.further.Dto.CategoriaDTO;
import com.further.spring.boot.further.Entity.Categoria;
import org.springframework.stereotype.Component;

@Component
public class CategoriaMapper {


        public CategoriaDTO toDTO(Categoria categorias) {
            CategoriaDTO dto = new CategoriaDTO();
            dto.setCategoriaId(categorias.getCategoriaId());
            dto.setNombre(categorias.getNombre());
            dto.setDescripcion(categorias.getDescripcion());
            return dto;
        }


        public Categoria toEntity(CategoriaDTO dto) {
            Categoria categorias = new Categoria();
            categorias.setCategoriaId(dto.getCategoriaId());
            categorias.setNombre(dto.getNombre());
            categorias.setDescripcion(dto.getDescripcion());
            return categorias;
        }
}
