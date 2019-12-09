package battleship;
import java.io.IOException;
import java.net.Socket;
public class ship {
	private static String role;
    Socket serverSocket;
    public int[][] ship;

    public boolean fail = true;

    public boolean boatBoolean;


    public ship() {

        ship = new int[30][30];


    }

    public void testPos(int x, int y) {

        int lineA = Math.abs(x / 30);

        int column1 = x % 30;

        int lineB = Math.abs(x / 30);

        int column2 = y % 30;

        if (lineA == lineB || column1 == column2) {

            boatBoolean = true;

        } else

            boatBoolean = false;

    }

    //construct ship

    public void ConstructShip(int x, int y) {

        //head/tail of ship

        int lineA = Math.abs(x / 30);

        int column1 = x % 30;

        int lineB = Math.abs(y / 30);

        int column2 = y % 30;

        if (lineA == lineB) {

            for (int a = column1; a <= column2; a++) {

                ship[lineA - 1][a - 1] = 1;

            }

        } else if (column1 == column2) {

            for (int a = lineA; a <= lineB; a++) {

                ship[a - 1][column1 - 1] = 1;

            }

        } else {

            boatBoolean = false;

        }

    }


    public void printShip() {

        for (int a = 0; a < ship.length; a++) {

            for (int b = 0; b < ship[a].length; b++) {

                System.out.print(ship[a][b] + " ");

            }

            System.out.println();

        }

    }

    public boolean testShot(int line, int column) {

        if (ship[line][column] == 2) {

            ship[line][column] = 10;

            printShip();

            return true;

        } else

            return false;

    }


    public boolean testFail() {

        int count = 0;

        boolean booleanX = true;

        for (int a = 0; a < ship.length; a++) {

            for (int b = 0; b < ship[a].length; b++) {

                if (ship[a][b] == 0 || ship[a][b] == 10) {

                    count++;

                }

            }

        }

        if (count == 200)

            booleanX = false;

        return booleanX;

    }

    public static void main(String[] args) throws IOException {
        final int port = 50000;
        Socket s;
        if (args.length == 0) {
            role = "Server";
            System.out.println();
            System.out.println("Waiting for client connection ");


        } else {
            role = "client";
            s = new Socket(args[0], port);
        }
        System.out.println("Connection established.");

        System.out.println("Connection closed.");

    

        }
}
