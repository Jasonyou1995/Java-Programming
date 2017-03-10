// Shengwei You, CSE 143 BO
// TA: Melissa Medsker
// Assignment #5 GrammarSolver
// Last Modified Date: 02/09/16
// 
//
// Class GrammarSolver takes a list of String of BNF grammar symbols and
// rules from client, and then stores these symbols by matching them with
// the given rule groups. This class allows client to generate a group of
// Strings by the given nonterminal symbol, and the size given by client.
// This class can also gives a method that can return a String of all
// the nonterminal symbols.


import java.util.*;


public class GrammarSolver {
   // stores the nonterminal symbols in alphabetical order and links them
   // with their rule groups
   private SortedMap<String, String[]> symbolRules;
   
   // pre : !grammar.isEmpty() && no duplicate nonterminal symbola are
   //       allowed (throws IllegalArgumentException otherwise)
   // post: build a GrammarSolver object by saving all the nonterminal
   //       symbols from the grammar list, and link each of these symbols
   //       to their rule groups.
   public GrammarSolver(List<String> grammar) {
      if (grammar.isEmpty())
         throw new IllegalArgumentException();
      
      symbolRules = new TreeMap<String, String[]>();
      for (String s : grammar) {
         String[] parts = s.split("::=");
         
         // throw exception when finding duplicate nonterminate symbol
         if (symbolRules.keySet().contains(parts[0]))
            throw new IllegalArgumentException();
         symbolRules.put(parts[0], parts[1].split("[|]"));
      }
   }
   
   // returns true if the given symbol was already added, and false otherwise
   public boolean grammarContains(String symbol) {
      return symbolRules.keySet().contains(symbol);
   }
   
   // pre : times >= 0 && the given symbol must be one of the added nonterminal
   //       symbols in this GrammarSolver (throws IllegalArgumentException otherwise)
   // post: returns a group of Strings with the size equlas to the given times.
   //       Randomly choose one rule from the rule group with equal possiblility,
   //       and repeats this step if the chosen rule is an nonterminal symbol by
   //       itself, until reaching a terminal symbol.
   public String[] generate(String symbol, int times) {
      if (!grammarContains(symbol) || times < 0)
         throw new IllegalArgumentException();
      
      String[] result = new String[times];
      for (int i = 0; i < times; i++)
         result[i] = generate(symbol).trim();
      return result;
   }
   
   // post: generates and returns a String by randomly choose rules from
   //       the rule groups, if the symbol is nonterminal.
   //       Otherwise returns the symbol, if this symbol is a terminal.
   private String generate(String symbol) {
      String result = "";
      // recursive case: the symbol is a nonterminal symbol
      if (grammarContains(symbol)) {
         int index = (int) (Math.random() * symbolRules.get(symbol).length);
         String[] parts = symbolRules.get(symbol)[index].trim().split("[ \t]+");
         for (String s : parts)
            result += generate(s);
         return result;
      } else { // base case: the symbol is a terminal
         return symbol + " ";
      }
   }
   
   // returns a bracketed String that lists all the nonterminal symbols,
   // and each symbol is separated by a comma and space.
   public String getSymbols() {
      return symbolRules.keySet().toString();
   }
}