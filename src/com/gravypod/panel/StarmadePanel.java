package com.gravypod.panel;

import java.io.File;
import java.io.IOException;

import com.gravypod.starmadewrapper.Configuration;
import com.gravypod.starmadewrapper.Server;
import com.gravypod.starmadewrapper.plugins.Plugin;
import com.gravypod.starmadewrapper.plugins.events.EventHandler;
import com.gravypod.starmadewrapper.plugins.events.Listener;
import com.gravypod.starmadewrapper.plugins.events.players.ChatEvent;
import com.gravypod.starmadewrapper.plugins.events.players.LoginEvent;
import com.gravypod.starmadewrapper.plugins.events.players.LogoutEvent;

public class StarmadePanel extends Plugin {
	
	private WrapperMonitor monitor;
	private Config config;
	@Override
	public void onEnable(Server server) {
		
		try {
			config = getConfig();
		} catch (IOException e1) {
			e1.printStackTrace();
			return;
		}
		
		monitor = new WrapperMonitor(config.port, server, config.password);
		try {
			getAssistant().getPluginManager().getEventManager().registerEvents(this, new Listener() {
				
				@EventHandler
				public void login(LoginEvent event) {
				
					monitor.updateLog("[LOGIN] " + event.getUsername() + " has logged in");
				}
				
				@EventHandler
				public void logout(LogoutEvent event) {
				
					monitor.updateLog("[LOGOUT] " + event.getUsername() + " has logged out");
				}
				
				@EventHandler
				public void logout(ChatEvent event) {
				
					monitor.updateLog("[CHAT] " + event.getUsername() + ": " + event.getMessage());
				}
			});
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			monitor.start();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public void onDisable(Server server) {
		monitor.stop();
	}
	
	
	public Config getConfig() throws IOException {
		Config c = new Config();
		File configFile = new File(getDataDir(), "config.yml");
		if (!Configuration.load(configFile, c)) {
			Configuration.save(configFile, c);
		}
		return c;
		
	}
}
