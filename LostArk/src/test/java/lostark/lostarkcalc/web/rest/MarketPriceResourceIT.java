package lostark.lostarkcalc.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import javax.persistence.EntityManager;
import lostark.lostarkcalc.IntegrationTest;
import lostark.lostarkcalc.domain.MarketPrice;
import lostark.lostarkcalc.repository.MarketPriceRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link MarketPriceResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class MarketPriceResourceIT {

    private static final Integer DEFAULT_ITEM_PRICE_PER_STACK = 1;
    private static final Integer UPDATED_ITEM_PRICE_PER_STACK = 2;

    private static final Integer DEFAULT_NUMBER_PER_STACK = 1;
    private static final Integer UPDATED_NUMBER_PER_STACK = 2;

    private static final Instant DEFAULT_TIME_UPDATED = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_TIME_UPDATED = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String ENTITY_API_URL = "/api/market-prices";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private MarketPriceRepository marketPriceRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restMarketPriceMockMvc;

    private MarketPrice marketPrice;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static MarketPrice createEntity(EntityManager em) {
        MarketPrice marketPrice = new MarketPrice()
            .itemPricePerStack(DEFAULT_ITEM_PRICE_PER_STACK)
            .numberPerStack(DEFAULT_NUMBER_PER_STACK)
            .timeUpdated(DEFAULT_TIME_UPDATED);
        return marketPrice;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static MarketPrice createUpdatedEntity(EntityManager em) {
        MarketPrice marketPrice = new MarketPrice()
            .itemPricePerStack(UPDATED_ITEM_PRICE_PER_STACK)
            .numberPerStack(UPDATED_NUMBER_PER_STACK)
            .timeUpdated(UPDATED_TIME_UPDATED);
        return marketPrice;
    }

    @BeforeEach
    public void initTest() {
        marketPrice = createEntity(em);
    }

    @Test
    @Transactional
    void createMarketPrice() throws Exception {
        int databaseSizeBeforeCreate = marketPriceRepository.findAll().size();
        // Create the MarketPrice
        restMarketPriceMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(marketPrice)))
            .andExpect(status().isCreated());

        // Validate the MarketPrice in the database
        List<MarketPrice> marketPriceList = marketPriceRepository.findAll();
        assertThat(marketPriceList).hasSize(databaseSizeBeforeCreate + 1);
        MarketPrice testMarketPrice = marketPriceList.get(marketPriceList.size() - 1);
        assertThat(testMarketPrice.getItemPricePerStack()).isEqualTo(DEFAULT_ITEM_PRICE_PER_STACK);
        assertThat(testMarketPrice.getNumberPerStack()).isEqualTo(DEFAULT_NUMBER_PER_STACK);
        assertThat(testMarketPrice.getTimeUpdated()).isEqualTo(DEFAULT_TIME_UPDATED);
    }

    @Test
    @Transactional
    void createMarketPriceWithExistingId() throws Exception {
        // Create the MarketPrice with an existing ID
        marketPrice.setId(1L);

        int databaseSizeBeforeCreate = marketPriceRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restMarketPriceMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(marketPrice)))
            .andExpect(status().isBadRequest());

        // Validate the MarketPrice in the database
        List<MarketPrice> marketPriceList = marketPriceRepository.findAll();
        assertThat(marketPriceList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkItemPricePerStackIsRequired() throws Exception {
        int databaseSizeBeforeTest = marketPriceRepository.findAll().size();
        // set the field null
        marketPrice.setItemPricePerStack(null);

        // Create the MarketPrice, which fails.

        restMarketPriceMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(marketPrice)))
            .andExpect(status().isBadRequest());

        List<MarketPrice> marketPriceList = marketPriceRepository.findAll();
        assertThat(marketPriceList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkNumberPerStackIsRequired() throws Exception {
        int databaseSizeBeforeTest = marketPriceRepository.findAll().size();
        // set the field null
        marketPrice.setNumberPerStack(null);

        // Create the MarketPrice, which fails.

        restMarketPriceMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(marketPrice)))
            .andExpect(status().isBadRequest());

        List<MarketPrice> marketPriceList = marketPriceRepository.findAll();
        assertThat(marketPriceList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkTimeUpdatedIsRequired() throws Exception {
        int databaseSizeBeforeTest = marketPriceRepository.findAll().size();
        // set the field null
        marketPrice.setTimeUpdated(null);

        // Create the MarketPrice, which fails.

        restMarketPriceMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(marketPrice)))
            .andExpect(status().isBadRequest());

        List<MarketPrice> marketPriceList = marketPriceRepository.findAll();
        assertThat(marketPriceList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllMarketPrices() throws Exception {
        // Initialize the database
        marketPriceRepository.saveAndFlush(marketPrice);

        // Get all the marketPriceList
        restMarketPriceMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(marketPrice.getId().intValue())))
            .andExpect(jsonPath("$.[*].itemPricePerStack").value(hasItem(DEFAULT_ITEM_PRICE_PER_STACK)))
            .andExpect(jsonPath("$.[*].numberPerStack").value(hasItem(DEFAULT_NUMBER_PER_STACK)))
            .andExpect(jsonPath("$.[*].timeUpdated").value(hasItem(DEFAULT_TIME_UPDATED.toString())));
    }

    @Test
    @Transactional
    void getMarketPrice() throws Exception {
        // Initialize the database
        marketPriceRepository.saveAndFlush(marketPrice);

        // Get the marketPrice
        restMarketPriceMockMvc
            .perform(get(ENTITY_API_URL_ID, marketPrice.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(marketPrice.getId().intValue()))
            .andExpect(jsonPath("$.itemPricePerStack").value(DEFAULT_ITEM_PRICE_PER_STACK))
            .andExpect(jsonPath("$.numberPerStack").value(DEFAULT_NUMBER_PER_STACK))
            .andExpect(jsonPath("$.timeUpdated").value(DEFAULT_TIME_UPDATED.toString()));
    }

    @Test
    @Transactional
    void getNonExistingMarketPrice() throws Exception {
        // Get the marketPrice
        restMarketPriceMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewMarketPrice() throws Exception {
        // Initialize the database
        marketPriceRepository.saveAndFlush(marketPrice);

        int databaseSizeBeforeUpdate = marketPriceRepository.findAll().size();

        // Update the marketPrice
        MarketPrice updatedMarketPrice = marketPriceRepository.findById(marketPrice.getId()).get();
        // Disconnect from session so that the updates on updatedMarketPrice are not directly saved in db
        em.detach(updatedMarketPrice);
        updatedMarketPrice
            .itemPricePerStack(UPDATED_ITEM_PRICE_PER_STACK)
            .numberPerStack(UPDATED_NUMBER_PER_STACK)
            .timeUpdated(UPDATED_TIME_UPDATED);

        restMarketPriceMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedMarketPrice.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedMarketPrice))
            )
            .andExpect(status().isOk());

        // Validate the MarketPrice in the database
        List<MarketPrice> marketPriceList = marketPriceRepository.findAll();
        assertThat(marketPriceList).hasSize(databaseSizeBeforeUpdate);
        MarketPrice testMarketPrice = marketPriceList.get(marketPriceList.size() - 1);
        assertThat(testMarketPrice.getItemPricePerStack()).isEqualTo(UPDATED_ITEM_PRICE_PER_STACK);
        assertThat(testMarketPrice.getNumberPerStack()).isEqualTo(UPDATED_NUMBER_PER_STACK);
        assertThat(testMarketPrice.getTimeUpdated()).isEqualTo(UPDATED_TIME_UPDATED);
    }

    @Test
    @Transactional
    void putNonExistingMarketPrice() throws Exception {
        int databaseSizeBeforeUpdate = marketPriceRepository.findAll().size();
        marketPrice.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restMarketPriceMockMvc
            .perform(
                put(ENTITY_API_URL_ID, marketPrice.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(marketPrice))
            )
            .andExpect(status().isBadRequest());

        // Validate the MarketPrice in the database
        List<MarketPrice> marketPriceList = marketPriceRepository.findAll();
        assertThat(marketPriceList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchMarketPrice() throws Exception {
        int databaseSizeBeforeUpdate = marketPriceRepository.findAll().size();
        marketPrice.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restMarketPriceMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(marketPrice))
            )
            .andExpect(status().isBadRequest());

        // Validate the MarketPrice in the database
        List<MarketPrice> marketPriceList = marketPriceRepository.findAll();
        assertThat(marketPriceList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamMarketPrice() throws Exception {
        int databaseSizeBeforeUpdate = marketPriceRepository.findAll().size();
        marketPrice.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restMarketPriceMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(marketPrice)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the MarketPrice in the database
        List<MarketPrice> marketPriceList = marketPriceRepository.findAll();
        assertThat(marketPriceList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateMarketPriceWithPatch() throws Exception {
        // Initialize the database
        marketPriceRepository.saveAndFlush(marketPrice);

        int databaseSizeBeforeUpdate = marketPriceRepository.findAll().size();

        // Update the marketPrice using partial update
        MarketPrice partialUpdatedMarketPrice = new MarketPrice();
        partialUpdatedMarketPrice.setId(marketPrice.getId());

        restMarketPriceMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedMarketPrice.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedMarketPrice))
            )
            .andExpect(status().isOk());

        // Validate the MarketPrice in the database
        List<MarketPrice> marketPriceList = marketPriceRepository.findAll();
        assertThat(marketPriceList).hasSize(databaseSizeBeforeUpdate);
        MarketPrice testMarketPrice = marketPriceList.get(marketPriceList.size() - 1);
        assertThat(testMarketPrice.getItemPricePerStack()).isEqualTo(DEFAULT_ITEM_PRICE_PER_STACK);
        assertThat(testMarketPrice.getNumberPerStack()).isEqualTo(DEFAULT_NUMBER_PER_STACK);
        assertThat(testMarketPrice.getTimeUpdated()).isEqualTo(DEFAULT_TIME_UPDATED);
    }

    @Test
    @Transactional
    void fullUpdateMarketPriceWithPatch() throws Exception {
        // Initialize the database
        marketPriceRepository.saveAndFlush(marketPrice);

        int databaseSizeBeforeUpdate = marketPriceRepository.findAll().size();

        // Update the marketPrice using partial update
        MarketPrice partialUpdatedMarketPrice = new MarketPrice();
        partialUpdatedMarketPrice.setId(marketPrice.getId());

        partialUpdatedMarketPrice
            .itemPricePerStack(UPDATED_ITEM_PRICE_PER_STACK)
            .numberPerStack(UPDATED_NUMBER_PER_STACK)
            .timeUpdated(UPDATED_TIME_UPDATED);

        restMarketPriceMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedMarketPrice.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedMarketPrice))
            )
            .andExpect(status().isOk());

        // Validate the MarketPrice in the database
        List<MarketPrice> marketPriceList = marketPriceRepository.findAll();
        assertThat(marketPriceList).hasSize(databaseSizeBeforeUpdate);
        MarketPrice testMarketPrice = marketPriceList.get(marketPriceList.size() - 1);
        assertThat(testMarketPrice.getItemPricePerStack()).isEqualTo(UPDATED_ITEM_PRICE_PER_STACK);
        assertThat(testMarketPrice.getNumberPerStack()).isEqualTo(UPDATED_NUMBER_PER_STACK);
        assertThat(testMarketPrice.getTimeUpdated()).isEqualTo(UPDATED_TIME_UPDATED);
    }

    @Test
    @Transactional
    void patchNonExistingMarketPrice() throws Exception {
        int databaseSizeBeforeUpdate = marketPriceRepository.findAll().size();
        marketPrice.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restMarketPriceMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, marketPrice.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(marketPrice))
            )
            .andExpect(status().isBadRequest());

        // Validate the MarketPrice in the database
        List<MarketPrice> marketPriceList = marketPriceRepository.findAll();
        assertThat(marketPriceList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchMarketPrice() throws Exception {
        int databaseSizeBeforeUpdate = marketPriceRepository.findAll().size();
        marketPrice.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restMarketPriceMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(marketPrice))
            )
            .andExpect(status().isBadRequest());

        // Validate the MarketPrice in the database
        List<MarketPrice> marketPriceList = marketPriceRepository.findAll();
        assertThat(marketPriceList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamMarketPrice() throws Exception {
        int databaseSizeBeforeUpdate = marketPriceRepository.findAll().size();
        marketPrice.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restMarketPriceMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(marketPrice))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the MarketPrice in the database
        List<MarketPrice> marketPriceList = marketPriceRepository.findAll();
        assertThat(marketPriceList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteMarketPrice() throws Exception {
        // Initialize the database
        marketPriceRepository.saveAndFlush(marketPrice);

        int databaseSizeBeforeDelete = marketPriceRepository.findAll().size();

        // Delete the marketPrice
        restMarketPriceMockMvc
            .perform(delete(ENTITY_API_URL_ID, marketPrice.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<MarketPrice> marketPriceList = marketPriceRepository.findAll();
        assertThat(marketPriceList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
