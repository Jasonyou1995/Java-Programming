// Shengwei You
// CSE 143 BO with Melissa Medsker
// Homework #3
// Last Modified Date: 01/27/2016
//
//
// Class AssassinManager constructs an object of Assassin game
// with a kill ring, which imports names from the input list, and
// a graveyard, which stores names of the killed players.
//
// Client can decide the next player to be killed, and is also
// allowed to examine the result and fields.


import java.util.*;


public class AssassinManager {
   // refers to the front of the kill ring.
   private AssassinNode killFront;
   // refers to the front of the graveyard.
   private AssassinNode graveFront;
   
   
   // pre : the size of the given list must not be empty
   //       (throws IllegalArgumentException otherwise).
   // post: constructs an assassinManager object (reserves the
   //       order of the names by the given list).
   public AssassinManager(List<String> names) {
      if (names.size() == 0)
         throw new IllegalArgumentException();
      killFront = new AssassinNode(names.get(0));
      AssassinNode current = killFront;
      
      for (int i = 1; i < names.size(); i++) {
         // only add non-duplicate names
         if (!killRingContains(names.get(i))) {
            current.next = new AssassinNode(names.get(i));
            current.next.killer = current.name;
         }
         current = current.next;
      }
      killFront.killer = current.name;
   }
   
   // post: prints the current kill ring. (one killer and the stalked
   //       player in one line, indented four spaces. If only one person
   //       left, prints that the person is stalking themselves.
   public void printKillRing() {
      AssassinNode current = killFront;
      while (current.next != null) {
         System.out.println("    " + current.name + " is stalking "
                                           + current.next.name);
         current = current.next;
      }
      // print the last player in the kill ring
      System.out.println("    " + killFront.killer + " is stalking "
                                          + killFront.name);
   }
   
   // post: prints one killed person and killer in one line, indented
   //       four spaces, with reversed order (most recent killed first).
   //       Produces no output if the graveyard is empty.
   public void printGraveyard() {
      AssassinNode current = graveFront;
      while (current != null) {
         System.out.println("    " + current.name + " was killed by "
                                                  + current.killer);
         current = current.next;
      }
   }
   
   // post: returns true if the given name is in the kill ring,
   //       and false other wise. (Ignoring case)
   public boolean killRingContains(String name) {
      String testName = name.toLowerCase();
      AssassinNode current = killFront;
      
      while (current != null) {
         if (current.name.toLowerCase().equals(testName))
            return true;
         current = current.next;
      }
      return false;
   }
   
   // post: returns true if the given name is in the graveyard
   //       list, and false otherwise. (Ignoring case)
   public boolean graveyardContains(String name) {
      String testName = name.toLowerCase();
      AssassinNode current = graveFront;
      
      while (current != null) {
         if (current.name.toLowerCase().equals(testName))
            return true;
         current = current.next;
      }
      return false;
   }
   
   // post: returns true if the game is over (only one person left in
   //       the kill ring), and false otherwise.
   public boolean gameOver() {
      return killFront.next == null;
   }
   
   // post: returns the name of the winner (the last one left in the
   //       kill ring). Returns null if the game is not over.
   public String winner() {
      return gameOver() ? killFront.name : null;
   }
   
   // pre : the given name should be in the kill ring
   //       (throws IllegalArgumentException otherwise).
   //       Throws IllegalStateException if the game was over
   //       when this method was called.
   // post: transfers the killed person from kill ring to graveyard,
   //       and passes killer of the player to the next one
   //       (ignores case in comparing names).
   public void kill(String name) {
      // eliminates the case when only one person left
      if (gameOver())
         throw new IllegalStateException();
      else if (!killRingContains(name))
         throw new IllegalArgumentException();
      
      String testName = name.toLowerCase();
      String killerTransfer = "";   // store the killer name
      AssassinNode killCurr = killFront;
      AssassinNode graveCurr = graveFront;
      
      // case when the given player is the first one in kill ring
      if (killFront.name.toLowerCase().equals(testName)) {
         graveFront = killFront;
         killFront = killFront.next;
         graveFront.next = graveCurr;
         killCurr = killFront;
         while (killCurr != null && killCurr.next != null) {
            // transfer the killer name of the last player
            if (killCurr.next.next == null)
               killCurr.next.killer = killCurr.name;
            killCurr = killCurr.next;
         }
         killFront.killer = killCurr.name;
      } else {
      // case when given player is in the middle or end of the ring.
         while (killCurr != null && killCurr.next != null) {
            if (killCurr.next.name.toLowerCase().equals(testName)) {
               if (killCurr.next.next != null)
                  killCurr.next.next.killer = killCurr.name;
               else
                  killFront.killer = killCurr.name;
               
               graveFront = killCurr.next;
               killCurr.next = killCurr.next.next;
               graveFront.next = graveCurr;
               // keeps graveCurr refers to the front of the graveyard.
               graveCurr = graveFront;
            }
            killCurr = killCurr.next;
         }
      }
   }
}