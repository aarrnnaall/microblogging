package ar.edu.um.ingenieria_aplicada.microblogging.repository;

import ar.edu.um.ingenieria_aplicada.microblogging.domain.Publication;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Spring Data  repository for the Publication entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PublicationRepository extends JpaRepository<Publication, Long> {

    @Query(value = "select distinct publication from Publication publication left join fetch publication.favedBies left join fetch publication.likedBies left join fetch publication.taggedBies",
        countQuery = "select count(distinct publication) from Publication publication")
    Page<Publication> findAllWithEagerRelationships(Pageable pageable);

    @Query(value = "select distinct publication from Publication publication left join fetch publication.favedBies left join fetch publication.likedBies left join fetch publication.taggedBies")
    List<Publication> findAllWithEagerRelationships();

    @Query("select publication from Publication publication left join fetch publication.favedBies left join fetch publication.likedBies left join fetch publication.taggedBies where publication.id =:id")
    Optional<Publication> findOneWithEagerRelationships(@Param("id") Long id);

}
