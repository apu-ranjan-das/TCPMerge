package com.example.tcpmerge.roomdatabase;

import java.util.List;

public class SocketData {
   public List<String> ipAddress;
   public String message;

    public SocketData(List<String> ipAddress, String message) {
        this.ipAddress = ipAddress;
        this.message = message;
    }
}
