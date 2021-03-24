package main.java.me.avankziar.myhomerules.spigot.commands.rules;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import main.java.me.avankziar.myhomerules.spigot.MyHomeRules;
import main.java.me.avankziar.myhomerules.spigot.assistence.MatchApi;
import main.java.me.avankziar.myhomerules.spigot.commands.RulesCommandExecutor;
import main.java.me.avankziar.myhomerules.spigot.commands.tree.ArgumentConstructor;
import main.java.me.avankziar.myhomerules.spigot.commands.tree.ArgumentModule;

public class ARGInfo extends ArgumentModule
{
	//private MyHomeRules plugin;
	
	public ARGInfo(MyHomeRules plugin, ArgumentConstructor argumentConstructor)
	{
		super(plugin, argumentConstructor);
		//this.plugin = plugin;
	}

	@Override
	public void run(CommandSender sender, String[] args)
	{
		Player player = (Player) sender;
		int page = 0;
		if(args.length == 2)
		{
			if(MatchApi.isInteger(args[1]))
			{
				page = Integer.parseInt(args[1]);
			}
		}
		RulesCommandExecutor.baseCommands(player, page);
	}
}
