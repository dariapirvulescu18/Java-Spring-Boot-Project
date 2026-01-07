package dpirvulescu.hotelManagement.controllerTests;

import com.fasterxml.jackson.databind.ObjectMapper;
import dpirvulescu.hotelManagement.controller.HotelPackageController;
import dpirvulescu.hotelManagement.model.HotelPackage;
import dpirvulescu.hotelManagement.service.HotelPackageService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import java.util.List;
import java.util.Optional;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;



@WebMvcTest(HotelPackageController.class)
class HotelPackageControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private HotelPackageService packageService;

    private final ObjectMapper objectMapper = new ObjectMapper();

    private HotelPackage samplePackage() {
        HotelPackage hp = new HotelPackage(
                "Romantic Getaway",
                true,
                true,
                false,
                10
        );
        hp.setId(1);
        return hp;
    }


    @Test
    void getAllPackages_shouldReturn200AndList() throws Exception {
        when(packageService.getAllPackages()).thenReturn(List.of(samplePackage()));

        mockMvc.perform(get("/rest/packages/get/all"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value("Romantic Getaway"))
                .andExpect(jsonPath("$[0].quantity").value(10));
    }


    @Test
    void getPackageById_found_shouldReturn200() throws Exception {
        when(packageService.getPackageById(1)).thenReturn(Optional.of(samplePackage()));

        mockMvc.perform(get("/rest/packages/get?id=1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Romantic Getaway"))
                .andExpect(jsonPath("$.quantity").value(10));
    }

    @Test
    void getPackageById_notFound_shouldReturn404() throws Exception {
        when(packageService.getPackageById(1)).thenReturn(Optional.empty());

        mockMvc.perform(get("/rest/packages/get?id=1"))
                .andExpect(status().isNotFound());
    }


    @Test
    void createPackage_valid_shouldReturn200() throws Exception {
        HotelPackage input = samplePackage();
        input.setId(null);

        when(packageService.createPackage(any(HotelPackage.class))).thenReturn(samplePackage());

        mockMvc.perform(post("/rest/packages/create")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(input)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Romantic Getaway"))
                .andExpect(jsonPath("$.quantity").value(10));
    }

    @Test
    void createPackage_invalid_shouldReturn400() throws Exception {
        HotelPackage invalid = new HotelPackage(
                "",   // invalid name (size min 1)
                true,
                false,
                false,
                5
        );

        mockMvc.perform(post("/rest/packages/create")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(invalid)))
                .andExpect(status().isBadRequest());
    }


    @Test
    void updatePackage_found_shouldReturn200() throws Exception {
        when(packageService.updatePackage(eq(1), any(HotelPackage.class)))
                .thenReturn(Optional.of(samplePackage()));

        mockMvc.perform(put("/rest/packages/update?id=1")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(samplePackage())))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Romantic Getaway"));
    }

    @Test
    void updatePackage_notFound_shouldReturn404() throws Exception {
        when(packageService.updatePackage(eq(1), any(HotelPackage.class)))
                .thenReturn(Optional.empty());

        mockMvc.perform(put("/rest/packages/update?id=1")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(samplePackage())))
                .andExpect(status().isNotFound());
    }


    @Test
    void deletePackage_found_shouldReturn204() throws Exception {
        when(packageService.deletePackage(1)).thenReturn(true);

        mockMvc.perform(delete("/rest/packages/delete?id=1"))
                .andExpect(status().isNoContent());
    }

    @Test
    void deletePackage_notFound_shouldReturn404() throws Exception {
        when(packageService.deletePackage(1)).thenReturn(false);

        mockMvc.perform(delete("/rest/packages/delete?id=1"))
                .andExpect(status().isNotFound());
    }
}