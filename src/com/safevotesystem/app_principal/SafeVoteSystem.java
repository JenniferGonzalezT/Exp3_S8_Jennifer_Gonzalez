/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package com.safevotesystem.app_principal;

import com.safevotesystem.archivos.Archivos;
import com.safevotesystem.hilos.PrimesThread;
import com.safevotesystem.logica.*;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * Sistema de votación electrónica seguro.
 * @author jennifer
 */

public class SafeVoteSystem {
    private static final String ARCHIVO_NUMEROS_CSV = "NumerosPrimos.csv";
    private static final String ARCHIVO_MENSAJES_TXT = "Mensajes.txt";

    public static void main(String[] args) {
        System.out.println("El programa ha sido iniciado.");
        
        // Crear la colección sincronizada (cola)
        BlockingQueue<Integer> cola = new LinkedBlockingQueue<>();
        
        // Instanciar las clases
        PrimesList primesList = new PrimesList();
        TopicNumeros topic = new TopicNumeros();
        Coordinador coordinador = new Coordinador();
        Archivos archivos = new Archivos(primesList, cola, coordinador);
        
        // Cargar números desde el archivo
        System.out.println("\nCargando archivo...");
        archivos.cargarNumerosCSV(ARCHIVO_NUMEROS_CSV);
        
        // Crear los hilos
        Thread thread1 = new Thread(new PrimesThread(primesList, cola, topic, coordinador), "Hilo 1");
        Thread thread2 = new Thread(new PrimesThread(primesList, cola, topic, coordinador), "Hilo 2");
        Thread thread3 = new Thread(new PrimesThread(primesList, cola, topic, coordinador), "Hilo 3");
        Thread thread4 = new Thread(new PrimesThread(primesList, cola, topic, coordinador), "Hilo 4");
        
        // Iniciar los hilos
        System.out.println("\nLos hilos comienzan su ejecución.");
        thread1.start();
        thread2.start();
        thread3.start();
        thread4.start();

        // Esperar a que cada hilo termine
        try {
            thread1.join();
            thread2.join();
            thread3.join();
            thread4.join();
        } catch (InterruptedException e) {
            System.out.println(Thread.currentThread().getName() + " fue interrumpido: " + e.getMessage());
            
            // Restablecer el hilo
            Thread.currentThread().interrupt();
        }
        System.out.println("\nTodos los hilos han terminado su ejecución.");
        
        // Mostrar la cantidad de primos en la lista
        System.out.println("\nCantidad de números primos en la lista: " + primesList.getPrimesCount());
        System.out.println("Números primos en la lista: " + primesList);
        
        // Escribir mensajes en archivo
        archivos.escribirMensajes(ARCHIVO_MENSAJES_TXT);
    }
    
}
