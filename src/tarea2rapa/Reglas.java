/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tarea2rapa;

import java.util.Scanner;
import java.util.regex.Pattern;

/**
 *
 * @author glady
 */
public class Reglas {
    
    private String reglaDigitos = "[0-9]";
    private String reglaNumeros = reglaDigitos+"|";
    
    private String word;
    
    public Reglas(){
        

    }
    
//    public boolean reglaDigito(String digito){
//        String regla = "[0-9]";
//        boolean res = Pattern.matches(regla, digito);
//        return res;
//    }
    
    public boolean reglaNumero(String numero){
        String regla = "[0-9]+";
        boolean res = Pattern.matches(regla, numero);
        return res;
    }
    
    public boolean reglaOperadorAritmetico(String operador){
        String regla = "[*[/[%[+[-]]]]]";
        boolean res = Pattern.matches(regla, operador);
        return res;
    }
    
    
    
    /*
    public boolean reglaOperadorLogico(String operador){
        String regla = ">=|<=|>|<|==|!=";
        boolean res = Pattern.matches(regla, operador);
        return res;
        
    }
*/
    
    
    public boolean reglaOperadorLogico(String operador){
        String regla = "[!=][==]";
        String regla2 = "[>[<]]";
        
        String regla3 = "[>][=]";//>=
        String regla4 = "[<][=]";//<=


        boolean res = Pattern.matches(regla, operador)||Pattern.matches(regla2, operador)||Pattern.matches(regla3, operador)||Pattern.matches(regla4, operador);
        return res;
        
    }
    
     
    public boolean reglaLetra(String operador){
        String regla = "[[a-z]*[A-Z]*]+";

        boolean res = Pattern.matches(regla, operador);
        return res;        
    }
    
    public boolean reglaAlfaNumerico(String operador){
        String regla = "[a-z][[[a-z]*[A-Z]*]*[0-9]*]*";

        boolean res = Pattern.matches(regla, operador);
        return res;        
    }
    
     public boolean reglaVariable(String operador){
        String regla = "[$][a-z][[[a-z]*[A-Z]*]*[0-9]*]*";

        boolean res = Pattern.matches(regla, operador);
        return res;        
    }
     
    public boolean parserN(String s){
        this.word = s;
        if (!parserD(word)){
            return false;
        }
        if (!parserNprima(word)){
            return false;
        }
        return true;
        
    }
    
    public boolean parserNprima(String s){
        char c = s.charAt(0);
        if (c == '0' || c == '1' || c == '2' || c == '3' || c == '4' || c =='5' || c == '6' || c == '7' || c == '8'|| c == '9'){
            return parserNprima1(s);
        }
        return parserNprima2();
    }
    
    public boolean parserNprima2(){
        return true;
    }
    
    public boolean parserNprima1(String s){
        return parserN(s);
    }
    
    public boolean parserD(String s){
        if(nextChar(s) == '0'){
            return parserD0(s);
        }
        if(nextChar(s) == '1'){
            return parserD1(s);
        }
        if(nextChar(s) == '2'){
            return parserD2(s);
        }
        if(nextChar(s) == '3'){
            return parserD3(s);
        }
        if(nextChar(s) == '4'){
            return parserD4(s);
        }
        if(nextChar(s) == '5'){
            return parserD5(s);
        }
        if(nextChar(s) == '6'){
            return parserD6(s);
        }
        if(nextChar(s) == '7'){
            return parserD7(s);
        }
        if(nextChar(s) == '8'){
            return parserD8(s);
        }
        if(nextChar(s) == '9'){
            return parserD9(s);
        }
        return false;
    }
    
    public boolean parserD0(String s){
         getChar(s);
         return true;
    }
    public boolean parserD1(String s){
        getChar(s);
         return true;
        
    }
    public boolean parserD2(String s){
        getChar(s);
         return true;
        
    }
    public boolean parserD3(String s){
        getChar(s);
         return true;
        
    }
    public boolean parserD4(String s){
        
        getChar(s);
         return true;
    }
    public boolean parserD5(String s){
        
        getChar(s);
         return true;
    }
    public boolean parserD6(String s){
        
        getChar(s);
         return true;
    }
    public boolean parserD7(String s){
        getChar(s);
         return true;
        
    }
    public boolean parserD8(String s){
        
         getChar(s);
         return true;
    }
    public boolean parserD9(String s){
         getChar(s);
         return true;
        
    }
        
        
    
    public char nextChar(String s){
        return s.charAt(0);
    }
    
    public void getChar(String s){
        if (s.length() > 1){
            word = s.substring(1);
        }
        else{
            word = " ";
        }
    }
    
    public int factorial(int n, String s){
        if (n == 1 || n == 0){
            return 1;
        }
        s = s.replaceAll(s, s.substring(1));
        return n*factorial(n-1,s);
    }
    
    
    
    
    
}
