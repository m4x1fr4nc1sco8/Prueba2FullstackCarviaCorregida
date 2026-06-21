package cl.duoc.notificacion_service;

import cl.duoc.notificacion_service.controller.NotificacionController;
import cl.duoc.notificacion_service.model.Notificacion;
import cl.duoc.notificacion_service.service.NotificacionService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest(NotificacionController.class)
public class NotificacionControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private NotificacionService notificacionService;

    @Autowired
    private ObjectMapper objectMapper;

    private Notificacion notificacionPrueba;

    @BeforeEach
    void setUp() {
        notificacionPrueba = new Notificacion(
                1L,
                50L,
                "Tu vehículo está listo para retiro",
                "EMAIL",
                LocalDate.of(2026, 6, 21),
                "ENVIADA"
        );
    }


    @Test
    @DisplayName("POST /api/v1/notificaciones - Debe crear una notificación (HTTP 201)")
    void crearNotificacion_DeberiaRetornarStatusCreated() throws Exception {
        when(notificacionService.guardarNotificacion(any(Notificacion.class))).thenReturn(notificacionPrueba);

        mockMvc.perform(post("/api/v1/notificaciones")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(notificacionPrueba)))
                // Aquí usamos isCreated() porque en el Controller tienes @ResponseStatus(HttpStatus.CREATED)
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.mensaje").value("Tu vehículo está listo para retiro"));
    }

    @Test
    @DisplayName("DELETE /api/v1/notificaciones/{id} - Debe eliminar notificación (HTTP 204)")
    void eliminarNotificacion_DeberiaRetornarStatusNoContent() throws Exception {
        String mensajeEsperado = "Notificación eliminada correctamente";
        when(notificacionService.eliminarNotificacion(1L)).thenReturn(mensajeEsperado);

        mockMvc.perform(delete("/api/v1/notificaciones/{id}", 1L))
                // Usamos isNoContent() porque en el Controller tienes @ResponseStatus(HttpStatus.NO_CONTENT)
                .andExpect(status().isNoContent())
                .andExpect(content().string(mensajeEsperado));
    }
}