/* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
 * Copyright (C) 2000 Gerwin Klein <lsf@jflex.de>                          *
 * All rights reserved.                                                    *
 *                                                                         *
 * Thanks to Larry Bell and Bob Jamison for suggestions and comments.      *
 *                                                                         *
 * License: BSD                                                            *
 *                                                                         *
 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */
import java.io.*;

%%

%class Lexer
%byaccj

%{

  public Parser   yyparser;
  public int      lineno;

  public Lexer(java.io.Reader r, Parser yyparser) {
    this(r);
    this.yyparser = yyparser;

    this.lineno   = 1;
  }

%}

int        = [0-9]+
float      = {int}(.{int})
identifier = [a-zA-Z_][a-zA-Z_0-9]*
newline    = \n
whitespace = [ \t\r]+
comment    = "//".*

%%

"main"                              { yyparser.yylval = new ParserVal(null            ); return Parser.MAIN   ; }
"int"                               { yyparser.yylval = new ParserVal(null            ); return Parser.INT    ; }
"float"                             { yyparser.yylval = new ParserVal(null            ); return Parser.FLOAT  ; }
"print"                             { yyparser.yylval = new ParserVal(null            ); return Parser.PRINT  ; }
"bool"                              { yyparser.yylval = new ParserVal(null            ); return Parser.BOOL   ; }
"record"                            { yyparser.yylval = new ParserVal(null            ); return Parser.REC    ; }
"size"                              { yyparser.yylval = new ParserVal(null            ); return Parser.SIZE   ; }
"new"                               { yyparser.yylval = new ParserVal(null            ); return Parser.NEW    ; }
"while"                             { yyparser.yylval = new ParserVal(null            ); return Parser.WHILE  ; }
"if"                                { yyparser.yylval = new ParserVal(null            ); return Parser.IF     ; }
"else"                              { yyparser.yylval = new ParserVal(null            ); return Parser.ELSE   ; }
"return"                            { yyparser.yylval = new ParserVal(null            ); return Parser.RET    ; }
"break"                             { yyparser.yylval = new ParserVal(null            ); return Parser.BREAK  ; }
"continue"                          { yyparser.yylval = new ParserVal(null            ); return Parser.CONT   ; }
"and"                               { yyparser.yylval = new ParserVal(null            ); return Parser.AND    ; }
"or"                                { yyparser.yylval = new ParserVal(null            ); return Parser.OR     ; }
"not"                               { yyparser.yylval = new ParserVal(null            ); return Parser.NOT    ; }
"true"                              { yyparser.yylval = new ParserVal(null            ); return Parser.TRUE   ; }
"false"                             { yyparser.yylval = new ParserVal(null            ); return Parser.FALSE  ; }

"{"                                 { yyparser.yylval = new ParserVal(null            ); return Parser.BEGIN  ; }
"}"                                 { yyparser.yylval = new ParserVal(null            ); return Parser.END    ; }
"("                                 { yyparser.yylval = new ParserVal(null            ); return Parser.LPAREN ; }
")"                                 { yyparser.yylval = new ParserVal(null            ); return Parser.RPAREN ; }
"="                                 { yyparser.yylval = new ParserVal(null            ); return Parser.ASSIGN ; }
"+"                                 { yyparser.yylval = new ParserVal(null            ); return Parser.PLUS   ; }
"*"                                 { yyparser.yylval = new ParserVal(null            ); return Parser.MUL    ; }
";"                                 { yyparser.yylval = new ParserVal(null            ); return Parser.SEMI   ; }
"&"                                 { yyparser.yylval = new ParserVal(null            ); return Parser.AMP    ; }
"["                                 { yyparser.yylval = new ParserVal(null            ); return Parser.LSQ    ; }
"]"                                 { yyparser.yylval = new ParserVal(null            ); return Parser.RSQ    ; }
"<"                                 { yyparser.yylval = new ParserVal(null            ); return Parser.LST    ; }
">"                                 { yyparser.yylval = new ParserVal(null            ); return Parser.GRT    ; }
"-"                                 { yyparser.yylval = new ParserVal(null            ); return Parser.MIN    ; }
"/"                                 { yyparser.yylval = new ParserVal(null            ); return Parser.FRWD   ; }
"%"                                 { yyparser.yylval = new ParserVal(null            ); return Parser.MOD    ; }
","                                 { yyparser.yylval = new ParserVal(null            ); return Parser.COMM   ; }
"."                                 { yyparser.yylval = new ParserVal(null            ); return Parser.DOT    ; }
"=="                                { yyparser.yylval = new ParserVal(null            ); return Parser.EQV    ; }
"!="                                { yyparser.yylval = new ParserVal(null            ); return Parser.NOTEQ  ; }
"<="                                { yyparser.yylval = new ParserVal(null            ); return Parser.LSTE   ; }
">="                                { yyparser.yylval = new ParserVal(null            ); return Parser.GRTE   ; }

{int}                               { yyparser.yylval = new ParserVal((Object)yytext()); return Parser.INT_LIT; }
{identifier}                        { yyparser.yylval = new ParserVal((Object)yytext()); return Parser.IDENT  ; }
{float}                             { yyparser.yylval = new ParserVal((Object)yytext()); return Parser.FLO_VAL; }
{comment}                           {           System.out.print(               yytext()); /* skip */ }
{newline}                           { lineno++; System.out.print(               yytext()); /* skip */ }
{whitespace}                        {           System.out.print(               yytext()); /* skip */ }


\b     { System.err.println("Sorry, backspace doesn't work"); }

/* error fallback */
[^]    { System.err.println("Error: unexpected character '"+yytext()+"'"); return -1; }
