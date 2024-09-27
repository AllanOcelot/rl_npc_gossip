package com.npcgossip;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class npcApprovedList {

    // List of approved NPC names
    private static final Set<String> APPROVED_NPC_NAMES = new HashSet<>(Arrays.asList(
            "Goblin",
            "Farmer",
            "Guard",
            "Ghost villager",
            "Ghost banker"
    ));

    // Method to check if the NPC is approved by name
    public static boolean isNpcApprovedByName(String npcName) {
        return APPROVED_NPC_NAMES.contains(npcName);
    }
}