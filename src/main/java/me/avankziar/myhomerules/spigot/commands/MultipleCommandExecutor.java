package main.java.me.avankziar.myhomerules.spigot.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import main.java.me.avankziar.myhomerules.spigot.MyHomeRules;
import main.java.me.avankziar.myhomerules.spigot.assistence.ChatApi;
import net.md_5.bungee.api.chat.ClickEvent;

public class MultipleCommandExecutor implements CommandExecutor 
{
	private MyHomeRules plugin;
	
	public MultipleCommandExecutor(MyHomeRules plugin)
	{
		this.plugin = plugin;
	}
	
	public boolean onCommand(CommandSender sender, Command cmd, String lable, String[] args) 
	{
		// Checks if the label is one of yours.
		if (cmd.getName().equalsIgnoreCase("rules")) 
		{		
			if (!(sender instanceof Player)) 
			{
				MyHomeRules.log.info("/rules is only for Player!");
				return false;
			}
			Player player = (Player) sender;
			if (args.length == 0) 
			{
				plugin.getCommandHelper().rules(player); //Info Command
				return false;
			}
			if (MyHomeRules.rulesarguments.containsKey(args[0])) 
			{
				CommandModule mod = MyHomeRules.rulesarguments.get(args[0]);
				//Abfrage ob der Spieler die Permission hat
				if (player.hasPermission(mod.permission)) 
				{
					//Abfrage, ob der Spieler in den min und max Argumenten Bereich ist.
					if(args.length >= mod.minArgs && args.length <= mod.maxArgs)
					{
						mod.run(sender, args);
					} else
					{
						///Deine Eingabe ist fehlerhaft, klicke hier auf den Text um &cweitere Infos zu bekommen!
						player.spigot().sendMessage(ChatApi.clickEvent(
								plugin.getYamlHandler().getL().getString("InputIsWrong"),
								ClickEvent.Action.RUN_COMMAND, "/rules info"));
						return false;
					}
				} else 
				{
					///Du hast dafür keine Rechte!
					player.spigot().sendMessage(ChatApi.tctl(
							plugin.getYamlHandler().getL().getString("NoPermission")));
					return false;
				}
			} else 
			{
				///Deine Eingabe ist fehlerhaft, klicke hier auf den Text um &cweitere Infos zu bekommen!
				player.spigot().sendMessage(ChatApi.clickEvent(
						plugin.getYamlHandler().getL().getString("InputIsWrong"),
						ClickEvent.Action.RUN_COMMAND, "/rules info"));
				return false;
			}
		}
		return false;
	}
}
