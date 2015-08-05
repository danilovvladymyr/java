import java.util.*;

public class Network {
    private IPv4Address address = null;
    private int maskLength = 0;
    
    public Network(IPv4Address address, int maskLength) {
        if ( maskLength >= 0 && maskLength <= 32 ) {
            this.maskLength = maskLength;
        } else {
            throw new IllegalArgumentException();
        }
        this.address = new IPv4Address(address.toLong() & getMask());
    }
    
    public boolean contains(IPv4Address address) {
        return address.greatThan(this.address) && address.lessThan(this.getBroadcastAddress());
    }
    
    public IPv4Address getAddress() {
        return address;
    }
    
    public IPv4Address getBroadcastAddress() {
        long fullMask = (long)Math.pow(2,32) - 1;
        
        return (new IPv4Address(address.toLong() | (fullMask >> maskLength)));
    }
    
    public IPv4Address getFirstUsableAddress() {
        if ( maskLength < 31 ) {
            return (new IPv4Address(address.toLong() + 1));
        } else {
            return (new IPv4Address(address.toLong()));
        }
    }
    
    public IPv4Address getLastUsableAddress() {
        if ( maskLength < 31 ) {
            return (new IPv4Address(getBroadcastAddress().toLong() - 1));
        } else {
            return (new IPv4Address(getBroadcastAddress().toLong()));
        }
    }
    
    public long getMask() {
        int shift = 32 - maskLength;
        long fullMask = (long)Math.pow(2,32) - 1;
        
        return (fullMask >> shift) << shift;
    }
    
    public String getMaskString() {
        long mask = getMask();
        
        StringBuffer result = new StringBuffer();
            
            for ( int i = 3; i >= 0; i-- ) {
                int shift = i * 8;
                
                result.append((mask >> shift) & 0xff);
                if ( i > 0 ) {
                    result.append(".");
                }
            }
            return result.toString();
    }
    
    public int getMaskLength() {
        return maskLength;
    }
    
    public Network[] getSubnets() {
        int newMaskLength = maskLength + 1;
        Network[] subnets = new Network[2];
        long newNetAddress = address.toLong() + ((((long)Math.pow(2,32) << newMaskLength) << maskLength) >> maskLength);
        IPv4Address secondNetAddress = new IPv4Address(newNetAddress);
        
        subnets[0] = new Network(address, newMaskLength);
        subnets[1] = new Network(secondNetAddress, newMaskLength);
        
        return subnets;
    }
    
    public long getTotalHosts() {
        // return getLastUsableAddress().toLong() - getFirstUsableAddress().toLong();
        
        long totalHosts = 0;
        
        if ( maskLength < 31 ) {
            totalHosts = ((long)Math.pow(2,(32 - maskLength)) - 2);
        } else if ( maskLength == 31 ) {
            totalHosts = 2;
        } else if ( maskLength == 32 ) {
            totalHosts = 1;
        }
        return totalHosts;
    }
    
    public boolean isPublic() {
        return !(address.equals(new IPv4Address("10.0.0.0")) || address.equals(new IPv4Address("172.16.0.0")) || address.equals(new IPv4Address("192.168.0.0")));
    }
    
    public String toString() {
        return getAddress().toString() + "/" + getMaskLength();
    }
    
    public static void main(String[] args) {
        IPv4Address address1 = new IPv4Address("192.168.0.0");
        
        Network net = new Network(address1, 24);

        System.out.println(net.toString());
        // 192.168.0.0/24
        System.out.println(net.getAddress().toString());
        // 192.168.0.0
        System.out.println(net.getFirstUsableAddress().toString());
        // 192.168.0.1
        System.out.println(net.getLastUsableAddress().toString());
        // 192.168.0.254
        System.out.println(net.getMaskString());
        // 255.255.255.0
        System.out.println(net.getMaskLength());
        // 24
        System.out.println(net.isPublic());
        // false
        System.out.println(net.contains(new IPv4Address("10.0.23.4")));
        // false
        System.out.println(net.contains(new IPv4Address("192.168.0.25")));
        // true
        System.out.println(net.getBroadcastAddress().toString());
        // 192.168.0.255


        Network[] subnets = net.getSubnets();

        System.out.println(subnets[0].toString());
        // 192.168.0.0/25
        System.out.println(subnets[0].getAddress().toString());
        // 192.168.0.0
        System.out.println(subnets[0].getFirstUsableAddress().toString());
        // 192.168.0.1
        System.out.println(subnets[0].getLastUsableAddress().toString());
        // 192.168.0.126
        System.out.println(subnets[0].getMaskLength());
        // 25
        
        
        // System.out.println(net.getTotalHosts());
    }
}
