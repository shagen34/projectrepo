// Shane Hagen
// CMPSC 470
// Semantic checker class

public class semanticChecker{
  public static void main(String[] args) throws Exception
  {
    if(args.length <= 0) { return; }
    java.io.Reader r = new java.io.FileReader(args[0]);
    Parser yyparser = new Parser(r);

    try {
        //Parser._debug = true;
        if (yyparser.yyparse() == 0)
        {
            System.out.println("Success: no error found.");
        }
        else
        {
            System.out.println("Error: there exists error(s).");
        }
    }
    catch(Exception e)
    {
        System.out.println(e.getMessage());
        System.out.println("Error: there exists error(s).");
    }
  }
}
