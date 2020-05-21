package main.java.me.avankziar.myhomerules.spigot;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.logging.Logger;

import org.bukkit.Bukkit;
import org.bukkit.event.HandlerList;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import main.java.me.avankziar.myhomerules.spigot.assistence.BackgroundTask;
import main.java.me.avankziar.myhomerules.spigot.commands.CommandHelper;
import main.java.me.avankziar.myhomerules.spigot.commands.CommandModule;
import main.java.me.avankziar.myhomerules.spigot.commands.MultipleCommandExecutor;
import main.java.me.avankziar.myhomerules.spigot.commands.TABCompletion;
import main.java.me.avankziar.myhomerules.spigot.commands.rules.ARGAccept;
import main.java.me.avankziar.myhomerules.spigot.commands.rules.ARGInfo;
import main.java.me.avankziar.myhomerules.spigot.commands.rules.ARGReload;
import main.java.me.avankziar.myhomerules.spigot.commands.rules.ARGRevoke;
import main.java.me.avankziar.myhomerules.spigot.database.MysqlHandler;
import main.java.me.avankziar.myhomerules.spigot.database.MysqlSetup;
import main.java.me.avankziar.myhomerules.spigot.database.YamlHandler;
import main.java.me.avankziar.myhomerules.spigot.listener.PlayerListener;

public class MyHomeRules extends JavaPlugin
{
	public static Logger log;
	public String pluginName = "MyHomeRules";
	private static YamlHandler yamlHandler;
	private static MysqlSetup mysqlSetup;
	private static MysqlHandler mysqlHandler;
	private static MyHomeRules plugin;
	private static BackgroundTask backgroundTask;
	private static CommandHelper commandHelper;
	public static HashMap<String, CommandModule> rulesarguments;
	
	public void onEnable()
	{
		log = getLogger();
		yamlHandler = new YamlHandler(this);
		rulesarguments = new HashMap<String, CommandModule>();
		if (yamlHandler.get().getBoolean("Mysql.Status", false) == true)
		{
			mysqlSetup = new MysqlSetup(this);
			mysqlHandler = new MysqlHandler(this);
		} else
		{
			log.severe("MySQL is not set in the Plugin " + pluginName + "!");
			Bukkit.getPluginManager().getPlugin(pluginName).getPluginLoader().disablePlugin(this);
			return;
		}
		commandHelper = new CommandHelper(this);
		backgroundTask = new BackgroundTask(this);
		CommandSetup();
		ListenerSetup();
	}
	
	public void onDisable()
	{
		Bukkit.getScheduler().cancelTasks(this);
		rulesarguments.clear();
		HandlerList.unregisterAll(this);
		log.info(pluginName + " is disabled!");
	}

	public static MyHomeRules getPlugin()
	{
		return plugin;
	}
	
	public YamlHandler getYamlHandler() 
	{
		return yamlHandler;
	}
	
	public MysqlSetup getMysqlSetup()
	{
		return mysqlSetup;
	}
	
	public MysqlHandler getMysqlHandler()
	{
		return mysqlHandler;
	}
	
	public BackgroundTask getBackgroundTask()
	{
		return backgroundTask;
	}

	public CommandHelper getCommandHelper()
	{
		return commandHelper;
	}

	public void CommandSetup()
	{
		new ARGAccept(plugin);
		new ARGInfo(plugin);
		new ARGReload(plugin);
		new ARGRevoke(plugin);
		getCommand("rules").setExecutor(new MultipleCommandExecutor(this));
		getCommand("rules").setTabCompleter(new TABCompletion());
	}
	
	public void ListenerSetup()
	{
		PluginManager pm = getServer().getPluginManager();
		pm.registerEvents(new PlayerListener(this), this);
	}
	
	public boolean reload()
	{
		if(!yamlHandler.loadYamlHandler())
		{
			return false;
		}
		if(yamlHandler.get().getBoolean("Mysql.Status", false))
		{
			mysqlSetup.closeConnection();
			if(!mysqlHandler.loadMysqlHandler())
			{
				return false;
			}
			if(!mysqlSetup.loadMysqlSetup())
			{
				return false;
			}
		} else
		{
			return false;
		}
		return true;
	}
	
	public boolean existMethod(Class<?> externclass, String method)
	{
	    try 
	    {
	    	Method[] mtds = externclass.getMethods();
	    	for(Method methods : mtds)
	    	{
	    		if(methods.getName().equalsIgnoreCase(method))
	    		{
	    	    	//SimpleChatChannels.log.info("Method "+method+" in Class "+externclass.getName()+" loaded!");
	    	    	return true;
	    		}
	    	}
	    	return false;
	    } catch (Exception e) 
	    {
	    	return false;
	    }
	}
}