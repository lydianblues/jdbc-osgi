package com.thirdmode.jdbc;

import java.util.Dictionary;
import java.util.Hashtable;

import org.apache.felix.service.command.CommandProcessor;
import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;

public class Activator implements BundleActivator {
	
	@SuppressWarnings("unused")
	private BundleContext context;
	
	public void start(BundleContext context) {
		
		this.context = context;
		registerCommand(context);
		return;
	}

	private void registerCommand(BundleContext context) {
		Dictionary<String, Object> dict = new Hashtable<String, Object>();
		dict.put(CommandProcessor.COMMAND_SCOPE, "jdbc");
		dict.put(CommandProcessor.COMMAND_FUNCTION,
			new String[] {"version", "status", "configuration"});
		context.registerService(Command.class.getName(),
			new Command(context), dict);
	}

	@Override
	public void stop(BundleContext context) throws Exception {
		// TODO Auto-generated method stub FIXME TODO
		
	}
	  
	
}