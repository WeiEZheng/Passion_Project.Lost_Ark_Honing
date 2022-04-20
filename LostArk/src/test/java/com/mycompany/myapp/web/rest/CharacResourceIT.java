package com.mycompany.myapp.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.mycompany.myapp.IntegrationTest;
import com.mycompany.myapp.domain.Charac;
import com.mycompany.myapp.domain.enumeration.Server;
import com.mycompany.myapp.repository.CharacRepository;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import javax.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link CharacResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class CharacResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_ADV_CLASS = "AAAAAAAAAA";
    private static final String UPDATED_ADV_CLASS = "BBBBBBBBBB";

    private static final Server DEFAULT_SERVER = Server.Azena;
    private static final Server UPDATED_SERVER = Server.Avesta;

    private static final String ENTITY_API_URL = "/api/characs";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private CharacRepository characRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restCharacMockMvc;

    private Charac charac;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Charac createEntity(EntityManager em) {
        Charac charac = new Charac().name(DEFAULT_NAME).advClass(DEFAULT_ADV_CLASS).server(DEFAULT_SERVER);
        return charac;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Charac createUpdatedEntity(EntityManager em) {
        Charac charac = new Charac().name(UPDATED_NAME).advClass(UPDATED_ADV_CLASS).server(UPDATED_SERVER);
        return charac;
    }

    @BeforeEach
    public void initTest() {
        charac = createEntity(em);
    }

    @Test
    @Transactional
    void createCharac() throws Exception {
        int databaseSizeBeforeCreate = characRepository.findAll().size();
        // Create the Charac
        restCharacMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(charac)))
            .andExpect(status().isCreated());

        // Validate the Charac in the database
        List<Charac> characList = characRepository.findAll();
        assertThat(characList).hasSize(databaseSizeBeforeCreate + 1);
        Charac testCharac = characList.get(characList.size() - 1);
        assertThat(testCharac.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testCharac.getAdvClass()).isEqualTo(DEFAULT_ADV_CLASS);
        assertThat(testCharac.getServer()).isEqualTo(DEFAULT_SERVER);
    }

    @Test
    @Transactional
    void createCharacWithExistingId() throws Exception {
        // Create the Charac with an existing ID
        charac.setId(1L);

        int databaseSizeBeforeCreate = characRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restCharacMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(charac)))
            .andExpect(status().isBadRequest());

        // Validate the Charac in the database
        List<Charac> characList = characRepository.findAll();
        assertThat(characList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = characRepository.findAll().size();
        // set the field null
        charac.setName(null);

        // Create the Charac, which fails.

        restCharacMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(charac)))
            .andExpect(status().isBadRequest());

        List<Charac> characList = characRepository.findAll();
        assertThat(characList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkAdvClassIsRequired() throws Exception {
        int databaseSizeBeforeTest = characRepository.findAll().size();
        // set the field null
        charac.setAdvClass(null);

        // Create the Charac, which fails.

        restCharacMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(charac)))
            .andExpect(status().isBadRequest());

        List<Charac> characList = characRepository.findAll();
        assertThat(characList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkServerIsRequired() throws Exception {
        int databaseSizeBeforeTest = characRepository.findAll().size();
        // set the field null
        charac.setServer(null);

        // Create the Charac, which fails.

        restCharacMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(charac)))
            .andExpect(status().isBadRequest());

        List<Charac> characList = characRepository.findAll();
        assertThat(characList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllCharacs() throws Exception {
        // Initialize the database
        characRepository.saveAndFlush(charac);

        // Get all the characList
        restCharacMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(charac.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].advClass").value(hasItem(DEFAULT_ADV_CLASS)))
            .andExpect(jsonPath("$.[*].server").value(hasItem(DEFAULT_SERVER.toString())));
    }

    @Test
    @Transactional
    void getCharac() throws Exception {
        // Initialize the database
        characRepository.saveAndFlush(charac);

        // Get the charac
        restCharacMockMvc
            .perform(get(ENTITY_API_URL_ID, charac.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(charac.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.advClass").value(DEFAULT_ADV_CLASS))
            .andExpect(jsonPath("$.server").value(DEFAULT_SERVER.toString()));
    }

    @Test
    @Transactional
    void getNonExistingCharac() throws Exception {
        // Get the charac
        restCharacMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewCharac() throws Exception {
        // Initialize the database
        characRepository.saveAndFlush(charac);

        int databaseSizeBeforeUpdate = characRepository.findAll().size();

        // Update the charac
        Charac updatedCharac = characRepository.findById(charac.getId()).get();
        // Disconnect from session so that the updates on updatedCharac are not directly saved in db
        em.detach(updatedCharac);
        updatedCharac.name(UPDATED_NAME).advClass(UPDATED_ADV_CLASS).server(UPDATED_SERVER);

        restCharacMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedCharac.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedCharac))
            )
            .andExpect(status().isOk());

        // Validate the Charac in the database
        List<Charac> characList = characRepository.findAll();
        assertThat(characList).hasSize(databaseSizeBeforeUpdate);
        Charac testCharac = characList.get(characList.size() - 1);
        assertThat(testCharac.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testCharac.getAdvClass()).isEqualTo(UPDATED_ADV_CLASS);
        assertThat(testCharac.getServer()).isEqualTo(UPDATED_SERVER);
    }

    @Test
    @Transactional
    void putNonExistingCharac() throws Exception {
        int databaseSizeBeforeUpdate = characRepository.findAll().size();
        charac.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCharacMockMvc
            .perform(
                put(ENTITY_API_URL_ID, charac.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(charac))
            )
            .andExpect(status().isBadRequest());

        // Validate the Charac in the database
        List<Charac> characList = characRepository.findAll();
        assertThat(characList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchCharac() throws Exception {
        int databaseSizeBeforeUpdate = characRepository.findAll().size();
        charac.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCharacMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(charac))
            )
            .andExpect(status().isBadRequest());

        // Validate the Charac in the database
        List<Charac> characList = characRepository.findAll();
        assertThat(characList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamCharac() throws Exception {
        int databaseSizeBeforeUpdate = characRepository.findAll().size();
        charac.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCharacMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(charac)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Charac in the database
        List<Charac> characList = characRepository.findAll();
        assertThat(characList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateCharacWithPatch() throws Exception {
        // Initialize the database
        characRepository.saveAndFlush(charac);

        int databaseSizeBeforeUpdate = characRepository.findAll().size();

        // Update the charac using partial update
        Charac partialUpdatedCharac = new Charac();
        partialUpdatedCharac.setId(charac.getId());

        partialUpdatedCharac.name(UPDATED_NAME).advClass(UPDATED_ADV_CLASS).server(UPDATED_SERVER);

        restCharacMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedCharac.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedCharac))
            )
            .andExpect(status().isOk());

        // Validate the Charac in the database
        List<Charac> characList = characRepository.findAll();
        assertThat(characList).hasSize(databaseSizeBeforeUpdate);
        Charac testCharac = characList.get(characList.size() - 1);
        assertThat(testCharac.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testCharac.getAdvClass()).isEqualTo(UPDATED_ADV_CLASS);
        assertThat(testCharac.getServer()).isEqualTo(UPDATED_SERVER);
    }

    @Test
    @Transactional
    void fullUpdateCharacWithPatch() throws Exception {
        // Initialize the database
        characRepository.saveAndFlush(charac);

        int databaseSizeBeforeUpdate = characRepository.findAll().size();

        // Update the charac using partial update
        Charac partialUpdatedCharac = new Charac();
        partialUpdatedCharac.setId(charac.getId());

        partialUpdatedCharac.name(UPDATED_NAME).advClass(UPDATED_ADV_CLASS).server(UPDATED_SERVER);

        restCharacMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedCharac.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedCharac))
            )
            .andExpect(status().isOk());

        // Validate the Charac in the database
        List<Charac> characList = characRepository.findAll();
        assertThat(characList).hasSize(databaseSizeBeforeUpdate);
        Charac testCharac = characList.get(characList.size() - 1);
        assertThat(testCharac.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testCharac.getAdvClass()).isEqualTo(UPDATED_ADV_CLASS);
        assertThat(testCharac.getServer()).isEqualTo(UPDATED_SERVER);
    }

    @Test
    @Transactional
    void patchNonExistingCharac() throws Exception {
        int databaseSizeBeforeUpdate = characRepository.findAll().size();
        charac.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCharacMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, charac.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(charac))
            )
            .andExpect(status().isBadRequest());

        // Validate the Charac in the database
        List<Charac> characList = characRepository.findAll();
        assertThat(characList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchCharac() throws Exception {
        int databaseSizeBeforeUpdate = characRepository.findAll().size();
        charac.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCharacMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(charac))
            )
            .andExpect(status().isBadRequest());

        // Validate the Charac in the database
        List<Charac> characList = characRepository.findAll();
        assertThat(characList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamCharac() throws Exception {
        int databaseSizeBeforeUpdate = characRepository.findAll().size();
        charac.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCharacMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(charac)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Charac in the database
        List<Charac> characList = characRepository.findAll();
        assertThat(characList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteCharac() throws Exception {
        // Initialize the database
        characRepository.saveAndFlush(charac);

        int databaseSizeBeforeDelete = characRepository.findAll().size();

        // Delete the charac
        restCharacMockMvc
            .perform(delete(ENTITY_API_URL_ID, charac.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Charac> characList = characRepository.findAll();
        assertThat(characList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
