/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.safevotesystem.archivos;

import com.safevotesystem.logica.*;

import java.io.*;
import java.util.concurrent.BlockingQueue;

/**
 * Clase para gestionar archivos.
 * @author jennifer
 */

public class Archivos {
    private final PrimesList primesList;
    private final BlockingQueue<Integer> cola;
    private final Coordinador coordinador;

    public Archivos(PrimesList primesList, BlockingQueue<Integer> cola, Coordinador coordinador) {
        this.primesList = primesList;
        this.cola = cola;
        this.coordinador = coordinador;
    }

    // Método para cargar números primos desde un archivo CSV y agregarlos a la cola.
    public void cargarNumerosCSV(String archivo) {
        try (BufferedReader lector = new BufferedReader(new FileReader(archivo))) {
            String linea;
            
            while ((linea = lector.readLine()) != null) {
                String[] numeros = linea.split(",");
                
                for (String numero : numeros) {
                    try {
                        int num = Integer.parseInt(numero.trim());
                        cola.put(num);
                        System.out.println("Número agregado desde el archivo a la cola: " + num);
                    } catch (NumberFormatException e) {
                        System.out.println("El dato \"" + numero + "\" no es un número entero válido. No se agregará a la lista.");
                    } catch (InterruptedException e) {
                        System.out.println("Interrupción al agregar un número a la cola: " + e.getMessage());
                        Thread.currentThread().interrupt();
                    }
                }
            }
            System.out.println("Números agregados correctamente desde el archivo a la cola.");
            coordinador.cargaLista();
        } catch (IOException e) {
            System.out.println("Error al cargar los números primos desde el archivo: " + e.getMessage());
        }
    }
    
    // Método para escribir mensajes encriptados y sus "Códigos Primos" en archivo TXT.
    public void escribirMensajes(String archivo) {
        try (BufferedWriter escritor = new BufferedWriter(new FileWriter(archivo))) {
            int contador = 1;
            
            for (Integer numero : primesList) {
                String mensaje = "Mensaje_" + contador;
                String linea = mensaje + " : Código Primo = " + numero;
                escritor.write(linea);
                escritor.newLine();
                contador++;
            }
            System.out.println("Mensajes escritos correctamente en el archivo.");
        } catch (IOException e) {
            System.out.println("Error al escribir los mensajes en el archivo: " + e.getMessage());
        }
    }
    
}
