/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Objetos;

import java.util.ArrayList;

/**
 *
 * @author emili
 */
public class Analizador {

    private String cadenaEntrada;
    private ArrayList<String> enteros;
    private ArrayList<String> decimales;
    private ArrayList<String> identificadores;
    private ArrayList<String> simbolos;
    private ArrayList<String> errores;
    private ArrayList<String> partes;
    private String informacion;

    public Analizador() {
    }

    public Analizador(String cadenaEntrada) {
        this.enteros = new ArrayList<>();
        this.decimales = new ArrayList<>();
        this.identificadores = new ArrayList<>();
        this.simbolos = new ArrayList<>();
        this.errores = new ArrayList<>();
        this.partes = new ArrayList<>();
        this.cadenaEntrada = cadenaEntrada;
        this.informacion = "";
        analizar(this.cadenaEntrada);
    }

    private void analizar(String cadenaEntrada) {
        char[] arreglo = cadenaEntrada.toCharArray();

        for (int i = 0; i < cadenaEntrada.length(); i++) {
            if (arreglo[i] == '\n') {
                arreglo[i] = ' ';
            }
        }
        System.out.println(String.valueOf(arreglo));
        String cadena = String.valueOf(arreglo);
        System.out.println("cadena original: " + cadena);
        String abecedario = "abcdefghijklmnopqrstuvwxyzñ";
        String digitos = "1234567890";
        String parte = "";
        int ultimaParte = 0;
        Posicion posicion = null;
        for (int i = 0; i < cadena.length(); i++) {
            System.out.println("carater --> " + cadena.charAt(i));
            if (!esEspacio(String.valueOf(cadena.charAt(i)))) {
                parte += cadena.charAt(i);
            } else if (((cadena.charAt(i) == '\n'))
                    || esEspacio(String.valueOf(cadena.charAt(i)))) {
                posicion = new Posicion(i);
                partes.add(parte);
                parte = "";
                continue;
            }
        }
        if (posicion != null) {
            String ultimaCadena = cadena.substring(posicion.getPosicon() + 1, cadena.length());
            System.out.println("ultima cadena -- > " + ultimaCadena);
            partes.add(ultimaCadena);
        }
        if (this.contieneSoloUnaCadena(cadena)) {
            partes.add(cadena);
        }
        for (String parteCadena : partes) {
            System.out.println("cadena -> " + parteCadena);
            if (esEntero(parteCadena, digitos)) {
                this.enteros.add(parteCadena);
            }
            if (esDecimal(parteCadena)) {
                this.decimales.add(parteCadena);
            }
            if (esIdentificador(parteCadena, abecedario, digitos) || purasLetras(parteCadena, abecedario)) {
                this.identificadores.add(parteCadena);
            }
            if (todosSimbolos(parteCadena, listadoSimbolos())) {
                for (int i = 0; i < parteCadena.length(); i++) {
                    this.simbolos.add(String.valueOf(parteCadena.charAt(i)));
                }
            }
            if (!esEntero(parteCadena, digitos)
                    && (!esDecimal(parteCadena))
                    && !esIdentificador(parteCadena, abecedario, digitos)
                    && (!todosSimbolos(parteCadena, listadoSimbolos()))
                    && (!purasLetras(parteCadena, abecedario))) {
                this.errores.add(parteCadena);
            }
        }
        System.out.println("");
        System.out.println("lista enteros:");
        this.informacion += "NUMEROS ENTEROS:\n";
        for (String parte1 : enteros) {
            this.informacion += parte1 + "\n";
            System.out.println(parte1);
        }
        System.out.println("lista decimales:\n");
        this.informacion += "NUMEROS DECIMALES\n";
        for (String parte1 : decimales) {
            this.informacion += parte1 + "\n";
            System.out.println(parte1);
        }
        this.informacion += "LISTA IDENTIFICADORES:\n";
        System.out.println("LISTA identificadores");
        for (String identificadore : identificadores) {
            this.informacion += identificadore + "\n";
            System.out.println(identificadore);
        }
        this.informacion += "LISTA DE SIMBOLOS\n";
        System.out.println("Lista de simbolos");
        for (String simbolo : simbolos) {
            this.informacion += simbolo + "\n";
            System.out.println(simbolo);
        }
        this.informacion += "ERRORES:\n";
        System.out.println("listado de errores");
        for (String errore : errores) {
            this.informacion += errore + "\n";
            System.out.println(errore);
        }
    }

    public String getInformacion() {
        return informacion;
    }

    public boolean esEntero(String cadena, String digitos) {
        int cantidadDigitos = 0;
        for (int i = 0; i < cadena.length(); i++) {
            for (int j = 0; j < digitos.length(); j++) {
                if (cadena.charAt(i) == digitos.charAt(j)) {
                    cantidadDigitos++;
                }
            }
        }
        return (cantidadDigitos == cadena.length());
    }

    public boolean esDecimal(String cadena) {
        try {
            Double.parseDouble(cadena);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    public boolean puntoPorEnMedio(String cadena) {
        return (!".".equals(cadena.substring(0, 1)))
                && (!".".equals(cadena.substring(cadena.length() - 1, cadena.length())));
    }

    public boolean esIdentificador(String cadena, String abecedario, String digitos) {
        int contadorId = 0;
        for (int i = 0; i < cadena.length(); i++) {
            if (esEntero(String.valueOf(cadena.charAt(i)), digitos)
                    || esLetra(String.valueOf(cadena.charAt(i)), abecedario)) {
                contadorId++;
            }
        }
        return ((contadorId == cadena.length() - 2)
                && (esLetra(String.valueOf(cadena.substring(0, 1)), abecedario)));
    }

    public boolean esLetra(String caracter, String abecedario) {
        for (int i = 0; i < abecedario.length(); i++) {
            if (caracter.equalsIgnoreCase(String.valueOf(abecedario.charAt(i)))) {
                return true;
            }
        }
        return false;
    }

    public boolean purasLetras(String cadena, String abecedario) {
        int contador = 0;
        for (int i = 0; i < cadena.length(); i++) {
            for (int j = 0; j < abecedario.length(); j++) {
                if (String.valueOf(cadena.charAt(i)).
                        equalsIgnoreCase(String.valueOf(abecedario.charAt(j)))) {
                    contador++;
                }
            }
        }
        return (contador == cadena.length());
    }

    public boolean esEspacio(String cadena) {
        return (cadena.equals(" "));
    }

    public String listadoSimbolos() {
        String simbolos = "";

        int n2 = 47;
        int n3 = 58;
        int n4 = 63;
        for (int i = 33; i < (n2 + 1); i++) {
            simbolos += ((char) i);
        }

        for (int i = n3; i < (n4 + 1); i++) {
            simbolos += ((char) i);
        }
        for (int i = 91; i < (94 + 1); i++) {
            simbolos += ((char) i);
        }
        for (int i = 123; i < (126 + 1); i++) {
            simbolos += ((char) i);
        }
        return simbolos;
    }

    public boolean todosSimbolos(String cadena, String simbolos) {
        int contadorSimbolos = 0;
        for (int i = 0; i < cadena.length(); i++) {
            for (int j = 0; j < simbolos.length(); j++) {
                if (cadena.charAt(i) == simbolos.charAt(j)) {
                    contadorSimbolos++;
                }
            }
        }
        return (contadorSimbolos == cadena.length());
    }

    public boolean contieneSoloUnaCadena(String cadena) {
        return (!cadena.contains(" ") || !cadena.contains("\n"));
    }

    public ArrayList<String> getEnteros() {
        return enteros;
    }

    public ArrayList<String> getDecimales() {
        return decimales;
    }

    public ArrayList<String> getIdentificadores() {
        return identificadores;
    }

    public ArrayList<String> getErrores() {
        return errores;
    }

    public ArrayList<String> getPartes() {
        return partes;
    }

    public ArrayList<String> getSimbolos() {
        return simbolos;
    }

}
