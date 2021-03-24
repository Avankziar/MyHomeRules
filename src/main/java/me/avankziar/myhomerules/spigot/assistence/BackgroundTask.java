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
	
	public void playerMustAcceptTask(Player player)
	{
		int runTaskTimer = plugin.getYamlHandler().getConfig().getInt("RunTaskTimer");
		new BukkitRunnable()
		{
			int count = 0;
			final int endcount = plugin.getYamlHandler().getConfig().getInt("HowOftenSendMessageBeforeKick");
			@Override
			public void run()
			{
				if(player != null && player.isOnline())
				{
					debug(player, "playerMustAcceptTask 1, runtasktime: "+runTaskTimer);
					if(RulePlayer.getRulePlayer(player.getUniqueId()) != null)
					{
						debug(player, "playerMustAcceptTask 2");
						cancel();
						return;
					}
					debug(player, "playerMustAcceptTask 3");
					if(count >= endcount)
					{
						debug(player, "playerMustAcceptTask 5");
						kickRunTask(player);
						cancel();
						return;
					}
					debug(player, "playerMustAcceptTask 6");
					count++;
					YamlConfiguration l = plugin.getYamlHandler().getLang();
					if(l == null)
					{
						debug(player, "playerMustAcceptTask lang == null");
					}
					player.sendTitle(ChatApi.tl(l.getString("RunTask.Title")),
							ChatApi.tl(l.getString("RunTask.SubTitle")),
							l.getInt("RunTask.FadeIn"), 
							l.getInt("RunTask.Stay"),
							l.getInt("RunTask.FadeOut"));
					debug(player, "playerMustAcceptTask 9");
					for(String message : l.getStringList("RunTask.Messages"))
					{
						debug(player, "playerMustAcceptTask 10; message: "+message);
						player.spigot().sendMessage(ChatApi.generateTextComponent(message));
					}
					debug(player, "playerMustAcceptTask 12");
				} else if(player != null && !player.isOnline())
				{
					debug(player, "playerMustAcceptTask 13");
					cancel();
				} else if(player == null)
				{
					debug(player, "playerMustAcceptTask 14");
					cancel();
				}
			}
		}.runTaskTimer(plugin, 20L, 20L*60*runTaskTimer);
	}
	
	public void kickRunTask(Player player)
	{
		new BukkitRunnable()
		{
			@Override
			public void run()
			{
				debug(player, "kickRunTask 1");
				player.kickPlayer(ChatApi.tl(plugin.getYamlHandler().getLang().getString("RunTask.Kick")));
			}
		}.runTaskLater(plugin, 20L*60*plugin.getYamlHandler().getConfig().getInt("KickEndTimer"));
	}

}
