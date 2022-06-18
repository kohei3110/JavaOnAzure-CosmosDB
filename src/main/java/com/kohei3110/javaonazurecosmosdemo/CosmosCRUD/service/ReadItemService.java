package com.kohei3110.javaonazurecosmosdemo.CosmosCRUD.service;

import org.springframework.stereotype.Service;

import com.azure.cosmos.models.CosmosItemResponse;
import com.kohei3110.javaonazurecosmosdemo.CosmosCRUD.model.Item;
import com.kohei3110.javaonazurecosmosdemo.CosmosCRUD.repository.ReadItemRepository;

import reactor.core.publisher.Mono;

@Service
public class ReadItemService {
    private ReadItemRepository repository;

    public ReadItemService(ReadItemRepository repository) {
        this.repository = repository;
    }

    public Item requestCosmosDBSync(String id) {
        return repository.requestCosmosDBSync(id);
    }

    public Mono<CosmosItemResponse<Item>> requestCosmosDBAsync(String id) throws Exception {
        return repository.requestCosmosDBAsync(id);
    }
}