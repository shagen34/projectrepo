public class Parser
{
    public static final int ENDMARKER   =  0;
    public static final int LEXERROR    =  1;

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
    public static final int LBRACKET    = 30;
    public static final int RBRACKET    = 31;
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
    public static final int COMMA       = 60;
    public static final int DOT         = 61;
    public static final int EQ          = 62;
    public static final int NOTEQ       = 63;
    public static final int LSTE        = 64;
    public static final int GRTE        = 65;
    public static final int COMMENT     = 66;
    public static final int NEWLINE     = 67;
    public static final int WHITESPACE  = 68;

    public class Token {
        public int       type;
        public ParserVal attr;
        public Token(int type, ParserVal attr) {
            this.type   = type;
            this.attr   = attr;
        }
    }

    public ParserVal yylval;
    Token _token;
    Lexer _lexer;
    public Parser(java.io.Reader r) throws java.io.IOException
    {
        _lexer = new Lexer(r, this);
        _token = null;
        Advance();
    }

    public void Advance() throws java.io.IOException
    {
        int token_type = _lexer.yylex();

        if(token_type ==  0)      _token = new Token(ENDMARKER , null  );
        else if(token_type == -1) _token = new Token(LEXERROR  , yylval);
        else                      _token = new Token(token_type, yylval);
    }

    public boolean Match(int token_type) throws java.io.IOException
    {
        boolean match = (token_type == _token.type);
        if(!match && token_type != LEXERROR)
        {
          // Report match error
          report(token_type, _token.type, _lexer.lineno);
        }
        if(_token.type != ENDMARKER)
            Advance();

        return match;
    }

    public void report(int tokenExpected, int tokenReceived, int lineno) throws java.io.IOException
    {
      if(tokenExpected != tokenReceived)
      {
        System.out.println("Syntax error: Token type " + tokenExpected + " is expected instead of '" + _lexer.yytext() + "' at line " + lineno);
      }
      else
      {
        System.out.println("Syntax error: There is a syntax error at line " + lineno);
      }
    }

    public int yyparse() throws java.io.IOException, Exception
    {
        parse();
        return 0;
    }

    public void parse() throws java.io.IOException, Exception
    {
        boolean successparse = program();
        if(successparse)
            System.out.println("Success: no syntax error found.");
        else {
          report(0, 0, _lexer.lineno);
          System.out.println("Error: there exist syntax error(s).");
        }
    }

// Parsing table
  public boolean program() throws java.io.IOException, Exception
  {
      switch(_token.type)
      {
          // program 			-> decl_list
          case INT:
	          if( decl_list() 	== false) {
              // If syntax error is not a match error, report to programmer
              return false;
            }
	          return true;
          case ENDMARKER:
            return true;
      }
      return false;
  }

  public boolean decl_list() throws java.io.IOException, Exception
  {
      switch(_token.type)
      {
          // decl_list 		-> decl_list'
          // First(decl_list) = { epsilon, int }
          case INT:
            if( decl_list_() 	== false) return false;
            return true;
          case ENDMARKER:
	          //if( decl_list_()	    == false) return false;
	          //if( Match(ENDMARKER) 	== false) return false;
	          return true;
      }
      return false;
  }

  public boolean decl_list_() throws java.io.IOException, Exception
  {
      switch(_token.type)
      {
          // decl_list'	-> fun_decl decl_list'
          // First (decl_list') = {"epsilon", "int"}
          case INT:
            if(fun_decl()   == false) return false;
            if(decl_list_() == false) return false;
            return true;
          // decl_list'	-> epsilon
          case ENDMARKER:
            return true;
      }
      return false;
  }

  public boolean type_spec() throws java.io.IOException, Exception
  {
      switch(_token.type)
      {
        // type_spec	-> "int"
        case INT:
            if( Match(INT) == false) return false;
            return true;
      }
      return false;
  }

  public boolean fun_decl() throws java.io.IOException, Exception
  {
      switch(_token.type)
      {
        // fun_decl	-> type_spec IDENT "(" params ")" compound_stmt
        case INT:
            if( type_spec()     == false) return false;
            if( Match(IDENT)    == false) return false;
            if( Match(LPAREN)   == false) return false;
            if( params()        == false) return false;
            if( Match(RPAREN)   == false) return false;
            if( compound_stmt() == false) return false;
            return true;
      }
      return false;
  }

  public boolean params() throws java.io.IOException, Exception
  {
  	switch(_token.type)
  	{
  	    // params 		   -> param_list
  	    case INT:
  		    if( param_list()   == false) return false;
          return true;
        case RPAREN:
          return true;

          //if( param_list()    == false) return false;
  	    // params 		   -> epsilon
  	    case ENDMARKER:
  		    return true;
  	}
  	return false;
      }

  public boolean param_list() throws java.io.IOException, Exception
  {
	   switch(_token.type)
	    {
	    // param_list	   -> param param_list'
	    case INT:
		    if( param()   	    == false) return false;
		    if( param_list_()   == false) return false;
		    return true;
      case RPAREN:
        return true;
	   }
	   return false;
  }

  public boolean param_list_() throws java.io.IOException, Exception
  {
    switch(_token.type)
    {
	    // param_list'	   -> "," param param_list'
	    case COMMA:
    		if( Match(COMMA)    == false) return false;
    		if( param()   	    == false) return false;
    		if( param_list_()   == false) return false;
		    return true;
      case RPAREN:
        return true;
	    // param_list_	   -> epsilon
	    case ENDMARKER:
		    return true;
	  }
	  return false;
  }

  public boolean param() throws java.io.IOException, Exception
  {
	   switch(_token.type)
	   {
	    // param		   -> type_spec IDENT
	    case INT:
		    if( type_spec()     == false) return false;
		    if( Match(IDENT)    == false) return false;
		    return true;
      case COMMA:
        return true;
      case RPAREN:
        return true;
	   }
	   return false;
  }

  public boolean stmt_list() throws java.io.IOException, Exception
  {
	   switch(_token.type)
	   {
	    // stmt_list	    -> stmt_list'
	    case IDENT:
		    if( stmt_list_()    == false) return false;
		    return true;
	    case LBRACKET:
		    if( stmt_list_()    == false) return false;
	      return true;
	    case IF:
    		if( stmt_list_()    == false) return false;
		    return true;
	    case WHILE:
		    if( stmt_list_()    == false) return false;
		    return true;
	    case SEMI:
		    if( stmt_list_()    == false) return false;
		    return true;
      case RBRACKET:
        if( stmt_list_()    == false) return false;
        return true;
	   }
	  return false;
  }

  public boolean stmt_list_() throws java.io.IOException, Exception
  {
  	switch(_token.type)
  	{
	    // stmt_list'	    -> stmt stmt_list'
	    case IDENT:
    		if( stmt()    	    == false) return false;
    		if( stmt_list_()    == false) return false;
    		return true;
	    case LBRACKET:
    		if( stmt()    	    == false) return false;
    		if( stmt_list_()    == false) return false;
    		return true;
	    case IF:
    		if( stmt()    	    == false) return false;
    		if( stmt_list_()    == false) return false;
    		return true;
	    case WHILE:
    		if( stmt()    	    == false) return false;
    		if( stmt_list_()    == false) return false;
    		return true;
	    case SEMI:
    		if( stmt()    	    == false) return false;
    		if( stmt_list_()    == false) return false;
    		return true;
      case RBRACKET:
        return true;
      case ENDMARKER:
        return true;
	 }
	 return false;
  }

  public boolean stmt() throws java.io.IOException, Exception
  {
  	switch(_token.type)
  	{
	    // stmt		-> expr_stmt
	    case IDENT:
    		if( expr_stmt() == false) return false;
    		return true;
     case SEMI:
        if( expr_stmt() == false) return false;
        return true;
	    // stmt		    -> compound_stmt
 	    case LBRACKET:
    		if( compound_stmt() == false) return false;
    		return true;
	    // stmt		-> if_stmt
	    case IF:
    		if( if_stmt() 	== false) return false;
    		return true;
	    // stmt		 -> while_stmt
	    case WHILE:
    		if( while_stmt() == false) return false;
    		return true;
	  }
	  return false;
  }

  public boolean expr_stmt() throws java.io.IOException, Exception
  {
  	switch(_token.type)
  	{
	    // expr_stmt	  -> IDENT "=" expr ";"
	    case IDENT:
    		if( Match(IDENT)   == false) return false;
    		if( Match(ASSIGN)  == false) return false;
    		if( expr()	       == false) return false;
    		if( Match(SEMI)    == false) return false;
    		return true;
	    // expr_stmt	-> ";"
	    case SEMI:
    		if( Match(SEMI)    == false) return false;
		    return true;
	  }
	  return false;
  }

  public boolean compound_stmt() throws java.io.IOException, Exception
  {
    switch(_token.type)
	  {
	    // compound_stmt		-> "{" local_decls stmt_list "}"
	    case LBRACKET:
      		if( Match(LBRACKET)	== false) return false;
      		if( local_decls()	  == false) return false;
      		if( stmt_list()   	== false) return false;
          if( Match(RBRACKET) == false) return false;
	        return true;
	  }
	  return false;
  }

  public boolean if_stmt() throws java.io.IOException, Exception
  {
    switch(_token.type)
	  {
      // if_stmt -> "if" "(" expr ")" stmt "else" stmt
      case IF:
        if( Match(IF)     == false) return false;
        if( Match(LPAREN) == false) return false;
        if( expr()        == false) return false;
        if( Match(RPAREN) == false) return false;
        if( stmt()        == false) return false;
        if( Match(ELSE)   == false) return false;
        if(stmt()         == false) return false;
        return true;
    }
    return false;
  }

  public boolean while_stmt() throws java.io.IOException, Exception
  {
    switch(_token.type)
	  {
      // while_stmt -> "while" "(" expr ")" stmt
      case WHILE:
        if( Match(WHILE)  == false) return false;
        if( Match(LPAREN) == false) return false;
        if( expr()        == false) return false;
        if( Match(RPAREN) == false) return false;
        if( stmt()        == false) return false;
        return true;
    }
    return false;
  }

  public boolean local_decls() throws java.io.IOException, Exception
  {
    switch(_token.type)
	  {
	    // local_decls	-> 	local_decls'
	    case INT:
    		if( local_decls_()	== false) return false;
    		return true;
      case IDENT:
        return true;
      case LBRACKET:
        return true;
      case IF:
        return true;
      case WHILE:
        return true;
      case SEMI:
        return true;
      case RBRACKET:
        return true;
	 }
	 return false;
  }

  public boolean local_decls_() throws java.io.IOException, Exception
  {
    switch(_token.type)
	  {
	    // local_decls'	-> 	local_decl local_decls'
	    case INT:
      	if( local_decl()	== false) return false;
      	if( local_decls_()	== false) return false;
      	return true;
      case IDENT:
        return true;
      case LBRACKET:
        return true;
      case IF:
        return true;
      case WHILE:
        return true;
      case SEMI:
        return true;
      case RBRACKET:
        return true;
	    // local_decls'	->	epsilon
	    case ENDMARKER:
	      return true;
	 }
	 return false;
  }

  public boolean local_decl() throws java.io.IOException, Exception
  {
    switch(_token.type)
	  {
	    // local_decl	-> 	type_spec IDENT ";"
	    case INT:
      	if( type_spec()	 == false) return false;
      	if( Match(IDENT) == false) return false;
      	if( Match(SEMI)  == false) return false;
        return true;
	 }
	 return false;
  }

  public boolean arg_list() throws java.io.IOException, Exception
  {
    switch(_token.type)
	  {
	    // arg_list 	-> expr arg_list'
	    case LPAREN:
      	if( expr()   	== false) return false;
      	if( arg_list_() == false) return false;
      	return true;
	    case IDENT:
    		if( expr()   	== false) return false;
    		if( arg_list_() == false) return false;
    		return true;
	    case INT_LIT:
    		if( expr()   	== false) return false;
    		if( arg_list_() == false) return false;
    		return true;
	 }
	 return false;
  }

  public boolean arg_list_() throws java.io.IOException, Exception
  {
    switch(_token.type)
	  {
	    // arg_list' 	-> "," expr arg_list'
	    case COMMA:
        if( Match(COMMA) == false) return false;
        if( expr()	     == false) return false;
        if( arg_list_()	 == false) return false;
        return true;
	    // arg_list'	-> epsilon
      case RPAREN:
        return true;
	    case ENDMARKER:
		    return true;
	 }
   return false;
  }

  public boolean args() throws java.io.IOException, Exception
  {
    switch(_token.type)
	  {
	    // args -> expr arg_list'
	    case LPAREN:
    		if( expr()	== false) return false;
    		if( arg_list_() == false) return false;
    		return true;
	    case IDENT:
    		if( expr()	== false) return false;
    		if( arg_list_()	== false) return false;
    		return true;
	    case INT_LIT:
    		if( expr()	== false) return false;
    		if( arg_list_()	== false) return false;
    		return true;
	    // args -> epsilon
     case COMMA:
        return true;
      case RPAREN:
        return true;
	   case ENDMARKER:
		    return true;
	  }
	  return false;
  }

  public boolean expr() throws java.io.IOException, Exception
  {
    switch(_token.type)
	  {
	    // expr -> term expr'
	    case LPAREN:
    		if( term()	== false) return false;
    		if( expr_()	== false) return false;
    		return true;
	    case IDENT:
    		if( term()	== false) return false;
    		if( expr_()	== false) return false;
    		return true;
	    case INT_LIT:
    		if( term()	== false) return false;
    		if( expr_()	== false) return false;
    		return true;
	 }
	 return false;
  }

  public boolean expr_() throws java.io.IOException, Exception
  {
    switch(_token.type)
	  {
	    // expr' -> "+" term expr'
	    case PLUS:
    		if( Match(PLUS)	== false) return false;
    		if( term()	== false) return false;
    		if( expr_()	== false) return false;
    		return true;
	    // expr' -> epsilon
	    case ENDMARKER:
		    return true;
      case COMMA:
        return true;
      case LPAREN:
        return true;
      case RPAREN:
        return true;
      case IDENT:
        return true;
      case INT_LIT:
        return true;
      case SEMI:
        return true;
	  }
	  return false;
  }

  public boolean term() throws java.io.IOException, Exception
  {
    switch(_token.type)
	  {
	    // term -> factor term'
	    case LPAREN:
    		if( factor()	== false) return false;
    		if( term_() 	== false) return false;
    		return true;
	    case IDENT:
    		if( factor()	== false) return false;
    		if( term_() 	== false) return false;
    		return true;
	    case INT_LIT:
    		if( factor()	== false) return false;
    		if( term_() 	== false) return false;
    		return true;
	  }
	  return false;
  }

  public boolean term_() throws java.io.IOException, Exception
  {
    switch(_token.type)
	  {
	    // term' -> "==" factor term'
	    case EQ:
    		if( Match(EQ)	== false) return false;
    		if( factor()	== false) return false;
    		if( term_()	== false) return false;
    		return true;
	    // term' -> "*" factor term'
	    case MUL:
    		if( Match(MUL)	== false) return false;
    		if( factor()	== false) return false;
    		if( term_()	== false) return false;
    		return true;
	    // term' -> epsilon
      case COMMA:
        return true;
      case RPAREN:
        return true;
      case PLUS:
        return true;
      case SEMI:
        return true;
	    case ENDMARKER:
		    return true;

	  }
	  return false;
  }

  public boolean factor() throws java.io.IOException, Exception
  {
    switch(_token.type)
	  {
	    // factor -> IDENT factor'
	    case IDENT:
    		if( Match(IDENT)	== false) return false;
    		if( factor_()		== false) return false;
    		return true;
	    // factor -> "(" expr ")"
	    case LPAREN:
    		if( Match(LPAREN)	== false) return false;
    		if( expr()		== false) return false;
    		if( Match(RPAREN)	== false) return false;
    		return true;
	    // factor -> INT_LIT
	    case INT_LIT:
		    if( Match(INT_LIT)	== false) return false;
		    return true;
	  }
	  return false;
  }

  public boolean factor_() throws java.io.IOException, Exception
  {
    switch(_token.type)
	  {
	    // factor' -> "(" args ")"
	    case LPAREN:
    		if( Match(LPAREN)	== false) return false;
    		if( args()		== false) return false;
    		if( Match(RPAREN)	== false) return false;
    		return true;
	    // factor' -> epsilon
      case COMMA:
        return true;
      case SEMI:
        return true;
      case RPAREN:
        return true;
	    case ENDMARKER:
		    return true;
      case PLUS:
        return true;
      case EQ:
        return true;
      case MUL:
        return true;
	  }
	  return false;
  }
}
