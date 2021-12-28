package main.java.me.avankziar.mhr.spigot.commands.rules;

import java.time.LocalDateTime;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import main.java.me.avankziar.mhr.spigot.MyHomeRules;
import main.java.me.avankziar.mhr.spigot.assistence.ChatApi;
import main.java.me.avankziar.mhr.spigot.commands.tree.ArgumentConstructor;
import main.java.me.avankziar.mhr.spigot.commands.tree.ArgumentModule;
import main.java.me.avankziar.mhr.spigot.objects.RulePlayer;

public class ARGAccept extends ArgumentModule
{
	private MyHomeRules plugin;
	
	public ARGAccept(MyHomeRules plugin, ArgumentConstructor argumentConstructor)
	{
		super(plugin, argumentConstructor);
		this.plugin = plugin;
	}

	@Override
	public void run(CommandSender sender, String[] args)
	{
		Player player = (Player) sender;
		String path = "CmdRules.";
		RulePlayer rp = RulePlayer.getRulePlayer(player);
		if(rp == null)
		{
			rp = new RulePlayer(player.getUniqueId().toString(), player.getName(), LocalDateTime.now(), false, false);
			plugin.getMysqlHandler().create(rp);
			player.sendMessage(ChatApi.tl(plugin.getYamlHandler().getLang().getString(path+"Accept.Accepting")));
		} else
		{
			player.sendMessage(ChatApi.tl(plugin.getYamlHandler().getLang().getString(path+"Accept.AlreadyAccepted")
					.replace("%time%", RulePlayer.serialised(rp.getDateTime()))));
			return;
		}
		if(!plugin.getYamlHandler().getConfig().getBoolean("Use.CommandByAccept", false))
		{
			return;
		}
		List<String> commands = plugin.getYamlHandler().getConfig().getStringList("CommandsBy.Accept");
		for(String s : commands)
		{
			if(!s.contains("<->"))
			{
				continue;
			}
			String[] split = s.split("<->");
			if(split.length != 2)
			{
				continue;
			}
			if(split[0].equals("player"))
			{
				//Ohne / bei commands
				Bukkit.dispatchCommand(player, split[1].replace("%player%", player.getName()));
			} else if(split[0].equals("console"))
			{
				Bukkit.dispatchCommand(Bukkit.getConsoleSender(), split[1].replace("%player%", player.getName()));
			}
		}
	}
}