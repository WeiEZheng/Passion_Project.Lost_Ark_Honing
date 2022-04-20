package lostark.lostarkcalc.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import javax.persistence.EntityManager;
import lostark.lostarkcalc.IntegrationTest;
import lostark.lostarkcalc.domain.Characters;
import lostark.lostarkcalc.domain.enumeration.AdvClasses;
import lostark.lostarkcalc.domain.enumeration.Server;
import lostark.lostarkcalc.repository.CharactersRepository;
import lostark.lostarkcalc.service.CharactersService;
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
 * Integration tests for the {@link CharactersResource} REST controller.
 */
@IntegrationTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
class CharactersResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final AdvClasses DEFAULT_ADV_CLASS = AdvClasses.Artillerist;
    private static final AdvClasses UPDATED_ADV_CLASS = AdvClasses.Bard;

    private static final Server DEFAULT_SERVER = Server.Azena;
    private static final Server UPDATED_SERVER = Server.Avesta;

    private static final String ENTITY_API_URL = "/api/characters";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private CharactersRepository charactersRepository;

    @Mock
    private CharactersRepository charactersRepositoryMock;

    @Mock
    private CharactersService charactersServiceMock;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restCharactersMockMvc;

    private Characters characters;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Characters createEntity(EntityManager em) {
        Characters characters = new Characters().name(DEFAULT_NAME).advClass(DEFAULT_ADV_CLASS).server(DEFAULT_SERVER);
        return characters;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Characters createUpdatedEntity(EntityManager em) {
        Characters characters = new Characters().name(UPDATED_NAME).advClass(UPDATED_ADV_CLASS).server(UPDATED_SERVER);
        return characters;
    }

    @BeforeEach
    public void initTest() {
        characters = createEntity(em);
    }

    @Test
    @Transactional
    void createCharacters() throws Exception {
        int databaseSizeBeforeCreate = charactersRepository.findAll().size();
        // Create the Characters
        restCharactersMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(characters)))
            .andExpect(status().isCreated());

        // Validate the Characters in the database
        List<Characters> charactersList = charactersRepository.findAll();
        assertThat(charactersList).hasSize(databaseSizeBeforeCreate + 1);
        Characters testCharacters = charactersList.get(charactersList.size() - 1);
        assertThat(testCharacters.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testCharacters.getAdvClass()).isEqualTo(DEFAULT_ADV_CLASS);
        assertThat(testCharacters.getServer()).isEqualTo(DEFAULT_SERVER);
    }

    @Test
    @Transactional
    void createCharactersWithExistingId() throws Exception {
        // Create the Characters with an existing ID
        characters.setId(1L);

        int databaseSizeBeforeCreate = charactersRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restCharactersMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(characters)))
            .andExpect(status().isBadRequest());

        // Validate the Characters in the database
        List<Characters> charactersList = charactersRepository.findAll();
        assertThat(charactersList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = charactersRepository.findAll().size();
        // set the field null
        characters.setName(null);

        // Create the Characters, which fails.

        restCharactersMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(characters)))
            .andExpect(status().isBadRequest());

        List<Characters> charactersList = charactersRepository.findAll();
        assertThat(charactersList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkAdvClassIsRequired() throws Exception {
        int databaseSizeBeforeTest = charactersRepository.findAll().size();
        // set the field null
        characters.setAdvClass(null);

        // Create the Characters, which fails.

        restCharactersMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(characters)))
            .andExpect(status().isBadRequest());

        List<Characters> charactersList = charactersRepository.findAll();
        assertThat(charactersList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkServerIsRequired() throws Exception {
        int databaseSizeBeforeTest = charactersRepository.findAll().size();
        // set the field null
        characters.setServer(null);

        // Create the Characters, which fails.

        restCharactersMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(characters)))
            .andExpect(status().isBadRequest());

        List<Characters> charactersList = charactersRepository.findAll();
        assertThat(charactersList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllCharacters() throws Exception {
        // Initialize the database
        charactersRepository.saveAndFlush(characters);

        // Get all the charactersList
        restCharactersMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(characters.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].advClass").value(hasItem(DEFAULT_ADV_CLASS.toString())))
            .andExpect(jsonPath("$.[*].server").value(hasItem(DEFAULT_SERVER.toString())));
    }

    @SuppressWarnings({ "unchecked" })
    void getAllCharactersWithEagerRelationshipsIsEnabled() throws Exception {
        when(charactersServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restCharactersMockMvc.perform(get(ENTITY_API_URL + "?eagerload=true")).andExpect(status().isOk());

        verify(charactersServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({ "unchecked" })
    void getAllCharactersWithEagerRelationshipsIsNotEnabled() throws Exception {
        when(charactersServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restCharactersMockMvc.perform(get(ENTITY_API_URL + "?eagerload=true")).andExpect(status().isOk());

        verify(charactersServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @Test
    @Transactional
    void getCharacters() throws Exception {
        // Initialize the database
        charactersRepository.saveAndFlush(characters);

        // Get the characters
        restCharactersMockMvc
            .perform(get(ENTITY_API_URL_ID, characters.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(characters.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.advClass").value(DEFAULT_ADV_CLASS.toString()))
            .andExpect(jsonPath("$.server").value(DEFAULT_SERVER.toString()));
    }

    @Test
    @Transactional
    void getNonExistingCharacters() throws Exception {
        // Get the characters
        restCharactersMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewCharacters() throws Exception {
        // Initialize the database
        charactersRepository.saveAndFlush(characters);

        int databaseSizeBeforeUpdate = charactersRepository.findAll().size();

        // Update the characters
        Characters updatedCharacters = charactersRepository.findById(characters.getId()).get();
        // Disconnect from session so that the updates on updatedCharacters are not directly saved in db
        em.detach(updatedCharacters);
        updatedCharacters.name(UPDATED_NAME).advClass(UPDATED_ADV_CLASS).server(UPDATED_SERVER);

        restCharactersMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedCharacters.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedCharacters))
            )
            .andExpect(status().isOk());

        // Validate the Characters in the database
        List<Characters> charactersList = charactersRepository.findAll();
        assertThat(charactersList).hasSize(databaseSizeBeforeUpdate);
        Characters testCharacters = charactersList.get(charactersList.size() - 1);
        assertThat(testCharacters.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testCharacters.getAdvClass()).isEqualTo(UPDATED_ADV_CLASS);
        assertThat(testCharacters.getServer()).isEqualTo(UPDATED_SERVER);
    }

    @Test
    @Transactional
    void putNonExistingCharacters() throws Exception {
        int databaseSizeBeforeUpdate = charactersRepository.findAll().size();
        characters.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCharactersMockMvc
            .perform(
                put(ENTITY_API_URL_ID, characters.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(characters))
            )
            .andExpect(status().isBadRequest());

        // Validate the Characters in the database
        List<Characters> charactersList = charactersRepository.findAll();
        assertThat(charactersList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchCharacters() throws Exception {
        int databaseSizeBeforeUpdate = charactersRepository.findAll().size();
        characters.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCharactersMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(characters))
            )
            .andExpect(status().isBadRequest());

        // Validate the Characters in the database
        List<Characters> charactersList = charactersRepository.findAll();
        assertThat(charactersList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamCharacters() throws Exception {
        int databaseSizeBeforeUpdate = charactersRepository.findAll().size();
        characters.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCharactersMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(characters)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Characters in the database
        List<Characters> charactersList = charactersRepository.findAll();
        assertThat(charactersList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateCharactersWithPatch() throws Exception {
        // Initialize the database
        charactersRepository.saveAndFlush(characters);

        int databaseSizeBeforeUpdate = charactersRepository.findAll().size();

        // Update the characters using partial update
        Characters partialUpdatedCharacters = new Characters();
        partialUpdatedCharacters.setId(characters.getId());

        restCharactersMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedCharacters.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedCharacters))
            )
            .andExpect(status().isOk());

        // Validate the Characters in the database
        List<Characters> charactersList = charactersRepository.findAll();
        assertThat(charactersList).hasSize(databaseSizeBeforeUpdate);
        Characters testCharacters = charactersList.get(charactersList.size() - 1);
        assertThat(testCharacters.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testCharacters.getAdvClass()).isEqualTo(DEFAULT_ADV_CLASS);
        assertThat(testCharacters.getServer()).isEqualTo(DEFAULT_SERVER);
    }

    @Test
    @Transactional
    void fullUpdateCharactersWithPatch() throws Exception {
        // Initialize the database
        charactersRepository.saveAndFlush(characters);

        int databaseSizeBeforeUpdate = charactersRepository.findAll().size();

        // Update the characters using partial update
        Characters partialUpdatedCharacters = new Characters();
        partialUpdatedCharacters.setId(characters.getId());

        partialUpdatedCharacters.name(UPDATED_NAME).advClass(UPDATED_ADV_CLASS).server(UPDATED_SERVER);

        restCharactersMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedCharacters.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedCharacters))
            )
            .andExpect(status().isOk());

        // Validate the Characters in the database
        List<Characters> charactersList = charactersRepository.findAll();
        assertThat(charactersList).hasSize(databaseSizeBeforeUpdate);
        Characters testCharacters = charactersList.get(charactersList.size() - 1);
        assertThat(testCharacters.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testCharacters.getAdvClass()).isEqualTo(UPDATED_ADV_CLASS);
        assertThat(testCharacters.getServer()).isEqualTo(UPDATED_SERVER);
    }

    @Test
    @Transactional
    void patchNonExistingCharacters() throws Exception {
        int databaseSizeBeforeUpdate = charactersRepository.findAll().size();
        characters.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCharactersMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, characters.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(characters))
            )
            .andExpect(status().isBadRequest());

        // Validate the Characters in the database
        List<Characters> charactersList = charactersRepository.findAll();
        assertThat(charactersList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchCharacters() throws Exception {
        int databaseSizeBeforeUpdate = charactersRepository.findAll().size();
        characters.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCharactersMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(characters))
            )
            .andExpect(status().isBadRequest());

        // Validate the Characters in the database
        List<Characters> charactersList = charactersRepository.findAll();
        assertThat(charactersList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamCharacters() throws Exception {
        int databaseSizeBeforeUpdate = charactersRepository.findAll().size();
        characters.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCharactersMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(characters))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Characters in the database
        List<Characters> charactersList = charactersRepository.findAll();
        assertThat(charactersList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteCharacters() throws Exception {
        // Initialize the database
        charactersRepository.saveAndFlush(characters);

        int databaseSizeBeforeDelete = charactersRepository.findAll().size();

        // Delete the characters
        restCharactersMockMvc
            .perform(delete(ENTITY_API_URL_ID, characters.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Characters> charactersList = charactersRepository.findAll();
        assertThat(charactersList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
