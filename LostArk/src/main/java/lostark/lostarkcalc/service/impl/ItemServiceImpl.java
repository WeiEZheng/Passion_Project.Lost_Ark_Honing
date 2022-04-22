package lostark.lostarkcalc.service.impl;

import java.util.List;
import java.util.Optional;
import lostark.lostarkcalc.domain.Item;
import lostark.lostarkcalc.domain.enumeration.MaterialName;
import lostark.lostarkcalc.repository.ItemRepository;
import lostark.lostarkcalc.service.ItemService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Item}.
 */
@Service
@Transactional
public class ItemServiceImpl implements ItemService {

    private final Logger log = LoggerFactory.getLogger(ItemServiceImpl.class);

    private final ItemRepository itemRepository;

    public ItemServiceImpl(ItemRepository itemRepository) {
        this.itemRepository = itemRepository;
    }

    @Override
    public Item save(Item item) {
        log.debug("Request to save Item : {}", item);
        return itemRepository.save(item);
    }

    @Override
    public Item update(Item item) {
        log.debug("Request to save Item : {}", item);
        return itemRepository.save(item);
    }

    @Override
    public Optional<Item> partialUpdate(Item item) {
        log.debug("Request to partially update Item : {}", item);

        return itemRepository
            .findById(item.getId())
            .map(existingItem -> {
                if (item.getItemName() != null) {
                    existingItem.setItemName(item.getItemName());
                }

                return existingItem;
            })
            .map(itemRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Item> findAll() {
        log.debug("Request to get all Items");
        return itemRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Item> findOne(Long id) {
        log.debug("Request to get Item : {}", id);
        return itemRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Item : {}", id);
        itemRepository.deleteById(id);
    }

    public Optional<Item> findOneByName(MaterialName materialName) {
        log.debug("Request to get Item : {}", materialName.getValue());
        return itemRepository.findOneByName(materialName);
    }
}
