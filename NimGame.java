package JuegoPelotitasAI;

import java.util.Arrays;
import java.util.Scanner;

// Esta clase es un manejador para poder jugar contra la maquina
public class NimGame {
    int[] game;
    int size_game;

    //Constructor vacio
    public NimGame() {

    }
    // Creamos el tablero con las medidas dadas, son n niveles con los primeros n impares
    public void intitializeGame(int size_game) {
        game = new int[size_game];
        this.size_game= size_game;
        int pos =0;

        for(int i =1; i <= size_game*2-1; i+=2){
            game[pos] = i;
            pos++;
        }
    }

    // Este metodo maneja, el caso a jugar, si es humano contra programa o si es programa contra programa
    public void play(Object player1, Object player2){
        if(player1 instanceof Scanner){

            Scanner lector = (Scanner) player1;

            SmartPlayerNBalls ai = (SmartPlayerNBalls) player2;

            System.out.println("El juego inicia así: " + Arrays.toString(game));

            System.out.println("\nQuieres iniciar? [ingresa s para sí]");

            // Si el humano no inicia el juego, ha perdido si n es impar, puede ganar si n es par

            String val = lector.nextLine();

            boolean alguienHaGanado = false;

            boolean jugadaValida = false;

            int[] tirada = new int[size_game];

            if(val.equalsIgnoreCase("s")){

                // No es necesario seguir desplegando si ya está terminado el juego
                while(!alguienHaGanado) {

                    // Lee la tirada del jugador

                    while(!jugadaValida) {
                        for (int i = 0; i < size_game; i++) {
                            int ren = i+1;
                            System.out.println("\nDame la cantidad de pelotas que quedan en el renglon numero: " + ren);
                            tirada[i] = lector.nextInt();
                        }
                        /* Es necesario validar que el jugador humano, no haya añadido pelota, quitado de más de un
                           renglón o en su defecto no haya tirado nada
                         */
                        jugadaValida = checkPlay(tirada, game);

                        if(!jugadaValida){
                            System.out.println("\nLa jugada ingresada no es valida, ingresa una valida por favor");

                            System.out.println("\nRecuerda que una jugada es valida si juegas solo en un renglon y no añades pelotas");

                            System.out.println("\nPD: Tampoco puedes pasar (no tirar nada)");
                        }
                    }
                    game = tirada;

                    System.out.println("\nTu jugada: ");

                    System.out.println(Arrays.toString(tirada));

                    PlayNBalls jugadaARevisar = new PlayNBalls(game);

                    // Revisa si el juego se ha terminado

                    alguienHaGanado = jugadaARevisar.isCrucialPlay();
                    if(!alguienHaGanado) {

                        // Aplica backtracking y minimax
                        game = ai.makeMove(game);

                        System.out.println("\nMy move: ");

                        System.out.println(Arrays.toString(game));

                        jugadaARevisar = new PlayNBalls(game);

                        alguienHaGanado = jugadaARevisar.isCrucialPlay();

                    }
                    jugadaValida = false;


                }
            }else{

                // Código muy similar al de arriba, solo que es el caso donde el humano decide no iniciar el juego

                while(!alguienHaGanado) {
                    game = ai.makeMove(game);

                    System.out.println("\nMi jugada: ");

                    System.out.println(Arrays.toString(game));

                    PlayNBalls jugadaARevisar = new PlayNBalls(game);

                    alguienHaGanado = jugadaARevisar.isCrucialPlay();

                    if(!alguienHaGanado){
                        while(!jugadaValida) {
                            for (int i = 0; i < size_game; i++) {
                                int ren = i +1;
                                System.out.println("\nDame la cantidad de pelotas que quedan en el renglon numero: " + ren);
                                tirada[i] = lector.nextInt();
                            }
                            jugadaValida = checkPlay(tirada, game);
                            if (!jugadaValida) {
                                System.out.println("\nLa jugada ingresada no es valida, ingresa una valida por favor");

                                System.out.println("\nRecuerda que una jugada es valida si juegas solo en un renglon y no añades pelotas");

                                System.out.println("\nPD: Tampoco puedes pasar (no tirar nada)");
                            }

                        }
                        game = tirada;
                        System.out.println("\nTu jugada: ");

                        System.out.println(Arrays.toString(tirada));

                        jugadaARevisar = new PlayNBalls(game);

                        alguienHaGanado = jugadaARevisar.isCrucialPlay();

                        jugadaValida = false;

                    }
                }
            }
        }

        else{
            // Caso de juego entre programa vs programa

            // Construcción de los jugadores inteligentes
            SmartPlayerNBalls ai1 = (SmartPlayerNBalls) player1;
            SmartPlayerNBalls ai2 = (SmartPlayerNBalls) player2;

            boolean alguienHaGanado = false;
            boolean jugador2Gano = false;

            // Imprime lo que va pasando en el juego

            System.out.println("\nCurrent board: ");
            System.out.println(Arrays.toString(game));

            /* En esta parte del código no es necesario que se revise si la jugada es valida, pues no puede tirar
               jugadas no validas
             */

            while(!alguienHaGanado){
                System.out.println("\nPlayer 1 move: ");
                game = ai1.makeMove(game);
                System.out.println(Arrays.toString(game));
                PlayNBalls jugadaARevisar = new PlayNBalls(game);
                alguienHaGanado = jugadaARevisar.isCrucialPlay();

                if(!alguienHaGanado){
                    System.out.println("\nPlayer 2 move: ");
                    game = ai2.makeMove(game);
                    System.out.println(Arrays.toString(game));
                    jugadaARevisar = new PlayNBalls(game);
                    alguienHaGanado = jugadaARevisar.isCrucialPlay();
                    jugador2Gano = alguienHaGanado;
                }
                else{
                    System.out.println("\nPlayer 1 won");
                }
            }

            if(jugador2Gano)
                System.out.println("\nPlayer 2 won");
        }
    }

    /* Método que revisa si una jugada es valida, que no se agreguen pelotas, que se quite al menos una y que no se
       quiten pelotas de multiples renglones
     */

    public boolean checkPlay(int[] tirada, int[] stategame) {
        int count_rens_changed = 0;
        int pos =0;
        int ren_changed = 0;
        while(pos < tirada.length && count_rens_changed <= 1){
            if(tirada[pos] != stategame[pos]) {
                count_rens_changed += 1;
                ren_changed = pos;
            }

            pos++;
        }
        if(!(count_rens_changed == 1) || tirada[ren_changed] > stategame[ren_changed])
            return false;
        return true;
    }
}
