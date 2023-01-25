public class SmartPlayerNBalls {



    public SmartPlayerNBalls() {

    }
    // Método sobrecargado que lo que recibe, es el estado del juego y llama a la funcion makeMove con el estado del juego
    // y obteniendo la jugada donde maximiza sus ganancias

    public int[] makeMove(int [] juego) {
        return makeMove(new PlayNBalls(juego), true).play;
    }


    // Método que aplica Minimax con un poco de optimización alfa-beta
    /* Recibe como parametro una clase jugada que es el estado del juego para tomar decisiones y un booleano indicando
       si es maximizador o minimizador
     */
    private PlayNBalls makeMove(PlayNBalls jugada, boolean maximizerOrMinimizer) {

        // Validar que se haya recibido una jugada válida, de lo contrario no se pueden hacer los procesos siguientes
        if(jugada.playInvalid()){
            return null;
        }

        // Detectar si la jugada es donde se pierde o se gana, es decir solo queda una pelotita
        if(jugada.isCrucialPlay()){
            // Si el jugador es maximizador y recibe una pelotita ha perdido y ese camino es perdedor
            if(maximizerOrMinimizer){
                jugada.setWinorlose(-1);
                jugada.setJugadasToGetToMe(jugada.getJugadasToGetToMe()+1);
                return jugada;
            }
            // Si el jugador es minimizador y recibe una pelotita ha ganado y ese camino es ganador
            else{
                jugada.setWinorlose(1);
                jugada.setJugadasToGetToMe(jugada.getJugadasToGetToMe()+1);
                return jugada;
            }

        }
        int size_plays = 0;
        for(int i =0; i< jugada.play.length; i++){
            size_plays += jugada.play[i]; // suma el total para iniciar los arreglos
        }
        PlayNBalls[] playsMade = new PlayNBalls[size_plays]; // este arreglo va a ir llevando todas los posibles subconjuntos
        PlayNBalls[] playsToChose = new PlayNBalls[size_plays]; // este arreglo lleva las jugadas elegibles
        int posTried =0;
        int posPlayed = 0;


        // Dependiendo de la llamada recursiva es si al smartplayer le toca se maximizador o minimizador
        if (maximizerOrMinimizer) {
            boolean heGanado = false;


            int i =0;
            /* En este punto es donde recae la optimización alfabeta, pues analiza si tiene que seguir abriendo el
               árbol o ya no es necesario
             */
            while( i< jugada.play.length && !heGanado){
                PlayNBalls newplay ;
                int[] temp = new int[jugada.play.length];
                hacerCopiaJugada(temp, jugada.play);
                int j =0;
                while(j < jugada.play[i] && !heGanado ){
                    temp[i] = j;
                    // Se hace la nueva jugada con las pelotas que queden en el juego
                    newplay = new PlayNBalls(hacerCopiaJugada(temp));
                    if(!haveIMadeThisPlay(playsMade, newplay)){
                        /* Se hace la llamada recursiva con el nuevo estado del juego, si fue maximizador
                           toca minimizar y viceversa
                         */
                        PlayNBalls playmade = makeMove(newplay, !maximizerOrMinimizer);
                        // Si ya ganó no es necesario seguir
                        if(playmade != null && playmade.getWinorlose() == 1)
                            heGanado = true;
                        // Registra la jugada para ser seleccionada
                        if(playmade!= null){
                            playsToChose[posPlayed] = newplay;
                            playsToChose[posPlayed].setWinorlose(playmade.getWinorlose());
                            playsToChose[posPlayed].setJugadasToGetToMe(playmade.getJugadasToGetToMe()+1);
                            posPlayed ++;
                        }
                    }
                    // Registra la jugada, para no volver a abrir ese caso
                    playsMade[posTried] = newplay;
                    posTried ++;
                    j++;
                  }
                  i++;
               }

            /* Este codigo presenta una alternativa menos eficiente, donde se abren todas las ramas del juego
               es decir no aplica alfa-beta
             */

            /*for(int i =0; i< jugada.play.length; i++){
                PlayNBalls newplay ;
                int[] temp = new int[jugada.play.length];
                hacerCopiaJugada(temp, jugada.play);
                for(int j =0; j < jugada.play[i]; j++){
                    temp[i] = j;
                    newplay = new PlayNBalls(hacerCopiaJugada(temp));
                    if(!haveIMadeThisPlay(playsMade, newplay)){
                        PlayNBalls playmade = makeMove(newplay, !maximizerOrMinimizer);
                        if(playmade.getWinorlose() == 1)
                            heGanado = true;
                        if(playmade!= null){
                            playsToChose[posPlayed] = newplay;
                            playsToChose[posPlayed].setWinorlose(playmade.getWinorlose());
                            playsToChose[posPlayed].setJugadasToGetToMe(playmade.getJugadasToGetToMe()+1);
                            posPlayed ++;
                        }
                    }
                    playsMade[posTried] = newplay;
                    posTried ++;
                }
            }*/


            return getMaximizedPlay(playsToChose);
        }
        else { // minimizer
            /*
                Este else, es el mismo código que la entrada al if, con la excepción que pregunta por haber perdido
                y no busca el camino ganador si no el perdedor. La optimización alfabeta, es hecha con el caso
                de haber perdido, cuando ya ha perdido no tiene que abrir más ramas del juego.
             */
            boolean hePerdido = false;

            int i =0;
            while( i< jugada.play.length && !hePerdido){
                PlayNBalls newplay ;
                int[] temp = new int[jugada.play.length];
                hacerCopiaJugada(temp, jugada.play);
                int j =0;
                while(j < jugada.play[i] && !hePerdido ){
                    temp[i] = j;
                    newplay = new PlayNBalls(hacerCopiaJugada(temp));
                    if(!haveIMadeThisPlay(playsMade, newplay)){
                        PlayNBalls playmade = makeMove(newplay, !maximizerOrMinimizer);
                        if(playmade != null && playmade.getWinorlose() == -1)
                            hePerdido = true;
                        if(playmade!= null){
                            playsToChose[posPlayed] = newplay;
                            playsToChose[posPlayed].setWinorlose(playmade.getWinorlose());
                            playsToChose[posPlayed].setJugadasToGetToMe(playmade.getJugadasToGetToMe()+1);
                            posPlayed ++;
                        }
                    }
                    playsMade[posTried] = newplay;
                    posTried ++;
                    j++;
                }
                i++;
            }


            /*for(int i =0; i< jugada.play.length; i++){
                PlayNBalls newplay ;
                int[] temp = new int[jugada.play.length];

                hacerCopiaJugada(temp, jugada.play);
                for(int j =0; j < jugada.play[i]; j++){
                    temp[i] = j;
                    newplay = new PlayNBalls(hacerCopiaJugada(temp));
                    if(!haveIMadeThisPlay(playsMade, newplay)){
                        PlayNBalls playmade = makeMove(newplay, !maximizerOrMinimizer);
                        if(playmade!= null){
                            playsToChose[posPlayed] = newplay;
                            playsToChose[posPlayed].setWinorlose(playmade.getWinorlose());
                            playsToChose[posPlayed].setJugadasToGetToMe(playmade.getJugadasToGetToMe()+1);
                            posPlayed ++;
                        }
                    }
                    playsMade[posTried] = newplay;
                    posTried ++;
                }
            }*/



            return getMinimizedPlay(playsToChose);
        }



    }
    /* Este método permite poder llegar a más profundidad en el árbol de juego, puesto que hay jugadas que ya analizó,
       que no es necesario que vuelva a abrir. P.E. (0, 2, 0) = (2, 0, 0)

     */
    private boolean haveIMadeThisPlay(PlayNBalls[] playsMade, PlayNBalls playing) {
        int pos = 0;
        boolean found = false;

        while(pos < playsMade.length && !found){
            if(playsMade[pos]!= null) {
                found = playing.equals(playsMade[pos]);
            }
            pos++;
        }

        return found;
    }
    // Método que a partir de todas las jugadas disponibles para selección toma la jugada en la que gana más rápido
    private PlayNBalls getMaximizedPlay(PlayNBalls[] plays) {

        PlayNBalls max = new PlayNBalls();

        max.setWinorlose(-1);

        max.setJugadasToGetToMe(1);


        int pos =0;



        while(pos < plays.length && plays[pos]!= null){
            if(plays[pos].compareToFromMax(max) > 0)
                max = plays[pos];
            pos++;
        }

        return max;
    }
    // Método que a partir de todas las jugadas disponibles para selección toma la jugada en la que pierde más lento
    private PlayNBalls getMinimizedPlay(PlayNBalls[] plays){

        PlayNBalls min;
        int pos =0;
        min = plays[pos];
        while(pos < plays.length && plays[pos]!= null){


            if(min.compareToFromMin(plays[pos]) > 0)
                min = plays[pos];
            pos++;
        }




        return min;
    }

    /*
       Debido a la manera en la que java maneja sus objetos, fue necesario crear dos métodos para lograr la copia de
       un objeto, en este caso una Jugada.
     */

    private void hacerCopiaJugada(int[] temp, int[] play){
        for(int i = 0; i< play.length; i++)
            temp[i] = play[i];
    }

    private int[] hacerCopiaJugada(int[] play) {
        int[] resp = new int[play.length];

        for(int i = 0; i< play.length; i++)
            resp[i] = play[i];


        return resp;
    }
}
