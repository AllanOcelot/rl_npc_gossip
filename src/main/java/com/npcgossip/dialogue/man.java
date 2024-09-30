package com.npcgossip.dialogue;

import com.npcgossip.npcDialogue;
import com.npcgossip.util.questStatus;
import net.runelite.api.Client;
import net.runelite.api.Quest;

import javax.inject.Inject;
import java.util.Random;

public class man implements npcDialogue {
    String[] DIALOGUES = {
            "What armor even is that?",
            "It's always something with you lot!",
            "Hello %s, saved the world yet?",
            "Ahhh, thing's were better back in Albion ",
            "Haha, look everyone, there goes %s ! "
    };

    @Inject
    private Client client;



    // Check if quest's related to "man" are completed.
    // Check if specific quests are completed and add dialogue if needed
    if (questStatus.checkQuestCompletion(client, Quest.COOKS_ASSISTANT)) {
        DIALOGUES = appendToDialogues(DIALOGUES, " You've done a fine job in Cook's Assistant!");
    }


    private static final Random random = new Random();

    @Override
    public String getDialogue(String playerName) {
        return String.format(DIALOGUES[random.nextInt(DIALOGUES.length)], playerName);
    }
}