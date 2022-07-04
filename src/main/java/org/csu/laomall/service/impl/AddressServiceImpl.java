package org.csu.laomall.service.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.csu.laomall.entity.Area;
import org.csu.laomall.persistence.AreaMapper;
import org.csu.laomall.service.AddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service("addressService")
public class AddressServiceImpl implements AddressService {

    @Autowired
    private AreaMapper areaMapper;


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
}
