package com.npcgossip.dialogue;

import com.npcgossip.npcDialogue;

import java.util.Random;

public class ghostVillager implements npcDialogue {
    private static final String[] DIALOGUES = {
            "What a day, what a dreadful day",
            "Worse things than dying you know...",
            "Pirates? What next, Dragons!?",
            "Adventures love our furnaces... shame they don't buy anything!",
            "So this is it... The afterlife..."
    };

    private static final Random random = new Random();

    @Override
    public String getDialogue(String playerName) {
        return String.format(DIALOGUES[random.nextInt(DIALOGUES.length)], playerName);
    }
}