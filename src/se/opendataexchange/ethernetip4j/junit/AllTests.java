package se.opendataexchange.ethernetip4j.junit;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

/***
 * These tests have been run together with an Allen Bradley L35E with IP 192.168.200.51 running RSLogix 5000.
 * 
 * To run the tests, one must read them and define the correct controller tags in the controller, since reading and writing is tested.
 */
@RunWith(Suite.class)
@Suite.SuiteClasses({
  EthernetIpBufferUtilTest.class,
  UnconnectedMessageManagerRequestTest.class,
  EthernetIpRegisterSessionRequestTest.class,
  SimpleControlLogixCommunicatorTest.class,
  UnconnectedMessagingTest.class,
  ArrayTest.class
})

public class AllTests {
	
}
