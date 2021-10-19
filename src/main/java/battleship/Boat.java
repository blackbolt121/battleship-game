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
        cord = new Coords[s.getSize()];
        for(int i = 0; i<s.getSize(); i++){
            cord[i] = new Coords();
        }
        hits = 0;
    }
    public void setCoords(int x, int y){
        
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

