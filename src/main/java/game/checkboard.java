/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package game;

import javax.swing.JButton;

/**
 *
 * @author rgo19
 */
public class checkboard extends JButton{
    private int i, j;
    private Board b;
    public checkboard(Board b, int i, int j){
        super();
        this.b = b;
        this.i = i;
        this.j = j;
        this.setBackground(Board.ocean);
        
    }
    public void paint(java.awt.Color c){
        this.setBackground(c);
    }
}
