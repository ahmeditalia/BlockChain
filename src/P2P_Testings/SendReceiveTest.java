package P2P_Testings;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import P2P.Client;
import P2P.Server;

public class SendReceiveTest {
	private Client client;
	private Server server;

	@BeforeMethod
	public void startup() throws UnknownHostException {
		server = new Server();
		server.start();
		client = new Client();
		client.connect(InetAddress.getLocalHost().getHostAddress());
	}

	@Test
	public void sendReceive() throws IOException {
		/*String respons1= client.send("hellow Sa2a");
		String respons2 = client.send("i am Sa2a");
		String respons3 =client.send(".");
		Assert.assertEquals(respons1, "hellow Sa2a");
		Assert.assertEquals(respons2, "i am Sa2a");
		Assert.assertEquals(respons3, "end");*/
	}

	/*@AfterMethod
	public void terminatio() {
		server.close();
		client.close();
	}*/

}
