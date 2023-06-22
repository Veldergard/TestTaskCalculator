package ru.veldergard;

import java.util.*;

public class Calculator {
    private final Scanner sc;
    private final List<Pair> romanNumbers;

    public Calculator() {
        this.sc = new Scanner(System.in);
        romanNumbers = new ArrayList<>();
        romanNumbers.add(new Pair(100, "C"));
        romanNumbers.add(new Pair(90, "XC"));
        romanNumbers.add(new Pair(50, "L"));
        romanNumbers.add(new Pair(40, "XL"));
        romanNumbers.add(new Pair(10, "X"));
        romanNumbers.add(new Pair(9, "IX"));
        romanNumbers.add(new Pair(5, "V"));
        romanNumbers.add(new Pair(4, "IV"));
        romanNumbers.add(new Pair(1, "I"));

    }

    public void run() {
        String[] s = sc.nextLine().trim().split(" ");
        if (s.length != 3 || s[0].length() == 0 || s[1].length() == 0 || s[2].length() == 0) {
            throw new InputMismatchException("Некорректный формат ввода данных.");
        } else if (checkRoman(s[0]) && checkRoman(s[2])) {
            calculateRoman(s);
        } else if (checkArabian(s[0]) && checkArabian(s[2])) {
            calculateArabian(s);
        } else {
            throw new InputMismatchException("Введены некорректные данные");
        }
    }

    private boolean checkRoman(String s) {
        for (int i = 0; i < s.length(); i++) {
            if (!"IVX".contains(String.valueOf(s.charAt(i)))) {
                throw new InputMismatchException("Введено некорректное число");
            }
        }
        return true;
    }

    private boolean checkArabian(String s) {
        for (int i = 0; i < s.length(); i++) {
            if (!Character.isDigit(s.charAt(i))) {
                throw new InputMismatchException("Введено некорректное число");
            }
        }
        return true;
    }

    private int doOperation(int first, int second, String operation) {
        if (operation.length() != 1) {
            throw new InputMismatchException("Неправильный ввод операции");
        }
        return switch (operation) {
            case "+" -> first + second;
            case "-" -> first - second;
            case "*" -> first * second;
            case "/" -> first / second;
            default -> throw new InputMismatchException("Указано неправильное действие");
        };
    }


    private void calculateArabian(String[] s) {
        int firstNumb = Integer.parseInt(s[0]);
        int secondNumb = Integer.parseInt(s[2]);

        int result = doOperation(firstNumb, secondNumb, s[1]);

        System.out.println(result);
    }

    private void calculateRoman(String[] s) {
        int firstNumb = parseRoman(s[0]);
        int secondNumb = parseRoman(s[2]);

        int result = doOperation(firstNumb, secondNumb, s[1]);

        if (result <= 0) {
            throw new ArithmeticException("Получен результат меньший или равный 0 при римских цифрах");
        }

        printRoman(result);
    }

    private void printRoman(int numb) {
        StringBuilder builder = new StringBuilder();

        while (numb > 0) {
            for (Pair p : romanNumbers) {
                while (numb >= p.first) {
                    builder.append(p.second);
                    numb -= p.first;
                }
            }
        }

        System.out.println(builder);
    }

    private int parseRoman(String s) {
        int result = 0;
        int prev = 0;
        int current;

        for (int i = 0; i < s.length(); i++) {
            current = switch (s.charAt(i)) {
                case 'I' -> 1;
                case 'V' -> 5;
                case 'X' -> 10;
                default -> throw new InputMismatchException("Введены некорректные данные");
            };

            if (current > prev) {
                result = result + current - 2 * prev;
            } else {
                result = result + current;
            }

            if (result <= 0 || result > 10) {
                throw new InputMismatchException("Введено число не входящее в допустимый диапазон");
            }

            prev = current;
        }

        return result;
    }
}
