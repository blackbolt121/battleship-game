/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Connect;
import GUI.Board;
import GUI.gui;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.BorderFactory;
import javax.swing.JOptionPane;
/**
 *
 * @author rgo19
 */
public class Client extends gui implements Runnable{
    private Socket skt;
    boolean stop;
    public Client() {
        super();
        stop = false;
        Thread t = new Thread(this);
        t.run();
    }
    
    @Override
    public void run() {
        while (skt == null) {
            try {
                skt = new Socket("127.0.0.1", 9999);
                chat.appendText("<---- (system): connection established with server ---->");
            } catch (Exception e) {
                try {
                    skt = null;
                    Thread.sleep(1000);

                } catch (Exception ex) {

                }
            }
        }
        try {
            DataInputStream in = new DataInputStream(skt.getInputStream());
            DataOutputStream out = new DataOutputStream(skt.getOutputStream());
            startService();
            while(!stop){
                if(in.available() > 0){
                    this.setCommand(in.readUTF());
                    if(this.command.startsWith("$message ")){
                        String r[] = this.command.split(" ");
                        String exit = "";
                        for(int i = 1; i<r.length; i++){
                            exit += r[i] + " ";
                        }
                        chat.appendText(exit);
                    }
                    if (command.startsWith("$attack")) {
                            String arr[] = command.split(" ");
                            if(arr.length == 3){
                                Integer i = Integer.valueOf(arr[1]), e = Integer.valueOf(arr[2]);
                                chat.appendText(String.format("Enemy shot over [%d,%d] and %s",i,e,(this.board.strToAttackCoords(i,e))));
                                if(this.board.attackCoords(i, e)){
                                    this.board.paintCheckBoard(i, e, Board.hit, ((this.board.getCheckBoard(i, e).isEnabled())?true:false));
                                    this.board.getCheckBoard(i, e).setForeground(Board.ship);
                                    this.board.getCheckBoard(i, e).setBorder(BorderFactory.createLineBorder(Board.ship));
                                    out.writeUTF(String.format("$success %d %d",i,e));
                                    out.writeUTF(String.format("$success %d %d",i,e));
                                    if(this.board.getBoatByHit(i, e).isSunked()){
                                        out.writeUTF("$message (" + this.board.getPlayerGame() + "): You have sunken my " + this.board.getBoatByHit(i, e).getShip().toString());
                                    }
                                }else{
                                    out.writeUTF(String.format("$fail %d %d",i,e));
                                }
                                
                            }
                    }
                    if(this.getCommand().equals("$end")){
                        JOptionPane.showMessageDialog(null,"(System): Host ended the game, :c ");
                        stopService();
                    }
                    if(command.equals("$ingame")){
                            otherInGame = true;
                            chat.appendText("(System): Enemy is ready to play");
                    }
                    if (command.startsWith("$fail ")) {
                        String r[] = this.command.split(" ");
                        Integer a = Integer.valueOf(r[1]), b = Integer.valueOf(r[2]);
                        if (!this.board.coordsInBoat(a, b)) {
                            this.board.paintCheckBoard(a, b, Board.tried, false);
                        } else {
                            this.board.paintBorderCheckBoard(a, b, Board.tried, false);
                        }
                        chat.appendText(String.format("(System): Shot over [%d,%d] failed", a, b));
                    } else if (command.startsWith("$success ")) {
                        String r[] = this.command.split(" ");
                        Integer a = Integer.valueOf(r[1]), b = Integer.valueOf(r[2]);
                        if (!this.board.coordsInBoat(a, b)) {
                            this.board.paintCheckBoard(a, b, Board.hit, false);
                        } else {
                            this.board.paintBorderCheckBoard(a, b, Board.hit, false);
                        }
                        chat.appendText(String.format("(System): Shot over [%d,%d] succeed", a, b));
                    }
                    //Se le la instruccion
                    this.setCommand("");
                }
                if(this.getCmd().length() > 0 && !(this.getCmd().matches("^\\s+$"))){
                    out.writeUTF(this.getCmd());
                    this.setCmd("");
                }
            }
            in.close();
            out.close();
        } catch (IOException ex) {
            Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
        }
        System.exit(0);
    }
    public void startService(){
        stop = false;
    }
    public void stopService(){
        stop = true;
    }
    
}
