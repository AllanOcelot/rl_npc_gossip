package com.npcgossip;

import net.runelite.api.Client;
import net.runelite.api.NPC;
import net.runelite.api.Player;
import net.runelite.api.coords.LocalPoint;
import net.runelite.api.Point;
import net.runelite.api.Perspective;
import net.runelite.client.ui.overlay.Overlay;
import net.runelite.client.ui.overlay.OverlayLayer;
import net.runelite.client.ui.overlay.OverlayPosition;

import javax.inject.Inject;
import java.awt.*;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Random;

public class npcTextOverlay extends Overlay {
    private final Client client;
    private final int maxDistance; // Max tile distance to show text
    private final int maxCheckRadius = 16; // Max radius to check NPCs (tiles)
    private final double speakProbability = 0.1; // 10% chance of NPC saying something


    // Cache for NPC dialogue to avoid regeneration every frame
    private final Map<Integer, String> npcDialogueCache = new HashMap<>();

    // Random number generator for probability check
    private final Random random = new Random();


    @Inject
    public npcTextOverlay(Client client) {
        this.client = client;
        this.maxDistance = 8; // Set this to the max number of tiles you want for text rendering
        setPosition(OverlayPosition.DYNAMIC);
        setLayer(OverlayLayer.ABOVE_SCENE);
    }

    @Override
    public Dimension render(Graphics2D graphics) {
        Player player = client.getLocalPlayer();

        if (player == null) {
            return null;
        }

        // Iterate over NPCs in the cache and remove those outside of range
        for (Iterator<Map.Entry<Integer, String>> it = npcDialogueCache.entrySet().iterator(); it.hasNext(); ) {
            Map.Entry<Integer, String> entry = it.next();
            NPC npc = client.getCachedNPCs()[entry.getKey()];

            if (npc == null || getDistance(player.getLocalLocation(), npc.getLocalLocation()) > maxCheckRadius) {
                // Remove from cache if out of the "check radius"
                it.remove();
            }
        }

        // Check only NPCs within the maxCheckRadius of the player
        for (NPC npc : client.getNpcs()) {
            if (npc.getName() != null && npcApprovedList.isNpcApprovedByName(npc.getName())) {
                int distance = getDistance(player.getLocalLocation(), npc.getLocalLocation());

                // Check only NPCs within the maxCheckRadius
                if (distance <= maxCheckRadius) {
                    // If within the smaller radius for text rendering, show the dialogue
                    if (distance <= maxDistance) {
                        String text = getNpcDialogue(npc, player.getName());
                        renderNpcText(graphics, npc, text);
                    }
                }
            }
        }
        return null;
    }

    private String getNpcDialogue(NPC npc, String playerName) {
        int npcId = npc.getIndex(); // Unique identifier for the NPC

        // Check if the NPC already has cached dialogue
        if (!npcDialogueCache.containsKey(npcId)) {
            // Check if the NPC should speak based on probability
            if (random.nextDouble() <= speakProbability) {
                // Generate new dialogue and cache it if the NPC should speak
                String newDialogue = npcDialogue.getRandomGoblinDialogue(playerName); // Replace with appropriate NPC type if needed
                npcDialogueCache.put(npcId, newDialogue);
            } else {
                // If the NPC doesn't speak, cache a "silent" state (null or empty string)
                npcDialogueCache.put(npcId, null);
            }
        }

        // Return the cached dialogue for this NPC
        return npcDialogueCache.get(npcId);
    }

    private void renderNpcText(Graphics2D graphics, NPC npc, String text) {
        LocalPoint npcLocation = npc.getLocalLocation();

        // Use LocalPoint directly here to get the canvas position
        Point canvasPoint = Perspective.getCanvasTextLocation(client, graphics, npcLocation, text, npc.getLogicalHeight());

        if (canvasPoint != null) {
            graphics.setFont(new Font("Arial", Font.BOLD, 12));
            graphics.setColor(Color.WHITE);

            // Draw the text at the canvas point
            graphics.drawString(text, canvasPoint.getX(), canvasPoint.getY());
        }
    }

    // Calculate the distance between the player and the NPC in tiles
    private int getDistance(LocalPoint playerLocation, LocalPoint npcLocation) {
        if (playerLocation == null || npcLocation == null) {
            return Integer.MAX_VALUE; // Return a large number if null
        }

        // Get the tile coordinates from the local point
        int dx = playerLocation.getX() - npcLocation.getX();
        int dy = playerLocation.getY() - npcLocation.getY();

        // Each tile is 128 units, so we divide by 128 to get tile distance
        return (int) Math.sqrt((dx * dx) + (dy * dy)) / 128;
    }
}