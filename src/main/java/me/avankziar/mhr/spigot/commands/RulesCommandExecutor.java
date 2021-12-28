package main.java.me.avankziar.mhr.spigot.commands;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import main.java.me.avankziar.mhr.spigot.MyHomeRules;
import main.java.me.avankziar.mhr.spigot.assistence.ChatApi;
import main.java.me.avankziar.mhr.spigot.assistence.MatchApi;
import main.java.me.avankziar.mhr.spigot.commands.tree.ArgumentConstructor;
import main.java.me.avankziar.mhr.spigot.commands.tree.ArgumentModule;
import main.java.me.avankziar.mhr.spigot.commands.tree.BaseConstructor;
import main.java.me.avankziar.mhr.spigot.commands.tree.CommandConstructor;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;

public class RulesCommandExecutor implements CommandExecutor
{
	private MyHomeRules plugin;
	private static CommandConstructor cc;
	
	public RulesCommandExecutor(MyHomeRules plugin, CommandConstructor cc)
	{
		this.plugin = plugin;
		RulesCommandExecutor.cc = cc;
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
		if(cc == null)
		{
			return false;
		}
		if (args.length == 1) 
		{
			if(MatchApi.isInteger(args[0]))
			{
				if(!player.hasPermission(cc.getPermission()))
				{
					///Du hast dafür keine Rechte!
					player.spigot().sendMessage(ChatApi.tctl(plugin.getYamlHandler().getLang().getString("NoPermission")));
					return false;
				}
				baseCommands(player, Integer.parseInt(args[0])); //Base and Info Command
				return true;
			}
		} else if(args.length == 0)
		{
			if(!player.hasPermission(cc.getPermission()))
			{
				///Du hast dafür keine Rechte!
				player.spigot().sendMessage(ChatApi.tctl(plugin.getYamlHandler().getLang().getString("NoPermission")));
				return false;
			}
			rules(player);
			return true;
		}
		int length = args.length-1;
		ArrayList<ArgumentConstructor> aclist = cc.subcommands;
		for(int i = 0; i <= length; i++)
		{
			for(ArgumentConstructor ac : aclist)
			{
				if(args[i].equalsIgnoreCase(ac.getName()))
				{
					if(length >= ac.minArgsConstructor && length <= ac.maxArgsConstructor)
					{
						if(player.hasPermission(ac.getPermission()))
						{
							ArgumentModule am = plugin.getArgumentMap().get(ac.getPath());
							if(am != null)
							{
								try
								{
									am.run(sender, args);
								} catch (IOException e)
								{
									e.printStackTrace();
								}
							} else
							{
								MyHomeRules.log.info("ArgumentModule from ArgumentConstructor %ac% not found! ERROR!"
										.replace("%ac%", ac.getName()));
								player.spigot().sendMessage(ChatApi.tctl(
										"ArgumentModule from ArgumentConstructor %ac% not found! ERROR!"
										.replace("%ac%", ac.getName())));
								return false;
							}
							return false;
						} else
						{
							///Du hast dafür keine Rechte!
							player.spigot().sendMessage(ChatApi.tctl(plugin.getYamlHandler().getLang().getString("NoPermission")));
							return false;
						}
					}/* else if(length > ac.maxArgsConstructor) 
					{
						///Deine Eingabe ist fehlerhaft, klicke hier auf den Text um &cweitere Infos zu bekommen!
						player.spigot().sendMessage(ChatApi.clickEvent(plugin.getYamlHandler().getL().getString("InputIsWrong"),
								ClickEvent.Action.RUN_COMMAND, MyHomeRules.infoCommand));
						return false;
					}*/ else
					{
						aclist = ac.subargument;
						break;
					}
				}
			}
		}
		///Deine Eingabe ist fehlerhaft, klicke hier auf den Text um &cweitere Infos zu bekommen!
		player.spigot().sendMessage(ChatApi.clickEvent(plugin.getYamlHandler().getLang().getString("InputIsWrong"),
				ClickEvent.Action.RUN_COMMAND, MyHomeRules.infoCommand));
		return false;
	}
	
	public void rules(Player player)
	{
		for(String message : plugin.getYamlHandler().getLang().getStringList("CmdRules.Base"))
		{
			player.spigot().sendMessage(ChatApi.generateTextComponent(message, true));
		}
	}
	
	public static void baseCommands(final Player player, int page)
	{
		int count = 0;
		int start = page*10;
		int end = page*10+9;
		int last = 0;
		player.spigot().sendMessage(ChatApi.tctl(MyHomeRules.getPlugin().getYamlHandler().getLang().getString(
				MyHomeRules.infoCommandPath+".BaseInfo.Headline")));
		for(BaseConstructor bc : MyHomeRules.getPlugin().getHelpList())
		{
			if(count >= start && count <= end)
			{
				if(player.hasPermission(bc.getPermission()))
				{
					sendInfo(player, bc.getPath(), bc.getHelpInfo(), bc.getSuggestion());
				}
			}
			count++;
			last++;
		}
		boolean lastpage = false;
		if(end > last)
		{
			lastpage = true;
		}
		pastNextPage(player, MyHomeRules.infoCommandPath, page, lastpage, MyHomeRules.infoCommand);
	}
	
	private static void sendInfo(Player player, String path, String info, String suggestion)
	{
		player.spigot().sendMessage(ChatApi.apiChat(
				info,
				ClickEvent.Action.SUGGEST_COMMAND, suggestion,
				HoverEvent.Action.SHOW_TEXT, MyHomeRules.getPlugin().getYamlHandler().getLang().getString("GeneralHover")));
	}
	
	public static void pastNextPage(Player player, String path,
			int page, boolean lastpage, String cmdstring, String...objects)
	{
		if(page==0 && lastpage)
		{
			return;
		}
		int i = page+1;
		int j = page-1;
		TextComponent MSG = ChatApi.tctl("");
		List<BaseComponent> pages = new ArrayList<BaseComponent>();
		if(page!=0)
		{
			TextComponent msg2 = ChatApi.tctl(
					MyHomeRules.getPlugin().getYamlHandler().getLang().getString(path+".BaseInfo.Past"));
			String cmd = cmdstring+" "+String.valueOf(j);
			for(String o : objects)
			{
				cmd += " "+o;
			}
			msg2.setClickEvent( new ClickEvent(ClickEvent.Action.RUN_COMMAND, cmd));
			pages.add(msg2);
		}
		if(!lastpage)
		{
			TextComponent msg1 = ChatApi.tctl(
					MyHomeRules.getPlugin().getYamlHandler().getLang().getString(path+".BaseInfo.Next"));
			String cmd = cmdstring+" "+String.valueOf(i);
			for(String o : objects)
			{
				cmd += " "+o;
			}
			msg1.setClickEvent( new ClickEvent(ClickEvent.Action.RUN_COMMAND, cmd));
			if(pages.size()==1)
			{
				pages.add(ChatApi.tc(" | "));
			}
			pages.add(msg1);
		}
		MSG.setExtra(pages);	
		player.spigot().sendMessage(MSG);
	}

}
