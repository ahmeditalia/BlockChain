package com.dottorsoft.SimpleBlockChain.networking;


public class ExecuteCommands {
	
	private Peer2Peer networking;
	
	public ExecuteCommands(){}
	
	public ExecuteCommands(int port){
		networking = new Peer2Peer(port);
	}
	
	/**
	 * Used for client mode
	 */
	public void connect(String host, int port){
		networking.connect(host, port);
	}
	
	public String getData(){
		networking.send(Commands.DATA.getCommand());
		return networking.receive();
	}
	public String SendAll(String message){
		networking.send(message);
		return networking.receive();
	}
	
	public String ping(Peer p){
		connect(p.getIp(), p.getServerPort());
		networking.send(Commands.PING.getCommand());
		return networking.receive();
	}
	
	public void pingAll(){
		for(Peer p : networking.getPeers()){
			System.out.println(ping(p));
		}
	}
	
	public String register(){
		networking.send(Commands.REGISTERING.getCommand());
		return networking.receive();
	}
	
	public String receive(){
		return networking.receive();
	}
	public void Close()
	{
		networking.close();
	}

}
