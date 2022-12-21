package com.asos.lightningtalks.chat.tests;

import com.asos.lightningtalks.cosmos.model.Joke;
import com.asos.lightningtalks.cosmos.service.AccountSettings;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class AccountSettingsShould {
    @Test
    public void readConfigurationSettingsFromPropertiesFile() throws IOException {
        var accountSettings = new AccountSettings().addCosmosMasterKeyConfig().build();
        assertThat(accountSettings.addCosmosMasterKeyConfig()).isNotNull();
        assertThat(accountSettings.getCosmosMasterKeyConfig().equalsIgnoreCase("fooyo!!"));
    }
}
