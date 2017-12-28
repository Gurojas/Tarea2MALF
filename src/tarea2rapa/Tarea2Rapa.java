/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tarea2rapa;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author glady
 */
public class Tarea2Rapa {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        Reglas reglas = new Reglas();
        //System.out.println(reglas.parserN("23"));
        Programa programa = new Programa("prog");
        programa.leerPrograma();
        
        
        
        
        /*
        int n = 10;
        int act = 0;
        if (n < 2){
            System.out.println(n);
        }
        else{
            int fib1 = 1;
            int fib2 = 0;
            int i = 2;
            while (i <= n){
                act = fib1 + fib2;
                fib2 = fib1;
                fib1 = act;
                i = i + 1;
            }
            System.out.println(act);
        }
        */
        
        
        
        
    }
    
}
