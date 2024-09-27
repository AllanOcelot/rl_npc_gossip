package com.npcgossip;

import net.runelite.client.RuneLite;
import net.runelite.client.externalplugins.ExternalPluginManager;

public class npcgossipPluginTest
{
	public static void main(String[] args) throws Exception
	{
		ExternalPluginManager.loadBuiltin(npcgossipPlugin.class);
		RuneLite.main(args);
	}
}