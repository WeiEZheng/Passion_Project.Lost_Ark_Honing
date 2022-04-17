package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.HoningMat;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the HoningMat entity.
 */
@Repository
public interface HoningMatRepository extends HoningMatRepositoryWithBagRelationships, JpaRepository<HoningMat, Long> {
    default Optional<HoningMat> findOneWithEagerRelationships(Long id) {
        return this.fetchBagRelationships(this.findById(id));
    }

    default List<HoningMat> findAllWithEagerRelationships() {
        return this.fetchBagRelationships(this.findAll());
    }

    default Page<HoningMat> findAllWithEagerRelationships(Pageable pageable) {
        return this.fetchBagRelationships(this.findAll(pageable));
    }
}
