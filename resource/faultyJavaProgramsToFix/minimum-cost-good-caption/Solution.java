public class Solution {
    private static final int MAX_HALF = Integer.MAX_VALUE / 2;

    public String minCostGoodCaption(String caption) {
        int length = caption.length();
        if (length < 3) return "";

        int[] minCosts = new int[length + 1], successorIndices = new int[length + 1], chosenLetters = new int[length + 1], segmentLengths = new int[length + 1];
        int[][] cumulativeCosts = new int[length + 1][26];
        char[] chars = caption.toCharArray();

        for (int pos = 0; pos < length; pos++) {
            int originalChar = chars[pos] - 'a';
            for (int letter = 0; letter < 26; letter++) {
                cumulativeCosts[pos + 1][letter] = cumulativeCosts[pos][letter] + Math.abs(originalChar - letter);
            }
        }

        for (int i = 0; i < length; i++) {
            minCosts[i] = MAX_HALF;
            successorIndices[i] = -1;
            chosenLetters[i] = -1;
            segmentLengths[i] = 0;
        }
        minCosts[length] = 0;

        for (int start = length - 1; start >= 0; start--) {
            for (int segLen = 3; segLen <= 5; segLen++) {
                int end = start + segLen;
                if (end > length) continue;

                int optimalLetter = 0;
                int minSegmentCost = MAX_HALF;
                for (int letter = 0; letter < 26; letter++) {
                    int segmentCost = cumulativeCosts[end][letter] - cumulativeCosts[start][letter];
                    if (segmentCost < minSegmentCost) {
                        minSegmentCost = segmentCost;
                        optimalLetter = letter;
                    }
                }

                int totalCost = minCosts[end] + minSegmentCost;
                if (totalCost < minCosts[start]) {
                    minCosts[start] = totalCost;
                    successorIndices[start] = end;
                    chosenLetters[start] = optimalLetter;
                    segmentLengths[start] = segLen;
                } else if (totalCost == minCosts[start]) {
                    int comparison = compareSequences(
                        start, chosenLetters[start], segmentLengths[start], successorIndices[start],
                        optimalLetter, segLen, end,
                        successorIndices, chosenLetters, segmentLengths, length, minCosts
                    );
                    if (comparison > 0) {
                        successorIndices[start] = end;
                        chosenLetters[start] = optimalLetter;
                        segmentLengths[start] = segLen;
                    }
                }
            }
        }

        if (minCosts[0] >= MAX_HALF) return "";

        StringBuilder result = new StringBuilder(length);
        int current = 0;
        while (current < length) {
            int len = segmentLengths[current];
            char c = (char) ('a' + chosenLetters[current]);
            result.append(String.valueOf(c).repeat(len));
            current = successorIndices[current];
        }
        return result.toString();
    }

    private int compareSequences(
        int basePos, int oldLetter, int oldLength, int oldNext,
        int newLetter, int newLength, int newNext,
        int[] successorIndices, int[] chosenLetters, int[] segmentLengths,
        int totalLength, int[] minCosts
    ) {
        int oldOffset = 0, newOffset = 0;
        int currentOldPos = basePos, currentNewPos = basePos;
        int currentOldLetter = oldLetter, currentNewLetter = oldLetter;
        int currentOldLen = oldLength, currentNewLen = newLength;
        int nextOldPos = oldNext, nextNewPos = newNext;

        while (true) {
            if (currentOldLetter != currentNewLetter) {
                return Integer.compare(currentOldLetter, currentNewLetter);
            }

            int remainingOld = currentOldLen - oldOffset;
            int remainingNew = currentNewLen - newOffset;
            int advance = Math.min(remainingOld, remainingNew);
            oldOffset += advance;
            newOffset += advance;

            if (oldOffset == currentOldLen && newOffset == currentNewLen) {
                if (nextOldPos == totalLength && nextNewPos == totalLength) return 0;
                if (nextOldPos == totalLength) return -1;
                if (nextNewPos == totalLength) return 1;
                
                currentOldPos = nextOldPos;
                currentOldLetter = chosenLetters[currentOldPos];
                currentOldLen = segmentLengths[currentOldPos];
                nextOldPos = successorIndices[currentOldPos];
                oldOffset = 0;

                currentNewPos = nextNewPos;
                currentNewLetter = chosenLetters[currentNewPos];
                currentNewLen = segmentLengths[currentNewPos];
                nextNewPos = successorIndices[currentNewPos];
                newOffset = 0;
            } else if (oldOffset == currentOldLen) {
                if (nextOldPos == totalLength) return -1;
                currentOldPos = nextOldPos;
                currentOldLetter = chosenLetters[currentOldPos];
                currentOldLen = segmentLengths[currentOldPos];
                nextOldPos = successorIndices[currentOldPos];
                oldOffset = 0;
            } else if (newOffset == currentNewLen) {
                if (nextNewPos == totalLength) return 1;
                currentNewPos = nextNewPos;
                currentNewLetter = chosenLetters[currentNewPos];
                currentNewLen = segmentLengths[currentNewPos];
                nextNewPos = successorIndices[currentNewPos];
                newOffset = 0;
            }
        }
    }
}