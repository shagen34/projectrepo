//Use map for symbol table
import java.util.*;
public class Parser
{
    public static final int MAIN        = 10;
    public static final int PRINT       = 11;
    public static final int INT         = 13;
    public static final int BOOL        = 14;
    public static final int FLOAT       = 15;
    public static final int REC         = 16;
    public static final int SIZE        = 17;
    public static final int NEW         = 18;
    public static final int WHILE       = 19;
    public static final int IF          = 20;
    public static final int ELSE        = 21;
    public static final int RET         = 22;
    public static final int BREAK       = 23;
    public static final int CONT        = 24;
    public static final int AND         = 25;
    public static final int OR          = 26;
    public static final int NOT         = 27;
    public static final int TRUE        = 28;
    public static final int FALSE       = 29;
    public static final int BEGIN       = 30;
    public static final int END         = 31;
    public static final int LPAREN      = 33;
    public static final int RPAREN      = 34;
    public static final int ASSIGN      = 38;
    public static final int PLUS        = 41;
    public static final int MUL         = 43;
    public static final int SEMI        = 47;
    public static final int AMP         = 48;
    public static final int LSQ         = 49;
    public static final int RSQ         = 50;
    public static final int LST         = 51;
    public static final int GRT         = 52;
    public static final int MIN         = 53;
    public static final int FRWD        = 54;
    public static final int INT_LIT     = 55;
    public static final int FLO_VAL     = 56;
    public static final int IDENT       = 58;
    public static final int MOD         = 59;
    public static final int COMM        = 60;
    public static final int DOT         = 61;
    public static final int EQV         = 62;
    public static final int NOTEQ       = 63;
    public static final int LSTE        = 64;
    public static final int GRTE        = 65;
    public static final int COMMENT     = 66;
    public static final int NEWLINE     = 67;
    public static final int WHITESPACE  = 68;

    public Parser(java.io.Reader r) throws java.io.IOException
    {
        this.lexer    = new Lexer(r, this);
    }

    Lexer   lexer;
    public ParserVal yylval;
    //Symbol table: HashMap<Key(symbol name), Value(location in map)>
    public HashMap<Object, Integer> ST = new HashMap<Object, Integer>();

    public int yyparse() throws java.io.IOException
    {
        while ( true )
        {
            int token = lexer.yylex();
            if(token == 0)
            {
                // EOF is reached
                return 0;
            }
            if(token == -1)
            {
                // error
                return -1;
            }
            Object attr = yylval.obj;
            switch(token)
            {
              case MAIN :
                System.out.print("<MAIN :" + lexer.lineno + ">");
                break;
              case PRINT :
                System.out.print("<PRINT :" + lexer.lineno + ">");
                break;
              case INT :
                System.out.print("<INT, :" + lexer.lineno + ">");
                break;
              case FLOAT :
                System.out.print("<FLOAT, :" + lexer.lineno + ">");
                break;
              case INT_LIT :
                System.out.print("<INT_VALUE, " + attr + " :" + lexer.lineno + ">");
                break;
              case BOOL :
                System.out.print("<BOOL :" + lexer.lineno + ">");
                break;
              case FLO_VAL :
                System.out.print("<FLOAT_VALUE, " + attr + " :" + lexer.lineno + ">");
                break;
              case REC :
                System.out.print("<RECORD :" + lexer.lineno + ">");
                break;
              case SIZE :
                System.out.print("<SIZE :" + lexer.lineno + ">");
                break;
              case NEW :
                System.out.print("<NEW :" + lexer.lineno + ">");
                break;
              case WHILE :
                System.out.print("<WHILE :" + lexer.lineno + ">");
                break;
              case IF :
                System.out.print("<IF :" + lexer.lineno + ">");
                break;
              case ELSE :
                System.out.print("<ELSE :" + lexer.lineno + ">");
                break;
              case RET :
                System.out.print("<RETURN :" + lexer.lineno + ">");
                break;
              case BREAK :
                System.out.print("<BREAK :" + lexer.lineno + ">");
                break;
              case CONT :
                System.out.print("<CONTINUE :" + lexer.lineno + ">");
                break;
              case AND :
                System.out.print("<AND :" + lexer.lineno + ">");
                break;
              case OR :
                System.out.print("<OR :" + lexer.lineno + ">");
                break;
              case NOT :
                System.out.print("<NOT :" + lexer.lineno + ">");
                break;
              case TRUE :
                System.out.print("<BOOL_VALUE, true :" + lexer.lineno + ">");
                break;
              case FALSE :
                System.out.print("<BOOL_VALUE, false :" + lexer.lineno + ">");
                break;
              case BEGIN :
                System.out.print("<{ :" + lexer.lineno + ">");
                break;
              case END :
                System.out.print("<} :" + lexer.lineno + ">");
                break;
              case LPAREN :
                System.out.print("<( :" + lexer.lineno + ">");
                break;
              case RPAREN :
                System.out.print("<) :" + lexer.lineno + ">");
                break;
              case ASSIGN :
                System.out.print("<= :" + lexer.lineno + ">");
                break;
              case PLUS :
                System.out.print("<+ :" + lexer.lineno + ">");
                break;
              case MUL :
                System.out.print("<* :" + lexer.lineno + ">");
                break;
              case SEMI :
                System.out.print("<; :" + lexer.lineno + ">");
                break;
              case AMP :
                System.out.print("<& :" + lexer.lineno + ">");
                break;
              case LSQ :
                System.out.print("<[ :" + lexer.lineno + ">");
                break;
              case RSQ :
                System.out.print("<] :" + lexer.lineno + ">");
                break;
              case LST :
                System.out.print("<< :" + lexer.lineno + ">");
                break;
              case GRT :
                System.out.print("<> :" + lexer.lineno + ">");
                break;
              case MIN :
                System.out.print("<- :" + lexer.lineno + ">");
                break;
              case FRWD :
                System.out.print("</ :" + lexer.lineno + ">");
                break;
              case MOD :
                System.out.print("<% :" + lexer.lineno + ">");
                break;
              case COMM :
                System.out.print("<, :" + lexer.lineno + ">");
                break;
              case DOT :
                System.out.print("<. :" + lexer.lineno + ">");
                break;
              case EQV :
                System.out.print("<== :" + lexer.lineno + ">");
                break;
              case NOTEQ :
                System.out.print("<!= :" + lexer.lineno + ">");
                break;
              case LSTE :
                System.out.print("<<= :" + lexer.lineno + ">");
                break;
              case GRTE :
                System.out.print("<>= :" + lexer.lineno + ">");
                break;
              case IDENT :
                if(ST.containsKey(attr))
                {
                  System.out.print("<ID, " + ST.get(attr) + " :" + lexer.lineno + ">");
                  break;
                }
                else
                {
                  System.out.print("<< add " + attr + " into symbol table at " + ST.size() + " :" + lexer.lineno + " >>"
                    + "<ID, " + ST.size() + " :" + lexer.lineno + ">");
                  ST.put(attr, ST.size());
                  break;
                }
              case COMMENT:
                System.out.print(lexer.yytext() + " :" + lexer.lineno + ">");
                break;
              case NEWLINE :
                System.out.println();
                break;
              case WHITESPACE :
                System.out.print(lexer.yytext() + " :" + lexer.lineno + ">");
                break;
            }
        }
    }
}
