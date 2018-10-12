package ar.edu.um.ingenieria_aplicada.microblogging.web.rest;

import com.codahale.metrics.annotation.Timed;
import ar.edu.um.ingenieria_aplicada.microblogging.domain.Publicacion;
import ar.edu.um.ingenieria_aplicada.microblogging.repository.PublicacionRepository;
import ar.edu.um.ingenieria_aplicada.microblogging.web.rest.errors.BadRequestAlertException;
import ar.edu.um.ingenieria_aplicada.microblogging.web.rest.util.HeaderUtil;
import ar.edu.um.ingenieria_aplicada.microblogging.web.rest.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing Publicacion.
 */
@RestController
@RequestMapping("/api")
public class PublicacionResource {

    private final Logger log = LoggerFactory.getLogger(PublicacionResource.class);

    private static final String ENTITY_NAME = "publicacion";

    private final PublicacionRepository publicacionRepository;

    public PublicacionResource(PublicacionRepository publicacionRepository) {
        this.publicacionRepository = publicacionRepository;
    }

    /**
     * POST  /publicacions : Create a new publicacion.
     *
     * @param publicacion the publicacion to create
     * @return the ResponseEntity with status 201 (Created) and with body the new publicacion, or with status 400 (Bad Request) if the publicacion has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/publicacions")
    @Timed
    public ResponseEntity<Publicacion> createPublicacion(@RequestBody Publicacion publicacion) throws URISyntaxException {
        log.debug("REST request to save Publicacion : {}", publicacion);
        if (publicacion.getId() != null) {
            throw new BadRequestAlertException("A new publicacion cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Publicacion result = publicacionRepository.save(publicacion);
        return ResponseEntity.created(new URI("/api/publicacions/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /publicacions : Updates an existing publicacion.
     *
     * @param publicacion the publicacion to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated publicacion,
     * or with status 400 (Bad Request) if the publicacion is not valid,
     * or with status 500 (Internal Server Error) if the publicacion couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/publicacions")
    @Timed
    public ResponseEntity<Publicacion> updatePublicacion(@RequestBody Publicacion publicacion) throws URISyntaxException {
        log.debug("REST request to update Publicacion : {}", publicacion);
        if (publicacion.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Publicacion result = publicacionRepository.save(publicacion);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, publicacion.getId().toString()))
            .body(result);
    }

    /**
     * GET  /publicacions : get all the publicacions.
     *
     * @param pageable the pagination information
     * @param eagerload flag to eager load entities from relationships (This is applicable for many-to-many)
     * @return the ResponseEntity with status 200 (OK) and the list of publicacions in body
     */
    @GetMapping("/publicacions")
    @Timed
    public ResponseEntity<List<Publicacion>> getAllPublicacions(Pageable pageable, @RequestParam(required = false, defaultValue = "false") boolean eagerload) {
        log.debug("REST request to get a page of Publicacions");
        Page<Publicacion> page;
        if (eagerload) {
            page = publicacionRepository.findAllWithEagerRelationships(pageable);
        } else {
            page = publicacionRepository.findAll(pageable);
        }
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, String.format("/api/publicacions?eagerload=%b", eagerload));
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /publicacions/:id : get the "id" publicacion.
     *
     * @param id the id of the publicacion to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the publicacion, or with status 404 (Not Found)
     */
    @GetMapping("/publicacions/{id}")
    @Timed
    public ResponseEntity<Publicacion> getPublicacion(@PathVariable Long id) {
        log.debug("REST request to get Publicacion : {}", id);
        Optional<Publicacion> publicacion = publicacionRepository.findOneWithEagerRelationships(id);
        return ResponseUtil.wrapOrNotFound(publicacion);
    }

    /**
     * DELETE  /publicacions/:id : delete the "id" publicacion.
     *
     * @param id the id of the publicacion to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/publicacions/{id}")
    @Timed
    public ResponseEntity<Void> deletePublicacion(@PathVariable Long id) {
        log.debug("REST request to delete Publicacion : {}", id);

        publicacionRepository.deleteById(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
