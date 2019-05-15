
%{
#include<stdio.h>
 int yylex(void);
 int yyerror(char *s);
%}
%token NUMBER
%%
S: 
	S expr '\n'   { printf (" = %d\n", $2); }
	| 
;
expr:
	expr '+' NUMBER      { $$ = $1 + $3 ;} 
	| expr '-' NUMBER    { $$ = $1 - $3 ; }
	| NUMBER
;
%%
int yyerror(char *s) {
	fprintf(stderr, "%s\n", s);
	return 0;
}
int main(void) {
	yyparse();
	return 0;
}


