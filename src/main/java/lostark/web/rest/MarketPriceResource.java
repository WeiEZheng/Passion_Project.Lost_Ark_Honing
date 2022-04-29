package lostark.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import lostark.domain.MarketPrice;
import lostark.domain.enumeration.MaterialName;
import lostark.repository.MarketPriceRepository;
import lostark.service.MarketPriceService;
import lostark.web.rest.errors.BadRequestAlertException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link lostark.domain.MarketPrice}.
 */
@RestController
@RequestMapping("/api")
public class MarketPriceResource {

    private final Logger log = LoggerFactory.getLogger(MarketPriceResource.class);

    private static final String ENTITY_NAME = "marketPrice";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final MarketPriceService marketPriceService;

    private final MarketPriceRepository marketPriceRepository;

    public MarketPriceResource(MarketPriceService marketPriceService, MarketPriceRepository marketPriceRepository) {
        this.marketPriceService = marketPriceService;
        this.marketPriceRepository = marketPriceRepository;
    }

    /**
     * {@code POST  /market-prices} : Create a new marketPrice.
     *
     * @param marketPrice the marketPrice to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new marketPrice, or with status {@code 400 (Bad Request)} if the marketPrice has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/market-prices")
    public ResponseEntity<MarketPrice> createMarketPrice(@Valid @RequestBody MarketPrice marketPrice) throws URISyntaxException {
        log.debug("REST request to save MarketPrice : {}", marketPrice);
        if (marketPrice.getId() != null) {
            throw new BadRequestAlertException("A new marketPrice cannot already have an ID", ENTITY_NAME, "idexists");
        }
        MarketPrice result = marketPriceService.save(marketPrice);
        return ResponseEntity
            .created(new URI("/api/market-prices/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /market-prices/:id} : Updates an existing marketPrice.
     *
     * @param id the id of the marketPrice to save.
     * @param marketPrice the marketPrice to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated marketPrice,
     * or with status {@code 400 (Bad Request)} if the marketPrice is not valid,
     * or with status {@code 500 (Internal Server Error)} if the marketPrice couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/market-prices/{id}")
    public ResponseEntity<MarketPrice> updateMarketPrice(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody MarketPrice marketPrice
    ) throws URISyntaxException {
        log.debug("REST request to update MarketPrice : {}, {}", id, marketPrice);
        if (marketPrice.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, marketPrice.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!marketPriceRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        MarketPrice result = marketPriceService.update(marketPrice);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, marketPrice.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /market-prices/:id} : Partial updates given fields of an existing marketPrice, field will ignore if it is null
     *
     * @param id the id of the marketPrice to save.
     * @param marketPrice the marketPrice to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated marketPrice,
     * or with status {@code 400 (Bad Request)} if the marketPrice is not valid,
     * or with status {@code 404 (Not Found)} if the marketPrice is not found,
     * or with status {@code 500 (Internal Server Error)} if the marketPrice couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/market-prices/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<MarketPrice> partialUpdateMarketPrice(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody MarketPrice marketPrice
    ) throws URISyntaxException {
        log.debug("REST request to partial update MarketPrice partially : {}, {}", id, marketPrice);
        if (marketPrice.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, marketPrice.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!marketPriceRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<MarketPrice> result = marketPriceService.partialUpdate(marketPrice);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, marketPrice.getId().toString())
        );
    }

    /**
     * {@code GET  /market-prices} : get all the marketPrices.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of marketPrices in body.
     */
    @GetMapping("/market-prices")
    public List<MarketPrice> getAllMarketPrices() {
        log.debug("REST request to get all MarketPrices");
        return marketPriceService.findAll();
    }

    /**
     * {@code GET  /market-prices/:id} : get the "id" marketPrice.
     *
     * @param id the id of the marketPrice to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the marketPrice, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/market-prices/{id}")
    public ResponseEntity<MarketPrice> getMarketPrice(@PathVariable Long id) {
        log.debug("REST request to get MarketPrice : {}", id);
        Optional<MarketPrice> marketPrice = marketPriceService.findOne(id);
        return ResponseUtil.wrapOrNotFound(marketPrice);
    }

    /**
     * {@code DELETE  /market-prices/:id} : delete the "id" marketPrice.
     *
     * @param id the id of the marketPrice to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/market-prices/{id}")
    public ResponseEntity<Void> deleteMarketPrice(@PathVariable Long id) {
        log.debug("REST request to delete MarketPrice : {}", id);
        marketPriceService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }

    @GetMapping("/market-prices/:name")
    public MarketPrice getMarketPriceByName(@PathVariable MaterialName name) {
        log.debug("REST request to get all MarketPrices");
        return marketPriceService.findByItemName(name);
    }
}
