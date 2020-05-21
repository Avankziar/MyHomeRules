package main.java.me.avankziar.myhomerules.spigot.commands.rules;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import main.java.me.avankziar.myhomerules.spigot.MyHomeRules;
import main.java.me.avankziar.myhomerules.spigot.assistence.ChatApi;
import main.java.me.avankziar.myhomerules.spigot.assistence.StringValues;
import main.java.me.avankziar.myhomerules.spigot.commands.CommandModule;

public class ARGReload extends CommandModule
{
	private MyHomeRules plugin;
	
	public ARGReload(MyHomeRules plugin)
	{
		super(StringValues.ARG_RULES_RELOAD,StringValues.PERM_CMD_RULES_RELOAD,
				MyHomeRules.rulesarguments,1,1,StringValues.ARG_RULES_RELOAD_ALIAS,
				StringValues.RULES_SUGGEST_RELOAD);
		this.plugin = plugin;
	}

	@Override
	public void run(CommandSender sender, String[] args)
	{
		Player player = (Player) sender;
		String language = plugin.getYamlHandler().getLanguages();
		String path = StringValues.PATH_RULES;
		if(plugin.reload())
		{
			///Yaml Datein wurden neugeladen.
			player.spigot().sendMessage(ChatApi.tctl(plugin.getYamlHandler().getL().getString(language+path+"Reload.Success")));
			return;
		} else
		{
			///Es wurde ein Fehler gefunden! Siehe Konsole!
			player.spigot().sendMessage(ChatApi.tctl(plugin.getYamlHandler().getL().getString(language+path+"Reload.Error")));
			return;
		}
	}
}