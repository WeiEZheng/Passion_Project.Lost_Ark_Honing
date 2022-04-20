package com.mycompany.myapp.service.impl;

import com.mycompany.myapp.domain.Charac;
import com.mycompany.myapp.repository.CharacRepository;
import com.mycompany.myapp.service.CharacService;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Charac}.
 */
@Service
@Transactional
public class CharacServiceImpl implements CharacService {

    private final Logger log = LoggerFactory.getLogger(CharacServiceImpl.class);

    private final CharacRepository characRepository;

    public CharacServiceImpl(CharacRepository characRepository) {
        this.characRepository = characRepository;
    }

    @Override
    public Charac save(Charac charac) {
        log.debug("Request to save Charac : {}", charac);
        return characRepository.save(charac);
    }

    @Override
    public Charac update(Charac charac) {
        log.debug("Request to save Charac : {}", charac);
        return characRepository.save(charac);
    }

    @Override
    public Optional<Charac> partialUpdate(Charac charac) {
        log.debug("Request to partially update Charac : {}", charac);

        return characRepository
            .findById(charac.getId())
            .map(existingCharac -> {
                if (charac.getName() != null) {
                    existingCharac.setName(charac.getName());
                }
                if (charac.getAdvClass() != null) {
                    existingCharac.setAdvClass(charac.getAdvClass());
                }
                if (charac.getServer() != null) {
                    existingCharac.setServer(charac.getServer());
                }

                return existingCharac;
            })
            .map(characRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Charac> findAll() {
        log.debug("Request to get all Characs");
        return characRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Charac> findOne(Long id) {
        log.debug("Request to get Charac : {}", id);
        return characRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Charac : {}", id);
        characRepository.deleteById(id);
    }
}
