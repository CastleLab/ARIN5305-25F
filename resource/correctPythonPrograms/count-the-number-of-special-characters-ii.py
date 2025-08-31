class Solution:
    def numberOfSpecialChars(self, word: str) -> int:
        c=0
        s=set()
        for i in range(len(word)):
            if word[i].islower():
                if word[i].lower() not in s and word[i].upper() not in s:
                    if word[i].lower() not in word[i+1:] and  word[i].upper() in word[i+1:] and word[i].upper() not in word[:i]:
                        s.add(word[i].lower())
                        s.add(word[i].upper())
                        c+=1
        return c
