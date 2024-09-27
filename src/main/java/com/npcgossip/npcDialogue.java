package com.npcgossip;
import java.util.Random;

public class npcDialogue {

    // File will contain all dialog in the plugin
    // My goal? I guess to have NPC's only "gossip" about quests/events that effect them
    // I'm new to Java so unsure the "best" way to do this
    // something like, "if npc is in list, get string (object) with requiremewnts, if reqs met, npc says it
    // will come back to this in future

    // this is random num's in Java
    private static final Random random = new Random();

    // Goblins, for instance will just say something random.
    private static final String[] GOBLIN_DIALOGUES = {
            "Why Human build gate?",
            "Why Goblin mail so big!?",
            "You don't belong here, human!",
            "Goblin smash %s !!!",
            "Goblins will rise again!",
    };


    // Method to get a random goblin dialogue, including the player name
    public static String getRandomGoblinDialogue(String playerName) {
        // Select a random dialogue and insert the player name
        String dialogue = GOBLIN_DIALOGUES[random.nextInt(GOBLIN_DIALOGUES.length)];
        return String.format(dialogue, playerName);
    }


}
