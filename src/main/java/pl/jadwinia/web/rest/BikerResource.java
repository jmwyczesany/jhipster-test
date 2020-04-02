package pl.jadwinia.web.rest;

import pl.jadwinia.domain.Biker;
import pl.jadwinia.repository.BikerRepository;
import pl.jadwinia.web.rest.errors.BadRequestAlertException;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link pl.jadwinia.domain.Biker}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class BikerResource {

    private final Logger log = LoggerFactory.getLogger(BikerResource.class);

    private static final String ENTITY_NAME = "biker";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final BikerRepository bikerRepository;

    public BikerResource(BikerRepository bikerRepository) {
        this.bikerRepository = bikerRepository;
    }

    /**
     * {@code POST  /bikers} : Create a new biker.
     *
     * @param biker the biker to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new biker, or with status {@code 400 (Bad Request)} if the biker has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/bikers")
    public ResponseEntity<Biker> createBiker(@RequestBody Biker biker) throws URISyntaxException {
        log.debug("REST request to save Biker : {}", biker);
        if (biker.getId() != null) {
            throw new BadRequestAlertException("A new biker cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Biker result = bikerRepository.save(biker);
        return ResponseEntity.created(new URI("/api/bikers/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /bikers} : Updates an existing biker.
     *
     * @param biker the biker to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated biker,
     * or with status {@code 400 (Bad Request)} if the biker is not valid,
     * or with status {@code 500 (Internal Server Error)} if the biker couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/bikers")
    public ResponseEntity<Biker> updateBiker(@RequestBody Biker biker) throws URISyntaxException {
        log.debug("REST request to update Biker : {}", biker);
        if (biker.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Biker result = bikerRepository.save(biker);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, biker.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /bikers} : get all the bikers.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of bikers in body.
     */
    @GetMapping("/bikers")
    public List<Biker> getAllBikers() {
        log.debug("REST request to get all Bikers");
        return bikerRepository.findAll();
    }

    /**
     * {@code GET  /bikers/:id} : get the "id" biker.
     *
     * @param id the id of the biker to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the biker, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/bikers/{id}")
    public ResponseEntity<Biker> getBiker(@PathVariable Long id) {
        log.debug("REST request to get Biker : {}", id);
        Optional<Biker> biker = bikerRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(biker);
    }

    /**
     * {@code DELETE  /bikers/:id} : delete the "id" biker.
     *
     * @param id the id of the biker to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/bikers/{id}")
    public ResponseEntity<Void> deleteBiker(@PathVariable Long id) {
        log.debug("REST request to delete Biker : {}", id);
        bikerRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}
