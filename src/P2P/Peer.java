package P2P;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;

public class Peer {
	private PeerSocket peerSocket;
	private String data;
	private ArrayList<PeerSocket> networkPeers;
	private MulticastPublisher multicastPublisher;
	private MulticastReceiver multicastReceiver;
	private Client client;
	private Server server;

	public Peer() throws UnknownHostException {
		peerSocket= new PeerSocket(InetAddress.getLocalHost().getHostAddress(), new NetInfo().sendPort);
		networkPeers =new ArrayList<>();
		networkPeers.add(peerSocket);
		multicastPublisher= new MulticastPublisher();
		multicastReceiver =new MulticastReceiver();
		client= new Client();
		server= new Server();
		
	}
	public Peer(int port) throws UnknownHostException {
		peerSocket= new PeerSocket(InetAddress.getLocalHost().getHostAddress(), port);
		networkPeers =new ArrayList<>();
		networkPeers.add(peerSocket);
		multicastPublisher= new MulticastPublisher();
		multicastReceiver =new MulticastReceiver();
		client= new Client();
		server= new Server();
		
	}
	public Peer(String ip,int port) throws UnknownHostException {
		peerSocket= new PeerSocket(ip,port);
		networkPeers =new ArrayList<>();
		networkPeers.add(peerSocket);
		multicastPublisher= new MulticastPublisher();
		//multicastReceiver =new MulticastReceiver();
		client= new Client();
		server= new Server();
	}



	public void start()
	{
		multicastReceiver =new MulticastReceiver();

		multicastReceiver.start();
		multicastPublisher.multicast("hi");
		networkPeers= multicastPublisher.getNetworkPeers();
		data= multicastPublisher.getData();
		multicastPublisher.close();
	}
	public String receiveLine()
	{
		return server.getData();
	}
	
	public String receiveBlock()
	{
		return server.getData();
	}
	
	public void sendLineAll(String data) throws IOException
	{
		networkPeers= multicastPublisher.getNetworkPeers();
		for(int i=1;i<networkPeers.size();i++)
		{
			client.connect(networkPeers.get(i).getIP());
			client.send(data);
		}
		client.close();
	}
	public void sendBlockAll(String block) throws IOException
	{
		networkPeers= multicastPublisher.getNetworkPeers();
		for(int i=1;i<networkPeers.size();i++)
		{
			client.connect(networkPeers.get(i).getIP());
			client.send(block);
		}
		client.close();

	}
	/**
	 * @return the data
	 */
	public PeerSocket getPeerSocket() {
		return peerSocket;
	}

	public void setPeerSocket(PeerSocket peerSocket) {
		this.peerSocket = peerSocket;
	}
	public String getData() {
		return data;
	}

	/**
	 * @param data
	 *            the data to set
	 */
	public void setData(String data) {
		this.data = data;
	}
	public ArrayList<PeerSocket> getNetworkPeers() {
		return networkPeers;
	}

	public void setNetworkPeers(ArrayList<PeerSocket> networkPeers) {
		this.networkPeers = networkPeers;
	}
	
}
