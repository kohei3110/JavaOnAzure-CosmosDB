package com.kohei3110.javaonazurecosmosdemo.CosmosCRUD.service;

import com.azure.cosmos.models.CosmosItemResponse;
import com.kohei3110.javaonazurecosmosdemo.CosmosCRUD.model.Item;
import com.kohei3110.javaonazurecosmosdemo.CosmosCRUD.repository.CreateItemRepository;

import org.springframework.stereotype.Service;

import reactor.core.publisher.Mono;

@Service
public class CreateItemService {
    
    private CreateItemRepository repository;

    public CreateItemService(CreateItemRepository repository) {
        this.repository = repository;
    }

    public CosmosItemResponse<Item> requestCosmosDBSync(Item item) throws Exception {
        return repository.createItemSync(item);
    }

    public Mono<CosmosItemResponse<Item>> requestCosmosDBAsync(Item item) throws Exception {
        return repository.createItemAsync(item);
    }
}
