# Calculator
Hand-coded recursive descent parser for right-recursive variant of the classic expression grammar
(see Figure 3.4 on pg. 101 of Engineering a Compiler 2nd Edition by Cooper & Torczan for the full
CFG specification).

Run the main function in the Driver class to enter expressions from the command line.

Here is an example of how to use the Calculator API in your project:

`Calculator.calculate("4 + ((5 + 9) - 3) - (9 * 2) + 4*2 - 10/5 + 592");`


Below is the operator precedence table. Operators are listed from top to bottom, in descending
order of precedence.

| Precedence        | Operator           | Description                      |Associativity  |
| ----------------- |:------------------:| -------------------------------:| ------------: |
| 1                 | ()                 |   Parenthesis                   | N/A
| 2                 | * /                |   Multiplication & Division     | Right-to-left
| 3                 | + -                |   Additon & Subtraction         | Right-to-left
| 4                 | -                  |    Unary Minus                  | N/A

Note that the arithmetic operations are right associative within each
precedence level. For example, `5-3+2` is evaluated as `5-(3+2)`, 
which evaluates to `0`. Moreover, the unary minus has lower precedence than addition and subtraction
so `-3+4` evaluates to `-7`, and `2+(-3+4)` evaluates to `-5`. The unary minus is implemented by inserting
a `0` before each occurrence (a bit hacky but will be improved in a future version). 
Other than that, the behavior is the same as traditional calculators.
