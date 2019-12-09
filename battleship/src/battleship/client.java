package battleship;
import java.net.*;
import java.io.*;
public class client {
	public static void main (String [] args ) throws IOException

    {
        //use portNumber (comparison to server)
        final  int port=50000;
        Socket clientSocket;
        String sentence;
        String modifiedSentence;
        String shot = "shot";
        String fail = "fail";
        Socket s =new Socket(args[0],port);
        System.out.println("Connection established.");
        //dertermine input and output of the current socket
        BufferedReader inFromUser = new BufferedReader(

                new InputStreamReader(System.in));

        //construct the Ship
        ship gameShip = new ship();

        //tasks of the player

        System.out.println("Add your ships to the board ");

        System.out.println("Now  place your battleships ");

        System.out.println("CONFIRMED. ");

         gameShip =null;
        while(true){
            //Player enter head/tail location
            System.out.println("Enter your head location:");

            String lineA = inFromUser.readLine();

            System.out.println("Enter your tail location:");

            String lineB = inFromUser.readLine();

            if(!lineA.equals("CONFIRMED") || !lineB.equals("CONFIRMED")){
                int tail = Integer.parseInt(lineB);
                int head = Integer.parseInt(lineA);
                //testPos
                gameShip.testPos(head, tail);

                if(gameShip.boatBoolean == true){

                    gameShip.ConstructShip(head, tail);

                    System.out.println("Constructing ship "+head+ " and "+tail);

                }

                else

                    System.out.println("Very sorry,your battleship can't be placed on these locations.Please change your location.");

            }

            else

                break;

        }
        gameShip.printShip();
        clientSocket = new Socket();

        //standard input stream
        BufferedReader inFromServer = new BufferedReader(

                new InputStreamReader(clientSocket.getInputStream()));

        DataOutputStream outToServer = new DataOutputStream(

                clientSocket.getOutputStream());



        while(true){
            int shotColumn;

            int shotLine;
            //shot from client
            sentence = inFromUser.readLine();

            outToServer.writeBytes(sentence+"\n");

            outToServer.flush();

            //client gets shot from server

            modifiedSentence = inFromServer.readLine();

            System.out.println("ANSWER: " + modifiedSentence);

            modifiedSentence = inFromServer.readLine();

            System.out.println("ANSWER: " + modifiedSentence);

            int clientInt = Integer.parseInt(modifiedSentence);

            shotColumn= clientInt%30-1;

            shotLine = Math.abs(clientInt/30)-1;


            if(gameShip.testShot(shotLine, shotColumn)){

                gameShip.testFail();

                if(gameShip.testFail() == false){

                    shot = fail = "Congratulations,You won!";

                    System.out.println(" Very sorry, you lost! Better luck next time!");

                }

                outToServer.writeBytes(shot+"\n");

                outToServer.flush();

            }

            else{

                outToServer.writeBytes(fail+"\n");

                outToServer.flush();
                s.shutdownOutput();
                System.out.println("Connection closed.");
            }

        }



    }


}
