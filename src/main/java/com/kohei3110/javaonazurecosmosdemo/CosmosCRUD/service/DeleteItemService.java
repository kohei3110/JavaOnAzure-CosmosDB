package com.kohei3110.javaonazurecosmosdemo.CosmosCRUD.service;

import org.springframework.stereotype.Service;

import com.azure.cosmos.models.CosmosItemResponse;
import com.kohei3110.javaonazurecosmosdemo.CosmosCRUD.repository.DeleteItemRepository;

@Service
public class DeleteItemService {
    
    private DeleteItemRepository repository;

    public DeleteItemService(DeleteItemRepository repository) {
        this.repository = repository;
    }

    public CosmosItemResponse<Object> requestCosmosDBSync(String id) throws Exception {
        return this.repository.requestCosmosDBSync(id);
    }

    public CosmosItemResponse<Object> requestCosmosDBAsync(String id) throws Exception {
        return this.repository.requesetCosmosDBAsync(id);
    }
}
