package lostark.service;

import java.util.List;
import java.util.Optional;
import lostark.domain.EffRequest;

/**
 * Service Interface for managing {@link EffRequest}.
 */
public interface EffRequestService {
    /**
     * Save a effRequest.
     *
     * @param effRequest the entity to save.
     * @return the persisted entity.
     */
    EffRequest save(EffRequest effRequest);

    /**
     * Updates a effRequest.
     *
     * @param effRequest the entity to update.
     * @return the persisted entity.
     */
    EffRequest update(EffRequest effRequest);

    /**
     * Partially updates a effRequest.
     *
     * @param effRequest the entity to update partially.
     * @return the persisted entity.
     */
    Optional<EffRequest> partialUpdate(EffRequest effRequest);

    /**
     * Get all the effRequests.
     *
     * @return the list of entities.
     */
    List<EffRequest> findAll();

    /**
     * Get the "id" effRequest.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<EffRequest> findOne(Long id);

    /**
     * Delete the "id" effRequest.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
