package com.github.felixbyrjall.plateinfo.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@RequiredArgsConstructor
@Getter
@Setter
public class PlateInfoDTO {
    private String county; // Fylke
    private String geographicalArea;
    private String vehicleType; // For special combinations like "Elektriskdrevet kjøretøy"
}
