/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ua.edu.chmnu.ki.networks.tcp.simple;

import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.*;
/**
 *
 * @author svpuzyrov
 */
public class SimpleMultiClientAppLab05 {
    public static <e> void main(String[] args) throws InterruptedException, IOException {
        String[] testData = {
            "Hello, I'm here!",
        };
        Scanner in = new Scanner(System.in);
        System.out.print("Input a host: ");
        String host = in.next();
        System.out.print("Input start port: ");
        int startP = in.nextInt();
        System.out.print("Input end port: ");
        int endP = in.nextInt();
        for(int i = startP;i<endP;i++) {
            int port = i;
            try {
                ExecutorService executor = Executors.newFixedThreadPool(10);
                for (String line : testData) {
                    executor.submit(new SimpleTCPClient(host, port, line));
                }

                executor.shutdown();
                System.out.println("Port "+i+" is opened");
            }catch (Exception e){
                System.out.println("Port "+i+" is closed");
            }
        }
    }
}
