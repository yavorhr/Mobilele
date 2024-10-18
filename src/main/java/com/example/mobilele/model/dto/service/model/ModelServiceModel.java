package com.example.mobilele.model.dto.service.model;

import com.example.mobilele.model.dto.view.offer.OfferViewModelWithIdAndUrl;
import com.example.mobilele.model.entity.enums.CategoryTypeEnum;

import java.util.List;

public class ModelServiceModel {
    private Long id;
    private String name;
    private CategoryTypeEnum category;
    private Integer startYear;
    private Integer endYear;
    private List<OfferViewModelWithIdAndUrl> offers;

    public ModelServiceModel() {
    }


    public List<OfferViewModelWithIdAndUrl> getOffers() {
        return offers;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public CategoryTypeEnum getCategory() {
        return category;
    }

    public Integer getStartYear() {
        return startYear;
    }

    public Integer getEndYear() {
        return endYear;
    }

    public ModelServiceModel setId(Long id) {
        this.id = id;
        return this;
    }

    public ModelServiceModel setName(String name) {
        this.name = name;
        return this;
    }

    public ModelServiceModel setCategory(CategoryTypeEnum category) {
        this.category = category;
        return this;
    }

    public ModelServiceModel setStartYear(Integer startYear) {
        this.startYear = startYear;
        return this;
    }

    public ModelServiceModel setEndYear(Integer endYear) {
        this.endYear = endYear;
        return this;
    }

    public ModelServiceModel setOffers(List<OfferViewModelWithIdAndUrl> offers) {
        this.offers = offers;
        return this;
    }
}
