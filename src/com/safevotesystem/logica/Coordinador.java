/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.safevotesystem.logica;

/**
 * Clase Coordinador que sincroniza los hilos con la carga de números desde el archivo.
 * @author jennifer
 */

public class Coordinador {
    private boolean archivoCargado = false;

    // Método que espera que a que se carguen los números desde el archivo.
    public synchronized void esperarCarga() throws InterruptedException {
        while (!archivoCargado) {
            System.out.println(Thread.currentThread().getName() + " esperando la carga de archivos.");
            wait();
        }
    }

    // Método que notifica a los hilos que estan en espera que la carga esta lista.
    public synchronized void cargaLista() {
        archivoCargado = true;
        notifyAll();
    }
    
}
