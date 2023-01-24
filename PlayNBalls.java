package JuegoPelotitasAI;



public class PlayNBalls {
    // Las jugadas o plays son un arreglo de n entradas, donde en la posición i hay las m pelotitas que queden en el i renglón
    int[] play ;
    int winorlose = 0; // mayor a cero si es una jugada ganadora menor a cero si es una jugada perdedora
    int jugadasToGetToMe = 0; // Distancia que se calcula desde llamadas recursivas, profundidad del árbol para llegar a esa jugada
    public PlayNBalls() {

    }
    // Constructor con el estado de juego
    public PlayNBalls(int [] arre) {
       play = arre;
    }

    // Regresa si la jugada es ganadora o perdedora
    public int getWinorlose() {
        return winorlose;
    }


    public int compareTo(PlayNBalls other) {

        if(winorlose*other.getWinorlose()<0) {
            if(winorlose < 0)
                return -1;
            else
                return 1;
        }
        else{
            if(winorlose < 0)
                return 0;// tengo el caso de dos perdedores
        }
        return 0;
    }
    // Método que compara dos jugadas desde la perspectiva de maximizar

    /* El porqué de este método es que si el maximizador recibe dos jugadas perdedoras y debe decidir entre ellas,
       elija la jugada más lejana y si recibe dos jugadas ganadoras elija la jugada más corta para ganar
     */

    public int compareToFromMax(PlayNBalls other) {
        // Dos jugadas, si la jugada que comparo es ganadora regresa 1 de lo contrario regresa -1
        if(winorlose*other.getWinorlose()<0){
            if(winorlose<0)
                return -1;
            else
                return 1;

        }

        else{
            // tengo dos perdedores
            if(winorlose<0){
                // Elijo la jugada más larga
                if(this.jugadasToGetToMe > other.jugadasToGetToMe)
                    return 1;
                else
                    return -1;

            }
            // tengo dos ganadores
            else{
                // Elijo la jugada más corta
                if(this.jugadasToGetToMe < other.jugadasToGetToMe)
                    return 1;
                else
                    return -1;
            }
        }
    }

    // Método que compara dos jugadas desde la perspectiva de minimizar

    /* El porqué de este método es que si el minimizador recibe dos jugadas perdedoras y debe decidir entre ellas,
       elija la jugada más cercana y si recibe dos jugadas ganadoras elija la jugada más larga para ganar
     */

    public int compareToFromMin(PlayNBalls other) {
        // Dos jugadas, si la jugada que comparo es ganadora regresa 1 de lo contrario regresa -1
        if(winorlose*other.getWinorlose()<0){
            if(winorlose<0)
                return -1;
            else
                return 1;

        }

        else{
            // tengo dos perdedores
            if(winorlose<0){
                // Elijo la jugada más corta
                if(this.jugadasToGetToMe < other.jugadasToGetToMe)
                    return -1;
                else
                    return 1;

            }
            // tengo dos ganadores
            else{
                // Elijo la jugada más larga
                if(this.jugadasToGetToMe > other.jugadasToGetToMe)
                    return -1;
                else
                    return 1;
            }
        }
    }
    // Una jugada crucial la definimos como aquella donde el que haya recibido la última pelotita perdió, es decir endgame
    public boolean isCrucialPlay() {
        int suma = 0;
        int pos =0;
        // Revisar si queda más de una pelotita en el juego
        while(pos < play.length && suma <= 2){
            suma += play[pos];
            pos++;
        }

        return suma == 1;
    }

    // Regresa la distancia (cuantas jugadas faltan) para llegar a esta jugada
    public int getJugadasToGetToMe() {
        return jugadasToGetToMe;
    }
    // Ajusta la distancia para que esta jugada ocurra
    public void setJugadasToGetToMe(int jugadasToGetToMe) {
        this.jugadasToGetToMe = jugadasToGetToMe;
    }
    /* Dos jugadas son iguales si para cada renglón de pelotitas en la jugada 1 existe otro renglón de pelotitas en
       la jugada 2 que tiene la misma cantidad de pelotitas
     */
    public boolean equals(PlayNBalls other) {
        boolean resp = true;
        // Hacemos copias de las jugadas para luego comparar
        Integer[] copia = new Integer[play.length];
        for(int i =0; i< play.length; i++){
            copia[i] = play[i];
        }

        Integer[] copiaotro = new Integer[other.play.length];
        for(int j  =0; j < other.play.length; j++){
            copiaotro[j] = other.play[j];
        }
        // Ordenamos las copias usando mergeSort, para poder comparar entrada por entrada
        int i = 0;
        mergeSort(copia);
        mergeSort(copiaotro);
        // Revisamos que entrada por entrada las jugadas sean iguales
        while(i < play.length && resp){
            resp = copia[i]== copiaotro[i];
            i++;
        }


        return resp;
    }


    // Determinar si una jugada es ganadora o perdedora solo puede ser determinado externamente a la clase

    public void setWinorlose(int winorlose) {
        this.winorlose = winorlose;
    }

    // Una jugada es invalida si no quedan pelotitas en el juego
    public boolean playInvalid() {
        int suma = 0;
        for(int i =0; i< play.length; i++)
            suma+= play[i];
        return suma==0;
    }

    // Los siguientes tres métodos son una implementación de mergeSort
    private static <T extends Comparable<T>> void mergeSort(T arre[]) {
        if(arre.length > 0)
            mergeSort(arre, 1);

    }


    private static <T extends Comparable<T>> void mergeSort(T arre[], int tamanio_particion){
        if(tamanio_particion <= arre.length) {
            int partir_cada = tamanio_particion;
            int ultimo_arre_menor = tamanio_particion - 1;
            int primero_arre_menor = 0;
            while (primero_arre_menor < arre.length) {

                mergeSort(arre, primero_arre_menor, ultimo_arre_menor, ultimo_arre_menor + 1, ultimo_arre_menor + partir_cada);

                primero_arre_menor = ultimo_arre_menor + partir_cada + 1;


                ultimo_arre_menor = primero_arre_menor + partir_cada-1;


            }





            mergeSort(arre, tamanio_particion * 2);
        }
    }

    private static <T extends Comparable<T>> void mergeSort(T arre[], int primero_menor, int ultimo_menor, int primero_mayor, int ultimo_mayor){
        int pm = primero_menor;
        int pM = primero_mayor;
        int posC = 0;

        if(ultimo_mayor >= arre.length)
            ultimo_mayor = arre.length-1;
        if(ultimo_menor >= arre.length)
            ultimo_menor = arre.length -1;



        Object[] copia = new Object[(ultimo_mayor - primero_menor) +1 ];
        while(pm <= ultimo_menor && pM <= ultimo_mayor){
            if(arre[pm].compareTo(arre[pM]) <= 0){
                copia[posC] = arre[pm];
                pm += 1;
            }
            else {
                copia[posC] = arre[pM];
                pM += 1;
            }

            posC += 1;
        }

        if(pm <= ultimo_menor || pM <= ultimo_mayor){
            while(pm<= ultimo_menor){
                copia[posC] = arre[pm];
                posC += 1;
                pm++;

            }

            while(pM <= ultimo_mayor){
                copia[posC] = arre[pM];
                posC+=1;
                pM ++;
            }
        }


        for(int i = primero_menor; i<= ultimo_mayor; i++)
            arre[i] = (T) copia[i-primero_menor];



    }






}
