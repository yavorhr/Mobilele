package com.example.mobilele.model.dto.view;

import java.util.ArrayList;
import java.util.List;

public class BrandViewModel {
    private String name;
    private List<ModelViewModel> models;

    public BrandViewModel() {
        this.models = new ArrayList<>();
    }

    public String getName() {
        return name.substring(0,1).toUpperCase() + name.substring(1);
    }

    public BrandViewModel setName(String name) {
        this.name = name;
        return this;
    }

    public List<ModelViewModel> getModels() {
        return models;
    }

    public BrandViewModel setModels(List<ModelViewModel> models) {
        this.models = models;
        return this;
    }

}
