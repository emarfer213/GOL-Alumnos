package com.miravent;

import java.util.Random;

public class Life {

    public static boolean[][] grid;
    public static int population;
    public static int generation;

    public static void inicializa(int numeroCeldas1, int numeroCeldas2) {
        grid = new boolean[numeroCeldas1][numeroCeldas2];
        fillRandom();
    }

    public static void fillRandom() {

        for (int i = 0; i < Life.grid.length; i++) {
            for (int j = 0; j < Life.grid.length; j++) {
                Random random = new Random();
                grid[i][j] = random.nextBoolean();
                if (grid[i][j]) {
                    population++;
                }
            }
        }
    }

    public static int contarVecinos(int i, int j) {
        int numeroVecinos = 0;
        for (int l = i - 1; l <= i + 1; l++) {
            for (int k = j - 1; k <= j + 1; k++) {
                if (l >= 0 && l < grid.length && k >= 0 && k < grid.length) {
                    if (grid[l][k]) {
                        numeroVecinos++;
                    }
                }
            }
        }
        return numeroVecinos;
    }

    public static void siguienteGeneracion() {
        boolean[][] newGrid = new boolean[grid.length][grid[0].length];

        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid.length; j++) {

                if (grid[i][j]) {
                    if (contarVecinos(i, j) == 2 || contarVecinos(i, j) == 3) {
                        newGrid[i][j] = true;
                    } else {
                        newGrid[i][j] = false;
                        population--;
                    }
                } else {
                    if (contarVecinos(i, j) == 3) {
                        newGrid[i][j] = true;
                        population++;
                    } else {
                        newGrid[i][j] = false;
                    }
                }
            }
        }
        generation++;
        grid = newGrid;
    }

    public static void init() {
        fillRandom();
        population = 0;
        generation = 0;
    }

    public static void tick() {
        siguienteGeneracion();
    }

}