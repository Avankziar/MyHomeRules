package main.java.me.avankziar.mhr.spigot.objects;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.bukkit.OfflinePlayer;

import main.java.me.avankziar.mhr.spigot.MyHomeRules;

public class RulePlayer
{
	private String playerUUID;
	private String playerName;
	private LocalDateTime dateTime;
	private boolean revoked;
	private boolean deleted;
	
	public RulePlayer(String playerUUID, String playerName, LocalDateTime dateTime, boolean revoked, boolean deleted)
	{
		setPlayerUUID(playerUUID);
		setPlayerName(playerName);
		setDateTime(dateTime);
		setRevoked(revoked);
		setDeleted(deleted);
	}
	
	public static RulePlayer getRulePlayer(OfflinePlayer player)
	{
		return (RulePlayer) MyHomeRules.getPlugin().getMysqlHandler().getData("`player_uuid` = ?", player.getUniqueId().toString());
	}
	
	public static RulePlayer getRulePlayer(String playername)
	{
		return (RulePlayer) MyHomeRules.getPlugin().getMysqlHandler().getData("`player_name` = ?", playername);
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

	public boolean isRevoked()
	{
		return revoked;
	}

	public void setRevoked(boolean revoked)
	{
		this.revoked = revoked;
	}

	public boolean isDeleted()
	{
		return deleted;
	}

	public void setDeleted(boolean deleted)
	{
		this.deleted = deleted;
	}

}
