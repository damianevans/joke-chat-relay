package com.asos.lightningtalks.cosmos.service;

import com.azure.cosmos.implementation.apachecommons.lang.StringUtils;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.*;

public class AccountSettings {

    private static final String containerKey = "CONTAINER";
    private static final String accountKey = "ACCOUNT_KEY";
    private static final String accountHostKey = "ACCOUNT_HOST";
    private static final String databaseIdKey = "DATABASE_ID";
    private List<String> requiredConfigProperties = new ArrayList<>();
    private Map<String, String> configuredProperties = new HashMap<>();
//    public static String CONTAINER = System.getProperty("CONTAINER",
//            StringUtils.defaultString(StringUtils.trimToNull(
//                            System.getenv().get("CONTAINER")),
//                    "funny-jokes"));
//
//    public static String MASTER_KEY =
//            System.getProperty("ACCOUNT_KEY",
//                    StringUtils.defaultString(StringUtils.trimToNull(
//                                    System.getenv().get("ACCOUNT_KEY")),
//                            "cu4Vu3KlvQz3KfUuwlK8Sx25FrqkkteSI7zaclzWPvZPkHBRnxg5mh6l4ZCfCoRT9SZ1fzCfF0RSACDbVGfRug=="));
//
//    public static String HOST =
//            System.getProperty("ACCOUNT_HOST",
//                    StringUtils.defaultString(StringUtils.trimToNull(
//                                    System.getenv().get("ACCOUNT_HOST")),
//                            "https://vnetdemo-cosmos-db.documents.azure.com:443/"));
//
//    public static String DATABASE = System.getProperty("DATABASE_ID",
//            StringUtils.defaultString(StringUtils.trimToNull(
//                    System.getenv().get("DATABASE_ID")),
//                    "jokes"
//            ));

    public AccountSettings addCosmosContainerConfig() {
        requiredConfigProperties.add(containerKey);
        return this;
    }

    public String getCosmosContainerConfig() {
        return configuredProperties.get(containerKey);
    }

    public AccountSettings addCosmosMasterKeyConfig() {
        requiredConfigProperties.add(accountKey);
        return this;
    }

    public String getCosmosMasterKeyConfig(){
        return configuredProperties.get(accountKey);
    }

    public AccountSettings addCosmosAccountHostConfig() {
        requiredConfigProperties.add(accountHostKey);
        return this;
    }

    public String getCosmosHostConfig() {
        return configuredProperties.get(accountHostKey);
    }

    public AccountSettings addCosmosDatabaseIdConfig() {
        requiredConfigProperties.add(databaseIdKey);
        return this;
    }

    public String getCosmosDatabaseId() {
        return configuredProperties.get(databaseIdKey);
    }

    public AccountSettings build() throws IOException {
        String rootPath = Thread.currentThread().getContextClassLoader().getResource("").getPath();
        String appConfigPath = rootPath + "config.properties";
        Properties appProps = new Properties();
        appProps.load(new FileInputStream(appConfigPath));

        requiredConfigProperties.stream().forEach(configItem -> {
            this.configuredProperties.put(configItem, appProps.getProperty(configItem));
        });
        return this;
    }
}
