package main.java.me.avankziar.myhomerules.spigot.interfacehub;

import java.time.Instant;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import org.bukkit.OfflinePlayer;

import main.java.me.avankziar.interfacehub.spigot.serverrules.ServerRule;
import main.java.me.avankziar.interfacehub.spigot.serverrules.ServerRules;
import main.java.me.avankziar.myhomerules.spigot.MyHomeRules;
import main.java.me.avankziar.myhomerules.spigot.objects.RulePlayer;

public class ServerRuleAPI implements ServerRules
{
	private MyHomeRules plugin;
	public LinkedHashMap<String, ServerRule> rules = new LinkedHashMap<>();
	private static String seperator = "";
	
	public ServerRuleAPI(MyHomeRules plugin)
	{
		this.plugin = plugin;
		loadRules();
		seperator = plugin.getYamlHandler().getConfig().getString("Rules.RuleSeperator", "_");
	}
	
	private void loadRules()
	{
		for(String key : plugin.getYamlHandler().getRul().getKeys(false))
		{
			String name = plugin.getYamlHandler().getRul().getString(key+".Name");
			List<String> l = plugin.getYamlHandler().getRul().getStringList(key+".Lines");
			ArrayList<String> lines = new ArrayList<>();
			lines.addAll(l);
			ServerRule sr = new ServerRule(key, name, lines);
			rules.put(key, sr);
		}
	}

	@Override
	public ServerRule getServerRule(int num, int... subnum)
	{
		String key = String.valueOf(num);
		for(int i : subnum)
		{
			key += seperator+i;
		}
		return rules.get(key);
	}

	@Override
	public LinkedHashMap<String, ServerRule> getServerRules()
	{
		return rules;
	}

	@Override
	public boolean hasAccepted(OfflinePlayer player)
	{
		RulePlayer rp = RulePlayer.getRulePlayer(player);
		if(rp == null)
		{
			return false;
		}
		if(rp.isRevoked() || rp.isDeleted())
		{
			return false;
		}
		return true;
	}

	@Override
	public long whenAccepted(OfflinePlayer player)
	{
		RulePlayer rp = RulePlayer.getRulePlayer(player);
		if(rp == null)
		{
			return 0;
		}
		Instant instant = Instant.now();
		ZoneId systemZone = ZoneId.systemDefault();
		ZoneOffset currentOffsetForMyZone = systemZone.getRules().getOffset(instant);
		
		return rp.getDateTime().toEpochSecond(currentOffsetForMyZone)*1000;
	}
}
