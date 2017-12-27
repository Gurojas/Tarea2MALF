/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tarea2rapa;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Pattern;

/**
 *
 * @author glady
 */
public class Programa {
    
    private HashMap<String,String> hash;
    private ArrayList<String> instrucciones;
    private ArrayList<BuscaIf> ifs;
    private Scanner scanner;
    private String programa;
    
    public Programa(String ruta){
        this.hash = new HashMap<>();
        this.instrucciones = new ArrayList<>();
        this.ifs = new ArrayList<>();
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
        int i = 1;
        this.instrucciones.add("lala");
        while (scanner.hasNextLine()){
            String line = scanner.nextLine();
            this.instrucciones.add(line);
            
            if (line.contains("if") && !line.equals("endif;")){
                System.out.println("ocurrencia if: "+i);
            }
            if (line.contains("endif;")){
                System.out.println("Ocurrencia endif: "+i);
            }
            i++;
            this.programa = this.programa + line+"\n";
        }
        

        for (int j = 0; j < instrucciones.size(); j++) {
            String line = instrucciones.get(j);
            scanner = new Scanner(line);
            
            if (line.contains("if") && !line.contains("endif;")){
                BuscaIf buscaIf = new BuscaIf();
                buscaIf.setIndexIf(j);
                int k = 0;
                char c = line.charAt(k);
                int count = 0;
                if (c == '\t'){
                    count = count + 1;
                    k++;
                    c = line.charAt(k);
                    while (c == '\t'){
                        count++;
                        k++;
                        c = line.charAt(k);
                    }
                }
                // concateno los \t
                String findElse = "";
                String findEndif = "";
                for (int l = 0; l < count; l++) {
                    findElse = "\t" + findElse;
                    findEndif = "\t" + findEndif;
                }
                findElse = findElse+"else";
                findEndif = findEndif+"endif;";
                
                // ahora busco donde estan los else o if
                for (int l = j; l < instrucciones.size(); l++) {
                    String token = instrucciones.get(l);
                    if (token.equals(findElse)){
                        buscaIf.setIndexElse(l);
                    }
                    else if (token.equals(findEndif)){
                        buscaIf.setIndexEndif(l);
                    }
                    
                }

                this.ifs.add(buscaIf);

                
                
            }
            

            
            
        }
        
        int a = 2;
        
        
        
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
            if (scanner.hasNext()){
                token = scanner.next();
                if (token.contains(" ")){
                    return false;
                }
                String regla = Pattern.quote("$")+"[a-z]([a-z]|[0-9])*;";
                boolean matches = token.matches(regla);
                if (matches){
                    int index = token.indexOf(";");
                    token = token.substring(0, index);
                    String key = token;
                    String value = this.scanner.nextLine();
                    this.hash.put(key, value);
                    return true;
                }
                else{
                    return false;
                }              
            }
            else{
                return false;
            }
            /*
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
            */
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
    
    
    
    //public boolean condicion(){
        
    //}
    
    private class BuscaIf{
        private int indexIf = -1;
        private int indexElse = -1;
        private int indexEndif = -1;

        public int getIndexIf() {
            return indexIf;
        }

        public void setIndexIf(int indexIf) {
            this.indexIf = indexIf;
        }

        public int getIndexElse() {
            return indexElse;
        }

        public void setIndexElse(int indexElse) {
            this.indexElse = indexElse;
        }

        public int getIndexEndif() {
            return indexEndif;
        }

        public void setIndexEndif(int indexEndif) {
            this.indexEndif = indexEndif;
        }
        
        
    }
}
