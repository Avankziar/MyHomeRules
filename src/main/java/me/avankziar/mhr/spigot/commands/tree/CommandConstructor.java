package main.java.me.avankziar.mhr.spigot.commands.tree;

import java.util.ArrayList;

import main.java.me.avankziar.mhr.spigot.MyHomeRules;

public class CommandConstructor extends BaseConstructor
{
    public ArrayList<ArgumentConstructor> subcommands;
    public ArrayList<String> tablist;

	public CommandConstructor(String path, boolean canConsoleAccess,
    		ArgumentConstructor...argumentConstructors)
    {
		super(MyHomeRules.getPlugin().getYamlHandler().getCom().getString(path+".Name"),
				path,
				MyHomeRules.getPlugin().getYamlHandler().getCom().getString(path+".Permission"),
				MyHomeRules.getPlugin().getYamlHandler().getCom().getString(path+".Suggestion"),
				MyHomeRules.getPlugin().getYamlHandler().getCom().getString(path+".CommandString"),
				MyHomeRules.getPlugin().getYamlHandler().getCom().getString(path+".HelpInfo"),
				canConsoleAccess);
        this.subcommands = new ArrayList<>();
        this.tablist = new ArrayList<>();
        for(ArgumentConstructor ac : argumentConstructors)
        {
        	this.subcommands.add(ac);
        	this.tablist.add(ac.getName());
        }
        MyHomeRules.getPlugin().getCommandTree().add(this);
    }
}