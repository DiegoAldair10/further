package com.further.spring.boot.further.Repository;

import com.further.spring.boot.further.Entity.Categoria;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoriaRepository extends JpaRepository<Categoria, Long> {
}
