import java.util.*;
import java.util.HashMap;

// Compile warnings about unchecked cast calls
// Not sure how to avoid (try/catch blocks didn't work)
// Just used suppress warnings
@SuppressWarnings("unchecked")
public class ParserImpl
{
    public int lineno = 1;
    Stack functions = new Stack();
    Stack functionLine = new Stack();
    String currentFunction = "";
    Env env = new Env(null);

    public static Boolean _debug = false;
    void Debug(String message)
    {
        if(_debug)
            System.out.println(message);
    }
    void enterScope()
    {
      env = new Env(env);
    }
    void enterScope(Object params)
    {
      // Overloaded function handles function scopes
      // by adding parameters to symbol table
      ArrayList<Object> pmtrs = new ArrayList<Object>();
      pmtrs.add(params);
      env = new Env(env);
      // Add each parameter to symbol table
      for(int i = 0; i < pmtrs.size(); i++)
      {
        env.Put("param", (String)((ArrayList<Object>)pmtrs.get(i)).get(1), (String)((ArrayList<Object>)pmtrs.get(i)).get(0));
      }
    }
    void exitScope()
    {
      env.prevEnv();
    }
    void checkForMain() throws Exception
    {
      if (env.Get("main") == null)
      {
        throw new Exception("Error: Program must have a main function.");
      }
      if (env.Get("main") != "int")
      {
        throw new Exception("Error: Return type of main function must be int.");
      }
    }
    void validateFunction() throws Exception
    {
      String id = (String)functions.peek();
      if((id != "main") && (id != currentFunction))
      {
        String full = "(";
        ArrayList<Object> fullParams = (ArrayList<Object>)env.Get("params", (String)id);
        for(int i = 0; i < fullParams.size(); i++)
        {
          full += fullParams.get(i);
          if(i == fullParams.size() - 1)
          {
              full += ")";
          }
          else
          {
            full += ", ";
          }
        }
      }
      functions.pop();
      functionLine.pop();
    }
    // State tranisition functions
    // program -> decl_list
    public Object _program(Object decl_list) { return decl_list; }
    // decl_list -> decl_list decl | eps
    public Object _decl_list(Object decl_list, Object decl)
    {
      ((ArrayList<Object>) decl_list).add(decl);
      return decl_list;
    }
    public Object _decl_list_eps(){ return new ArrayList<Object>(); }
    // decl - > var_decl | fun_decl
    public Object _decl_var(Object var_decl){ return var_decl; }
    public Object _decl_fun                   (Object fun_decl) { return fun_decl; }
    // fun_decl       ->  type_spec  IDENT  LPAREN  params  RPAREN  LBRACE  local_decls  stmt_list  RBRACE
    public Object _fun_decl(Object type_spec, Object IDENT, Object params) throws Exception
    {
      if(env.envContains(env, (String)IDENT))
      {
        throw new Exception("Error at line " + lineno + ": identifier " + IDENT + " is already defined.");
      }
      env.Put("returnValue", (String)IDENT, type_spec);
      env.Put("params", (String)IDENT, params);
      functions.push((String)IDENT);
      functionLine.push(lineno);
      return "_fun_decl";
    }
    public Object _func_body(Object local_decls, Object stmt_list) { return ""; }
    // var_decl -> type_spec IDENT SEMI
    public Object _var_decl(Object type_spec, Object IDENT) throws Exception
    {
      if (env.Get("local", (String)IDENT) != null)
      {
        throw new Exception("Error at line " + lineno + ": identifier " + IDENT + " is already defined in this scope.");
      }
      env.Put((String)IDENT, type_spec);
      return "_var_decl";
    }
    // type_spec -> BOOL | INT | FLOAT | RECORD BEGIN local_decls END | type_spec LBRACKET RBRACKET
    public Object _type_spec_BOOL() throws Exception { return "bool"; }
    public Object _type_spec_INT() throws Exception { return "int"; }
    public Object _type_spec_FLOAT() throws Exception { return "float"; }
    public Object _type_spec_RECORD(Object local_decls) throws Exception { return "record"; }
    public Object _type_spec_ARRAY(Object type_spec) throws Exception { return type_spec + "[]"; }
    // params -> param_list | eps
    public Object _params(Object param_list) throws Exception { return param_list; }
    public Object _params_eps() throws Exception { return ""; }
    // param_list -> param_list COMMA param | param
    public Object _param_list(Object param_list, Object param)
    {
      ((ArrayList<Object>)param_list).add((ArrayList<Object>)param);
      return param_list;
    }
    public Object _param_singleton(Object param)
    {
      ArrayList<Object> single_param = new ArrayList<Object>();
      single_param.add(param);
      return single_param;
    }
    // param -> type_spec IDENT
    public Object _param(Object type_spec, Object IDENT)
    {
      ArrayList<Object> addParams = new ArrayList<Object>();
      addParams.add((String)type_spec);
      addParams.add((String)IDENT);
      return addParams;
    }
    // stmt_list -> stmt_list stmt | eps
    public Object _stmt_list(Object stmt_list, Object stmt)
    {
      ((ArrayList<Object>) stmt_list).add(stmt);
      return stmt_list;
    }
    public Object _stmt_list_eps() { return new ArrayList<Object>(); }
    // stmt -> expr_stmt | compound_stmt | if_stmt | while_stmt | return_stmt | break_stmt | continue_stmt | print_stmt | SEMI
    public Object _stmt_expr(Object expr_stmt)  { return expr_stmt; }
    public Object _stmt_compound(Object compound_stmt) { return compound_stmt; }
    public Object _stmt_if(Object if_stmt) { return if_stmt; }
    public Object _stmt_while(Object while_stmt) { return while_stmt; }
    public Object _stmt_return(Object return_stmt)  { return return_stmt; }
    public Object _stmt_break(Object break_stmt)  { return break_stmt; }
    public Object _stmt_continue(Object continue_stmt) { return continue_stmt; }
    public Object _stmt_print(Object print_stmt)  { return print_stmt; }
    // expr_stmt -> IDENT ASSIGN expr_stmt | expr SEMI | IDENT LBRACKET expr RBRACKET ASSIGN expr_stmt
    //              | IDENT DOT IDENT ASSIGN expr_stmt
    public Object _expr_stmt_assign(Object IDENT, Object expr_stmt) throws Exception
    {
      if(env.Get((String)IDENT) == null)
      {
        throw new Exception("Error at line " + lineno + ": undeclared identifier '" + IDENT + "'");
      }
      if(expr_stmt != env.Get((String)IDENT) && ((String)expr_stmt).contains("[]"))
      {
        throw new Exception("Error at line " + lineno + ": undeclared identifier '" + IDENT + "'");
      }
      env.Put((String)IDENT, expr_stmt);
      return expr_stmt;
    }
    public Object _expr_stmt(Object expr) { return expr; }
    public Object _expr_stmt_array(Object IDENT, Object expr, Object expr_stmt) throws Exception
    {
      //Error handle
      if(expr != "int") { throw new Exception("Error at line " + lineno + ": invalid array index."); }
      if(env.Get((String)IDENT) == null)
      { throw new Exception("Error at " + lineno + ": undeclared identifier '" + IDENT + "'"); }
      String raw_ident = (String)env.Get((String)IDENT);
      if(!(raw_ident.contains("[]")))
      { throw new Exception("Error at " + lineno + ": '" + IDENT + "' is declared as type array."); }
      else { env.Put((String) IDENT, expr_stmt); return "_expr_stmt_array"; }

    }
    public Object _expr_stmt_record(Object IDENT1, Object IDENT2, Object expr_stmt) throws Exception
    {
      Object IDENT = IDENT1 + "." + IDENT2;
      if(env.Get((String)IDENT) == null)
      { throw new Exception("Error at " + lineno + ": undeclared identifier '" + IDENT + "'"); }
      if ((!((String)env.Get((String)IDENT)).contains("[]")) && ((String)expr_stmt).contains("[]"))
      { throw new Exception("Error at " + lineno + " : " + IDENT + " is not type array."); }
      env.Put((String)IDENT, expr_stmt);
      return "_expr_stmt_record";
    }
    // compound_stmt  ->  LBRACE  local_decls  stmt_list  RBRACE
    public Object _compound_stmt(Object local_decls, Object stmt_list) { return "_compound_stmt"; }
    // return_stmt    ->  RETURN  expr  SEMI
    public Object _ret_stmt(Object expr) throws Exception
    {
      currentFunction = (String)functions.peek();
      String retType = (String)env.prev.Get("returnValues", currentFunction);
      if(retType != expr)
      {
        throw new Exception("Error at " + lineno + " : return type of " + currentFunction + "() is ''" + retType + "', not '" + expr + "'.");
      }
      return "_ret_stmt";
    }
    // local_decls    ->  local_decls  local_decl  |  eps
    public Object _local_decls(Object local_decls, Object local_decl) { return "_local_decls"; }
    public Object _local_decls_eps() { return ""; }
    // local_decl     ->  type_spec  IDENT  SEMI
    public Object _local_decl(Object type_spec, Object IDENT) throws Exception
    {
      if(env.Get("local", (String)IDENT) != null)
      { throw new Exception("Error at " + lineno + ":  identifier '" + IDENT + "' is already defined in this scope."); }
      env.Put((String)IDENT, type_spec);
      return "_local_decl";
    }
    // if_stmt        ->  IF  LPAREN  expr  RPAREN  stmt  ELSE  stmt
    public Object _if_stmt(Object expr) throws Exception
    {
      if(expr != "bool")
      { throw new Exception("Error at line " + lineno + ": invalid condition statement."); }
      return "_if_stmt";
    }
    // while_stmt     ->  WHILE  LPAREN  expr  RPAREN  stmt
    public Object _while_stmt(Object expr) throws Exception
    {
      if(expr != "bool")
      { throw new Exception("Error at line " + lineno + ": invalid loop condition."); }
      return null;
    }
    // break_stmt     ->  BREAK  SEMI
    public Object _break_stmt() { return "break"; }
    // continue_stmt  ->  CONTINUE  SEMI
    public Object _continue_stmt() { return "continue"; }
    // print_stmt     ->  PRINT  expr  SEMI
    public Object _print_stmt(Object expr) { return "_print_stmt"; }
    // arg_list       ->  arg_list  COMMA  expr  |  expr
    public Object _arg_list(Object arg_list, Object expr)
    {
      ((ArrayList<String>)arg_list).add((String)expr);
      return arg_list;
    }
    public Object _arg_list_expr(Object expr) throws Exception
    {
      ArrayList<Object> list = new ArrayList<>();
      list.add(expr);
      return list;
    }
    // args           ->  arg_list  |  eps
    public Object _args(Object arg_list) { return arg_list; }
    public Object _args_eps() throws Exception { return new ArrayList<Object>(); }
    /*
    expr           ->  expr  OR  expr  |  expr  AND  expr  |  NOT  expr
                    |  expr  EQ  expr  |  expr  NE  expr  |  expr  LE  expr  |  expr  LT  expr  |  expr  GE  expr  |  expr  GT  expr
                    |  expr  PLUS  expr  |  expr  MINUS  expr  |  expr  MUL  expr  |  expr  DIV  expr
                    |  LPAREN  expr  RPAREN  |  IDENT  |  BOOL_LIT  |  INT_LIT  |  FLOAT_LIT
                    |  IDENT  LPAREN  args  RPAREN                                  // to support function call
                    |  IDENT  LBRACKET  expr  RBRACKET  |  IDENT  DOT  SIZE         // to support array
                    |  NEW  type_spec  LBRACKET  expr  RBRACKET                     // to support array
                    |  IDENT  DOT  IDENT                                            // to support record
    */
    public Object _expr_EQ_expr(Object expr1, Object expr2) throws Exception
    {
      if(expr1.equals("bool") && (expr2.equals("bool"))) { return "bool"; }
      if(expr1.equals("int") && (expr2.equals("int"))) { return "bool"; }
      if(expr1.equals("float") && (expr2.equals("float"))) { return "bool"; }
      throw new Exception("Error at line " + lineno + ": expression(s) cannot be resolved to boolean value.");
    }
    public Object _expr_AND_expr(Object expr1, Object expr2) throws Exception
    {
      if(expr1.equals("bool") && (expr2.equals("bool"))) { return "bool"; }
      throw new Exception("Error at line " + lineno + ": expression(s) cannot be resolved to boolean value.");
    }
    public Object _expr_OR_expr(Object expr1, Object expr2) throws Exception
    {
      if(expr1.equals("bool") && (expr2.equals("bool"))) { return "bool"; }
      throw new Exception("Error at line " + lineno + ": expression(s) cannot be resolved to boolean value.");
    }
    public Object _NOT_expr(Object expr) throws Exception
    {
      if(expr.equals("bool")) { return "bool"; }
      throw new Exception("Error at line " + lineno + ": expression cannot be resolved to boolean value.");
    }
    public Object _expr_NE_expr(Object expr1, Object expr2) throws Exception
    {
      if(expr1.equals("bool") && (expr2.equals("bool"))) { return "bool"; }
      if(expr1.equals("int") && (expr2.equals("int"))) { return "bool"; }
      if(expr1.equals("float") && (expr2.equals("float"))) { return "bool"; }
      throw new Exception("Error at line " + lineno + ": expression(s) cannot be resolved to boolean value.");
    }
    public Object _expr_LE_expr(Object expr1, Object expr2) throws Exception
    {
      if(expr1.equals("int") && (expr2.equals("int"))) { return "bool"; }
      if(expr1.equals("float") && (expr2.equals("float"))) { return "bool"; }
      throw new Exception("Error at line " + lineno + ": expression(s) cannot be resolved to numeric value.");
    }
    public Object _expr_LT_expr(Object expr1, Object expr2) throws Exception
    {
      if(expr1.equals("int") && (expr2.equals("int"))) { return "bool"; }
      if(expr1.equals("float") && (expr2.equals("float"))) { return "bool"; }
      throw new Exception("Error at line " + lineno + ": expression(s) cannot be resolved to numeric value.");
    }
    public Object _expr_GE_expr(Object expr1, Object expr2) throws Exception
    {
      if(expr1.equals("int") && (expr2.equals("int"))) { return "bool"; }
      if(expr1.equals("float") && (expr2.equals("float"))) { return "bool"; }
      throw new Exception("Error at line " + lineno + ": expression(s) cannot be resolved to numeric value.");
    }
    public Object _expr_GT_expr(Object expr1, Object expr2) throws Exception
    {
      if(expr1.equals("int") && (expr2.equals("int"))) { return "bool"; }
      if(expr1.equals("float") && (expr2.equals("float"))) { return "bool"; }
      throw new Exception("Error at line " + lineno + ": expression(s) cannot be resolved to numeric value.");
    }
    public Object _expr_PLUS_expr(Object expr1, Object expr2) throws Exception
    {
      if(expr1.equals("int") && (expr2.equals("int"))) { return "bool"; }
      if(expr1.equals("float") && (expr2.equals("float"))) { return "bool"; }
      throw new Exception("Error at line " + lineno + ": expression(s) cannot be resolved to numeric value.");
    }
    public Object _expr_MINUS_expr(Object expr1, Object expr2) throws Exception
    {
      if(expr1.equals("int") && (expr2.equals("int"))) { return "bool"; }
      if(expr1.equals("float") && (expr2.equals("float"))) { return "bool"; }
      throw new Exception("Error at line " + lineno + ": expression(s) cannot be resolved to numeric value.");
    }
    public Object _expr_MUL_expr(Object expr1, Object expr2) throws Exception
    {
      if(expr1.equals("int") && (expr2.equals("int"))) { return "bool"; }
      if(expr1.equals("float") && (expr2.equals("float"))) { return "bool"; }
      throw new Exception("Error at line " + lineno + ": expression(s) cannot be resolved to numeric value.");
    }
    public Object _expr_DIV_expr(Object expr1, Object expr2) throws Exception
    {
      if(expr1 == "int" && expr2 == "int") { return "int"; }
      if(expr1 == "float" && expr2 == "float") { return "float"; }
      else { throw new Exception("Error at line " + lineno + " : '" + expr1 + " cannot divide '" + expr2 + "'."); }
    }
    public Object _LPAREN_expr_RPAREN(Object expr) throws Exception { return expr; }
    public Object _expr_IDENT(Object IDENT) throws Exception
    {
      String raw_ident = (String)env.Get((String)IDENT);
      if(env.Get("param", (String)IDENT) != null)
      {
        throw new Exception("Error at line " + lineno + ":  invalid function call.");
      }
      if(env.Get((String)IDENT) == null)
      {
        throw new Exception("Error at line " + lineno + ":  undeclared identifier '" + IDENT + "'.");
      }
      if(env.Get((String)IDENT) == "int" || env.Get((String)IDENT) == "bool" || env.Get((String)IDENT) == "float") { return raw_ident; }
      if(env.Get((String)IDENT) == "int[]" || env.Get((String)IDENT) == "bool[]" || env.Get((String)IDENT) == "float[]") { return raw_ident;}
      throw new Exception("Error at line " + lineno + " : undeclared identifier '" + IDENT + "'.");
    }
    // fix
    public Object _IDENT_LPAREN_args_RPAREN(Object IDENT, Object args) throws Exception
    {
      if(env.Get("local", (String)IDENT) == null)
      {
        String find_func = (String)env.Get((String)IDENT);
        if(!(find_func.contains("()")))
        {
          throw new Exception("Error at line " + lineno + ":  undeclared function '" + IDENT + "'.");
        }
        ArrayList<Object> params = (ArrayList<Object>) env.Get("global", "params", (String)IDENT);
        ArrayList<Object> lst_args = (ArrayList<Object>) args;
        if(lst_args.size() != params.size())
        {
          throw new Exception("Error at line " + lineno + ":  invalid number of parameters passed to function '" + IDENT + "'.");
        }

        for(int i = 0; i < params.size(); i++)
        {
          String raw_hold = (String)((ArrayList<Object>) ((ArrayList<Object>) env.Get("global", "params", (String)IDENT)).get(i)).get(0);
          if(lst_args.size() < params.size() && i == lst_args.size())
          {
            throw new Exception("Error at line " + lineno + ":  invalid parameters passed to function '" + IDENT + "' expected " + raw_hold + ".");
          }
          if(raw_hold != (String)((ArrayList<Object>) args).get(i))
          {
            throw new Exception("Error at line " + lineno + ":  invalid parameters passed to function '" + IDENT + "' expected " + raw_hold + ".");
          }
        }
        return env.Get("global", "returnValues", (String)IDENT);
      }
      else
      {
        throw new Exception("Error at line " + lineno + ":  '" + IDENT + "' is not a function.");
      }
    }
    public Object _expr_BOOLLIT(Object BOOL_LIT) { return "bool"; }
    public Object _expr_INTLIT(Object INT_LIT) { return "int"; }
    public Object _expr_FLOATLIT(Object FLOAT_LIT) { return "float"; }
    public Object _expr_array(Object IDENT, Object expr) throws Exception
    {
      if (!((String)env.Get((String)IDENT)).contains("[]"))
      {
        throw new Exception("Error at line " + lineno + " : " + IDENT + " must be an array to call size function.");
      }
      return "int";
    }
    public Object _expr_record(Object IDENT) throws Exception
    {
      String raw_id = (String)env.Get((String)IDENT);
      if(!(raw_id.contains("[]")))
      {
        throw new Exception("Error at line " + lineno + ": uninitialized array used '" + IDENT + "'.");
      }
      return null;
    }
    public Object _expr_array_assign(Object type_spec, Object expr) throws Exception
    {
      if(expr != "int")
      {
          throw new Exception("Error at line " + lineno + ": array size must be initialized with an INT value.");
      }
      return type_spec + "[]";
    }
    public Object _expr_record_assign(Object IDENT1, Object IDENT2) { return IDENT1 + "." + IDENT2; }
}
