// Shengwei You
// CSE 143 BO with Melissa Medsker
// Homework #1
// Last Modified Date: 01/12/2016
//
// Class LetterInventory can be used to track a list of characters
// from the input String.


public class LetterInventory {
// represent each of 26 characters in the inventory.
public static final int DEFAULT_SIZE = 26;

   // a list of inventory that stores the amount of each character
   // (from 0 to 25).
   int[] inventory;
   // total number of elements in this list.
   int size;

   // pre : take a String of data input from client.
   // post: constracts a lowercase alphabatic ordered character inventory.
   //       (ignornig all non-character inputs)
   public LetterInventory(String data) {
      inventory = new int[DEFAULT_SIZE];
      size = 0;
      String inputData = "";     // stores the selected input data
      for (char c : data.toCharArray())  // convert String to a list of character
         if ((c >= 'a' && c <= 'z') || (c >= 'A' && c <= 'Z'))
            inputData += c;
            
      inputData = inputData.toLowerCase();
      for (int i = 0; i < inputData.length(); i++) {
         inventory[inputData.charAt(i) - 'a']++;
         size++;
      }
   }
   
   // pre : take a character from client, case insensitive.
   //       The letter input must be English character.
   //       (throws IllegalArgumentException if not)
   // post: returns elements get from the place of this letter in
   //       the inventory.
   public int get(char letter) {
      return inventory[testAndChangeLetter(letter) - 'a'];
   }
   
   // pre : take a character and an integer from client, case 
   //       insensitive. The letter input must be English character.
   //       (throws IllegalArgumentException if not) &&
   //       value >= 0 (throws IlligalArgumentException if not)
   // post: changes the amount of the given letter in the inventory
   //       to the given value.
   public void set(char letter, int value) {
      // stores the lowercase form of the input letter.
      char givenLetter = testAndChangeLetter(letter);
      // record the amount changed between the initial and final values.
      int valueDifference = testValue(value) - inventory[givenLetter - 'a'];
      
      inventory[givenLetter - 'a'] = value;
      size += valueDifference;
   }
   
   // post: returns the total number of elements in the current inventory.
   public int size() {
      return size;
   }
   
   // post: returns true if the inventory is empty, false otherwise.
   public boolean isEmpty() {
      return size == 0;
   }
   
   // post: returns a String of sorted, bracketed lowercase version of
   //       the inventory to the console.
   public String toString() {
      String result = "[";
      for (int i = 0; i < inventory.length; i++)
         for (int j = 0; j < inventory[i]; j++)
            result += (char) ('a' + i);
            
      return result += "]";
   }
   
   // pre : take a letter inventory as parameter.
   // post: returns the added version of this inventory and the
   //       other given inventory. (The amount of each character
   //       in the return inventory is the sum of that of this
   //       and the other inventory)
   public LetterInventory add(LetterInventory other) {
      // a temporary inventory to be manipulated
      LetterInventory tempInventory = new LetterInventory("");
      for (int i = 0; i < DEFAULT_SIZE; i++)
         tempInventory.set((char)('a' + i),
                                 inventory[i] + other.get((char)('a' + i)));
      return tempInventory;
   }
   
   // pre : take a letter inventory as parameter.
   // post: returns the subtracted version inventory of this inventory by
   //       the other inventory, returns null if any negtive count appears.
   //       (the amount of characters in this inventory is subtraced by
   //        that of the other inventory)
   public LetterInventory subtract(LetterInventory other) {
      LetterInventory tempInventory = new LetterInventory("");
      for (int i = 0; i < inventory.length; i++) {
         int difference = inventory[i] - other.get((char)('a' + i));
                       // difference of the count of this specific character
         if (difference < 0)
            return null;
         tempInventory.set((char)('a' + i), difference);
      } 
      
      return tempInventory;
   }
   
   // pre : take a character as parameter.
   // post: throws IllegalArgumentException if the given character is not an
   //       English character.
   //       Otherwise return lower-case of the given character.
   private char testAndChangeLetter(char letter) {
      if (letter < 'A' || (letter > 'Z' && letter < 'a') || letter > 'z')
         throw new IllegalArgumentException();
      return Character.toLowerCase(letter);
   }
   
   // pre : take an integer as parameter.
   // post: throws an IllegalArgumentException if the given value is negative.
   //       Otherwise return the given value.
   private int testValue(int value) {
      if (value < 0)
         throw new IllegalArgumentException();
      return value;
   }
}
