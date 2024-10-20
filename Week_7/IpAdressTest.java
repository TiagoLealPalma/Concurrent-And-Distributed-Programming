package Week_7;

import java.net.InetAddress;
import java.net.UnknownHostException;

public class IpAdressTest {

    public static void main(String[] args) throws UnknownHostException {
        InetAddress addr = InetAddress.getByName(null);
        InetAddress addr1 = InetAddress.getByName("publico.pt");

        System.out.println(addr.getHostAddress());
        System.out.println(addr1.getHostAddress());
    }
}
