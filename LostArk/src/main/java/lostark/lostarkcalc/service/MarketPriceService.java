package lostark.lostarkcalc.service;

import java.util.List;
import java.util.Optional;
import lostark.lostarkcalc.domain.MarketPrice;

/**
 * Service Interface for managing {@link MarketPrice}.
 */
public interface MarketPriceService {
    /**
     * Save a marketPrice.
     *
     * @param marketPrice the entity to save.
     * @return the persisted entity.
     */
    MarketPrice save(MarketPrice marketPrice);

    /**
     * Updates a marketPrice.
     *
     * @param marketPrice the entity to update.
     * @return the persisted entity.
     */
    MarketPrice update(MarketPrice marketPrice);

    /**
     * Partially updates a marketPrice.
     *
     * @param marketPrice the entity to update partially.
     * @return the persisted entity.
     */
    Optional<MarketPrice> partialUpdate(MarketPrice marketPrice);

    /**
     * Get all the marketPrices.
     *
     * @return the list of entities.
     */
    List<MarketPrice> findAll();

    /**
     * Get the "id" marketPrice.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<MarketPrice> findOne(Long id);

    /**
     * Delete the "id" marketPrice.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
