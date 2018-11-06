package ar.edu.um.ingenieria_aplicada.microblogging.repository;

import ar.edu.um.ingenieria_aplicada.microblogging.domain.Publisher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Spring Data  repository for the Publisher entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PublisherRepository extends JpaRepository<Publisher, Long> {

    @Query(value = "select distinct publisher from Publisher publisher left join fetch publisher.mentionedBies left join fetch publisher.followedBies",
        countQuery = "select count(distinct publisher) from Publisher publisher")
    Page<Publisher> findAllWithEagerRelationships(Pageable pageable);

    @Query(value = "select distinct publisher from Publisher publisher left join fetch publisher.mentionedBies left join fetch publisher.followedBies")
    List<Publisher> findAllWithEagerRelationships();

    @Query("select publisher from Publisher publisher left join fetch publisher.mentionedBies left join fetch publisher.followedBies where publisher.id =:id")
    Optional<Publisher> findOneWithEagerRelationships(@Param("id") Long id);

}
