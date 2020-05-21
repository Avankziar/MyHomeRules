package main.java.me.avankziar.myhomerules.spigot.database;

import java.io.File;
import java.io.IOException;

import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.YamlConfiguration;

import main.java.me.avankziar.myhomerules.spigot.MyHomeRules;

public class YamlHandler
{
	private MyHomeRules plugin;
	private File config = null;
	private YamlConfiguration cfg = new YamlConfiguration();
	private File language = null;
	private YamlConfiguration lgg = new YamlConfiguration();
	private String languages;
	
	public YamlHandler(MyHomeRules plugin) 
	{
		this.plugin = plugin;
		loadYamlHandler();
	}
	
	public boolean loadYamlHandler()
	{
		if(!mkdir())
		{
			return false;
		}
		if(!loadYamls())
		{
			return false;
		}
		languages = cfg.getString("Language", "English");
		return true;
	}
	
	public YamlConfiguration get()
	{
		return cfg;
	}
	
	public YamlConfiguration getL()
	{
		return lgg;
	}
	
	public boolean mkdir() 
	{
		config = new File(plugin.getDataFolder(), "config.yml");
		if(!config.exists()) 
		{
			MyHomeRules.log.info("Create config.yml...");
			plugin.saveResource("config.yml", false);
		}
		language = new File(plugin.getDataFolder(), "language.yml");
		if(!language.exists()) 
		{
			MyHomeRules.log.info("Create language.yml...");
			plugin.saveResource("language.yml", false);
		}
		return true;
	}
	
	public boolean saveConfig() 
	{
	    try 
	    {
	    	MyHomeRules.log.info("Save config.yml...");
	        cfg.save(config);
	    } catch (IOException e) 
	    {
	    	MyHomeRules.log.severe("Could not save the config.yml! Error: " + e.getMessage());
			e.printStackTrace();
			return false;
	    }
	    return true;
	}
	
	public boolean saveLanguage() 
	{
	    try 
	    {
	    	MyHomeRules.log.info("Save language.yml...");
	        lgg.save(language);
	    } catch (IOException e) 
	    {
	    	MyHomeRules.log.severe("Could not save the language.yml! Error: " + e.getMessage());
			e.printStackTrace();
			return false;
	    }
	    return true;
	}
	
	public boolean loadYamls() 
	{
		try 
		{
			MyHomeRules.log.info("Load config.yml...");
			cfg.load(config);
		} catch (IOException | InvalidConfigurationException e) {
			MyHomeRules.log.severe("Could not load the spigotconfig file! You need to regenerate the config! Error: " + e.getMessage());
			e.printStackTrace();
			return false;
		}
		try 
		{
			lgg.load(language);
		} catch (IOException | InvalidConfigurationException e) 
		{
			MyHomeRules.log.severe("Could not load the language file! You need to regenerate the language! Error: " + e.getMessage());
			e.printStackTrace();
			return false;
		}
		return true;
	}

	public String getLanguages()
	{
		return languages;
	}
}