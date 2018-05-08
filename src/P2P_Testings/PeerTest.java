package P2P_Testings;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.net.SocketException;
import java.net.UnknownHostException;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import P2P.Client;
import P2P.NetInfo;
import P2P.Peer;
import P2P.Server;

public class PeerTest {
	private Peer peer1, peer2;

	@BeforeMethod
	public void setup() throws UnknownHostException {
		peer1 = new Peer(9991);
		peer2 = new Peer(9992);
	}

	@Test
	public void melticast() throws IOException {

		peer1.setbCastSendPort(4444);
		peer1.setbCastRecPort(4444);
		peer2.setbCastSendPort(4444);
		peer2.setbCastRecPort(3333);
		peer1.start();
		Assert.assertEquals(1, peer1.getNetworkPeers().size());
		peer1.setData("peer1 data");
		peer2.start();
		String d = peer1.getData();
		Assert.assertEquals(2, peer1.getNetworkPeers().size());
		Assert.assertEquals(2, peer2.getNetworkPeers().size());
		Assert.assertEquals("peer1 data", peer2.getData());
		peer2.sendLineAll("sa2l");
		Assert.assertEquals("sa2l", peer1.receiveLine());
		
		peer1.close();
		peer2.close();
		Client client= new Client();
		Server server= new Server(1101);
		server.start();
		client.connect("localhost", 1101);
		client.send("Sa2a");
		Assert.assertEquals("Sa2a", server.getData());
		client.close();
		server.close();
//		DatagramSocket socketRec = new DatagramSocket(1111, InetAddress.getByName("localhost")); // DatagramSocket
//		//DatagramSocket socketRec2 = new DatagramSocket(1112, InetAddress.getByName("localhost"));
//		DatagramSocket socketSend = new DatagramSocket(2222, InetAddress.getByName("localhost"));
//		socketSend.setBroadcast(true);
//		byte[] send = new String("hellow").getBytes();
//		DatagramPacket packet = new DatagramPacket(send, send.length, InetAddress.getByName("255.255.255.255"), 1111);
//		socketSend.send(packet);
//		byte[] buf = new byte[1000];
//
//		packet = new DatagramPacket(buf, buf.length);
//		socketRec.receive(packet);
//		String rec = new String(packet.getData(), 0, packet.getData().length);
//
////		packet = new DatagramPacket(buf, buf.length); 
////		// socketRec2.receive(packet);
////		String rec2 = new String(packet.getData(), 0, packet.getData().length);
////		
//		send= rec.getBytes();
//		packet = new DatagramPacket(send, send.length,packet.getAddress(),packet.getPort() ); // socketRec2.receive(packet);
//		socketRec.send(packet);
//		
//		packet = new DatagramPacket(buf, buf.length);
//		socketSend.receive(packet);
//		String rec2 = new String(packet.getData(), 0, packet.getData().length);
//
//		
//		socketRec.close();
//		//socketRec2.close();
//		socketSend.close();
//
//		// MulticastSocket socketRec2= new MulticastSocket(1112);
//		// socketRec2.joinGroup(InetAddress.getByName(NetInfo.groupIP));
//
//		/*MulticastSocket socketRec = new MulticastSocket(1111);
//		socketRec.joinGroup(InetAddress.getByName("239.0.0.0"));
//		DatagramSocket socketSend = new DatagramSocket(2222, InetAddress.getByName("localhost"));
//
//		byte[] send = new String("hellow").getBytes();
//		DatagramPacket packet = new DatagramPacket(send, send.length, InetAddress.getByName("239.0.0.0"), 1111);
//		socketSend.send(packet);
//
//		byte[] buf = new byte[1000];
//
//		packet = new DatagramPacket(buf, buf.length);
//		socketRec.setSoTimeout(500);
//		socketRec.receive(packet);
//
//		String rec = new String(packet.getData(), 0, packet.getData().length);
//
//		packet = new DatagramPacket(buf, buf.length);
//		// socketRec2.receive(packet);
//		String rec2 = new String(packet.getData(), 0, packet.getData().length);
//
//		socketRec.close();
//		socketRec.leaveGroup(InetAddress.getByName(NetInfo.groupIP));
//		/// socketRec2.close();
//		socketSend.close();*/

	}
}
