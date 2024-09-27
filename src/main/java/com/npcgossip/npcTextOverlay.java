package com.npcgossip;

import net.runelite.api.Client;
import net.runelite.api.NPC;
import net.runelite.api.Player;
import net.runelite.api.coords.LocalPoint;
import net.runelite.api.coords.WorldPoint;
import net.runelite.api.Point;
import net.runelite.api.Perspective;
import net.runelite.client.ui.overlay.Overlay;
import net.runelite.client.ui.overlay.OverlayLayer;
import net.runelite.client.ui.overlay.OverlayPosition;

import javax.inject.Inject;
import java.awt.*;

public class npcTextOverlay extends Overlay {
    private final Client client;
    private final int maxDistance; // Max tile distance to show text

    @Inject
    public npcTextOverlay(Client client) {
        this.client = client;
        this.maxDistance = 5; // Set this to the max number of tiles you want
        setPosition(OverlayPosition.DYNAMIC);
        setLayer(OverlayLayer.ABOVE_SCENE);
    }

    @Override
    public Dimension render(Graphics2D graphics) {
        Player player = client.getLocalPlayer();

        if (player == null) {
            return null;
        }

        for (NPC npc : client.getNpcs()) {
            if (npc.getName() != null && npc.getName().equals("Ghost villager")) { // Change "Goblin" to your desired NPC name
                int distance = getDistance(player.getLocalLocation(), npc.getLocalLocation());

                if (distance <= maxDistance) {
                    String text = "Hello, " + npc.getName() + "!";
                    renderNpcText(graphics, npc, text);
                }
            }
        }
        return null;
    }

    private void renderNpcText(Graphics2D graphics, NPC npc, String text) {
        LocalPoint npcLocation = npc.getLocalLocation();
        WorldPoint npcWorldPoint = WorldPoint.fromLocal(client, npcLocation); // Convert LocalPoint to WorldPoint

        // Correct usage of getCanvasTextLocation with 5 arguments and WorldPoint
        Point canvasPoint = Perspective.getCanvasTextLocation(client, graphics, npcLocation, text, npc.getLogicalHeight() + 10);

        if (canvasPoint != null) {
            graphics.setFont(new Font("Arial", Font.BOLD, 12));
            graphics.setColor(Color.YELLOW);

            // Draw the text at the canvas point
            graphics.drawString(text, canvasPoint.getX(), canvasPoint.getY());
        }
    }

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