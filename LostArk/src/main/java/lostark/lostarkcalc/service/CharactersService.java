package lostark.lostarkcalc.service;

import java.util.List;
import java.util.Optional;
import lostark.lostarkcalc.domain.Characters;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link Characters}.
 */
public interface CharactersService {
    /**
     * Save a characters.
     *
     * @param characters the entity to save.
     * @return the persisted entity.
     */
    Characters save(Characters characters);

    /**
     * Updates a characters.
     *
     * @param characters the entity to update.
     * @return the persisted entity.
     */
    Characters update(Characters characters);

    /**
     * Partially updates a characters.
     *
     * @param characters the entity to update partially.
     * @return the persisted entity.
     */
    Optional<Characters> partialUpdate(Characters characters);

    /**
     * Get all the characters.
     *
     * @return the list of entities.
     */
    List<Characters> findAll();

    /**
     * Get all the characters with eager load of many-to-many relationships.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<Characters> findAllWithEagerRelationships(Pageable pageable);

    /**
     * Get the "id" characters.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<Characters> findOne(Long id);

    /**
     * Delete the "id" characters.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
