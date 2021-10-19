/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package battleship;

import java.util.List;
import java.util.function.Predicate;

/**
 *
 * @author rgo19
 */
public class Boat {
    private Coords[] cord;
    private int setCoords;
    private Ships ship;
    private int hits;
    private boolean sunked;
    
    public Boat(){
        hits = 0;
    }
    public Boat(Ships s){
        setCoords = 0;
        this.ship = s;
        
        int size = s.getSize();
        cord = new Coords[size];
        for(int i = 0; i<s.getSize(); i++){
            cord[i] = new Coords();
        }
        hits = 0;
    }
    public Coords[] getCoords(){
        return this.cord;
    }
    public void setCoords(int x, int y){
        if(this.setCoords<ship.getSize()){
            System.out.println(ship.getName());
            cord[setCoords].setX(x);
            cord[setCoords].setY(y);
            System.out.println(this.setCoords);
            this.setCoords++;
        }
    }
    public void resetCoords(){
        for(int i = 0; i<this.getCoords().length; i++){
            cord[i].reset();
        }
        this.setCoords = 0;
    }
    public void setCoordsVar(int i){
        this.setCoords = i;
    }
    public boolean isHit(Coords coord){
        Predicate<Coords> equalsCoords = x -> x.equals(coord);
        boolean hit = List.of(cord)
                .stream()
                .anyMatch(equalsCoords);
        if(hit){
            hits++;
            sunked = (hits == ship.getSize()); 
        }
        return hit;
    }
    public boolean isSunked(){
        return sunked;
    }
    public Ships getShip(){
        return ship;
    } 
}

