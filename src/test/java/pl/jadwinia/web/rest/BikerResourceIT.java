package pl.jadwinia.web.rest;

import pl.jadwinia.JhipsterTestApp;
import pl.jadwinia.domain.Biker;
import pl.jadwinia.repository.BikerRepository;

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
 * Integration tests for the {@link BikerResource} REST controller.
 */
@SpringBootTest(classes = JhipsterTestApp.class)

@AutoConfigureMockMvc
@WithMockUser
public class BikerResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_LAST_NAME = "AAAAAAAAAA";
    private static final String UPDATED_LAST_NAME = "BBBBBBBBBB";

    private static final Integer DEFAULT_MILEAGE = 1;
    private static final Integer UPDATED_MILEAGE = 2;

    @Autowired
    private BikerRepository bikerRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restBikerMockMvc;

    private Biker biker;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Biker createEntity(EntityManager em) {
        Biker biker = new Biker()
            .name(DEFAULT_NAME)
            .lastName(DEFAULT_LAST_NAME)
            .mileage(DEFAULT_MILEAGE);
        return biker;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Biker createUpdatedEntity(EntityManager em) {
        Biker biker = new Biker()
            .name(UPDATED_NAME)
            .lastName(UPDATED_LAST_NAME)
            .mileage(UPDATED_MILEAGE);
        return biker;
    }

    @BeforeEach
    public void initTest() {
        biker = createEntity(em);
    }

    @Test
    @Transactional
    public void createBiker() throws Exception {
        int databaseSizeBeforeCreate = bikerRepository.findAll().size();

        // Create the Biker
        restBikerMockMvc.perform(post("/api/bikers").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(biker)))
            .andExpect(status().isCreated());

        // Validate the Biker in the database
        List<Biker> bikerList = bikerRepository.findAll();
        assertThat(bikerList).hasSize(databaseSizeBeforeCreate + 1);
        Biker testBiker = bikerList.get(bikerList.size() - 1);
        assertThat(testBiker.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testBiker.getLastName()).isEqualTo(DEFAULT_LAST_NAME);
        assertThat(testBiker.getMileage()).isEqualTo(DEFAULT_MILEAGE);
    }

    @Test
    @Transactional
    public void createBikerWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = bikerRepository.findAll().size();

        // Create the Biker with an existing ID
        biker.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restBikerMockMvc.perform(post("/api/bikers").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(biker)))
            .andExpect(status().isBadRequest());

        // Validate the Biker in the database
        List<Biker> bikerList = bikerRepository.findAll();
        assertThat(bikerList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllBikers() throws Exception {
        // Initialize the database
        bikerRepository.saveAndFlush(biker);

        // Get all the bikerList
        restBikerMockMvc.perform(get("/api/bikers?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(biker.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].lastName").value(hasItem(DEFAULT_LAST_NAME)))
            .andExpect(jsonPath("$.[*].mileage").value(hasItem(DEFAULT_MILEAGE)));
    }
    
    @Test
    @Transactional
    public void getBiker() throws Exception {
        // Initialize the database
        bikerRepository.saveAndFlush(biker);

        // Get the biker
        restBikerMockMvc.perform(get("/api/bikers/{id}", biker.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(biker.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.lastName").value(DEFAULT_LAST_NAME))
            .andExpect(jsonPath("$.mileage").value(DEFAULT_MILEAGE));
    }

    @Test
    @Transactional
    public void getNonExistingBiker() throws Exception {
        // Get the biker
        restBikerMockMvc.perform(get("/api/bikers/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateBiker() throws Exception {
        // Initialize the database
        bikerRepository.saveAndFlush(biker);

        int databaseSizeBeforeUpdate = bikerRepository.findAll().size();

        // Update the biker
        Biker updatedBiker = bikerRepository.findById(biker.getId()).get();
        // Disconnect from session so that the updates on updatedBiker are not directly saved in db
        em.detach(updatedBiker);
        updatedBiker
            .name(UPDATED_NAME)
            .lastName(UPDATED_LAST_NAME)
            .mileage(UPDATED_MILEAGE);

        restBikerMockMvc.perform(put("/api/bikers").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedBiker)))
            .andExpect(status().isOk());

        // Validate the Biker in the database
        List<Biker> bikerList = bikerRepository.findAll();
        assertThat(bikerList).hasSize(databaseSizeBeforeUpdate);
        Biker testBiker = bikerList.get(bikerList.size() - 1);
        assertThat(testBiker.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testBiker.getLastName()).isEqualTo(UPDATED_LAST_NAME);
        assertThat(testBiker.getMileage()).isEqualTo(UPDATED_MILEAGE);
    }

    @Test
    @Transactional
    public void updateNonExistingBiker() throws Exception {
        int databaseSizeBeforeUpdate = bikerRepository.findAll().size();

        // Create the Biker

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restBikerMockMvc.perform(put("/api/bikers").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(biker)))
            .andExpect(status().isBadRequest());

        // Validate the Biker in the database
        List<Biker> bikerList = bikerRepository.findAll();
        assertThat(bikerList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteBiker() throws Exception {
        // Initialize the database
        bikerRepository.saveAndFlush(biker);

        int databaseSizeBeforeDelete = bikerRepository.findAll().size();

        // Delete the biker
        restBikerMockMvc.perform(delete("/api/bikers/{id}", biker.getId()).with(csrf())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Biker> bikerList = bikerRepository.findAll();
        assertThat(bikerList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
