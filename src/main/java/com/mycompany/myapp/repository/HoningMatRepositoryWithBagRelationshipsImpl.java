package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.HoningMat;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.hibernate.annotations.QueryHints;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

/**
 * Utility repository to load bag relationships based on https://vladmihalcea.com/hibernate-multiplebagfetchexception/
 */
public class HoningMatRepositoryWithBagRelationshipsImpl implements HoningMatRepositoryWithBagRelationships {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Optional<HoningMat> fetchBagRelationships(Optional<HoningMat> honingMat) {
        return honingMat.map(this::fetchMarketPrices);
    }

    @Override
    public Page<HoningMat> fetchBagRelationships(Page<HoningMat> honingMats) {
        return new PageImpl<>(fetchBagRelationships(honingMats.getContent()), honingMats.getPageable(), honingMats.getTotalElements());
    }

    @Override
    public List<HoningMat> fetchBagRelationships(List<HoningMat> honingMats) {
        return Optional.of(honingMats).map(this::fetchMarketPrices).orElse(Collections.emptyList());
    }

    HoningMat fetchMarketPrices(HoningMat result) {
        return entityManager
            .createQuery(
                "select honingMat from HoningMat honingMat left join fetch honingMat.marketPrices where honingMat is :honingMat",
                HoningMat.class
            )
            .setParameter("honingMat", result)
            .setHint(QueryHints.PASS_DISTINCT_THROUGH, false)
            .getSingleResult();
    }

    List<HoningMat> fetchMarketPrices(List<HoningMat> honingMats) {
        return entityManager
            .createQuery(
                "select distinct honingMat from HoningMat honingMat left join fetch honingMat.marketPrices where honingMat in :honingMats",
                HoningMat.class
            )
            .setParameter("honingMats", honingMats)
            .setHint(QueryHints.PASS_DISTINCT_THROUGH, false)
            .getResultList();
    }
}
