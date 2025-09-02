public class Solution {
    public int numberOfSpecialChars(String s) {
        int[][] lowerCaseRecords = new int[26][2];
        int[][] upperCaseRecords = new int[26][2];
        
        for (int index = 0; index < s.length(); index++) {
            char current = s.charAt(index);
            if (Character.isLowerCase(current)) {
                int position = current - 'a';
                lowerCaseRecords[position][0] = 1;
                lowerCaseRecords[position][1] = index;
            }
        }
        
        for (int index = s.length() - 1; index > 0; index--) {
            char current = s.charAt(index);
            if (Character.isUpperCase(current)) {
                int position = current - 'A';
                upperCaseRecords[position][0] = 1;
                upperCaseRecords[position][1] = index;
            }
        }
        
        int count = 0;
        for (int i = 0; i < 26; i++) {
            boolean hasLower = lowerCaseRecords[i][0] == 1;
            boolean hasUpper = upperCaseRecords[i][0] == 1;
            
            if (hasLower && hasUpper) {
                int lastLowerIndex = lowerCaseRecords[i][1];
                int firstUpperIndex = upperCaseRecords[i][1];
                if (lastLowerIndex < firstUpperIndex) {
                    count++;
                }
            }
        }
        return count;
    }
}