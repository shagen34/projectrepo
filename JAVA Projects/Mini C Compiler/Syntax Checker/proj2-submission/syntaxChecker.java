public class syntaxChecker {
  public static void main(String[] args) throws Exception
  {
    if( args.length <= 0)
      return;
    java.io.Reader r = new java.io.FileReader(args[0]);

    Parser p = new Parser(r);
    p.yyparse();
  }
}
