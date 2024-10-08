package com.github.felixbyrjall.plateinfo.controller;

import com.github.felixbyrjall.plateinfo.dto.PlateInfoDTO;
import com.github.felixbyrjall.plateinfo.service.PlateInfoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/vehicle/plate-info")
public class PlateInfoController {

    private final PlateInfoService plateInfoService;

    public PlateInfoController(PlateInfoService plateInfoService) {
        this.plateInfoService = plateInfoService;
    }

    @GetMapping("/{vehicleId}")
    public ResponseEntity<PlateInfoDTO> getPlateInfo(@PathVariable String vehicleId) {
        PlateInfoDTO plateInfo = plateInfoService.getPlateInfo(vehicleId);
        if (plateInfo != null) {
            return ResponseEntity.ok(plateInfo);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
