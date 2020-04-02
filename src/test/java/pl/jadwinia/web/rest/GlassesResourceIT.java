package pl.jadwinia.web.rest;

import pl.jadwinia.JhipsterTestApp;
import pl.jadwinia.domain.Glasses;
import pl.jadwinia.repository.GlassesRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import javax.persistence.EntityManager;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link GlassesResource} REST controller.
 */
@SpringBootTest(classes = JhipsterTestApp.class)

@AutoConfigureMockMvc
@WithMockUser
public class GlassesResourceIT {

    private static final String DEFAULT_MODEL = "AAAAAAAAAA";
    private static final String UPDATED_MODEL = "BBBBBBBBBB";

    private static final String DEFAULT_FRONT = "AAAAAAAAAA";
    private static final String UPDATED_FRONT = "BBBBBBBBBB";

    private static final String DEFAULT_TEMPLES = "AAAAAAAAAA";
    private static final String UPDATED_TEMPLES = "BBBBBBBBBB";

    private static final String DEFAULT_LENSES = "AAAAAAAAAA";
    private static final String UPDATED_LENSES = "BBBBBBBBBB";

    private static final String DEFAULT_SIZE = "AAAAAAAAAA";
    private static final String UPDATED_SIZE = "BBBBBBBBBB";

    @Autowired
    private GlassesRepository glassesRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restGlassesMockMvc;

    private Glasses glasses;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Glasses createEntity(EntityManager em) {
        Glasses glasses = new Glasses()
            .model(DEFAULT_MODEL)
            .front(DEFAULT_FRONT)
            .temples(DEFAULT_TEMPLES)
            .lenses(DEFAULT_LENSES)
            .size(DEFAULT_SIZE);
        return glasses;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Glasses createUpdatedEntity(EntityManager em) {
        Glasses glasses = new Glasses()
            .model(UPDATED_MODEL)
            .front(UPDATED_FRONT)
            .temples(UPDATED_TEMPLES)
            .lenses(UPDATED_LENSES)
            .size(UPDATED_SIZE);
        return glasses;
    }

    @BeforeEach
    public void initTest() {
        glasses = createEntity(em);
    }

    @Test
    @Transactional
    public void createGlasses() throws Exception {
        int databaseSizeBeforeCreate = glassesRepository.findAll().size();

        // Create the Glasses
        restGlassesMockMvc.perform(post("/api/glasses").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(glasses)))
            .andExpect(status().isCreated());

        // Validate the Glasses in the database
        List<Glasses> glassesList = glassesRepository.findAll();
        assertThat(glassesList).hasSize(databaseSizeBeforeCreate + 1);
        Glasses testGlasses = glassesList.get(glassesList.size() - 1);
        assertThat(testGlasses.getModel()).isEqualTo(DEFAULT_MODEL);
        assertThat(testGlasses.getFront()).isEqualTo(DEFAULT_FRONT);
        assertThat(testGlasses.getTemples()).isEqualTo(DEFAULT_TEMPLES);
        assertThat(testGlasses.getLenses()).isEqualTo(DEFAULT_LENSES);
        assertThat(testGlasses.getSize()).isEqualTo(DEFAULT_SIZE);
    }

    @Test
    @Transactional
    public void createGlassesWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = glassesRepository.findAll().size();

        // Create the Glasses with an existing ID
        glasses.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restGlassesMockMvc.perform(post("/api/glasses").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(glasses)))
            .andExpect(status().isBadRequest());

        // Validate the Glasses in the database
        List<Glasses> glassesList = glassesRepository.findAll();
        assertThat(glassesList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllGlasses() throws Exception {
        // Initialize the database
        glassesRepository.saveAndFlush(glasses);

        // Get all the glassesList
        restGlassesMockMvc.perform(get("/api/glasses?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(glasses.getId().intValue())))
            .andExpect(jsonPath("$.[*].model").value(hasItem(DEFAULT_MODEL)))
            .andExpect(jsonPath("$.[*].front").value(hasItem(DEFAULT_FRONT)))
            .andExpect(jsonPath("$.[*].temples").value(hasItem(DEFAULT_TEMPLES)))
            .andExpect(jsonPath("$.[*].lenses").value(hasItem(DEFAULT_LENSES)))
            .andExpect(jsonPath("$.[*].size").value(hasItem(DEFAULT_SIZE)));
    }
    
    @Test
    @Transactional
    public void getGlasses() throws Exception {
        // Initialize the database
        glassesRepository.saveAndFlush(glasses);

        // Get the glasses
        restGlassesMockMvc.perform(get("/api/glasses/{id}", glasses.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(glasses.getId().intValue()))
            .andExpect(jsonPath("$.model").value(DEFAULT_MODEL))
            .andExpect(jsonPath("$.front").value(DEFAULT_FRONT))
            .andExpect(jsonPath("$.temples").value(DEFAULT_TEMPLES))
            .andExpect(jsonPath("$.lenses").value(DEFAULT_LENSES))
            .andExpect(jsonPath("$.size").value(DEFAULT_SIZE));
    }

    @Test
    @Transactional
    public void getNonExistingGlasses() throws Exception {
        // Get the glasses
        restGlassesMockMvc.perform(get("/api/glasses/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateGlasses() throws Exception {
        // Initialize the database
        glassesRepository.saveAndFlush(glasses);

        int databaseSizeBeforeUpdate = glassesRepository.findAll().size();

        // Update the glasses
        Glasses updatedGlasses = glassesRepository.findById(glasses.getId()).get();
        // Disconnect from session so that the updates on updatedGlasses are not directly saved in db
        em.detach(updatedGlasses);
        updatedGlasses
            .model(UPDATED_MODEL)
            .front(UPDATED_FRONT)
            .temples(UPDATED_TEMPLES)
            .lenses(UPDATED_LENSES)
            .size(UPDATED_SIZE);

        restGlassesMockMvc.perform(put("/api/glasses").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedGlasses)))
            .andExpect(status().isOk());

        // Validate the Glasses in the database
        List<Glasses> glassesList = glassesRepository.findAll();
        assertThat(glassesList).hasSize(databaseSizeBeforeUpdate);
        Glasses testGlasses = glassesList.get(glassesList.size() - 1);
        assertThat(testGlasses.getModel()).isEqualTo(UPDATED_MODEL);
        assertThat(testGlasses.getFront()).isEqualTo(UPDATED_FRONT);
        assertThat(testGlasses.getTemples()).isEqualTo(UPDATED_TEMPLES);
        assertThat(testGlasses.getLenses()).isEqualTo(UPDATED_LENSES);
        assertThat(testGlasses.getSize()).isEqualTo(UPDATED_SIZE);
    }

    @Test
    @Transactional
    public void updateNonExistingGlasses() throws Exception {
        int databaseSizeBeforeUpdate = glassesRepository.findAll().size();

        // Create the Glasses

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restGlassesMockMvc.perform(put("/api/glasses").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(glasses)))
            .andExpect(status().isBadRequest());

        // Validate the Glasses in the database
        List<Glasses> glassesList = glassesRepository.findAll();
        assertThat(glassesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteGlasses() throws Exception {
        // Initialize the database
        glassesRepository.saveAndFlush(glasses);

        int databaseSizeBeforeDelete = glassesRepository.findAll().size();

        // Delete the glasses
        restGlassesMockMvc.perform(delete("/api/glasses/{id}", glasses.getId()).with(csrf())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Glasses> glassesList = glassesRepository.findAll();
        assertThat(glassesList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
