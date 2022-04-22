package lostark.lostarkcalc.service.impl;

import java.util.List;
import java.util.Optional;
import lostark.lostarkcalc.domain.Characters;
import lostark.lostarkcalc.domain.User;
import lostark.lostarkcalc.repository.CharactersRepository;
import lostark.lostarkcalc.repository.UserRepository;
import lostark.lostarkcalc.security.SecurityUtils;
import lostark.lostarkcalc.service.CharactersService;
import lostark.lostarkcalc.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Characters}.
 */
@Service
@Transactional
public class CharactersServiceImpl implements CharactersService {

    private final Logger log = LoggerFactory.getLogger(CharactersServiceImpl.class);

    private final CharactersRepository charactersRepository;

    @Autowired
    private UserRepository userRepository;

    public CharactersServiceImpl(CharactersRepository charactersRepository) {
        this.charactersRepository = charactersRepository;
    }

    @Override
    public Characters save(Characters characters) {
        log.debug("Request to save Characters : {}", characters);
        characters.setBelongTo(SecurityUtils.getCurrentUserLogin().flatMap(userRepository::findOneByLogin).get());
        return charactersRepository.save(characters);
    }

    @Override
    public Characters update(Characters characters) {
        log.debug("Request to save Characters : {}", characters);
        return this.save(characters);
    }

    @Override
    public Optional<Characters> partialUpdate(Characters characters) {
        log.debug("Request to partially update Characters : {}", characters);

        return charactersRepository
            .findById(characters.getId())
            .map(existingCharacters -> {
                if (characters.getName() != null) {
                    existingCharacters.setName(characters.getName());
                }
                if (characters.getAdvClass() != null) {
                    existingCharacters.setAdvClass(characters.getAdvClass());
                }
                if (characters.getServer() != null) {
                    existingCharacters.setServer(characters.getServer());
                }
                return existingCharacters;
            })
            .map(this::save);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Characters> findAll() {
        log.debug("Request to get all Characters");
        return charactersRepository.findByBelongToIsCurrentUser();
    }

    public Page<Characters> findAllWithEagerRelationships(Pageable pageable) {
        return charactersRepository.findAllWithEagerRelationships(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Characters> findOne(Long id) {
        log.debug("Request to get Characters : {}", id);
        return charactersRepository.findOneWithEagerRelationships(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Characters : {}", id);
        charactersRepository.deleteById(id);
    }
}
