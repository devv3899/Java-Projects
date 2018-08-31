package apps;

import structures.Graph;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;

public class Test2 {
    private static Scanner scan = new Scanner(System.in);
    public static void main(String[] args) throws IOException {
        String file;
            System.out.println("Enter vertices list file name (or type " + "\"quit\"" + " to terminate program)");
                file = scan.nextLine();
                while(!file.equals("quit"))
                {
                int wsum = 0;
                file = exist(file);
                Graph p = new Graph(file);
                for(PartialTree.Arc x: MST.execute(MST.initialize(p)))
                {
                    wsum += x.weight;
                }
                System.out.println("Sum of weights = " + wsum);
                System.out.println("\nEnter another file of vertices (or type " +  "\"quit\"" + " to terminate program)");
                file = scan.nextLine();
                }
        System.out.println("\nTerminating...");
    }
    public static String exist(String Filename)
    {
        try{
            new Scanner(new File(Filename));
        }
        catch (IOException e)
        {
            System.out.println("Wrong filename or file does not exist!");
            System.out.println("Enter correct file name or type " +  "\"quit\"" + " to terminate program");
            Filename = scan.nextLine();
            if(!Filename.equals("quit")) {
                return exist(Filename);
            }
            else{
                System.out.println("\nTerminating...");
                System.exit(0);
            }
        }
        return Filename;
    }
}

