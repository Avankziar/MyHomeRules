package main.java.me.avankziar.myhomerules.spigot.commands.rules;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import main.java.me.avankziar.myhomerules.spigot.MyHomeRules;
import main.java.me.avankziar.myhomerules.spigot.assistence.ChatApi;
import main.java.me.avankziar.myhomerules.spigot.commands.tree.ArgumentConstructor;
import main.java.me.avankziar.myhomerules.spigot.commands.tree.ArgumentModule;
import main.java.me.avankziar.myhomerules.spigot.objects.RulePlayer;

public class ARGDelete extends ArgumentModule
{
	private MyHomeRules plugin;
	
	public ARGDelete(MyHomeRules plugin, ArgumentConstructor argumentConstructor)
	{
		super(plugin, argumentConstructor);
		this.plugin = plugin;
	}

	@Override
	public void run(CommandSender sender, String[] args)
	{
		Player player = (Player) sender;
		String playername = args[1];
		RulePlayer rp = RulePlayer.getRulePlayer(playername);
		if(rp == null)
		{
			player.sendMessage(ChatApi.tl(plugin.getYamlHandler().getLang().getString("NoPlayerExist")));
			return;
		}
		if(!rp.isRevoked())
		{
			player.sendMessage(ChatApi.tl(plugin.getYamlHandler().getLang().getString("CmdRules.Delete.PlayerHasNotRevoke")));
			return;
		}
		rp.setDeleted(true);
		plugin.getMysqlHandler().updateData(rp, "`player_name` = ?", playername);
		player.sendMessage(ChatApi.tl(plugin.getYamlHandler().getLang().getString("CmdRules.Delete.ThePlayerIsConsideredDeleted")
				.replace("%playername%", playername)));
	}
}