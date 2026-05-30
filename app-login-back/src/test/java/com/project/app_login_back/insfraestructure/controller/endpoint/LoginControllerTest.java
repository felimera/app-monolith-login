package com.project.app_login_back.insfraestructure.controller.endpoint;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.app_login_back.application.dto.auth.LoginRequest;
import com.project.app_login_back.application.dto.auth.LoginResponse;
import com.project.app_login_back.application.dto.auth.TokenRequest;
import com.project.app_login_back.application.service.jwt.JwtFilter;
import com.project.app_login_back.application.service.jwt.JwtService;
import com.project.app_login_back.domain.service.IAuthService;
import com.project.app_login_back.insfraestructure.util.MessageUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.MessageSource;
import org.springframework.http.MediaType;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Locale;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = LoginController.class)
@AutoConfigureMockMvc(addFilters = false)
class LoginControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private JwtService jwtService;

    @MockitoBean
    private IAuthService iAuthService;

    @MockitoBean
    private UserDetailsService userDetailsService;

    @MockitoBean
    private JwtFilter jwtFilter;
    @MockitoBean
    private MessageSource messageSource;

    @BeforeEach
    void setUp() {
        MessageUtils.initialize(messageSource);

        // Creamos un comportamiento inteligente para el Mock
        Mockito.when(messageSource.getMessage(anyString(), any(), any(Locale.class)))
                .thenAnswer(invocation -> {
                    String key = invocation.getArgument(0); // Obtenemos la llave solicitada

                    // Si piden la propiedad del token, devolvemos el nombre correcto del campo JSON
                    if ("config.token".equals(key)) {
                        return "token";
                    }

                    // Para cualquier otra cosa (mensajes de error, etc.), devolvemos la llave misma
                    return key;
                });

        // Hacemos exactamente lo mismo para la variante del método con texto por defecto
        Mockito.when(messageSource.getMessage(anyString(), any(), anyString(), any(Locale.class)))
                .thenAnswer(invocation -> {
                    String key = invocation.getArgument(0);
                    if ("config.token".equals(key)) {
                        return "token";
                    }
                    return key;
                });
    }

    @Test
    @DisplayName("🔑 Login Exitoso - Debería retornar Token y datos con credenciales válidas")
    void login_DeberiaRetornarTokenYDatos_CuandoCredencialesSonCorrectas() throws Exception {
        // GIVEN
        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setIdentifier("test");
        loginRequest.setPassword("password123");

        LoginResponse mockResponse = new LoginResponse();
        mockResponse.setToken("eyJhbGciOiJIUzI1Ni...");
        mockResponse.setTokenType("Bearer");
        mockResponse.setFullName("test test");
        mockResponse.setCodeRole("DIRECT");

        Mockito.when(iAuthService.getUserPassword(any(LoginRequest.class))).thenReturn(Boolean.TRUE);
        Mockito.when(jwtService.login(any(LoginRequest.class))).thenReturn(mockResponse);

        // WHEN / THEN
        mockMvc.perform(post("/api/v1/login/in")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(loginRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.token").value("eyJhbGciOiJIUzI1Ni..."))
                .andExpect(jsonPath("$.codeRole").value("DIRECT"))
                .andExpect(jsonPath("$.fullName").value("test test"));
    }

    @Test
    @DisplayName("🔑 LoginApi Exitoso - Debería retornar Token con credenciales válidas")
    void loginApi_DeberiaRetornarToken_CuandoCredencialesSonCorrectas() throws Exception {
        // GIVEN
        TokenRequest tokenRequest = new TokenRequest();
        tokenRequest.setUsername("test");
        tokenRequest.setPassword("1234");

        String token = "eyJhbGciOiJIUzI1Ni...";

        Mockito.when(iAuthService.getUserPassword(any(LoginRequest.class))).thenReturn(Boolean.TRUE);
        Mockito.when(jwtService.crearTokenUsername(anyString())).thenReturn(token);

        // WHEN / THEN
        mockMvc.perform(post("/api/v1/login/token")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(tokenRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.token").value("eyJhbGciOiJIUzI1Ni..."));
    }

    @Test
    @DisplayName("❌ Login Fallido - Debería lanzar error 401 cuando el formato del email es inválido")
    void login_DeberiaLanzarBadRequest_CuandoEmailEsInvalido() throws Exception {
        // GIVEN
        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setIdentifier("test");
        loginRequest.setPassword("password123");

        Mockito.when(iAuthService.getUserPassword(any(LoginRequest.class))).thenReturn(Boolean.TRUE);
        Mockito.when(jwtService.login(any(LoginRequest.class))).thenReturn(null);

        // WHEN / THEN
        mockMvc.perform(post("/api/v1/login/in")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(loginRequest)))
                .andExpect(status().isUnauthorized());
    }

    @Test
    @DisplayName("❌ Login Fallido - Debería lanzar error 401 cuando el password es incorrecto.")
    void login_DeberiaLanzarUnauthorized_CuandoPasswordIncorrecto() throws Exception {
        // GIVEN
        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setIdentifier("test");
        loginRequest.setPassword("password123");

        Mockito.when(iAuthService.getUserPassword(any(LoginRequest.class))).thenReturn(Boolean.FALSE);

        // WHEN / THEN
        mockMvc.perform(post("/api/v1/login/in")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(loginRequest)))
                .andExpect(status().isUnauthorized());
    }
}