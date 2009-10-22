package se.opendataexchange.ethernetip4j.junit;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

/***
 * These tests should be run together with an Allen Bradley L35E with IP 192.168.200.51 running RSLogix 5000 project P5.ACD.
 */
@RunWith(Suite.class)
@Suite.SuiteClasses({
  EthernetIpBufferUtilTest.class,
  UnconnectedMessageManagerRequestTest.class,
  EthernetIpRegisterSessionRequestTest.class,
  SimpleControlLogixCommunicatorTest.class,
  UnconnectedMessagingTest.class
})

public class AllTests {
	
}
