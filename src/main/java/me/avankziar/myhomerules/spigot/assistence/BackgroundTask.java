package main.java.me.avankziar.myhomerules.spigot.assistence;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import main.java.me.avankziar.myhomerules.spigot.MyHomeRules;
import main.java.me.avankziar.myhomerules.spigot.objects.RulePlayer;
import main.java.me.avankziar.myhomerules.spigot.objects.ServerRule;
import net.md_5.bungee.api.chat.TextComponent;

public class BackgroundTask
{
	private MyHomeRules plugin;
	public ArrayList<ArrayList<TextComponent>> multipleSite = new ArrayList<>();
	public LinkedHashMap<String, ServerRule> rules = new LinkedHashMap<>();
	
	public BackgroundTask(MyHomeRules plugin)
	{
		this.plugin = plugin;	
		loadMultipleSite();
		loadRules();
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
	
	private void loadRules()
	{
		for(String key : plugin.getYamlHandler().getRul().getKeys(false))
		{
			String name = plugin.getYamlHandler().getRul().getString(key+".Name");
			List<String> l = plugin.getYamlHandler().getRul().getStringList(key+".Lines");
			ArrayList<String> lines = new ArrayList<>();
			lines.addAll(l);
			String k = key.replace(plugin.getYamlHandler().getConfig().getString("Rules.RuleSeperator"), ".");
			ServerRule sr = new ServerRule(k, name, lines);
			rules.put(k, sr);
		}
	}
	
	private void loadMultipleSite()
	{
		boolean simpleSite = plugin.getYamlHandler().getConfig().getBoolean("RunTask.SimpleSite");
		if(!simpleSite)
		{
			for(String key : plugin.getYamlHandler().getMpS().getKeys(false))
			{
				ArrayList<TextComponent> list = new ArrayList<>();
				for(String s : plugin.getYamlHandler().getMpS().getStringList(key+".Message"))
				{
					list.add(ChatApi.generateTextComponent(s, false));
				}
				multipleSite.add(list);
			}
		}
	}
	
	public void playerMustAcceptTaskSimpleSite(Player player)
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
					if(RulePlayer.getRulePlayer(player) != null)
					{
						debug(player, "playerMustAcceptTask 2");
						cancel();
						return;
					}
					debug(player, "playerMustAcceptTask 3");
					if(count >= endcount)
					{
						debug(player, "playerMustAcceptTask 5");
						kickRunTask(player, plugin.getYamlHandler().getConfig().getInt("KickEndTimerSimpleSite"));
						cancel();
						return;
					}
					debug(player, "playerMustAcceptTask 6");
					count++;
					YamlConfiguration l = plugin.getYamlHandler().getLang();
					if(l == null)
					{
						debug(player, "playerMustAcceptTask lang == null");
						return;
					}
					player.sendTitle(ChatApi.tl(l.getString("RunTask.Title")),
							ChatApi.tl(l.getString("RunTask.SubTitle")),
							l.getInt("RunTask.FadeIn"), 
							l.getInt("RunTask.Stay"),
							l.getInt("RunTask.FadeOut"));
					debug(player, "playerMustAcceptTask 9");
					for(String message : l.getStringList("RunTask.SimpleSite.Messages"))
					{
						debug(player, "playerMustAcceptTask 10; message: "+message);
						player.spigot().sendMessage(ChatApi.generateTextComponent(message, true));
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
		}.runTaskTimerAsynchronously(plugin, 20L, 20L*60*runTaskTimer);
	}
	
	public void playerMustAcceptTaskMultipleSite(Player player)
	{
		new BukkitRunnable()
		{
			
			@Override
			public void run()
			{
				YamlConfiguration l = plugin.getYamlHandler().getLang();
				if(l == null)
				{
					debug(player, "playerMustAcceptTask lang == null");
					return;
				}
				player.sendTitle(ChatApi.tl(l.getString("RunTask.Title")),
						ChatApi.tl(l.getString("RunTask.SubTitle")),
						l.getInt("RunTask.FadeIn"), 
						l.getInt("RunTask.Stay"),
						l.getInt("RunTask.FadeOut"));
				for(TextComponent tc : multipleSite.get(0))
				{
					player.spigot().sendMessage(tc);
				}
				kickRunTask(player, plugin.getYamlHandler().getConfig().getInt("KickEndTimerMultipleSite"));
			}
		}.runTaskAsynchronously(plugin);
	}
	
	public void kickRunTask(Player player, final int kickTime)
	{
		new BukkitRunnable()
		{
			@Override
			public void run()
			{
				debug(player, "kickRunTask 1");
				if(RulePlayer.getRulePlayer(player) != null)
				{
					debug(player, "playerMustAcceptTask 2");
					cancel();
					return;
				}
				player.kickPlayer(ChatApi.tl(plugin.getYamlHandler().getLang().getString("RunTask.Kick")));
			}
		}.runTaskLater(plugin, 20L*60*kickTime);
	}

}
