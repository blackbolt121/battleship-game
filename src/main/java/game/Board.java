/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package game;

import java.awt.GridLayout;
import javax.swing.JPanel;
import battleship.Player;
import java.awt.Color;
/**
 *
 * @author rgo19
 */
public class Board extends JPanel{
    public final static Color 
            ocean = new Color(151, 200, 235),
            ship = new Color(169, 180, 194),
            tried = new Color(231, 187, 65),
            hit= new Color(217, 30, 54);
    private final static int columns = 10, rows = 10;
    private checkboard[][] check;
    private Player p;
    public Board(String name){
        
        this.p = new Player(name);
        this.setLayout(new GridLayout(rows,columns));
        check = new checkboard[rows][columns];
        for(int i = 0; i<rows; i++){
            for(int e = 0; e < columns; e++ ){
                check[i][e] = new checkboard(this,i,e);
                this.add(check[i][e],i,e);
            }
        }
    }
    
}
