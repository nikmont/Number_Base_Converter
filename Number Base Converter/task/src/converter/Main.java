package converter;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Scanner;

public class Main {
    static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        boolean isExit = false;

        while (!isExit) {
            System.out.print("\nEnter two numbers in format: {source base} {target base} (To quit type /exit) ");
            String userChoise = scanner.nextLine().trim();

            if (userChoise.equals("/exit")) {
                isExit = true;
                continue;
            }

            int sourceBase = Integer.parseInt(userChoise.split("\\s")[0]);
            int targetBase = Integer.parseInt(userChoise.split("\\s")[1]);

            boolean isBack = false;
            while (!isBack) {
                System.out.printf("\nEnter number in base %d to convert to base %d (To go back type /back) ", sourceBase, targetBase);

                String lowLevelUserChoise = scanner.nextLine().trim();

                if (lowLevelUserChoise.equals("/back")) {
                    isBack = true;
                    continue;
                }

                String resultOfConverion;

                if (lowLevelUserChoise.contains(".")) {

                    String [] number = lowLevelUserChoise.split("\\.");
                    String integerPart = convertInteger(number[0], sourceBase, targetBase);
                    String fractalPart = number[1];

                    if (sourceBase != 10) {
                        fractalPart = convertDecimaltoBase10(fractalPart, sourceBase);
                    }

                    if (targetBase == 10) {
                        resultOfConverion = integerPart + "." + fractalPart.substring(2, 7);
                    } else {

                        fractalPart = convertDecimalFromBase10(fractalPart ,targetBase);
                        resultOfConverion = integerPart + "." + fractalPart;

                    }
                } else {
                    resultOfConverion = convertInteger(lowLevelUserChoise, sourceBase, targetBase);
                }

                System.out.println("Conversion result: " + resultOfConverion);
            }
        }
    }

    private static String convertInteger(String num, int srcBase, int tgtBase) {
        BigInteger bigint = new BigInteger(num, srcBase);

        if (tgtBase == 10) {
            return bigint.toString();
        } else {
            return bigint.toString(tgtBase);
        }
    }

    private static String convertDecimaltoBase10(String fractalPart, int sourceBase) {
        BigDecimal sumOfFractals = BigDecimal.ZERO;
        char[] fractals = ("0" + fractalPart).toCharArray();

        for (int i = 1; i < fractals.length; i++) {
            sumOfFractals = sumOfFractals.add(BigDecimal.valueOf(Character.getNumericValue(fractals[i]) * Math.pow(sourceBase, -1*i)));
        }

        return sumOfFractals.toString();
    }

    private static String convertDecimalFromBase10(String fractal,int targetBase) {

        BigDecimal resultBase = BigDecimal.valueOf(targetBase);
        BigDecimal base10 = new BigDecimal(fractal);
        StringBuilder converted = new StringBuilder();
        int scaleCounter = 0;

        do {
            scaleCounter++;
            base10 = base10.multiply(resultBase);
            converted.append(Character.forDigit(base10.intValue(), targetBase)); // целую часть переводим и записываем
            base10 = base10.remainder(BigDecimal.ONE);

        } while (scaleCounter < 5 && base10.compareTo(BigDecimal.ZERO) != 0);

        while (converted.length() != 5) {
            converted.append(0);
        }
        return converted.toString();
    }
}
