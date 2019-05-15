//### This file created by BYACC 1.8(/Java extension  1.15)
//### Java capabilities added 7 Jan 97, Bob Jamison
//### Updated : 27 Nov 97  -- Bob Jamison, Joe Nieten
//###           01 Jan 98  -- Bob Jamison -- fixed generic semantic constructor
//###           01 Jun 99  -- Bob Jamison -- added Runnable support
//###           06 Aug 00  -- Bob Jamison -- made state variables class-global
//###           03 Jan 01  -- Bob Jamison -- improved flags, tracing
//###           16 May 01  -- Bob Jamison -- added custom stack sizing
//###           04 Mar 02  -- Yuval Oren  -- improved java performance, added options
//###           14 Mar 02  -- Tomas Hurka -- -d support, static initializer workaround
//### Please send bug reports to tom@hukatronic.cz
//### static char yysccsid[] = "@(#)yaccpar	1.8 (Berkeley) 01/20/90";






//#line 15 "./Parser.y"
import java.io.*;
//#line 19 "Parser.java"




public class Parser
             extends ParserImpl
{

boolean yydebug;        //do I want debug output?
int yynerrs;            //number of errors so far
int yyerrflag;          //was there an error?
int yychar;             //the current working character

//########## MESSAGES ##########
//###############################################################
// method: debug
//###############################################################
void debug(String msg)
{
  if (yydebug)
    System.out.println(msg);
}

//########## STATE STACK ##########
final static int YYSTACKSIZE = 500;  //maximum stack size
int statestk[] = new int[YYSTACKSIZE]; //state stack
int stateptr;
int stateptrmax;                     //highest index of stackptr
int statemax;                        //state when highest index reached
//###############################################################
// methods: state stack push,pop,drop,peek
//###############################################################
final void state_push(int state)
{
  try {
		stateptr++;
		statestk[stateptr]=state;
	 }
	 catch (ArrayIndexOutOfBoundsException e) {
     int oldsize = statestk.length;
     int newsize = oldsize * 2;
     int[] newstack = new int[newsize];
     System.arraycopy(statestk,0,newstack,0,oldsize);
     statestk = newstack;
     statestk[stateptr]=state;
  }
}
final int state_pop()
{
  return statestk[stateptr--];
}
final void state_drop(int cnt)
{
  stateptr -= cnt; 
}
final int state_peek(int relative)
{
  return statestk[stateptr-relative];
}
//###############################################################
// method: init_stacks : allocate and prepare stacks
//###############################################################
final boolean init_stacks()
{
  stateptr = -1;
  val_init();
  return true;
}
//###############################################################
// method: dump_stacks : show n levels of the stacks
//###############################################################
void dump_stacks(int count)
{
int i;
  System.out.println("=index==state====value=     s:"+stateptr+"  v:"+valptr);
  for (i=0;i<count;i++)
    System.out.println(" "+i+"    "+statestk[i]+"      "+valstk[i]);
  System.out.println("======================");
}


//########## SEMANTIC VALUES ##########
//public class ParserVal is defined in ParserVal.java


String   yytext;//user variable to return contextual strings
ParserVal yyval; //used to return semantic vals from action routines
ParserVal yylval;//the 'lval' (result) I got from yylex()
ParserVal valstk[];
int valptr;
//###############################################################
// methods: value stack push,pop,drop,peek.
//###############################################################
void val_init()
{
  valstk=new ParserVal[YYSTACKSIZE];
  yyval=new ParserVal();
  yylval=new ParserVal();
  valptr=-1;
}
void val_push(ParserVal val)
{
  if (valptr>=YYSTACKSIZE)
    return;
  valstk[++valptr]=val;
}
ParserVal val_pop()
{
  if (valptr<0)
    return new ParserVal();
  return valstk[valptr--];
}
void val_drop(int cnt)
{
int ptr;
  ptr=valptr-cnt;
  if (ptr<0)
    return;
  valptr = ptr;
}
ParserVal val_peek(int relative)
{
int ptr;
  ptr=valptr-relative;
  if (ptr<0)
    return new ParserVal();
  return valstk[ptr];
}
final ParserVal dup_yyval(ParserVal val)
{
  ParserVal dup = new ParserVal();
  dup.ival = val.ival;
  dup.dval = val.dval;
  dup.sval = val.sval;
  dup.obj = val.obj;
  return dup;
}
//#### end semantic value section ####
public final static short ASSIGN=257;
public final static short OR=258;
public final static short AND=259;
public final static short EQ=260;
public final static short NE=261;
public final static short LE=262;
public final static short LT=263;
public final static short GE=264;
public final static short GT=265;
public final static short PLUS=266;
public final static short MINUS=267;
public final static short MUL=268;
public final static short DIV=269;
public final static short MOD=270;
public final static short NOT=271;
public final static short INT_LIT=272;
public final static short FLOAT_LIT=273;
public final static short BOOL_LIT=274;
public final static short IDENT=275;
public final static short INT=276;
public final static short FLOAT=277;
public final static short BOOL=278;
public final static short NEW=279;
public final static short BEGIN=280;
public final static short END=281;
public final static short IF=282;
public final static short ELSE=283;
public final static short PRINT=284;
public final static short WHILE=285;
public final static short RETURN=286;
public final static short CONTINUE=287;
public final static short BREAK=288;
public final static short LPAREN=289;
public final static short RPAREN=290;
public final static short LBRACE=291;
public final static short RBRACE=292;
public final static short LBRACKET=293;
public final static short RBRACKET=294;
public final static short RECORD=295;
public final static short SIZE=296;
public final static short SEMI=297;
public final static short COMMA=298;
public final static short DOT=299;
public final static short ADDR=300;
public final static short YYERRCODE=256;
final static short yylhs[] = {                           -1,
    0,    1,    1,    2,    2,    4,    8,    8,    8,   24,
   25,    8,    8,   26,   27,   28,    5,    9,    9,   18,
   18,   23,   14,   14,   15,   15,   15,   15,   15,   15,
   15,   15,   15,   17,   17,   17,   17,   29,   30,    3,
   16,    6,    6,    7,   10,   31,   22,   11,   12,   13,
   19,   19,   20,   20,   21,   21,   21,   21,   21,   21,
   21,   21,   21,   21,   21,   21,   21,   21,   21,   21,
   21,   21,   21,   21,   21,   21,
};
final static short yylen[] = {                            2,
    1,    2,    0,    1,    1,    3,    1,    1,    1,    0,
    0,    6,    3,    0,    0,    0,   14,    1,    0,    3,
    1,    2,    2,    0,    1,    1,    1,    1,    1,    1,
    1,    1,    1,    3,    2,    6,    5,    0,    0,    6,
    3,    2,    0,    3,    7,    0,    6,    2,    2,    3,
    3,    1,    1,    0,    3,    3,    3,    2,    3,    3,
    3,    3,    3,    3,    3,    3,    3,    3,    1,    4,
    1,    1,    1,    4,    3,    3,
};
final static short yydefred[] = {                         3,
    0,    0,    8,    9,    7,    0,    2,    4,    5,    0,
   10,    0,    0,   43,    0,    6,   13,    0,    0,    0,
    0,   21,   42,    0,    0,   22,   14,    0,    0,   12,
    0,   20,   44,   15,    0,   43,    0,    0,    0,   72,
   73,   71,    0,   38,    0,    0,    0,    0,    0,    0,
    0,   16,   33,   26,   27,   30,   31,   32,   23,   29,
   25,    0,   28,    0,   58,    0,    0,    0,    0,   43,
    0,    0,    0,    0,   49,   48,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
   35,    0,    0,   34,    0,    0,    0,    0,    0,   75,
    0,    0,   50,    0,   41,   68,   17,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,   66,   67,    0,
   76,    0,   70,    0,    0,    0,    0,   46,   74,    0,
    0,   37,    0,    0,    0,   36,   40,    0,   47,   45,
};
final static short yydgoto[] = {                          1,
    2,    7,   54,    8,    9,   18,   23,   24,   20,   55,
   56,   57,   58,   38,   59,   60,   61,   21,   95,   96,
   62,   63,   22,   14,   25,   31,   35,   78,   70,  133,
  135,
};
final static short yysindex[] = {                         0,
    0, -262,    0,    0,    0, -267,    0,    0,    0, -270,
    0, -279, -286,    0, -262,    0,    0, -262, -256, -268,
 -287,    0,    0, -254, -250,    0,    0, -262, -259,    0,
 -245,    0,    0,    0, -196,    0, -262,  195,   26,    0,
    0,    0, -237,    0, -192,   26, -189,   26, -243, -199,
   26,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,   -1,    0, -154,    0,   90,   26,   26, -272,    0,
   26,   11,   26,   23,    0,    0,  151, -180,   26,   26,
   26,   26,   26,   26,   26,   26,   26,   26,   26,   26,
    0,   26, -269,    0, -181, -172,  308,  127, -138,    0,
 -262,  184,    0,  196,    0,    0,    0,  163,  318,  -22,
  -22, -187, -187, -187, -187, -210, -210,    0,    0,  139,
    0,   26,    0, -136,   90,  268,  268,    0,    0,  308,
   90,    0, -159, -147,  268,    0,    0,  268,    0,    0,
};
final static short yyrindex[] = {                         0,
    0,  137,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0, -152,    0,    0, -140,    0,    0,
 -148,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,  222,    0,    0,    0,
    0,    0,   63,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0, -191,    0,    0, -144,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0, -132,    0, -235,    0,   75,    0,
  249,    0,    0,    0,    0,    0,    0,  -59, -233,  -83,
  -42, -150, -109,  -92,  -68, -174, -133,    0,    0,    0,
    0,    0,    0,   87,    0, -122,    0,    0,    0, -194,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
};
final static short yygindex[] = {                         0,
    0,    0,    0,    0,    0,  -34,    0,    2,    0,    0,
    0,    0,    0,   42,  -33,    0,  -65,    0,    0,    0,
  -39,    0,  132,    0,    0,    0,    0,    0,    0,    0,
    0,
};
final static int YYTABLESIZE=587;
static short yytable[];
static { yytable();}
static void yytable(){
yytable = new short[]{                         65,
   94,   37,   99,   10,   12,  121,   72,   17,   74,   15,
   28,   77,   11,    3,    4,    5,   19,   16,   26,   66,
   29,   27,   13,  100,   56,   56,  100,   97,   98,   19,
   30,  102,    6,  104,   34,  101,   13,   33,   13,  108,
  109,  110,  111,  112,  113,  114,  115,  116,  117,  118,
  119,   67,  120,   75,   52,   68,   56,   89,   90,  132,
   56,   69,   52,   56,   56,  136,   69,   69,   69,   69,
   69,   69,   69,   69,   69,   69,   69,   69,   87,   88,
   89,   90,  130,   64,   64,   64,   64,   64,   64,   64,
   64,   64,   64,  134,   36,   51,   71,   76,   69,   73,
  107,  139,   69,   51,  140,   69,   69,   60,   60,   60,
   60,   60,   60,   60,   60,   64,  122,  123,  125,   64,
  131,  137,   64,   64,   65,   65,   65,   65,   65,   65,
   65,   65,   65,   65,   67,  138,    1,   19,   92,   60,
   11,   18,  126,   60,   93,   54,   60,   60,   61,   61,
   61,   61,   61,   61,   61,   61,   65,   53,   39,   32,
   65,    0,    0,   65,   65,   62,   62,   62,   62,   62,
   62,   62,   62,    0,   55,   55,   55,   55,    0,    0,
   61,    0,    0,    0,   61,    0,    0,   61,   61,   63,
   63,   63,   63,   63,   63,   63,   63,   62,   57,    0,
    0,   62,    0,    0,   62,   62,   55,    0,    0,    0,
   55,    0,    0,   55,   55,   59,   59,   59,   59,    0,
    0,   63,    0,    0,    0,   63,    0,    0,   63,   63,
   57,    0,    0,    0,   57,    0,    0,   57,   57,   83,
   84,   85,   86,   87,   88,   89,   90,   59,    0,    0,
    0,   59,    0,    0,   59,   59,   79,   80,   81,   82,
   83,   84,   85,   86,   87,   88,   89,   90,   79,   80,
   81,   82,   83,   84,   85,   86,   87,   88,   89,   90,
   79,   80,   81,   82,   83,   84,   85,   86,   87,   88,
   89,   90,    0,    0,    0,   91,   39,   40,   41,   42,
   64,    0,    0,    0,    0,    0,    0,  103,    0,    0,
    0,    0,    0,    0,   51,    0,    0,    0,    0,  105,
   69,   69,   69,   69,   69,   69,   69,   69,   69,   69,
   69,   69,   76,   76,   76,   76,   76,   76,   76,   76,
   76,   76,   76,   76,   74,   74,   74,   74,   74,   74,
   74,   74,   74,   74,   74,   74,    0,    0,    0,   69,
   39,   40,   41,   42,   43,    0,    0,    0,    0,    0,
    0,   76,    0,    0,    0,    0,    0,    0,   51,    0,
    0,    0,    0,   74,   79,   80,   81,   82,   83,   84,
   85,   86,   87,   88,   89,   90,   79,   80,   81,   82,
   83,   84,   85,   86,   87,   88,   89,   90,   79,   80,
   81,   82,   83,   84,   85,   86,   87,   88,   89,   90,
  124,   80,   81,   82,   83,   84,   85,   86,   87,   88,
   89,   90,  129,    0,    0,    0,    0,    0,    0,    0,
  106,   79,   80,   81,   82,   83,   84,   85,   86,   87,
   88,   89,   90,   79,   80,   81,   82,   83,   84,   85,
   86,   87,   88,   89,   90,   39,   40,   41,   42,   43,
    0,    0,    0,  127,   44,    0,   45,    0,   46,   47,
   48,   49,   50,   51,    0,  128,   52,    0,    0,    0,
    0,   53,   24,   24,   24,   24,   24,    0,    0,    0,
    0,   24,    0,   24,    0,   24,   24,   24,   24,   24,
   24,    0,    0,   24,    0,    0,    0,    0,   24,   24,
   24,   24,   24,   24,    0,    0,    0,    0,   24,   24,
   24,    0,   24,   24,   24,   24,   24,   24,   39,   40,
   41,   42,   43,    0,    0,   24,    0,   44,    0,   45,
    0,   46,   47,   48,   49,   50,   51,    0,    0,    0,
    0,    0,    0,    0,   53,   79,   80,   81,   82,   83,
   84,   85,   86,   87,   88,   89,   90,   81,   82,   83,
   84,   85,   86,   87,   88,   89,   90,
};
}
static short yycheck[];
static { yycheck(); }
static void yycheck() {
yycheck = new short[] {                         39,
   66,   36,  275,    2,  275,  275,   46,  294,   48,  289,
  298,   51,  280,  276,  277,  278,   15,  297,  275,  257,
  275,  290,  293,  296,  258,  259,  296,   67,   68,   28,
  281,   71,  295,   73,  280,   70,  293,  297,  293,   79,
   80,   81,   82,   83,   84,   85,   86,   87,   88,   89,
   90,  289,   92,  297,  290,  293,  290,  268,  269,  125,
  294,  299,  298,  297,  298,  131,  258,  259,  260,  261,
  262,  263,  264,  265,  266,  267,  268,  269,  266,  267,
  268,  269,  122,  258,  259,  260,  261,  262,  263,  264,
  265,  266,  267,  127,  291,  290,  289,  297,  290,  289,
  281,  135,  294,  298,  138,  297,  298,  258,  259,  260,
  261,  262,  263,  264,  265,  290,  298,  290,  257,  294,
  257,  281,  297,  298,  258,  259,  260,  261,  262,  263,
  264,  265,  266,  267,  289,  283,    0,  290,  293,  290,
  281,  290,  101,  294,  299,  290,  297,  298,  258,  259,
  260,  261,  262,  263,  264,  265,  290,  290,  281,   28,
  294,   -1,   -1,  297,  298,  258,  259,  260,  261,  262,
  263,  264,  265,   -1,  258,  259,  260,  261,   -1,   -1,
  290,   -1,   -1,   -1,  294,   -1,   -1,  297,  298,  258,
  259,  260,  261,  262,  263,  264,  265,  290,  258,   -1,
   -1,  294,   -1,   -1,  297,  298,  290,   -1,   -1,   -1,
  294,   -1,   -1,  297,  298,  258,  259,  260,  261,   -1,
   -1,  290,   -1,   -1,   -1,  294,   -1,   -1,  297,  298,
  290,   -1,   -1,   -1,  294,   -1,   -1,  297,  298,  262,
  263,  264,  265,  266,  267,  268,  269,  290,   -1,   -1,
   -1,  294,   -1,   -1,  297,  298,  258,  259,  260,  261,
  262,  263,  264,  265,  266,  267,  268,  269,  258,  259,
  260,  261,  262,  263,  264,  265,  266,  267,  268,  269,
  258,  259,  260,  261,  262,  263,  264,  265,  266,  267,
  268,  269,   -1,   -1,   -1,  297,  271,  272,  273,  274,
  275,   -1,   -1,   -1,   -1,   -1,   -1,  297,   -1,   -1,
   -1,   -1,   -1,   -1,  289,   -1,   -1,   -1,   -1,  297,
  258,  259,  260,  261,  262,  263,  264,  265,  266,  267,
  268,  269,  258,  259,  260,  261,  262,  263,  264,  265,
  266,  267,  268,  269,  258,  259,  260,  261,  262,  263,
  264,  265,  266,  267,  268,  269,   -1,   -1,   -1,  297,
  271,  272,  273,  274,  275,   -1,   -1,   -1,   -1,   -1,
   -1,  297,   -1,   -1,   -1,   -1,   -1,   -1,  289,   -1,
   -1,   -1,   -1,  297,  258,  259,  260,  261,  262,  263,
  264,  265,  266,  267,  268,  269,  258,  259,  260,  261,
  262,  263,  264,  265,  266,  267,  268,  269,  258,  259,
  260,  261,  262,  263,  264,  265,  266,  267,  268,  269,
  294,  259,  260,  261,  262,  263,  264,  265,  266,  267,
  268,  269,  294,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
  290,  258,  259,  260,  261,  262,  263,  264,  265,  266,
  267,  268,  269,  258,  259,  260,  261,  262,  263,  264,
  265,  266,  267,  268,  269,  271,  272,  273,  274,  275,
   -1,   -1,   -1,  290,  280,   -1,  282,   -1,  284,  285,
  286,  287,  288,  289,   -1,  290,  292,   -1,   -1,   -1,
   -1,  297,  271,  272,  273,  274,  275,   -1,   -1,   -1,
   -1,  280,   -1,  282,   -1,  284,  285,  286,  287,  288,
  289,   -1,   -1,  292,   -1,   -1,   -1,   -1,  297,  271,
  272,  273,  274,  275,   -1,   -1,   -1,   -1,  280,  281,
  282,   -1,  284,  285,  286,  287,  288,  289,  271,  272,
  273,  274,  275,   -1,   -1,  297,   -1,  280,   -1,  282,
   -1,  284,  285,  286,  287,  288,  289,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,  297,  258,  259,  260,  261,  262,
  263,  264,  265,  266,  267,  268,  269,  260,  261,  262,
  263,  264,  265,  266,  267,  268,  269,
};
}
final static short YYFINAL=1;
final static short YYMAXTOKEN=300;
final static String yyname[] = {
"end-of-file",null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,"ASSIGN","OR","AND","EQ","NE","LE","LT","GE","GT","PLUS","MINUS",
"MUL","DIV","MOD","NOT","INT_LIT","FLOAT_LIT","BOOL_LIT","IDENT","INT","FLOAT",
"BOOL","NEW","BEGIN","END","IF","ELSE","PRINT","WHILE","RETURN","CONTINUE",
"BREAK","LPAREN","RPAREN","LBRACE","RBRACE","LBRACKET","RBRACKET","RECORD",
"SIZE","SEMI","COMMA","DOT","ADDR",
};
final static String yyrule[] = {
"$accept : program",
"program : decl_list",
"decl_list : decl_list decl",
"decl_list :",
"decl : var_decl",
"decl : fun_decl",
"var_decl : type_spec IDENT SEMI",
"type_spec : BOOL",
"type_spec : INT",
"type_spec : FLOAT",
"$$1 :",
"$$2 :",
"type_spec : RECORD BEGIN $$1 local_decls $$2 END",
"type_spec : type_spec LBRACKET RBRACKET",
"$$3 :",
"$$4 :",
"$$5 :",
"fun_decl : type_spec IDENT LPAREN params RPAREN $$3 BEGIN $$4 LBRACE local_decls stmt_list RBRACE $$5 END",
"params : param_list",
"params :",
"param_list : param_list COMMA param",
"param_list : param",
"param : type_spec IDENT",
"stmt_list : stmt_list stmt",
"stmt_list :",
"stmt : expr_stmt",
"stmt : compound_stmt",
"stmt : if_stmt",
"stmt : while_stmt",
"stmt : return_stmt",
"stmt : break_stmt",
"stmt : continue_stmt",
"stmt : print_stmt",
"stmt : SEMI",
"expr_stmt : IDENT ASSIGN expr_stmt",
"expr_stmt : expr SEMI",
"expr_stmt : IDENT LBRACKET expr RBRACKET ASSIGN expr_stmt",
"expr_stmt : IDENT DOT IDENT ASSIGN expr_stmt",
"$$6 :",
"$$7 :",
"compound_stmt : BEGIN $$6 local_decls stmt_list $$7 END",
"return_stmt : RETURN expr SEMI",
"local_decls : local_decls local_decl",
"local_decls :",
"local_decl : type_spec IDENT SEMI",
"if_stmt : IF LPAREN expr RPAREN stmt ELSE stmt",
"$$8 :",
"while_stmt : WHILE LPAREN expr RPAREN $$8 stmt",
"break_stmt : BREAK SEMI",
"continue_stmt : CONTINUE SEMI",
"print_stmt : PRINT expr SEMI",
"arg_list : arg_list COMMA expr",
"arg_list : expr",
"args : arg_list",
"args :",
"expr : expr EQ expr",
"expr : expr AND expr",
"expr : expr OR expr",
"expr : NOT expr",
"expr : expr NE expr",
"expr : expr LE expr",
"expr : expr LT expr",
"expr : expr GE expr",
"expr : expr GT expr",
"expr : expr PLUS expr",
"expr : expr MINUS expr",
"expr : expr MUL expr",
"expr : expr DIV expr",
"expr : LPAREN expr RPAREN",
"expr : IDENT",
"expr : IDENT LPAREN args RPAREN",
"expr : BOOL_LIT",
"expr : INT_LIT",
"expr : FLOAT_LIT",
"expr : IDENT LBRACKET expr RBRACKET",
"expr : IDENT DOT SIZE",
"expr : IDENT DOT IDENT",
};

//#line 167 "./Parser.y"
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
//#line 471 "Parser.java"
//###############################################################
// method: yylexdebug : check lexer state
//###############################################################
void yylexdebug(int state,int ch)
{
String s=null;
  if (ch < 0) ch=0;
  if (ch <= YYMAXTOKEN) //check index bounds
     s = yyname[ch];    //now get it
  if (s==null)
    s = "illegal-symbol";
  debug("state "+state+", reading "+ch+" ("+s+")");
}





//The following are now global, to aid in error reporting
int yyn;       //next next thing to do
int yym;       //
int yystate;   //current parsing state from state table
String yys;    //current token string


//###############################################################
// method: yyparse : parse input and execute indicated items
//###############################################################
int yyparse()
throws Exception
{
boolean doaction;
  init_stacks();
  yynerrs = 0;
  yyerrflag = 0;
  yychar = -1;          //impossible char forces a read
  yystate=0;            //initial state
  state_push(yystate);  //save it
  val_push(yylval);     //save empty value
  while (true) //until parsing is done, either correctly, or w/error
    {
    doaction=true;
    if (yydebug) debug("loop"); 
    //#### NEXT ACTION (from reduction table)
    for (yyn=yydefred[yystate];yyn==0;yyn=yydefred[yystate])
      {
      if (yydebug) debug("yyn:"+yyn+"  state:"+yystate+"  yychar:"+yychar);
      if (yychar < 0)      //we want a char?
        {
        yychar = yylex();  //get next token
        if (yydebug) debug(" next yychar:"+yychar);
        //#### ERROR CHECK ####
        if (yychar < 0)    //it it didn't work/error
          {
          yychar = 0;      //change it to default string (no -1!)
          if (yydebug)
            yylexdebug(yystate,yychar);
          }
        }//yychar<0
      yyn = yysindex[yystate];  //get amount to shift by (shift index)
      if ((yyn != 0) && (yyn += yychar) >= 0 &&
          yyn <= YYTABLESIZE && yycheck[yyn] == yychar)
        {
        if (yydebug)
          debug("state "+yystate+", shifting to state "+yytable[yyn]);
        //#### NEXT STATE ####
        yystate = yytable[yyn];//we are in a new state
        state_push(yystate);   //save it
        val_push(yylval);      //push our lval as the input for next rule
        yychar = -1;           //since we have 'eaten' a token, say we need another
        if (yyerrflag > 0)     //have we recovered an error?
           --yyerrflag;        //give ourselves credit
        doaction=false;        //but don't process yet
        break;   //quit the yyn=0 loop
        }

    yyn = yyrindex[yystate];  //reduce
    if ((yyn !=0 ) && (yyn += yychar) >= 0 &&
            yyn <= YYTABLESIZE && yycheck[yyn] == yychar)
      {   //we reduced!
      if (yydebug) debug("reduce");
      yyn = yytable[yyn];
      doaction=true; //get ready to execute
      break;         //drop down to actions
      }
    else //ERROR RECOVERY
      {
      if (yyerrflag==0)
        {
        yyerror("syntax error");
        yynerrs++;
        }
      if (yyerrflag < 3) //low error count?
        {
        yyerrflag = 3;
        while (true)   //do until break
          {
          if (stateptr<0)   //check for under & overflow here
            {
            yyerror("stack underflow. aborting...");  //note lower case 's'
            return 1;
            }
          yyn = yysindex[state_peek(0)];
          if ((yyn != 0) && (yyn += YYERRCODE) >= 0 &&
                    yyn <= YYTABLESIZE && yycheck[yyn] == YYERRCODE)
            {
            if (yydebug)
              debug("state "+state_peek(0)+", error recovery shifting to state "+yytable[yyn]+" ");
            yystate = yytable[yyn];
            state_push(yystate);
            val_push(yylval);
            doaction=false;
            break;
            }
          else
            {
            if (yydebug)
              debug("error recovery discarding state "+state_peek(0)+" ");
            if (stateptr<0)   //check for under & overflow here
              {
              yyerror("Stack underflow. aborting...");  //capital 'S'
              return 1;
              }
            state_pop();
            val_pop();
            }
          }
        }
      else            //discard this token
        {
        if (yychar == 0)
          return 1; //yyabort
        if (yydebug)
          {
          yys = null;
          if (yychar <= YYMAXTOKEN) yys = yyname[yychar];
          if (yys == null) yys = "illegal-symbol";
          debug("state "+yystate+", error recovery discards token "+yychar+" ("+yys+")");
          }
        yychar = -1;  //read another
        }
      }//end error recovery
    }//yyn=0 loop
    if (!doaction)   //any reason not to proceed?
      continue;      //skip action
    yym = yylen[yyn];          //get count of terminals on rhs
    if (yydebug)
      debug("state "+yystate+", reducing "+yym+" by rule "+yyn+" ("+yyrule[yyn]+")");
    if (yym>0)                 //if count of rhs not 'nil'
      yyval = val_peek(yym-1); //get current semantic value
    yyval = dup_yyval(yyval); //duplicate yyval if ParserVal is used as semantic value
    switch(yyn)
      {
//########## USER-SUPPLIED ACTIONS ##########
case 1:
//#line 42 "./Parser.y"
{ Debug("program  ->  decl_list");         yyval.obj = _program(val_peek(0).obj); checkForMain(); }
break;
case 2:
//#line 45 "./Parser.y"
{ Debug("decl_list  ->  decl_list  decl"); yyval.obj = _decl_list(val_peek(1).obj, val_peek(0).obj); }
break;
case 3:
//#line 46 "./Parser.y"
{ Debug("decl_list  ->  eps");             yyval.obj = _decl_list_eps(); }
break;
case 4:
//#line 49 "./Parser.y"
{ Debug("decl  ->  var_decl"); yyval.obj =      _decl_var(val_peek(0).obj); }
break;
case 5:
//#line 50 "./Parser.y"
{ Debug("decl  ->  fun_decl"); yyval.obj = _decl_fun(val_peek(0).obj); }
break;
case 6:
//#line 53 "./Parser.y"
{ Debug("var_decl  ->  type_spec IDENT ;"); yyval.obj = _var_decl(val_peek(2).obj, val_peek(1).obj); }
break;
case 7:
//#line 56 "./Parser.y"
{ Debug("type_spec  ->  BOOL"); yyval.obj = _type_spec_BOOL(); }
break;
case 8:
//#line 57 "./Parser.y"
{ Debug("type_spec  ->  INT");  yyval.obj = _type_spec_INT();  }
break;
case 9:
//#line 58 "./Parser.y"
{ Debug("type_spec  ->  FLOAT"); yyval.obj = _type_spec_FLOAT(); }
break;
case 10:
//#line 59 "./Parser.y"
{ Debug("type_spec  -> RECORD BEGIN local_decls END"); enterScope();  }
break;
case 11:
//#line 60 "./Parser.y"
{ yyval.obj = _type_spec_RECORD(val_peek(1).obj); }
break;
case 12:
//#line 61 "./Parser.y"
{ exitScope(); }
break;
case 13:
//#line 62 "./Parser.y"
{ Debug("type_spec  ->  type_spec LBRACKET RBRACKET"); yyval.obj = _type_spec_ARRAY(val_peek(2).obj); }
break;
case 14:
//#line 65 "./Parser.y"
{ Debug("fun_decl  ->  type_spec IDENT LPAREN params RPAREN"); yyval.obj = _fun_decl(val_peek(4).obj, val_peek(3).obj, val_peek(1).obj);}
break;
case 15:
//#line 66 "./Parser.y"
{ Debug("entering scope"); enterScope(val_peek(3).obj); }
break;
case 16:
//#line 67 "./Parser.y"
{ Debug("func body"); yyval.obj = _func_body(val_peek(4).obj, val_peek(3).obj); }
break;
case 17:
//#line 68 "./Parser.y"
{ Debug("exit and check"); exitScope(); validateFunction(); }
break;
case 18:
//#line 71 "./Parser.y"
{ Debug("params  ->  param_list"                    ); yyval.obj = _params(val_peek(0).obj); }
break;
case 19:
//#line 72 "./Parser.y"
{ Debug("params  ->  eps"                           ); yyval.obj = _params_eps(); }
break;
case 20:
//#line 75 "./Parser.y"
{ yyval.obj = _param_list(val_peek(2).obj, val_peek(0).obj); }
break;
case 21:
//#line 76 "./Parser.y"
{ yyval.obj = _param_singleton(val_peek(0).obj); }
break;
case 22:
//#line 79 "./Parser.y"
{ yyval.obj = _param(val_peek(1).obj, val_peek(0).obj); }
break;
case 23:
//#line 82 "./Parser.y"
{ Debug("stmt_list  ->  stmt_list  stmt"            ); yyval.obj = _stmt_list(val_peek(1).obj, val_peek(0).obj); }
break;
case 24:
//#line 83 "./Parser.y"
{ Debug("stmt_list  ->  eps"                        ); yyval.obj = _stmt_list_eps();  }
break;
case 25:
//#line 86 "./Parser.y"
{ Debug("stmt  ->  expr_stmt"                       ); yyval.obj = _stmt_expr(val_peek(0).obj); }
break;
case 26:
//#line 87 "./Parser.y"
{ yyval.obj = _stmt_compound(val_peek(0).obj); }
break;
case 27:
//#line 88 "./Parser.y"
{ yyval.obj = _stmt_if(val_peek(0).obj); }
break;
case 28:
//#line 89 "./Parser.y"
{ yyval.obj = _stmt_while(val_peek(0).obj); }
break;
case 29:
//#line 90 "./Parser.y"
{ Debug("stmt  ->  return_stmt"                     ); yyval.obj = _stmt_return(val_peek(0).obj); }
break;
case 30:
//#line 91 "./Parser.y"
{ yyval.obj = _stmt_break(val_peek(0).obj); }
break;
case 31:
//#line 92 "./Parser.y"
{ yyval.obj = _stmt_continue(val_peek(0).obj); }
break;
case 32:
//#line 93 "./Parser.y"
{ yyval.obj = _stmt_print(val_peek(0).obj); }
break;
case 33:
//#line 94 "./Parser.y"
{ Debug("stmt  -> ;"                                ); }
break;
case 34:
//#line 97 "./Parser.y"
{ Debug("expr_stmt  ->  IDENT  ASSIGN  expr_stmt"   ); yyval.obj = _expr_stmt_assign(val_peek(2).obj, val_peek(0).obj); }
break;
case 35:
//#line 98 "./Parser.y"
{ Debug("expr_stmt  ->  expr ;"                     ); yyval.obj = _expr_stmt(val_peek(1).obj    ); }
break;
case 36:
//#line 99 "./Parser.y"
{ yyval.obj = _expr_stmt_array(val_peek(5).obj, val_peek(3).obj, val_peek(0).obj); }
break;
case 37:
//#line 100 "./Parser.y"
{ yyval.obj = _expr_stmt_record(val_peek(4).obj, val_peek(2).obj, val_peek(0).obj); }
break;
case 38:
//#line 103 "./Parser.y"
{ enterScope();}
break;
case 39:
//#line 104 "./Parser.y"
{ Debug("compound_stmt  ->  BEGIN  local_decls  stmt_list  END"); yyval.obj = _compound_stmt(val_peek(1).obj, val_peek(0).obj);}
break;
case 40:
//#line 105 "./Parser.y"
{ exitScope();}
break;
case 41:
//#line 107 "./Parser.y"
{ Debug("return_stmt  ->  RETURN  expr ;"           ); yyval.obj = _ret_stmt(val_peek(1).obj); }
break;
case 42:
//#line 110 "./Parser.y"
{ Debug("local_decls  ->  local_decls local_decl"   ); yyval.obj = _local_decls(val_peek(1).obj, val_peek(0).obj); }
break;
case 43:
//#line 111 "./Parser.y"
{ Debug("local_decls  ->  eps"                      ); yyval.obj = _local_decls_eps();}
break;
case 44:
//#line 114 "./Parser.y"
{ Debug("local_decl  ->  type_spec IDENT SEMI"      ); yyval.obj = _local_decl(val_peek(2).obj, val_peek(1).obj); }
break;
case 45:
//#line 117 "./Parser.y"
{ Debug("if_stmt -> IF LPAREN expr RPAREN stmt ELSE stmt"  ); yyval.obj = _if_stmt(val_peek(4).obj); }
break;
case 46:
//#line 120 "./Parser.y"
{ Debug("while_stmt  ->  WHILE  LPAREN  expr  RPAREN  stmt"); yyval.obj = _while_stmt(val_peek(1).obj); }
break;
case 47:
//#line 121 "./Parser.y"
{ }
break;
case 48:
//#line 124 "./Parser.y"
{ Debug ("break_stmt -> BREAK SEMI"); yyval.obj = _break_stmt(); }
break;
case 49:
//#line 127 "./Parser.y"
{ Debug ("continue_stmt -> BREAK SEMI"); yyval.obj = _continue_stmt(); }
break;
case 50:
//#line 130 "./Parser.y"
{ Debug("print_stmt  ->  PRINT  expr  SEMI"); yyval.obj = _print_stmt(val_peek(1).obj);}
break;
case 51:
//#line 133 "./Parser.y"
{ Debug("arg_list  ->  arg_list  COMMA  expr"); yyval.obj = _arg_list(val_peek(2).obj, val_peek(0).obj);}
break;
case 52:
//#line 134 "./Parser.y"
{ Debug("arg_list  ->  expr"); yyval.obj = _arg_list_expr(val_peek(0).obj);}
break;
case 53:
//#line 137 "./Parser.y"
{ Debug("args  ->  arg_list"); yyval.obj = _args(val_peek(0).obj);}
break;
case 54:
//#line 138 "./Parser.y"
{ Debug("args  ->  eps"     ); yyval.obj = _args_eps(); }
break;
case 55:
//#line 141 "./Parser.y"
{ yyval.obj = _expr_EQ_expr            (val_peek(2).obj, val_peek(0).obj);  }
break;
case 56:
//#line 142 "./Parser.y"
{ yyval.obj = _expr_AND_expr           (val_peek(2).obj, val_peek(0).obj);  }
break;
case 57:
//#line 143 "./Parser.y"
{ yyval.obj = _expr_OR_expr            (val_peek(2).obj, val_peek(0).obj);  }
break;
case 58:
//#line 144 "./Parser.y"
{ yyval.obj = _NOT_expr                (val_peek(0).obj);      }
break;
case 59:
//#line 145 "./Parser.y"
{ yyval.obj = _expr_NE_expr            (val_peek(2).obj, val_peek(0).obj);  }
break;
case 60:
//#line 146 "./Parser.y"
{ yyval.obj = _expr_LE_expr            (val_peek(2).obj, val_peek(0).obj);  }
break;
case 61:
//#line 147 "./Parser.y"
{ yyval.obj = _expr_LT_expr            (val_peek(2).obj, val_peek(0).obj);  }
break;
case 62:
//#line 148 "./Parser.y"
{ yyval.obj = _expr_GE_expr            (val_peek(2).obj, val_peek(0).obj);  }
break;
case 63:
//#line 149 "./Parser.y"
{ yyval.obj = _expr_GT_expr            (val_peek(2).obj, val_peek(0).obj);  }
break;
case 64:
//#line 150 "./Parser.y"
{ yyval.obj = _expr_PLUS_expr          (val_peek(2).obj, val_peek(0).obj);  }
break;
case 65:
//#line 151 "./Parser.y"
{ yyval.obj = _expr_MINUS_expr         (val_peek(2).obj, val_peek(0).obj);  }
break;
case 66:
//#line 152 "./Parser.y"
{ yyval.obj = _expr_MUL_expr           (val_peek(2).obj, val_peek(0).obj);  }
break;
case 67:
//#line 153 "./Parser.y"
{ yyval.obj = _expr_DIV_expr           (val_peek(2).obj, val_peek(0).obj);  }
break;
case 68:
//#line 154 "./Parser.y"
{ yyval.obj = _LPAREN_expr_RPAREN      (val_peek(1).obj    );  }
break;
case 69:
//#line 155 "./Parser.y"
{ yyval.obj = _expr_IDENT              (val_peek(0).obj    );  }
break;
case 70:
//#line 156 "./Parser.y"
{ yyval.obj = _IDENT_LPAREN_args_RPAREN(val_peek(3).obj, val_peek(1).obj);  }
break;
case 71:
//#line 157 "./Parser.y"
{ yyval.obj = _expr_BOOLLIT            (val_peek(0).obj    );  }
break;
case 72:
//#line 158 "./Parser.y"
{ yyval.obj = _expr_INTLIT             (val_peek(0).obj    );  }
break;
case 73:
//#line 159 "./Parser.y"
{ yyval.obj = _expr_FLOATLIT           (val_peek(0).obj    );  }
break;
case 74:
//#line 160 "./Parser.y"
{ yyval.obj = _expr_array              (val_peek(3).obj, val_peek(1).obj);  }
break;
case 75:
//#line 161 "./Parser.y"
{ yyval.obj = _expr_record             (val_peek(2).obj    );  }
break;
case 76:
//#line 163 "./Parser.y"
{ yyval.obj = _expr_record_assign      (val_peek(2).obj, val_peek(0).obj);  }
break;
//#line 924 "Parser.java"
//########## END OF USER-SUPPLIED ACTIONS ##########
    }//switch
    //#### Now let's reduce... ####
    if (yydebug) debug("reduce");
    state_drop(yym);             //we just reduced yylen states
    yystate = state_peek(0);     //get new state
    val_drop(yym);               //corresponding value drop
    yym = yylhs[yyn];            //select next TERMINAL(on lhs)
    if (yystate == 0 && yym == 0)//done? 'rest' state and at first TERMINAL
      {
      if (yydebug) debug("After reduction, shifting from state 0 to state "+YYFINAL+"");
      yystate = YYFINAL;         //explicitly say we're done
      state_push(YYFINAL);       //and save it
      val_push(yyval);           //also save the semantic value of parsing
      if (yychar < 0)            //we want another character?
        {
        yychar = yylex();        //get next character
        if (yychar<0) yychar=0;  //clean, if necessary
        if (yydebug)
          yylexdebug(yystate,yychar);
        }
      if (yychar == 0)          //Good exit (if lex returns 0 ;-)
         break;                 //quit the loop--all DONE
      }//if yystate
    else                        //else not done yet
      {                         //get next state and push, for next yydefred[]
      yyn = yygindex[yym];      //find out where to go
      if ((yyn != 0) && (yyn += yystate) >= 0 &&
            yyn <= YYTABLESIZE && yycheck[yyn] == yystate)
        yystate = yytable[yyn]; //get new state
      else
        yystate = yydgoto[yym]; //else go to new defred
      if (yydebug) debug("after reduction, shifting from state "+state_peek(0)+" to state "+yystate+"");
      state_push(yystate);     //going again, so push state & val...
      val_push(yyval);         //for next action
      }
    }//main loop
  return 0;//yyaccept!!
}
//## end of method parse() ######################################



//## run() --- for Thread #######################################
//## The -Jnorun option was used ##
//## end of method run() ########################################



//## Constructors ###############################################
/**
 * Default constructor.  Turn off with -Jnoconstruct .

 */
public Parser()
{
  //nothing to do
}


/**
 * Create a parser, setting the debug to true or false.
 * @param debugMe true for debugging, false for no debug.
 */
public Parser(boolean debugMe)
{
  yydebug=debugMe;
}
//###############################################################



}
//################### END OF CLASS ##############################
