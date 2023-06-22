package com.example;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class Server extends Thread {

    private int clientNbr;
    private List<communication> allClients = new ArrayList<communication>();
    public static void main (String[] args) {
        new Server().start();
    }

    @Override
    public void run() {
        try {
            ServerSocket ss = new ServerSocket(1234);

            System.out.println("Server online...");

            while (true) {
                Socket s = ss.accept(); // accepet client
                ++clientNbr;
                communication newCom = new communication(s, clientNbr);
                allClients.add(newCom);
                newCom.start();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public class communication extends Thread {
        private Socket s;
        private int cn;

        communication (Socket s, int cn) {
            this.s = s;
            this.cn = cn;
        }
        
        @Override
        public void run() {
            try{
                InputStream is = s. getInputStream(); // bit
                InputStreamReader isr = new InputStreamReader(is); // caracter
                BufferedReader br = new BufferedReader(isr); // chaine of caracteres
                OutputStream os = s.getOutputStream(); //
                String ip = s.getRemoteSocketAddress().toString();
                System.out.println("Client : "+cn+" Address : "+ip);
                PrintWriter pw = new PrintWriter(os,true); //
                pw.println("Hi Client ["+cn+"] ;)");
                pw.println("**------------------------------------**");

                while (true) {
                    String userInput = br.readLine();
                    if (userInput.contains("->")) {
                        String[] msg = userInput.split("->");
                        if (msg.length == 2) {
                            int cNbr = Integer.parseInt(msg[0]);
                            String ms = "(private)"+msg[1];
                            Send (ms, s, cNbr);
                        }
                    }
                    else {
                        Send (userInput, s, -1);
                    }
                }

            } catch (Exception e) {
                e.printStackTrace();
            }   
        }
        void Send (String userInput, Socket suka, int cNbr) throws IOException {
            for (communication client : allClients) {
                if (client.s != suka) {
                    if (client.cn == cNbr || cNbr==-1) {
                        PrintWriter pw = new PrintWriter(client.s.getOutputStream(), true);
                        pw.println("From client["+this.cn+"]: "+userInput);
                    }
                }
            }
            PrintWriter pwme = new PrintWriter(suka.getOutputStream(), true);
            pwme.println("sent");
        }
    }
}
