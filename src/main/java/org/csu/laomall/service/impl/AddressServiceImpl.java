package org.csu.laomall.service.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import org.csu.laomall.entity.Area;
import org.csu.laomall.entity.User;
import org.csu.laomall.entity.UserAddress;
import org.csu.laomall.persistence.AreaMapper;
import org.csu.laomall.persistence.UserAddressMapper;
import org.csu.laomall.service.AddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service("addressService")
public class AddressServiceImpl implements AddressService {

    @Autowired
    private AreaMapper areaMapper;
    @Autowired
    private UserAddressMapper userAddressMapper;


    @Override
    public List<Area> getAllProvince() {
        return areaMapper.selectList(new QueryWrapper<Area>().eq("level", "1"));
    }

    @Override
    public List<Area> getAllCity(String provinceId) {
        return areaMapper.selectList(new QueryWrapper<Area>().eq("level", "2").eq("parent_id", provinceId));
    }

    @Override
    public List<Area> getAllDistrict(String cityId) {
        return areaMapper.selectList(new QueryWrapper<Area>().eq("level", "3").eq("parent_id", cityId));
    }

    @Override
    public String getNameByAreaId(String areaId) {
        Area area = areaMapper.selectById(areaId);
        StringBuilder name = new StringBuilder(area.getName());
        while (area.getLevel() != 1) {
            area = areaMapper.selectById(area.getParentId());
            name.insert(0, area.getName() + "-");
        }
        return name.toString();
    }

    @Override
    public boolean setAddressInfo(UserAddress userAddress, String areaId) {
        Area area3 = areaMapper.selectById(areaId);
        if (area3 == null || area3.getLevel() != 3) {
            return false;
        }
        userAddress.setDistrict(area3.getName());
        Area area2 = areaMapper.selectById(area3.getParentId());
        if (area2 == null || area2.getLevel() != 2) {
            return false;
        }
        userAddress.setCity(area2.getName());
        Area area1 = areaMapper.selectById(area2.getParentId());
        if (area1 == null || area1.getLevel() != 1) {
            return false;
        }
        userAddress.setProvince(area1.getName());
        return true;


    }

    @Override
    public int addAddress(UserAddress userAddress) {
        if (userAddress.getIsDefault().equals("是")) {
            UpdateWrapper<UserAddress> updateWrapper = new UpdateWrapper<>();
            updateWrapper.eq("user_id", userAddress.getUserId());
            updateWrapper.set("is_default", "否");
            userAddressMapper.update(null, updateWrapper);
        }
        return userAddressMapper.insert(userAddress);
    }

    @Override
    public UserAddress getAddressById(String addressId) {
        return userAddressMapper.selectById(addressId);
    }

    @Override
    public List<UserAddress> getAddressByUserId(String userId) {
        return userAddressMapper.selectList(new QueryWrapper<UserAddress>().eq("user_id", userId).eq("status", "正常"));
    }

    @Override
    public int updateAddress(UserAddress userAddress) {
        if (userAddress.getIsDefault().equals("是")) {
            UpdateWrapper<UserAddress> updateWrapper = new UpdateWrapper<>();
            updateWrapper.eq("user_id", userAddress.getUserId());
            updateWrapper.set("is_default", "否");
            userAddressMapper.update(null, updateWrapper);
        }
        return userAddressMapper.updateById(userAddress);
    }

    @Override
    public String getRawString(int addressId) {
        UserAddress userAddress = userAddressMapper.selectById(addressId);
        return userAddress.getProvince() + "-" + userAddress.getCity() + "-" + userAddress.getDistrict() + "-" + userAddress.getDetail();
    }
}
