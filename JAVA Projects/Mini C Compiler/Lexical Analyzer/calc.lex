%{
#include <stdlib.h>
#include "y.tab.h"
%}
%%
/* regular expressions */
[0-9]+     return NUMBER;
.|\n 	   return yytext[0];
%%
int yywrap(void) {
	return 1;
}

