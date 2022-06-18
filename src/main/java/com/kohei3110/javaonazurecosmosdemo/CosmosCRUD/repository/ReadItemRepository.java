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
import com.azure.cosmos.models.PartitionKey;
import com.azure.cosmos.models.SqlParameter;
import com.azure.cosmos.models.SqlQuerySpec;
import com.kohei3110.javaonazurecosmosdemo.CosmosCRUD.model.Item;

import reactor.core.publisher.Mono;

public class ReadItemRepository {
    private CosmosClient cosmosClient;
    private CosmosDatabase  cosmosDatabase;
    private CosmosContainer cosmosContainer;
    private CosmosAsyncClient cosmosAsyncClient;
    private CosmosAsyncDatabase cosmosAsyncDatabase;
    private CosmosAsyncContainer cosmosAsyncContainer;

    private static final String DATABASE_ID = "Items";
    private static final String CONTAINER_ID = "Items";
    private static final String SQL_QUERY = "SELECT c.id, c.name FROM c";

    Logger logger = Logger.getLogger(CreateItemRepository.class.getName());

    public ReadItemRepository() {
        try {
            this.cosmosClient = new CosmosClientBuilder()
                .endpoint(System.getenv("COSMOSDB_ENDPOINT"))
                .key(System.getenv("COSMOSDB_KEY"))
                .buildClient();
            this.cosmosDatabase = this.cosmosClient.getDatabase(DATABASE_ID);
            this.cosmosContainer = this.cosmosDatabase.getContainer(CONTAINER_ID);
            this.cosmosAsyncClient = new CosmosClientBuilder()
                .endpoint(System.getenv("COSMOSDB_ENDPOINT"))
                .key(System.getenv("COSMOSDB_KEY"))
                .buildAsyncClient();
            this.cosmosAsyncDatabase = this.cosmosAsyncClient.getDatabase(DATABASE_ID);
            this.cosmosAsyncContainer = this.cosmosAsyncDatabase.getContainer(CONTAINER_ID);
        } catch (CosmosException e) {
            logger.warning(e.getMessage());
        }
    }

    public Item requestCosmosDBSync(String id) {
        CosmosItemResponse<Item> response = this.cosmosContainer
            .readItem(id, new PartitionKey(id), Item.class);
        return response.getItem();
    }

    public Mono<CosmosItemResponse<Item>> requestCosmosDBAsync(String id) throws Exception {
        try {
            Mono<CosmosItemResponse<Item>> response = cosmosAsyncContainer.readItem(id, new PartitionKey(id), Item.class);
            response
                .doOnSubscribe(onSubscribe -> {
                    logger.info("subscribe!!");
                })
                .doOnSuccess(onSuccess -> {
                    logger.info("onSuccess!!");
                })
                .doFinally(onFinally -> {
                    logger.info("onFinally!!");
                })
                .subscribe();
            return response;
        } catch (Exception e) {
            logger.warning(e.getMessage());
            throw new Exception("read item operation has failed");
        }
    }

    private SqlQuerySpec getQuerySpec(String id) {
        StringBuilder queryStringBuilder = new StringBuilder();

        queryStringBuilder.append(SQL_QUERY);

        queryStringBuilder.append(" WHERE c.id = " + id);

        SqlParameter sqlParameter = new SqlParameter("@id", id);
        return new SqlQuerySpec(queryStringBuilder.toString(), sqlParameter);
    }
}