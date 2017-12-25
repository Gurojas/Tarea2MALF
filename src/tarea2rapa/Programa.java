/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tarea2rapa;

import java.io.File;
import java.io.FileNotFoundException;
import java.math.BigInteger;
import java.util.HashMap;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author glady
 */
public class Programa {
    
    private HashMap<String,String> hash;
    private Scanner scanner;
    private String programa;
    
    public Programa(String ruta){
        this.hash = new HashMap<>();
        this.scanner = new Scanner(System.in);
        this.programa = "";
        
        this.cargarPrograma(ruta);
    }
    // Metodo que carga el contenido de un archivo al atributo progama 
    private void cargarPrograma(String ruta){
        Scanner scanner = null;
        try {
            scanner = new Scanner(new File(ruta));
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Programa.class.getName()).log(Level.SEVERE, null, ex);
        }
        while (scanner.hasNextLine()){
            String line = scanner.nextLine();
            this.programa = this.programa + line+"\n";
        }        
    }
    
    // Metodo que lee el programa que esta almacenado en el atributo programa
    // y revisa cada linea y pregunta de que tipo de instruccion es
    public void leerPrograma(){
        Scanner scanner = new Scanner(this.programa);
        while (scanner.hasNextLine()){
            String line = scanner.nextLine();
            if (this.read(line)){
                System.out.println("Lectura exitosa");
            }
            else if (this.write(line)){
                System.out.println("Escritura exitosa");
            }
            else if(this.asignacion(line)){
                System.out.println("Asignacion exitosa");
            }
        }
    }
    
    // Metodo que simula la funcion read().Esta funcion recibe un dato por 
    // entrada estandar y la guarda en una variable
    // retorna true si el metodo logro leer el dato
    // de lo contrario retorna false.
    public boolean read(String line){
        Scanner scanner = new Scanner(line);
        String token = scanner.next();
        if (token.equals("read")){
            token = scanner.next();
            if(token.startsWith("$") && token.endsWith(";")){
                int index = token.indexOf(";");
                String var = token.substring(0, index);
                String value = this.scanner.nextLine();
                this.hash.put(var, value);
                return true;
            }
            else{
                return false;
            }
        }
        else{
            return false;
        }
    }
    // Metodo que simula la funcion write. Esta funcion muestra por pantalla
    // el resultado de una expresion.
    public boolean write(String line){
        Scanner scanner = new Scanner(line);
        String token = scanner.next();
        if (token.equals("write")){
            String exp = "";
            while (scanner.hasNext()){
                exp = exp + scanner.next();
            }
            int index = exp.indexOf(";");
            if (exp.endsWith(";")){
                exp = exp.substring(0,index);
                RPM rpm = new RPM();
                String res = rpm.resultadoRPM(exp, this.hash);
                if (res == null){
                    return false;
                }
                System.out.println(res);
                return true;
            }
            else{
                return false;
            }
        }
        else{
            return false;
        }
    }
    
    public boolean asignacion(String line){
        Scanner scanner = new Scanner(line);
        String token = scanner.next();
        if (token.contains("$")){
            String key = token;
            token = scanner.next();
            if (token.equals("=")){
                String exp = "";
                while (scanner.hasNext()){
                    exp = exp + scanner.next();
                }
                if (exp.endsWith(";")){
                    int index = exp.indexOf(";");
                    exp = exp.substring(0, index);
                    RPM rpm = new RPM();
                    String res = rpm.resultadoRPM(exp, this.hash);
                    this.hash.put(key, res);
                    return true;
                }
                else{
                    return false;
                }
            }
            else{
                return false;
            }
        }
        else{
            return false;
        }
    }
}
