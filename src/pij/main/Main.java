package pij.main;

import java.io.*;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        System.out.println("============" + "                     " + "============");
        System.out.println("============" + "  S k r a B B k l e  " + "============");
        System.out.println("============" + "                     " + "============");
        Scanner scan = new Scanner(System.in);
        String userChoice;

        /**
         * Prompts the user to choose between loading a custom board or using the default board.
         * It repeats the prompt until a valid choice ('l' or 'd') is entered.
         */
        do {
            System.out.println("Would you like to _l_oad a board or use the _d_efault board?\n" + "Please enter your choice (l/d): ");
            userChoice = scan.nextLine();
        } while (!userChoice.equals("l") && !userChoice.equals("d"));

        if (userChoice.equals("l")) {
            System.out.println("You have chosen to load a  board.");
        } else {
            System.out.println("You have chosen to use the default board.");
//            read defaultBoard.txt from resources folder
            File defaultBoard = new File("resources/defaultBoard.txt");
            try (BufferedReader in = new BufferedReader(new FileReader(defaultBoard))) {
                String line;
                // first line that tells S is here
                int boardSize = Integer.parseInt(in.readLine());
                int yAxis = 1;
                System.out.print("   ");
                for (char i = 97; i < 97 + boardSize; i++) {
                    System.out.print(" " + i + " ");
                }
                System.out.println(" ");
                while ((line = in.readLine()) != null) {
                    StringBuilder modLine = new StringBuilder();

//                    for loop to loop through the string, to find 4 letters block
                    for (int i = 0; i < line.length(); i++) {
                        if ((line.charAt(i) == '}' && line.charAt(i - 2) != '{') || (line.charAt(i) == ')' && line.charAt(i - 2) != '(')) {
                            modLine.append("");
                        } else if (line.charAt(i) == '.') {
                            modLine.append(" . ");
                        } else {
                            modLine.append(line.charAt(i));
                        }
                    }
                    System.out.println((yAxis < 10 ? " " + yAxis : yAxis) + " " + modLine + " " + (yAxis < 10 ? " " + yAxis : yAxis));
                    yAxis++;

                }

                System.out.print("   ");
                for (char i = 97; i < 97 + boardSize; i++) {
                    System.out.print(" " + i + " ");
                }


            } catch (FileNotFoundException ex) {
                System.out.println("File " + defaultBoard + " not found!");
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }


    }

}

