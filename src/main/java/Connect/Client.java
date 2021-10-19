/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Connect;
import GUI.gui;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;
/**
 *
 * @author rgo19
 */
public class Client extends gui implements Runnable{
    private Socket skt;
    private String outputMsg, inputMsg;
    boolean stop;
    public Client() throws IOException{
        skt = new Socket("127.0.0.1",999);
        outputMsg = "";
        inputMsg = "";
        stop = false;
    }

    @Override
    public void run() {
        try {
            DataInputStream in = new DataInputStream(skt.getInputStream());
            DataOutputStream out = new DataOutputStream(skt.getOutputStream());
            while(!stop){
                if(in.available() > 0){
                    inputMsg = in.readUTF();
                    //Se le la instruccion
                }
                if(outputMsg.length() > 0 && !outputMsg.matches("^\\s+$")){
                    out.writeUTF(outputMsg);
                    outputMsg = "";
                }
            }
        } catch (IOException ex) {
            Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    public void setOutputMessage(String msg){
        this.outputMsg = msg;
    }
    public void setStopService(){
        stop = true;
    }
}
