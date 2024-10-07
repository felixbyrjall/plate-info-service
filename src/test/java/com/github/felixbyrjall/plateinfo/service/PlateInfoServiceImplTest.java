package com.github.felixbyrjall.plateinfo.service;

import com.github.felixbyrjall.plateinfo.dto.PlateInfoDTO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class PlateInfoServiceImplTest {

    @Autowired
    private PlateInfoService plateInfoService;

    @Test
    void testSpecialPrefixCD() {
        PlateInfoDTO result = plateInfoService.getPlateInfo("CD1234");
        assertNotNull(result);
        assertEquals("Ambassadekjøretøy (Corps Diplomatique)", result.getVehicleType());
        assertNull(result.getCounty());
        assertNull(result.getGeographicalArea());
    }

    @Test
    void testRegularPrefixBN() {
        PlateInfoDTO result = plateInfoService.getPlateInfo("BN1234");
        assertNotNull(result);
        assertEquals("Akershus", result.getCounty());
        assertEquals("Asker og Bærum", result.getGeographicalArea());
        assertNull(result.getVehicleType());
    }

    @Test
    void testTUnknownPrefix() {
        PlateInfoDTO result = plateInfoService.getPlateInfo("XQ1234");
        assertNull(result);
    }
}
