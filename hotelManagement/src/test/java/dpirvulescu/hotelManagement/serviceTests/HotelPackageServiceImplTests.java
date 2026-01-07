package dpirvulescu.hotelManagement.serviceTests;

import dpirvulescu.hotelManagement.model.HotelPackage;
import dpirvulescu.hotelManagement.repository.JPAHotelPackageRepository;
import dpirvulescu.hotelManagement.service.HotelPackageServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class HotelPackageServiceImplTests {

    @InjectMocks
    private HotelPackageServiceImpl hotelPackageService;

    @Mock
    private JPAHotelPackageRepository packageRepository;

    private HotelPackage package1;
    private HotelPackage package2;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        package1 = new HotelPackage("Romantic Getaway", true, true, false, 10);
        package1.setId(1);

        package2 = new HotelPackage("Family Fun", true, false, true, 5);
        package2.setId(2);
    }



    @Test
    void testGetAllPackages() {
        when(packageRepository.findAll()).thenReturn(List.of(package1, package2));

        List<HotelPackage> result = hotelPackageService.getAllPackages();

        assertEquals(2, result.size());
        assertTrue(result.contains(package1));
        assertTrue(result.contains(package2));
    }



    @Test
    void testGetPackageById_Found() {
        when(packageRepository.findById(1)).thenReturn(Optional.of(package1));

        Optional<HotelPackage> result = hotelPackageService.getPackageById(1);

        assertTrue(result.isPresent());
        assertEquals(package1, result.get());
    }

    @Test
    void testGetPackageById_NotFound() {
        when(packageRepository.findById(99)).thenReturn(Optional.empty());

        Optional<HotelPackage> result = hotelPackageService.getPackageById(99);

        assertFalse(result.isPresent());
    }



    @Test
    void testCreatePackage() {
        when(packageRepository.save(package1)).thenReturn(package1);

        HotelPackage result = hotelPackageService.createPackage(package1);

        assertEquals(package1, result);
    }



    @Test
    void testUpdatePackage_Success() {
        HotelPackage updatedDetails = new HotelPackage("Romantic Escape", false, true, true, 15);

        when(packageRepository.findById(1)).thenReturn(Optional.of(package1));
        when(packageRepository.save(any(HotelPackage.class))).thenAnswer(i -> i.getArgument(0));

        Optional<HotelPackage> result = hotelPackageService.updatePackage(1, updatedDetails);

        assertTrue(result.isPresent());
        assertEquals("Romantic Escape", result.get().getName());
        assertEquals(false, result.get().getBreakfastIncluded());
        assertEquals(true, result.get().getSpaIncluded());
        assertEquals(true, result.get().getPoolIncluded());
    }

    @Test
    void testUpdatePackage_NotFound() {
        HotelPackage updatedDetails = new HotelPackage("Romantic Escape", false, true, true, 15);

        when(packageRepository.findById(99)).thenReturn(Optional.empty());

        Optional<HotelPackage> result = hotelPackageService.updatePackage(99, updatedDetails);

        assertFalse(result.isPresent());
    }


    @Test
    void testDeletePackage_Success() {
        when(packageRepository.existsById(1)).thenReturn(true);
        doNothing().when(packageRepository).deleteById(1);

        boolean result = hotelPackageService.deletePackage(1);
        assertTrue(result);
        verify(packageRepository, times(1)).deleteById(1);
    }

    @Test
    void testDeletePackage_NotFound() {
        when(packageRepository.existsById(99)).thenReturn(false);

        boolean result = hotelPackageService.deletePackage(99);
        assertFalse(result);
        verify(packageRepository, never()).deleteById(99);
    }
}
