package com.mycompany.myapp.service.impl;

import com.mycompany.myapp.domain.Equipment;
import com.mycompany.myapp.repository.EquipmentRepository;
import com.mycompany.myapp.service.EquipmentService;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Equipment}.
 */
@Service
@Transactional
public class EquipmentServiceImpl implements EquipmentService {

    private final Logger log = LoggerFactory.getLogger(EquipmentServiceImpl.class);

    private final EquipmentRepository equipmentRepository;

    public EquipmentServiceImpl(EquipmentRepository equipmentRepository) {
        this.equipmentRepository = equipmentRepository;
    }

    @Override
    public Equipment save(Equipment equipment) {
        log.debug("Request to save Equipment : {}", equipment);
        return equipmentRepository.save(equipment);
    }

    @Override
    public Equipment update(Equipment equipment) {
        log.debug("Request to save Equipment : {}", equipment);
        return equipmentRepository.save(equipment);
    }

    @Override
    public Optional<Equipment> partialUpdate(Equipment equipment) {
        log.debug("Request to partially update Equipment : {}", equipment);

        return equipmentRepository
            .findById(equipment.getId())
            .map(existingEquipment -> {
                if (equipment.getTier() != null) {
                    existingEquipment.setTier(equipment.getTier());
                }
                if (equipment.getHoningLevel() != null) {
                    existingEquipment.setHoningLevel(equipment.getHoningLevel());
                }
                if (equipment.getEquipmentType() != null) {
                    existingEquipment.setEquipmentType(equipment.getEquipmentType());
                }

                return existingEquipment;
            })
            .map(equipmentRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Equipment> findAll() {
        log.debug("Request to get all Equipment");
        return equipmentRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Equipment> findOne(Long id) {
        log.debug("Request to get Equipment : {}", id);
        return equipmentRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Equipment : {}", id);
        equipmentRepository.deleteById(id);
    }
}
