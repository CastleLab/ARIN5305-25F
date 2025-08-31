class Solution:
    precedence_stack = {
        '(': 0,
        '+': 2,
        '-': 2,
        '*': 4,
        '/': 4,
        'neg': 5,
    }

    precedence_input = {
        '(': 6,
        ')': 0,
        '+': 1,
        '-': 1,
        '*': 3,
        '/': 3,
        'neg': 5,
    }

    operators = set("+-*/")

    def transform(self, s):
        # The generated postfix expression of s
        output = []
        # Wrap `s` with '(' and ')' to make processing easier
        s = s + ')'
        stack = ['(']
        prevC = '('

        hasNum = False
        num = 0
        for c in s:
            if c == ' ':
                continue

            # c is part of number
            if c.isdigit():
                hasNum = True
                num = num * 10 + int(c)
                prevC = c
                continue
            # c is operator
            if hasNum:
                hasNum = False
                output.append(num)
                num = 0

            # check if is negative unary operator
            if c == '-' and (prevC == '(' or prevC in self.operators):
                c = 'neg'
            
            # Pop until '('
            while self.precedence_stack[stack[-1]] >= self.precedence_input[c]:
                popped = stack.pop()
                if popped != '(':
                    output.append(popped)
                else:
                    break
            # push input on to the stack
            if c != ')':
                stack.append(c)

            prevC = c
        
        return output

    def evaluate_postfix(self, postfix):
        stack = []

        for each in postfix:
            # Is number
            if each not in self.precedence_input:
                stack.append(each)
                continue

            # Is operator
            if each == '+':
                stack.append(stack.pop() + stack.pop())
            elif each == '-':
                a, b = stack.pop(), stack.pop()
                stack.append(b - a)
            elif each == '*':
                stack.append(stack.pop() * stack.pop())
            elif each == '/':
                a, b = stack.pop(), stack.pop()
                stack.append(int(b / a)) # truncate towards zero
            elif each == 'neg':
                stack.append(-stack.pop())

        return stack[0]

    def calculate(self, s):
        postfix = self.transform(s)
        return self.evaluate_postfix(postfix)
