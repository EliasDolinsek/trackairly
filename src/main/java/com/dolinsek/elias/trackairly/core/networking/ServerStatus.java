package com.dolinsek.elias.trackairly.core.networking;

public class ServerStatus {

	private final String serverStatus, description;

	public ServerStatus(String serverStatus, String description) {
		this.serverStatus = serverStatus;
		this.description = description;
	}

	public String getServerStatus() {
		return serverStatus;
	}

	public String getDescription() {
		return description;
	}
	
	
}
