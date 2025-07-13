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
    private boolean errorCarga = false;

    // Método que espera que a que se carguen los números desde el archivo.
    public synchronized void esperarCarga() throws InterruptedException {
        while (!archivoCargado && !errorCarga) {
            System.out.println(Thread.currentThread().getName() + " esperando la carga de archivos.");
            wait();
        }
        
        if (errorCarga) {
            throw new IllegalStateException(Thread.currentThread().getName() + " no ejecutará esta tarea por error en la carga.");
        }
    }

    // Método que notifica a los hilos que están en espera que la carga está lista.
    public synchronized void cargaLista() {
        archivoCargado = true;
        notifyAll();
    }
    
    // Método que notifica a los hilos si hubo un error en la carga.
    public synchronized void errorEnCarga() {
        errorCarga = true;
        notifyAll();
    }
    
    public synchronized boolean estadoErrorCarga() {
        return errorCarga;
    }
    
}
