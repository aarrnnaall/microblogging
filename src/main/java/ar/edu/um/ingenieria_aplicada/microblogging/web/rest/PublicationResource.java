package ar.edu.um.ingenieria_aplicada.microblogging.web.rest;

import com.codahale.metrics.annotation.Timed;
import ar.edu.um.ingenieria_aplicada.microblogging.domain.Publication;
import ar.edu.um.ingenieria_aplicada.microblogging.repository.PublicationRepository;
import ar.edu.um.ingenieria_aplicada.microblogging.web.rest.errors.BadRequestAlertException;
import ar.edu.um.ingenieria_aplicada.microblogging.web.rest.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing Publication.
 */
@RestController
@RequestMapping("/api")
public class PublicationResource {

    private final Logger log = LoggerFactory.getLogger(PublicationResource.class);

    private static final String ENTITY_NAME = "publication";

    private final PublicationRepository publicationRepository;

    public PublicationResource(PublicationRepository publicationRepository) {
        this.publicationRepository = publicationRepository;
    }

    /**
     * POST  /publications : Create a new publication.
     *
     * @param publication the publication to create
     * @return the ResponseEntity with status 201 (Created) and with body the new publication, or with status 400 (Bad Request) if the publication has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/publications")
    @Timed
    public ResponseEntity<Publication> createPublication(@RequestBody Publication publication) throws URISyntaxException {
        log.debug("REST request to save Publication : {}", publication);
        if (publication.getId() != null) {
            throw new BadRequestAlertException("A new publication cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Publication result = publicationRepository.save(publication);
        return ResponseEntity.created(new URI("/api/publications/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /publications : Updates an existing publication.
     *
     * @param publication the publication to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated publication,
     * or with status 400 (Bad Request) if the publication is not valid,
     * or with status 500 (Internal Server Error) if the publication couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/publications")
    @Timed
    public ResponseEntity<Publication> updatePublication(@RequestBody Publication publication) throws URISyntaxException {
        log.debug("REST request to update Publication : {}", publication);
        if (publication.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Publication result = publicationRepository.save(publication);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, publication.getId().toString()))
            .body(result);
    }

    /**
     * GET  /publications : get all the publications.
     *
     * @param eagerload flag to eager load entities from relationships (This is applicable for many-to-many)
     * @return the ResponseEntity with status 200 (OK) and the list of publications in body
     */
    @GetMapping("/publications")
    @Timed
    public List<Publication> getAllPublications(@RequestParam(required = false, defaultValue = "false") boolean eagerload) {
        log.debug("REST request to get all Publications");
        return publicationRepository.findAllWithEagerRelationships();
    }

    /**
     * GET  /publications/:id : get the "id" publication.
     *
     * @param id the id of the publication to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the publication, or with status 404 (Not Found)
     */
    @GetMapping("/publications/{id}")
    @Timed
    public ResponseEntity<Publication> getPublication(@PathVariable Long id) {
        log.debug("REST request to get Publication : {}", id);
        Optional<Publication> publication = publicationRepository.findOneWithEagerRelationships(id);
        return ResponseUtil.wrapOrNotFound(publication);
    }

    /**
     * DELETE  /publications/:id : delete the "id" publication.
     *
     * @param id the id of the publication to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/publications/{id}")
    @Timed
    public ResponseEntity<Void> deletePublication(@PathVariable Long id) {
        log.debug("REST request to delete Publication : {}", id);

        publicationRepository.deleteById(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
