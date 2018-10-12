package ar.edu.um.ingenieria_aplicada.microblogging.web.rest;

import ar.edu.um.ingenieria_aplicada.microblogging.MicrobloggingApp;

import ar.edu.um.ingenieria_aplicada.microblogging.domain.Publicacion;
import ar.edu.um.ingenieria_aplicada.microblogging.repository.PublicacionRepository;
import ar.edu.um.ingenieria_aplicada.microblogging.web.rest.errors.ExceptionTranslator;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;


import static ar.edu.um.ingenieria_aplicada.microblogging.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the PublicacionResource REST controller.
 *
 * @see PublicacionResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = MicrobloggingApp.class)
public class PublicacionResourceIntTest {

    private static final Instant DEFAULT_FECHA = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_FECHA = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_CONTENIDO = "AAAAAAAAAA";
    private static final String UPDATED_CONTENIDO = "BBBBBBBBBB";

    private static final Boolean DEFAULT_VISIBILIDAD = false;
    private static final Boolean UPDATED_VISIBILIDAD = true;

    private static final String DEFAULT_PAIS = "AAAAAAAAAA";
    private static final String UPDATED_PAIS = "BBBBBBBBBB";

    private static final String DEFAULT_CIUDAD = "AAAAAAAAAA";
    private static final String UPDATED_CIUDAD = "BBBBBBBBBB";

    @Autowired
    private PublicacionRepository publicacionRepository;
    @Mock
    private PublicacionRepository publicacionRepositoryMock;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restPublicacionMockMvc;

    private Publicacion publicacion;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final PublicacionResource publicacionResource = new PublicacionResource(publicacionRepository);
        this.restPublicacionMockMvc = MockMvcBuilders.standaloneSetup(publicacionResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Publicacion createEntity(EntityManager em) {
        Publicacion publicacion = new Publicacion()
            .fecha(DEFAULT_FECHA)
            .contenido(DEFAULT_CONTENIDO)
            .visibilidad(DEFAULT_VISIBILIDAD)
            .pais(DEFAULT_PAIS)
            .ciudad(DEFAULT_CIUDAD);
        return publicacion;
    }

    @Before
    public void initTest() {
        publicacion = createEntity(em);
    }

    @Test
    @Transactional
    public void createPublicacion() throws Exception {
        int databaseSizeBeforeCreate = publicacionRepository.findAll().size();

        // Create the Publicacion
        restPublicacionMockMvc.perform(post("/api/publicacions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(publicacion)))
            .andExpect(status().isCreated());

        // Validate the Publicacion in the database
        List<Publicacion> publicacionList = publicacionRepository.findAll();
        assertThat(publicacionList).hasSize(databaseSizeBeforeCreate + 1);
        Publicacion testPublicacion = publicacionList.get(publicacionList.size() - 1);
        assertThat(testPublicacion.getFecha()).isEqualTo(DEFAULT_FECHA);
        assertThat(testPublicacion.getContenido()).isEqualTo(DEFAULT_CONTENIDO);
        assertThat(testPublicacion.isVisibilidad()).isEqualTo(DEFAULT_VISIBILIDAD);
        assertThat(testPublicacion.getPais()).isEqualTo(DEFAULT_PAIS);
        assertThat(testPublicacion.getCiudad()).isEqualTo(DEFAULT_CIUDAD);
    }

    @Test
    @Transactional
    public void createPublicacionWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = publicacionRepository.findAll().size();

        // Create the Publicacion with an existing ID
        publicacion.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restPublicacionMockMvc.perform(post("/api/publicacions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(publicacion)))
            .andExpect(status().isBadRequest());

        // Validate the Publicacion in the database
        List<Publicacion> publicacionList = publicacionRepository.findAll();
        assertThat(publicacionList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllPublicacions() throws Exception {
        // Initialize the database
        publicacionRepository.saveAndFlush(publicacion);

        // Get all the publicacionList
        restPublicacionMockMvc.perform(get("/api/publicacions?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(publicacion.getId().intValue())))
            .andExpect(jsonPath("$.[*].fecha").value(hasItem(DEFAULT_FECHA.toString())))
            .andExpect(jsonPath("$.[*].contenido").value(hasItem(DEFAULT_CONTENIDO.toString())))
            .andExpect(jsonPath("$.[*].visibilidad").value(hasItem(DEFAULT_VISIBILIDAD.booleanValue())))
            .andExpect(jsonPath("$.[*].pais").value(hasItem(DEFAULT_PAIS.toString())))
            .andExpect(jsonPath("$.[*].ciudad").value(hasItem(DEFAULT_CIUDAD.toString())));
    }
    
    public void getAllPublicacionsWithEagerRelationshipsIsEnabled() throws Exception {
        PublicacionResource publicacionResource = new PublicacionResource(publicacionRepositoryMock);
        when(publicacionRepositoryMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        MockMvc restPublicacionMockMvc = MockMvcBuilders.standaloneSetup(publicacionResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter).build();

        restPublicacionMockMvc.perform(get("/api/publicacions?eagerload=true"))
        .andExpect(status().isOk());

        verify(publicacionRepositoryMock, times(1)).findAllWithEagerRelationships(any());
    }

    public void getAllPublicacionsWithEagerRelationshipsIsNotEnabled() throws Exception {
        PublicacionResource publicacionResource = new PublicacionResource(publicacionRepositoryMock);
            when(publicacionRepositoryMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));
            MockMvc restPublicacionMockMvc = MockMvcBuilders.standaloneSetup(publicacionResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter).build();

        restPublicacionMockMvc.perform(get("/api/publicacions?eagerload=true"))
        .andExpect(status().isOk());

            verify(publicacionRepositoryMock, times(1)).findAllWithEagerRelationships(any());
    }

    @Test
    @Transactional
    public void getPublicacion() throws Exception {
        // Initialize the database
        publicacionRepository.saveAndFlush(publicacion);

        // Get the publicacion
        restPublicacionMockMvc.perform(get("/api/publicacions/{id}", publicacion.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(publicacion.getId().intValue()))
            .andExpect(jsonPath("$.fecha").value(DEFAULT_FECHA.toString()))
            .andExpect(jsonPath("$.contenido").value(DEFAULT_CONTENIDO.toString()))
            .andExpect(jsonPath("$.visibilidad").value(DEFAULT_VISIBILIDAD.booleanValue()))
            .andExpect(jsonPath("$.pais").value(DEFAULT_PAIS.toString()))
            .andExpect(jsonPath("$.ciudad").value(DEFAULT_CIUDAD.toString()));
    }
    @Test
    @Transactional
    public void getNonExistingPublicacion() throws Exception {
        // Get the publicacion
        restPublicacionMockMvc.perform(get("/api/publicacions/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePublicacion() throws Exception {
        // Initialize the database
        publicacionRepository.saveAndFlush(publicacion);

        int databaseSizeBeforeUpdate = publicacionRepository.findAll().size();

        // Update the publicacion
        Publicacion updatedPublicacion = publicacionRepository.findById(publicacion.getId()).get();
        // Disconnect from session so that the updates on updatedPublicacion are not directly saved in db
        em.detach(updatedPublicacion);
        updatedPublicacion
            .fecha(UPDATED_FECHA)
            .contenido(UPDATED_CONTENIDO)
            .visibilidad(UPDATED_VISIBILIDAD)
            .pais(UPDATED_PAIS)
            .ciudad(UPDATED_CIUDAD);

        restPublicacionMockMvc.perform(put("/api/publicacions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedPublicacion)))
            .andExpect(status().isOk());

        // Validate the Publicacion in the database
        List<Publicacion> publicacionList = publicacionRepository.findAll();
        assertThat(publicacionList).hasSize(databaseSizeBeforeUpdate);
        Publicacion testPublicacion = publicacionList.get(publicacionList.size() - 1);
        assertThat(testPublicacion.getFecha()).isEqualTo(UPDATED_FECHA);
        assertThat(testPublicacion.getContenido()).isEqualTo(UPDATED_CONTENIDO);
        assertThat(testPublicacion.isVisibilidad()).isEqualTo(UPDATED_VISIBILIDAD);
        assertThat(testPublicacion.getPais()).isEqualTo(UPDATED_PAIS);
        assertThat(testPublicacion.getCiudad()).isEqualTo(UPDATED_CIUDAD);
    }

    @Test
    @Transactional
    public void updateNonExistingPublicacion() throws Exception {
        int databaseSizeBeforeUpdate = publicacionRepository.findAll().size();

        // Create the Publicacion

        // If the entity doesn't have an ID, it will throw BadRequestAlertException 
        restPublicacionMockMvc.perform(put("/api/publicacions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(publicacion)))
            .andExpect(status().isBadRequest());

        // Validate the Publicacion in the database
        List<Publicacion> publicacionList = publicacionRepository.findAll();
        assertThat(publicacionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deletePublicacion() throws Exception {
        // Initialize the database
        publicacionRepository.saveAndFlush(publicacion);

        int databaseSizeBeforeDelete = publicacionRepository.findAll().size();

        // Get the publicacion
        restPublicacionMockMvc.perform(delete("/api/publicacions/{id}", publicacion.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Publicacion> publicacionList = publicacionRepository.findAll();
        assertThat(publicacionList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Publicacion.class);
        Publicacion publicacion1 = new Publicacion();
        publicacion1.setId(1L);
        Publicacion publicacion2 = new Publicacion();
        publicacion2.setId(publicacion1.getId());
        assertThat(publicacion1).isEqualTo(publicacion2);
        publicacion2.setId(2L);
        assertThat(publicacion1).isNotEqualTo(publicacion2);
        publicacion1.setId(null);
        assertThat(publicacion1).isNotEqualTo(publicacion2);
    }
}
