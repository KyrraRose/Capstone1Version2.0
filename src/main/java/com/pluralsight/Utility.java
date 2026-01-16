package com.pluralsight;

import org.w3c.dom.ls.LSOutput;

import java.util.InputMismatchException;
import java.util.Scanner;


public class Utility {
    public static final Scanner SCANNER = new Scanner(System.in);


    public static int getUserInt(String prompt) {
        System.out.println(prompt);
        System.out.printf("Type Here:");
        try {
            int choice = SCANNER.nextInt();
            SCANNER.nextLine();
            return choice;
        } catch (InputMismatchException e) {
            SCANNER.nextLine();

        }
        return 1;
    }

    public static String getUserString(String prompt) {
        System.out.printf("%s", prompt);
        return SCANNER.nextLine().trim();
    }


    //menu utility
    //console colors! "\033[38;2;<R>;<G>;<B>m"
    public static final String RESET = "\033[0m";

    public static final String BLUE = "\033[38;2;1;186;239m";
    public static final String DARK_BLUE = "\033[38;2;9;64;116m";
    public static final String GOLD = "\033[38;2;254;144;0m";
    public static final String LIGHT = "\033[38;2;191;255;188m";

    //Borders
    public static final String BORDER_T = "\n╔══════. ■ .═════════════════════════════════════════╗\n";
    public static final String BORDER_B = "\n╚══════════════════════════════════════. ■ .═════════╝";
    public static final String DISPLAY_T = "\n╔══════. ■ .═══════════════════════════════════════════════════════════════════════╗\n";
    public static final String DISPLAY_B = "\n╚════════════════════════════════════════════════════════════════════. ■ .═════════╝";

    public static void border(String border,String color){
        System.out.printf("%s%s%s%n",color,border,RESET);
    }

    public static void systemDialogue(String message){
        border(BORDER_T,DARK_BLUE);
        System.out.printf("%s\t%s %n",LIGHT,message);
        border(BORDER_B,DARK_BLUE);
    }
    public static void systemDisplay( String message){
        border(DISPLAY_T,BLUE);
        System.out.printf("%s\t"+message+"%n",GOLD);
        border(DISPLAY_B,BLUE);
    }

}
