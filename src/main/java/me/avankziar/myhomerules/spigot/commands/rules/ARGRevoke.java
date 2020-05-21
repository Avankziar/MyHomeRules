package main.java.me.avankziar.myhomerules.spigot.commands.rules;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import main.java.me.avankziar.myhomerules.spigot.MyHomeRules;
import main.java.me.avankziar.myhomerules.spigot.assistence.ChatApi;
import main.java.me.avankziar.myhomerules.spigot.assistence.StringValues;
import main.java.me.avankziar.myhomerules.spigot.commands.CommandModule;
import main.java.me.avankziar.myhomerules.spigot.objects.RulePlayer;

public class ARGRevoke extends CommandModule
{
	private MyHomeRules plugin;
	
	public ARGRevoke(MyHomeRules plugin)
	{
		super(StringValues.ARG_RULES_REVOKE,StringValues.PERM_CMD_RULES_REVOKE,
				MyHomeRules.rulesarguments,1,2,StringValues.ARG_RULES_REVOKE_ALIAS,
				StringValues.RULES_SUGGEST_REVOKE);
		this.plugin = plugin;
	}

	@Override
	public void run(CommandSender sender, String[] args)
	{
		Player player = (Player) sender;
		String language = plugin.getYamlHandler().getLanguages();
		String path = StringValues.PATH_RULES;
		String confirm = "no";
		if(args.length == 2)
		{
			confirm = args[1];
		}
		RulePlayer rp = RulePlayer.getRulePlayer(player.getUniqueId());
		if(rp == null)
		{
			player.sendMessage(ChatApi.tl(plugin.getYamlHandler().getL().getString(language+path+"Revoke.NoAccept")));
			return;
		}
		if(confirm.equalsIgnoreCase("confirm") || confirm.equalsIgnoreCase("bestätigen"))
		{
			player.kickPlayer(ChatApi.tl(plugin.getYamlHandler().getL().getString(language+path+"Revoke.Kick")));
			plugin.getMysqlHandler().deleteData("`player_uuid` = ?", player.getUniqueId().toString());
			RulePlayer.removeList(rp);
		} else
		{
			player.sendMessage(ChatApi.tl(plugin.getYamlHandler().getL().getString(language+path+"Revoke.Warning")));
		}
		return;
	}
}
