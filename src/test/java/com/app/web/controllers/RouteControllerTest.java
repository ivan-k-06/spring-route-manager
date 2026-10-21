package com.app.web.controllers;

import com.app.model.Route;
import com.app.repository.RouteRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.web.servlet.mvc.support.RedirectAttributesModelMap;
import java.util.Optional;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.within;
import static org.mockito.Mockito.*;

class RouteControllerTest {
    private final RouteRepository repository = mock(RouteRepository.class);
    private final RouteController controller = new RouteController(repository);
    private final MockHttpSession session = new MockHttpSession();
    private final RedirectAttributesModelMap flash = new RedirectAttributesModelMap();

    @BeforeEach
    void login() { session.setAttribute("user", "ivan"); }

    @Test
    void ownerCanDeleteTheirRoute() {
        Route route = new Route();
        route.setOwner("ivan");
        when(repository.findById(1L)).thenReturn(Optional.of(route));
        assertThat(controller.deleteRoute(1L, session, flash)).isEqualTo("redirect:/routes");
        verify(repository).delete(route);
        assertThat(flash.getFlashAttributes()).containsKey("toastSuccess");
    }

    @Test
    void anotherUsersRouteCannotBeDeleted() {
        Route route = new Route();
        route.setOwner("other");
        when(repository.findById(1L)).thenReturn(Optional.of(route));
        controller.deleteRoute(1L, session, flash);
        verify(repository, never()).delete(any(Route.class));
        assertThat(flash.getFlashAttributes()).containsKey("toastError");
    }

    @Test
    void missingRouteDoesNotCauseDeletion() {
        when(repository.findById(1L)).thenReturn(Optional.empty());
        assertThat(controller.deleteRoute(1L, session, flash)).isEqualTo("redirect:/routes");
        verify(repository, never()).delete(any(Route.class));
    }

    @Test
    void newRouteUsesLoggedInOwnerAndGeographicDistance() {
        assertThat(controller.addRoute("Trip", "A", 0d, 0d, "B", 0d, 1d, session, flash))
                .isEqualTo("redirect:/routes");
        ArgumentCaptor<Route> captured = ArgumentCaptor.forClass(Route.class);
        verify(repository).save(captured.capture());
        Route route = captured.getValue();
        assertThat(route.getOwner()).isEqualTo("ivan");
        assertThat(route.getName()).isEqualTo("Trip");
        assertThat(route.getDistance()).isCloseTo(111.195f, within(0.01f));
        assertThat(route.getFrom().getName()).isEqualTo("A");
        assertThat(route.getTo().getLongitude()).isEqualTo(1d);
        assertThat(route.getCoordinates().getLongitude()).isEqualTo(0.5d);
    }
}
