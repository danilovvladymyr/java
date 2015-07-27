public class Network {
    public Network(IPv4Address address, int maskLength) {}
    public boolean contains(IPv4Address address) {}
    public IPv4Address getAddress() {}
    public IPv4Address getFirstUsableAddress() {}
    public IPv4Address getLastUsableAddress() {}
    public long getMask() {}
    public String getMaskString() {}
    public int getMaskLength() {}
    public Network[] getSubnets() {}
    public long getTotalHosts() {}
    public boolean isPublic() {}
}
