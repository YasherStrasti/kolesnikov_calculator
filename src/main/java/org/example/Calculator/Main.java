package org.example.Calculator;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) throws ScannerException {

        Scanner scanner = new Scanner(System.in);
        String input = scanner.nextLine();

        System.out.println(calc(input));

    }

    public static String calc(String input) throws ScannerException {
        String[] splitedString = input.split(" ");
        String strResult = "";

        int error;
        int result = 0;

        if ((error = stringCheckCorrect(splitedString)) != 0) {
            printException(error);
        }

        splitedString[0] = splitedString[0].toUpperCase();
        splitedString[2] = splitedString[2].toUpperCase();

        int op1 = 0;
        int op2 = 0;

        boolean isRomanian = false;

        if (isRoman(splitedString[0]) && isRoman(splitedString[2])) {
            isRomanian = true;
            op1 = romanToInt(splitedString[0]);
            op2 = romanToInt(splitedString[2]);

            int romanResult = op1 - op2;
            if (romanResult < 0 && splitedString[1].equals("-")) {
                printException(5);
            }

        } else if (isRoman(splitedString[0]) || isRoman(splitedString[2])) {
            printException(3);
        } else {
            op1 = Integer.parseInt(splitedString[0]);
            op2 = Integer.parseInt(splitedString[2]);
        }

        if (op1 > 10 || op2 > 10 || op1 < 1 || op2 < 1) {
            printException(4);
        }
        result = calculate(op1, op2, splitedString[1]);

        if (isRomanian)
            strResult += intToRoman(result);
        else
            strResult += result;

        return strResult;
    }

    static int calculate(int op1, int op2, String operation) {
        return switch (operation) {
            case "+" -> op1 + op2;
            case "-" -> op1 - op2;
            case "*" -> op1 * op2;
            case "/" -> op2 != 0 ? op1 / op2 : 0;
            default -> 0;
        };
    }

    static int stringCheckCorrect(String[] args) {
        char[] operation = {'+', '-', '*', '/'};
        args[0] = args[0].toUpperCase();
        args[2] = args[2].toUpperCase();

        if (args.length != 3) {
            return 1;
        }

        if (!isRomanArabian(args[0]) && !isRomanArabian(args[2])) {
            return 3;
        }

        if (args[1].length() != 1) {
            return 2;
        }

        if (Arrays.binarySearch(operation, args[1].charAt(0)) < 0) {
            return args[1].charAt(0) == '*' ? 0 : 2;
        }

        return 0;
    }

    static boolean isRomanArabian(String number) {
        for (int i = 0; i < number.length(); i++) {
            if (number.charAt(i) >= '0' && number.charAt(i) <= '9' || isRoman(number)) {
                return true;
            }
        }

        return false;
    }

    static boolean isRoman(String str) {
        char[] romans = {'I', 'V', 'X'};
        for (int i = 0; i < str.length(); i++) {
            if (Arrays.binarySearch(romans, str.charAt(i)) < 0)
                return false;
        }

        return true;
    }

    static String intToRoman(int number) {
        String[] romansUnits = {"I", "II", "III", "IV", "V", "VI", "VII", "VIII", "IX"};
        String[] romansDozens = {"X", "XX", "XXX", "XL", "L", "LX", "LXX", "LXXX", "XC"};
        String result = "";

        int temp;

        if (number == 100) {
            return ("C");
        } else {
            temp = number / 10;
            if (temp >= 1) {
                result += romansDozens[temp - 1];
                number %= 10;
            }
            if (number != 0)
                result += romansUnits[number - 1];
        }

        return result;
    }

    static int romanToInt(String roman) {
        ArrayList<Integer> integers = new ArrayList<>();

        int result = 0;

        for (int i = 0; i < roman.length(); i++) {
            if (roman.charAt(i) == 'I')
                integers.add(1);
            else if (roman.charAt(i) == 'V')
                integers.add(5);
            else if (roman.charAt(i) == 'X')
                integers.add(10);
        }

        for (int i = 0; i < integers.size(); i++) {
            if (i + 1 < integers.size()) {
                if (integers.get(i) >= integers.get(i + 1)) {
                    result += integers.get(i);
                } else {
                    result += integers.get(i + 1) - integers.get(i);
                    i++;
                }
            } else {
                result += integers.get(i);
            }
        }

        return result;
    }

    static void printException(int number) throws ScannerException {
        switch (number) {
            case 1 -> {
                throw new ScannerException("Формат математической операции не удовлетворяет заданию - два операнда и один оператор (+, -, /, *)");
            }
            case 2 -> {

                throw new ScannerException("Используйте оператор (+, -, /, *)");
            }
            case 3 -> {
                throw new ScannerException("Используются одновременно разные системы счисления");
            }
            case 4 -> {
                throw new ScannerException("Неверный ввод");
            }
            case 5 -> {
                throw new ScannerException("Римские цифры не могут быть отрицательными");
            }
        }
    }
}