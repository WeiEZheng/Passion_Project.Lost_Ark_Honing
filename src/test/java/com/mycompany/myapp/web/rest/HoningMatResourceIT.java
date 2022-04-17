package com.mycompany.myapp.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.mycompany.myapp.IntegrationTest;
import com.mycompany.myapp.domain.HoningMat;
import com.mycompany.myapp.domain.enumeration.EquipType;
import com.mycompany.myapp.domain.enumeration.ShardType;
import com.mycompany.myapp.repository.HoningMatRepository;
import com.mycompany.myapp.service.HoningMatService;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import javax.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link HoningMatResource} REST controller.
 */
@IntegrationTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
class HoningMatResourceIT {

    private static final Integer DEFAULT_TIER = 1;
    private static final Integer UPDATED_TIER = 2;

    private static final Integer DEFAULT_LEVEL = 1;
    private static final Integer UPDATED_LEVEL = 2;

    private static final ShardType DEFAULT_SHARD_TYPE = ShardType.Destruction;
    private static final ShardType UPDATED_SHARD_TYPE = ShardType.Guardian;

    private static final EquipType DEFAULT_EQUIP_TYPE = EquipType.Armor;
    private static final EquipType UPDATED_EQUIP_TYPE = EquipType.Weapon;

    private static final String DEFAULT_FUSION_MATERIAL_NAME_1 = "AAAAAAAAAA";
    private static final String UPDATED_FUSION_MATERIAL_NAME_1 = "BBBBBBBBBB";

    private static final Integer DEFAULT_FUSION_MATERIAL_NUM_1 = 1;
    private static final Integer UPDATED_FUSION_MATERIAL_NUM_1 = 2;

    private static final String DEFAULT_FUSION_MATERIAL_NAME_2 = "AAAAAAAAAA";
    private static final String UPDATED_FUSION_MATERIAL_NAME_2 = "BBBBBBBBBB";

    private static final Integer DEFAULT_FUSION_MATERIAL_NUM_2 = 1;
    private static final Integer UPDATED_FUSION_MATERIAL_NUM_2 = 2;

    private static final String DEFAULT_FUSION_MATERIAL_NAME_3 = "AAAAAAAAAA";
    private static final String UPDATED_FUSION_MATERIAL_NAME_3 = "BBBBBBBBBB";

    private static final Integer DEFAULT_FUSION_MATERIAL_NUM_3 = 1;
    private static final Integer UPDATED_FUSION_MATERIAL_NUM_3 = 2;

    private static final String ENTITY_API_URL = "/api/honing-mats";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private HoningMatRepository honingMatRepository;

    @Mock
    private HoningMatRepository honingMatRepositoryMock;

    @Mock
    private HoningMatService honingMatServiceMock;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restHoningMatMockMvc;

    private HoningMat honingMat;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static HoningMat createEntity(EntityManager em) {
        HoningMat honingMat = new HoningMat()
            .tier(DEFAULT_TIER)
            .level(DEFAULT_LEVEL)
            .shardType(DEFAULT_SHARD_TYPE)
            .equipType(DEFAULT_EQUIP_TYPE)
            .fusionMaterialName1(DEFAULT_FUSION_MATERIAL_NAME_1)
            .fusionMaterialNum1(DEFAULT_FUSION_MATERIAL_NUM_1)
            .fusionMaterialName2(DEFAULT_FUSION_MATERIAL_NAME_2)
            .fusionMaterialNum2(DEFAULT_FUSION_MATERIAL_NUM_2)
            .fusionMaterialName3(DEFAULT_FUSION_MATERIAL_NAME_3)
            .fusionMaterialNum3(DEFAULT_FUSION_MATERIAL_NUM_3);
        return honingMat;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static HoningMat createUpdatedEntity(EntityManager em) {
        HoningMat honingMat = new HoningMat()
            .tier(UPDATED_TIER)
            .level(UPDATED_LEVEL)
            .shardType(UPDATED_SHARD_TYPE)
            .equipType(UPDATED_EQUIP_TYPE)
            .fusionMaterialName1(UPDATED_FUSION_MATERIAL_NAME_1)
            .fusionMaterialNum1(UPDATED_FUSION_MATERIAL_NUM_1)
            .fusionMaterialName2(UPDATED_FUSION_MATERIAL_NAME_2)
            .fusionMaterialNum2(UPDATED_FUSION_MATERIAL_NUM_2)
            .fusionMaterialName3(UPDATED_FUSION_MATERIAL_NAME_3)
            .fusionMaterialNum3(UPDATED_FUSION_MATERIAL_NUM_3);
        return honingMat;
    }

    @BeforeEach
    public void initTest() {
        honingMat = createEntity(em);
    }

    @Test
    @Transactional
    void createHoningMat() throws Exception {
        int databaseSizeBeforeCreate = honingMatRepository.findAll().size();
        // Create the HoningMat
        restHoningMatMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(honingMat)))
            .andExpect(status().isCreated());

        // Validate the HoningMat in the database
        List<HoningMat> honingMatList = honingMatRepository.findAll();
        assertThat(honingMatList).hasSize(databaseSizeBeforeCreate + 1);
        HoningMat testHoningMat = honingMatList.get(honingMatList.size() - 1);
        assertThat(testHoningMat.getTier()).isEqualTo(DEFAULT_TIER);
        assertThat(testHoningMat.getLevel()).isEqualTo(DEFAULT_LEVEL);
        assertThat(testHoningMat.getShardType()).isEqualTo(DEFAULT_SHARD_TYPE);
        assertThat(testHoningMat.getEquipType()).isEqualTo(DEFAULT_EQUIP_TYPE);
        assertThat(testHoningMat.getFusionMaterialName1()).isEqualTo(DEFAULT_FUSION_MATERIAL_NAME_1);
        assertThat(testHoningMat.getFusionMaterialNum1()).isEqualTo(DEFAULT_FUSION_MATERIAL_NUM_1);
        assertThat(testHoningMat.getFusionMaterialName2()).isEqualTo(DEFAULT_FUSION_MATERIAL_NAME_2);
        assertThat(testHoningMat.getFusionMaterialNum2()).isEqualTo(DEFAULT_FUSION_MATERIAL_NUM_2);
        assertThat(testHoningMat.getFusionMaterialName3()).isEqualTo(DEFAULT_FUSION_MATERIAL_NAME_3);
        assertThat(testHoningMat.getFusionMaterialNum3()).isEqualTo(DEFAULT_FUSION_MATERIAL_NUM_3);
    }

    @Test
    @Transactional
    void createHoningMatWithExistingId() throws Exception {
        // Create the HoningMat with an existing ID
        honingMat.setId(1L);

        int databaseSizeBeforeCreate = honingMatRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restHoningMatMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(honingMat)))
            .andExpect(status().isBadRequest());

        // Validate the HoningMat in the database
        List<HoningMat> honingMatList = honingMatRepository.findAll();
        assertThat(honingMatList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkTierIsRequired() throws Exception {
        int databaseSizeBeforeTest = honingMatRepository.findAll().size();
        // set the field null
        honingMat.setTier(null);

        // Create the HoningMat, which fails.

        restHoningMatMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(honingMat)))
            .andExpect(status().isBadRequest());

        List<HoningMat> honingMatList = honingMatRepository.findAll();
        assertThat(honingMatList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkLevelIsRequired() throws Exception {
        int databaseSizeBeforeTest = honingMatRepository.findAll().size();
        // set the field null
        honingMat.setLevel(null);

        // Create the HoningMat, which fails.

        restHoningMatMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(honingMat)))
            .andExpect(status().isBadRequest());

        List<HoningMat> honingMatList = honingMatRepository.findAll();
        assertThat(honingMatList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkShardTypeIsRequired() throws Exception {
        int databaseSizeBeforeTest = honingMatRepository.findAll().size();
        // set the field null
        honingMat.setShardType(null);

        // Create the HoningMat, which fails.

        restHoningMatMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(honingMat)))
            .andExpect(status().isBadRequest());

        List<HoningMat> honingMatList = honingMatRepository.findAll();
        assertThat(honingMatList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkEquipTypeIsRequired() throws Exception {
        int databaseSizeBeforeTest = honingMatRepository.findAll().size();
        // set the field null
        honingMat.setEquipType(null);

        // Create the HoningMat, which fails.

        restHoningMatMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(honingMat)))
            .andExpect(status().isBadRequest());

        List<HoningMat> honingMatList = honingMatRepository.findAll();
        assertThat(honingMatList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkFusionMaterialName1IsRequired() throws Exception {
        int databaseSizeBeforeTest = honingMatRepository.findAll().size();
        // set the field null
        honingMat.setFusionMaterialName1(null);

        // Create the HoningMat, which fails.

        restHoningMatMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(honingMat)))
            .andExpect(status().isBadRequest());

        List<HoningMat> honingMatList = honingMatRepository.findAll();
        assertThat(honingMatList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkFusionMaterialNum1IsRequired() throws Exception {
        int databaseSizeBeforeTest = honingMatRepository.findAll().size();
        // set the field null
        honingMat.setFusionMaterialNum1(null);

        // Create the HoningMat, which fails.

        restHoningMatMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(honingMat)))
            .andExpect(status().isBadRequest());

        List<HoningMat> honingMatList = honingMatRepository.findAll();
        assertThat(honingMatList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllHoningMats() throws Exception {
        // Initialize the database
        honingMatRepository.saveAndFlush(honingMat);

        // Get all the honingMatList
        restHoningMatMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(honingMat.getId().intValue())))
            .andExpect(jsonPath("$.[*].tier").value(hasItem(DEFAULT_TIER)))
            .andExpect(jsonPath("$.[*].level").value(hasItem(DEFAULT_LEVEL)))
            .andExpect(jsonPath("$.[*].shardType").value(hasItem(DEFAULT_SHARD_TYPE.toString())))
            .andExpect(jsonPath("$.[*].equipType").value(hasItem(DEFAULT_EQUIP_TYPE.toString())))
            .andExpect(jsonPath("$.[*].fusionMaterialName1").value(hasItem(DEFAULT_FUSION_MATERIAL_NAME_1)))
            .andExpect(jsonPath("$.[*].fusionMaterialNum1").value(hasItem(DEFAULT_FUSION_MATERIAL_NUM_1)))
            .andExpect(jsonPath("$.[*].fusionMaterialName2").value(hasItem(DEFAULT_FUSION_MATERIAL_NAME_2)))
            .andExpect(jsonPath("$.[*].fusionMaterialNum2").value(hasItem(DEFAULT_FUSION_MATERIAL_NUM_2)))
            .andExpect(jsonPath("$.[*].fusionMaterialName3").value(hasItem(DEFAULT_FUSION_MATERIAL_NAME_3)))
            .andExpect(jsonPath("$.[*].fusionMaterialNum3").value(hasItem(DEFAULT_FUSION_MATERIAL_NUM_3)));
    }

    @SuppressWarnings({ "unchecked" })
    void getAllHoningMatsWithEagerRelationshipsIsEnabled() throws Exception {
        when(honingMatServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restHoningMatMockMvc.perform(get(ENTITY_API_URL + "?eagerload=true")).andExpect(status().isOk());

        verify(honingMatServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({ "unchecked" })
    void getAllHoningMatsWithEagerRelationshipsIsNotEnabled() throws Exception {
        when(honingMatServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restHoningMatMockMvc.perform(get(ENTITY_API_URL + "?eagerload=true")).andExpect(status().isOk());

        verify(honingMatServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @Test
    @Transactional
    void getHoningMat() throws Exception {
        // Initialize the database
        honingMatRepository.saveAndFlush(honingMat);

        // Get the honingMat
        restHoningMatMockMvc
            .perform(get(ENTITY_API_URL_ID, honingMat.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(honingMat.getId().intValue()))
            .andExpect(jsonPath("$.tier").value(DEFAULT_TIER))
            .andExpect(jsonPath("$.level").value(DEFAULT_LEVEL))
            .andExpect(jsonPath("$.shardType").value(DEFAULT_SHARD_TYPE.toString()))
            .andExpect(jsonPath("$.equipType").value(DEFAULT_EQUIP_TYPE.toString()))
            .andExpect(jsonPath("$.fusionMaterialName1").value(DEFAULT_FUSION_MATERIAL_NAME_1))
            .andExpect(jsonPath("$.fusionMaterialNum1").value(DEFAULT_FUSION_MATERIAL_NUM_1))
            .andExpect(jsonPath("$.fusionMaterialName2").value(DEFAULT_FUSION_MATERIAL_NAME_2))
            .andExpect(jsonPath("$.fusionMaterialNum2").value(DEFAULT_FUSION_MATERIAL_NUM_2))
            .andExpect(jsonPath("$.fusionMaterialName3").value(DEFAULT_FUSION_MATERIAL_NAME_3))
            .andExpect(jsonPath("$.fusionMaterialNum3").value(DEFAULT_FUSION_MATERIAL_NUM_3));
    }

    @Test
    @Transactional
    void getNonExistingHoningMat() throws Exception {
        // Get the honingMat
        restHoningMatMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewHoningMat() throws Exception {
        // Initialize the database
        honingMatRepository.saveAndFlush(honingMat);

        int databaseSizeBeforeUpdate = honingMatRepository.findAll().size();

        // Update the honingMat
        HoningMat updatedHoningMat = honingMatRepository.findById(honingMat.getId()).get();
        // Disconnect from session so that the updates on updatedHoningMat are not directly saved in db
        em.detach(updatedHoningMat);
        updatedHoningMat
            .tier(UPDATED_TIER)
            .level(UPDATED_LEVEL)
            .shardType(UPDATED_SHARD_TYPE)
            .equipType(UPDATED_EQUIP_TYPE)
            .fusionMaterialName1(UPDATED_FUSION_MATERIAL_NAME_1)
            .fusionMaterialNum1(UPDATED_FUSION_MATERIAL_NUM_1)
            .fusionMaterialName2(UPDATED_FUSION_MATERIAL_NAME_2)
            .fusionMaterialNum2(UPDATED_FUSION_MATERIAL_NUM_2)
            .fusionMaterialName3(UPDATED_FUSION_MATERIAL_NAME_3)
            .fusionMaterialNum3(UPDATED_FUSION_MATERIAL_NUM_3);

        restHoningMatMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedHoningMat.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedHoningMat))
            )
            .andExpect(status().isOk());

        // Validate the HoningMat in the database
        List<HoningMat> honingMatList = honingMatRepository.findAll();
        assertThat(honingMatList).hasSize(databaseSizeBeforeUpdate);
        HoningMat testHoningMat = honingMatList.get(honingMatList.size() - 1);
        assertThat(testHoningMat.getTier()).isEqualTo(UPDATED_TIER);
        assertThat(testHoningMat.getLevel()).isEqualTo(UPDATED_LEVEL);
        assertThat(testHoningMat.getShardType()).isEqualTo(UPDATED_SHARD_TYPE);
        assertThat(testHoningMat.getEquipType()).isEqualTo(UPDATED_EQUIP_TYPE);
        assertThat(testHoningMat.getFusionMaterialName1()).isEqualTo(UPDATED_FUSION_MATERIAL_NAME_1);
        assertThat(testHoningMat.getFusionMaterialNum1()).isEqualTo(UPDATED_FUSION_MATERIAL_NUM_1);
        assertThat(testHoningMat.getFusionMaterialName2()).isEqualTo(UPDATED_FUSION_MATERIAL_NAME_2);
        assertThat(testHoningMat.getFusionMaterialNum2()).isEqualTo(UPDATED_FUSION_MATERIAL_NUM_2);
        assertThat(testHoningMat.getFusionMaterialName3()).isEqualTo(UPDATED_FUSION_MATERIAL_NAME_3);
        assertThat(testHoningMat.getFusionMaterialNum3()).isEqualTo(UPDATED_FUSION_MATERIAL_NUM_3);
    }

    @Test
    @Transactional
    void putNonExistingHoningMat() throws Exception {
        int databaseSizeBeforeUpdate = honingMatRepository.findAll().size();
        honingMat.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restHoningMatMockMvc
            .perform(
                put(ENTITY_API_URL_ID, honingMat.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(honingMat))
            )
            .andExpect(status().isBadRequest());

        // Validate the HoningMat in the database
        List<HoningMat> honingMatList = honingMatRepository.findAll();
        assertThat(honingMatList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchHoningMat() throws Exception {
        int databaseSizeBeforeUpdate = honingMatRepository.findAll().size();
        honingMat.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restHoningMatMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(honingMat))
            )
            .andExpect(status().isBadRequest());

        // Validate the HoningMat in the database
        List<HoningMat> honingMatList = honingMatRepository.findAll();
        assertThat(honingMatList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamHoningMat() throws Exception {
        int databaseSizeBeforeUpdate = honingMatRepository.findAll().size();
        honingMat.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restHoningMatMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(honingMat)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the HoningMat in the database
        List<HoningMat> honingMatList = honingMatRepository.findAll();
        assertThat(honingMatList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateHoningMatWithPatch() throws Exception {
        // Initialize the database
        honingMatRepository.saveAndFlush(honingMat);

        int databaseSizeBeforeUpdate = honingMatRepository.findAll().size();

        // Update the honingMat using partial update
        HoningMat partialUpdatedHoningMat = new HoningMat();
        partialUpdatedHoningMat.setId(honingMat.getId());

        partialUpdatedHoningMat.tier(UPDATED_TIER).shardType(UPDATED_SHARD_TYPE);

        restHoningMatMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedHoningMat.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedHoningMat))
            )
            .andExpect(status().isOk());

        // Validate the HoningMat in the database
        List<HoningMat> honingMatList = honingMatRepository.findAll();
        assertThat(honingMatList).hasSize(databaseSizeBeforeUpdate);
        HoningMat testHoningMat = honingMatList.get(honingMatList.size() - 1);
        assertThat(testHoningMat.getTier()).isEqualTo(UPDATED_TIER);
        assertThat(testHoningMat.getLevel()).isEqualTo(DEFAULT_LEVEL);
        assertThat(testHoningMat.getShardType()).isEqualTo(UPDATED_SHARD_TYPE);
        assertThat(testHoningMat.getEquipType()).isEqualTo(DEFAULT_EQUIP_TYPE);
        assertThat(testHoningMat.getFusionMaterialName1()).isEqualTo(DEFAULT_FUSION_MATERIAL_NAME_1);
        assertThat(testHoningMat.getFusionMaterialNum1()).isEqualTo(DEFAULT_FUSION_MATERIAL_NUM_1);
        assertThat(testHoningMat.getFusionMaterialName2()).isEqualTo(DEFAULT_FUSION_MATERIAL_NAME_2);
        assertThat(testHoningMat.getFusionMaterialNum2()).isEqualTo(DEFAULT_FUSION_MATERIAL_NUM_2);
        assertThat(testHoningMat.getFusionMaterialName3()).isEqualTo(DEFAULT_FUSION_MATERIAL_NAME_3);
        assertThat(testHoningMat.getFusionMaterialNum3()).isEqualTo(DEFAULT_FUSION_MATERIAL_NUM_3);
    }

    @Test
    @Transactional
    void fullUpdateHoningMatWithPatch() throws Exception {
        // Initialize the database
        honingMatRepository.saveAndFlush(honingMat);

        int databaseSizeBeforeUpdate = honingMatRepository.findAll().size();

        // Update the honingMat using partial update
        HoningMat partialUpdatedHoningMat = new HoningMat();
        partialUpdatedHoningMat.setId(honingMat.getId());

        partialUpdatedHoningMat
            .tier(UPDATED_TIER)
            .level(UPDATED_LEVEL)
            .shardType(UPDATED_SHARD_TYPE)
            .equipType(UPDATED_EQUIP_TYPE)
            .fusionMaterialName1(UPDATED_FUSION_MATERIAL_NAME_1)
            .fusionMaterialNum1(UPDATED_FUSION_MATERIAL_NUM_1)
            .fusionMaterialName2(UPDATED_FUSION_MATERIAL_NAME_2)
            .fusionMaterialNum2(UPDATED_FUSION_MATERIAL_NUM_2)
            .fusionMaterialName3(UPDATED_FUSION_MATERIAL_NAME_3)
            .fusionMaterialNum3(UPDATED_FUSION_MATERIAL_NUM_3);

        restHoningMatMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedHoningMat.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedHoningMat))
            )
            .andExpect(status().isOk());

        // Validate the HoningMat in the database
        List<HoningMat> honingMatList = honingMatRepository.findAll();
        assertThat(honingMatList).hasSize(databaseSizeBeforeUpdate);
        HoningMat testHoningMat = honingMatList.get(honingMatList.size() - 1);
        assertThat(testHoningMat.getTier()).isEqualTo(UPDATED_TIER);
        assertThat(testHoningMat.getLevel()).isEqualTo(UPDATED_LEVEL);
        assertThat(testHoningMat.getShardType()).isEqualTo(UPDATED_SHARD_TYPE);
        assertThat(testHoningMat.getEquipType()).isEqualTo(UPDATED_EQUIP_TYPE);
        assertThat(testHoningMat.getFusionMaterialName1()).isEqualTo(UPDATED_FUSION_MATERIAL_NAME_1);
        assertThat(testHoningMat.getFusionMaterialNum1()).isEqualTo(UPDATED_FUSION_MATERIAL_NUM_1);
        assertThat(testHoningMat.getFusionMaterialName2()).isEqualTo(UPDATED_FUSION_MATERIAL_NAME_2);
        assertThat(testHoningMat.getFusionMaterialNum2()).isEqualTo(UPDATED_FUSION_MATERIAL_NUM_2);
        assertThat(testHoningMat.getFusionMaterialName3()).isEqualTo(UPDATED_FUSION_MATERIAL_NAME_3);
        assertThat(testHoningMat.getFusionMaterialNum3()).isEqualTo(UPDATED_FUSION_MATERIAL_NUM_3);
    }

    @Test
    @Transactional
    void patchNonExistingHoningMat() throws Exception {
        int databaseSizeBeforeUpdate = honingMatRepository.findAll().size();
        honingMat.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restHoningMatMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, honingMat.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(honingMat))
            )
            .andExpect(status().isBadRequest());

        // Validate the HoningMat in the database
        List<HoningMat> honingMatList = honingMatRepository.findAll();
        assertThat(honingMatList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchHoningMat() throws Exception {
        int databaseSizeBeforeUpdate = honingMatRepository.findAll().size();
        honingMat.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restHoningMatMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(honingMat))
            )
            .andExpect(status().isBadRequest());

        // Validate the HoningMat in the database
        List<HoningMat> honingMatList = honingMatRepository.findAll();
        assertThat(honingMatList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamHoningMat() throws Exception {
        int databaseSizeBeforeUpdate = honingMatRepository.findAll().size();
        honingMat.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restHoningMatMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(honingMat))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the HoningMat in the database
        List<HoningMat> honingMatList = honingMatRepository.findAll();
        assertThat(honingMatList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteHoningMat() throws Exception {
        // Initialize the database
        honingMatRepository.saveAndFlush(honingMat);

        int databaseSizeBeforeDelete = honingMatRepository.findAll().size();

        // Delete the honingMat
        restHoningMatMockMvc
            .perform(delete(ENTITY_API_URL_ID, honingMat.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<HoningMat> honingMatList = honingMatRepository.findAll();
        assertThat(honingMatList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
