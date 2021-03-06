package com.massivecraft.vampire.cmd;

import java.util.*;

import org.bukkit.ChatColor;

import com.massivecraft.mcore5.MCore;
import com.massivecraft.mcore5.cmd.VisibilityMode;
import com.massivecraft.mcore5.cmd.arg.ARInteger;
import com.massivecraft.mcore5.cmd.req.ReqHasPerm;
import com.massivecraft.mcore5.usys.Multiverse;
import com.massivecraft.mcore5.util.Txt;
import com.massivecraft.vampire.*;

public class CmdList extends VCommand
{	
	public CmdList()
	{
		this.addAliases("l", "list");
		this.addOptionalArg("page", "1");
		this.addOptionalArg("universe", "you");
		
		this.setVisibilityMode(VisibilityMode.SECRET);
		
		this.addRequirements(ReqHasPerm.get(VPerm.LIST.node));
	}
	
	@Override
	public void perform()
	{
		Integer pageHumanBased = this.arg(0, ARInteger.get(), 1);
		if (pageHumanBased == null) return;
		
		Multiverse mv = P.p.playerAspect.multiverse();
		String universe = this.arg(1, mv.argReaderUniverse(), senderIsConsole ? MCore.DEFAULT : mv.getUniverse(me));
		if (universe == null) return;
		
		List<String> vampiresOnline = new ArrayList<String>();
		List<String> vampiresOffline = new ArrayList<String>();
		List<String> infectedOnline = new ArrayList<String>();
		List<String> infectedOffline = new ArrayList<String>();
		
		for (VPlayer vplayer : VPlayerColls.i.getForUniverse(universe).getAll())
		{
			if (vplayer.isVampire())
			{
				if (vplayer.isOnline())
				{
					vampiresOnline.add(ChatColor.WHITE.toString() + vplayer.getDisplayName());
				}
				else
				{
					vampiresOffline.add(ChatColor.WHITE.toString() + vplayer.getDisplayName());
				}
			}
			else if (vplayer.isInfected())
			{
				if (vplayer.isOnline())
				{
					infectedOnline.add(ChatColor.WHITE.toString() + vplayer.getDisplayName());
				}
				else
				{
					infectedOffline.add(ChatColor.WHITE.toString() + vplayer.getDisplayName());
				}
			}
		}

		// Create Messages
		List<String> lines = new ArrayList<String>();
		
		if (vampiresOnline.size() > 0)
		{
			lines.add("<h>=== Vampires Online ===");
			lines.add(Txt.implodeCommaAndDot(vampiresOnline, "<i>"));
		}
		
		if (vampiresOffline.size() > 0)
		{
			lines.add("<h>=== Vampires Offline ===");
			lines.add(Txt.implodeCommaAndDot(vampiresOffline, "<i>"));
		}
		
		if (infectedOnline.size() > 0)
		{
			lines.add("<h>=== Infected Online ===");
			lines.add(Txt.implodeCommaAndDot(infectedOnline, "<i>"));
		}
		
		if (infectedOffline.size() > 0)
		{
			lines.add("<h>=== Infected Offline ===");
			lines.add(Txt.implodeCommaAndDot(infectedOffline, "<i>"));
		}
		
		// Send them
		lines = Txt.parseWrap(lines);
		this.sendMessage(Txt.getPage(lines, pageHumanBased, Txt.upperCaseFirst(universe)+" Vampire Players", sender));	
	}
}
