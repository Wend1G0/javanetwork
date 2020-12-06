/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ua.edu.chmnu.ki.networks.tcp.simple;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author svpuzyrov
 */
public class ServerClientSession implements Runnable{
    private final Socket socket;
    private final ObjectInputStream inS;
    private final ObjectOutputStream outS;
    
    public ServerClientSession(Socket socket) throws IOException {
        this.socket = socket;
        inS = new ObjectInputStream(socket.getInputStream());
        outS = new ObjectOutputStream(socket.getOutputStream());
    }

    public Socket getSocket() {
        return socket;
    }

    @Override
    public void run() {
        String from = String.format("[%s:%d]", 
                                    socket.getInetAddress().toString(),
                                    socket.getPort());
        try(ObjectInputStream in = inS;
            ObjectOutputStream out = outS)
        {
            while (!socket.isClosed())
            {
                Object o = in.readObject();
                System.out.println(from + ">" + o);
                out.writeObject("OK");
            }
        } catch (IOException ex) {
            String what;
            String action = "shutdown";
            if (socket.isInputShutdown())
            {
                what = "Input stream";
                
            }
            else if (socket.isOutputShutdown())
            {
                what = "Output stream";
            } else if (socket.isClosed())
            {
                what = "Connection";
            } else {
                what = "Unknown error";
                action = ex.getMessage();
                Logger.getLogger(ServerClientSession.class.getName()).log(Level.SEVERE, null, ex);
            }            
            System.out.printf("%s from %s was %s\n", what, from, action);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(ServerClientSession.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
