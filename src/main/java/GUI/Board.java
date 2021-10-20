/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;

import battleship.Boat;
import battleship.Coords;
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
import javax.swing.BorderFactory;
import javax.swing.JOptionPane;
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
    private boolean putBoat,attack, error;
    private Ships tempBoat;
    private int count;
    private Player p;
    private String name;
    private Status st;
    public Board(String name){
        this.name = ((name.equals(""))?"Unknown":name);
        error = putBoat = attack = false;
        coords = new HashMap<>();
        this.p = new Player(this.name);
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
                            error = false;
                            if(!coords.containsKey(tempBoat)){
                                coords.put(tempBoat, p.getBoat(tempBoat.getID()-1));
                            }
                            if(p.validCoords(x, y)){
                                auto.paintCheckBoard(x, y, Board.ship,false);
                                p.getBoat(tempBoat.getID()-1).setCoords(x, y);
                                count--;
                            }
                            //Si se han seleccionado correctamente las casillas entonces
                            //Se procede a verificar si la continuación de las coordenadas es valida
                            if(count == 0){
                                if(Board.checkValidCoords(coords.get(tempBoat).getCoords())){
                                    
                                }else{
                                    
                                    JOptionPane.showMessageDialog(null, "Las coordenadas ingresadas de su bote no son las adecuadas");
                                    for(Coords c : coords.get(tempBoat).getCoords()){
                                        auto.resetButton(c.getX(), c.getY());
                                    }
                                    st.updateLabelStatus(5-tempBoat.getID(), 3);
                                    st.getSet()[5-tempBoat.getID()].setText("Set");
                                    coords.get(tempBoat).resetCoords();
                                    count = tempBoat.getSize();
                                }
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
    public boolean coordsInBoat(int i, int e){
        for(int x = 0; x<5; x++){
            if(List.of(this.p.getBoat(x).getCoords()).stream().anyMatch(z -> z.equals(new Coords(i,e)))){
                return true;
            }
        }
        return false;
    }
    public void setStatus(Status st){
        this.st = st;
    }
    public String getPlayerGame(){
        return this.p.getName();
    }
    public void setPlayerGame(String name){
        this.p.setName(name);
    }
    public void paintCheckBoard(int row, int column, java.awt.Color c, boolean mode){
        if((row < rows && column < columns) && (row>=0 && column >= 0)){
            this.check[row][column].setBackground(c);
            check[row][column].setEnabled(mode);
        }
    }
    public void paintBorderCheckBoard(int row, int column, java.awt.Color c, boolean mode){
        if((row < rows && column < columns) && (row>=0 && column >= 0)){
            this.check[row][column].setBorder(BorderFactory.createLineBorder(c));
            check[row][column].setEnabled(mode);
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
        return error;
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
    
    public void resetBoatStatus(int i){
        if(i>0 && i<=Ships.NUMBEROFSHIPS){
            for(Coords c : p.getBoat(i-1).getCoords()){
                this.resetButton(c.getX(), c.getY());
            }
            this.p.getBoat(i-1).resetCoords();
            count = Ships.getShip(i).getSize();
            coords.remove(Ships.getShip(i));
        }
        
    }
    public void resetCheckboard(){
        List.of(this.check)
                .stream()
                .forEach(x -> List.of(x).stream()
                        .forEach(y -> y.setBackground(Board.ocean)));
        for(int i = 0; i<Ships.NUMBEROFSHIPS; i++){
            this.p.getBoat(i).resetCoords();
        }
    }
    
    public boolean attackCoords(int x, int y){
        this.p.attackCoords(x, y);
        Boat b;
        if((b = this.p.getHittenBoat(x, y)) != null){
            this.paintCheckBoard(x, y, Board.hit,true);
            if(b.isSunked()){
                JOptionPane.showMessageDialog(null, "Your " + b.getShip().toString() + " has been sunked");
            }
            return true;
        }
        return false;
    }
    public String strToAttackCoords(int x, int y){
        String s = "";
        if(attackCoords(x,y)){
            Boat b;    
            if ((b = this.p.getHittenBoat(x, y)) != null) {
                s += "your boat "+ b.getShip().toString() + " has been damaged";
                this.st.updateLabelStatus(5-b.getShip().getID(), 1);
                if (b.isSunked()) {
                    s += "\n    Your " + b.getShip().toString() + " has been sunked";
                    this.st.updateLabelStatus(5-b.getShip().getID(), 2);
                }

            }
        }else{
            s = "failed";
        }
        return s;
    }
    public checkboard getCheckBoard(int i, int j){
        return this.check[i][j];
    }
    private static boolean checkValidCoords(Coords[] coords){
        Set<Integer> columns = List.of(coords).stream().map(c -> c.getX()).collect(Collectors.toSet());
        Set<Integer> rows = List.of(coords).stream().map(c -> c.getY()).collect(Collectors.toSet());
        boolean var = true;
        if(columns.size() == 1){
            int last = -1;
            for(Integer e: rows){
                if(last == -1){
                    last = e;
                }else{
                    if(!(Math.abs(e-last) == 1)){
                        return false;
                    }else{
                        System.out.println(e + " - " + last + " = " + Math.abs(e-last));
                    }
                    last = e;
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
                    }else{
                        last = e;
                    }
                }
            }
            return true;
        }else{
          return false;  
        }
        
    }

    public boolean isError() {
        return error;
    }
    
    private static class putBoat implements Runnable{
        
        private Board b;
        private boolean error;
        public putBoat(Board board){
            b = board;
        }
        public void setError(boolean error){
            this.error = error;
        }
        public boolean getError(){
            return error;
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
