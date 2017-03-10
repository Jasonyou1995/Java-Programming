// Shengwei You
// CSE 143 BO with Melissa Medsker
// Homework #2
// Last Modified Date: 01/21/2016
//
// Class Guitar37 keep track of 37 guitar strings. It allows
// client to pluck, tic the guitar, and play the given notes
// by the keyboard layout.



public class Guitar37 implements Guitar {
   public static final int DEFAULT_STRINGS = 37; // default number of strings
   public static final String KEYBOARD =
        "q2we4r5ty7u8i9op-[=zxdcfvgbnjmk,.;/' "; // keyboard layout
   
   private GuitarString[] guitar_strings; // keep track of all the strings
   private int tic_times;                 // count the times of tic
   
   
   // post: builds default number of guitar strings with increment frequencies.
   public Guitar37() {
      // build default number of guitar strings
      guitar_strings = new GuitarString[DEFAULT_STRINGS];
      for (int i = 0; i < DEFAULT_STRINGS; i++)
         // designs strings for each key by the desire frequency
         guitar_strings[i] = new GuitarString(440 
                                    * Math.pow(2.0, (i - 24.0) / 12.0));
      tic_times = 0;
   }
   
   // pre : pitch >= -12 && pitch <= 24 (ignores this pitch otherwise)
   // post: pluck the string of the given pitch.
   public void playNote(int pitch) {
      if (pitch >= -12 && pitch <= 24)
         guitar_strings[pitch + 12].pluck();
   }
   
   // post: returns true if the given string character has a 
   //       designed string, and false otherwise.
   public boolean hasString(char string) {
      return KEYBOARD.indexOf(string) >= 0;
   }
   
   // pre : the given string character must be in the keyboard layout
   //       (throws IllegalArgumentException otherwise).
   // post: pluck the given string
   public void pluck(char string) {
      if (KEYBOARD.indexOf(string) < 0 || KEYBOARD.indexOf(string) 
                                                > DEFAULT_STRINGS - 1)
         throw new IllegalArgumentException();
      guitar_strings[KEYBOARD.indexOf(string)].pluck();
   }
   
   // post: returns sum of the sample values of ring buffers from each of
   //       the default number strings.
   public double sample() {
      double sum = 0;      // add the sample values from each string
      for (int i = 0; i < DEFAULT_STRINGS; i++)
         sum += guitar_strings[i].sample();
      return sum;
   }
   
   // post: tic each string once by the keyboard layout order.
   public void tic() {
      for (int i = 0; i < DEFAULT_STRINGS; i++)
         guitar_strings[i].tic();
      tic_times++;
   }
   
   // post: returns the number of times tic guitar strings.
   public int time() {
      return tic_times;
   }
}