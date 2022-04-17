package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.HoningMat;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;

public interface HoningMatRepositoryWithBagRelationships {
    Optional<HoningMat> fetchBagRelationships(Optional<HoningMat> honingMat);

    List<HoningMat> fetchBagRelationships(List<HoningMat> honingMats);

    Page<HoningMat> fetchBagRelationships(Page<HoningMat> honingMats);
}
