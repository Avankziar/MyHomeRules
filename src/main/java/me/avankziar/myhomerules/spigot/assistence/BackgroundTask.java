package main.java.me.avankziar.myhomerules.spigot.assistence;

import java.util.HashMap;

import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import main.java.me.avankziar.myhomerules.spigot.MyHomeRules;
import main.java.me.avankziar.myhomerules.spigot.objects.RulePlayer;

public class BackgroundTask
{
	private MyHomeRules plugin;
	private HashMap<Player,Integer> count = new HashMap<Player,Integer>();
	
	public BackgroundTask(MyHomeRules plugin)
	{
		this.plugin = plugin;
	}
	
	public void playerMustAcceptTask(Player player)
	{
		new BukkitRunnable()
		{
			
			@Override
			public void run()
			{
				if(player != null && player.isOnline())
				{
					if(RulePlayer.getRulePlayer(player.getUniqueId()) != null)
					{
						cancel();
						return;
					}
					if(count.containsKey(player))
					{
						int endcount = plugin.getYamlHandler().get().getInt("HowOftenSendMessageBeforeKick");
						if(count.get(player) >= endcount)
						{
							kickRunTask(player);
							cancel();
							return;
						}
						int counts = count.get(player)+1;
						count.replace(player, counts);
					} else
					{
						count.put(player, 1);
					}
					YamlConfiguration l = plugin.getYamlHandler().getL();
					String language = plugin.getYamlHandler().getLanguages();
					player.sendTitle(ChatApi.tl(l.getString(language+".RunTask.Title")),
							ChatApi.tl(l.getString(language+".RunTask.SubTitle")),
							l.getInt(language+".RunTask.FadeIn"), 
							l.getInt(language+".RunTask.Stay"),
							l.getInt(language+".RunTask.FadeOut"));
					for(String message : l.getStringList(language+".RunTask.Messages"))
					{
						player.spigot().sendMessage(ChatApi.generateTextComponent(message));
					}
					
				} else if(player != null && !player.isOnline())
				{
					cancel();
				} else if(player == null)
				{
					cancel();
				}
			}
		}.runTaskTimer(plugin, 20L, 20L*60*plugin.getYamlHandler().get().getInt("RunTaskTimer"));
	}
	
	public void kickRunTask(Player player)
	{
		new BukkitRunnable()
		{
			@Override
			public void run()
			{
				player.kickPlayer(ChatApi.tl(plugin.getYamlHandler().getL().getString(
						plugin.getYamlHandler().getLanguages()+".RunTask.Kick")));
			}
		}.runTaskLater(plugin, 20L*60*plugin.getYamlHandler().get().getInt("KickEndTimer"));
	}

}
