package ar.edu.um.ingenieria_aplicada.microblogging.repository;

import ar.edu.um.ingenieria_aplicada.microblogging.domain.Tag;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Tag entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TagRepository extends JpaRepository<Tag, Long> {

}
