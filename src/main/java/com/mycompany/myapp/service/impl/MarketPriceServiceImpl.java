package com.mycompany.myapp.service.impl;

import com.mycompany.myapp.domain.MarketPrice;
import com.mycompany.myapp.repository.MarketPriceRepository;
import com.mycompany.myapp.service.MarketPriceService;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link MarketPrice}.
 */
@Service
@Transactional
public class MarketPriceServiceImpl implements MarketPriceService {

    private final Logger log = LoggerFactory.getLogger(MarketPriceServiceImpl.class);

    private final MarketPriceRepository marketPriceRepository;

    public MarketPriceServiceImpl(MarketPriceRepository marketPriceRepository) {
        this.marketPriceRepository = marketPriceRepository;
    }

    @Override
    public MarketPrice save(MarketPrice marketPrice) {
        log.debug("Request to save MarketPrice : {}", marketPrice);
        return marketPriceRepository.save(marketPrice);
    }

    @Override
    public MarketPrice update(MarketPrice marketPrice) {
        log.debug("Request to save MarketPrice : {}", marketPrice);
        return marketPriceRepository.save(marketPrice);
    }

    @Override
    public Optional<MarketPrice> partialUpdate(MarketPrice marketPrice) {
        log.debug("Request to partially update MarketPrice : {}", marketPrice);

        return marketPriceRepository
            .findById(marketPrice.getId())
            .map(existingMarketPrice -> {
                if (marketPrice.getItemName() != null) {
                    existingMarketPrice.setItemName(marketPrice.getItemName());
                }
                if (marketPrice.getItemPricePerStack() != null) {
                    existingMarketPrice.setItemPricePerStack(marketPrice.getItemPricePerStack());
                }
                if (marketPrice.getNumberPerStack() != null) {
                    existingMarketPrice.setNumberPerStack(marketPrice.getNumberPerStack());
                }
                if (marketPrice.getTimeUpdated() != null) {
                    existingMarketPrice.setTimeUpdated(marketPrice.getTimeUpdated());
                }

                return existingMarketPrice;
            })
            .map(marketPriceRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public List<MarketPrice> findAll() {
        log.debug("Request to get all MarketPrices");
        return marketPriceRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<MarketPrice> findOne(Long id) {
        log.debug("Request to get MarketPrice : {}", id);
        return marketPriceRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete MarketPrice : {}", id);
        marketPriceRepository.deleteById(id);
    }
}
