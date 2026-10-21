package com.app.web.controllers;

import com.app.model.User;
import com.app.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.mockito.ArgumentCaptor;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.ui.ExtendedModelMap;
import org.springframework.web.servlet.mvc.support.RedirectAttributesModelMap;
import java.util.Optional;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

class AuthControllerTest {
    private final UserRepository repository = mock(UserRepository.class);
    private final PasswordEncoder encoder = mock(PasswordEncoder.class);
    private final AuthController controller = new AuthController(repository, encoder);
    private final ExtendedModelMap model = new ExtendedModelMap();
    private final MockHttpSession session = new MockHttpSession();
    private final RedirectAttributesModelMap flash = new RedirectAttributesModelMap();

    @Test
    void correctPasswordCreatesAuthenticatedSession() {
        User user = new User();
        user.setLogin("ivan");
        user.setPasswordHash("hash");
        when(repository.findByLogin("ivan")).thenReturn(Optional.of(user));
        when(encoder.matches("secret", "hash")).thenReturn(true);
        assertThat(controller.processLogin("ivan", "secret", session, model)).isEqualTo("redirect:/routes");
        assertThat(session.getAttribute("user")).isEqualTo("ivan");
    }

    @Test
    void wrongPasswordDoesNotAuthenticate() {
        User user = new User();
        user.setPasswordHash("hash");
        when(repository.findByLogin("ivan")).thenReturn(Optional.of(user));
        assertThat(controller.processLogin("ivan", "wrong", session, model)).isEqualTo("login");
        assertThat(session.getAttribute("user")).isNull();
        assertThat(model).containsKey("error");
    }

    @Test
    void unknownUserDoesNotAuthenticate() {
        when(repository.findByLogin("missing")).thenReturn(Optional.empty());
        assertThat(controller.processLogin("missing", "secret", session, model)).isEqualTo("login");
        assertThat(session.getAttribute("user")).isNull();
        verifyNoInteractions(encoder);
    }

    @ParameterizedTest
    @CsvSource({"' ', secret", "ivan, ' '"})
    void blankCredentialsAreRejected(String login, String password) {
        assertThat(controller.processRegister(login, password, flash, model)).isEqualTo("register");
        assertThat(model).containsKey("error");
        verifyNoInteractions(repository, encoder);
    }

    @Test
    void duplicateLoginIsNotSaved() {
        when(repository.findByLogin("ivan")).thenReturn(Optional.of(new User()));
        assertThat(controller.processRegister("ivan", "secret", flash, model)).isEqualTo("register");
        verify(repository, never()).save(any(User.class));
        verifyNoInteractions(encoder);
    }

    @Test
    void registrationStoresEncodedPassword() {
        when(repository.findByLogin("ivan")).thenReturn(Optional.empty());
        when(encoder.encode("secret")).thenReturn("encoded");
        assertThat(controller.processRegister("ivan", "secret", flash, model)).isEqualTo("redirect:/login");
        ArgumentCaptor<User> captured = ArgumentCaptor.forClass(User.class);
        verify(repository).save(captured.capture());
        assertThat(captured.getValue().getLogin()).isEqualTo("ivan");
        assertThat(captured.getValue().getPasswordHash()).isEqualTo("encoded");
        assertThat(flash.getFlashAttributes()).containsKey("message");
    }
}
