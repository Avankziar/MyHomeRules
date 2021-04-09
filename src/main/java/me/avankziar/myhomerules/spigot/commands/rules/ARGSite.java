package main.java.me.avankziar.myhomerules.spigot.commands.rules;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import main.java.me.avankziar.myhomerules.spigot.MyHomeRules;
import main.java.me.avankziar.myhomerules.spigot.assistence.ChatApi;
import main.java.me.avankziar.myhomerules.spigot.assistence.MatchApi;
import main.java.me.avankziar.myhomerules.spigot.commands.tree.ArgumentConstructor;
import main.java.me.avankziar.myhomerules.spigot.commands.tree.ArgumentModule;
import net.md_5.bungee.api.chat.TextComponent;

public class ARGSite extends ArgumentModule
{
	private MyHomeRules plugin;
	
	public ARGSite(MyHomeRules plugin, ArgumentConstructor argumentConstructor)
	{
		super(plugin, argumentConstructor);
		this.plugin = plugin;
	}

	@Override
	public void run(CommandSender sender, String[] args)
	{
		Player player = (Player) sender;
		if(!MatchApi.isInteger(args[1]))
		{
			player.sendMessage(ChatApi.tl(plugin.getYamlHandler().getLang().getString("NoNumber")
					.replace("%arg%", args[1])));
			return;
		}
		int page = Integer.parseInt(args[1])-1;
		if(page < 0)
		{
			page = 0;
		}
		if(page >= plugin.getBackgroundTask().multipleSite.size())
		{
			player.sendMessage(ChatApi.tl(plugin.getYamlHandler().getLang().getString("CmdRules.Site.PageNotAvailable")));
			return;
		}
		for(TextComponent tx : plugin.getBackgroundTask().multipleSite.get(page))
		{
			player.spigot().sendMessage(tx);
		}
	}
}