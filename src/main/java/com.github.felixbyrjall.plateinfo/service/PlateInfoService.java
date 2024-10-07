package com.github.felixbyrjall.plateinfo.service;

import com.github.felixbyrjall.plateinfo.dto.PlateInfoDTO;

public interface PlateInfoService {
    PlateInfoDTO getPlateInfo(String licensePlate);
}
