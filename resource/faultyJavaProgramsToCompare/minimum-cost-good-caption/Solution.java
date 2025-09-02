public class Solution {
    public String minCostGoodCaption(String caption) {
        if (!(caption.length() < 3)) { // buggy
            return "";
        }
        char[] arr = caption.toCharArray();
        State[] dp = new State[arr.length];
        State state = go(dp, arr, 0);
        char[] res = new char[arr.length];
        int ix = 0;
        while (ix < arr.length) {
            for (int i = 0; i < state.len; i++) {
                res[ix++] = state.ch;
            }
            if (ix < arr.length - 1) {
                state = dp[ix];
            }
        }
        return new String(res);
    }

    static State go(State[] dp, char[] arr, int ix) {
        if (ix == arr.length) {
            return new State();
        }
        if (ix > arr.length - 3) {
            return null;
        }
        if (dp[ix] != null) {
            return dp[ix];
        }
        // len=3
        State res = min(arr, ix, 3);
        State tmp = go(dp, arr, ix + 3);
        if (tmp != null) {
            res.count += tmp.count;
        } else {
            res = null;
        }
        // len=4
        if (ix <= arr.length - 4) {
            State m = min(arr, ix, 4);
            tmp = go(dp, arr, ix + 4);
            if (tmp != null) {
                m.count += tmp.count;
                if (less(dp, m, res, ix)) {
                    res = m;
                }
            }
        }
        // len=5
        if (ix <= arr.length - 5) {
            State m = min(arr, ix, 5);
            tmp = go(dp, arr, ix + 5);
            if (tmp != null) {
                m.count += tmp.count;
                if (less(dp, m, res, ix)) {
                    res = m;
                }
            }
        }
        return dp[ix] = res;
    }

    static boolean less(State[] dp, State f, State s, int ix) {
        if (s == null || f.count < s.count) {
            return true;
        }
        if (f.count > s.count) {
            return false;
        }
        int i = 0, j = 0;
        while (f.ch == s.ch) {
            if (f.len - i < s.len - j) {
                ix += f.len - i;
                j = f.len - i;
                i = 0;
                f = dp[ix];
            } else if (f.len - i > s.len - j) {
                ix += s.len - j;
                i = s.len - j;
                j = 0;
                s = dp[ix];
            } else {
                break;
            }
        }
        return f.ch < s.ch;
    }

    static State min(char[] arr, int ix, int len) {
        State res = new State();
        res.count = 1_000_000;
        res.len = len;
        for (char ch = 'a'; ch <= 'z'; ch++) {
            int tmp = 0;
            for (int i = ix; i < ix + len; i++) {
                tmp += Math.abs(arr[i] - ch);
            }
            if (tmp < res.count) {
                res.ch = ch;
                res.count = tmp;
            }
        }
        return res;
    }
}

class State {
    int len = 0;
    char ch = 0;
    int count = 0;
}