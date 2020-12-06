package ua.edu.chmnu.ki.networks.udp.multicast;

import java.io.IOException;
import java.net.SocketException;
import java.time.LocalDateTime;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static ua.edu.chmnu.ki.networks.common.CmdLineParser.extractValue;
import java.io.*;

public class MultiCastSenderAppLab07 {

    public static void main(String[] args) throws SocketException, IOException {
        ExecutorService service = Executors.newCachedThreadPool();
        String tmp = "";
        try(FileReader reader = new FileReader("adresses.txt"))
        {
            // читаем посимвольно
            int c;
            while((c=reader.read())!=-1){

               tmp+=(char)c;
            }
        }
        catch(IOException ex){

            System.out.println(ex.getMessage());
        }
        String[] group = tmp.split("\n");
        int port = 5559;
        for(int index = 0;index<group.length;index++) {

            for (int i = 0; i < args.length; ++i) {
                String value = extractValue(args[i], "-g:");
                if (value != null) {
                    group[index] = value;
                    continue;
                }

                value = extractValue(args[i], "-p:");
                if (value != null) {
                    port = Integer.parseInt(value);
                }

            }

            int finalIndex = index;
            MultiCastSender sender = new MultiCastSender(group[index], port).setAction(() -> {
                String toSend = "["+group[finalIndex]+"] Message";
                return toSend.getBytes();
            });
            service.submit(sender);
//            try
//            {
//                Thread.sleep(100000000);
//            }
//            catch(InterruptedException ex)
//            {
//                Thread.currentThread().interrupt();
//            }
//
//            sender.setActive(false);
        }
//        service.shutdown();
    }
}
