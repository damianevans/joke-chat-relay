package com.asos.lightningtalks.cosmos.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Joke {


    private String id;
    private String partitionKey;
    @JsonProperty("joke")
    private ArrayList<String> jokeScript;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPartitionKey() {
        return partitionKey;
    }

    public void setPartitionKey(String partitionKey) {
        this.partitionKey = partitionKey;
    }

    public ArrayList<String> getJokeScript() {
        return jokeScript;
    }

    public void setJokeScript(ArrayList<String> jokeScript) {
        this.jokeScript = jokeScript;
    }

    public ArrayList<String> getFirstPersonLines() {
        return (ArrayList<String>) IntStream.range(0, this.jokeScript.size())
                .boxed()
                .filter( index -> index % 2 == 0)
                .map(index -> this.jokeScript.get(index))
                .collect(Collectors.toList());
    }

    public ArrayList<String> getSecondPersonLines() {
        return (ArrayList<String>) IntStream.range(0, this.jokeScript.size())
                .boxed()
                .filter( index -> index % 2 == 1)
                .map(index -> this.jokeScript.get(index))
                .collect(Collectors.toList());
    }
}
