package com.github.felixbyrjall.plateinfo.service;

import com.github.felixbyrjall.plateinfo.dto.PlateInfoDTO;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.core.io.ResourceLoader;
import java.io.InputStream;

@Slf4j
@Service
public class PlateInfoServiceImpl implements PlateInfoService {

    private final JsonNode plateInfoData;

    public PlateInfoServiceImpl(ResourceLoader resourceLoader) throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        InputStream inputStream = resourceLoader.getResource("classpath:plate_info.json").getInputStream();
        this.plateInfoData = mapper.readTree(inputStream);
        log.info("Plate info data loaded successfully");
    }

    @Override
    public PlateInfoDTO getPlateInfo(String licensePlate) {
        String prefix = licensePlate.substring(0, 2).toUpperCase();
        log.info("Checking plate info for license plate \"{}\" with prefix: \"{}\"", licensePlate ,prefix);

        // Check special combinations first
        JsonNode specialCombinations = plateInfoData.path("Felles bokstavkombinasjoner");
        for (JsonNode combination : specialCombinations) {
            JsonNode bokstavkombinasjoner = combination.path("Bokstavkombinasjon");
            if (bokstavkombinasjoner.isArray()) {
                for (JsonNode bokstav : bokstavkombinasjoner) {
                    if (bokstav.asText().equals(prefix)) {
                        String vehicleType = combination.path("Kjøretøy").asText();
                        log.info("Match found in special combinations. Vehicle type: {}", vehicleType);
                        return createPlateInfoDTO(null, null, vehicleType);
                    }
                }
            }
        }

        // Check regular combinations
        JsonNode regularCombinations = plateInfoData.path("Bokstavkombinasjoner fra trafikkstasjon");
        for (JsonNode combination : regularCombinations) {
            JsonNode bokstavkombinasjoner = combination.path("Bokstavkombinasjon");
            if (bokstavkombinasjoner.isArray()) {
                for (JsonNode bokstav : bokstavkombinasjoner) {
                    if (bokstav.asText().equals(prefix)) {
                        String county = combination.path("Fylke").asText();
                        String area = combination.path("Geografisk område").asText();
                        log.info("Match found in regular combinations. County: {}, Area: {}", county, area);
                        return createPlateInfoDTO(county, area, null);
                    }
                }
            }
        }

        log.warn("No matching information found for license plate prefix: {}", prefix);
        return null; // No matching information found
    }

    private PlateInfoDTO createPlateInfoDTO(String county, String area, String vehicleType) {
        PlateInfoDTO dto = new PlateInfoDTO();
        dto.setCounty(county);
        dto.setGeographicalArea(area);
        dto.setVehicleType(vehicleType);
        log.debug("Created PlateInfoDTO - County: {}, Area: {}, Vehicle Type: {}", county, area, vehicleType);
        return dto;
    }
}
