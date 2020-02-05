package cs544_2020_01_light_attendanceproject.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import cs544_2020_01_light_attendanceproject.domain.Location;
import static org.assertj.core.api.Assertions.assertThat;

import cs544_2020_01_light_attendanceproject.service.LocationService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@WebMvcTest(controllers = LocationController.class)
class LocationControllerTest {
    @Autowired
    MockMvc mockMvc;

    @MockBean
    LocationService locationService;

    @Autowired
    private ObjectMapper objectMapper;

    static final List<Location> listOfMockedLocations = Arrays.asList(
            new Location(1L, "Location A"),
            new Location(2L, "Location B"),
            new Location(3L, "Location C")
    );

    public static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    @WithMockUser(value = "admin",roles={"ADMIN"})
    void retrieveAllLocations() throws Exception {
        when(locationService.findAllLocations()).thenReturn(listOfMockedLocations);

        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders
                .get("/locations")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
        ).andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();

        List<Location> returnedListOfLocations = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), new TypeReference<List<Location>>() {});

        assertThat(returnedListOfLocations).isEqualTo(listOfMockedLocations);
    }

    @Test
    @WithMockUser(value = "admin",roles={"ADMIN"})
    void retrieveLocationById() throws Exception {
        when(locationService.findLocationById(1)).thenReturn(Optional.of(listOfMockedLocations.get(0)));

        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders
                .get("/locations/id/1")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
        ).andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();

        Location returnedLocation = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), Location.class);

        assertThat(returnedLocation).isEqualTo(listOfMockedLocations.get(0));
    }

    @Test
    @WithMockUser(value = "admin",roles={"ADMIN"})
    void findByDescription() throws Exception{
        when(locationService.findLocationByDescription("Location B")).thenReturn(Optional.of(listOfMockedLocations.get(1)));

        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders
                .get("/locations/description/Location B")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
        ).andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();

        Location returnedLocation = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), Location.class);

        assertThat(returnedLocation).isEqualTo(listOfMockedLocations.get(1));
    }

    @Test
    @WithMockUser(value = "admin",roles={"ADMIN"})
    void createLocation() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                .post("/locations")
                .content(asJsonString(listOfMockedLocations.get(0)))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
        ).andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.redirectedUrlPattern("**/locations/id/1"));
    }

    @Test
    @WithMockUser(value = "admin",roles={"ADMIN"})
    void updateLocation() throws Exception {
        when(locationService.findLocationById(1L)).thenReturn(Optional.of(listOfMockedLocations.get(1)));
        when(locationService.updateLocation(any(Location.class))).thenReturn(null);
        when(locationService.updateLocation(listOfMockedLocations.get(1))).thenReturn(listOfMockedLocations.get(1));

        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders
                .put("/locations/1")
                .content(asJsonString(listOfMockedLocations.get(2)))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
        ).andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();

        Location returnedLocation = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), Location.class);

        assertThat(returnedLocation).isInstanceOf(Location.class);
        assertThat(returnedLocation).isNotNull();
        assertThat(returnedLocation).isEqualTo(new Location(2L, "Location C"));
    }

    @Test
    @WithMockUser(value = "admin",roles={"ADMIN"})
    void deleteLocation() throws Exception {
        when(locationService.findLocationById(1L)).thenReturn(Optional.of(listOfMockedLocations.get(0)));

        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders
                .delete("/locations/1")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
        ).andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();

        Location returnedLocation = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), Location.class);

        assertThat(returnedLocation).isInstanceOf(Location.class);
        assertThat(returnedLocation).isNotNull();
        assertThat(returnedLocation).isEqualTo(listOfMockedLocations.get(0));
    }
}