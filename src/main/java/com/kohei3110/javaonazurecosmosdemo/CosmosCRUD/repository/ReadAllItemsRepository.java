package com.kohei3110.javaonazurecosmosdemo.CosmosCRUD.repository;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import com.azure.cosmos.CosmosAsyncClient;
import com.azure.cosmos.CosmosAsyncContainer;
import com.azure.cosmos.CosmosAsyncDatabase;
import com.azure.cosmos.CosmosClient;
import com.azure.cosmos.CosmosClientBuilder;
import com.azure.cosmos.CosmosContainer;
import com.azure.cosmos.CosmosDatabase;
import com.azure.cosmos.CosmosException;
import com.azure.cosmos.models.CosmosQueryRequestOptions;
import com.azure.cosmos.models.FeedResponse;
import com.azure.cosmos.models.SqlQuerySpec;
import com.azure.cosmos.util.CosmosPagedFlux;
import com.kohei3110.javaonazurecosmosdemo.CosmosCRUD.model.Item;

import reactor.core.publisher.Flux;

public class ReadAllItemsRepository {
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

    public ReadAllItemsRepository() {
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

    public List<Item> requestCosmosDBSync() {
        Iterable<FeedResponse<Item>> feedResponseIterator = this.cosmosContainer
            .queryItems(getScanQuerySpec(), new CosmosQueryRequestOptions(), Item.class)
            .iterableByPage();
        List<Item> items = new ArrayList<Item>();
        for (FeedResponse<Item> page : feedResponseIterator) {
            for (Item item : page.getResults()) {
                items.add(item);
            }
        }
        return items;
    }

    public List<Item> requesetCosmosDBAsync() throws Exception {
        int preferredPageSize = 10;
        CosmosPagedFlux<Item> pagedFluxResponse = cosmosAsyncContainer.queryItems(getScanQuerySpec(), Item.class);
        try {
            List<Item> items = pagedFluxResponse.byPage(preferredPageSize)
                .flatMap(fluxResponse -> {
                    logger.info("Item: " + fluxResponse.getResults()
                        .stream()
                        .map(Item::getId)
                        .collect(Collectors.toList())
                    );
                    return Flux.fromIterable(Arrays.asList(fluxResponse.getResults()));
                }).blockLast();
            return items;
            } catch (Exception e) {
            logger.warning(e.getMessage());
            throw new Exception("read all items operation has failed");
        }
    }

    private SqlQuerySpec getScanQuerySpec() {
        StringBuilder queryStringBuilder = new StringBuilder();

        queryStringBuilder.append(SQL_QUERY);
        return new SqlQuerySpec(queryStringBuilder.toString());
    }
}
