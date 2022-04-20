package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.Charac;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the Charac entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CharacRepository extends JpaRepository<Charac, Long> {}
