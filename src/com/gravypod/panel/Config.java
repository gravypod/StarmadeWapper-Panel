package com.gravypod.panel;

import java.util.UUID;

import com.gravypod.starmadewrapper.ConfigurationFile;

public class Config implements ConfigurationFile<Config> {
	
	public int port;
	public String password;
	
	@Override
	public void setDefault() {
		port = 453;
		password = UUID.randomUUID().toString().replaceAll("-", "");
	}

	@Override
	public void set(Config t) {
		this.port = t.port;
		this.password = t.password;
		
	}
}
