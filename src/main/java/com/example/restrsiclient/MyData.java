package com.example.restrsiclient;

import java.net.DatagramSocket;
import java.net.InetAddress;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class MyData {
    public static void myInfo() {
        System.out.printf("%s\n%s\n", "Joanna Mazurkiewicz 259134", "Maciej Olejnik 260444");
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd MMMM, hh:mm:ss");
        LocalDateTime now = LocalDateTime.now();
        System.out.println(dtf.format(now));
        System.out.println(System.getProperty("java.version"));
        System.out.println(System.getProperty("user.name"));
        System.out.println(System.getProperty("os.name"));

        try(final DatagramSocket socket = new DatagramSocket()){
            socket.connect(InetAddress.getByName("8.8.8.8"), 10002);
            System.out.println(socket.getLocalAddress().getHostAddress());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
