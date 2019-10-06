package com.dolinsek.elias.trackairly.core.networking;

import org.json.JSONObject;

public class ServerStatus {

	private final int statusCode;
	private final String serverStatus, description;

	public ServerStatus(String serverStatus, String description, int statusCode) {
		this.serverStatus = serverStatus;
		this.description = description;
		this.statusCode = statusCode;
	}

	public static ServerStatus fromJSON(String toString, int statusCode) throws Exception{
		final JSONObject jsonObject = new JSONObject(toString);
		return new ServerStatus(jsonObject.getString("serverStatus"), jsonObject.getString("description"), statusCode);
	}

    public String getServerStatus() {
		return serverStatus;
	}

	public String getDescription() {
		return description;
	}
	
	
}
