/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.safevotesystem.logica;

import java.util.ArrayList;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Clase que representa la lista de números primos.
 * @author jennifer
 */

public class PrimesList extends ArrayList<Integer> {
    private final Lock lock = new ReentrantLock();

    // Método isPrime para verificar si un número es primo.
    public boolean isPrime(int numero) {
        lock.lock();
        try {
            if (numero <= 1) {
                return false;
            }

            for (int i = 2; i * i <= numero; i++) {
                if (numero % i == 0) {
                    return false;
                }
            }
            return true;
        } finally {
            lock.unlock();
        }
    }

    // Sobreescritura del método add() con Lock.
    @Override
    public boolean add(Integer numero) {
        lock.lock();
        try {
            if (numero == null) {
                throw new IllegalArgumentException("Número inválido: No se puede agregar un valor nulo.");
            }

            if (isPrime(numero)) {
                return super.add(numero);
            } else {
                throw new IllegalArgumentException("Número inválido: El número no es primo.");
            }
        } finally {
            lock.unlock();
        }
    }

    // Sobreescritura del método remove() con Lock.
    @Override
    public boolean remove(Object o) {
        lock.lock();
        try {
            return super.remove(o);
        } finally {
            lock.unlock();
        }
    }

    // Método getPrimesCount con Lock que devuelve la cantidad de números primos en la lista.
    public int getPrimesCount() {
        lock.lock();
        try {
            return this.size();
        } finally {
            lock.unlock();
        }
    }
    
}
