package lostark.service;

import java.util.List;
import java.util.Optional;
import lostark.domain.Equipment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link Equipment}.
 */
public interface EquipmentService {
    /**
     * Save a equipment.
     *
     * @param equipment the entity to save.
     * @return the persisted entity.
     */
    Equipment save(Equipment equipment);

    /**
     * Updates a equipment.
     *
     * @param equipment the entity to update.
     * @return the persisted entity.
     */
    Equipment update(Equipment equipment);

    /**
     * Partially updates a equipment.
     *
     * @param equipment the entity to update partially.
     * @return the persisted entity.
     */
    Optional<Equipment> partialUpdate(Equipment equipment);

    /**
     * Get all the equipment.
     *
     * @return the list of entities.
     */
    List<Equipment> findAll();

    /**
     * Get all the equipment with eager load of many-to-many relationships.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<Equipment> findAllWithEagerRelationships(Pageable pageable);

    /**
     * Get the "id" equipment.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<Equipment> findOne(Long id);

    /**
     * Delete the "id" equipment.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
