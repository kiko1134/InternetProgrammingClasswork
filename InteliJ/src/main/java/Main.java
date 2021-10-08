import java.util.Arrays;

public class Main {
    public static void main(@org.jetbrains.annotations.NotNull String[] args) throws NumberFormatException{

                for (String arg : args) {

                    try {
                        int number = Integer.parseInt(arg);
                    } catch (NumberFormatException e) {
                        try {
                            double d = Double.parseDouble(arg);
                            System.out.println(arg + " is not integer");
                            continue;
                        } catch (NumberFormatException q) {
                            System.out.println(arg + " is not a number");
                            continue;
                        }
                    }

                    int i, flag = 0;
                    int number = Integer.parseInt(arg);

                    if (number > Integer.MIN_VALUE && number < Integer.MAX_VALUE) {

                        for (i = 2; i <= number / 2; ++i) {
                            if (number % i == 0) {
                                flag = 1;
                                break;
                            }
                        }

                        if (number == 1)
                            System.out.println("1 is neither prime nor composite.");
                        else {
                            if (flag == 0)
                                System.out.println(number + " is a prime");
                            else
                                System.out.println(number + " is not a prime");
                        }
                    }
                    else System.out.println(number + " is out of bounds");
                }
        }

    }


