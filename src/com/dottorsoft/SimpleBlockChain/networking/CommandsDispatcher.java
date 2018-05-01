package com.dottorsoft.SimpleBlockChain.networking;

import com.google.gson.GsonBuilder;

public class CommandsDispatcher {
	
	private static CommandsDispatcher dispatcher = null;
	
	private CommandsDispatcher(){}
	
	public static CommandsDispatcher getInstance(){
		
		if(dispatcher == null){
			dispatcher = new CommandsDispatcher();
		}
		
		return dispatcher;
	}
	public static String getJson(Object o) {
		return new GsonBuilder().setPrettyPrinting().create().toJson(o);
	}
	public String elaborateCommands(String command){
		
		if(command == null) return null;
		if(command.equals(Commands.DATA.getCommand()))
		{
			return getJson("iam you");
		}
		if(command.equals(Commands.SEND.getCommand()))
		{
			return getJson("iam you");
		}
		//if(command.equals(Commands.GET_BLOCKCHAIN.getCommand())){return StringUtil.getJson(Parameters.blockchain);}
		//if(command.equals(Commands.POST_LAST_MINED_BLOCK.getCommand())){return StringUtil.getJson(ChainUtils.getLastBlock());}
		//if(command.equals(Commands.GET_BLOCK_CHAIN_SIZE.getCommand())){return StringUtil.getJson(Parameters.blockchain.size());}
		//if(command.equals(Commands.PING.getCommand())){return StringUtil.getJson(Commands.PONG.getCommand());}
		
		return Commands.UNKNOWN_COMMAND.getCommand();
	}

}
