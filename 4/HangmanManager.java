// Shengwei You, CSE 143 BO
// TA: Melissa Medsker
// Assignment #4 Evil Hangman
// Last Modified Date: 02/02/16
// 
// Class hangman manager builds a game that delay picking a word until it
// is forced to. It initializes the word families by the passed dictionary.
// Clients can decide the length of the words to guess, and the maximum
// number of wrong guesses allowed to make.


import java.util.*;

public class HangmanManager {
   // track the relationship between pattern and word groups
   private Map<String, SortedSet<String>> patterns;
   // records the letters that had already been guessed
   private SortedSet<Character> alreadyGuessed;
   // counts the remaining chance allowed to guess wrong
   private int remainGuesses;
   
   
   // pre : length >= 1 && max >= 0 (else throws IllegalArgumentException);
   //       The  dictionary of words passed by client is assumed as a nonempty
   //       collection of lowercase words.
   // post: imports all the nonduplicate words, which satisfy the given length,
   //       to the current word choosing group, and link it with the current
   //       game pattern displayed. Bulid a character group to keep track of
   //       all the guessed letters. Set the value of max to be the maximum
   //       number of wrong guesses allowed by player.
   public HangmanManager(Collection<String> dictionary, int length, int max) {
      if (length < 1 || max < 0)
         throw new IllegalArgumentException();
      
      patterns = new TreeMap<String, SortedSet<String>>();
      alreadyGuessed = new TreeSet<Character>();
      remainGuesses = max;
      
      String initialPattern = "-";
      for (int i = 1; i < length; i++)
         initialPattern += " -";
      
      patterns.put(initialPattern, new TreeSet<String>());
      for (String word : dictionary)
         if (word.length() == length)
            patterns.get(initialPattern).add(word);
   }

   // post: returns the current group of words used by hangman manager to
   //       choose the next group of guessing words
   public Set<String> words() {
      return patterns.get(patterns.keySet().iterator().next());
   }
   
   // post: returns the number of wrong guesses still allowed by player
   public int guessesLeft() {
      return remainGuesses;
   }
   
   // post: returns the group of letters that had already been guessed
   //       by player
   public SortedSet<Character> guesses() {
      return alreadyGuessed;
   }
   
   // pre : current group of words considered by the hangman manager must not
   //       be empty (else throws IllegalStateException)
   // post: returns the current pattern displayed in the game. Displays letter
   //       as dash if the letter had not been guessed, and reveals the letter
   //       if it was guessed right. Each letters are separated by spaces.
   public String pattern() {
      if (patterns.keySet().isEmpty() ||
          patterns.get(patterns.keySet().iterator().next()).isEmpty())
         throw new IllegalStateException();
      return patterns.keySet().iterator().next();
   }
   
   // pre : remainGuesses >= 1 && currentWords must not be empty
   //       (else throws IllegalStateException)
   //       Already guessed letters cannot be guessed again
   //       (else throws IllegalArgumentException)
   //       Assumes that all guesses are lowercases.
   // post: returns the number of guessed letter in the new pattern;
   //       decides the word group and pattern to use next, depends
   //       on the guess; if the pattern didn't changed (which means
   //       didn't guess right), decrements remaining guess, else the
   //       number of remaining guess does not change
   public int record(char guess) {
      if (remainGuesses < 1 || patterns.values().isEmpty())
         throw new IllegalStateException();
      else if (alreadyGuessed.contains(guess))
         throw new IllegalArgumentException();
      alreadyGuessed.add(guess);
      // saves current display pattern
      String prevPattern = patterns.keySet().iterator().next();
      // saves current word group
      SortedSet<String> oldWords = patterns.get(prevPattern);
      
      addToPattern(guess, prevPattern, oldWords);
      String maxPattern = choosePattern(guess);
      
      // remove all patterns except the pattern that links
      // to the largest word group
      Iterator<String> itr = patterns.keySet().iterator();
      while (itr.hasNext())
         if (!itr.next().equals(maxPattern))
            itr.remove();
      
      if (prevPattern.equals(maxPattern))
         remainGuesses--;
         
      return maxPattern.length() - maxPattern.replace("" + guess, "").length();
   }
   
   // post: find patterns from the current word group based on the guessed
   //       letter. Adding each word to to the appropriate pattern, and
   //       build a new pattern relationship if this pattern does not exist
   private void addToPattern(char guess, String prevPattern,
                             SortedSet<String> oldWords) {
      // removes previous patterns and word families 
      patterns.keySet().clear();
      String tempPattern = prevPattern;
      for (String word : oldWords) {
         int index = word.indexOf(guess);
         while (index >= 0) {
            tempPattern = tempPattern.substring(0, index * 2) + guess +
                          tempPattern.substring(index * 2 + 1);
            index = word.indexOf(guess, index + 1);
         }
         
         if (!patterns.containsKey(tempPattern))
            patterns.put(tempPattern, new TreeSet<String>());
         patterns.get(tempPattern).add(word);
         
         tempPattern = prevPattern;
      }
   }

   // post: returns the pattern that has the largest group of words; if
   //       two patterns have the same size of word group, returns the
   //       one appears earlier
   private String choosePattern(char guess) {
      int max = 0;
      String maxPattern = "";
      for (String pattern : patterns.keySet())
         if (patterns.get(pattern).size() > max) {
            max = patterns.get(pattern).size();
            maxPattern = pattern;
         }
      return maxPattern;
   }
}