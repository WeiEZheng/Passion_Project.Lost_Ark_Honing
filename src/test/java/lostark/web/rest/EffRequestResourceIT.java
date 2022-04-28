package lostark.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import javax.persistence.EntityManager;
import lostark.IntegrationTest;
import lostark.domain.EffRequest;
import lostark.repository.EffRequestRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link EffRequestResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class EffRequestResourceIT {

    private static final Double DEFAULT_BASE_PERCENT = 1D;
    private static final Double UPDATED_BASE_PERCENT = 2D;

    private static final Double DEFAULT_ADDITION_PERCENT_PER_FAIL = 1D;
    private static final Double UPDATED_ADDITION_PERCENT_PER_FAIL = 2D;

    private static final Double DEFAULT_MAX_PERCENT_AFTER_MATS = 1D;
    private static final Double UPDATED_MAX_PERCENT_AFTER_MATS = 2D;

    private static final Integer DEFAULT_FUSION_MAT_1_AMOUNT = 1;
    private static final Integer UPDATED_FUSION_MAT_1_AMOUNT = 2;

    private static final Integer DEFAULT_FUSION_MAT_2_AMOUNT = 1;
    private static final Integer UPDATED_FUSION_MAT_2_AMOUNT = 2;

    private static final Integer DEFAULT_FUSION_MAT_3_AMOUNT = 1;
    private static final Integer UPDATED_FUSION_MAT_3_AMOUNT = 2;

    private static final Integer DEFAULT_FAIL_LIMIT = 1;
    private static final Integer UPDATED_FAIL_LIMIT = 2;

    private static final Double DEFAULT_AMOUNT_DIFF = 1D;
    private static final Double UPDATED_AMOUNT_DIFF = 2D;

    private static final String ENTITY_API_URL = "/api/eff-requests";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private EffRequestRepository effRequestRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restEffRequestMockMvc;

    private EffRequest effRequest;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static EffRequest createEntity(EntityManager em) {
        EffRequest effRequest = new EffRequest()
            .basePercent(DEFAULT_BASE_PERCENT)
            .additionPercentPerFail(DEFAULT_ADDITION_PERCENT_PER_FAIL)
            .maxPercentAfterMats(DEFAULT_MAX_PERCENT_AFTER_MATS)
            .fusionMat1Amount(DEFAULT_FUSION_MAT_1_AMOUNT)
            .fusionMat2Amount(DEFAULT_FUSION_MAT_2_AMOUNT)
            .fusionMat3Amount(DEFAULT_FUSION_MAT_3_AMOUNT)
            .failLimit(DEFAULT_FAIL_LIMIT)
            .amountDiff(DEFAULT_AMOUNT_DIFF);
        return effRequest;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static EffRequest createUpdatedEntity(EntityManager em) {
        EffRequest effRequest = new EffRequest()
            .basePercent(UPDATED_BASE_PERCENT)
            .additionPercentPerFail(UPDATED_ADDITION_PERCENT_PER_FAIL)
            .maxPercentAfterMats(UPDATED_MAX_PERCENT_AFTER_MATS)
            .fusionMat1Amount(UPDATED_FUSION_MAT_1_AMOUNT)
            .fusionMat2Amount(UPDATED_FUSION_MAT_2_AMOUNT)
            .fusionMat3Amount(UPDATED_FUSION_MAT_3_AMOUNT)
            .failLimit(UPDATED_FAIL_LIMIT)
            .amountDiff(UPDATED_AMOUNT_DIFF);
        return effRequest;
    }

    @BeforeEach
    public void initTest() {
        effRequest = createEntity(em);
    }

    @Test
    @Transactional
    void createEffRequest() throws Exception {
        int databaseSizeBeforeCreate = effRequestRepository.findAll().size();
        // Create the EffRequest
        restEffRequestMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(effRequest)))
            .andExpect(status().isCreated());

        // Validate the EffRequest in the database
        List<EffRequest> effRequestList = effRequestRepository.findAll();
        assertThat(effRequestList).hasSize(databaseSizeBeforeCreate + 1);
        EffRequest testEffRequest = effRequestList.get(effRequestList.size() - 1);
        assertThat(testEffRequest.getBasePercent()).isEqualTo(DEFAULT_BASE_PERCENT);
        assertThat(testEffRequest.getAdditionPercentPerFail()).isEqualTo(DEFAULT_ADDITION_PERCENT_PER_FAIL);
        assertThat(testEffRequest.getMaxPercentAfterMats()).isEqualTo(DEFAULT_MAX_PERCENT_AFTER_MATS);
        assertThat(testEffRequest.getFusionMat1Amount()).isEqualTo(DEFAULT_FUSION_MAT_1_AMOUNT);
        assertThat(testEffRequest.getFusionMat2Amount()).isEqualTo(DEFAULT_FUSION_MAT_2_AMOUNT);
        assertThat(testEffRequest.getFusionMat3Amount()).isEqualTo(DEFAULT_FUSION_MAT_3_AMOUNT);
        assertThat(testEffRequest.getFailLimit()).isEqualTo(DEFAULT_FAIL_LIMIT);
        assertThat(testEffRequest.getAmountDiff()).isEqualTo(DEFAULT_AMOUNT_DIFF);
    }

    @Test
    @Transactional
    void createEffRequestWithExistingId() throws Exception {
        // Create the EffRequest with an existing ID
        effRequest.setId(1L);

        int databaseSizeBeforeCreate = effRequestRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restEffRequestMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(effRequest)))
            .andExpect(status().isBadRequest());

        // Validate the EffRequest in the database
        List<EffRequest> effRequestList = effRequestRepository.findAll();
        assertThat(effRequestList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllEffRequests() throws Exception {
        // Initialize the database
        effRequestRepository.saveAndFlush(effRequest);

        // Get all the effRequestList
        restEffRequestMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(effRequest.getId().intValue())))
            .andExpect(jsonPath("$.[*].basePercent").value(hasItem(DEFAULT_BASE_PERCENT.doubleValue())))
            .andExpect(jsonPath("$.[*].additionPercentPerFail").value(hasItem(DEFAULT_ADDITION_PERCENT_PER_FAIL.doubleValue())))
            .andExpect(jsonPath("$.[*].maxPercentAfterMats").value(hasItem(DEFAULT_MAX_PERCENT_AFTER_MATS.doubleValue())))
            .andExpect(jsonPath("$.[*].fusionMat1Amount").value(hasItem(DEFAULT_FUSION_MAT_1_AMOUNT)))
            .andExpect(jsonPath("$.[*].fusionMat2Amount").value(hasItem(DEFAULT_FUSION_MAT_2_AMOUNT)))
            .andExpect(jsonPath("$.[*].fusionMat3Amount").value(hasItem(DEFAULT_FUSION_MAT_3_AMOUNT)))
            .andExpect(jsonPath("$.[*].failLimit").value(hasItem(DEFAULT_FAIL_LIMIT)))
            .andExpect(jsonPath("$.[*].amountDiff").value(hasItem(DEFAULT_AMOUNT_DIFF.doubleValue())));
    }

    @Test
    @Transactional
    void getEffRequest() throws Exception {
        // Initialize the database
        effRequestRepository.saveAndFlush(effRequest);

        // Get the effRequest
        restEffRequestMockMvc
            .perform(get(ENTITY_API_URL_ID, effRequest.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(effRequest.getId().intValue()))
            .andExpect(jsonPath("$.basePercent").value(DEFAULT_BASE_PERCENT.doubleValue()))
            .andExpect(jsonPath("$.additionPercentPerFail").value(DEFAULT_ADDITION_PERCENT_PER_FAIL.doubleValue()))
            .andExpect(jsonPath("$.maxPercentAfterMats").value(DEFAULT_MAX_PERCENT_AFTER_MATS.doubleValue()))
            .andExpect(jsonPath("$.fusionMat1Amount").value(DEFAULT_FUSION_MAT_1_AMOUNT))
            .andExpect(jsonPath("$.fusionMat2Amount").value(DEFAULT_FUSION_MAT_2_AMOUNT))
            .andExpect(jsonPath("$.fusionMat3Amount").value(DEFAULT_FUSION_MAT_3_AMOUNT))
            .andExpect(jsonPath("$.failLimit").value(DEFAULT_FAIL_LIMIT))
            .andExpect(jsonPath("$.amountDiff").value(DEFAULT_AMOUNT_DIFF.doubleValue()));
    }

    @Test
    @Transactional
    void getNonExistingEffRequest() throws Exception {
        // Get the effRequest
        restEffRequestMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewEffRequest() throws Exception {
        // Initialize the database
        effRequestRepository.saveAndFlush(effRequest);

        int databaseSizeBeforeUpdate = effRequestRepository.findAll().size();

        // Update the effRequest
        EffRequest updatedEffRequest = effRequestRepository.findById(effRequest.getId()).get();
        // Disconnect from session so that the updates on updatedEffRequest are not directly saved in db
        em.detach(updatedEffRequest);
        updatedEffRequest
            .basePercent(UPDATED_BASE_PERCENT)
            .additionPercentPerFail(UPDATED_ADDITION_PERCENT_PER_FAIL)
            .maxPercentAfterMats(UPDATED_MAX_PERCENT_AFTER_MATS)
            .fusionMat1Amount(UPDATED_FUSION_MAT_1_AMOUNT)
            .fusionMat2Amount(UPDATED_FUSION_MAT_2_AMOUNT)
            .fusionMat3Amount(UPDATED_FUSION_MAT_3_AMOUNT)
            .failLimit(UPDATED_FAIL_LIMIT)
            .amountDiff(UPDATED_AMOUNT_DIFF);

        restEffRequestMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedEffRequest.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedEffRequest))
            )
            .andExpect(status().isOk());

        // Validate the EffRequest in the database
        List<EffRequest> effRequestList = effRequestRepository.findAll();
        assertThat(effRequestList).hasSize(databaseSizeBeforeUpdate);
        EffRequest testEffRequest = effRequestList.get(effRequestList.size() - 1);
        assertThat(testEffRequest.getBasePercent()).isEqualTo(UPDATED_BASE_PERCENT);
        assertThat(testEffRequest.getAdditionPercentPerFail()).isEqualTo(UPDATED_ADDITION_PERCENT_PER_FAIL);
        assertThat(testEffRequest.getMaxPercentAfterMats()).isEqualTo(UPDATED_MAX_PERCENT_AFTER_MATS);
        assertThat(testEffRequest.getFusionMat1Amount()).isEqualTo(UPDATED_FUSION_MAT_1_AMOUNT);
        assertThat(testEffRequest.getFusionMat2Amount()).isEqualTo(UPDATED_FUSION_MAT_2_AMOUNT);
        assertThat(testEffRequest.getFusionMat3Amount()).isEqualTo(UPDATED_FUSION_MAT_3_AMOUNT);
        assertThat(testEffRequest.getFailLimit()).isEqualTo(UPDATED_FAIL_LIMIT);
        assertThat(testEffRequest.getAmountDiff()).isEqualTo(UPDATED_AMOUNT_DIFF);
    }

    @Test
    @Transactional
    void putNonExistingEffRequest() throws Exception {
        int databaseSizeBeforeUpdate = effRequestRepository.findAll().size();
        effRequest.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restEffRequestMockMvc
            .perform(
                put(ENTITY_API_URL_ID, effRequest.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(effRequest))
            )
            .andExpect(status().isBadRequest());

        // Validate the EffRequest in the database
        List<EffRequest> effRequestList = effRequestRepository.findAll();
        assertThat(effRequestList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchEffRequest() throws Exception {
        int databaseSizeBeforeUpdate = effRequestRepository.findAll().size();
        effRequest.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEffRequestMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(effRequest))
            )
            .andExpect(status().isBadRequest());

        // Validate the EffRequest in the database
        List<EffRequest> effRequestList = effRequestRepository.findAll();
        assertThat(effRequestList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamEffRequest() throws Exception {
        int databaseSizeBeforeUpdate = effRequestRepository.findAll().size();
        effRequest.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEffRequestMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(effRequest)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the EffRequest in the database
        List<EffRequest> effRequestList = effRequestRepository.findAll();
        assertThat(effRequestList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateEffRequestWithPatch() throws Exception {
        // Initialize the database
        effRequestRepository.saveAndFlush(effRequest);

        int databaseSizeBeforeUpdate = effRequestRepository.findAll().size();

        // Update the effRequest using partial update
        EffRequest partialUpdatedEffRequest = new EffRequest();
        partialUpdatedEffRequest.setId(effRequest.getId());

        partialUpdatedEffRequest
            .basePercent(UPDATED_BASE_PERCENT)
            .additionPercentPerFail(UPDATED_ADDITION_PERCENT_PER_FAIL)
            .maxPercentAfterMats(UPDATED_MAX_PERCENT_AFTER_MATS)
            .fusionMat1Amount(UPDATED_FUSION_MAT_1_AMOUNT)
            .fusionMat3Amount(UPDATED_FUSION_MAT_3_AMOUNT)
            .amountDiff(UPDATED_AMOUNT_DIFF);

        restEffRequestMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedEffRequest.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedEffRequest))
            )
            .andExpect(status().isOk());

        // Validate the EffRequest in the database
        List<EffRequest> effRequestList = effRequestRepository.findAll();
        assertThat(effRequestList).hasSize(databaseSizeBeforeUpdate);
        EffRequest testEffRequest = effRequestList.get(effRequestList.size() - 1);
        assertThat(testEffRequest.getBasePercent()).isEqualTo(UPDATED_BASE_PERCENT);
        assertThat(testEffRequest.getAdditionPercentPerFail()).isEqualTo(UPDATED_ADDITION_PERCENT_PER_FAIL);
        assertThat(testEffRequest.getMaxPercentAfterMats()).isEqualTo(UPDATED_MAX_PERCENT_AFTER_MATS);
        assertThat(testEffRequest.getFusionMat1Amount()).isEqualTo(UPDATED_FUSION_MAT_1_AMOUNT);
        assertThat(testEffRequest.getFusionMat2Amount()).isEqualTo(DEFAULT_FUSION_MAT_2_AMOUNT);
        assertThat(testEffRequest.getFusionMat3Amount()).isEqualTo(UPDATED_FUSION_MAT_3_AMOUNT);
        assertThat(testEffRequest.getFailLimit()).isEqualTo(DEFAULT_FAIL_LIMIT);
        assertThat(testEffRequest.getAmountDiff()).isEqualTo(UPDATED_AMOUNT_DIFF);
    }

    @Test
    @Transactional
    void fullUpdateEffRequestWithPatch() throws Exception {
        // Initialize the database
        effRequestRepository.saveAndFlush(effRequest);

        int databaseSizeBeforeUpdate = effRequestRepository.findAll().size();

        // Update the effRequest using partial update
        EffRequest partialUpdatedEffRequest = new EffRequest();
        partialUpdatedEffRequest.setId(effRequest.getId());

        partialUpdatedEffRequest
            .basePercent(UPDATED_BASE_PERCENT)
            .additionPercentPerFail(UPDATED_ADDITION_PERCENT_PER_FAIL)
            .maxPercentAfterMats(UPDATED_MAX_PERCENT_AFTER_MATS)
            .fusionMat1Amount(UPDATED_FUSION_MAT_1_AMOUNT)
            .fusionMat2Amount(UPDATED_FUSION_MAT_2_AMOUNT)
            .fusionMat3Amount(UPDATED_FUSION_MAT_3_AMOUNT)
            .failLimit(UPDATED_FAIL_LIMIT)
            .amountDiff(UPDATED_AMOUNT_DIFF);

        restEffRequestMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedEffRequest.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedEffRequest))
            )
            .andExpect(status().isOk());

        // Validate the EffRequest in the database
        List<EffRequest> effRequestList = effRequestRepository.findAll();
        assertThat(effRequestList).hasSize(databaseSizeBeforeUpdate);
        EffRequest testEffRequest = effRequestList.get(effRequestList.size() - 1);
        assertThat(testEffRequest.getBasePercent()).isEqualTo(UPDATED_BASE_PERCENT);
        assertThat(testEffRequest.getAdditionPercentPerFail()).isEqualTo(UPDATED_ADDITION_PERCENT_PER_FAIL);
        assertThat(testEffRequest.getMaxPercentAfterMats()).isEqualTo(UPDATED_MAX_PERCENT_AFTER_MATS);
        assertThat(testEffRequest.getFusionMat1Amount()).isEqualTo(UPDATED_FUSION_MAT_1_AMOUNT);
        assertThat(testEffRequest.getFusionMat2Amount()).isEqualTo(UPDATED_FUSION_MAT_2_AMOUNT);
        assertThat(testEffRequest.getFusionMat3Amount()).isEqualTo(UPDATED_FUSION_MAT_3_AMOUNT);
        assertThat(testEffRequest.getFailLimit()).isEqualTo(UPDATED_FAIL_LIMIT);
        assertThat(testEffRequest.getAmountDiff()).isEqualTo(UPDATED_AMOUNT_DIFF);
    }

    @Test
    @Transactional
    void patchNonExistingEffRequest() throws Exception {
        int databaseSizeBeforeUpdate = effRequestRepository.findAll().size();
        effRequest.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restEffRequestMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, effRequest.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(effRequest))
            )
            .andExpect(status().isBadRequest());

        // Validate the EffRequest in the database
        List<EffRequest> effRequestList = effRequestRepository.findAll();
        assertThat(effRequestList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchEffRequest() throws Exception {
        int databaseSizeBeforeUpdate = effRequestRepository.findAll().size();
        effRequest.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEffRequestMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(effRequest))
            )
            .andExpect(status().isBadRequest());

        // Validate the EffRequest in the database
        List<EffRequest> effRequestList = effRequestRepository.findAll();
        assertThat(effRequestList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamEffRequest() throws Exception {
        int databaseSizeBeforeUpdate = effRequestRepository.findAll().size();
        effRequest.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEffRequestMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(effRequest))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the EffRequest in the database
        List<EffRequest> effRequestList = effRequestRepository.findAll();
        assertThat(effRequestList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteEffRequest() throws Exception {
        // Initialize the database
        effRequestRepository.saveAndFlush(effRequest);

        int databaseSizeBeforeDelete = effRequestRepository.findAll().size();

        // Delete the effRequest
        restEffRequestMockMvc
            .perform(delete(ENTITY_API_URL_ID, effRequest.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<EffRequest> effRequestList = effRequestRepository.findAll();
        assertThat(effRequestList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
