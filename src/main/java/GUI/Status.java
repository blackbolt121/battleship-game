/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;

import battleship.Ships;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JPanel;
import javax.swing.BorderFactory;
import javax.swing.JButton;

/**
 *
 * @author rgo19
 */
public final class Status extends JPanel{
   public static java.awt.Color green = new java.awt.Color(6, 214, 160);
   private shipStatus[] st;
   private List<Integer> buttons;
   private JButton[] set;
   private resetOption rs;
   private Board b;
   public Status(){
       super();
       rs = new resetOption(this);
       buttons = new ArrayList<>();
       st = new shipStatus[Ships.NUMBEROFSHIPS];
       initComponents();
       setDefaultEvents();
   }
   public void setBoard(Board b){
       this.b = b;
   }
   public void initComponents(){
       this.setLayout(new java.awt.GridLayout(5,1));
       for(int i = Ships.NUMBEROFSHIPS; i>0; i--){
           st[Ships.NUMBEROFSHIPS-i] = new shipStatus(Ships.getShip(i));
           this.add(st[Ships.NUMBEROFSHIPS - i],i,0);
           
           
       }
   }
   public void hardReset(){
       List.of(this.set).forEach(x ->{ x.removeActionListener(rs); x.setText("Set");});
   }
   public Board getBoard(){
       return this.b;
   }
   public JButton[] getSet(){
       return set;
   }
   public void setDefaultEvents(){
       set = new JButton[Ships.NUMBEROFSHIPS];
       for(int i = 0; i<Ships.NUMBEROFSHIPS; i++){
           final int num = i;
           set[i] = st[i].getSet();
           set[i].setEnabled(true);
           set[i].addActionListener(l -> {
               JButton button = (JButton) l.getSource();
               if(l.getSource().equals(set[num])){
                   for (int j = 0; j < Ships.NUMBEROFSHIPS; j++) {
                       set[j].setEnabled(false);
                   }
                   b.insertBoat(Ships.getShip(5-num));
                   //boolean isSeted = b.insertBoat(Ships.getShip(num+1));
                   Runnable running = () -> {
                       while (b.getCount() > 0) {
                           try{
                               Thread.sleep(10);
                               //Check if coords where selected incorrectly with flag
                           }catch(Exception e){}
                       }
                       //Use if flag actived
                       
                       //Else case
                       buttons.add(num);
                       //Enable available buttons
                       for (int j = 0; j < Ships.NUMBEROFSHIPS; j++) {
                           final int aux = j;
                           if(buttons.stream().noneMatch(number->number==aux))
                                set[j].setEnabled(true);
                           else{
                               set[j].setText("Reset");
                               if (set[j].getActionListeners().length <= 1) {
                                   set[j].addActionListener(this.rs);
                               }else{
                                   System.out.println("Se ha agregado el evento anteriormente");
                               }
                               
                               set[j].setEnabled(true);
                           }
                       }
                   };
                   Thread t = new Thread(running);
                   t.start();
               }
           });
       }
       
   }
   
   private class resetOption implements ActionListener{
       
       private Status s;
       
       public resetOption(Status s){
           this.s = s;
       }

       @Override
       public void actionPerformed(ActionEvent e) {
           for(int i = 0; i<s.getSet().length; i++){
               if(e.getSource().equals(s.getSet()[i])){
                   System.out.println(5-i);
                   s.getBoard().resetBoatStatus(5-i);
               }
           }
       }
       
   }
   private static class shipStatus extends JPanel{
       
       private javax.swing.JLabel name,st;
       private Ships ship;
       RadiusPanel radius;
       private javax.swing.JButton bt;
       private final static String set = "Set";
       private final static String status[] = {"Online","Damaged","Sunked","Unknown"};
       public shipStatus(Ships s){
           super();
           ship = s;
           initComponents();
       }
       public void setLabelStatus(int i){
           if(i<=status.length && i>=0){
               this.st.setText(status[i]);
           }
       }
       public void setColor(java.awt.Color c){
           st.setForeground(c);
       }
       private void initComponents(){
           name = new javax.swing.JLabel();
           st = new javax.swing.JLabel();
           name.setText(ship.getName());
           st.setText(status[3]);
           bt = new javax.swing.JButton(set);
           bt.setEnabled(false);
           this.setLayout(new GridBagLayout());
           GridBagConstraints gbc = new GridBagConstraints();
           gbc.gridwidth = 2;
           gbc.fill = GridBagConstraints.BOTH;
           gbc.weightx = 1;
           gbc.weighty = 0.5;
           //Editing name label
           name.setFont(new java.awt.Font("Arial", java.awt.Font.BOLD, 16));
           name.setText(ship.getName() + "  ");
           name.setHorizontalAlignment(javax.swing.JLabel.CENTER);
           name.setVerticalAlignment(javax.swing.JLabel.CENTER);
           this.add(name,gbc);
           gbc.gridwidth = 1;
           gbc.weightx = 0.5;
           gbc.gridx = 0;
           gbc.gridy = 1;
           this.add(bt,gbc);
           gbc.gridx = 1;
           st.setHorizontalAlignment(javax.swing.JLabel.CENTER);
           st.setForeground(Board.ship);
           this.add(st,gbc);
           
       }
       public JButton getSet(){
           return bt;
       }
   }
   
   private static class RadiusPanel extends JPanel{
       private java.awt.Color status;
       public RadiusPanel(){
           super();
           status = Board.ship;
       }
       @Override
       public void paintComponent(Graphics g){
           g.setColor(status);
           g.fillOval(this.getX(),this.getY(), this.getWidth()/2, this.getHeight()/2);
       }
       public void changeStatus(java.awt.Color c){
           status = c;
       }
   }


}
