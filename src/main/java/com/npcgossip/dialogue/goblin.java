package com.npcgossip.dialogue;

import com.npcgossip.npcDialogue;

import java.util.Random;

public class goblin implements npcDialogue {
    private static final String[] DIALOGUES = {
            "Grrr... I'm a goblin, %s!",
            "Stay away from me, %s!",
            "You don't belong here, %s!",
            "Goblin smash %s!",
            "What do you want, %s?"
    };

    private static final Random random = new Random();

    @Override
    public String getDialogue(String playerName) {
        return String.format(DIALOGUES[random.nextInt(DIALOGUES.length)], playerName);
    }
}