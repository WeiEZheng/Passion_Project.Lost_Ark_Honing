package com.mycompany.myapp.service;

import com.mycompany.myapp.domain.Charac;
import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link Charac}.
 */
public interface CharacService {
    /**
     * Save a charac.
     *
     * @param charac the entity to save.
     * @return the persisted entity.
     */
    Charac save(Charac charac);

    /**
     * Updates a charac.
     *
     * @param charac the entity to update.
     * @return the persisted entity.
     */
    Charac update(Charac charac);

    /**
     * Partially updates a charac.
     *
     * @param charac the entity to update partially.
     * @return the persisted entity.
     */
    Optional<Charac> partialUpdate(Charac charac);

    /**
     * Get all the characs.
     *
     * @return the list of entities.
     */
    List<Charac> findAll();

    /**
     * Get the "id" charac.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<Charac> findOne(Long id);

    /**
     * Delete the "id" charac.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
