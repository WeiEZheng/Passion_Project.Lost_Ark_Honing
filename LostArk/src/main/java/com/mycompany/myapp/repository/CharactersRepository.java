package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.Characters;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the Characters entity.
 */
@Repository
public interface CharactersRepository extends JpaRepository<Characters, Long> {
    @Query("select characters from Characters characters where characters.user.login = ?#{principal.username}")
    List<Characters> findByUserIsCurrentUser();

    default Optional<Characters> findOneWithEagerRelationships(Long id) {
        return this.findOneWithToOneRelationships(id);
    }

    default List<Characters> findAllWithEagerRelationships() {
        return this.findAllWithToOneRelationships();
    }

    default Page<Characters> findAllWithEagerRelationships(Pageable pageable) {
        return this.findAllWithToOneRelationships(pageable);
    }

    @Query(
        value = "select distinct characters from Characters characters left join fetch characters.user",
        countQuery = "select count(distinct characters) from Characters characters"
    )
    Page<Characters> findAllWithToOneRelationships(Pageable pageable);

    @Query("select distinct characters from Characters characters left join fetch characters.user")
    List<Characters> findAllWithToOneRelationships();

    @Query("select characters from Characters characters left join fetch characters.user where characters.id =:id")
    Optional<Characters> findOneWithToOneRelationships(@Param("id") Long id);
}
