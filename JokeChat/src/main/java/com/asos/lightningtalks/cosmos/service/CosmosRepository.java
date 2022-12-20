package com.asos.lightningtalks.cosmos.service;

import com.asos.lightningtalks.cosmos.model.Joke;
import com.azure.cosmos.*;
import com.azure.cosmos.models.CosmosDatabaseProperties;
import com.azure.cosmos.models.CosmosQueryRequestOptions;
import com.azure.cosmos.models.FeedResponse;
import com.azure.cosmos.util.CosmosPagedFlux;
import com.azure.cosmos.util.CosmosPagedIterable;
import reactor.core.publisher.Flux;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.stream.Collectors;
import java.util.stream.Stream;


public class CosmosRepository {
    private CosmosAsyncClient client;


    private CosmosAsyncDatabase database;
    private CosmosAsyncContainer container;

    public CosmosRepository() {
        client = new CosmosClientBuilder()
                .endpoint(AccountSettings.HOST)
                .key(AccountSettings.MASTER_KEY)
                .preferredRegions(Collections.singletonList("UK South"))
                .consistencyLevel(ConsistencyLevel.EVENTUAL)
                .contentResponseOnWriteEnabled(true)
                .buildAsyncClient();
        CosmosPagedFlux<CosmosDatabaseProperties> databaseResponse = client.readAllDatabases();

                //.filter(p -> p);
        database = client.getDatabase(AccountSettings.DATABASE);
        container = database.getContainer(AccountSettings.CONTAINER);
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
