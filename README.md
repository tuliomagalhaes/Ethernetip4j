This project was created by: daniel.biloglav, sewalliniusm and maja.arvehammar <br />
Google Code Repository: https://code.google.com/p/ethernetip4j/

<h2>Introduction</h2>
This page will show you how to use EthernetIP4J library.

<h2>Details</h2>

Sample code. There is both the SimpleLogixCommunicator and some unit tests to use.

To use the SimpleLogixCommunicator it is intended to connect to one Ethernet card in Control Logix rack or to a Compact Logix unit.

When using the Control Logix you need to keep track of where your Ethernet card is located and where your CPU is located. If it is like CPU slot 0 and ECard slot 1 the you just go ahead and do read and writes with tag names. If the Ecard is at slot 1 then just use the read where cpuSlot is given for example slot 2 then do read(tags,2); and you will get a reply from the CPU.

There are samples where we use this as a means to communicate with several CPU:s in one rack. We have a customer that requires this.

If your Ecard is a another slot than 1 use the function where both ethernetSlot and cpuSlot are given. 
