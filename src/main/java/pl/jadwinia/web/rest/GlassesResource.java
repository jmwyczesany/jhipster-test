package pl.jadwinia.web.rest;

import pl.jadwinia.domain.Glasses;
import pl.jadwinia.repository.GlassesRepository;
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
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

/**
 * REST controller for managing {@link pl.jadwinia.domain.Glasses}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class GlassesResource {

    private final Logger log = LoggerFactory.getLogger(GlassesResource.class);

    private static final String ENTITY_NAME = "glasses";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final GlassesRepository glassesRepository;

    public GlassesResource(GlassesRepository glassesRepository) {
        this.glassesRepository = glassesRepository;
    }

    /**
     * {@code POST  /glasses} : Create a new glasses.
     *
     * @param glasses the glasses to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new glasses, or with status {@code 400 (Bad Request)} if the glasses has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/glasses")
    public ResponseEntity<Glasses> createGlasses(@RequestBody Glasses glasses) throws URISyntaxException {
        log.debug("REST request to save Glasses : {}", glasses);
        if (glasses.getId() != null) {
            throw new BadRequestAlertException("A new glasses cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Glasses result = glassesRepository.save(glasses);
        return ResponseEntity.created(new URI("/api/glasses/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /glasses} : Updates an existing glasses.
     *
     * @param glasses the glasses to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated glasses,
     * or with status {@code 400 (Bad Request)} if the glasses is not valid,
     * or with status {@code 500 (Internal Server Error)} if the glasses couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/glasses")
    public ResponseEntity<Glasses> updateGlasses(@RequestBody Glasses glasses) throws URISyntaxException {
        log.debug("REST request to update Glasses : {}", glasses);
        if (glasses.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Glasses result = glassesRepository.save(glasses);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, glasses.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /glasses} : get all the glasses.
     *
     * @param filter the filter of the request.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of glasses in body.
     */
    @GetMapping("/glasses")
    public List<Glasses> getAllGlasses(@RequestParam(required = false) String filter) {
        if ("person-is-null".equals(filter)) {
            log.debug("REST request to get all Glassess where person is null");
            return StreamSupport
                .stream(glassesRepository.findAll().spliterator(), false)
                .filter(glasses -> glasses.getPerson() == null)
                .collect(Collectors.toList());
        }
        log.debug("REST request to get all Glasses");
        return glassesRepository.findAll();
    }

    /**
     * {@code GET  /glasses/:id} : get the "id" glasses.
     *
     * @param id the id of the glasses to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the glasses, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/glasses/{id}")
    public ResponseEntity<Glasses> getGlasses(@PathVariable Long id) {
        log.debug("REST request to get Glasses : {}", id);
        Optional<Glasses> glasses = glassesRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(glasses);
    }

    /**
     * {@code DELETE  /glasses/:id} : delete the "id" glasses.
     *
     * @param id the id of the glasses to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/glasses/{id}")
    public ResponseEntity<Void> deleteGlasses(@PathVariable Long id) {
        log.debug("REST request to delete Glasses : {}", id);
        glassesRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}
