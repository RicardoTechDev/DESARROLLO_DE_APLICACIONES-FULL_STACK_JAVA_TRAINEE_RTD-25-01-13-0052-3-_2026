package cl.bootcamp.springedumanager_2.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import cl.bootcamp.springedumanager_2.model.Curso;


@Repository
public interface CursoRepository extends JpaRepository <Curso, Integer> {

    boolean existsByNombreIgnoreCase(
            String nombre
    );


    boolean existsByNombreIgnoreCaseAndIdNot(
            String nombre,
            int id
    );

}