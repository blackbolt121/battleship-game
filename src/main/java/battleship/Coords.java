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
public class Coords {
    private int x, y;
    public Coords(){
        super();
        this.x = -1;
        this.y = -1;
    }
    
    public Coords(int x, int y){
        this.x = x;
        this.y = y;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }
    
    public void reset(){
        this.x = -1;
        this.y = -1;
    }
    
    @Override
    public boolean equals(Object o){
        if(o instanceof Coords){
            Coords cmp = (Coords) o;
            return (this.getX() == cmp.getX() && this.getY() == cmp.getY());
        }
        return false;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 59 * hash + this.x;
        hash = 59 * hash + this.y;
        return hash;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
    
}
