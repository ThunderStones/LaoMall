package org.csu.laomall.service;

import org.csu.laomall.entity.Carousel;

import java.util.List;

public interface CarouselService {
    List<Carousel> getAllCarousel();

    boolean addCarousel(Carousel carousel);

    boolean deleteCarousel(int id);
}
