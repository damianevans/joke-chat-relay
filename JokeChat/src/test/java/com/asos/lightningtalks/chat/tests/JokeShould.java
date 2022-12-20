package com.asos.lightningtalks.chat.tests;

import com.asos.lightningtalks.cosmos.model.Joke;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class JokeShould {

    @Test
    public void returnPersonOneJokeScript() {
        Joke joke = new Joke();
        List<String> sourceJokeScript = new ArrayList<String>( Arrays.asList(
                "Knock, Knock",
                "Who's there?",
                "Dishwasher",
                "Dishwasher Who?",
                "Dishwasher way I used to speak until I got my new false teeth",
                "ROFL"
        ));

        joke.setJokeScript((ArrayList<String>) sourceJokeScript);

        ArrayList<String> firstPersonLines = joke.getFirstPersonLines();
        assertThat(firstPersonLines).asList().isEqualTo(
                new ArrayList<String>(
                Arrays.asList( "Knock, Knock", "Dishwasher", "Dishwasher way I used to speak until I got my new false teeth" )));
    }

    @Test
    public void returnPersonTwoJokeScript() {
        Joke joke = new Joke();
        List<String> sourceJokeScript = new ArrayList<String>( Arrays.asList(
                "Knock, Knock",
                "Who's there?",
                "Dishwasher",
                "Dishwasher Who?",
                "Dishwasher way I used to speak until I got my new false teeth",
                "ROFL"
        ));

        joke.setJokeScript((ArrayList<String>) sourceJokeScript);

        ArrayList<String> secondPersonLines = joke.getSecondPersonLines();
        assertThat(secondPersonLines).asList().isEqualTo(
                new ArrayList<String>(
                        Arrays.asList( "Who's there?", "Dishwasher Who?", "ROFL" )));
    }
}
