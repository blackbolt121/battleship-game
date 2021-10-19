/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

/**
 *
 * @author rgo19
 */
public class gui extends JFrame{
    private Board board;
    private ChatBox chat;
    private Status status;
    private GridBagConstraints gbc;
    public gui(){
        super();
        gbc = new GridBagConstraints();
        this.setTitle("Battleship trial");
        this.setLayout(new GridBagLayout());
        this.setSize(700,700);
        this.setResizable(false);
        addBoard();
        addStatus();
        addChat();
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);
        
    }
    private void addBoard(){ 
        board = new Board(JOptionPane.showInputDialog("Digite su nombre", DISPOSE_ON_CLOSE));
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 1;
        gbc.weightx = 0.6;
        gbc.weighty = 0.7;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.insets = new Insets(5,5,5,5);
        this.add(board,gbc);
        
    }
    private void addStatus(){
        status = new Status();
        status.setBoard(board);
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
        this.add(chat,gbc);
    }
    public static void main(String[] args) {
        gui g = new gui();
    }
}
