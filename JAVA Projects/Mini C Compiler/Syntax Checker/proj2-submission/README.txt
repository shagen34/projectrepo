Shane Hagen
CMPSC470 - Project 2 Parser 

To run:
1. Requires jflex-1.6.1
2. Compile Lexer.flex file with command java -jar ./jflex-1.6.1.jar Lexer.flex
3. Compile java files
4. Run java syntaxChecker file_name to check the syntax of file_name

Notes:
- Could not determine how to get the text of the expected token for error reporting
  so if there is a match error it will report the expected token integer value rather
  than the text value, but it will print the text of the given token 