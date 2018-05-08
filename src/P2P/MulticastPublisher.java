package P2P;

import java.io.IOException;
import java.net.*;
import java.util.ArrayList;

public class MulticastPublisher {
	//private MulticastSocket socket;
	private DatagramSocket socket;
	private DatagramPacket packet;
	// private NetInfo GROUP= new NetInfo();
	private InetAddress groupIP;
	private int groupPort,myPort;
	private byte[] sendBuf;
	private byte[] recBuf;
	private String data;
	private ArrayList<PeerSocket> networkPeers;
	private int sendCounter = 0;

	public MulticastPublisher(String myIP, int myPort,String groupIP,int groupPort) {
		try {
			this.groupPort = groupPort;
			this.myPort= myPort;
			this.groupIP = InetAddress.getByName(groupIP);
			socket= new DatagramSocket(this.myPort,InetAddress.getByName(myIP));
			//socket = new MulticastSocket(groupPort);
			//socket.joinGroup(this.groupIP);

			networkPeers = new ArrayList<>();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void multicast(String message) {
		// String received = "";

		try {
			// send requist
			sendBuf = message.getBytes();
			packet = new DatagramPacket(sendBuf, sendBuf.length, groupIP, groupPort);
			socket.send(packet);
			System.out.println("socket.getInetAddress()= "+
			socket.getInetAddress()+" ,getLocalAddress()= "+socket.getLocalAddress()+", "+socket.getLocalPort());
			System.out.println(packet.getAddress()+", "+packet.getPort());
			
			// recaive Data
			recBuf = new byte[10000];
			packet = new DatagramPacket(recBuf, recBuf.length);
			socket.setSoTimeout(1000);
			socket.receive(packet);
			String recData = new String(packet.getData(), 0, packet.getLength());

			if (!recData.equals(message)) {
				data = recData;

				// recaive peers list
				recBuf = new byte[10000];
				packet = new DatagramPacket(recBuf, recBuf.length);
				socket.receive(packet);
				String[] peer = new String(packet.getData(), 0, packet.getLength()).split("/");

				for (int i = 0; i < peer.length; i += 2) {
					networkPeers.add(new PeerSocket(peer[i], Integer.parseInt(peer[i + 1])));
				}
			}
		} catch (SocketTimeoutException e) {
			if (++sendCounter < 2)
				multicast(message);
			else
				System.out.println("receiver timeout!!");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		sendCounter = 0;
	}

	public void close() {
		/*try {
			socket.leaveGroup(groupIP);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
		socket.close();
	}

	public InetAddress getGroupIP() {
		return groupIP;
	}

	public void setGroupIP(InetAddress groupIP) {
		this.groupIP = groupIP;
	}

	public int getGroupPort() {
		return groupPort;
	}

	public void setGroupPort(int groupPort) {
		this.groupPort = groupPort;
	}
	public int getMyPort() {
		return myPort;
	}

	public void setMyPort(int myPort) {
		this.myPort = myPort;
	}

	/**
	 * @return the data
	 */
	public String getData() {
		return data;
	}

	/**
	 * @return the networkPeers
	 */
	public void setNetworkPeers(ArrayList<PeerSocket> networkPeers) {
		this.networkPeers = networkPeers;
	}

	public ArrayList<PeerSocket> getNetworkPeers() {
		return networkPeers;
	}

}
