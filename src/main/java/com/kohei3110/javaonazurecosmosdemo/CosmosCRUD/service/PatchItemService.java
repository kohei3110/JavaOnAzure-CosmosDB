package com.kohei3110.javaonazurecosmosdemo.CosmosCRUD.service;

import com.azure.cosmos.models.CosmosItemResponse;
import com.kohei3110.javaonazurecosmosdemo.CosmosCRUD.model.Item;
import com.kohei3110.javaonazurecosmosdemo.CosmosCRUD.repository.PatchItemRepository;

public class PatchItemService {
    
    private PatchItemRepository patchItemRepository;

    public PatchItemService(PatchItemRepository patchItemRepository) {
        this.patchItemRepository = patchItemRepository;
    }

    public CosmosItemResponse<Item> requestCosmosDBSync(Item item) {
        return this.patchItemRepository.requestCosmosDB(item);
    }
}
