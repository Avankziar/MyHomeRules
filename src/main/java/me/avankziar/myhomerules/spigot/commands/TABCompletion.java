package main.java.me.avankziar.myhomerules.spigot.commands;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import main.java.me.avankziar.myhomerules.spigot.MyHomeRules;

public class TABCompletion implements TabCompleter
{	
	public TABCompletion()
	{
		
	}
	
	@Override
	public List<String> onTabComplete(CommandSender sender, Command cmd, String lable, String[] args) 
	{
		Player player = (Player)sender;
		List<String> list = new ArrayList<String>();
		if (cmd.getName().equalsIgnoreCase("rules") && args.length == 1) 
		{
			if (!args[0].equals("")) 
			{
				for (String commandString : MyHomeRules.rulesarguments.keySet()) 
				{
					CommandModule mod = MyHomeRules.rulesarguments.get(commandString);
					if (player.hasPermission(mod.permission))
					{
						if (commandString.startsWith(args[0])) 
						{
							list.add(commandString);
						}
					}
				}
				Collections.sort(list);
				return list;
			} else
			{
				for (String commandString : MyHomeRules.rulesarguments.keySet()) 
				{
					CommandModule mod = MyHomeRules.rulesarguments.get(commandString);
					if (player.hasPermission(mod.permission)) 
					{
						list.add(commandString);
					}
				}
				Collections.sort(list);
				return list;
			}
		}
		return null;
	}
}
