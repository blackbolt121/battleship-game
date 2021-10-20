/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package battleship;

import java.util.ArrayList;
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
    private List<Coords> damagedCoords;
    public Boat(){
        hits = 0;
        damagedCoords = new ArrayList<>();
    }
    public Boat(Ships s){
        this();
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
    
    public boolean isSet(){
        Predicate<Integer> validComponent = x -> x<10 && x>=0;
        return  List.of(cord)
                .stream()
                .map(x -> x.getX())
                .noneMatch(validComponent) 
                && 
                List.of(cord)
                .stream()
                .map(x -> x.getY())
                .noneMatch(validComponent);
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
            if(this.damagedCoords.stream().noneMatch(equalsCoords)){
                hits++;
                sunked = (hits == ship.getSize());
                this.damagedCoords.add(coord);
            }
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

