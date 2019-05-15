// Shane Hagen
// CMPSC 470
// Semantic checker class

public class semanticChecker{
  public static void main(String[] args) throws Exception
  {
    if(args.length <= 0) { return; }
    String path = "E:\\Documents\\School Projects\\CMPSC470\\proj3 - Semantic Checker\\proj3-startup2\\src\\minc\\" + args[0];
    java.io.Reader r = new java.io.FileReader(path);
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
