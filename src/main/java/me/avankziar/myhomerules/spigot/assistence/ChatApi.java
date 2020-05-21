package main.java.me.avankziar.myhomerules.spigot.assistence;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.configuration.file.YamlConfiguration;

import main.java.me.avankziar.myhomerules.spigot.MyHomeRules;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.chat.ComponentSerializer;


public class ChatApi
{
	public static String tl(String s)
	{
		return ChatColor.translateAlternateColorCodes('&', s);
	}
	
	public static TextComponent tc(String s)
	{
		return new TextComponent(TextComponent.fromLegacyText(s));
	}
	
	public static TextComponent tctl(String s)
	{
		return new TextComponent(TextComponent.fromLegacyText(ChatColor.translateAlternateColorCodes('&', s)));
	}
	
	public static TextComponent TextWithExtra(String s, List<BaseComponent> list)
	{
		TextComponent tc = tctl(s);
		tc.setExtra(list);
		return tc;
	}
	
	public static TextComponent clickEvent(TextComponent msg, ClickEvent.Action caction, String cmd)
	{
		msg.setClickEvent( new ClickEvent(caction, cmd));
		return msg;
	}
	
	public static TextComponent clickEvent(String text, ClickEvent.Action caction, String cmd)
	{
		TextComponent msg = null;
		msg = tctl(text);
		msg.setClickEvent( new ClickEvent(caction, cmd));
		return msg;
	}
	
	public static TextComponent hoverEvent(TextComponent msg, HoverEvent.Action haction, String[] hover)
	{
		ArrayList<BaseComponent> components = new ArrayList<>();
		TextComponent hoverMessage = new TextComponent(new ComponentBuilder().create());
		TextComponent newLine = new TextComponent(ComponentSerializer.parse("{text: \"\n\"}"));
		int i = 0; 
		for(String h : hover)
		{
			if(i == 0)
			{
				hoverMessage.addExtra(new TextComponent(new ComponentBuilder(tl(h)).create()));
			} else
			{
				hoverMessage.addExtra(newLine);
				hoverMessage.addExtra(new TextComponent(new ComponentBuilder(tl(h)).create()));
			}
			i++;
		}
		components.add(hoverMessage);
		BaseComponent[] hoverToSend = (BaseComponent[])components.toArray(new BaseComponent[components.size()]); 
		msg.setHoverEvent( new HoverEvent(haction, hoverToSend));
		return msg;
	}
	
	public static TextComponent hoverEvent(String text, HoverEvent.Action haction, String[] hover)
	{
		TextComponent msg = null;
		ArrayList<BaseComponent> components = new ArrayList<>();
		msg = tctl(text);
		TextComponent hoverMessage = new TextComponent(new ComponentBuilder().create());
		TextComponent newLine = new TextComponent(ComponentSerializer.parse("{text: \"\n\"}"));
		int i = 0; 
		for(String h : hover)
		{
			if(i == 0)
			{
				hoverMessage.addExtra(new TextComponent(new ComponentBuilder(tl(h)).create()));
			} else
			{
				hoverMessage.addExtra(newLine);
				hoverMessage.addExtra(new TextComponent(new ComponentBuilder(tl(h)).create()));
			}
			i++;
		}
		components.add(hoverMessage);
		BaseComponent[] hoverToSend = (BaseComponent[])components.toArray(new BaseComponent[components.size()]); 
		msg.setHoverEvent( new HoverEvent(haction, hoverToSend));
		return msg;
	}
	
	public static TextComponent apiChat(String text, ClickEvent.Action caction, String cmd,
			HoverEvent.Action haction, String[] hover)
	{
		TextComponent msg = null;
		msg = tctl(text);
		if(caction != null)
		{
			msg.setClickEvent( new ClickEvent(caction, cmd));
		}
		if(haction != null)
		{
			hoverEvent(msg, haction, hover);
		}
		return msg;
	}
	
	public static TextComponent apiChatItem(String text, ClickEvent.Action caction, String cmd,
			String itemStringFromReflection)
	{
		TextComponent msg = tctl(text);
		if(caction != null && cmd != null)
		{
			msg.setClickEvent( new ClickEvent(caction, cmd));
		}
		msg.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_ITEM, 
				new BaseComponent[]{new TextComponent(itemStringFromReflection)}));
		return msg;
	}
	
	public static TextComponent generateTextComponent(String message)
	{
		MyHomeRules plugin = MyHomeRules.getPlugin();
		String[] array = message.split(" ");
		YamlConfiguration cfg = plugin.getYamlHandler().get();
		String idclick = cfg.getString("Identifier.Click");
		String idhover = cfg.getString("Identifier.Hover");
		String idbook = cfg.getString("Identifier.Book");
		String sepb = cfg.getString("Seperator.BetweenFunction");
		String sepw = cfg.getString("Seperator.WhithinFuction");
		String sepspace = cfg.getString("Seperator.Space");
		String sepnewline = cfg.getString("Seperator.HoverNewLine");
		List<BaseComponent> list = new ArrayList<BaseComponent>();
		TextComponent textcomponent = ChatApi.tc("");
		for(int i = 0; i < array.length; i++)
		{
			String word = array[i];
			//Word enthält Funktion
			if(word.contains(idclick) || word.contains(idhover) || word.contains(idbook))
			{
				
				if(word.contains(sepb))
				{
					String[] functionarray = word.split(sepb);
					String originmessage = null;
					if(i+1 == array.length)
					{
						//Letzter Value
						originmessage = functionarray[0].replace(sepspace, " ");
					} else
					{
						originmessage = functionarray[0].replace(sepspace, " ")+" ";
					}
					//Eine Funktion muss mehr als einen wert haben
					if(functionarray.length<2)
					{
						continue;
					}
					TextComponent tc = ChatApi.tctl(originmessage);
					for(String f : functionarray)
					{
						if(f.contains(idclick))
						{
							String[] function = f.split(sepw);
							if(function.length!=3)
							{
								continue;
							}
							String clickaction = function[1];
							String clickstring = function[2];
							ChatApi.clickEvent(tc, ClickEvent.Action.valueOf(clickaction), clickstring);
						} else if(f.contains(idhover))
						{
							String[] function = f.split(sepw);
							if(function.length!=3)
							{
								continue;
							}
							String hoveraction = function[1];
							String hoverstringpath = function[2];
							String hoverstring = ChatApi.tl(
									plugin.getYamlHandler().getL().getString(hoverstringpath));
							ChatApi.hoverEvent(tc, HoverEvent.Action.valueOf(hoveraction),
									hoverstring.split(sepnewline));
						}
					}
					list.add(tc);
				}
			} else
			{
				//Beinhalten keine Funktion
				if(i+1 == array.length)
				{
					list.add(ChatApi.tctl(word));
				} else
				{
					list.add(ChatApi.tctl(word+" "));
				}
			}
		}
		textcomponent.setExtra(list);
		return textcomponent;
	}
}
