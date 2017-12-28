/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tarea2rapa;

import java.io.File;
import java.io.FileNotFoundException;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.InputMismatchException;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Pattern;

/**
 *
 * @author glady
 */
public class Programa {
    
    private HashMap<String,BigInteger> hash;
    private ArrayList<String> instrucciones;
    private HashMap<Integer,BuscaIf> ifs;
    private HashMap<Integer,BuscaWhile> whiles;
    private Scanner scanner;
    private String programa;
    private int pcCounter = 0;
    
    public Programa(String ruta){
        this.hash = new HashMap<>();
        this.instrucciones = new ArrayList<>();
        this.ifs = new HashMap<>();
        this.whiles = new HashMap<>();
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
                        break;
                    }
                    
                }

                this.ifs.put(j,buscaIf);

            }
            else if (line.contains("while") && !line.contains("wend;")){
                BuscaWhile buscaWhile = new BuscaWhile();
                buscaWhile.setIndexWhile(j);
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
                String findEndWhile = "";
                for (int l = 0; l < count; l++) {
                    findEndWhile = "\t" + findEndWhile;
                }
                findEndWhile = findEndWhile+"wend;";
                
                // ahora busco donde estan los else o if
                for (int l = j; l < instrucciones.size(); l++) {
                    String token = instrucciones.get(l);
                    if (token.equals(findEndWhile)){
                        buscaWhile.setIndexEndwhile(l);
                        break;
                    }  
                }
                this.whiles.put(j,buscaWhile);
                
            }
        }
        int a = 2;

    }
    
    // Metodo que lee el programa que esta almacenado en el atributo programa
    // y revisa cada linea y pregunta de que tipo de instruccion es
    public void leerPrograma(){
        Scanner scanner = new Scanner(this.programa);
        /*
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
        */
        
        for (pcCounter = 0; pcCounter < this.instrucciones.size(); pcCounter++) {
            String line = instrucciones.get(pcCounter);
            if (this.read(line)){
                System.out.println("Lectura exitosa");
            }
            else if (this.write(line)){
                System.out.println("Escritura exitosa");
            }
            
            else if(this.asignacion(line)){
                System.out.println("Asignacion exitosa");
            }
            
            else if (this.condicion(line, pcCounter)){
                
            }
            else if (this.ciclo(line, pcCounter)){
                
            }
            
        }  
    }
    
     public void leerPrograma(int i, int f){
        Scanner scanner = new Scanner(this.programa);
        
        for (int j = i; j < f; j++) {
            String line = instrucciones.get(j);
            if (this.read(line)){
                System.out.println("Lectura exitosa");
            }
            else if (this.write(line)){
                System.out.println("Escritura exitosa");
            }
            else if(this.asignacion(line)){
                System.out.println("Asignacion exitosa");
            }
            else if (this.condicion(line, j)){
                j = pcCounter;
            }
            else if (this.ciclo(line, j)){
                j = pcCounter;
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
                    BigInteger value = BigInteger.ZERO;
                    try{
                        value = this.scanner.nextBigInteger();
                    }
                    catch(InputMismatchException e){
                        System.out.println("error: La entrada no es un numero");
                        return false;
                    }
                    //BigInteger value = this.scanner.nextBigInteger();
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
                BigInteger bi = rpm.resultadoRPM(exp, this.hash);
                //String res = rpm.resultadoRPM(exp, this.hash);
                if (bi == null){
                    return false;
                }
                System.out.println(bi);
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
                    //String res = rpm.resultadoRPM(exp, this.hash);
                    BigInteger bi = rpm.resultadoRPM(exp, this.hash);
                    this.hash.put(key, bi);
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
    
    
    
    public boolean condicion(String line, int index){
        BuscaIf buscaif = this.ifs.get(index);
        
        Scanner scanner = new Scanner(line);
        String token = scanner.next();
        if (token.contains("if")){
            if (scanner.hasNext()){
                token = scanner.nextLine();
                
                int apertura = token.indexOf("(");
                int cerradura = token.indexOf(")");
                String exp = token.substring(apertura+1,cerradura);
                
                //if (token.startsWith("(") && token.endsWith(")")){
                    //int apertura = token.indexOf("(");
                    //int cerradura = token.indexOf(")");
                    //String exp = token.substring(apertura+1, cerradura);
                    String exp1 = "";
                    String exp2 = "";
                    String res1 = "";
                    String res2 = "";
                    String oplog = "";
                    BigInteger resu1 = BigInteger.ZERO;
                    BigInteger resu2 = BigInteger.ZERO;
                    if (exp.contains(">=")){
                        int ocu = exp.indexOf(">=");
                        exp1 = exp.substring(0, ocu);
                        exp2 = exp.substring(ocu+2,exp.length());
                        oplog = ">=";
                        
                    }
                    else if (exp.contains("<=")){
                        int ocu = exp.indexOf("<=");
                        exp1 = exp.substring(0, ocu);
                        exp2 = exp.substring(ocu+2,exp.length());
                        oplog = "<=";
                    }
                    else if (exp.contains("<")){
                        int ocu = exp.indexOf("<");
                        exp1 = exp.substring(0, ocu);
                        exp2 = exp.substring(ocu+1,exp.length());
                        oplog = "<";
                    }
                    else if (exp.contains(">")){
                        int ocu = exp.indexOf(">");
                        exp1 = exp.substring(0, ocu);
                        exp2 = exp.substring(ocu+1,exp.length());
                        oplog = ">";
                    }
                    else if (exp.contains("==")){
                        int ocu = exp.indexOf("==");
                        exp1 = exp.substring(0, ocu);
                        exp2 = exp.substring(ocu+2,exp.length());
                        oplog = "==";
                    }
                    else if (exp.contains("!=")){
                        int ocu = exp.indexOf("!=");
                        exp1 = exp.substring(0, ocu);
                        exp2 = exp.substring(ocu+2,exp.length());
                        oplog = "!=";
                    }
                    RPM rpm = new RPM();
                    exp1 = exp1.replaceAll(" ", "");
                    resu1 = rpm.resultadoRPM(exp1, hash);
                    exp2 = exp2.replaceAll(" ", "");
                    resu2 = rpm.resultadoRPM(exp2, hash);
                    
                    token = token.substring(cerradura+2);
                    //token = scanner.next();
                    if (token.equals("then")){
                        boolean val = false;
                        if (oplog.equals(">=")){
                            res1 = String.valueOf(resu1);
                            res2 = String.valueOf(resu2);
                            val = Integer.valueOf(res1) >= Integer.valueOf(res2);
                        }
                        else if (oplog.equals("<=")){
                            res1 = String.valueOf(resu1);
                            res2 = String.valueOf(resu2);
                            val = Integer.valueOf(res1) <= Integer.valueOf(res2);
                        }
                        else if (oplog.equals(">")){
                            res1 = String.valueOf(resu1);
                            res2 = String.valueOf(resu2);
                            val = Integer.valueOf(res1) > Integer.valueOf(res2);
                        }
                        else if (oplog.equals("<")){
                            res1 = String.valueOf(resu1);
                            res2 = String.valueOf(resu2);
                            val = Integer.valueOf(res1) < Integer.valueOf(res2);
                        }
                        else if (oplog.equals("==")){
                            res1 = String.valueOf(resu1);
                            res2 = String.valueOf(resu2);
                            val = Integer.valueOf(res1) == Integer.valueOf(res2);
                        }
                        else if (oplog.equals("!=")){
                            res1 = String.valueOf(resu1);
                            res2 = String.valueOf(resu2);
                            val = Integer.valueOf(res1) != Integer.valueOf(res2);
                        }
                        // aca veo si salto a un endif o a un else
                        
                        if (val){
                            if (buscaif.indexIf != -1 && buscaif.indexElse != -1){
                                this.leerPrograma(buscaif.getIndexIf()+1, buscaif.getIndexElse());
                                pcCounter = buscaif.indexEndif;
                                return true;
                            }
                            else{
                                this.leerPrograma(buscaif.getIndexIf()+1, buscaif.indexEndif);
                                pcCounter = buscaif.indexEndif;
                                return true;
                            }
                        }
                        else{
                            if (buscaif.indexElse != -1 && buscaif.indexEndif != -1){
                                this.leerPrograma(buscaif.getIndexElse()+1, buscaif.getIndexEndif());
                                pcCounter = buscaif.indexEndif;
                                return true;
                            }
                            else{
                                pcCounter = buscaif.indexEndif;
                                return true;
                            }
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
            else{
                return false;
            }
        //}
        //else{
        //    return false;
        //}
    }
    
    public boolean ciclo(String line, int index){
        BuscaWhile buscaWhile = this.whiles.get(index);
        Scanner scanner = new Scanner(line);
        String token = scanner.nextLine();
        
        if (!token.contains("while")){
            return false;
        }
        
        int apertura = token.indexOf("(");
        int cerradura = token.indexOf(")");
        String exp = token.substring(apertura+1, cerradura);
        String exp1 = "";
        String exp2 = "";
        String res1 = "";
        String res2 = "";
        String oplog = "";
        BigInteger resu1 = BigInteger.ZERO;
        BigInteger resu2 = BigInteger.ZERO;
        if (exp.contains(">=")){
            int ocu = exp.indexOf(">=");
            exp1 = exp.substring(0, ocu);
            exp2 = exp.substring(ocu+2,exp.length());
            oplog = ">=";

        }
        else if (exp.contains("<=")){
            int ocu = exp.indexOf("<=");
            exp1 = exp.substring(0, ocu);
            exp2 = exp.substring(ocu+2,exp.length());
            oplog = "<=";
        }
        else if (exp.contains("<")){
            int ocu = exp.indexOf("<");
            exp1 = exp.substring(0, ocu);
            exp2 = exp.substring(ocu+1,exp.length());
            oplog = "<";
        }
        else if (exp.contains(">")){
            int ocu = exp.indexOf(">");
            exp1 = exp.substring(0, ocu);
            exp2 = exp.substring(ocu+1,exp.length());
            oplog = ">";
        }
        else if (exp.contains("==")){
            int ocu = exp.indexOf("==");
            exp1 = exp.substring(0, ocu);
            exp2 = exp.substring(ocu+2,exp.length());
            oplog = "==";
        }
        else if (exp.contains("!=")){
            int ocu = exp.indexOf("!=");
            exp1 = exp.substring(0, ocu);
            exp2 = exp.substring(ocu+2,exp.length());
            oplog = "!=";
        }
        RPM rpm = new RPM();
        exp1 = exp1.replaceAll(" ", "");
        resu1 = rpm.resultadoRPM(exp1, this.hash);
        exp2 = exp2.replaceAll(" ", "");
        resu2 = rpm.resultadoRPM(exp2, this.hash);
        
        boolean val = false;
        if (oplog.equals(">=")){
            res1 = String.valueOf(resu1);
            res2 = String.valueOf(resu2);
            val = Integer.valueOf(res1) >= Integer.valueOf(res2);
        }
        else if (oplog.equals("<=")){
            res1 = String.valueOf(resu1);
            res2 = String.valueOf(resu2);
            val = Integer.valueOf(res1) <= Integer.valueOf(res2);
        }
        else if (oplog.equals(">")){
            res1 = String.valueOf(resu1);
            res2 = String.valueOf(resu2);
            val = Integer.valueOf(res1) > Integer.valueOf(res2);
        }
        else if (oplog.equals("<")){
            res1 = String.valueOf(resu1);
            res2 = String.valueOf(resu2);
            val = Integer.valueOf(res1) < Integer.valueOf(res2);
        }
        else if (oplog.equals("==")){
            res1 = String.valueOf(resu1);
            res2 = String.valueOf(resu2);
            val = Integer.valueOf(res1) == Integer.valueOf(res2);
        }
        else if (oplog.equals("!=")){
            res1 = String.valueOf(resu1);
            res2 = String.valueOf(resu2);
            val = Integer.valueOf(res1) != Integer.valueOf(res2);
        }
        
        while(val){
            
            this.leerPrograma(buscaWhile.indexWhile+1, buscaWhile.indexEndwhile);
            exp1 = exp1.replaceAll(" ", "");
            resu1 = rpm.resultadoRPM(exp1, this.hash);
            exp2 = exp2.replaceAll(" ", "");
            resu2 = rpm.resultadoRPM(exp2, this.hash);
            if (oplog.equals(">=")){
                res1 = String.valueOf(resu1);
                res2 = String.valueOf(resu2);
                val = Integer.valueOf(res1) >= Integer.valueOf(res2);
            }
            else if (oplog.equals("<=")){
                res1 = String.valueOf(resu1);
                res2 = String.valueOf(resu2);
                val = Integer.valueOf(res1) <= Integer.valueOf(res2);
            }
            else if (oplog.equals(">")){
                res1 = String.valueOf(resu1);
                res2 = String.valueOf(resu2);
                val = Integer.valueOf(res1) > Integer.valueOf(res2);
            }
            else if (oplog.equals("<")){
                res1 = String.valueOf(resu1);
                res2 = String.valueOf(resu2);
                val = Integer.valueOf(res1) < Integer.valueOf(res2);
            }
            else if (oplog.equals("==")){
                res1 = String.valueOf(resu1);
                res2 = String.valueOf(resu2);
                val = Integer.valueOf(res1) == Integer.valueOf(res2);
            }
            else if (oplog.equals("!=")){
                res1 = String.valueOf(resu1);
                res2 = String.valueOf(resu2);
                val = Integer.valueOf(res1) != Integer.valueOf(res2);
            }
        }
        pcCounter = buscaWhile.indexEndwhile;
       
        
        return true;
    }
    
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
    
    private class BuscaWhile{
        private int indexWhile = -1;
        private int indexEndwhile = -1;

        public int getIndexWhile() {
            return indexWhile;
        }

        public void setIndexWhile(int indexWhile) {
            this.indexWhile = indexWhile;
        }

        public int getIndexEndwhile() {
            return indexEndwhile;
        }

        public void setIndexEndwhile(int indexEndwhile) {
            this.indexEndwhile = indexEndwhile;
        }
        
        
    }
}
