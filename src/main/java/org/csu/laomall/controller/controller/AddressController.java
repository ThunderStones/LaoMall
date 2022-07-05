package org.csu.laomall.controller.controller;

import org.csu.laomall.anotation.PassToken;
import org.csu.laomall.common.CommonResponse;
import org.csu.laomall.entity.Area;
import org.csu.laomall.entity.UserAddress;
import org.csu.laomall.service.AddressService;
import org.csu.laomall.vo.UserVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
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

    @PostMapping("")
    public CommonResponse<UserAddress> addAddress(@RequestBody UserAddress userAddress, HttpServletRequest request) {
        String userId = ((UserVO) request.getAttribute("user")).getUserId();
        System.out.println(userAddress);
        if (userAddress.getAreaId() != null) {
            if (!addressService.setAddressInfo(userAddress, userAddress.getAreaId())) {
                return CommonResponse.createForError("AreaId 不是三级地址Id");
            }
        }
        userAddress.setUserId(userId);
        userAddress.setStatus("正常");
        if (addressService.addAddress(userAddress) > 0) {
            return CommonResponse.createForSuccessMessage("添加成功");
        } else {
            return CommonResponse.createForError("添加失败");
        }
    }

    @GetMapping("")
    public CommonResponse<List<UserAddress>> getAddressByUserId(HttpServletRequest request) {
        String userId = ((UserVO) request.getAttribute("user")).getUserId();
        return CommonResponse.createForSuccess(addressService.getAddressByUserId(userId));
    }

    @PutMapping("")
    public CommonResponse<UserAddress> updateAddress( @RequestBody UserAddress userAddress, HttpServletRequest request) {
        String userId = ((UserVO) request.getAttribute("user")).getUserId();
        if (!userId.equalsIgnoreCase(userAddress.getUserId())) {
            return CommonResponse.createForError("该用户地址id所对应的地址不属于该用户");
        }
        if (userAddress.getAreaId() != null) {
            if (!addressService.setAddressInfo(userAddress, userAddress.getAreaId())) {
                return CommonResponse.createForError("AreaId 不是三级地址Id");
            }
        }
        userAddress.setUserId(userId);
        if (addressService.updateAddress(userAddress) > 0) {
            return CommonResponse.createForSuccessMessage("更新成功");
        } else {
            return CommonResponse.createForError("更新失败");
        }
    }


}
