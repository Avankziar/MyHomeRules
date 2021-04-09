package main.java.me.avankziar.myhomerules.spigot.commands.rules;

import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import main.java.me.avankziar.myhomerules.spigot.MyHomeRules;
import main.java.me.avankziar.myhomerules.spigot.assistence.ChatApi;
import main.java.me.avankziar.myhomerules.spigot.commands.tree.ArgumentConstructor;
import main.java.me.avankziar.myhomerules.spigot.commands.tree.ArgumentModule;
import main.java.me.avankziar.myhomerules.spigot.objects.RulePlayer;

public class ARGRevoke extends ArgumentModule
{
	private MyHomeRules plugin;
	
	public ARGRevoke(MyHomeRules plugin, ArgumentConstructor argumentConstructor)
	{
		super(plugin, argumentConstructor);
		this.plugin = plugin;
	}

	@Override
	public void run(CommandSender sender, String[] args)
	{
		Player player = (Player) sender;
		String path = "CmdRules.";
		String confirm = "no";
		if(args.length == 2)
		{
			confirm = args[1];
		}
		RulePlayer rp = RulePlayer.getRulePlayer(player);
		if(rp == null)
		{
			player.sendMessage(ChatApi.tl(plugin.getYamlHandler().getLang().getString(path+"Revoke.NoAccept")));
			return;
		}
		if(confirm.equalsIgnoreCase("confirm") || confirm.equalsIgnoreCase("bestätigen"))
		{
			rp.setRevoked(true);
			plugin.getMysqlHandler().updateData(rp, "`player_uuid` = ?", rp.getPlayerUUID());
			if(plugin.getYamlHandler().getConfig().getBoolean("Use.CommandByRevoke", false))
			{
				List<String> commands = plugin.getYamlHandler().getConfig().getStringList("CommandsBy.Revoke");
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
			player.kickPlayer(ChatApi.tl(plugin.getYamlHandler().getLang().getString(path+"Revoke.Kick")));
		} else
		{
			player.sendMessage(ChatApi.tl(plugin.getYamlHandler().getLang().getString(path+"Revoke.Warning")));
		}
		return;
	}
}
