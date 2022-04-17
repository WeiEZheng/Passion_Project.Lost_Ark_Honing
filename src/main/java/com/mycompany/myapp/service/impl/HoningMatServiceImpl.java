package com.mycompany.myapp.service.impl;

import com.mycompany.myapp.domain.HoningMat;
import com.mycompany.myapp.repository.HoningMatRepository;
import com.mycompany.myapp.service.HoningMatService;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link HoningMat}.
 */
@Service
@Transactional
public class HoningMatServiceImpl implements HoningMatService {

    private final Logger log = LoggerFactory.getLogger(HoningMatServiceImpl.class);

    private final HoningMatRepository honingMatRepository;

    public HoningMatServiceImpl(HoningMatRepository honingMatRepository) {
        this.honingMatRepository = honingMatRepository;
    }

    @Override
    public HoningMat save(HoningMat honingMat) {
        log.debug("Request to save HoningMat : {}", honingMat);
        return honingMatRepository.save(honingMat);
    }

    @Override
    public HoningMat update(HoningMat honingMat) {
        log.debug("Request to save HoningMat : {}", honingMat);
        return honingMatRepository.save(honingMat);
    }

    @Override
    public Optional<HoningMat> partialUpdate(HoningMat honingMat) {
        log.debug("Request to partially update HoningMat : {}", honingMat);

        return honingMatRepository
            .findById(honingMat.getId())
            .map(existingHoningMat -> {
                if (honingMat.getTier() != null) {
                    existingHoningMat.setTier(honingMat.getTier());
                }
                if (honingMat.getLevel() != null) {
                    existingHoningMat.setLevel(honingMat.getLevel());
                }
                if (honingMat.getShardType() != null) {
                    existingHoningMat.setShardType(honingMat.getShardType());
                }
                if (honingMat.getEquipType() != null) {
                    existingHoningMat.setEquipType(honingMat.getEquipType());
                }
                if (honingMat.getFusionMaterialName1() != null) {
                    existingHoningMat.setFusionMaterialName1(honingMat.getFusionMaterialName1());
                }
                if (honingMat.getFusionMaterialNum1() != null) {
                    existingHoningMat.setFusionMaterialNum1(honingMat.getFusionMaterialNum1());
                }
                if (honingMat.getFusionMaterialName2() != null) {
                    existingHoningMat.setFusionMaterialName2(honingMat.getFusionMaterialName2());
                }
                if (honingMat.getFusionMaterialNum2() != null) {
                    existingHoningMat.setFusionMaterialNum2(honingMat.getFusionMaterialNum2());
                }
                if (honingMat.getFusionMaterialName3() != null) {
                    existingHoningMat.setFusionMaterialName3(honingMat.getFusionMaterialName3());
                }
                if (honingMat.getFusionMaterialNum3() != null) {
                    existingHoningMat.setFusionMaterialNum3(honingMat.getFusionMaterialNum3());
                }

                return existingHoningMat;
            })
            .map(honingMatRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public List<HoningMat> findAll() {
        log.debug("Request to get all HoningMats");
        return honingMatRepository.findAllWithEagerRelationships();
    }

    public Page<HoningMat> findAllWithEagerRelationships(Pageable pageable) {
        return honingMatRepository.findAllWithEagerRelationships(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<HoningMat> findOne(Long id) {
        log.debug("Request to get HoningMat : {}", id);
        return honingMatRepository.findOneWithEagerRelationships(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete HoningMat : {}", id);
        honingMatRepository.deleteById(id);
    }
}
