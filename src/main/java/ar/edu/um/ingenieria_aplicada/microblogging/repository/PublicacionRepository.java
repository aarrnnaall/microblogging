package ar.edu.um.ingenieria_aplicada.microblogging.repository;

import ar.edu.um.ingenieria_aplicada.microblogging.domain.Publicacion;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Spring Data  repository for the Publicacion entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PublicacionRepository extends JpaRepository<Publicacion, Long> {

    @Query(value = "select distinct publicacion from Publicacion publicacion left join fetch publicacion.mencionas left join fetch publicacion.esFavs",
        countQuery = "select count(distinct publicacion) from Publicacion publicacion")
    Page<Publicacion> findAllWithEagerRelationships(Pageable pageable);

    @Query(value = "select distinct publicacion from Publicacion publicacion left join fetch publicacion.mencionas left join fetch publicacion.esFavs")
    List<Publicacion> findAllWithEagerRelationships();

    @Query("select publicacion from Publicacion publicacion left join fetch publicacion.mencionas left join fetch publicacion.esFavs where publicacion.id =:id")
    Optional<Publicacion> findOneWithEagerRelationships(@Param("id") Long id);

}
