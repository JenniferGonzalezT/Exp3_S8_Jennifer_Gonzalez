/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.safevotesystem.hilos;

import com.safevotesystem.logica.*;

import java.util.concurrent.BlockingQueue;

/**
 * Clase PrimesThread que implementa Runnable para gestionar de forma
 * concurrente la verificación y adición de números primos a una lista.
 * @author jennifer
 */

public class PrimesThread implements Runnable {
    private final PrimesList primesList;
    private final BlockingQueue<Integer> cola;
    private final TopicNumeros topic;
    private final Coordinador coordinador;

    public PrimesThread(PrimesList primesList, BlockingQueue<Integer> cola, TopicNumeros topic, Coordinador coordinador) {
        this.primesList = primesList;
        this.cola = cola;
        this.topic = topic;
        this.coordinador = coordinador;
    }

    @Override
    public void run() {
        try {
            // Los hilos esperan a que termine la carga de archivos.
            coordinador.esperarCarga();
            
            
            // Agregar los números del archivo desde la cola, verificando si son primos.
            while (true) {
                Integer numero = cola.poll();
                
                if (numero == null) {
                    break;
                }
                
                if (primesList.isPrime(numero)) {
                    try {
                        primesList.add(numero);
                        System.out.println(Thread.currentThread().getName() + " agregó desde la Cola: " + numero);
                    } catch (IllegalArgumentException e) {
                        System.out.println(Thread.currentThread().getName() + " intentó agregar desde la Cola un número no primo: " + numero);
                    }
                } else {
                    System.out.println(Thread.currentThread().getName() + " descartó desde la Cola: " + numero);
                }
            }
            System.out.println(Thread.currentThread().getName() + " no encontró más elementos en la cola.");
            
            
            // Generar 10 números aleatorios, publicarlos y suscribirlos en el Topic.
            for (int i = 0; i < 10; i++) {
                // Generar un número aleatorio
                int numero = (int) (Math.random() * 1000);
                
                // Agregarlo al topic
                topic.publicar(numero);
                System.out.println(Thread.currentThread().getName() + " publicó en Topic: " + numero);
            }
            
            for (int i = 0; i < 10; i++) {
                int numero = topic.suscribir();
                if (primesList.isPrime(numero)) {
                    try {
                        primesList.add(numero);
                        System.out.println(Thread.currentThread().getName() + " agregó desde Topic: " + numero);
                    } catch (IllegalArgumentException e) {
                        System.out.println(Thread.currentThread().getName() + " intentó agregar desde Topic un número no primo: " + numero);
                    }
                } else {
                    System.out.println(Thread.currentThread().getName() + " descartó desde Topic: " + numero);
                }
            }
        } catch (InterruptedException e) {
            System.out.println(Thread.currentThread().getName() + " fue interrumpido.");
            // Restablecer el hilo
            Thread.currentThread().interrupt();
        }
    }

}
