package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.domain.HoningMat;
import com.mycompany.myapp.repository.HoningMatRepository;
import com.mycompany.myapp.service.HoningMatService;
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
 * REST controller for managing {@link com.mycompany.myapp.domain.HoningMat}.
 */
@RestController
@RequestMapping("/api")
public class HoningMatResource {

    private final Logger log = LoggerFactory.getLogger(HoningMatResource.class);

    private static final String ENTITY_NAME = "honingMat";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final HoningMatService honingMatService;

    private final HoningMatRepository honingMatRepository;

    public HoningMatResource(HoningMatService honingMatService, HoningMatRepository honingMatRepository) {
        this.honingMatService = honingMatService;
        this.honingMatRepository = honingMatRepository;
    }

    /**
     * {@code POST  /honing-mats} : Create a new honingMat.
     *
     * @param honingMat the honingMat to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new honingMat, or with status {@code 400 (Bad Request)} if the honingMat has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/honing-mats")
    public ResponseEntity<HoningMat> createHoningMat(@Valid @RequestBody HoningMat honingMat) throws URISyntaxException {
        log.debug("REST request to save HoningMat : {}", honingMat);
        if (honingMat.getId() != null) {
            throw new BadRequestAlertException("A new honingMat cannot already have an ID", ENTITY_NAME, "idexists");
        }
        HoningMat result = honingMatService.save(honingMat);
        return ResponseEntity
            .created(new URI("/api/honing-mats/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /honing-mats/:id} : Updates an existing honingMat.
     *
     * @param id the id of the honingMat to save.
     * @param honingMat the honingMat to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated honingMat,
     * or with status {@code 400 (Bad Request)} if the honingMat is not valid,
     * or with status {@code 500 (Internal Server Error)} if the honingMat couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/honing-mats/{id}")
    public ResponseEntity<HoningMat> updateHoningMat(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody HoningMat honingMat
    ) throws URISyntaxException {
        log.debug("REST request to update HoningMat : {}, {}", id, honingMat);
        if (honingMat.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, honingMat.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!honingMatRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        HoningMat result = honingMatService.update(honingMat);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, honingMat.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /honing-mats/:id} : Partial updates given fields of an existing honingMat, field will ignore if it is null
     *
     * @param id the id of the honingMat to save.
     * @param honingMat the honingMat to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated honingMat,
     * or with status {@code 400 (Bad Request)} if the honingMat is not valid,
     * or with status {@code 404 (Not Found)} if the honingMat is not found,
     * or with status {@code 500 (Internal Server Error)} if the honingMat couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/honing-mats/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<HoningMat> partialUpdateHoningMat(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody HoningMat honingMat
    ) throws URISyntaxException {
        log.debug("REST request to partial update HoningMat partially : {}, {}", id, honingMat);
        if (honingMat.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, honingMat.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!honingMatRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<HoningMat> result = honingMatService.partialUpdate(honingMat);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, honingMat.getId().toString())
        );
    }

    /**
     * {@code GET  /honing-mats} : get all the honingMats.
     *
     * @param eagerload flag to eager load entities from relationships (This is applicable for many-to-many).
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of honingMats in body.
     */
    @GetMapping("/honing-mats")
    public List<HoningMat> getAllHoningMats(@RequestParam(required = false, defaultValue = "false") boolean eagerload) {
        log.debug("REST request to get all HoningMats");
        return honingMatService.findAll();
    }

    /**
     * {@code GET  /honing-mats/:id} : get the "id" honingMat.
     *
     * @param id the id of the honingMat to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the honingMat, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/honing-mats/{id}")
    public ResponseEntity<HoningMat> getHoningMat(@PathVariable Long id) {
        log.debug("REST request to get HoningMat : {}", id);
        Optional<HoningMat> honingMat = honingMatService.findOne(id);
        return ResponseUtil.wrapOrNotFound(honingMat);
    }

    /**
     * {@code DELETE  /honing-mats/:id} : delete the "id" honingMat.
     *
     * @param id the id of the honingMat to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/honing-mats/{id}")
    public ResponseEntity<Void> deleteHoningMat(@PathVariable Long id) {
        log.debug("REST request to delete HoningMat : {}", id);
        honingMatService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
