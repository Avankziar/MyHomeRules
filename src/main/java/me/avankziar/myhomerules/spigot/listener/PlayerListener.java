package main.java.me.avankziar.myhomerules.spigot.listener;

import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import main.java.me.avankziar.myhomerules.spigot.MyHomeRules;
import main.java.me.avankziar.myhomerules.spigot.objects.RulePlayer;

public class PlayerListener implements Listener
{
	private MyHomeRules plugin;
	
	public PlayerListener(MyHomeRules plugin)
	{
		this.plugin = plugin;
	}
	
	@EventHandler (priority = EventPriority.LOWEST)
	public void onJoin(PlayerJoinEvent event)
	{
		String playeruuid = event.getPlayer().getUniqueId().toString();
		if(!plugin.getMysqlHandler().exist("`player_uuid` = ?", playeruuid))
		{
			plugin.getBackgroundTask().playerMustAcceptTask(event.getPlayer());
		} else
		{
			RulePlayer.addList((RulePlayer) plugin.getMysqlHandler().getData("`player_uuid` = ?", playeruuid));
		}
		return;
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
		if(event.getMessage().equalsIgnoreCase(plugin.getYamlHandler().get().getString("CustomCommand")))
		{
			event.setMessage("/rules accept");
		}
	}
	
	/*@EventHandler (priority = EventPriority.LOWEST)
	public void onInteract(PlayerInteractEvent event)
	{
		RulePlayer rp = RulePlayer.getRulePlayer(event.getPlayer().getUniqueId());
		if(rp == null)
		{
			event.setCancelled(true);
		}
	}
	
	@EventHandler
	public void onChat(AsyncPlayerChatEvent event)
	{
		RulePlayer rp = RulePlayer.getRulePlayer(event.getPlayer().getUniqueId());
		if(rp == null)
		{
			event.setCancelled(true);
		}
	}
	
	@EventHandler
	public void onCommand(PlayerCommandPreprocessEvent event)
	{
		RulePlayer rp = RulePlayer.getRulePlayer(event.getPlayer().getUniqueId());
		if(rp == null)
		{
			event.setCancelled(true);
		}
	}
	
	@EventHandler (priority = EventPriority.LOWEST)
	public void onDamage(EntityDamageEvent event)
	{
		if(!(event.getEntity() instanceof Player))
		{
			return;
		}
		RulePlayer rp = RulePlayer.getRulePlayer(event.getEntity().getUniqueId());
		if(rp == null)
		{
			event.setCancelled(true);
		}
	}*/

}
