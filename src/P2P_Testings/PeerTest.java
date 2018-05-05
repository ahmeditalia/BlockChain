package P2P_Testings;

import java.net.UnknownHostException;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import P2P.Peer;

public class PeerTest {
	private Peer peer1,peer2;
	@BeforeMethod
	public void setup() throws UnknownHostException {
		peer1 = new Peer(4444);
		peer2 = new Peer(5555);
	}
	
  @Test
  public void melticast() {
	  peer1.start();
	  Assert.assertEquals(1, peer1.getNetworkPeers().size());
	  peer2.start();
	  Assert.assertEquals(2, peer1.getNetworkPeers().size());
	  Assert.assertEquals(2, peer2.getNetworkPeers().size());

  }
}
