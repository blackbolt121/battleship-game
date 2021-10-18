/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package game;

import battleship.Ships;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import javax.swing.JPanel;
import javax.swing.BorderFactory;

/**
 *
 * @author rgo19
 */
public final class Status extends JPanel{
   public static java.awt.Color green = new java.awt.Color(6, 214, 160);
   private shipStatus[] st;
   public Status(){
       super();
       st = new shipStatus[Ships.NUMBEROFSHIPS];
       initComponents();
   }
   public void initComponents(){
       this.setLayout(new java.awt.GridLayout(5,1));
       for(int i = 0; i<Ships.NUMBEROFSHIPS; i++){
           st[i] = new shipStatus(Ships.getShip(i+1));
           this.add(st[i],i,0);
           
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
