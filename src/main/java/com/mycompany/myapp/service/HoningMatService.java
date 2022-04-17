package com.mycompany.myapp.service;

import com.mycompany.myapp.domain.HoningMat;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link HoningMat}.
 */
public interface HoningMatService {
    /**
     * Save a honingMat.
     *
     * @param honingMat the entity to save.
     * @return the persisted entity.
     */
    HoningMat save(HoningMat honingMat);

    /**
     * Updates a honingMat.
     *
     * @param honingMat the entity to update.
     * @return the persisted entity.
     */
    HoningMat update(HoningMat honingMat);

    /**
     * Partially updates a honingMat.
     *
     * @param honingMat the entity to update partially.
     * @return the persisted entity.
     */
    Optional<HoningMat> partialUpdate(HoningMat honingMat);

    /**
     * Get all the honingMats.
     *
     * @return the list of entities.
     */
    List<HoningMat> findAll();

    /**
     * Get all the honingMats with eager load of many-to-many relationships.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<HoningMat> findAllWithEagerRelationships(Pageable pageable);

    /**
     * Get the "id" honingMat.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<HoningMat> findOne(Long id);

    /**
     * Delete the "id" honingMat.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
