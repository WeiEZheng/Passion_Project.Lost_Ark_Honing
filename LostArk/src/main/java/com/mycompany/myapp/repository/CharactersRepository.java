package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.Characters;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the Characters entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CharactersRepository extends JpaRepository<Characters, Long> {}
