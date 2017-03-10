// it seems like that the HM will first choose the word group that don't
// contains the guessed char, instead of the largest size group...
// (This is the problem in choosing the pattern)


import java.util.*;

public class TestHM {
   public static void main(String[] args) {
      ArrayList<String> list = new ArrayList<String>();
      ArrayList<String> emptyList = new ArrayList<String>();
      list.add("good");
      list.add("kook");
      list.add("like");
      list.add("okay");
      list.add("oooa");
      HangmanManager HM = new HangmanManager(emptyList, 4, 10);
      System.out.println("pattern: " + HM.pattern());
   }
}