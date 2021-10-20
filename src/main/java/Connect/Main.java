/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Connect;

/**
 *
 * @author rgo19
 */
public class Main {
    public static void main(String[] args) {
        Runnable a = () ->{ System.out.println("Excuting client"); Client c = new Client(); };
        Runnable b = () -> {System.out.println("Executing server");Server s = new Server();};
        Thread x = new Thread(a), y = new Thread(b);
        if(true)
            y.run();
        else
            x.run();
    }
}
