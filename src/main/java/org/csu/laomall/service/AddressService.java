package org.csu.laomall.service;

import org.csu.laomall.entity.Area;
import org.springframework.stereotype.Service;

import java.util.List;


public interface AddressService {
    List<Area> getAllProvince();
    List<Area> getAllCity(String provinceId);
    List<Area> getAllDistrict(String cityId);
    String getNameByAreaId(String areaId);
}
