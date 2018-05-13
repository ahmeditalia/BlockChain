package P2P;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;

public class Peer {
	private PeerSocket peerSocket;
	private String data;
	private ArrayList<PeerSocket> networkPeers;
	// private MulticastPublisher multicastPublisher;
	// private MulticastReceiver multicastReceiver;
	private Client client;
	private Server server;
	private BcastReceiver bCastReceiver;
	private int bCastRecPort;
	private int bCastSendPort;
	private static int bCasstPort=1111;
	public int getbCastRecPort() {
		return bCastRecPort;
	}

	public void setbCastRecPort(int bCastRecPort) {
		this.bCastRecPort = bCastRecPort;
	}

	public int getbCastSendPort() {
		return bCastSendPort;
	}

	public void setbCastSendPort(int bCastSendPort) {
		this.bCastSendPort = bCastSendPort;
	}

	public Peer() throws UnknownHostException {
		peerSocket = new PeerSocket(InetAddress.getLocalHost().getHostAddress(), NetInfo.sendPort);
		networkPeers = new ArrayList<>();
		networkPeers.add(peerSocket);
		// multicastPublisher= new MulticastPublisher();
		// multicastReceiver =new
		// MulticastReceiver(peerSocket.getIP(),peerSocket.getPort());
		// multicastReceiver =new
		// MulticastReceiver(NetInfo.groupIP,NetInfo.receivePort);

	}

	public Peer(int port) throws UnknownHostException {
		peerSocket = new PeerSocket(InetAddress.getLocalHost().getHostAddress(), port);
		networkPeers = new ArrayList<>();
		networkPeers.add(peerSocket);
		// multicastPublisher= new MulticastPublisher();

		// multicastReceiver =new
		// MulticastReceiver(peerSocket.getIP(),peerSocket.getPort());
		// multicastReceiver =new
		// MulticastReceiver(NetInfo.groupIP,NetInfo.receivePort);
		// client = new Client();
		// server = new Server();

	}

	public Peer(String ip, int port) throws UnknownHostException {
		peerSocket = new PeerSocket(ip, port);
		networkPeers = new ArrayList<>();
		networkPeers.add(peerSocket);
		// multicastPublisher= new MulticastPublisher();
		// multicastReceiver =new
		// MulticastReceiver(peerSocket.getIP(),peerSocket.getPort());
		// multicastReceiver =new
		// MulticastReceiver(NetInfo.groupIP,NetInfo.receivePort);
		// client = new Client();
		// server = new Server();
	}

	public void start() {
		//
		// MulticastPublisher multicastPublisher = new MulticastPublisher
		// ("localhost", peerSocket.getPort(),NetInfo.groupIP,NetInfo.receivePort);
		// multicastPublisher.setNetworkPeers(networkPeers);
		// multicastPublisher.multicast("hi");
		// data = multicastPublisher.getData();
		// multicastPublisher.close();
		// multicastReceiver = new MulticastReceiver(NetInfo.groupIP,
		// NetInfo.receivePort);
		// multicastReceiver.setData(data);
		// multicastReceiver.setNetworkPeers(networkPeers);
		// multicastReceiver.start();

		BcastSender bCastSender = new BcastSender(NetInfo.sendPort, bCastSendPort);
		bCastSender.setNetworkPeers(networkPeers);
		bCastSender.bCast("hi"+"/"+peerSocket.getPort());
		data = bCastSender.getData();
		bCastSender.close();
		bCastReceiver = new BcastReceiver(bCastRecPort);
		bCastReceiver.setData(data);
		bCastReceiver.setNetworkPeers(networkPeers);
		bCastReceiver.start();
		client = new Client();
		server = new Server(peerSocket.getPort());
		server.start();

	}

	public String receiveLine() {
		data= server.getData();
		return data;
	}

	public String receiveBlock() {
		data= server.getData();
		return data;
	}

	public void sendLineAll(String data) throws IOException {
		for (int i = 1; i < networkPeers.size(); i++) {
			client.connect(networkPeers.get(i).getIP(),networkPeers.get(i).getPort());
			client.send('l'+data);
		}
		client.close();
	}

	public void sendBlockAll(String block) throws IOException {
		for (int i = 1; i < networkPeers.size(); i++) {
			client.connect(networkPeers.get(i).getIP(),networkPeers.get(i).getPort());
			client.send('b'+block);
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
		bCastReceiver.setData(data);
		this.data = data;
	}

	public ArrayList<PeerSocket> getNetworkPeers() {
		return networkPeers;
	}

	public void setNetworkPeers(ArrayList<PeerSocket> networkPeers) {
		this.networkPeers = networkPeers;
	}

	public void close() {
		client.close();
		server.close();
		bCastReceiver.close();
	}
}
