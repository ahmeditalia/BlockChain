package com.dottorsoft.SimpleBlockChain.networking;

public enum Commands {
	
	DATA("data"),
	SEND("send"),
	UNKNOWN_COMMAND("command unknown"),
	REGISTERING("registering"),
	PING("ping"),
	PONG("pong");
	
	private String command;
	
	private Commands(String commands){
		this.command = commands;
	}
	
	public String getCommand(){
		return this.command;
	}
	
	public String getCommand(String param){
		return String.format(this.command, param);
	}

}
