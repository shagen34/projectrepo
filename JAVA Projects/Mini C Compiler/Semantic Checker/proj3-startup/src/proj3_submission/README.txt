Shane Hagen
CMPSC 470
Project 3 Submission

How to run:

- To run, first compile all .java and .y files
  Commands:
  java -jar jflex-1.6.1.jar Lexer.flex
  yacc -Jthrows="Exception" -Jextends=ParserImpl -Jnorun -J ./Parser.y
  javac *.java

  java TestEnv

- run command semanticChecker 'C:/Path/to/input/file'  // with no ' '
  Example: java semanticChecker ../sample/minc/test_01_main_succ.minc

Notes:

- All files should compile and run however:
- Env should pass all TestEnv cases
- Parser does not correctly identify errors/success of parsing input file
- I believe there is a problem with the grammar or parser implementation
  but I cannot determine where the issue lies

- It appears to be related to func_decl production rule
  which is a problem for every test case.
- It seems to correctly identify/parse most other production rules
- I feel that I was close to debugging the problem but I do not want to delay
  submission any longer
