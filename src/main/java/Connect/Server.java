/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Connect;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import GUI.gui;
import java.io.DataInputStream;
import java.io.DataOutputStream;
/**
 *
 * @author rgo19
 */
public class Server extends gui implements Runnable{
    private String command, cmd;
    private ServerSocket ss;
    private Socket sckt;
    public Server() throws IOException{
        ss = new ServerSocket(9999);
        sckt = new Socket();
    }
    
    @Override
    public void run(){
        while(true){
            sckt = ss.accept();
            boolean band = true;
            try {
                DataInputStream dis = new DataInputStream(sckt.getInputStream());
                DataOutputStream dos = new DataOutputStream(sckt.getOutputStream());
                while (band) {
                    if(dis.available()>0){
                        command = dis.readUTF();
                        if(command.equals("$end")){
                            chat.appendText("Client is closing session, entering in hardreset");
                            this.hardReset();
                            band = false;
                        }
                        if(command.startsWith("$attack")){
                            String arr[] = command.split(" ");
                            if(arr.length == 3){
                                
                            }
                        }
                    }
                    if(cmd.length() > 0 && !cmd.matches("^\\s+$")){
                      
                        dos.writeUTF(cmd);
                        if(cmd.equals("$end")){
                            this.chat.appendText("Server is closing");
                            Thread.sleep(1000);
                            System.exit(0);
                        }
                        cmd = "";
                    }
                }
                dis.close();
                dos.close();
            } catch (Exception e) {

            }
            
        }
    }
}
