package com.kohei3110.javaonazurecosmosdemo.CosmosCRUD.service;

import com.azure.cosmos.models.CosmosItemResponse;
import com.kohei3110.javaonazurecosmosdemo.CosmosCRUD.model.Item;
import com.kohei3110.javaonazurecosmosdemo.CosmosCRUD.repository.CreateItemRepository;

import java.util.logging.Logger;

import org.springframework.stereotype.Service;

import reactor.core.publisher.Mono;

@Service
public class CreateItemService {
    
    private CreateItemRepository repository;

    Logger logger = Logger.getLogger(CreateItemService.class.getName());

    public CreateItemService(CreateItemRepository repository) {
        this.repository = repository;
    }

    public CosmosItemResponse<Item> requestCosmosDBSync(Item item) throws Exception {
        long start = System.currentTimeMillis();
        logger.info("=====CreateItemService Start=====");
        CosmosItemResponse<Item> response = repository.createItemSync(item);
        long end = System.currentTimeMillis();
        logger.info("=====CreateItemService End=====");
        logger.info("It took " + (end - start) + " ms in CreateItemService");
        return response;
    }

    public Mono<CosmosItemResponse<Item>> requestCosmosDBAsync(Item item) throws Exception {
        long start = System.currentTimeMillis();
        logger.info("=====CreateItemService Start=====");
        Mono<CosmosItemResponse<Item>> response = repository.createItemAsync(item);
        long end = System.currentTimeMillis();
        logger.info("=====CreateItemService End=====");
        logger.info("It took " + (end - start) + " ms in CreateItemService");
        return response;
    }
}
