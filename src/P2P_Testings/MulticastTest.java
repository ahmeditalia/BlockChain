package P2P_Testings;

import java.io.IOException;

import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
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

		String ack= publisher.multicast("hellow Sa2a");
		Assert.assertEquals("done", ack);

		ack =publisher.multicast("end");
		Assert.assertEquals("done", ack);
	}
	
	/*@AfterClass
	public void end() {
		receiver.close();
		publisher.close();
	}*/
}
