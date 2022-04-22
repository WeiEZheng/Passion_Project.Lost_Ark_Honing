package lostark.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import lostark.domain.Equipment;
import lostark.repository.EquipmentRepository;
import lostark.service.EquipmentService;
import lostark.web.rest.errors.BadRequestAlertException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link lostark.domain.Equipment}.
 */
@RestController
@RequestMapping("/api")
public class EquipmentResource {

    private final Logger log = LoggerFactory.getLogger(EquipmentResource.class);

    private static final String ENTITY_NAME = "equipment";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final EquipmentService equipmentService;

    private final EquipmentRepository equipmentRepository;

    public EquipmentResource(EquipmentService equipmentService, EquipmentRepository equipmentRepository) {
        this.equipmentService = equipmentService;
        this.equipmentRepository = equipmentRepository;
    }

    /**
     * {@code POST  /equipment} : Create a new equipment.
     *
     * @param equipment the equipment to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new equipment, or with status {@code 400 (Bad Request)} if the equipment has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/equipment")
    public ResponseEntity<Equipment> createEquipment(@Valid @RequestBody Equipment equipment) throws URISyntaxException {
        log.debug("REST request to save Equipment : {}", equipment);
        if (equipment.getId() != null) {
            throw new BadRequestAlertException("A new equipment cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Equipment result = equipmentService.save(equipment);
        return ResponseEntity
            .created(new URI("/api/equipment/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /equipment/:id} : Updates an existing equipment.
     *
     * @param id the id of the equipment to save.
     * @param equipment the equipment to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated equipment,
     * or with status {@code 400 (Bad Request)} if the equipment is not valid,
     * or with status {@code 500 (Internal Server Error)} if the equipment couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/equipment/{id}")
    public ResponseEntity<Equipment> updateEquipment(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody Equipment equipment
    ) throws URISyntaxException {
        log.debug("REST request to update Equipment : {}, {}", id, equipment);
        if (equipment.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, equipment.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!equipmentRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Equipment result = equipmentService.update(equipment);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, equipment.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /equipment/:id} : Partial updates given fields of an existing equipment, field will ignore if it is null
     *
     * @param id the id of the equipment to save.
     * @param equipment the equipment to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated equipment,
     * or with status {@code 400 (Bad Request)} if the equipment is not valid,
     * or with status {@code 404 (Not Found)} if the equipment is not found,
     * or with status {@code 500 (Internal Server Error)} if the equipment couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/equipment/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Equipment> partialUpdateEquipment(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody Equipment equipment
    ) throws URISyntaxException {
        log.debug("REST request to partial update Equipment partially : {}, {}", id, equipment);
        if (equipment.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, equipment.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!equipmentRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Equipment> result = equipmentService.partialUpdate(equipment);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, equipment.getId().toString())
        );
    }

    /**
     * {@code GET  /equipment} : get all the equipment.
     *
     * @param eagerload flag to eager load entities from relationships (This is applicable for many-to-many).
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of equipment in body.
     */
    @GetMapping("/equipment")
    public List<Equipment> getAllEquipment(@RequestParam(required = false, defaultValue = "false") boolean eagerload) {
        log.debug("REST request to get all Equipment");
        return equipmentService.findAll();
    }

    /**
     * {@code GET  /equipment/:id} : get the "id" equipment.
     *
     * @param id the id of the equipment to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the equipment, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/equipment/{id}")
    public ResponseEntity<Equipment> getEquipment(@PathVariable Long id) {
        log.debug("REST request to get Equipment : {}", id);
        Optional<Equipment> equipment = equipmentService.findOne(id);
        return ResponseUtil.wrapOrNotFound(equipment);
    }

    /**
     * {@code DELETE  /equipment/:id} : delete the "id" equipment.
     *
     * @param id the id of the equipment to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/equipment/{id}")
    public ResponseEntity<Void> deleteEquipment(@PathVariable Long id) {
        log.debug("REST request to delete Equipment : {}", id);
        equipmentService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
