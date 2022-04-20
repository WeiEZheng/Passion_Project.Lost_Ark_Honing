package lostark.lostarkcalc.service;

import java.util.List;
import java.util.Optional;
import lostark.lostarkcalc.domain.Item;

/**
 * Service Interface for managing {@link Item}.
 */
public interface ItemService {
    /**
     * Save a item.
     *
     * @param item the entity to save.
     * @return the persisted entity.
     */
    Item save(Item item);

    /**
     * Updates a item.
     *
     * @param item the entity to update.
     * @return the persisted entity.
     */
    Item update(Item item);

    /**
     * Partially updates a item.
     *
     * @param item the entity to update partially.
     * @return the persisted entity.
     */
    Optional<Item> partialUpdate(Item item);

    /**
     * Get all the items.
     *
     * @return the list of entities.
     */
    List<Item> findAll();

    /**
     * Get the "id" item.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<Item> findOne(Long id);

    /**
     * Delete the "id" item.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
