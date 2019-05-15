
%{
#include<stdio.h>
 int yylex(void);
 int yyerror(char *s);
%}
%token NUMBER
%%
expr:
	expr '+' NUMBER
	| expr '-' NUMBER
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


