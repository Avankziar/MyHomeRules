package main.java.me.avankziar.myhomerules.spigot.commands.rules;

import java.time.LocalDateTime;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import main.java.me.avankziar.myhomerules.spigot.MyHomeRules;
import main.java.me.avankziar.myhomerules.spigot.assistence.ChatApi;
import main.java.me.avankziar.myhomerules.spigot.assistence.StringValues;
import main.java.me.avankziar.myhomerules.spigot.commands.CommandModule;
import main.java.me.avankziar.myhomerules.spigot.objects.RulePlayer;

public class ARGAccept extends CommandModule
{
	private MyHomeRules plugin;
	
	public ARGAccept(MyHomeRules plugin)
	{
		super(StringValues.ARG_RULES_ACCEPT,StringValues.PERM_CMD_RULES_ACCEPT,
				MyHomeRules.rulesarguments,1,1,StringValues.ARG_RULES_ACCEPT_ALIAS,
				StringValues.RULES_SUGGEST_ACCEPT);
		this.plugin = plugin;
	}

	@Override
	public void run(CommandSender sender, String[] args)
	{
		Player player = (Player) sender;
		String language = plugin.getYamlHandler().getLanguages();
		String path = StringValues.PATH_RULES;
		RulePlayer rp = RulePlayer.getRulePlayer(player.getUniqueId());
		if(rp == null)
		{
			rp = new RulePlayer(player.getUniqueId().toString(), player.getName(), LocalDateTime.now());
			plugin.getMysqlHandler().create(rp);
			RulePlayer.addList(rp);
			player.sendMessage(ChatApi.tl(plugin.getYamlHandler().getL().getString(language+path+"Accept.Accepting")));
		} else
		{
			player.sendMessage(ChatApi.tl(plugin.getYamlHandler().getL().getString(language+path+"Accept.AlreadyAccepted")
					.replace("%time%", RulePlayer.serialised(rp.getDateTime()))));
		}
		return;
	}
}