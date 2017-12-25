/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tarea2rapa;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Stack;

/**
 *
 * @author glady
 */
public class RPM {
    
    private HashMap<String,String> hash;
    
    public RPM(){
        this.hash = new HashMap<>();
        this.hash.put("$a", "33");
        this.hash.put("$b", "34");
    }
    
    public ArrayList<String> infijoToRPM(String input){
        ArrayList<String> listOutput = new ArrayList<>();
        char inputChar[] = input.toCharArray();
        int n = inputChar.length;
        Stack<Character> stack = new Stack<>();
        String output = "";
        String num = "";
        // leo todos los token de entrada
        int i = 0;
        while (i < n) {
            char c = inputChar[i];
            // pregunto si lo que me viene es una variable
            boolean esVariable = c == '$';
            if (esVariable){
                String s = String.valueOf(c);
                num = num + s;
                if (i + 1 == n){
                    // no puede venir un $ como ultimo caracter de la cadena
                    System.out.println("Error");
                    break;
                }
                i++;
                c = inputChar[i];
                // ahora pregunto si me viene una letra
                boolean esLetra = c >= 97 && c <= 122;
                if (esLetra){
                    s = String.valueOf(c);
                    num = num + s;
                }
                else{
                    // despues de un $ siempre debe venir una letra
                    System.out.println("Error");
                    break;
                }
                // pregunto si la variable es lo ultimo que viene en la cadena de entrada
                if (i + 1 == n){
                    listOutput.add(num);
                    output = output + num;
                    break;
                }
                
                i++;
                c = inputChar[i];
                // pregunto si despues de haber venido un $ y una letra viene un alfanumerico
                boolean esAlfaNumerico = (c >= 97 && c <= 122) || (c >= 48 && c <= 57);
                while(esAlfaNumerico){
                    s = String.valueOf(c);
                    num = num + s;
                    if (i + 1 == n){
                        listOutput.add(num);
                        output = output + num;
                        break;
                    }
                    else{
                        i++;
                    }
                    c = inputChar[i];
                    esAlfaNumerico = (c >= 97 && c <= 122) || (c >= 48 && c <= 57);
                }
      
            }
            
            // pregunto si lo que me viene es un numero
            boolean esNumero = c == '0' || c == '1' || c == '2' || c == '3' || c == '4' || c == '5' || c == '6' || c == '7' || c == '8' || c == '9';
            while (esNumero){
                String s = String.valueOf(c);
                num = num + s;
                if (i + 1 == n){
                    listOutput.add(num);
                    output = output + num;
                    break;
                }
                else{
                    i++;
                }
                c = inputChar[i];
                esNumero = c == '0' || c == '1' || c == '2' || c == '3' || c == '4' || c == '5' || c == '6' || c == '7' || c == '8' || c == '9';
            }
            
            // pregunto si lo que me viene es un operador * o / o % o + o -
            boolean esOperador = c == '*' || c == '/' || c == '%' || c == '+' || c == '-';
            if (esOperador){
                
                listOutput.add(num);
                output = output + num;
                num = "";
                
                // pregunto si la pila no esta vacia
                if (!stack.empty()){
                    char peek = stack.peek();
                    if ((c == '*' || c == '/' || c == '%' || c == '+' || c  == '-') && (peek == '*' || peek == '/' || peek == '%')){
                        // retiro el operador en el tope de la pila y lo agrego a la salida
                        char retirado = stack.pop();
                        String s = String.valueOf(retirado);
                        output = output+s;
                        listOutput.add(s);
                        // agrego el operador al tope de la pila
                        stack.push(c);
                    }
                    else{
                        stack.push(c);
                    } 
                }
                else{
                    // agrego el operador al tope de la pila
                    stack.push(c);
                }
            }
            i++;
        }
        // termino de leer los tokens de la entrada
        
        while (!stack.empty()){
            char retirado = stack.pop();
            String s = String.valueOf(retirado);
            listOutput.add(s);
            output = output+s;
        }
        
        return listOutput;
    }
    
    
    public String resultadoRPM(String expression, HashMap<String,String> hash){
        ArrayList<String> notRPM = this.infijoToRPM(expression);
        //char elements[] = notRPM.toCharArray();
        Stack<String> stack = new Stack<>();
        String output = "";
        int n = notRPM.size();
        for (int i = 0; i < n; i++) {
            String element = notRPM.get(i);
            // pregunto si es una variable
            if (element.contains("$")){
                String key = element;
                String value = hash.get(key);
                stack.push(value);
            }
            // Si no es una variable es un numero o un operador
            else{
                boolean esNumero = isNumeric(element);
                if (esNumero){
                    stack.push(element);
                }
                boolean  esOperador = esOperador(element);
                if (esOperador){
                    String s = stack.pop();
                    int operando1 =  Integer.valueOf(s);
                    s = stack.pop();
                    int operando2 = Integer.valueOf(s);
                    String res = "";
                    switch (element) {
                        case "*":
                            res = String.valueOf(operando2*operando1);
                            stack.push(res);
                            break;
                        case "/":
                            res = String.valueOf(operando2/operando1);
                            stack.push(res);
                            break;
                        case "%":
                            res = String.valueOf(operando2%operando1);
                            stack.push(res);
                            break;
                        case "+":
                            res = String.valueOf(operando2+operando1);
                            stack.push(res);
                            break;
                        case "-":
                            res = String.valueOf(operando2-operando1);
                            stack.push(res);
                            break;
                    }
                }
            }
            
            
            
        }
        
        if (stack.size() == 1){
            output = stack.pop();
        }
        
        return output;  
    }
    
    public boolean isNumeric(String s){
        try{
            int num = Integer.parseInt(s);
        }
        catch(NumberFormatException e){
            return false;
        }        
        return true;
    }
    
    public boolean esOperador(String s){
        return s.equals("*") || s.equals("/") || s.equals("%") || s.equals("+")  || s.equals("-");
    }
    
    
    
}
