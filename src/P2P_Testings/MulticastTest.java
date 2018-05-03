package P2P_Testings;

import java.io.IOException;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import P2P.MulticastPublisher;
import P2P.MulticastReceiver;
import org.testng.*;

public class MulticastTest {

	private MulticastPublisher publisher;
	private MulticastReceiver receiver;

	@BeforeMethod
	private void start() {
		
		receiver = new MulticastReceiver();
		receiver.start();
		publisher = new MulticastPublisher();
	}

	@Test
	public void multicast() throws IOException {

		publisher.multicast("hellow Sa2a");
		publisher.multicast("end");
		String received = receiver.getReceived();
		Assert.assertEquals("hellow Sa2a/end/", received);
	}
}
