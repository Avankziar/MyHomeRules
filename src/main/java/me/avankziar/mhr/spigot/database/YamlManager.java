package main.java.me.avankziar.mhr.spigot.database;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;

import org.bukkit.configuration.file.YamlConfiguration;

import main.java.me.avankziar.mhr.spigot.database.Language.ISO639_2B;

public class YamlManager
{
	private ISO639_2B languageType = ISO639_2B.GER;
	//The default language of your plugin. Mine is german.
	private ISO639_2B defaultLanguageType = ISO639_2B.GER;
	
	//Per Flatfile a linkedhashmap.
	private static LinkedHashMap<String, Language> configKeys = new LinkedHashMap<>();
	private static LinkedHashMap<String, Language> commandsKeys = new LinkedHashMap<>();
	private static LinkedHashMap<String, Language> languageKeys = new LinkedHashMap<>();
	private static LinkedHashMap<String, Language> multipleSiteKeys = new LinkedHashMap<>();
	private static LinkedHashMap<String, Language> rulesKeys = new LinkedHashMap<>();
	
	public YamlManager()
	{
		initConfig();
		initCommands();
		initLanguage();
		initMultipleSite();
		initRules();
	}
	
	public ISO639_2B getLanguageType()
	{
		return languageType;
	}

	public void setLanguageType(ISO639_2B languageType)
	{
		this.languageType = languageType;
	}
	
	public ISO639_2B getDefaultLanguageType()
	{
		return defaultLanguageType;
	}
	
	public LinkedHashMap<String, Language> getConfigKey()
	{
		return configKeys;
	}
	
	public LinkedHashMap<String, Language> getCommandsKey()
	{
		return commandsKeys;
	}
	
	public LinkedHashMap<String, Language> getLanguageKey()
	{
		return languageKeys;
	}
	
	public LinkedHashMap<String, Language> getMultipleSiteKey()
	{
		return multipleSiteKeys;
	}
	
	public LinkedHashMap<String, Language> getRulesKey()
	{
		return rulesKeys;
	}
	
	/*
	 * The main methode to set all paths in the yamls.
	 */
	public void setFileInput(YamlConfiguration yml, LinkedHashMap<String, Language> keyMap, String key, ISO639_2B languageType)
	{
		if(!keyMap.containsKey(key))
		{
			return;
		}
		if(yml.get(key) != null)
		{
			return;
		}
		if(keyMap.get(key).languageValues.get(languageType).length == 1)
		{
			if(keyMap.get(key).languageValues.get(languageType)[0] instanceof String)
			{
				yml.set(key, ((String) keyMap.get(key).languageValues.get(languageType)[0]).replace("\r\n", ""));
			} else
			{
				yml.set(key, keyMap.get(key).languageValues.get(languageType)[0]);
			}
		} else
		{
			List<Object> list = Arrays.asList(keyMap.get(key).languageValues.get(languageType));
			ArrayList<String> stringList = new ArrayList<>();
			if(list instanceof List<?>)
			{
				for(Object o : list)
				{
					if(o instanceof String)
					{
						stringList.add(((String) o).replace("\r\n", ""));
					} else
					{
						stringList.add(o.toString().replace("\r\n", ""));
					}
				}
			}
			yml.set(key, (List<String>) stringList);
		}
	}
	
	public void initConfig() //INFO:Config
	{
		configKeys.put("Language"
				, new Language(new ISO639_2B[] {ISO639_2B.GER}, new Object[] {
				"ENG"}));
		configKeys.put("Mysql.Status"
				, new Language(new ISO639_2B[] {ISO639_2B.GER}, new Object[] {
				false}));
		configKeys.put("Mysql.Host"
				, new Language(new ISO639_2B[] {ISO639_2B.GER}, new Object[] {
				"127.0.0.1"}));
		configKeys.put("Mysql.Port"
				, new Language(new ISO639_2B[] {ISO639_2B.GER}, new Object[] {
				3306}));
		configKeys.put("Mysql.DatabaseName"
				, new Language(new ISO639_2B[] {ISO639_2B.GER}, new Object[] {
				"mydatabase"}));
		configKeys.put("Mysql.SSLEnabled"
				, new Language(new ISO639_2B[] {ISO639_2B.GER}, new Object[] {
				false}));
		configKeys.put("Mysql.AutoReconnect"
				, new Language(new ISO639_2B[] {ISO639_2B.GER}, new Object[] {
				true}));
		configKeys.put("Mysql.VerifyServerCertificate"
				, new Language(new ISO639_2B[] {ISO639_2B.GER}, new Object[] {
				false}));
		configKeys.put("Mysql.User"
				, new Language(new ISO639_2B[] {ISO639_2B.GER}, new Object[] {
				"admin"}));
		configKeys.put("Mysql.Password"
				, new Language(new ISO639_2B[] {ISO639_2B.GER}, new Object[] {
				"not_0123456789"}));
		configKeys.put("Mysql.TableNameI"
				, new Language(new ISO639_2B[] {ISO639_2B.GER}, new Object[] {
				"MyHomeRulesPlayerData"}));
		
		configKeys.put("RunTaskTimer"
				, new Language(new ISO639_2B[] {ISO639_2B.GER}, new Object[] {
				1}));
		configKeys.put("KickEndTimerSimpleSite"
				, new Language(new ISO639_2B[] {ISO639_2B.GER}, new Object[] {
				1}));
		configKeys.put("KickEndTimerMultipleSite"
				, new Language(new ISO639_2B[] {ISO639_2B.GER}, new Object[] {
				5}));
		configKeys.put("HowOftenSendMessageBeforeKick"
				, new Language(new ISO639_2B[] {ISO639_2B.GER}, new Object[] {
				4}));
		configKeys.put("CustomCommand"
				, new Language(new ISO639_2B[] {ISO639_2B.GER}, new Object[] {
				"/dsgvo"}));
		
		configKeys.put("Use.CommandByAccept"
				, new Language(new ISO639_2B[] {ISO639_2B.GER}, new Object[] {
				false}));
		configKeys.put("Use.CommandByRevoke"
				, new Language(new ISO639_2B[] {ISO639_2B.GER}, new Object[] {
				false}));
		configKeys.put("CommandsBy.Accept"
				, new Language(new ISO639_2B[] {ISO639_2B.GER}, new Object[] {
				"player<->default test %player%",
				"console<->default test %player%"}));
		configKeys.put("CommandsBy.Revoke"
				, new Language(new ISO639_2B[] {ISO639_2B.GER}, new Object[] {
				"player<->default test %player%",
				"console<->default test %player%"}));
		
		configKeys.put("RunTask.SimpleSite",
				new Language(new ISO639_2B[] {ISO639_2B.GER, ISO639_2B.ENG}, new Object[] {
						true,
						true}));
		
		configKeys.put("Rules.RuleSeperator",
				new Language(new ISO639_2B[] {ISO639_2B.GER, ISO639_2B.ENG}, new Object[] {
						"x",
						"x"}));
		
		configKeys.put("Identifier.Click"
				, new Language(new ISO639_2B[] {ISO639_2B.GER}, new Object[] {
				"click"}));
		configKeys.put("Identifier.Hover"
				, new Language(new ISO639_2B[] {ISO639_2B.GER}, new Object[] {
				"hover"}));
		configKeys.put("Seperator.BetweenFunction"
				, new Language(new ISO639_2B[] {ISO639_2B.GER}, new Object[] {
				"~"}));
		configKeys.put("Seperator.WhithinFuction"
				, new Language(new ISO639_2B[] {ISO639_2B.GER}, new Object[] {
				"@"}));
		configKeys.put("Seperator.Space"
				, new Language(new ISO639_2B[] {ISO639_2B.GER}, new Object[] {
				"+"}));
		configKeys.put("Seperator.HoverNewLine"
				, new Language(new ISO639_2B[] {ISO639_2B.GER}, new Object[] {
				"~!~"}));
	}
	
	//INFO:Commands
	public void initCommands()
	{
		//comBypass();
		commandsInput("rule", "rule", "rule.cmd.rule", 
				"/rule", "/rule ",
				"&c/rule &f| Zeigt pro Seite 10 ServerRegeln an.",
				"&c/rule &f| Displays 10 server rules per page.");
		String path = "rules";
		commandsInput(path, "rules", "rules.cmd.rules", 
				"/rules", "/rules ",
				"&c/rules &f| Zeigt die >Akzeptiere die Regeln< Seite an.",
				"&c/rules &f| Shows the >Accept the rules< site.");
		String basePermission = "rules.cmd";
		argumentInput(path+"_accept", "accept", basePermission,
				"/rules accept", "/rules accept ",
				"&c/rules accept &f| Akzeptiert die Regeln",
				"&c/rules accpet &f| Accept the rules.");
		argumentInput(path+"_delete", "delete", basePermission,
				"/rules delete <playername>", "/rules delete ",
				"&c/rules delete <Spielernamen> &f| Stellt den User als >gelöscht< dar.",
				"&c/rules delete <playername> &f| Represents the user as >deleted<.");
		argumentInput(path+"_info", "info", basePermission,
				"/rules info", "/rules info ",
				"&c/rules info <Zahl> &f| Infobefehl",
				"&c/rules info <number> &f| Infobefehl.");
		argumentInput(path+"_revoke", "revoke", basePermission,
				"/rules revoke", "/rules revoke ",
				"&c/rules revoke &f| Wiederruft die Akzeptierung.",
				"&c/rules revoke &f| Revoke the accept of the rules.");
		argumentInput(path+"_site", "site", basePermission,
				"/rules site [number]", "/rules site ",
				"&c/rules site [Seitenzahl] &f| Zeigt die Seite der multiblen Regelseiten an.",
				"&c/rules site [number] &f| Displays the page of multiple rule pages.");
		argumentInput(path+"_todeletelist", "todeletelist", basePermission,
				"/rules todeletelist", "/rules todeletelist ",
				"&c/rules todeletelist &f| Listet alle Spieler auf, welche die Regeln wiederrufen haben.",
				"&c/rules todeletelist &f| Lists all players who have revoked the rules.");
	}
	
	/*private void comBypass() //INFO:ComBypass
	{
		String path = "Bypass.";
		commandsKeys.put(path+"Perm1.Perm"
				, new Language(new ISO639_2B[] {ISO639_2B.GER}, new Object[] {
				"perm.bypass.perm"}));
	}*/
	
	private void commandsInput(String path, String name, String basePermission, 
			String suggestion, String commandString,
			String helpInfoGerman, String helpInfoEnglish)
	{
		commandsKeys.put(path+".Name"
				, new Language(new ISO639_2B[] {ISO639_2B.GER}, new Object[] {
				name}));
		commandsKeys.put(path+".Permission"
				, new Language(new ISO639_2B[] {ISO639_2B.GER}, new Object[] {
				basePermission}));
		commandsKeys.put(path+".Suggestion"
				, new Language(new ISO639_2B[] {ISO639_2B.GER}, new Object[] {
				suggestion}));
		commandsKeys.put(path+".CommandString"
				, new Language(new ISO639_2B[] {ISO639_2B.GER}, new Object[] {
				commandString}));
		commandsKeys.put(path+".HelpInfo"
				, new Language(new ISO639_2B[] {ISO639_2B.GER, ISO639_2B.ENG}, new Object[] {
				helpInfoGerman,
				helpInfoEnglish}));
	}
	
	private void argumentInput(String path, String argument, String basePermission, 
			String suggestion, String commandString,
			String helpInfoGerman, String helpInfoEnglish)
	{
		commandsKeys.put(path+".Argument"
				, new Language(new ISO639_2B[] {ISO639_2B.GER}, new Object[] {
				argument}));
		commandsKeys.put(path+".Permission"
				, new Language(new ISO639_2B[] {ISO639_2B.GER}, new Object[] {
				basePermission+"."+argument}));
		commandsKeys.put(path+".Suggestion"
				, new Language(new ISO639_2B[] {ISO639_2B.GER}, new Object[] {
				suggestion}));
		commandsKeys.put(path+".CommandString"
				, new Language(new ISO639_2B[] {ISO639_2B.GER}, new Object[] {
				commandString}));
		commandsKeys.put(path+".HelpInfo"
				, new Language(new ISO639_2B[] {ISO639_2B.GER, ISO639_2B.ENG}, new Object[] {
				helpInfoGerman,
				helpInfoEnglish}));
	}
	
	public void initLanguage() //INFO:Languages
	{
		languageKeys.put("InputIsWrong",
				new Language(new ISO639_2B[] {ISO639_2B.GER, ISO639_2B.ENG}, new Object[] {
						"&cDeine Eingabe ist fehlerhaft! Klicke hier auf den Text, um weitere Infos zu bekommen!",
						"&cYour input is incorrect! Click here on the text to get more information!"}));
		languageKeys.put("NoIFH",
				new Language(new ISO639_2B[] {ISO639_2B.GER, ISO639_2B.ENG}, new Object[] {
						"&cDas Plugin InterfaceHub ist nicht installiert!",
						"&cThe InterfaceHub plugin is not installed!"}));
		languageKeys.put("NoPermission",
				new Language(new ISO639_2B[] {ISO639_2B.GER, ISO639_2B.ENG}, new Object[] {
						"&cDu hast dafür keine Rechte!",
						"&cYou have no rights!"}));
		languageKeys.put("NoPlayerExist",
				new Language(new ISO639_2B[] {ISO639_2B.GER, ISO639_2B.ENG}, new Object[] {
						"&cDer Spieler existiert nicht!",
						"&cThe player does not exist!"}));
		languageKeys.put("NoNumber",
				new Language(new ISO639_2B[] {ISO639_2B.GER, ISO639_2B.ENG}, new Object[] {
						"&cDas Argument &f%arg% &cmuss eine ganze Zahl sein.",
						"&cThe argument &f%arg% &cmust be a whole number."}));
		languageKeys.put("GeneralHover", 
				new Language(new ISO639_2B[] {ISO639_2B.GER, ISO639_2B.ENG}, new Object[] {
						"&eKlick mich!",
						"&eClick me!"}));
		languageKeys.put("RunTask.Title",
				new Language(new ISO639_2B[] {ISO639_2B.GER, ISO639_2B.ENG}, new Object[] {
						"&c&oAchtung!",
						"&c&oAttention!"}));
		languageKeys.put("RunTask.SubTitle",
				new Language(new ISO639_2B[] {ISO639_2B.GER, ISO639_2B.ENG}, new Object[] {
						"&c&nBitte bestätige die Regeln mit &f/rules",
						"&c&nPlease confirm the rules with &f/rules"}));
		languageKeys.put("RunTask.FadeIn",
				new Language(new ISO639_2B[] {ISO639_2B.GER, ISO639_2B.ENG}, new Object[] {
						10,
						10}));
		languageKeys.put("RunTask.Stay",
				new Language(new ISO639_2B[] {ISO639_2B.GER, ISO639_2B.ENG}, new Object[] {
						300,
						300}));
		languageKeys.put("RunTask.FadeOut",
				new Language(new ISO639_2B[] {ISO639_2B.GER, ISO639_2B.ENG}, new Object[] {
						10,
						10}));
		languageKeys.put("RunTask.SimpleSite.Messages",
				new Language(new ISO639_2B[] {ISO639_2B.GER, ISO639_2B.ENG}, new Object[] {
						"§d======§c======§e======§7[§bMyHome&4Rules§7]§e======§c======§d======",
						"§bUm ein angenehmes Klima auf dem Server zu ermöglichen,",
						"§bmüssen gewisse Regeln eingehalten werden. §c/regeln~click@RUN_COMMAND@/rules~hover@SHOW_TEXT@Hover.Message.LineThree",
						"§bDie DSGVO Richtlinien findest du unter §c/dsgvolink~click@RUN_COMMAND@/dsgvolink~hover@SHOW_TEXT@Hover.Message.LineTwo",
						"§bNeuigkeiten und Nachrichten kannst du per §c/forum~click@RUN_COMMAND@/forum~hover@SHOW_TEXT@Hover.Message.LineFour §bnachlesen.",
						"§2-----------------------------------------------------",
						"§0.",
						"§6=====§2Klick §2hier§6======>>> §4§l§o/dsgvo~click@RUN_COMMAND@/dsgvo~hover@SHOW_TEXT@Hover.Message.LineSix §6<<<======§2Klick §2hier§6=====",
						"§0.",
						"§2-----------------------------------------------------",
						
						"§d======§c======§e======§7[§bMyHome&4Rules§7]§e======§c======§d======",
						"§bTo enable a pleasant climate on the server,",
						"§bcertain rules must be observed. §c/regeln~click@RUN_COMMAND@/rules~hover@SHOW_TEXT@Hover.Message.LineThree",
						"§bThe DSGVO guidelines can be found at §c/dsgvolink~click@RUN_COMMAND@/dsgvolink~hover@SHOW_TEXT@Hover.Message.LineTwo",
						"§bYou can get news and updates via §c/forum~click@RUN_COMMAND@/forum~hover@SHOW_TEXT@Hover.Message.LineFour §b.",
						"§2-----------------------------------------------------",
						" ",
						"§6=====§2Click §2here§6======>>> §4§l§o/dsgvo~click@RUN_COMMAND@/dsgvo~hover@SHOW_TEXT@Hover.Message.LineSix §6<<<======§2Click §2here§6=====",
						" ",
						"§2-----------------------------------------------------"}));
		languageKeys.put("RunTask.Kick",
				new Language(new ISO639_2B[] {ISO639_2B.GER, ISO639_2B.ENG}, new Object[] {
						"&cDu musst die Regeln & DSGVO akzeptieren bevor du weiterspielen kannst.",
						"&cYou must accept the rules & DSGVO before you can continue playing."}));
		languageKeys.put("Hover.Message.LineTwo",
				new Language(new ISO639_2B[] {ISO639_2B.GER, ISO639_2B.ENG}, new Object[] {
						"&eKlicke hier um den Befehl &c/dsgvolink &edirekt auszuführen.~!~&eDieses leitet dich dann zu den DSGVO Richtlinien weiter.",
						"&eClick here to execute the command &c/dsgvolink &edirectly.~!~&e This will take you to the DSGVO guidelines."}));
		languageKeys.put("Hover.Message.LineThree",
				new Language(new ISO639_2B[] {ISO639_2B.GER, ISO639_2B.ENG}, new Object[] {
						"&eKlicke hier um den Befehl &c/regeln &edirekt auszuführen.~!~&eDieses leitet dich dann zu den eigentlichen Regeln weiter.",
						"&eClick here to execute the command &c/rules &edirectly.~!~&eThis will redirect you to the actual rules."}));
		languageKeys.put("Hover.Message.LineFour",
				new Language(new ISO639_2B[] {ISO639_2B.GER, ISO639_2B.ENG}, new Object[] {
						"&eKlicke hier für die Weiterleitung zum Forum.~!~&eDort kannst du alle Updates, Neuigkeiten und~!~&eDisskusionen finden, und sogar über Themen abstimmen.",
						"&eClick here to be redirected to the forum.~!~&There you can find all updates, news and~!~&discussions, and even vote on topics."}));
		languageKeys.put("Hover.Message.LineSix",
				new Language(new ISO639_2B[] {ISO639_2B.GER, ISO639_2B.ENG}, new Object[] {
						"&eKlicke hier zum Bestätigen der Regeln.",
						"&eClick here to confirm the rules."}));
		languageKeys.put("Hover.Message.Base",
				new Language(new ISO639_2B[] {ISO639_2B.GER, ISO639_2B.ENG}, new Object[] {
						"&eKlicke hier zum Aufrufen der Regeln auf der Webseite.",
						"&eClick here to view the rules on the website."}));
		languageKeys.put("CmdRules.Base",
				new Language(new ISO639_2B[] {ISO639_2B.GER, ISO639_2B.ENG}, new Object[] {
						"§bvvv",
						"§eAlle Regeln findest du §chier~click@OPEN_URL@https://de.wikipedia.org/wiki/Regel_(Richtlinie)~hover@SHOW_TEXT@Hover.Message.Base",
						"§b^^^",
						
						"§bvvv",
						"§eAll rules you will find §chere~click@OPEN_URL@https://de.wikipedia.org/wiki/Regel_(Richtlinie)~hover@SHOW_TEXT@Hover.Message.Base",
						"§b^^^"}));
		languageKeys.put("CmdRule.Line",
				new Language(new ISO639_2B[] {ISO639_2B.GER, ISO639_2B.ENG}, new Object[] {
						"&e===&c%number%&f: &r%rulename%&e====",
						"&e===&c%number%&f: &r%rulename%&e===="}));
		languageKeys.put("CmdRules.BaseInfo.Headline",
				new Language(new ISO639_2B[] {ISO639_2B.GER, ISO639_2B.ENG}, new Object[] {
						"&e==========&7[&bMyHome&4Rules&7]&e==========",
						"&e==========&7[&bMyHome&4Rules&7]&e=========="}));
		languageKeys.put("CmdRules.BaseInfo.Next",
				new Language(new ISO639_2B[] {ISO639_2B.GER, ISO639_2B.ENG}, new Object[] {
						"&e&nnächste Seite &e==>",
						"&e&nnext page &e==>"}));
		languageKeys.put("CmdRules.BaseInfo.Past",
				new Language(new ISO639_2B[] {ISO639_2B.GER, ISO639_2B.ENG}, new Object[] {
						"&e<== &nvorherige Seite",
						"&e<== &nlast page"}));
		languageKeys.put("CmdRules.Accept.Accepting",
				new Language(new ISO639_2B[] {ISO639_2B.GER, ISO639_2B.ENG}, new Object[] {
						"&6Du hast die Regeln akzeptiert! Noch viel Spaß auf dem Server.",
						"&6You accepted the rules! Have fun on the server."}));
		languageKeys.put("CmdRules.Accept.AlreadyAccepted",
				new Language(new ISO639_2B[] {ISO639_2B.GER, ISO639_2B.ENG}, new Object[] {
						"&eDu hast die Regeln & DSGVO schon am &6%time% &eakzeptiert.",
						"&eYou have already accepted the rules & DSGVO on &6%time%."}));
		languageKeys.put("CmdRules.Delete.PlayerHasNotRevoke",
				new Language(new ISO639_2B[] {ISO639_2B.GER, ISO639_2B.ENG}, new Object[] {
						"&cDer Spieler hat die Regeln nicht wiederrufen.",
						"&cThe player has not revoked the rules."}));
		languageKeys.put("CmdRules.Delete.ThePlayerIsConsideredDeleted",
				new Language(new ISO639_2B[] {ISO639_2B.GER, ISO639_2B.ENG}, new Object[] {
						"&eDer Spieler gilt nun als gelöscht.",
						"&eThe player is now considered deleted."}));
		languageKeys.put("CmdRules.Revoke.NoAccept",
				new Language(new ISO639_2B[] {ISO639_2B.GER, ISO639_2B.ENG}, new Object[] {
						"&cDu hast die Regeln & DSGVO nicht akzeptiert. Somit kannst du sie auch nicht widerrufen!",
						"&cYou have not accepted the rules & DSGVO. Therefore you can not revoke them!"}));
		languageKeys.put("CmdRules.Revoke.Warning",
				new Language(new ISO639_2B[] {ISO639_2B.GER, ISO639_2B.ENG}, new Object[] {
						"&c&oAchtung! &r&cDurch das Widerrufen der Regeln & DSGVO, wirst du nicht nur vom Server gekickt, sondern die Betreiber des Servers behalten sich das Recht vor, all deine Ingame Wertgegenstände oder Ähnliches, zu löschen oder andersweitig zu verarbeiten. Du gibst somit alle Rechte dies bezüglich auf! Bitte bestätige mit &f/rules widerrufen bestätigen&c.",
						"&c&oAttention! &r&cBy revoking the rules & DSGVO, you will not only be kicked from the server, but the operators of the server reserve the right to delete or otherwise process all your ingame valuables or similar. You give up all rights regarding this! Please confirm with &f/rules revoked confirm&c."}));
		languageKeys.put("CmdRules.Revoke.Kick",
				new Language(new ISO639_2B[] {ISO639_2B.GER, ISO639_2B.ENG}, new Object[] {
						"&cDu hast die Regeln & DSGVO widerrufen. Damit hast du alle Rechte auf erspielte und gekaufte Gegenstände aufgegeben. &bWir wünschen dir trotzdem alles Gute auf deinem Lebensweg!",
						"&cYou have revoked the rules & DSGVO. This means that you have given up all rights to earned and purchased items. &bWe wish you all the best on your path through life anyway!"}));
		languageKeys.put("CmdRules.Site.PageNotAvailable",
				new Language(new ISO639_2B[] {ISO639_2B.GER, ISO639_2B.ENG}, new Object[] {
						"&cDiese Seite ist nicht vorhanden!",
						"&cThis page does not exist!"}));
		languageKeys.put("CmdRules.ToDeleteList.Headline",
				new Language(new ISO639_2B[] {ISO639_2B.GER, ISO639_2B.ENG}, new Object[] {
						"&e=====&bSpieler welche die Regeln wiederrufen haben&e=====",
						"&e=====&bPlayers who have revoked the rules&e====="}));
		languageKeys.put("CmdRules.ToDeleteList.NoPlayerHasRevoke",
				new Language(new ISO639_2B[] {ISO639_2B.GER, ISO639_2B.ENG}, new Object[] {
						"&cKein Spieler hat in letzter Zeit, die Regeln wiederrufen.",
						"&cNo player has recently revoked the rules."}));
		languageKeys.put("CmdRules.ToDeleteList.Hover",
				new Language(new ISO639_2B[] {ISO639_2B.GER, ISO639_2B.ENG}, new Object[] {
						"&eKlicke hier, um den Spieler als &7>&egelöscht&7< &ezu setzten.",
						"&eClick here to set the player as &7>&deleted&7<&e."}));
		
		/*languageKeys.put(""
				, new Language(new ISO639_2B[] {ISO639_2B.GER, ISO639_2B.ENG}, new Object[] {
				"",
				""}))*/
	}
	public void initMultipleSite() //INFO:MultipleSite
	{
		multipleSiteKeys.put("1.Message",
				new Language(new ISO639_2B[] {ISO639_2B.GER, ISO639_2B.ENG}, new Object[] {
						"§d======§c======§e======§7[§bMyHome&4Rules§7]§e======§c======§d======",
						"§bUm ein angenehmes Klima auf dem Server zu ermöglichen,",
						"§bmüssen gewisse Regeln eingehalten werden. §c/regeln~click@RUN_COMMAND@/rules~hover@SHOW_TEXT@1.Hover.Message.LineThree",
						"§bDie DSGVO Richtlinien findest du unter §c/dsgvolink~click@RUN_COMMAND@/dsgvolink~hover@SHOW_TEXT@1.Hover.Message.LineTwo",
						"§bNeuigkeiten und Nachrichten kannst du per §c/forum~click@RUN_COMMAND@/forum~hover@SHOW_TEXT@1.Hover.Message.LineFour §bnachlesen.",
						"§2-----------------------------------------------------",
						"§0.",
						"§6=====§2Klick §2hier§6======>>> §4§l§o/dsgvo~click@RUN_COMMAND@/dsgvo~hover@SHOW_TEXT@1.Hover.Message.LineSix §6<<<======§2Klick §2hier§6=====",
						"§0.",
						"§2-----------------------------------------------------",
						"§enächste+Seite+=>~click@RUN_COMMAND@/rules+site+2~hover@SHOW_TEXT@1.Hover.Command.Next",
						
						"§d======§c======§e======§7[§bMyHome&4Rules§7]§e======§c======§d======",
						"§bTo enable a pleasant climate on the server,",
						"§bcertain rules must be observed. §c/regeln~click@RUN_COMMAND@/rules~hover@SHOW_TEXT@1.Hover.Message.LineThree",
						"§bThe DSGVO guidelines can be found at §c/dsgvolink~click@RUN_COMMAND@/dsgvolink~hover@1.SHOW_TEXT@Hover.Message.LineTwo",
						"§bYou can get news and updates via §c/forum~click@RUN_COMMAND@/forum~hover@SHOW_TEXT@1.Hover.Message.LineFour §b.",
						"§2-----------------------------------------------------",
						" ",
						"§6=====§2Click §2here§6======>>> §4§l§o/dsgvo~click@RUN_COMMAND@/dsgvo~hover@SHOW_TEXT@1.Hover.Message.LineSix §6<<<======§2Click §2here§6=====",
						" ",
						"§2-----------------------------------------------------",
						"§enext+site+=>~click@RUN_COMMAND@/rules+site+2~hover@SHOW_TEXT@1.Hover.Command.Next"}));
		multipleSiteKeys.put("1.Hover.Message.LineTwo",
				new Language(new ISO639_2B[] {ISO639_2B.GER, ISO639_2B.ENG}, new Object[] {
						"&eKlicke hier um den Befehl &c/dsgvolink &edirekt auszuführen.~!~&eDieses leitet dich dann zu den DSGVO Richtlinien weiter.",
						"&eClick here to execute the command &c/dsgvolink &edirectly.~!~&e This will take you to the DSGVO guidelines."}));
		multipleSiteKeys.put("1.Hover.Message.LineThree",
				new Language(new ISO639_2B[] {ISO639_2B.GER, ISO639_2B.ENG}, new Object[] {
						"&eKlicke hier um den Befehl &c/regeln &edirekt auszuführen.~!~&eDieses leitet dich dann zu den eigentlichen Regeln weiter.",
						"&eClick here to execute the command &c/rules &edirectly.~!~&eThis will redirect you to the actual rules."}));
		multipleSiteKeys.put("1.Hover.Message.LineFour",
				new Language(new ISO639_2B[] {ISO639_2B.GER, ISO639_2B.ENG}, new Object[] {
						"&eKlicke hier für die Weiterleitung zum Forum.~!~&eDort kannst du alle Updates, Neuigkeiten und~!~&eDisskusionen finden, und sogar über Themen abstimmen.",
						"&eClick here to be redirected to the forum.~!~&There you can find all updates, news and~!~&discussions, and even vote on topics."}));
		multipleSiteKeys.put("1.Hover.Message.LineSix",
				new Language(new ISO639_2B[] {ISO639_2B.GER, ISO639_2B.ENG}, new Object[] {
						"&eKlicke hier zum Bestätigen der Regeln.",
						"&eClick here to confirm the rules."}));
		multipleSiteKeys.put("1.Hover.Message.Base",
				new Language(new ISO639_2B[] {ISO639_2B.GER, ISO639_2B.ENG}, new Object[] {
						"&eKlicke hier zum Aufrufen der Regeln auf der Webseite.",
						"&eClick here to view the rules on the website."}));
		multipleSiteKeys.put("1.Hover.Command.Next",
				new Language(new ISO639_2B[] {ISO639_2B.GER, ISO639_2B.ENG}, new Object[] {
						"&eKlicke hier zum wechseln zur nächsten Seite.",
						"&eClick here to go to the next page."}));
		multipleSiteKeys.put("1.Hover.Command.Past",
				new Language(new ISO639_2B[] {ISO639_2B.GER, ISO639_2B.ENG}, new Object[] {
						"&eKlicke hier zum wechseln zur vorherigen Seite.",
						"&eClick here to go to the past page."}));
	}
	
	public void initRules() //INFO:Rules
	{
		rulesKeys.put("1.Number",
				new Language(new ISO639_2B[] {ISO639_2B.GER, ISO639_2B.ENG}, new Object[] {
						"1",
						"1"}));
		rulesKeys.put("1.Name",
				new Language(new ISO639_2B[] {ISO639_2B.GER, ISO639_2B.ENG}, new Object[] {
						"§9Seid freundlich zueinander",
						"§9Be kind to each other"}));
		rulesKeys.put("1.Lines",
				new Language(new ISO639_2B[] {ISO639_2B.GER, ISO639_2B.ENG}, new Object[] {
						"§eKeine Beleidigungen.",
						"§6Keine Anfeindungen.",
						"§eNo insults.",
						"§6No hostility."}));
	}
}
