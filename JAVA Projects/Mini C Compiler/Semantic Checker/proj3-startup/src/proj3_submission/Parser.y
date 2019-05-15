/* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
 * Copyright (C) 2001 Gerwin Klein <lsf@jflex.de>                          *
 * All rights reserved.                                                    *
 *                                                                         *
 * This is a modified version of the example from                          *
 *   http://www.lincom-asg.com/~rjamison/byacc/                            *
 *                                                                         *
 * Thanks to Larry Bell and Bob Jamison for suggestions and comments.      *
 *                                                                         *
 * License: BSD                                                            *
 *                                                                         *
 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */

%{
import java.io.*;
%}

%right  ASSIGN
%left   OR
%left   AND
%left   EQ      NE
%left   LE      LT      GE      GT
%left   PLUS    MINUS
%left   MUL     DIV     MOD
%right  NOT


%token <obj> INT_LIT  FLOAT_LIT  BOOL_LIT  IDENT
%token <obj> INT      FLOAT      BOOL      NEW  BEGIN   END
%token <obj> IF  ELSE    PRINT  WHILE  RETURN  CONTINUE  BREAK
%token <obj> ASSIGN  LPAREN  RPAREN  LBRACE  RBRACE  LBRACKET  RBRACKET  RECORD  SIZE  SEMI  COMMA  DOT  ADDR

%type <obj> program   decl_list  decl          compound_stmt
%type <obj> var_decl  fun_decl   local_decls   local_decl    type_spec
%type <obj> params    if_stmt    break_stmt    continue_stmt print_stmt
%type <obj> stmt_list stmt       return_stmt   expr_stmt     param_list
%type <obj> params    arg_list   args          expr          while_stmt    param

%%


program         : decl_list                                     { Debug("program  ->  decl_list");         $$ = _program($1); checkForMain(); }
                ;

decl_list       : decl_list decl                                { Debug("decl_list  ->  decl_list  decl"); $$ = _decl_list($1, $2); }
                |                                               { Debug("decl_list  ->  eps");             $$ = _decl_list_eps(); }
                ;

decl            : var_decl                                      { Debug("decl  ->  var_decl"); $$ =      _decl_var($1); }
                | fun_decl                                      { Debug("decl  ->  fun_decl"); $$ = _decl_fun($1); }
                ;

var_decl        : type_spec IDENT SEMI                          { Debug("var_decl  ->  type_spec IDENT ;"); $$ = _var_decl($1, $2); }
                ;

type_spec       : BOOL                                          { Debug("type_spec  ->  BOOL"); $$ = _type_spec_BOOL(); }
                | INT                                           { Debug("type_spec  ->  INT");  $$ = _type_spec_INT();  }
                | FLOAT                                         { Debug("type_spec  ->  FLOAT"); $$ = _type_spec_FLOAT(); }
                | RECORD BEGIN                                  { Debug("type_spec  -> RECORD BEGIN local_decls END"); enterScope();  }
                          local_decls                           { $$ = _type_spec_RECORD($3); }
                            END                                 { exitScope(); }
                | type_spec LBRACKET RBRACKET                   { Debug("type_spec  ->  type_spec LBRACKET RBRACKET"); $$ = _type_spec_ARRAY($1); }
                ;

fun_decl        : type_spec IDENT LPAREN params RPAREN          { Debug("fun_decl  ->  type_spec IDENT LPAREN params RPAREN"); $$ = _fun_decl($1, $2, $4);}
                    BEGIN                                       { Debug("entering scope"); enterScope($4); }
                      LBRACE local_decls stmt_list RBRACE       { Debug("func body"); $$ = _func_body($8, $9); }
                    END                                         { Debug("exit and check"); exitScope(); validateFunction(); }
                ;

params          : param_list                                    { Debug("params  ->  param_list"                    ); $$ = _params($1); }
                |                                               { Debug("params  ->  eps"                           ); $$ = _params_eps(); }
                ;

param_list      : param_list COMMA param                        { $$ = _param_list($1, $3); }
                | param                                         { $$ = _param_singleton($1); }
                ;

param           : type_spec IDENT                               { $$ = _param($1, $2); }
                ;

stmt_list       : stmt_list stmt                                { Debug("stmt_list  ->  stmt_list  stmt"            ); $$ = _stmt_list($1, $2); }
                |                                               { Debug("stmt_list  ->  eps"                        ); $$ = _stmt_list_eps();  }
                ;

stmt            : expr_stmt                                     { Debug("stmt  ->  expr_stmt"                       ); $$ = _stmt_expr($1); }
                | compound_stmt                                 { $$ = _stmt_compound($1); }
                | if_stmt                                       { $$ = _stmt_if($1); }
                | while_stmt                                    { $$ = _stmt_while($1); }
                | return_stmt                                   { Debug("stmt  ->  return_stmt"                     ); $$ = _stmt_return($1); }
                | break_stmt                                    { $$ = _stmt_break($1); }
                | continue_stmt                                 { $$ = _stmt_continue($1); }
                | print_stmt                                    { $$ = _stmt_print($1); }
                | SEMI                                          { Debug("stmt  -> ;"                                ); }
                ;

expr_stmt       : IDENT ASSIGN expr_stmt                        { Debug("expr_stmt  ->  IDENT  ASSIGN  expr_stmt"   ); $$ = _expr_stmt_assign($1, $3); }
                | expr SEMI                                     { Debug("expr_stmt  ->  expr ;"                     ); $$ = _expr_stmt($1    ); }
                | IDENT LBRACKET expr RBRACKET ASSIGN expr_stmt { $$ = _expr_stmt_array($1, $3, $6); } // to support array
                | IDENT DOT IDENT ASSIGN expr_stmt          { $$ = _expr_stmt_record($1, $3, $5); } // to support record
                ;

compound_stmt   :  BEGIN                                        { enterScope();}
                    local_decls  stmt_list                      { Debug("compound_stmt  ->  BEGIN  local_decls  stmt_list  END"); $$ = _compound_stmt($3, $4);}
                      END                                       { exitScope();}

return_stmt     : RETURN expr SEMI                              { Debug("return_stmt  ->  RETURN  expr ;"           ); $$ = _ret_stmt($2); }
                ;

local_decls     : local_decls local_decl                        { Debug("local_decls  ->  local_decls local_decl"   ); $$ = _local_decls($1, $2); }
                |                                               { Debug("local_decls  ->  eps"                      ); $$ = _local_decls_eps();}
                ;

local_decl      : type_spec IDENT SEMI                          { Debug("local_decl  ->  type_spec IDENT SEMI"      ); $$ = _local_decl($1, $2); }
                ;

if_stmt         : IF LPAREN expr RPAREN stmt ELSE stmt          { Debug("if_stmt -> IF LPAREN expr RPAREN stmt ELSE stmt"  ); $$ = _if_stmt($3); }
                ;

while_stmt      : WHILE LPAREN expr RPAREN                      { Debug("while_stmt  ->  WHILE  LPAREN  expr  RPAREN  stmt"); $$ = _while_stmt($3); }
                                stmt                            { }
                ;

break_stmt      : BREAK SEMI                                    { Debug ("break_stmt -> BREAK SEMI"); $$ = _break_stmt(); }
                ;

continue_stmt   : CONTINUE SEMI                                 { Debug ("continue_stmt -> BREAK SEMI"); $$ = _continue_stmt(); }
                ;

print_stmt      : PRINT expr SEMI                               { Debug("print_stmt  ->  PRINT  expr  SEMI"); $$ = _print_stmt($2);}
                ;

arg_list        : arg_list COMMA expr                           { Debug("arg_list  ->  arg_list  COMMA  expr"); $$ = _arg_list($1, $3);}
                | expr                                          { Debug("arg_list  ->  expr"); $$ = _arg_list_expr($1);}
                ;

args            : arg_list                                      { Debug("args  ->  arg_list"); $$ = _args($1);}
                |                                               { Debug("args  ->  eps"     ); $$ = _args_eps(); }
                ;

expr            : expr EQ expr                                  { $$ = _expr_EQ_expr            ($1, $3);  }
                | expr AND expr                                 { $$ = _expr_AND_expr           ($1, $3);  }
                | expr OR expr                                  { $$ = _expr_OR_expr            ($1, $3);  }
                | NOT expr                                      { $$ = _NOT_expr                ($2);      }
                | expr NE expr                                  { $$ = _expr_NE_expr            ($1, $3);  }
                | expr LE expr                                  { $$ = _expr_LE_expr            ($1, $3);  }
                | expr LT expr                                  { $$ = _expr_LT_expr            ($1, $3);  }
                | expr GE expr                                  { $$ = _expr_GE_expr            ($1, $3);  }
                | expr GT expr                                  { $$ = _expr_GT_expr            ($1, $3);  }
                | expr PLUS expr                                { $$ = _expr_PLUS_expr          ($1, $3);  }
                | expr MINUS expr                               { $$ = _expr_MINUS_expr         ($1, $3);  }
                | expr MUL expr                                 { $$ = _expr_MUL_expr           ($1, $3);  }
                | expr DIV expr                                 { $$ = _expr_DIV_expr           ($1, $3);  }
                | LPAREN expr RPAREN                            { $$ = _LPAREN_expr_RPAREN      ($2    );  }
                | IDENT                                         { $$ = _expr_IDENT              ($1    );  }
                | IDENT LPAREN args RPAREN                      { $$ = _IDENT_LPAREN_args_RPAREN($1, $3);  }
                | BOOL_LIT                                      { $$ = _expr_BOOLLIT            ($1    );  }
                | INT_LIT                                       { $$ = _expr_INTLIT             ($1    );  }
                | FLOAT_LIT                                     { $$ = _expr_FLOATLIT           ($1    );  }
                | IDENT LBRACKET expr RBRACKET                  { $$ = _expr_array              ($1, $3);  } // to support array
                | IDENT DOT SIZE                                { $$ = _expr_record             ($1    );  } // to support record
                // | NEW type_spec LBRACKET expr RBRACKET          { $$ = _expr_array_assign       ($2, $4);  } // to support array assignment
                | IDENT DOT IDENT                               { $$ = _expr_record_assign      ($1, $3);  } // to support record
                ;

%%
    private Lexer lexer;

    private int yylex () {
        int yyl_return = -1;
        try {
            yylval =  new ParserVal(0);
            yyl_return = lexer.yylex();
        }
        catch (IOException e) {
            System.out.println("IO error :"+e);
        }
        return yyl_return;
    }

    public void yyerror (String error) {
        System.out.println ("Error: " + error);
    }

    public Parser(Reader r) {
        lexer =  new Lexer(r, this);
    }
