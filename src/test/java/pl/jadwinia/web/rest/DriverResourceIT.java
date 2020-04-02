package pl.jadwinia.web.rest;

import pl.jadwinia.JhipsterTestApp;
import pl.jadwinia.domain.Driver;
import pl.jadwinia.repository.DriverRepository;

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
 * Integration tests for the {@link DriverResource} REST controller.
 */
@SpringBootTest(classes = JhipsterTestApp.class)

@AutoConfigureMockMvc
@WithMockUser
public class DriverResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_LAST_NAME = "AAAAAAAAAA";
    private static final String UPDATED_LAST_NAME = "BBBBBBBBBB";

    private static final Integer DEFAULT_MILEAGE = 1;
    private static final Integer UPDATED_MILEAGE = 2;

    @Autowired
    private DriverRepository driverRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restDriverMockMvc;

    private Driver driver;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Driver createEntity(EntityManager em) {
        Driver driver = new Driver()
            .name(DEFAULT_NAME)
            .lastName(DEFAULT_LAST_NAME)
            .mileage(DEFAULT_MILEAGE);
        return driver;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Driver createUpdatedEntity(EntityManager em) {
        Driver driver = new Driver()
            .name(UPDATED_NAME)
            .lastName(UPDATED_LAST_NAME)
            .mileage(UPDATED_MILEAGE);
        return driver;
    }

    @BeforeEach
    public void initTest() {
        driver = createEntity(em);
    }

    @Test
    @Transactional
    public void createDriver() throws Exception {
        int databaseSizeBeforeCreate = driverRepository.findAll().size();

        // Create the Driver
        restDriverMockMvc.perform(post("/api/drivers").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(driver)))
            .andExpect(status().isCreated());

        // Validate the Driver in the database
        List<Driver> driverList = driverRepository.findAll();
        assertThat(driverList).hasSize(databaseSizeBeforeCreate + 1);
        Driver testDriver = driverList.get(driverList.size() - 1);
        assertThat(testDriver.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testDriver.getLastName()).isEqualTo(DEFAULT_LAST_NAME);
        assertThat(testDriver.getMileage()).isEqualTo(DEFAULT_MILEAGE);
    }

    @Test
    @Transactional
    public void createDriverWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = driverRepository.findAll().size();

        // Create the Driver with an existing ID
        driver.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restDriverMockMvc.perform(post("/api/drivers").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(driver)))
            .andExpect(status().isBadRequest());

        // Validate the Driver in the database
        List<Driver> driverList = driverRepository.findAll();
        assertThat(driverList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllDrivers() throws Exception {
        // Initialize the database
        driverRepository.saveAndFlush(driver);

        // Get all the driverList
        restDriverMockMvc.perform(get("/api/drivers?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(driver.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].lastName").value(hasItem(DEFAULT_LAST_NAME)))
            .andExpect(jsonPath("$.[*].mileage").value(hasItem(DEFAULT_MILEAGE)));
    }
    
    @Test
    @Transactional
    public void getDriver() throws Exception {
        // Initialize the database
        driverRepository.saveAndFlush(driver);

        // Get the driver
        restDriverMockMvc.perform(get("/api/drivers/{id}", driver.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(driver.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.lastName").value(DEFAULT_LAST_NAME))
            .andExpect(jsonPath("$.mileage").value(DEFAULT_MILEAGE));
    }

    @Test
    @Transactional
    public void getNonExistingDriver() throws Exception {
        // Get the driver
        restDriverMockMvc.perform(get("/api/drivers/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateDriver() throws Exception {
        // Initialize the database
        driverRepository.saveAndFlush(driver);

        int databaseSizeBeforeUpdate = driverRepository.findAll().size();

        // Update the driver
        Driver updatedDriver = driverRepository.findById(driver.getId()).get();
        // Disconnect from session so that the updates on updatedDriver are not directly saved in db
        em.detach(updatedDriver);
        updatedDriver
            .name(UPDATED_NAME)
            .lastName(UPDATED_LAST_NAME)
            .mileage(UPDATED_MILEAGE);

        restDriverMockMvc.perform(put("/api/drivers").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedDriver)))
            .andExpect(status().isOk());

        // Validate the Driver in the database
        List<Driver> driverList = driverRepository.findAll();
        assertThat(driverList).hasSize(databaseSizeBeforeUpdate);
        Driver testDriver = driverList.get(driverList.size() - 1);
        assertThat(testDriver.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testDriver.getLastName()).isEqualTo(UPDATED_LAST_NAME);
        assertThat(testDriver.getMileage()).isEqualTo(UPDATED_MILEAGE);
    }

    @Test
    @Transactional
    public void updateNonExistingDriver() throws Exception {
        int databaseSizeBeforeUpdate = driverRepository.findAll().size();

        // Create the Driver

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restDriverMockMvc.perform(put("/api/drivers").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(driver)))
            .andExpect(status().isBadRequest());

        // Validate the Driver in the database
        List<Driver> driverList = driverRepository.findAll();
        assertThat(driverList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteDriver() throws Exception {
        // Initialize the database
        driverRepository.saveAndFlush(driver);

        int databaseSizeBeforeDelete = driverRepository.findAll().size();

        // Delete the driver
        restDriverMockMvc.perform(delete("/api/drivers/{id}", driver.getId()).with(csrf())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Driver> driverList = driverRepository.findAll();
        assertThat(driverList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
