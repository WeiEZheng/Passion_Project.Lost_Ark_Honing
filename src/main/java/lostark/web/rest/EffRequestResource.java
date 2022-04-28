package lostark.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import lostark.domain.EffRequest;
import lostark.repository.EffRequestRepository;
import lostark.service.EffRequestService;
import lostark.web.rest.errors.BadRequestAlertException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link lostark.domain.EffRequest}.
 */
@RestController
@RequestMapping("/api")
public class EffRequestResource {

    private final Logger log = LoggerFactory.getLogger(EffRequestResource.class);

    private static final String ENTITY_NAME = "effRequest";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final EffRequestService effRequestService;

    private final EffRequestRepository effRequestRepository;

    public EffRequestResource(EffRequestService effRequestService, EffRequestRepository effRequestRepository) {
        this.effRequestService = effRequestService;
        this.effRequestRepository = effRequestRepository;
    }

    /**
     * {@code POST  /eff-requests} : Create a new effRequest.
     *
     * @param effRequest the effRequest to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new effRequest, or with status {@code 400 (Bad Request)} if the effRequest has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/eff-requests")
    public ResponseEntity<EffRequest> createEffRequest(@RequestBody EffRequest effRequest) throws URISyntaxException {
        log.debug("REST request to save EffRequest : {}", effRequest);
        if (effRequest.getId() != null) {
            throw new BadRequestAlertException("A new effRequest cannot already have an ID", ENTITY_NAME, "idexists");
        }
        EffRequest result = effRequestService.save(effRequest);
        return ResponseEntity
            .created(new URI("/api/eff-requests/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /eff-requests/:id} : Updates an existing effRequest.
     *
     * @param id the id of the effRequest to save.
     * @param effRequest the effRequest to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated effRequest,
     * or with status {@code 400 (Bad Request)} if the effRequest is not valid,
     * or with status {@code 500 (Internal Server Error)} if the effRequest couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/eff-requests/{id}")
    public ResponseEntity<EffRequest> updateEffRequest(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody EffRequest effRequest
    ) throws URISyntaxException {
        log.debug("REST request to update EffRequest : {}, {}", id, effRequest);
        if (effRequest.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, effRequest.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!effRequestRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        EffRequest result = effRequestService.update(effRequest);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, effRequest.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /eff-requests/:id} : Partial updates given fields of an existing effRequest, field will ignore if it is null
     *
     * @param id the id of the effRequest to save.
     * @param effRequest the effRequest to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated effRequest,
     * or with status {@code 400 (Bad Request)} if the effRequest is not valid,
     * or with status {@code 404 (Not Found)} if the effRequest is not found,
     * or with status {@code 500 (Internal Server Error)} if the effRequest couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/eff-requests/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<EffRequest> partialUpdateEffRequest(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody EffRequest effRequest
    ) throws URISyntaxException {
        log.debug("REST request to partial update EffRequest partially : {}, {}", id, effRequest);
        if (effRequest.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, effRequest.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!effRequestRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<EffRequest> result = effRequestService.partialUpdate(effRequest);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, effRequest.getId().toString())
        );
    }

    /**
     * {@code GET  /eff-requests} : get all the effRequests.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of effRequests in body.
     */
    @GetMapping("/eff-requests")
    public List<EffRequest> getAllEffRequests() {
        log.debug("REST request to get all EffRequests");
        return effRequestService.findAll();
    }

    /**
     * {@code GET  /eff-requests/:id} : get the "id" effRequest.
     *
     * @param id the id of the effRequest to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the effRequest, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/eff-requests/{id}")
    public ResponseEntity<EffRequest> getEffRequest(@PathVariable Long id) {
        log.debug("REST request to get EffRequest : {}", id);
        Optional<EffRequest> effRequest = effRequestService.findOne(id);
        return ResponseUtil.wrapOrNotFound(effRequest);
    }

    /**
     * {@code DELETE  /eff-requests/:id} : delete the "id" effRequest.
     *
     * @param id the id of the effRequest to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/eff-requests/{id}")
    public ResponseEntity<Void> deleteEffRequest(@PathVariable Long id) {
        log.debug("REST request to delete EffRequest : {}", id);
        effRequestService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
