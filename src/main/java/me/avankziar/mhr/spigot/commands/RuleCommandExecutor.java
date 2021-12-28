package main.java.me.avankziar.mhr.spigot.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import main.java.me.avankziar.mhr.spigot.MyHomeRules;
import main.java.me.avankziar.mhr.spigot.assistence.ChatApi;
import main.java.me.avankziar.mhr.spigot.assistence.MatchApi;
import main.java.me.avankziar.mhr.spigot.commands.tree.CommandConstructor;
import main.java.me.avankziar.mhr.spigot.objects.ServerRule;

public class RuleCommandExecutor implements CommandExecutor
{
	private MyHomeRules plugin;
	private static CommandConstructor cc;
	
	public RuleCommandExecutor(MyHomeRules plugin, CommandConstructor cc)
	{
		this.plugin = plugin;
		RuleCommandExecutor.cc = cc;
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String lable, String[] args) 
	{
		if (!(sender instanceof Player)) 
		{
			MyHomeRules.log.info("/%cmd% is only for Player!".replace("%cmd%", cc.getName()));
			return false;
		}
		Player player = (Player) sender;		
		int page = 0;
		if(args.length == 1)
		{
			if(!MatchApi.isInteger(args[0]))
			{
				player.sendMessage(ChatApi.tl(plugin.getYamlHandler().getLang().getString("NoNumber")
						.replace("%arg%", args[1])));
				return false;
			}
		}
		int start = page*9;
		int end = start+9;
		if(end >= plugin.getBackgroundTask().rules.size())
		{
			end = plugin.getBackgroundTask().rules.size()-1;
			start = end-9;
			if(start < 0)
			{
				start = 0;
			}
		}
		int i = 0;
		for(ServerRule sr : plugin.getBackgroundTask().rules.values())
		{
			if(i >= start && i <= end)
			{
				player.sendMessage(ChatApi.tl(plugin.getYamlHandler().getLang().getString("CmdRule.Line")
						.replace("%number%", sr.getNumber())
						.replace("%rulename%", sr.getRuleName())));
				for(String s : sr.getLines())
				{
					player.sendMessage(ChatApi.tl(s));
				}
			}
			i++;
		}
		return true;
	}
}