// Shengwei You, CSE 143, Winter 2016
// TA: Melissa Medsker, BO
// Assignment #6: AnagramSolver
// Last modified date: 02/21/16
//
// The AnagramSolver class provides method that can prints out all
// the combinations of the anagrams based on the given dictionary
// and the passed phrases from the client. The method of it also
// allows the client to set the maximum words to be printed.



import java.util.*;


public class AnagramSolver {
   private List<String> dict;
   private Map<String, LetterInventory> words;
   
   
   // Constructs an AnagramSolver with the specified list of words. The
   // character list of each word is converted and matched with itself.
   public AnagramSolver(List<String> list) {
      words = new HashMap<String, LetterInventory>();
      dict = list;
      for (String s : list) {
         LetterInventory newInventory = new LetterInventory(s);
         words.put(s, newInventory);
      }
   }
   
   // pre : max >= 0 (else throws IllegalArgumentException);
   // post: print out all the anagram relates to the given phrases s with the 
   //       maximum number of words less than the given max value (or unlimited
   //       size of words if the given max is equals to zero).
   public void print(String s, int max) {
      if (max < 0)
         throw new IllegalArgumentException();
   
      List<String> dict2 = new ArrayList<String>();
      Stack<String> result = new Stack<String>();
      LetterInventory mainPhrases = new LetterInventory(s);
      
      // build a short dictionary that only storing relevant words
      for (String vocabulary : dict)
         if (mainPhrases.subtract(words.get(vocabulary)) != null)
            dict2.add(vocabulary);  
      print(mainPhrases, max, result, dict2);
   }
   
   // pre : max >= 0 (else throws IllegalArgumentException);
   // post: performs the searching and printing of all possible anagrams of the
   //       given character list mainPhrases. The given max is the value with
   //       which the largest amount of words are allowed to use in the building
   //       of the result (or use unlimited size of words if the max is equals to
   //       zero). Use the result to store words that can be used to build anagrams.
   //       The dict2 is the dictionary where the possible anagram words come from.
   private void print(LetterInventory mainPhrases, int max, Stack<String> result,
                      List<String> dict2) {
      if ((max == 0 && mainPhrases.isEmpty()) ||
          (result.size() <= max && mainPhrases.isEmpty()))
         System.out.println(result);
      else if (max == 0 || result.size() <= max) {
         for (String vocabulary : dict2) {
            LetterInventory test = mainPhrases.subtract(words.get(vocabulary));
            if (test != null) {
               result.push(vocabulary);
               print(test, max, result, dict2);
               result.pop();
            }
         }
      }
   }
}
