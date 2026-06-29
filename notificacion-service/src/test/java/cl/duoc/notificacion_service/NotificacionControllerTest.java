package cl.duoc.notificacion_service;

import cl.duoc.notificacion_service.controller.NotificacionController;
import cl.duoc.notificacion_service.exception.NotificacionNotExistException;
import cl.duoc.notificacion_service.model.Notificacion;
import cl.duoc.notificacion_service.service.NotificacionService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.Arrays;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest(NotificacionController.class)
@DisplayName("Pruebas en la capa Controller de Notificaciones")
public class NotificacionControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private NotificacionService notificacionService;

    private ObjectMapper objectMapper;
    private Notificacion notificacion;
    private Notificacion notificacionSinId;

    @BeforeEach
    void setUp() {
        objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());

        // Configurado según tu modelo Notificacion
        notificacion = new Notificacion();
        notificacion.setId(1L);
        notificacion.setUsuarioId(5L);
        notificacion.setMensaje("Tu vehículo está listo para retiro");
        notificacion.setTipoNotificacion("Informativa");
        notificacion.setFechaEnvio(LocalDate.now());
        notificacion.setEstadoNotificacion("Enviada");

        notificacionSinId = new Notificacion();
        notificacionSinId.setUsuarioId(5L);
        notificacionSinId.setMensaje("Tu vehículo está listo para retiro");
        notificacionSinId.setTipoNotificacion("Informativa");
        notificacionSinId.setFechaEnvio(LocalDate.now());
        notificacionSinId.setEstadoNotificacion("Enviada");
    }

    @Test
    @DisplayName("GET /api/v1/notificaciones - Debería retornar 200 OK con la lista")
    void testEndpointListar() throws Exception {
        when(notificacionService.obtenerNotificaciones()).thenReturn(Arrays.asList(notificacion));

        mockMvc.perform(get("/api/v1/notificaciones"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].mensaje").value("Tu vehículo está listo para retiro"));
    }

    @Test
    @DisplayName("GET /api/v1/notificaciones/{id} - Debería retornar 200 OK cuando existe")
    void testEndpointBuscarPorId_cuandoExiste() throws Exception {
        when(notificacionService.buscarNotificacionPorId(1L)).thenReturn(notificacion);

        mockMvc.perform(get("/api/v1/notificaciones/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.mensaje").value("Tu vehículo está listo para retiro"));
    }

    @Test
    @DisplayName("GET /api/v1/notificaciones/{id} - Debería retornar 404 NOT FOUND si no existe")
    void testEndpointBuscarPorId_cuandoNoExiste() throws Exception {
        when(notificacionService.buscarNotificacionPorId(99L))
                .thenThrow(new NotificacionNotExistException("Notificación no encontrada"));

        mockMvc.perform(get("/api/v1/notificaciones/99"))
                .andExpect(status().isNotFound()); // Estructura aprobada por el profesor
    }

    @Test
    @DisplayName("POST /api/v1/notificaciones - Debería retornar 201 CREATED")
    void testEndpointCrear() throws Exception {
        when(notificacionService.guardarNotificacion(any(Notificacion.class))).thenReturn(notificacion);

        mockMvc.perform(post("/api/v1/notificaciones")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(notificacionSinId)))
                .andExpect(status().isCreated()) // Valida código 201
                .andExpect(jsonPath("$.id").value(1));
    }

    @Test
    @DisplayName("DELETE /api/v1/notificaciones/{id} - Debería retornar 204 NO CONTENT")
    void testEndpointEliminar() throws Exception {
        // Como el servicio retorna un String, usamos `thenReturn` de manera segura para evitar fallos de Mockito
        when(notificacionService.eliminarNotificacion(1L)).thenReturn("Notificación eliminada correctamente");

        mockMvc.perform(delete("/api/v1/notificaciones/1"))
                .andExpect(status().isNoContent()); // Valida código 204
    }
}