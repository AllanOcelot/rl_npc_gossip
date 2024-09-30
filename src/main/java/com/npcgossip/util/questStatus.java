package com.npcgossip.util;

import net.runelite.api.Client;
import net.runelite.api.Quest;
import net.runelite.api.QuestState;

public class questStatus {
    public static boolean checkQuestCompletion(Client client, Quest quest) {
        QuestState questState = quest.getState(client);
        return questState == QuestState.FINISHED;
    }
}