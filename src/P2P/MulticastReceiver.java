package P2P;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;

public class MulticastReceiver extends Thread {
	private MulticastSocket socket = null;
	private byte[] buf = new byte[256];
	private String received = "";

	public String getReceived() {
		return received;
	}

	InetAddress group;

	public MulticastReceiver() {
	}

	public void run() {
		try {
			socket = new MulticastSocket(4446);

			group = InetAddress.getByName("230.0.0.0");
			socket.joinGroup(group);
			while (true) {
				DatagramPacket packet = new DatagramPacket(buf, buf.length);
				socket.receive(packet);
				received += new String(packet.getData(), 0, packet.getLength()) + "/";
				if ("end".equals(received)) {
					break;
				}
			}

			socket.leaveGroup(group);

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		socket.close();
	}
}