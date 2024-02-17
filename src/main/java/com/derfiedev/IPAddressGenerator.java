package com.derfiedev;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Random;

public class IPAddressGenerator {
    public static String generateRandomIPAddress() {
        Random random = new Random();

        while (true) {
            // Generate a random IPv4 address
            String ipAddress = random.nextInt(256) + "." + random.nextInt(256) + "." + random.nextInt(256) + "."
                    + random.nextInt(256);

            try {
                InetAddress inetAddress = InetAddress.getByName(ipAddress);

                // Check if the generated IP is not reserved, multicast, loopback, or local
                if (!inetAddress.isAnyLocalAddress() && !inetAddress.isLinkLocalAddress() &&
                        !inetAddress.isLoopbackAddress() && !inetAddress.isMulticastAddress() &&
                        !inetAddress.isSiteLocalAddress() && !inetAddress.isMCGlobal() &&
                        !inetAddress.isMCLinkLocal() && !inetAddress.isMCNodeLocal() &&
                        !inetAddress.isMCOrgLocal() && !inetAddress.isMCSiteLocal()) {
                    return ipAddress;
                }
            } catch (UnknownHostException e) {
                e.printStackTrace();
            }
        }
    }
}
