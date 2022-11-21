package com.kohei3110.javaonazurecosmosdemo.CosmosCRUD.repository;

import java.time.Duration;
import java.util.logging.Logger;

import com.azure.cosmos.CosmosAsyncClient;
import com.azure.cosmos.CosmosAsyncContainer;
import com.azure.cosmos.CosmosAsyncDatabase;
import com.azure.cosmos.CosmosClient;
import com.azure.cosmos.CosmosClientBuilder;
import com.azure.cosmos.CosmosContainer;
import com.azure.cosmos.CosmosDatabase;
import com.azure.cosmos.CosmosException;
import com.azure.cosmos.DirectConnectionConfig;
import com.azure.cosmos.GatewayConnectionConfig;
import com.azure.cosmos.models.CosmosItemResponse;
import com.kohei3110.javaonazurecosmosdemo.CosmosCRUD.model.Item;

import reactor.core.publisher.Mono;

public class CreateItemRepository {
    
    private CosmosClient cosmosClient;
    private CosmosDatabase  cosmosDatabase;
    private CosmosContainer cosmosContainer;
    private CosmosAsyncClient cosmosAsyncClient;
    private CosmosAsyncDatabase cosmosAsyncDatabase;
    private CosmosAsyncContainer cosmosAsyncContainer;

    private static final String DATABASE_ID = "Items";
    private static final String CONTAINER_ID = "Items";

    Logger logger = Logger.getLogger(CreateItemRepository.class.getName());

    public CreateItemRepository() {
        // 直接モード
        DirectConnectionConfig directConnectionConfig = DirectConnectionConfig.getDefaultConfig();
        directConnectionConfig.setMaxConnectionsPerEndpoint(120);
        directConnectionConfig.setIdleEndpointTimeout(Duration.ofMillis(100));
        try {
            this.cosmosClient = new CosmosClientBuilder()
                .endpoint(System.getenv("COSMOSDB_ENDPOINT"))
                .key(System.getenv("COSMOSDB_KEY"))
                .contentResponseOnWriteEnabled(true)
                .directMode(directConnectionConfig)
                .buildClient();
            this.cosmosDatabase = this.cosmosClient.getDatabase(DATABASE_ID);
            this.cosmosContainer = this.cosmosDatabase.getContainer(CONTAINER_ID);
        // ゲートウェイモード
        GatewayConnectionConfig gatewayConnectionConfig = GatewayConnectionConfig.getDefaultConfig();
        gatewayConnectionConfig.setMaxConnectionPoolSize(150);
        gatewayConnectionConfig.setIdleConnectionTimeout(Duration.ofSeconds(3));
            this.cosmosAsyncClient = new CosmosClientBuilder()
                .endpoint(System.getenv("COSMOSDB_ENDPOINT"))
                .key(System.getenv("COSMOSDB_KEY"))
                .contentResponseOnWriteEnabled(true)
                .gatewayMode(gatewayConnectionConfig)
                .buildAsyncClient();
            this.cosmosAsyncDatabase = this.cosmosAsyncClient.getDatabase(DATABASE_ID);
            this.cosmosAsyncContainer = this.cosmosAsyncDatabase
                .getContainer(CONTAINER_ID);
        } catch (CosmosException e) {
            logger.warning(e.getMessage());
        }
    }

    public CosmosItemResponse<Item> createItemSync(Item item) throws Exception {
        try {
            return cosmosContainer.createItem(item);
        } catch (Exception e) {
            logger.warning(e.getMessage());
            throw new Exception("create item operation has failed");
        }
    }

    public Mono<CosmosItemResponse<Item>> createItemAsync(Item item) throws Exception {
        try {
            return cosmosAsyncContainer.createItem(item);
        } catch (Exception e) {
            logger.warning(e.getMessage());
            throw new Exception("create item operation has failed");
        }
    }
}
