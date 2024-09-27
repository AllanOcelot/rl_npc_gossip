package com.npcgossip;

import com.npcgossip.dialogue.ghostVillager;
import com.npcgossip.dialogue.goblin;

import java.util.HashMap;
import java.util.Map;

public class npcDialogueFactory {
    private static final Map<String, npcDialogue> npcDialogueMap = new HashMap<>();

    static {
        npcDialogueMap.put("Goblin", new goblin());


        npcDialogueMap.put("Ghost villager", new ghostVillager());
//        npcDialogueMap.put("Guard", new GuardDialogue());
//        npcDialogueMap.put("Farmer", new FarmerDialogue());
    }

    // Method to get the appropriate dialogue object based on NPC name
    public static npcDialogue getNpcDialogue(String npcName) {
        return npcDialogueMap.get(npcName);
    }
}