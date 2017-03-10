// Shengwei You
// CSE 143 BO with Melissa Medsker
// Homework #2
// Last Modified Date: 01/21/2016
//
// Class GuitarString can be use to build a string depends on
// the given frequency. It also allows pluck and tic the string.



import java.util.*;

public class GuitarString {
   // Use this factor to generate tic method of the string.
   public static final double EnergyDecayFactor = 0.996;
   // Ring buffer that manipulates the performence of the string.
   private Queue<Double> q = new LinkedList<Double>();
   // The desired capacity of the ring buffer.
   private int capacity;
   
   
   // pre : frequency > 0 && frequency <= 29400
   //       (throws IllegalArgumentException otherwise).
   // post: build a guitar string by the given frequency.
   public GuitarString(double frequency) {
      // 29400 is the maximum value to gaurantee the valid capacity
      if (frequency <= 0 || frequency > 29400)
         throw new IllegalArgumentException();
      
      // initialize the desired capacity for the ring buffer
      capacity = (int) Math.round(StdAudio.SAMPLE_RATE / frequency);
      for (int i = 0; i < capacity; i++)
         q.add(0.0);
   }
   
   // pre : init.length >= 2 (throws IllegalArgumentException otherwise).
   // post: build a guitar string by the given initial values.
   public GuitarString(double[] init) {
      if (init.length < 2)
         throw new IllegalArgumentException();
      for (int i = 0; i < init.length; i++)
         q.add(init[i]);
   }
   
   // post: fills the ring buffer with random values, and
   //       -0.5 <= value < 0.5.
   public void pluck() {
      // generates some random numbers
      Random rand = new Random();
      for (int i = 0; i < capacity; i++) {
         q.remove();
         q.add(rand.nextDouble() - 0.5);
      }
   }
   
   // post: removes the front value from the ring buffer, and apply
   //       the Karplus-Strong update once, then append the result to
   //       the back of the ring buffer.
   public void tic() {
      double first = q.remove();    // front value from the ring buffer
      double second = q.peek();   // second front value from the ring buffer
      q.add(EnergyDecayFactor * 0.5 * (first + second));
   }
   
   // post: return the first sample value at the front of the ring buffer.
   public double sample() {
      return q.peek();
   }
}