package edu.csudh.cs.se.p2.applet;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class listexample {

    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        List<Object> str = new ArrayList<Object>();
        int n = input.nextInt();
        System.out.println("Enter rows:");
        int count = 0;
        while (count < n) {
            str.add(input.nextInt());
            str.add(input.next());
            count++;
        }
        System.out.println(str);
    }
}