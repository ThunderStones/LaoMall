package org.csu.laomall.controller.controller;

import org.csu.laomall.anotation.PassToken;
import org.csu.laomall.common.CommonResponse;
import org.csu.laomall.entity.Area;
import org.csu.laomall.service.AddressService;
import org.csu.laomall.vo.UserVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/address")
public class AddressController {
    @Autowired
    private AddressService addressService;

    @RequestMapping("/provinces")
    @PassToken
    public CommonResponse<List<Area>> getAllProvince() {
        return CommonResponse.createForSuccess(addressService.getAllProvince());
    }

    @RequestMapping("/province/{id}/cities")
    @PassToken
    public CommonResponse<List<Area>> getAllCity(@PathVariable String id) {
        return CommonResponse.createForSuccess(addressService.getAllCity(id));
    }

    @RequestMapping("/city/{id}/districts")
    @PassToken
    public CommonResponse<List<Area>> getAllDistrict(@PathVariable String id) {
        return CommonResponse.createForSuccess(addressService.getAllDistrict(id));
    }


}
