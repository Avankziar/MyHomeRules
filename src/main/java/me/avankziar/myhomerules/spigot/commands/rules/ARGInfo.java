package main.java.me.avankziar.myhomerules.spigot.commands.rules;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import main.java.me.avankziar.myhomerules.spigot.MyHomeRules;
import main.java.me.avankziar.myhomerules.spigot.assistence.ChatApi;
import main.java.me.avankziar.myhomerules.spigot.assistence.MatchApi;
import main.java.me.avankziar.myhomerules.spigot.assistence.StringValues;
import main.java.me.avankziar.myhomerules.spigot.commands.CommandModule;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.TextComponent;

public class ARGInfo extends CommandModule
{
	private MyHomeRules plugin;
	
	public ARGInfo(MyHomeRules plugin)
	{
		super(StringValues.ARG_RULES_INFO,StringValues.PERM_CMD_RULES_INFO,
				MyHomeRules.rulesarguments,1,2,StringValues.ARG_RULES_INFO_ALIAS,
				StringValues.RULES_SUGGEST_INFO);
		this.plugin = plugin;
	}

	@Override
	public void run(CommandSender sender, String[] args)
	{
		Player player = (Player) sender;
		String language = plugin.getYamlHandler().getLanguages();
		String path = StringValues.PATH_RULES;
		int page = 0;
		if(args.length == 2)
		{
			if(MatchApi.isInteger(args[1]))
			{
				page = Integer.parseInt(args[1]);
			}
		}
		int count = 0;
		int start = page*10;
		int end = page*10+9;
		int last = MyHomeRules.rulesarguments.size()-1;
		boolean lastpage = false;
		if(end > last)
		{
			lastpage = true;
		}
		player.spigot().sendMessage(ChatApi.tctl(plugin.getYamlHandler().getL().getString(language+".CmdRules.Info.Headline")));
		for(String argument : MyHomeRules.rulesarguments.keySet())
		{
			if(count >= start && count <= end)
			{
				sendInfo(player, MyHomeRules.rulesarguments.get(argument));
			}
			count++;
		}
		pastNext(player, page, lastpage, language, path);
	}
	
	private void sendInfo(Player player, CommandModule module)
	{
		String language = plugin.getYamlHandler().getLanguages();
		if(player.hasPermission(module.permission))
		{
			player.spigot().sendMessage(ChatApi.clickEvent(
					plugin.getYamlHandler().getL().getString(language+".CmdRules.Info."+module.argument),
					ClickEvent.Action.SUGGEST_COMMAND, module.commandSuggest));
		}
	}
	
	private void pastNext(Player player, int page, boolean lastpage, String language, String path)
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
					plugin.getYamlHandler().getL().getString(language+path+"Info.Past"));
			String cmd = "/rules info "+String.valueOf(j);
			msg2.setClickEvent( new ClickEvent(ClickEvent.Action.RUN_COMMAND, cmd));
			pages.add(msg2);
		}
		if(!lastpage)
		{
			TextComponent msg1 = ChatApi.tctl(
					plugin.getYamlHandler().getL().getString(language+path+"Info.Next"));
			String cmd = "/rules info "+String.valueOf(i);
			msg1.setClickEvent( new ClickEvent(ClickEvent.Action.RUN_COMMAND, cmd));
			if(pages.size()==1)
			{
				pages.add(ChatApi.tc(" | "));
			}
			pages.add(msg1);
		}
		MSG.setExtra(pages);	
		player.spigot().sendMessage(MSG);
		return;
	}
}
