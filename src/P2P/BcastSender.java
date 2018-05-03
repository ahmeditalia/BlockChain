package P2P;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;

public class BcastSender {
	private DatagramSocket socket;
	private InetAddress address;

	private byte[] buf;

	public BcastSender() {
		try {
			socket = new DatagramSocket();
			address = InetAddress.getByName("localhost");

		} catch (SocketException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public String sendEcho(String msg) {
		String received = "";
		try {
			buf = msg.getBytes();

			DatagramPacket packet = new DatagramPacket(buf, buf.length, address, 4445);

			socket.send(packet);

			packet = new DatagramPacket(buf, buf.length);
			socket.receive(packet);
			received = new String(packet.getData(), 0, packet.getLength());
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return received;

	}

	public void close() {
		socket.close();
	}

}
