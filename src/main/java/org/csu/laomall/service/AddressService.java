package org.csu.laomall.service;

import org.csu.laomall.entity.Area;
import org.csu.laomall.entity.UserAddress;
import org.springframework.stereotype.Service;

import java.util.List;


public interface AddressService {
    List<Area> getAllProvince();
    List<Area> getAllCity(String provinceId);
    List<Area> getAllDistrict(String cityId);
    String getNameByAreaId(String areaId);

    boolean setAddressInfo(UserAddress userAddress, String areaId);

    int addAddress(UserAddress userAddress);

    UserAddress getAddressById(String addressId);

    List<UserAddress> getAddressByUserId(String userId);

    int updateAddress(UserAddress userAddress);

    String getRawString(int addressId);
}
