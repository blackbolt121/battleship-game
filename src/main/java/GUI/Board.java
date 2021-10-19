/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;

import battleship.Boat;
import java.awt.GridLayout;
import javax.swing.JPanel;
import battleship.Player;
import battleship.Ships;
import java.awt.Color;
import java.util.List;
import java.util.Map.Entry;
import java.util.AbstractMap.SimpleEntry;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
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
    private HashMap<Ships,Boat> coords;
    private final static int columns = 10, rows = 10;
    private checkboard[][] check;
    private boolean putBoat,attack;
    private Ships tempBoat;
    private int count;
    private Player p;
    public Board(String name){
        putBoat = attack = true;
        coords = new HashMap<>();
        this.p = new Player(name);
        this.setLayout(new GridLayout(rows,columns));
        check = new checkboard[rows][columns];
        Board auto = this;
        count = 0;
        for(int i = 0; i<rows; i++){
            for(int e = 0; e < columns; e++ ){
                check[i][e] = new checkboard(this,i,e);
                this.add(check[i][e],i,e);
                final int x = i, y = e;
                check[i][e].addActionListener(l -> {
                    checkboard ch = (checkboard) l.getSource();
                    if(putBoat)
                    {
                        if(tempBoat != null)
                        {
                            if(!coords.containsKey(tempBoat)){
                                coords.put(tempBoat, p.getBoat(tempBoat))
                            }
                            auto.paintCheckBoard(x, y, Board.ship);
                            count--;
                            if(count == 0){
                                
                            }
                        }
                        else
                        {
                            count = 0;
                        }
                    }
                    if(attack)
                    {
                        
                    }
                });
            }
        }
        count = 0;
    }
    public void paintCheckBoard(int row, int column, java.awt.Color c){
        if((row < rows && column < columns) && (row>=0 && column >= 0)){
            this.check[row][column].setBackground(c);
            check[row][column].setEnabled(false);
        }
    }
    public void resetButton(int row, int column){
        if((row < rows && column < columns) && (row>=0 && column >= 0)){
            this.check[row][column].setBackground(Board.ocean);
            check[row][column].setEnabled(true);
        }
    }
    public boolean insertBoat(Ships s){
        synchronized (this) {
            count = s.getSize();
            putBoat = true;
            tempBoat = s;
            Thread insert = new Thread(new putBoat(this));
            insert.start();
            
        }
        return true;
    }
    public void setTempBoat(Ships s){
        this.tempBoat = s;
    }
    public void decrementCount(){
        synchronized(this){
            if(count > 0){
                count--;
            }
        }
    }
    public synchronized void setPutBoat(boolean b){
        this.putBoat = b;
    }
    public synchronized int getCount(){
        return count;
    }
    private static boolean checkValidCoords(List<Entry<Integer,Integer>> coords){
        Set<Integer> columns = coords.stream().map(x -> x.getKey()).collect(Collectors.toSet());
        Set<Integer> rows = coords.stream().map(x -> x.getValue()).collect(Collectors.toSet());
        boolean var = true;
        if(columns.size() == 1){
            int last = -1;
            for(Integer e: rows){
                if(last == -1){
                    last = e;
                }else{
                    if(!(Math.abs(last - e) == 1)){
                        return false;
                    }
                }
            }
            return true;
        }else if(rows.size() == 1){
            int last = -1;
            for(Integer e : columns){
                if(last == -1){
                    last = e;
                }else{
                    if(!(Math.abs(last - e) == 1)){
                        return false;
                    }
                }
            }
            return true;
        }else{
          return false;  
        }
        
    }
    private static class putBoat implements Runnable{
        private Board b;
        public putBoat(Board board){
            b = board;
        }
        @Override
        public void run() {
            while (b.getCount() > 0) {
                try {
                    Thread.sleep(10);
                } catch (InterruptedException ex) {
                    Logger.getLogger(Board.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            b.setPutBoat(false);
            
        }
        
    }
}
