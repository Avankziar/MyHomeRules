package main.java.me.avankziar.myhomerules.spigot.database;

import java.io.File;
import java.io.IOException;

import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.craftbukkit.libs.org.apache.commons.io.FileUtils;

import main.java.me.avankziar.myhomerules.spigot.MyHomeRules;

public class YamlHandler
{
	private MyHomeRules plugin;
	private File config = null;
	private YamlConfiguration cfg = new YamlConfiguration();
	private File arabic = null;
	private YamlConfiguration ara = new YamlConfiguration();
	private File english = null;
	private YamlConfiguration eng = new YamlConfiguration();
	private File french = null;
	private YamlConfiguration fre = new YamlConfiguration();
	private File german = null;
	private YamlConfiguration ger = new YamlConfiguration();
	private File hindi = null;
	private YamlConfiguration hin = new YamlConfiguration();
	private File japanese = null;
	private YamlConfiguration jap = new YamlConfiguration();
	private File mandarin = null;
	private YamlConfiguration mad = new YamlConfiguration();
	private File spanish = null;
	private YamlConfiguration spa = new YamlConfiguration();
	private String languages;
	
	public YamlHandler(MyHomeRules plugin) 
	{
		this.plugin = plugin;
		loadYamlHandler();
	}
	
	public boolean loadYamlHandler()
	{
		if(!mkdirConfig())
		{
			return false;
		}
		if(!loadYamlTask(config, cfg, "config.yml"))
		{
			return false;
		}
		languages = cfg.getString("Language", "English");
		if(!mkdir())
		{
			return false;
		}
		if(!loadYamls())
		{
			return false;
		}
		
		return true;
	}
	
	public YamlConfiguration get()
	{
		return cfg;
	}
	
	public YamlConfiguration getL()
	{
		if(languages.equalsIgnoreCase("Arabic"))
		{
			return ara;
		} else if(languages.equalsIgnoreCase("French"))
		{
			return fre;
		} else if(languages.equalsIgnoreCase("German"))
		{
			return ger;
		} else if(languages.equalsIgnoreCase("Hindi"))
		{
			return ger;
		} else if(languages.equalsIgnoreCase("Japanese"))
		{
			return jap;
		} else if(languages.equalsIgnoreCase("Mandarin"))
		{
			return mad;
		} else if(languages.equalsIgnoreCase("Spanish"))
		{
			return spa;
		} else
		{
			return eng;
		}
	}
	
	public boolean mkdirConfig()
	{
		config = new File(plugin.getDataFolder(), "config.yml");
		if(!config.exists()) 
		{
			MyHomeRules.log.info("Create config.yml...");
			plugin.saveResource("config.yml", false);
		}
		return true;
	}
	
	private boolean mkdir()
	{
		File directory = new File(plugin.getDataFolder()+"/Languages/");
		if(!directory.exists())
		{
			directory.mkdir();
		}
		arabic = new File(directory.getPath(), "arabic.yml");
		if(!arabic.exists()) 
		{
			MyHomeRules.log.info("Create arabic.yml...");
			try
			{
				FileUtils.copyToFile(plugin.getResource("arabic.yml"), arabic);
			} catch (IOException e)
			{
				e.printStackTrace();
			}
		}
		english = new File(directory.getPath(), "english.yml");
		if(!english.exists()) 
		{
			MyHomeRules.log.info("Create english.yml...");
			try
			{
				FileUtils.copyToFile(plugin.getResource("english.yml"), english);
			} catch (IOException e)
			{
				e.printStackTrace();
			}
		}
		french = new File(directory.getPath(), "french.yml");
		if(!french.exists())
		{
			MyHomeRules.log.info("Create french.yml...");
			try
			{
				FileUtils.copyToFile(plugin.getResource("french.yml"), french);
			} catch (IOException e)
			{
				e.printStackTrace();
			}
		}
		german = new File(directory.getPath(), "german.yml");
		if(!german.exists()) 
		{
			MyHomeRules.log.info("Create german.yml...");
			try
			{
				FileUtils.copyToFile(plugin.getResource("german.yml"), german);
			} catch (IOException e)
			{
				e.printStackTrace();
			}
		}
		hindi = new File(directory.getPath(), "hindi.yml");
		if(!hindi.exists()) 
		{
			MyHomeRules.log.info("Create hindi.yml...");
			try
			{
				FileUtils.copyToFile(plugin.getResource("hindi.yml"), hindi);
			} catch (IOException e)
			{
				e.printStackTrace();
			}
		}
		japanese = new File(directory.getPath(), "japanese.yml");
		if(!japanese.exists()) 
		{
			MyHomeRules.log.info("Create japanese.yml...");
			try
			{
				FileUtils.copyToFile(plugin.getResource("japanese.yml"), japanese);
			} catch (IOException e)
			{
				e.printStackTrace();
			}
		}
		mandarin = new File(directory.getPath(), "mandarin.yml");
		if(!mandarin.exists()) 
		{
			MyHomeRules.log.info("Create mandarin.yml...");
			try
			{
				FileUtils.copyToFile(plugin.getResource("mandarin.yml"), mandarin);
			} catch (IOException e)
			{
				e.printStackTrace();
			}
		}
		spanish = new File(directory.getPath(), "spanish.yml");
		if(!spanish.exists()) 
		{
			MyHomeRules.log.info("Create spanish.yml...");
			try
			{
				FileUtils.copyToFile(plugin.getResource("spanish.yml"), spanish);
			} catch (IOException e)
			{
				e.printStackTrace();
			}
		}
		return true;
	}
	
	public boolean loadYamls()
	{
		if(!loadYamlTask(arabic, ara, "arabic.yml"))
		{
			return false;
		}
		if(!loadYamlTask(english, eng, "english.yml"))
		{
			return false;
		}
		if(!loadYamlTask(french, fre, "french.yml"))
		{
			return false;
		}
		if(!loadYamlTask(german, ger, "german.yml"))
		{
			return false;
		}
		if(!loadYamlTask(hindi, hin, "hindi.yml"))
		{
			return false;
		}
		if(!loadYamlTask(japanese, jap, "japanese.yml"))
		{
			return false;
		}
		if(!loadYamlTask(mandarin, mad, "mandarin.yml"))
		{
			return false;
		}
		if(!loadYamlTask(spanish, spa, "spanish.yml"))
		{
			return false;
		}
		return true;
	}
	
	private boolean loadYamlTask(File file, YamlConfiguration yaml, String filename)
	{
		try 
		{
			yaml.load(file);
		} catch (IOException | InvalidConfigurationException e) 
		{
			MyHomeRules.log.severe(
					"Could not load the %file% file! You need to regenerate the %file%! Error: ".replace("%file%", filename)
					+ e.getMessage());
			e.printStackTrace();
			return false;
		}
		return true;
	}
}