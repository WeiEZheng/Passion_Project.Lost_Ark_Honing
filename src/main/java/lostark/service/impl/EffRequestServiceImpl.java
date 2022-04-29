package lostark.service.impl;

import java.util.List;
import java.util.Optional;
import lostark.calc.model.Calc;
import lostark.domain.EffRequest;
import lostark.domain.Equipment;
import lostark.repository.EffRequestRepository;
import lostark.service.EffRequestService;
import lostark.service.EquipmentService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link EffRequest}.
 */
@Service
@Transactional
public class EffRequestServiceImpl implements EffRequestService {

    private final Logger log = LoggerFactory.getLogger(EffRequestServiceImpl.class);

    private final EffRequestRepository effRequestRepository;

    @Autowired
    EquipmentService equipmentService;

    public EffRequestServiceImpl(EffRequestRepository effRequestRepository) {
        this.effRequestRepository = effRequestRepository;
    }

    @Override
    public EffRequest save(EffRequest effRequest) {
        log.debug("Request to save EffRequest : {}", effRequest);
        Equipment equipment = equipmentService.findOne(effRequest.getId()).get();
        Double amountDiff = Calc
            .getInstance()
            .compareCost(
                equipment,
                effRequest.getBasePercent(),
                effRequest.getAdditionPercentPerFail(),
                effRequest.getFailLimit(),
                effRequest.getMaxPercentAfterMats(),
                effRequest.getFusionMat1Amount(),
                effRequest.getFusionMat2Amount(),
                effRequest.getFusionMat3Amount()
            );
        return this.save(effRequest);
    }

    @Override
    public EffRequest update(EffRequest effRequest) {
        log.debug("Request to save EffRequest : {}", effRequest);
        return this.save(effRequest);
    }

    @Override
    public Optional<EffRequest> partialUpdate(EffRequest effRequest) {
        log.debug("Request to partially update EffRequest : {}", effRequest);

        return effRequestRepository
            .findById(effRequest.getId())
            .map(existingEffRequest -> {
                if (effRequest.getBasePercent() != null) {
                    existingEffRequest.setBasePercent(effRequest.getBasePercent());
                }
                if (effRequest.getAdditionPercentPerFail() != null) {
                    existingEffRequest.setAdditionPercentPerFail(effRequest.getAdditionPercentPerFail());
                }
                if (effRequest.getMaxPercentAfterMats() != null) {
                    existingEffRequest.setMaxPercentAfterMats(effRequest.getMaxPercentAfterMats());
                }
                if (effRequest.getFusionMat1Amount() != null) {
                    existingEffRequest.setFusionMat1Amount(effRequest.getFusionMat1Amount());
                }
                if (effRequest.getFusionMat2Amount() != null) {
                    existingEffRequest.setFusionMat2Amount(effRequest.getFusionMat2Amount());
                }
                if (effRequest.getFusionMat3Amount() != null) {
                    existingEffRequest.setFusionMat3Amount(effRequest.getFusionMat3Amount());
                }
                if (effRequest.getFailLimit() != null) {
                    existingEffRequest.setFailLimit(effRequest.getFailLimit());
                }

                return existingEffRequest;
            })
            .map(this::save);
    }

    @Override
    @Transactional(readOnly = true)
    public List<EffRequest> findAll() {
        log.debug("Request to get all EffRequests");
        return effRequestRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<EffRequest> findOne(Long id) {
        log.debug("Request to get EffRequest : {}", id);
        return effRequestRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete EffRequest : {}", id);
        effRequestRepository.deleteById(id);
    }
}
