package main.java.me.avankziar.myhomerules.spigot.commands.rules;

import java.util.ArrayList;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import main.java.me.avankziar.myhomerules.spigot.MyHomeRules;
import main.java.me.avankziar.myhomerules.spigot.assistence.ChatApi;
import main.java.me.avankziar.myhomerules.spigot.assistence.MatchApi;
import main.java.me.avankziar.myhomerules.spigot.commands.tree.ArgumentConstructor;
import main.java.me.avankziar.myhomerules.spigot.commands.tree.ArgumentModule;
import main.java.me.avankziar.myhomerules.spigot.objects.RulePlayer;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;

public class ARGToDeleteList extends ArgumentModule
{
	private MyHomeRules plugin;
	
	public ARGToDeleteList(MyHomeRules plugin, ArgumentConstructor argumentConstructor)
	{
		super(plugin, argumentConstructor);
		this.plugin = plugin;
	}

	@Override
	public void run(CommandSender sender, String[] args)
	{
		Player player = (Player) sender;
		int page = 0;
		if(args.length >= 2)
		{
			if(!MatchApi.isInteger(args[1]))
			{
				player.sendMessage(ChatApi.tl(plugin.getYamlHandler().getLang().getString("NoNumber")
						.replace("%arg%", args[1])));
				return;
			}
			page = Integer.parseInt(args[1]);
		}
		int countwhere = plugin.getMysqlHandler().countWhereID("`revoked` = ? AND `deleted` = ?", true, false);
		int amount = 9;
		int start = page*amount;
		if((start+amount) >= countwhere)
		{
			start = countwhere-amount;
			if(start < 0)
			{
				start = 0;
			}
		}
		ArrayList<RulePlayer> list = plugin.getMysqlHandler().getList("`id`", start, amount, "`revoked` = ? AND `deleted` = ?", true, false);
		if(list == null || list.isEmpty())
		{
			player.sendMessage(ChatApi.tl(plugin.getYamlHandler().getLang().getString("CmdRules.ToDeleteList.NoPlayerHasRevoke")));
			return;
		}
		ArrayList<BaseComponent> bc = new ArrayList<>();
		for(RulePlayer rp : list)
		{
			TextComponent tc = ChatApi.apiChat("&7"+rp.getPlayerName()+"&e, ",
					ClickEvent.Action.RUN_COMMAND, MyHomeRules.map.get("DELETE")+" "+rp.getPlayerName(),
					HoverEvent.Action.SHOW_TEXT, plugin.getYamlHandler().getLang().getString("CmdRules.ToDeleteList.Hover"));
			bc.add(tc);
		}
		TextComponent tc = ChatApi.tc("");
		tc.setExtra(bc);
		player.sendMessage(ChatApi.tl(plugin.getYamlHandler().getLang().getString("CmdRules.ToDeleteList.Headline")));
		player.spigot().sendMessage(tc);
	}
}
