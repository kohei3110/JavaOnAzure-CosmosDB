package com.kohei3110.javaonazurecosmosdemo.CosmosCRUD.repository;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.logging.Logger;

import com.azure.cosmos.CosmosAsyncClient;
import com.azure.cosmos.CosmosAsyncContainer;
import com.azure.cosmos.CosmosAsyncDatabase;
import com.azure.cosmos.CosmosClient;
import com.azure.cosmos.CosmosClientBuilder;
import com.azure.cosmos.CosmosContainer;
import com.azure.cosmos.CosmosDatabase;
import com.azure.cosmos.CosmosException;
import com.azure.cosmos.models.CosmosItemRequestOptions;
import com.azure.cosmos.models.CosmosItemResponse;
import com.azure.cosmos.models.PartitionKey;

public class DeleteItemRepository {
    private CosmosClient cosmosClient;
    private CosmosDatabase  cosmosDatabase;
    private CosmosContainer cosmosContainer;
    private CosmosAsyncClient cosmosAsyncClient;
    private CosmosAsyncDatabase cosmosAsyncDatabase;
    private CosmosAsyncContainer cosmosAsyncContainer;

    private static final String DATABASE_ID = "Items";
    private static final String CONTAINER_ID = "Items";
    private static final List<String> PREFERRED_REGIONS;
    static {
        List<String> regions = new ArrayList<String>();
        regions.add("East US");
        PREFERRED_REGIONS = Collections.unmodifiableList(regions);
    }

    Logger logger = Logger.getLogger(DeleteItemRepository.class.getName());

    public DeleteItemRepository() {
        try {
            // 可用性を意識したクライアント
            this.cosmosClient = new CosmosClientBuilder()
                .endpoint(System.getenv("COSMOSDB_ENDPOINT"))
                .key(System.getenv("COSMOSDB_KEY"))
                .contentResponseOnWriteEnabled(true)
                .preferredRegions(PREFERRED_REGIONS)
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
                .getContainer(CONTAINER_ID);
        } catch (CosmosException e) {
            logger.warning(e.getMessage());
        }
    }
    
    public CosmosItemResponse<Object> requestCosmosDBSync(String id) throws Exception {
        try {
            return this.cosmosContainer.deleteItem(id, new PartitionKey(id), new CosmosItemRequestOptions());
        } catch (Exception e) {
            logger.warning(e.getMessage());
            throw new Exception("delete item operation has failed");
        }
    }

    public CosmosItemResponse<Object> requesetCosmosDBAsync(String id) throws Exception {
        try {
            return this.cosmosAsyncContainer.deleteItem(id, new PartitionKey(id)).block();
        } catch (Exception e) {
            logger.warning(e.getMessage());
            throw new Exception("delete item operation has failed");
        }
    }
}
