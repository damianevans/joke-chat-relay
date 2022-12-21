package com.asos.lightningtalks.cosmos.service;

import com.asos.lightningtalks.cosmos.model.Joke;
import com.azure.cosmos.*;
import com.azure.cosmos.models.CosmosDatabaseProperties;
import com.azure.cosmos.models.CosmosQueryRequestOptions;
import com.azure.cosmos.models.FeedResponse;
import com.azure.cosmos.util.CosmosPagedFlux;
import com.azure.cosmos.util.CosmosPagedIterable;
import reactor.core.publisher.Flux;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.stream.Collectors;
import java.util.stream.Stream;


public class CosmosRepository {
    private CosmosAsyncClient client;


    private CosmosAsyncDatabase database;
    private CosmosAsyncContainer container;

    private AccountSettings accountSettings;

    public CosmosRepository() {
        try {
            accountSettings = new AccountSettings()
                    .addCosmosDatabaseIdConfig()
                    .addCosmosAccountHostConfig()
                    .addCosmosContainerConfig()
                    .addCosmosMasterKeyConfig()
                    .build();

            client = new CosmosClientBuilder()
                    .endpoint(accountSettings.getCosmosHostConfig())
                    .key(accountSettings.getCosmosMasterKeyConfig())
                    .preferredRegions(Collections.singletonList("UK South"))
                    .consistencyLevel(ConsistencyLevel.EVENTUAL)
                    .contentResponseOnWriteEnabled(true)
                    .buildAsyncClient();
            CosmosPagedFlux<CosmosDatabaseProperties> databaseResponse = client.readAllDatabases();

            //.filter(p -> p);
            database = client.getDatabase(accountSettings.getCosmosDatabaseId());
            container = database.getContainer(accountSettings.getCosmosContainerConfig());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }


    }

    public ArrayList<Joke> queryItems() {

        CosmosQueryRequestOptions queryOptions = new CosmosQueryRequestOptions();
        queryOptions.setQueryMetricsEnabled(true);
        String sqlQuery = "SELECT * FROM c";
        CosmosPagedFlux<Joke> pagedFluxResponse = container.queryItems(sqlQuery, queryOptions, Joke.class);
        ArrayList<Joke> jokes = (ArrayList<Joke>) pagedFluxResponse.toStream().collect(Collectors.toList());
        return jokes;
//
//        FeedResponse<Joke> allJokes = (FeedResponse<Joke>) pagedFluxResponse.byPage(10).flatMap(
//                fluxResponse -> {
//                    fluxResponse.getResults().stream().collect(Collectors.toList());
//                    return Flux.empty();
//                }).blockLast();
//        return allJokes;
    }
}
