/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package System;

import java.awt.image.BufferedImage;

/**
 *
 * @author Dave van Rijn, Student 500714558, Klas IS202
 */
public class Mail {
    
    private String user;
    private Exception exception;
    private static BufferedImage screenshot;
    
    public Mail(String user, Exception exception){
        this.user = user;
        this.exception = exception;
    }
    
    public static void takeScreenshot(){
        
        screenshot = null;
    }
}
