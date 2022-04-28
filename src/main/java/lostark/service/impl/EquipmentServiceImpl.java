package lostark.service.impl;

import java.util.List;
import java.util.Optional;
import lostark.calc.model.Calc;
import lostark.domain.EffRequest;
import lostark.domain.Equipment;
import lostark.repository.EquipmentRepository;
import lostark.repository.UserRepository;
import lostark.security.SecurityUtils;
import lostark.service.EquipmentService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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

    @Autowired
    private UserRepository userRepository;

    public EquipmentServiceImpl(EquipmentRepository equipmentRepository) {
        this.equipmentRepository = equipmentRepository;
    }

    @Override
    public Equipment save(Equipment equipment) {
        log.debug("Request to save Equipment : {}", equipment);
        equipment.setUser(SecurityUtils.getCurrentUserLogin().flatMap(userRepository::findOneByLogin).get());
        return equipmentRepository.save(equipment);
    }

    @Override
    public Equipment update(Equipment equipment) {
        log.debug("Request to save Equipment : {}", equipment);
        return this.save(equipment);
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
            .map(this::save);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Equipment> findAll() {
        log.debug("Request to get all Equipment");
        return equipmentRepository.findByUserIsCurrentUser();
    }

    public Page<Equipment> findAllWithEagerRelationships(Pageable pageable) {
        return equipmentRepository.findAllWithEagerRelationships(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Equipment> findOne(Long id) {
        log.debug("Request to get Equipment : {}", id);
        return equipmentRepository.findOneWithEagerRelationships(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Equipment : {}", id);
        equipmentRepository.deleteById(id);
    }

    public Equipment effCalc(EffRequest effRequest) {
        Equipment equipment = findOne(effRequest.getEqid()).get();
        Double amountDiff = Calc.compareCost(
            equipment,
            effRequest.getBasePercent(),
            effRequest.getAdditionPercentPerFail(),
            effRequest.getFailLimit(),
            effRequest.getMaxPercentAfterMats(),
            effRequest.getFusionMat1Amount(),
            effRequest.getFusionMat2Amount(),
            effRequest.getFusionMat3Amount()
        );
        equipment.setAmountDiff(amountDiff);
        return this.save(equipment);
    }
}
