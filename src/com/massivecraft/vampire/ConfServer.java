package com.massivecraft.vampire;

import java.util.List;

import com.massivecraft.mcore5.SimpleConfig;
import com.massivecraft.mcore5.util.MUtil;

public class ConfServer extends SimpleConfig
{
	// -------------------------------------------- //
	// META
	// -------------------------------------------- //
	
	public static String dburi = "default";
	public static int taskInterval = 10; // Defines how often the task runs.
	public static List<String> baseCommandAliases = MUtil.list("v");
	
	// -------------------------------------------- //
	// PERSISTENCE
	// -------------------------------------------- //
	
	public static transient ConfServer i = new ConfServer();
	public ConfServer() { super(P.p); }
}
