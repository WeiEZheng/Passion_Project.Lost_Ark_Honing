package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.domain.Charac;
import com.mycompany.myapp.repository.CharacRepository;
import com.mycompany.myapp.service.CharacService;
import com.mycompany.myapp.web.rest.errors.BadRequestAlertException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link com.mycompany.myapp.domain.Charac}.
 */
@RestController
@RequestMapping("/api")
public class CharacResource {

    private final Logger log = LoggerFactory.getLogger(CharacResource.class);

    private static final String ENTITY_NAME = "charac";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final CharacService characService;

    private final CharacRepository characRepository;

    public CharacResource(CharacService characService, CharacRepository characRepository) {
        this.characService = characService;
        this.characRepository = characRepository;
    }

    /**
     * {@code POST  /characs} : Create a new charac.
     *
     * @param charac the charac to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new charac, or with status {@code 400 (Bad Request)} if the charac has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/characs")
    public ResponseEntity<Charac> createCharac(@Valid @RequestBody Charac charac) throws URISyntaxException {
        log.debug("REST request to save Charac : {}", charac);
        if (charac.getId() != null) {
            throw new BadRequestAlertException("A new charac cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Charac result = characService.save(charac);
        return ResponseEntity
            .created(new URI("/api/characs/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /characs/:id} : Updates an existing charac.
     *
     * @param id the id of the charac to save.
     * @param charac the charac to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated charac,
     * or with status {@code 400 (Bad Request)} if the charac is not valid,
     * or with status {@code 500 (Internal Server Error)} if the charac couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/characs/{id}")
    public ResponseEntity<Charac> updateCharac(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody Charac charac
    ) throws URISyntaxException {
        log.debug("REST request to update Charac : {}, {}", id, charac);
        if (charac.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, charac.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!characRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Charac result = characService.update(charac);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, charac.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /characs/:id} : Partial updates given fields of an existing charac, field will ignore if it is null
     *
     * @param id the id of the charac to save.
     * @param charac the charac to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated charac,
     * or with status {@code 400 (Bad Request)} if the charac is not valid,
     * or with status {@code 404 (Not Found)} if the charac is not found,
     * or with status {@code 500 (Internal Server Error)} if the charac couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/characs/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Charac> partialUpdateCharac(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody Charac charac
    ) throws URISyntaxException {
        log.debug("REST request to partial update Charac partially : {}, {}", id, charac);
        if (charac.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, charac.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!characRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Charac> result = characService.partialUpdate(charac);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, charac.getId().toString())
        );
    }

    /**
     * {@code GET  /characs} : get all the characs.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of characs in body.
     */
    @GetMapping("/characs")
    public List<Charac> getAllCharacs() {
        log.debug("REST request to get all Characs");
        return characService.findAll();
    }

    /**
     * {@code GET  /characs/:id} : get the "id" charac.
     *
     * @param id the id of the charac to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the charac, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/characs/{id}")
    public ResponseEntity<Charac> getCharac(@PathVariable Long id) {
        log.debug("REST request to get Charac : {}", id);
        Optional<Charac> charac = characService.findOne(id);
        return ResponseUtil.wrapOrNotFound(charac);
    }

    /**
     * {@code DELETE  /characs/:id} : delete the "id" charac.
     *
     * @param id the id of the charac to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/characs/{id}")
    public ResponseEntity<Void> deleteCharac(@PathVariable Long id) {
        log.debug("REST request to delete Charac : {}", id);
        characService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
