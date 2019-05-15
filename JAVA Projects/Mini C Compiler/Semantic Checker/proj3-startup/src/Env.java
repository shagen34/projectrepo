import java.util.ArrayList;
import java.util.HashMap;
import java.util.Stack;

// Put procedure was changed to include another parameter 'type'
// to distinguish symbols, parameters, and return values

// Get procedure changed to include another parameter 'scope'
// which lets the function call check the local scope or global scope

// Compile warnings about unchecked cast calls
// Not sure how to avoid (try/catch blocks didn't work)
// Just used suppress warnings
@SuppressWarnings("unchecked")
public class Env
{
    Env prev;
    HashMap<String, Object> symbols;
    HashMap<String, Object> params;
    HashMap<String, Object> returnValues;
  // Stack of Env for scope control
  // Static to prevent new stack from being created with each Env call
    private static Stack symtab = new Stack();

    public Env(Env prev)
    {
      this.prev = prev;
      symbols = new HashMap<String, Object>();
      params = new HashMap<String, Object>();
      returnValues = new HashMap<String, Object>();
      // Creating new environment = new scope
      symtab.push(this);
    }
    public void Put(String name, Object value)
    {
      // Add entry to current scope (top of stack)
      Env currentEnv = (Env)symtab.peek();
      // Check each hashmap in current Env for symbol
      if(envContains(currentEnv, name))
      {
        System.out.println("Error: " + name + " is already declared");
        return;
      }
      if(emptySymbolTable(symtab))
      {
        System.out.println("Error: no symbol table exists.");
        return;
      }
      ((HashMap)currentEnv.symbols).put(name, value);
    }
    // Overloaded function for storing parameters and return values
    public void Put(String type, String name, Object value)
    {
      // Add entry to current scope (top of stack)
      Env currentEnv = (Env)symtab.peek();
      // Check each hashmap in current Env for symbol
      if(emptySymbolTable(symtab))
      {
        System.out.println("Error: no symbol table exists.");
        return;
      }
      if(type == "param")
      {
        if(((HashMap)currentEnv.params).get(name) != null)
        {
          System.out.println("Error: " + name + " is already declared");
          return;
        }
        ((HashMap)currentEnv.params).put(name, value);
      }
      if(type == "returnValue")
      {
        if(((HashMap)currentEnv.returnValues).get(name) != null)
        {
          System.out.println("Error: " + name + " is already declared");
          return;
        }
        ((HashMap)currentEnv.returnValues).put(name, value);
      }
    }
    public Object Get(String name)
    {
      if(symtab.empty())
      {
        System.out.println("Error: symbol table is empty.");
      }
     for(int i = symtab.size() - 1; i >= 0; i--)
     {
       Env currentEnv = (Env)symtab.elementAt(i);
       // Call get(name) on each Env in the symbol table until found or return null
       Object retVal = (((HashMap)currentEnv.symbols).get(name));
       if(retVal != null) return retVal;
       retVal = (((HashMap)currentEnv.params).get(name));
       if(retVal != null) return retVal;
       retVal = (((HashMap)currentEnv.returnValues).get(name));
       if(retVal != null) return retVal;
     }
     return null;
    }
    // Overloaded function for only searching local scope
    public Object Get(String local_scope, String name)
    {
       if(symtab.empty())
       {
         System.out.println("Error: symbol table is empty.");
       }
       Env currentEnv = (Env)symtab.peek();
       // Call get(name) on each Env in the symbol table until found or return null
       Object retVal = (((HashMap)currentEnv.symbols).get(name));
       if(retVal != null) return retVal;
       retVal = (((HashMap)currentEnv.params).get(name));
       if(retVal != null) return retVal;
       retVal = (((HashMap)currentEnv.returnValues).get(name));
       if(retVal != null) return retVal;
       return null;
    }
    // Another overloaded function for finding only symbols/params/returnValues
    // in local or global scope
    public Object Get(String scope, String type, String name)
    {
      Env currentEnv = (Env)symtab.peek();
      int scopenum = 0;
      Object retVal;
      if(scope == "local") { scopenum = 1; }
      else { scopenum = symtab.size() - 1; }

      for(int i = scopenum; i >= 0; i++)
      {
        if(type == "symbols")
        {
          retVal = (((HashMap)currentEnv.symbols).get(name));
          return retVal;
        }
        if(type == "params")
        {
          retVal = (((HashMap)currentEnv.params).get(name));
          return retVal;
        }
        if(type == "returnValues")
        {
          retVal = (((HashMap)currentEnv.returnValues).get(name));
          return retVal;
        }
      }
      return null;
    }
    public void prevEnv()
    {
      // Leaving current scope, pop first Env in the stack
      if(symtab.empty())
      {
        System.out.println("Error: symbol table is empty.");
      }
      symtab.pop();
    }
    public boolean envContains(Env env, String name)
    {
      if(((HashMap)env.symbols).containsKey(name))
      {
        return true;
      }
      if(((HashMap)env.params).containsKey(name))
      {
        return true;
      }
      if(((HashMap)env.returnValues).containsKey(name))
      {
        return true;
      }
      return false;
    }
    public boolean emptySymbolTable(Stack symbolTable)
    {
      if(symbolTable.empty())
      {
        return true;
      }
      return false;
    }
}
