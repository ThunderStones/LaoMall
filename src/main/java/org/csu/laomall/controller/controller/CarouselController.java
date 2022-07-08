package org.csu.laomall.controller.controller;

import org.csu.laomall.anotation.PassToken;
import org.csu.laomall.common.CommonResponse;
import org.csu.laomall.entity.Carousel;
import org.csu.laomall.service.CarouselService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class CarouselController {
    @Autowired
    private CarouselService carouselService;

    @GetMapping("/carousels")
    @PassToken
    public CommonResponse<List<Carousel>> getAllCarousel() {
        List<Carousel> carousels = carouselService.getAllCarousel();
        return CommonResponse.createForSuccess(carousels);

    }

    @PostMapping("/carousel")
    @PassToken
    public CommonResponse<Carousel> addCarousel(@RequestBody Carousel carousel) {
        carouselService.addCarousel(carousel);
        return CommonResponse.createForSuccess(carousel);
    }

    @DeleteMapping("/carousel/{id}")
    @PassToken
    public CommonResponse<Carousel> deleteCarousel(@PathVariable int id) {
        if (carouselService.deleteCarousel(id)) {
            return CommonResponse.createForSuccessMessage("删除成功");
        } else {
            return CommonResponse.createForError("删除失败");
        }
    }
}
