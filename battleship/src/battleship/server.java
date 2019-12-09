package battleship;
import java.net.*; 
import java.io.*;
public class server {
	 public static void main(String[] args) throws IOException{
         //use portNumber
         final int port = 50000;
         ServerSocket ss= new ServerSocket(port);
         Socket serverSocket;

         String clientSentence;

         String serverSentence;

         ServerSocket helloSocket;
         boolean turn = true;
         String shot = "shot";

         String fail = "fail";

         String lose ="lose";

         String won = "won";

         System.out.println("Starting Server ");
          System.out.println("Waiting for client connection");
          Socket s = ss.accept();
          System.out.println("Connection established.");

         //dertermine input and output of the current socket

         BufferedReader inFromUser = new BufferedReader(

                 new InputStreamReader(System.in));

         //construct the ship
         ship gameShip = new ship();

         //tasks of the player

         System.out.println("Add your ships to the board!");

         System.out.println("Now place your battleships!");

         System.out.println("CONFIRMED.");


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

                     System.out.println("Very sorry, your battleship can't be placed on these locations.Please change your location.");

             }

             else

                 break;

         }
         gameShip.printShip();

         helloSocket = new ServerSocket(50000);

         serverSocket  = helloSocket.accept();
         //client
         BufferedReader inFromClient = new BufferedReader(

                 new InputStreamReader(serverSocket.getInputStream()));

         DataOutputStream outToClient1 =

                 new DataOutputStream(serverSocket.getOutputStream());





         while(true){

             int shotColumn;

             int shotLine;

             //shot from client

             clientSentence = inFromClient.readLine();

             System.out.println("ANSWER: " + clientSentence);

             int clientInt = Integer.parseInt(clientSentence);

             shotColumn = clientInt%30-1;

             shotLine = Math.abs(clientInt/10)-1;


             if(gameShip.testShot(shotLine, shotColumn)){

                 gameShip.testFail();

                 if(gameShip.testFail() == false){

                     shot = fail = "Congratulations,You won!";

                     System.out.println("Very sorry, you lost! Better luck next time!");

                 }

                 outToClient1.writeBytes(shot+"\n");

                 outToClient1.flush();

             }

             else{

                 outToClient1.writeBytes(fail+"\n");

                 outToClient1.flush();

             }



             //shot from server

             String newS = inFromUser.readLine();

             outToClient1.writeBytes(newS+"\n");

             outToClient1.flush();



             clientSentence = inFromClient.readLine();

             System.out.println("ANSWER: " + clientSentence);

             s.shutdownOutput();
             System.out.println("Connection closed.");

         }

     }


}
