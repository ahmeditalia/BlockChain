package P2P;

import java.net.InetAddress;
import java.net.UnknownHostException;

public final class NetInfo {
	public final String groupIP = "225.0.0.0";
	private InetAddress ip;
	public final byte[] Ack = { 'd', 'o', 'n', 'e' };
	public  final int sendPort = 4444;
	public  int receivePort = 5555;
	public NetInfo() {

		try {
			ip = InetAddress.getByName("225.0.0.0");
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public InetAddress IP() {
		return ip;
	}
}