public class Solution {
    private boolean checkPrime(long number) {
        if (number < 2) return false;
        if (number == 2 || number == 3) return true;
        if (number % 2 == 0 || number % 3 == 0) return false;
        
        for (int divisor = 5; divisor * divisor <= number; divisor += 6) {
            if (number % divisor == 0 || number % (divisor + 2) == 0) {
                return false;
            }
        }
        return true;
    }
    
    public long sumOfLargestPrimes(String digits) {
        long firstMax = 0, secondMax = 0, thirdMax = 0;
        int length = digits.length();
        
        for (int start = 0; start < length; start++) {
            long current = 0;
            for (int end = start; end < length; end++) {
                current = current * 10 + (digits.charAt(end) - '0');
                if (current != firstMax && current != secondMax && current != thirdMax) {
                    if (checkPrime(current)) {
                        if (current > firstMax) {
                            thirdMax = secondMax;
                            secondMax = firstMax;
                            firstMax = current;
                        } else if (current > secondMax) {
                            thirdMax = secondMax;
                            secondMax = current;
                        } else if (current > thirdMax) {
                            thirdMax = current;
                        }
                    }
                }
            }
        }
        return firstMax + secondMax + thirdMax;
    }
}