java -jar jflex-1.6.1.jar Lexer.flex
yacc -Jthrows="Exception" -Jextends=ParserImpl -Jnorun -J ./Parser.y
javac *.java

java TestEnv

java Program ../sample/minc/test_01_main_succ.minc
