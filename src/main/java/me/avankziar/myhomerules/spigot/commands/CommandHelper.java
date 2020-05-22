package main.java.me.avankziar.myhomerules.spigot.commands;

import org.bukkit.entity.Player;

import main.java.me.avankziar.myhomerules.spigot.MyHomeRules;
import main.java.me.avankziar.myhomerules.spigot.assistence.ChatApi;

public class CommandHelper
{
	private MyHomeRules plugin;

	public CommandHelper(MyHomeRules plugin)
	{
		this.plugin = plugin;
	}

	public void rules(Player player)
	{
		for(String message : plugin.getYamlHandler().getL().getStringList("CmdRules.Base"))
		{
			player.spigot().sendMessage(ChatApi.generateTextComponent(message));
		}
	}
}
