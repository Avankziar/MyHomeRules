package main.java.me.avankziar.mhr.spigot.database;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.EnumSet;
import java.util.LinkedHashMap;
import java.util.List;

import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.YamlConfiguration;

import main.java.me.avankziar.mhr.spigot.MyHomeRules;
import main.java.me.avankziar.mhr.spigot.database.Language.ISO639_2B;

public class YamlHandler
{
	private MyHomeRules plugin;
	private File config = null;
	private YamlConfiguration cfg = new YamlConfiguration();
	
	private File commands = null;
	private YamlConfiguration com = new YamlConfiguration();
	
	private String languages;
	private File language = null;
	private YamlConfiguration lang = new YamlConfiguration();
	
	private File multipleSite = null;
	private YamlConfiguration mps = new YamlConfiguration();
	
	private File rules = null;
	private YamlConfiguration rul = new YamlConfiguration();

	public YamlHandler(MyHomeRules plugin)
	{
		this.plugin = plugin;
		loadYamlHandler();
	}
	
	public YamlConfiguration getConfig()
	{
		return cfg;
	}
	
	public YamlConfiguration getCom()
	{
		return com;
	}
	
	public YamlConfiguration getLang()
	{
		return lang;
	}
	
	public YamlConfiguration getMpS()
	{
		return mps;
	}
	
	public YamlConfiguration getRul()
	{
		return rul;
	}
	
	private YamlConfiguration loadYamlTask(File file, YamlConfiguration yaml)
	{
		try 
		{
			yaml.load(file);
		} catch (IOException | InvalidConfigurationException e) 
		{
			MyHomeRules.log.severe(
					"Could not load the %file% file! You need to regenerate the %file%! Error: ".replace("%file%", file.getName())
					+ e.getMessage());
			e.printStackTrace();
		}
		return yaml;
	}
	
	@SuppressWarnings("deprecation")
	private boolean writeFile(File file, YamlConfiguration yml, LinkedHashMap<String, Language> keyMap)
	{
		yml.options().header("For more explanation see \nhttps://www.spigotmc.org/resources/my-home-rules.79165/");
		for(String key : keyMap.keySet())
		{
			Language languageObject = keyMap.get(key);
			if(languageObject.languageValues.containsKey(plugin.getYamlManager().getLanguageType()) == true)
			{
				plugin.getYamlManager().setFileInput(yml, keyMap, key, plugin.getYamlManager().getLanguageType());
			} else if(languageObject.languageValues.containsKey(plugin.getYamlManager().getDefaultLanguageType()) == true)
			{
				plugin.getYamlManager().setFileInput(yml, keyMap, key, plugin.getYamlManager().getDefaultLanguageType());
			}
		}
		try
		{
			yml.save(file);
		} catch (IOException e)
		{
			e.printStackTrace();
		}
		return true;
	}
	
	public boolean loadYamlHandler()
	{
		/*
		 * Init all path from all yamls files
		 */
		plugin.setYamlManager(new YamlManager());
		/*
		 * Load all files, which are unique, for examples config.yml, commands.yml etc.
		 */
		if(!mkdirStaticFiles())
		{
			return false;
		}
		/*
		 * Load all files, which exist mutiple versions. Languages etc.
		 */
		if(!mkdirDynamicFiles())
		{
			return false;
		}
		return true;
	}
	
	public boolean mkdirStaticFiles()
	{
		/*
		 * Create the plugin general directory
		 */
		File directory = new File(plugin.getDataFolder()+"");
		if(!directory.exists())
		{
			directory.mkdir();
		}
		/*
		 * Init config.yml
		 */
		config = new File(plugin.getDataFolder(), "config.yml");
		if(!config.exists()) 
		{
			MyHomeRules.log.info("Create config.yml...");
			try(InputStream in = plugin.getResource("default.yml"))
			{
				/*
				 * If config.yml dont exist in the main directory, than create config.yml as empty file
				 */
				Files.copy(in, config.toPath());
			} catch (IOException e)
			{
				e.printStackTrace();
			}
		}
		/*
		 * Load the config.yml
		 */
		cfg = loadYamlTask(config, cfg);
        if(cfg == null)
        {
        	return false;
        }
		/*
		 * Write all path for the configfile
		 * Make sure, you use the right linkedHashmap from the YamlManager
		 */
		writeFile(config, cfg, plugin.getYamlManager().getConfigKey());
		/*
		 * Define the language
		 */
		languages = plugin.getAdministration() == null 
				? cfg.getString("Language", "ENG").toUpperCase() 
				: plugin.getAdministration().getLanguage();
		/*
		 * Repeat for all other single flatfiles.
		 */
		commands = new File(plugin.getDataFolder(), "commands.yml");
		if(!commands.exists()) 
		{
			MyHomeRules.log.info("Create commands.yml...");
			try(InputStream in = plugin.getResource("default.yml"))
			{
				//Erstellung einer "leere" config.yml
				Files.copy(in, commands.toPath());
			} catch (IOException e)
			{
				e.printStackTrace();
			}
		}
		
		com = loadYamlTask(commands, com);
        if(com == null)
        {
        	return false;
        }
		writeFile(commands, com, plugin.getYamlManager().getCommandsKey());
		return true;
	}
	
	private boolean mkdirDynamicFiles()
	{
		//Compare the using languages and set this as standard
		List<Language.ISO639_2B> types = new ArrayList<Language.ISO639_2B>(EnumSet.allOf(Language.ISO639_2B.class));
		ISO639_2B languageType = ISO639_2B.ENG;
		for(ISO639_2B type : types)
		{
			if(type.toString().equals(languages))
			{
				languageType = type;
				break;
			}
		}
		/*
		 * Set the standard languages
		 */
		plugin.getYamlManager().setLanguageType(languageType);
		/*
		 * Start to make the specific languagefile.
		 * Attention! Only one file will be created.
		 */
		if(!mkdirLanguage())
		{
			return false;
		}
		/*
		 * Here a example to create multiple flatfile for one purpose.
		 * This example is been using for inventory-guis.
		 */
		if(!mkdirMpS())
		{
			return false;
		}
		
		if(!mkdirRules())
		{
			return false;
		}
		return true;
	}
	
	private boolean mkdirLanguage()
	{
		/*
		 * Making the "prefix" thing for the filename.
		 */
		String languageString = plugin.getYamlManager().getLanguageType().toString().toLowerCase();
		/*
		 * Adding a new directory
		 */
		File directory = new File(plugin.getDataFolder()+"/Languages/");
		if(!directory.exists())
		{
			directory.mkdir();
		}
		/*
		 * The rest is equals part from the config.yml
		 */
		language = new File(directory.getPath(), languageString+".yml");
		if(!language.exists()) 
		{
			MyHomeRules.log.info("Create %lang%.yml...".replace("%lang%", languageString));
			try(InputStream in = plugin.getResource("default.yml"))
			{
				Files.copy(in, language.toPath());
			} catch (IOException e)
			{
				e.printStackTrace();
			}
		}
		lang = loadYamlTask(language, lang);
        if(lang == null)
        {
        	return false;
        }
		writeFile(language, lang, plugin.getYamlManager().getLanguageKey());
		return true;
	}
	
	private boolean mkdirMpS()
	{
		String languageString = plugin.getYamlManager().getLanguageType().toString().toLowerCase();
		File directory = new File(plugin.getDataFolder()+"/MultipleSite/");
		if(!directory.exists())
		{
			directory.mkdir();
		}
		multipleSite = new File(directory.getPath(), languageString+"-mps.yml");
		if(!multipleSite.exists()) 
		{
			MyHomeRules.log.info("Create %lang%.yml...".replace("%lang%", languageString+"-mps"));
			try(InputStream in = plugin.getResource("default.yml"))
			{
				Files.copy(in, multipleSite.toPath());
			} catch (IOException e)
			{
				e.printStackTrace();
			}
		}
		mps = loadYamlTask(multipleSite, mps);
        if(mps == null)
        {
        	return false;
        }
		writeFile(multipleSite, mps, plugin.getYamlManager().getMultipleSiteKey());
		return true;
	}
	
	private boolean mkdirRules()
	{
		String languageString = plugin.getYamlManager().getLanguageType().toString().toLowerCase();
		File directory = new File(plugin.getDataFolder()+"/Rules/");
		if(!directory.exists())
		{
			directory.mkdir();
		}
		rules = new File(directory.getPath(), languageString+"-rules.yml");
		if(!rules.exists()) 
		{
			MyHomeRules.log.info("Create %lang%.yml...".replace("%lang%", languageString+"-rules"));
			try(InputStream in = plugin.getResource("default.yml"))
			{
				Files.copy(in, rules.toPath());
			} catch (IOException e)
			{
				e.printStackTrace();
			}
		}
		rul = loadYamlTask(rules, rul);
        if(rul == null)
        {
        	return false;
        }
		writeFile(rules, rul, plugin.getYamlManager().getRulesKey());
		return true;
	}
}