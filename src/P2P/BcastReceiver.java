package P2P;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;

public class BcastReceiver extends Thread {

	private DatagramSocket socket;
	private boolean running;
	private byte[] buf = new byte[256];

	public BcastReceiver()  {
		try {
			socket = new DatagramSocket(4445);
		} catch (SocketException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void run() {

		while (true) {
			try {

				DatagramPacket packet = new DatagramPacket(buf, buf.length);
				
				socket.receive(packet);
				String received = new String(packet.getData(), 0, packet.getLength());
				
				InetAddress address = packet.getAddress();
				int port = packet.getPort();
				packet = new DatagramPacket(buf, buf.length, address, port);
				
				socket.send(packet);
				if (received.equals("end")) {
					break;
				}
				
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
		socket.close();
	}
}