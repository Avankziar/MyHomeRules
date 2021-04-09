package main.java.me.avankziar.myhomerules.spigot.listener;

import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.scheduler.BukkitRunnable;

import main.java.me.avankziar.myhomerules.spigot.MyHomeRules;
import main.java.me.avankziar.myhomerules.spigot.objects.RulePlayer;

public class PlayerListener implements Listener
{
	private MyHomeRules plugin;
	
	public PlayerListener(MyHomeRules plugin)
	{
		this.plugin = plugin;
	}
	
	@EventHandler (priority = EventPriority.LOW)
	public void onJoin(PlayerJoinEvent event)
	{
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
					boolean simpleSite = plugin.getYamlHandler().getConfig().getBoolean("RunTask.SimpleSite");
					if(simpleSite)
					{
						plugin.getBackgroundTask().playerMustAcceptTaskSimpleSite(event.getPlayer());
					} else
					{
						plugin.getBackgroundTask().playerMustAcceptTaskMultipleSite(event.getPlayer());
					}
				} else
				{
					RulePlayer rp = RulePlayer.getRulePlayer(event.getPlayer());
					if(rp != null)
					{
						if(rp.isRevoked() || rp.isDeleted())
						{
							plugin.getMysqlHandler().deleteData("`player_uuid` = ?", playeruuid);
							boolean simpleSite = plugin.getYamlHandler().getConfig().getBoolean("RunTask.SimpleSite");
							if(simpleSite)
							{
								plugin.getBackgroundTask().playerMustAcceptTaskSimpleSite(event.getPlayer());
							} else
							{
								plugin.getBackgroundTask().playerMustAcceptTaskMultipleSite(event.getPlayer());
							}
							return;
						}
					}
				}
			}
		}.runTaskLater(plugin, 20L);
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
