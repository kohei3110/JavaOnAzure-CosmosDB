package com.kohei3110.javaonazurecosmosdemo.CosmosCRUD.repository;

import java.util.logging.Logger;

import com.azure.cosmos.CosmosAsyncClient;
import com.azure.cosmos.CosmosAsyncContainer;
import com.azure.cosmos.CosmosAsyncDatabase;
import com.azure.cosmos.CosmosClient;
import com.azure.cosmos.CosmosClientBuilder;
import com.azure.cosmos.CosmosContainer;
import com.azure.cosmos.CosmosDatabase;
import com.azure.cosmos.CosmosException;
import com.azure.cosmos.models.CosmosItemResponse;
import com.kohei3110.javaonazurecosmosdemo.CosmosCRUD.model.Item;

import reactor.core.publisher.Mono;

public class UpdateItemRepository {
    
    private CosmosClient cosmosClient;
    private CosmosDatabase  cosmosDatabase;
    private CosmosContainer cosmosContainer;
    private CosmosAsyncClient cosmosAsyncClient;
    private CosmosAsyncDatabase cosmosAsyncDatabase;
    private CosmosAsyncContainer cosmosAsyncContainer;

    private static final String DATABASE_ID = "Items";
    private static final String CONTAINER_ID = "Items";

    Logger logger = Logger.getLogger(UpdateItemRepository.class.getName());

    public UpdateItemRepository() {
        try {
            this.cosmosClient = new CosmosClientBuilder()
                .endpoint(System.getenv("COSMOSDB_ENDPOINT"))
                .key(System.getenv("COSMOSDB_KEY"))
                .contentResponseOnWriteEnabled(true)
                .buildClient();
            this.cosmosDatabase = this.cosmosClient.getDatabase(DATABASE_ID);
            this.cosmosContainer = this.cosmosDatabase.getContainer(CONTAINER_ID);
            this.cosmosAsyncClient = new CosmosClientBuilder()
                .endpoint(System.getenv("COSMOSDB_ENDPOINT"))
                .key(System.getenv("COSMOSDB_KEY"))
                .contentResponseOnWriteEnabled(true)
                .buildAsyncClient();
            this.cosmosAsyncDatabase = this.cosmosAsyncClient.getDatabase(DATABASE_ID);
            this.cosmosAsyncContainer = this.cosmosAsyncDatabase
                .getContainer(DATABASE_ID);
        } catch (CosmosException e) {
            logger.warning(e.getMessage());
        }
    }

    public Item requestCosmosDBSync(Item item) throws Exception {
        try {
            CosmosItemResponse<Item> response = this.cosmosContainer.upsertItem(item);
            return response.getItem();
        } catch (Exception e) {
            logger.warning(e.getMessage());
            throw new Exception("update item operation has failed");
        }
    }

    public Item requestCosmosDBAsync(Item item) {
        Mono.just(item)
            .flatMap(data -> {
                CosmosItemResponse<Item> response = this.cosmosAsyncContainer.upsertItem(item).block();
                logger.info(response.toString());
                return Mono.just(data);
            })
            .subscribe();
        return item;
    }
}