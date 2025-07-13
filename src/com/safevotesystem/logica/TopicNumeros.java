/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.safevotesystem.logica;

import java.util.LinkedList;
import java.util.Queue;

/**
 * Clase Topic que publica mensajes de los números generados aleatoriamente
 * suscribiendo a los hilos.
 * @author jennifer
 */

public class TopicNumeros {
    private final Queue<Integer> mensajes = new LinkedList<>();
    
    // Método que agrega un número a la cola y notifica a los hilos.
    public synchronized void publicar(int numero) {
        mensajes.offer(numero);
        notifyAll();
    }
    
    // Método que espera a que haya un número disponible y devuelve el primero en la cola.
    public synchronized Integer suscribir() throws InterruptedException {
        while (mensajes.isEmpty()) {
            wait();
        }
        return mensajes.poll();
    }
}
