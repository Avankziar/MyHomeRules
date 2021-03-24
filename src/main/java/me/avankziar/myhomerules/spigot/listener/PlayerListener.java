package main.java.me.avankziar.myhomerules.spigot.listener;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.scheduler.BukkitRunnable;

import main.java.me.avankziar.myhomerules.spigot.MyHomeRules;
import main.java.me.avankziar.myhomerules.spigot.assistence.ChatApi;
import main.java.me.avankziar.myhomerules.spigot.objects.RulePlayer;

public class PlayerListener implements Listener
{
	private MyHomeRules plugin;
	
	public PlayerListener(MyHomeRules plugin)
	{
		this.plugin = plugin;
	}
	
	private void debug(Player player, String s)
	{
		boolean bo = false;
		if(bo)
		{
			if(player != null)
			{
				player.spigot().sendMessage(ChatApi.tctl(s));
			}
			System.out.println(s);
		}
	}
	
	@EventHandler (priority = EventPriority.LOW)
	public void onJoin(PlayerJoinEvent event)
	{
		debug(event.getPlayer(),"PJE 1");
		new BukkitRunnable()
		{
			@Override
			public void run()
			{
				if(event.getPlayer() == null)
				{
					return;
				}
				String playeruuid = event.getPlayer().getUniqueId().toString();
				if(!plugin.getMysqlHandler().exist("`player_uuid` = ?", playeruuid))
				{
					cancel();
					debug(event.getPlayer(),"PJE 2 uuid: "+playeruuid);
					plugin.getBackgroundTask().playerMustAcceptTask(event.getPlayer());
				} else
				{
					cancel();
					debug(event.getPlayer(),"PJE 3");
					RulePlayer.addList((RulePlayer) plugin.getMysqlHandler().getData("`player_uuid` = ?", playeruuid));
				}
			}
		}.runTaskTimer(plugin, 0L, 2L);
	}
	
	@EventHandler
	public void onQuit(PlayerQuitEvent event)
	{
		RulePlayer rp = RulePlayer.getRulePlayer(event.getPlayer().getUniqueId());
		if(rp != null)
		{
			RulePlayer.removeList(rp);
		}
	}
	
	@EventHandler
	public void onCustomCommand(PlayerCommandPreprocessEvent event)
	{
		if(event.getMessage().equalsIgnoreCase(plugin.getYamlHandler().getConfig().getString("CustomCommand")))
		{
			event.setMessage(MyHomeRules.ruleaccepting.getSuggestion());
		}
	}
}
