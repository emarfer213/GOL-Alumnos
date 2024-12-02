package com.miravent;

import java.util.Random;

public class Life {

    /*establecemos el valor booleano que definira el estado de las celdas, vivas(true) o muertas(false)*/
    private static boolean[][] grid;

    /*creamos dos int que definiran el numero de poblacion que hay
    y la generacion en la que nos encontramos*/
    private static int population;
    private static int generation;

    /*ahora crearemos un metodo que inicializara la matriz segun el tamaño que tenga*/
    public static void inicializa(int numeroCeldas1, int numeroCeldas2) {

        /*estableceremo aqui el tamaño de las filas y las columnas de grit*/
        grid = new boolean[numeroCeldas1][numeroCeldas2];

        /*llamamos a la funcion "fillRandom" la cual determinara de
        forma aleatoria que posicion esta "viva" o "muerta"*/
        fillRandom();
    }

    /*crearemos esta funcion que se encargara de recorrer cada posicion
    del tablero y le otorgara el valor de "vivo" o "muerto"*/
    public static void fillRandom() {

        /*crearemos un for dentro de otro, para recorrer todas las posiciones,
        en cada posicion se cambiara su valor aleatoriamente a un valor true(vivo) o false(muerto)*/
        for (int i = 0; i < Life.grid.length; i++) {
            for (int j = 0; j < Life.grid.length; j++) {
                Random random = new Random();

                /*aqui indicamos que la posicion concreta en la que se encuentran ambos bucles
                sera igual al valor determinado aleatoriamente*/
                grid[i][j] = random.nextBoolean();

                /*en el caso de que el valor dado a la posicion en la que se encuentran
                ambos bucles sea verdadera, se sumara uno al valor de "population"*/
                if (grid[i][j]) {
                    population++;
                }
            }
        }
    }

    /*crearemos una funcion para contar la cantidad de vecinos adyacentes de cada casilla,
    para saber que sucedera con esa casilla en la siguiente generacion*/
    public static int contarVecinos(int i, int j) {

        /*estableceremos un int que nos ira contando la cantidad de vecinos adyacentes en cada casilla*/
        int numeroVecinos = 0;

        /*crearemos dos bucles para recorrer las casillas adyacentes de cada casilla del tablero*/
        for (int l = i - 1; l <= i + 1; l++) {
            for (int k = j - 1; k <= j + 1; k++) {

                /*crearemos un if para asegurarnos de que los dos bucles
                permanezcan dentro de la longitud de la matriz*/
                if (l >= 0 && l < grid.length && k >= 0 && k < grid.length) {

                    /*este if sera para determinar que si la posicion recorrida
                    tiene un valor de verdadero sumara uno al numero de vecinos*/
                    if (grid[l][k] && (l != i || k != j)) {
                        numeroVecinos++;
                    }
                }
            }
        }
        return numeroVecinos;
    }

    /*creamos esta funcion para calcular y dibujar como avanza y evoluciona cada generacion,
    sumando uno a la poblacion o restando uno cada vez que una casilla muera o reviva*/
    public static void siguienteGeneracion() {

        /*establecemos una nueva matriz la cual sera el proximo tablero con sus modificaciones pertinentes*/
        boolean[][] newGrid = new boolean[grid.length][grid[0].length];

        /*crearemos dos bucles que recorreran cada casilla para comprobar si deberia revicir,
        matar o dejar igual a cada casilla, esto simplemente establecera si se debe sumar o restar
        a la poblacion y cambiar el valor de la casilla de verdadero a falso o vicebersa*/
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid.length; j++) {

                /*en el caso de que la la posicion actual sea verdadera se comprobara
                cuantos vecinos tiene adyacente, si son 2 o 3 vecinos la casilla se mantendra verdadera,
                si tiene menos de 2 o mas de 3, la casilla se volvera falsa*/
                if (grid[i][j]) {
                    if (contarVecinos(i, j) == 2 || contarVecinos(i, j) == 3) {
                        newGrid[i][j] = true;

                    } else {
                        newGrid[i][j] = false;

                        /*si la poblacion antes era verdadera y ahora se ha vuelto falsa perdera 1 de poblacion*/
                        population--;
                    }

                    /*en el caso de que la posicion sea falsa, se mantendra falsa
                    a no ser que el numero de vecinos sean 3, en ese caso la casilla
                    se volvera verdadera y se sumara 1 a la poblacion*/
                } else {
                    if (contarVecinos(i, j) == 3) {
                        newGrid[i][j] = true;
                        population++;
                    } else {
                        newGrid[i][j] = false;

                        /*creamos una variable la cual usaremos para crear la probabilidad de que nuestra casilla muerta nos reviva
                         * y le ponemos un math.random con un rango de mil*/
                        int revivir = (int) (Math.random() * 1000) + 1;

                        /*crearemos un if para hacer que si el valor es de 1 a 5 la casilla revivira y se sumara a la poblacion*/
                        if (revivir <= 5 && revivir >= 1) {
                            newGrid[i][j] = true;
                            population++;

                            /*creamos dos bucles en los cuales comprobaremos las casillas adyacentes de cada casilla revivida*/
                            for (int l = i - 1; l <= i + 1; l++) {
                                for (int k = j - 1; k <= j + 1; k++) {

                                    /*creamos un if para establecer que no se pueda salir del tamaño de nuestro grid*/
                                    if (l >= 0 && l < grid.length && k >= 0 && k < grid.length) {
                                        /*crearemos una nueva variable que comprobara si las casillas adyacentes revivira o no,
                                    y le asignaremos un valor aleatorio entre 1 y 4*/
                                        int revivirAdyacente = (int) (Math.random() * 4) + 1;

                                        /*si el valor es 1 la casilla adyacente revivira y le sumaremos 1 a la poblacion*/
                                        if (revivirAdyacente == 2) {
                                            newGrid[l][k] = true;
                                            population++;
                                        }
                                    }
                                }
                            }
                        }

                    }
                }
            }
        }

        /*una vez que se terminen los bucles se sumara 1 al valor de la generacion
        y se igualara la matriz anterior con la nueva*/
        generation++;
        grid = newGrid;
    }

    /*esta funcion simplemente hara que una vez se pulse el boton init,
    los valores de population, generation y el tablero se reiniciaran*/
    public static void init() {
        population = 0;
        generation = 0;
        fillRandom();

    }

    /*esta funcion llamara una unica vez para mostrar el cambio dado en el transcurso de una sola generacion*/
    public static void tick() {
        siguienteGeneracion();
    }

    /*creamos esta funcion la cual llama al valor "grid",
    ya que es un valor privado necesitamos llamarlo mediante un metodo public,
    para que AppStatic pueda tomar el valor sin acceder directemente a la variable*/
    public static boolean[][] getGrid() {
        return grid;
    }

    /*creamos esta funcion para que AppStatic pueda acceder al valor population
    sin acceder directamente a la variable*/
    public static int getPopulation() {
        return population;
    }

    /*creamos esta funcion para que AppStatic pueda acceder al valor generation
    sin tener que acceder directamente a la variable ya que es privada y esta funcion es publica*/
    public static int getGeneration() {
        return generation;
    }

}