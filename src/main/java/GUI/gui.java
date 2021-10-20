/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

/**
 *
 * @author rgo19
 */
public class gui extends JFrame{
    protected String command, cmd;
    protected Board board;
    protected ChatBox chat;
    protected Status status;
    protected boolean inGame;
    protected boolean otherInGame;
    private GridBagConstraints gbc;
    private sendHit sh;
    public gui(){
        
        super();
        command = new String();
        cmd = new String();
        inGame = otherInGame = false;
        sh = new sendHit(this);
        gbc = new GridBagConstraints();
        
        this.setLayout(new GridBagLayout());
        this.setSize(750,700);
        this.setResizable(false);
        addBoard();
        addStatus();
        addChat();
        this.setTitle(this.board.getPlayerGame());
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);
        
    }
    private void addBoard(){
        addBoard(JOptionPane.showInputDialog("Digite su nombre", DISPOSE_ON_CLOSE));
    };
    private void addBoard(String name){ 
        board = new Board(name);
        this.sh.setGUI(this);
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 1;
        gbc.weightx = 0.6;
        gbc.weighty = 0.7;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.insets = new Insets(5,5,5,5);
        for(int i = 0; i<10; i++){
            for(int e = 0; e<10; e++){
                board.getCheckBoard(i, e).addActionListener(sh);
            }
        }
        this.add(board,gbc);
        
    }
    private void addStatus(){
        status = new Status();
        status.setBoard(board);
        board.setStatus(status);
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.gridwidth = 1;
        gbc.weightx = 0.4;
        gbc.weighty = 0.7;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.insets = new Insets(5,0,5,5);
        this.add(status,gbc);
    }
    private void addChat(){
        chat = new ChatBox();
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 2;
        gbc.weightx = 1;
        gbc.weighty = 0.3;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.insets = new Insets(0,5,5,5);
        chat.getSendMessageButton().addActionListener(x -> {
            JTextField js = chat.getMessageField();
            if(js.getText().length() > 0 && !js.getText().matches("^\\s+$"))
                this.cmd = "$message " + "(" + this.board.getPlayerGame() + "): " + js.getText();
        });
        chat.getMessageField().addActionListener(x -> {
            JTextField js = chat.getMessageField();
            if(js.getText().length() > 0 && !js.getText().matches("^\\s+$")){
                this.cmd = "$message " + "(" + this.board.getPlayerGame() + "): " + js.getText();
                chat.appendText("(You): " + js.getText());
                js.setText("");                
            }
        });
        chat.getStartGameButton().addActionListener(x -> {
            javax.swing.JButton b = (javax.swing.JButton) x.getSource();
            if(inGame == false){
                if(List.of(status.getSet()).stream().allMatch(button -> button.getText().equals("Reset"))){
                    List.of(status.getSet()).forEach(l->l.setEnabled(false));
                    List.of(status.getSet()).forEach(l->l.setText("Locked"));
                    for(int i = 0; i<10; i++){
                        for(int j = 0; j<10; j++){
                            this.board.getCheckBoard(i, j).setEnabled(true);
                        }
                    }
                    inGame = true;
                    cmd = "$ingame";
                }
                
            }
            else{
                if(inGame == false){
                    JOptionPane.showMessageDialog(null, "You must set the locations of all the ships");
                }
            }
        });
        this.add(chat,gbc);
    }
    public boolean isOtherInGame(){
        return this.otherInGame;
    }
    public void hardReset(){
        this.status.hardReset();
        this.board.resetCheckboard();
    }
    
    public static void main(String[] args) {
        gui g = new gui();
    }
    private static class sendHit implements ActionListener{
        gui g;
        Board b;
        ChatBox c;
        public sendHit(gui g){
            b = g.getBoard();
            c = g.getChat();
        }
        public void setGUI(gui g){
            this.g = g;
        }
        @Override
        public void actionPerformed(ActionEvent e) {
            b = g.getBoard();
            c = g.getChat();
            for(int i = 0; i<10; i++){
                for(int j = 0; j<10; j++){
                    if(e.getSource().equals(b.getCheckBoard(i, j))){
                        if (g.isInGame() && g.isOtherInGame()) {
                            c.appendText(String.format("Attacking coords [%d,%d]", i, j));
                            g.setCmd(String.format("$attack %d %d", i, j));
                            b.getCheckBoard(i, j).setEnabled(false);
                        }
                    }
                }
            }
        }
        
    }

    public String getCommand() {
        return command;
    }

    public String getCmd() {
        return cmd;
    }

    public void setCommand(String command) {
        this.command = command;
    }

    public void setCmd(String cmd) {
        this.cmd = cmd;
    }

    public void setInGame(boolean inGame) {
        this.inGame = inGame;
    }
    
    public boolean isInGame() {
        return inGame;
    }
    
    public Board getBoard() {
        return board;
    }

    public ChatBox getChat() {
        return chat;
    }

    public Status getStatus() {
        return status;
    }
    
}
