package com.kohei3110.javaonazurecosmosdemo.CosmosCRUD.service;

import com.kohei3110.javaonazurecosmosdemo.CosmosCRUD.model.Item;
import com.kohei3110.javaonazurecosmosdemo.CosmosCRUD.repository.UpdateItemRepository;

public class UpdateItemService {
    private UpdateItemRepository repository;

    public UpdateItemService(UpdateItemRepository repository) {
        this.repository = repository;
    }

    public Item requestCosmosDBSync(Item item) throws Exception {
        return this.repository.requestCosmosDBSync(item);
    }

    public Item requestCosmosDBAsync(Item item) {
        return this.repository.requestCosmosDBAsync(item);
    }
}
