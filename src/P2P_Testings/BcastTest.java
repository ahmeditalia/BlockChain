package P2P_Testings;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import P2P.BcastReceiver;
import P2P.BcastSender;
import org.testng.*;

@Test
public class BcastTest {
	BcastSender client;
//
//	@BeforeMethod
//	public void start() {
//		new BcastReceiver().start();
//		client = new BcastSender();
//	}
//
//	public void Bcast() {
//		String echo = client.sendEcho("hello Sa2a");
//		Assert.assertEquals("hello Sa2a", echo);
//		echo = client.sendEcho("Sa2a is working");
//		Assert.assertFalse(echo.equals("hello Sa2a"));
//	}
//
//	@AfterMethod
//	public void end() {
//		String echo =client.sendEcho("end");
//		Assert.assertEquals("end", echo);
//		client.close();
//	}
}
