package org.csu.laomall.service.impl;

import org.csu.laomall.entity.Carousel;
import org.csu.laomall.persistence.CarouselMapper;
import org.csu.laomall.service.CarouselService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("carouselService")
public class CarouselServiceImpl implements CarouselService {
    @Autowired
    private CarouselMapper carouselMapper;

    @Override
    public List<Carousel> getAllCarousel() {
        return carouselMapper.selectList(null);
    }

    @Override
    public boolean addCarousel(Carousel carousel) {
        carousel.setIsDeleted(0);
        return carouselMapper.insert(carousel) > 0;
    }

    @Override
    public boolean deleteCarousel(int id) {
        return carouselMapper.deleteById(id) > 0;
    }
}

