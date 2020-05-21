package main.java.me.avankziar.myhomerules.spigot.assistence;

import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import main.java.me.avankziar.myhomerules.spigot.MyHomeRules;
import main.java.me.avankziar.myhomerules.spigot.objects.RulePlayer;

public class BackgroundTask
{
	private MyHomeRules plugin;
	
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
					}
					YamlConfiguration l = plugin.getYamlHandler().getL();
					String language = plugin.getYamlHandler().getLanguages();
					player.sendTitle(l.getString(language+".RunTask.Title"),
							l.getString(language+".RunTask.SubTitle"),
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
		}.runTaskTimer(plugin, 10L, 20L*60*plugin.getYamlHandler().get().getInt("RunTaskTimer"));
	}

}
