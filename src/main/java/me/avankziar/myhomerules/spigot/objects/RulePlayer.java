package main.java.me.avankziar.myhomerules.spigot.objects;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.UUID;

public class RulePlayer
{
	private String playerUUID;
	private String playerName;
	private LocalDateTime dateTime;
	
	public static ArrayList<RulePlayer> allRulePlayer = new ArrayList<>();
	
	public RulePlayer(String playerUUID, String playerName, LocalDateTime dateTime)
	{
		setPlayerUUID(playerUUID);
		setPlayerName(playerName);
		setDateTime(dateTime);
	}
	
	public static RulePlayer getRulePlayer(UUID uuid)
	{
		RulePlayer rp = null;
		for(RulePlayer r : allRulePlayer)
		{
			if(r.getPlayerUUID().equals(uuid.toString()))
			{
				rp = r;
				break;
			}
		}
		return rp;
	}
	
	public static RulePlayer getRulePlayer(String name)
	{
		RulePlayer rp = null;
		for(RulePlayer r : allRulePlayer)
		{
			if(r.getPlayerName().equals(name))
			{
				rp = r;
				break;
			}
		}
		return rp;
	}
	
	public static void addList(RulePlayer player)
	{
		allRulePlayer.add(player);
	}
	
	public static void removeList(RulePlayer player)
	{
		RulePlayer rp = null;
		for(RulePlayer r : allRulePlayer)
		{
			if(r.getPlayerUUID().equals(player.getPlayerUUID()))
			{
				rp = r;
				break;
			}
		}
		allRulePlayer.remove(rp);
		rp = null;
	}
	
	public static LocalDateTime deserialised(String datetime)
	{
		LocalDateTime dt = LocalDateTime.parse((CharSequence) datetime,
				DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm:ss"));
		return dt;
	}
	
	public static String serialised(LocalDateTime dt)
	{
		String MM = "";
		int month = 0;
		if(dt.getMonthValue()<10)
		{
			MM+=month;
		}
		MM += dt.getMonthValue();
		String dd = "";
		int day = 0;
		if(dt.getDayOfMonth()<10)
		{
			dd+=day;
		}
		dd +=dt.getDayOfMonth();
		String hh = "";
		int hour = 0;
		if(dt.getHour()<10)
		{
			hh+=hour;
		}
		hh += dt.getHour();
		String mm = "";
		int min = 0;
		if(dt.getMinute()<10)
		{
			mm+=min;
		}
		mm += dt.getMinute();
		String ss = "";
		int sec = 0;
		if(dt.getSecond()<10)
		{
			ss+=sec;
		}
		ss += dt.getSecond();
		return dd+"."+MM+"."+dt.getYear()+" "+hh+":"+mm+":"+ss;
	}

	public String getPlayerUUID()
	{
		return playerUUID;
	}

	public void setPlayerUUID(String playerUUID)
	{
		this.playerUUID = playerUUID;
	}

	public String getPlayerName()
	{
		return playerName;
	}

	public void setPlayerName(String playerName)
	{
		this.playerName = playerName;
	}

	public LocalDateTime getDateTime()
	{
		return dateTime;
	}

	public void setDateTime(LocalDateTime dateTime)
	{
		this.dateTime = dateTime;
	}

}
