package ar.edu.um.ingenieria_aplicada.microblogging.repository;

import ar.edu.um.ingenieria_aplicada.microblogging.domain.Usuario;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Spring Data  repository for the Usuario entity.
 */
@SuppressWarnings("unused")
@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

    @Query(value = "select distinct usuario from Usuario usuario left join fetch usuario.likeas left join fetch usuario.sigues left join fetch usuario.bloqueas",
        countQuery = "select count(distinct usuario) from Usuario usuario")
    Page<Usuario> findAllWithEagerRelationships(Pageable pageable);

    @Query(value = "select distinct usuario from Usuario usuario left join fetch usuario.likeas left join fetch usuario.sigues left join fetch usuario.bloqueas")
    List<Usuario> findAllWithEagerRelationships();

    @Query("select usuario from Usuario usuario left join fetch usuario.likeas left join fetch usuario.sigues left join fetch usuario.bloqueas where usuario.id =:id")
    Optional<Usuario> findOneWithEagerRelationships(@Param("id") Long id);

}
