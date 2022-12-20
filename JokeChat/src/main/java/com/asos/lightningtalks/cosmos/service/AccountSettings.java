package com.asos.lightningtalks.cosmos.service;

import com.azure.cosmos.implementation.apachecommons.lang.StringUtils;

public class AccountSettings {
    public static String CONTAINER = System.getProperty("CONTAINER",
            StringUtils.defaultString(StringUtils.trimToNull(
                            System.getenv().get("CONTAINER")),
                    "funny-jokes"));

    public static String MASTER_KEY =
            System.getProperty("ACCOUNT_KEY",
                    StringUtils.defaultString(StringUtils.trimToNull(
                                    System.getenv().get("ACCOUNT_KEY")),
                            "cu4Vu3KlvQz3KfUuwlK8Sx25FrqkkteSI7zaclzWPvZPkHBRnxg5mh6l4ZCfCoRT9SZ1fzCfF0RSACDbVGfRug=="));

    public static String HOST =
            System.getProperty("ACCOUNT_HOST",
                    StringUtils.defaultString(StringUtils.trimToNull(
                                    System.getenv().get("ACCOUNT_HOST")),
                            "https://vnetdemo-cosmos-db.documents.azure.com:443/"));

    public static String DATABASE = System.getProperty("DATABASE_ID",
            StringUtils.defaultString(StringUtils.trimToNull(
                    System.getenv().get("DATABASE_ID")),
                    "jokes"
            ));
}
