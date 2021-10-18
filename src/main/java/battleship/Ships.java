/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package battleship;

/**
 *
 * @author rgo19
 */
public enum Ships{
    CARRIER(1,5,"CARRIER"),
    BATTLESHIP(2,4,"BATTLESHIP"),
    CRUISER(3,3,"CRUISER"),
    SUBMARINE(4,3,"SUBMARINE"),
    DESTROYER(5,2,"DESTROYER");
    private int id,size;
    private String type;
    private Ships(int id, int size, String type){
        this.id = id;
        this.size = size;
        this.type = type;
    }
    public int getSize(){
        return size;
    }
    public String getName(){
        return type;
    }
    public String toString(){
        return type;
    }
    public static Ships getShip(int n){
        switch(n){
            case 1: 
                return Ships.CARRIER;
            case 2:
                return Ships.BATTLESHIP;
            case 3:
                return Ships.CRUISER;
            case 4:
                return Ships.SUBMARINE;
            case 5:
                return Ships.DESTROYER;
            default:
                return null;
        }
    }
    public final static int NUMBEROFSHIPS = 5;
}
