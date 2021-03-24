package main.java.me.avankziar.myhomerules.spigot;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.logging.Logger;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandMap;
import org.bukkit.command.PluginCommand;
import org.bukkit.event.HandlerList;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.SimplePluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import main.java.me.avankziar.myhomerules.spigot.assistence.BackgroundTask;
import main.java.me.avankziar.myhomerules.spigot.commands.RulesCommandExecutor;
import main.java.me.avankziar.myhomerules.spigot.commands.TabCompletion;
import main.java.me.avankziar.myhomerules.spigot.commands.rules.ARGAccept;
import main.java.me.avankziar.myhomerules.spigot.commands.rules.ARGInfo;
import main.java.me.avankziar.myhomerules.spigot.commands.rules.ARGRevoke;
import main.java.me.avankziar.myhomerules.spigot.commands.tree.ArgumentConstructor;
import main.java.me.avankziar.myhomerules.spigot.commands.tree.ArgumentModule;
import main.java.me.avankziar.myhomerules.spigot.commands.tree.BaseConstructor;
import main.java.me.avankziar.myhomerules.spigot.commands.tree.CommandConstructor;
import main.java.me.avankziar.myhomerules.spigot.database.MysqlHandler;
import main.java.me.avankziar.myhomerules.spigot.database.MysqlSetup;
import main.java.me.avankziar.myhomerules.spigot.database.YamlHandler;
import main.java.me.avankziar.myhomerules.spigot.database.YamlManager;
import main.java.me.avankziar.myhomerules.spigot.listener.PlayerListener;
import main.java.me.avankziar.myhomerules.spigot.metrics.Metrics;

public class MyHomeRules extends JavaPlugin
{
	public static Logger log;
	public String pluginName = "MyHomeRules";
	private static YamlHandler yamlHandler;
	private static YamlManager yamlManager;
	private static MysqlSetup mysqlSetup;
	private static MysqlHandler mysqlHandler;
	private static MyHomeRules plugin;
	private static BackgroundTask backgroundTask;
	
	private ArrayList<CommandConstructor> commandTree;
	private ArrayList<BaseConstructor> helpList;
	private LinkedHashMap<String, ArgumentModule> argumentMap;
	public static String baseCommandI = "rules"; //Pfad angabe + ürspungliches Commandname
	
	public static String baseCommandIName = ""; //CustomCommand name
	
	public static String infoCommandPath = "CmdRules";
	public static String infoCommand = "/"; //InfoComamnd
	public static ArgumentConstructor ruleaccepting = null;
	
	public void onEnable()
	{
		plugin = this;
		log = getLogger();
		
		//https://patorjk.com/software/taag/#p=display&f=ANSI%20Shadow&t=MHR
		log.info(" ███╗   ███╗██╗  ██╗██████╗  | API-Version: "+plugin.getDescription().getAPIVersion());
		log.info(" ████╗ ████║██║  ██║██╔══██╗ | Author: "+plugin.getDescription().getAuthors().toString());
		log.info(" ██╔████╔██║███████║██████╔╝ | Plugin Website: "+plugin.getDescription().getWebsite());
		log.info(" ██║╚██╔╝██║██╔══██║██╔══██╗ | Depend Plugins: "+plugin.getDescription().getDepend().toString());
		log.info(" ██║ ╚═╝ ██║██║  ██║██║  ██║ | SoftDepend Plugins: "+plugin.getDescription().getSoftDepend().toString());
		log.info(" ╚═╝     ╚═╝╚═╝  ╚═╝╚═╝  ╚═╝ | LoadBefore: "+plugin.getDescription().getLoadBefore().toString());
		
		commandTree = new ArrayList<>();
		argumentMap = new LinkedHashMap<>();
		helpList = new ArrayList<>();
		
		yamlHandler = new YamlHandler(this);
		if (yamlHandler.getConfig().getBoolean("Mysql.Status", false) == true)
		{
			mysqlHandler = new MysqlHandler(this);
			mysqlSetup = new MysqlSetup(this);
		} else
		{
			log.severe("MySQL is not set in the Plugin " + pluginName + "!");
			Bukkit.getPluginManager().getPlugin(pluginName).getPluginLoader().disablePlugin(this);
			return;
		}
		backgroundTask = new BackgroundTask(this);
		setupStrings();
		setupCommandTree();
		ListenerSetup();
		setupBstats();
	}
	
	public void onDisable()
	{
		Bukkit.getScheduler().cancelTasks(this);
		HandlerList.unregisterAll(this);
		log.info(pluginName + " is disabled!");
	}
	
	private void setupStrings()
	{
		//Hier baseCommands
		baseCommandIName = plugin.getYamlHandler().getCom().getString(baseCommandI+".Name");
		
		//Zuletzt infoCommand deklarieren
		infoCommand += baseCommandIName;
	}
	
	private void setupCommandTree()
	{	
		ArgumentConstructor accept = new ArgumentConstructor(baseCommandI+"_accept", 0, 0, 0, false, null);
		ruleaccepting = accept;
		ArgumentConstructor info = new ArgumentConstructor(baseCommandI+"_info", 0, 0, 1, false, null);
		ArgumentConstructor revoke = new ArgumentConstructor(baseCommandI+"_revoke", 0, 0, 1, false, null);
		
		CommandConstructor rules = new CommandConstructor(baseCommandI, false,
				accept, info, revoke);
		
		registerCommand(rules.getPath(), rules.getName());
		getCommand(rules.getName()).setExecutor(new RulesCommandExecutor(plugin, rules));
		getCommand(rules.getName()).setTabCompleter(new TabCompletion(plugin));
		
		addingHelps(rules,
						accept, info, revoke);
		
		new ARGAccept(plugin, accept);
		new ARGInfo(plugin, info);
		new ARGRevoke(plugin, revoke);
	}
	
	public void ListenerSetup()
	{
		PluginManager pm = getServer().getPluginManager();
		pm.registerEvents(new PlayerListener(this), this);
	}
	
	public ArrayList<BaseConstructor> getHelpList()
	{
		return helpList;
	}
	
	public void addingHelps(BaseConstructor... objects)
	{
		for(BaseConstructor bc : objects)
		{
			helpList.add(bc);
		}
	}
	
	public ArrayList<CommandConstructor> getCommandTree()
	{
		return commandTree;
	}
	
	public CommandConstructor getCommandFromPath(String commandpath)
	{
		CommandConstructor cc = null;
		for(CommandConstructor coco : getCommandTree())
		{
			if(coco.getPath().equalsIgnoreCase(commandpath))
			{
				cc = coco;
				break;
			}
		}
		return cc;
	}
	
	public CommandConstructor getCommandFromCommandString(String command)
	{
		CommandConstructor cc = null;
		for(CommandConstructor coco : getCommandTree())
		{
			if(coco.getName().equalsIgnoreCase(command))
			{
				cc = coco;
				break;
			}
		}
		return cc;
	}

	public LinkedHashMap<String, ArgumentModule> getArgumentMap()
	{
		return argumentMap;
	}
	
	public void registerCommand(String... aliases) 
	{
		PluginCommand command = getCommand(aliases[0], plugin);
	 
		command.setAliases(Arrays.asList(aliases));
		getCommandMap().register(plugin.getDescription().getName(), command);
	}
	 
	private static PluginCommand getCommand(String name, MyHomeRules plugin) 
	{
		PluginCommand command = null;
	 
		try {
			Constructor<PluginCommand> c = PluginCommand.class.getDeclaredConstructor(String.class, Plugin.class);
			c.setAccessible(true);
	 
			command = c.newInstance(name, plugin);
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		}
	 
		return command;
	}
	 
	private static CommandMap getCommandMap() 
	{
		CommandMap commandMap = null;
	 
		try {
			if (Bukkit.getPluginManager() instanceof SimplePluginManager) 
			{
				Field f = SimplePluginManager.class.getDeclaredField("commandMap");
				f.setAccessible(true);
	 
				commandMap = (CommandMap) f.get(Bukkit.getPluginManager());
			}
		} catch (NoSuchFieldException e) {
			e.printStackTrace();
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
	 
		return commandMap;
	}

	public static MyHomeRules getPlugin()
	{
		return plugin;
	}
	
	public YamlHandler getYamlHandler() 
	{
		return yamlHandler;
	}
	
	public YamlManager getYamlManager()
	{
		return yamlManager;
	}

	public void setYamlManager(YamlManager yamlManager)
	{
		MyHomeRules.yamlManager = yamlManager;
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
	
	public boolean reload()
	{
		if(!yamlHandler.loadYamlHandler())
		{
			return false;
		}
		if(yamlHandler.getConfig().getBoolean("Mysql.Status", false))
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
	
	public void setupBstats()
	{
		int pluginId = 10791;
        new Metrics(this, pluginId);
	}
}