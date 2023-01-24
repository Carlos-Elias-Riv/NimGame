package JuegoPelotitasAI;
import java.util.Scanner;
public class PlayNimGame {
    public static void main(String[] args) {
        NimGame juego = new NimGame();
        juego.intitializeGame(4);
        Scanner lee = new Scanner(System.in);
        SmartPlayerNBalls ai = new SmartPlayerNBalls();
        SmartPlayerNBalls ai2 = new SmartPlayerNBalls();
        juego.play(lee, ai);
        System.out.println("\nQuieres ver a la máquina jugar contra ella misma? [s para sí]");
        String resp = lee.nextLine();
        if(resp.equals("s") || resp.equals("S")){
            NimGame juego2 = new NimGame();
            juego2.intitializeGame(4);
            System.out.println("\nJuego entre los programas, que prueba que si n es par, el jugador 2 gana, si n impar jugador 1 gana");
            juego2.play(ai, ai2);
        }
        
        NimGame juego3 = new NimGame();
        juego3.intitializeGame(4);
        juego3.play(lee, ai);
    }
}
