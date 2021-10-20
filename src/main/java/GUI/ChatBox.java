/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;

import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

/**
 *
 * @author rgo19
 */
public class ChatBox extends JPanel{
    private JTextArea chat;
    private JTextField msgbox;
    private JButton b[];
    private GridBagConstraints gbc;
    private JScrollPane js;
    private final static int buttons = 3;
    
    public ChatBox(){
        super();
        setLayout();
        initComponents();
        initDefualtValues();
        
    }
    private void setLayout(){
        this.setLayout(new GridBagLayout());
        gbc = new GridBagConstraints();
    }
    
    private void initComponents(){
        //Se crea el TextArea donde se imprimira el chat
        chat = new JTextArea(100000000,50);
        chat.setEnabled(false);
        chat.setForeground(Color.ORANGE);
        js = new JScrollPane(chat);
        js.setAutoscrolls(true);
        gbc.fill = GridBagConstraints.BOTH;
        gbc.gridx = 0;
        gbc.gridy = 1;
        b = new JButton[buttons];
        msgbox = new JTextField();
        gbc.weightx = 0.85;
        gbc.weighty = 0.1;
        //Adding 
        this.add(msgbox,gbc);
        gbc.weightx = 0.05;
        String content[] = {"Send","Clean","Connect"}; 
        for(int i = 0; i<buttons; i++){
            b[i] = new JButton(content[i]);
            gbc.gridx++;
            this.add(b[i],gbc);
        }
        gbc.weightx = 1;
        gbc.weighty = 0.9; 
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 4;
        this.add(js,gbc);    
    }
    
    public void initDefualtValues(){
        this.getSendMessageButton().setEnabled(true);
        this.getCleanChatBoxButton().setEnabled(true);
        this.getStartGameButton().setEnabled(true);
        this.getCleanChatBoxButton().addActionListener(a -> chat.setText(""));
        this.getSendMessageButton().addActionListener(a -> {
            if(!msgbox.getText().equals(""))
                chat.setText(chat.getText() + "(You): " + msgbox.getText() + "\n");
            msgbox.setText("");
        });
    }
    
    public void clearChatBox(){
        this.chat.setText("");
    }
    
    public JTextArea getChatBox(){
        return this.chat;
    }
    
    public JTextField getMessageField(){
        return this.msgbox;
    }
    
    public JButton getSendMessageButton(){
        return this.b[0];
    }
    
    public JButton getCleanChatBoxButton(){
        return this.b[1];
    }
    
    public JButton getStartGameButton(){
        return this.b[2];
    }
    public void appendText(String s){
        this.chat.setText(chat.getText() + s + "\n");
    }
}
