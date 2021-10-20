/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Connect;
import GUI.Board;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import GUI.gui;
import java.awt.Color;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import javax.swing.BorderFactory;
/**
 *
 * @author rgo19
 */
public class Server extends gui implements Runnable{
    private ServerSocket ss;
    private Socket sckt;
    public Server() {
        super();
        this.setTitle("Server");
        this.board.setPlayerGame("Server");
        try{
            ss = new ServerSocket(9999);
            sckt = new Socket();
            Thread t = new Thread(this);
            t.run();
        }catch(IOException e){
            
        }
        this.chat.getSendMessageButton().addActionListener(l -> {
            chat.appendText(chat.getSendMessageButton().getText());
            
        });
    }
    
    @Override
    public void run(){
        while(true){
            try {
                sckt = ss.accept();
                chat.appendText("<---- (system): connection established with client ---->");
                boolean band = true;
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
                                Integer i = Integer.valueOf(arr[1]), e = Integer.valueOf(arr[2]);
                                chat.appendText(String.format("Enemy shot over [%d,%d] and %s",i,e,(this.board.strToAttackCoords(i,e))));
                                if(this.board.attackCoords(i, e)){
                                    this.board.paintCheckBoard(i, e, Board.hit, ((this.board.getCheckBoard(i, e).isEnabled())?true:false));
                                    this.board.getCheckBoard(i, e).setForeground(Board.ship);
                                    this.board.getCheckBoard(i, e).setBorder(BorderFactory.createLineBorder(Board.ship));
                                    dos.writeUTF(String.format("$success %d %d",i,e));
                                    if(this.board.getBoatByHit(i, e).isSunked()){
                                        dos.writeUTF("$message (" + this.board.getPlayerGame() + "): You have sunken my " + this.board.getBoatByHit(i, e).getShip().toString());
                                    }
                                }else{
                                    dos.writeUTF(String.format("$fail %d %d",i,e));
                                }
                                
                            }
                        }
                        if(command.startsWith("$message")){
                            String r[] = this.command.split(" ");
                            String exit = "";
                            for (int i = 1; i < r.length; i++) {
                                exit += r[i] + " ";
                            }
                            chat.appendText(exit);
                        }
                        if(command.equals("$ingame")){
                            otherInGame = true;
                            chat.appendText("(System): Enemy is ready to play");
                        }
                        
                        if(command.startsWith("$fail ")){
                            String r[] = this.command.split(" ");
                            Integer a = Integer.valueOf(r[1]), b = Integer.valueOf(r[2]);
                            if(!this.board.coordsInBoat(a, b)){
                                this.board.paintCheckBoard(a, b, Board.tried, false);
                            }else{
                                this.board.paintBorderCheckBoard(a, b, Board.tried, false);
                            }
                            chat.appendText(String.format("(System): Shot over [%d,%d] failed",a,b));
                            
                        }
                        if(command.startsWith("$success ")){
                            String r[] = this.command.split(" ");
                            Integer a = Integer.valueOf(r[1]), b = Integer.valueOf(r[2]);
                            
                            if(this.board.coordsInBoat(a, b) == false){
                                this.board.paintCheckBoard(a, b, Board.hit, false);
                            }else{
                                this.board.paintBorderCheckBoard(a, b, Board.hit, false);
                            }
                            chat.appendText(String.format("(System): Shot over [%d,%d] succeed",a,b));
                        }
                        this.setCommand("");
                    }
                    if(this.getCmd().length() > 0 && !this.getCmd().matches("^\\s+$")){
                        dos.writeUTF(cmd);
                        if(cmd.equals("$end")){
                            this.chat.appendText("Server is closing");
                            Thread.sleep(1000);
                            band = false;
                            System.exit(0);
                        }
                        cmd = "";
                    }
                } 
            } catch (Exception e) {
                e.printStackTrace();
            }
            
        }
    }
   
    
}
