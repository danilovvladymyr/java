import java.util.*;

public class IPv4Address {
    private String stringIP = null;
    private long longIP = 0;
    private boolean isString = false;
    
    public IPv4Address(String address) throws IllegalArgumentException {
        // validate(address);
        this.stringIP = address;
        isString = true;
    }
    
    public IPv4Address(long address) throws IllegalArgumentException {
        // validate(address);
        this.longIP = address;
        // System.out.println("Constructor has worked: " + longIP);
    }
    
    public boolean lessThan(IPv4Address address) {
        return this.toLong() < address.toLong();
    }
    
    public boolean greatThan(IPv4Address address) {
        return this.toLong() < address.toLong();
    }
    
    public boolean equals(IPv4Address address) {
        return this.toLong() == address.toLong();
    }
    
    public String toString() {
        if ( !isString ) {
            StringBuffer result = new StringBuffer();
            
            for ( int i = 3; i >= 0; i-- ) {
                int shift = i * 8;
                
                result.append((longIP >> shift) & 0xff);
                if ( i > 0 ) {
                    result.append(".");
                }
            }
            return result.toString();
        } else {
            return stringIP;
        }
    }
    
    public long toLong() {
        if ( isString ) {
            long result = 0;
            String[] octets = stringIP.split("\\.");
            
            for ( int i = 0; i < octets.length; i++ ) {
                int power = 3 - i;
                
                result += Integer.parseInt(octets[i]) * Math.pow(256, power);
            }
            return result;
        } else {
            return longIP;
        }
    }
    
    // public void validate(String address) {
    //     String[] octets = address.split("//.");
        
    //     if ( octets.length != 4 ) {
    //         throw new IllegalArgumentException();
    //     }
    //     for ( int i = 0; i < octets.length; i++ ) {
    //         int ip = Integer.parseInt(octets[i]);
            
    //         if ( ip > 255 || ip < 0 ) {
    //             throw new IllegalArgumentException();
    //         }
    //     }
    // }
    
    // public void validate(long address) {
    //     if ( address > 4294967295l || address < 0 ) {
    //         throw new IllegalArgumentException();
    //     }
    // }
    
    public static void main(String[] args) {
        IPv4Address ip1 = new IPv4Address("192.168.0.1");
        IPv4Address ip2 = new IPv4Address(3234673832l);
        
        System.out.println(ip1.toString());
        System.out.println(ip1.toLong());
        System.out.println(ip2.toString());
        System.out.println(ip2.toLong());
        
        System.out.println(ip1.lessThan(new IPv4Address("182.0.0.1")));
    }
}
