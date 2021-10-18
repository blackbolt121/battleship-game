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
public class Player {
    private Boat boats[];
    private String name;
    public Player(String name){
        boats = new Boat[5];
        for(int i = 0; i<5; i++){
            Ships s = Ships.getShip(i+1);
            boats[i] = new Boat(s);
        }
        this.name = name;
    }
}
