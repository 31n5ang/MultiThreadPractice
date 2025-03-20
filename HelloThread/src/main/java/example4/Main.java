package example4;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        List<Long> inputNumbers = Arrays.asList(0L, 3435L, 11L, 3423L, 3223L, 6602L, 3243000L);

        List<FactorialThread> threads = new ArrayList<>();

        for (long inputNumber : inputNumbers) {
            threads.add(new FactorialThread(inputNumber));
        }

        for (Thread thread : threads) {
            thread.setDaemon(true);
            thread.start();
        }

        for (Thread thread : threads) {
            try {
                thread.join(2000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }

        int index = 0;
        for (FactorialThread factorialThread : threads) {
            if (factorialThread.isFinished()) {
                System.out.println("Factorial of " + inputNumbers.get(index) + " is " + factorialThread.getResult());
            }
            else {
                System.out.println("The calculation for " + inputNumbers.get(index));
            }

            index++;
        }
    }

    public static class FactorialThread extends Thread {
        private long inputNumber;
        private BigInteger result = BigInteger.ZERO;
        private boolean isFinished = false;

        public FactorialThread(long inputNumber) {
            this.inputNumber = inputNumber;
        }

        @Override
        public void run() {
            this.result = factorial(inputNumber);
            this.isFinished = true;
        }

        public BigInteger factorial(long n) {
            BigInteger tempResult = BigInteger.ONE;

            for (long i = n; i > 0; i--) {
                tempResult = tempResult.multiply(new BigInteger(Long.toString(i)));
            }
            return tempResult;
        }

        public boolean isFinished() {
            return isFinished;
        }

        public BigInteger getResult() {
            return result;
        }
    }
}
