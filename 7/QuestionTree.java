// Shengwei You, CSE 143, Winter 2016
// TA: Melissa Medsker, BO
// Assignment #7: QuestionTree
// Last modified date: 02/28/16
//
// The QuestionTree class provides an object guessing game to the user.
// User can choose to read questions and anwers from other input files.
// If the guessed result is not the same as what the user thought, it
// will ask the user to enter a distinctive question for that object.
// All the change will be stored and can be write into other specified
// output file.

import java.util.*;
import java.io.*;

public class QuestionTree {
   // stores the reference to the over all root 
   private QuestionNode overallRoot;
   // used to get the input from the client
   private Scanner console;
   
   // constructs a new QuestionTree built by a leaf node, and stores the
   // "computer" as its value. 
   public QuestionTree() {
      overallRoot = new QuestionNode("computer");
      console = new Scanner(System.in);
   }
   
   // pre : given input file should be legal and in standard format.
   // post: replaces all the information in the current question tree
   //       by the given input file. The input Scanner is linked to the
   //       target file. Stores the question in the branch node, and answer
   //       in the leaf node. 
   public void read(Scanner input) {
      overallRoot = replace(overallRoot, input);
   }
   
   // pre : given input file should be legal and in standard format.
   // post: replaces all the information in the current question tree
   //       by the given input file. The input Scanner is linked to the
   //       target file. Stores the question in the branch node, and answer
   //       in the leaf node. Use the QuestionNode root to keep track
   //       of the place to append next value.
   private QuestionNode replace(QuestionNode root, Scanner input) {
      if (input.hasNextLine()) {
         String firstLine = input.nextLine();
         root = new QuestionNode(input.nextLine());
         if (firstLine.contains("Q")) {
            root.left = replace(root.left, input);
            root.right = replace(root.right, input);
         }
      }
      return root;
   }
   
   // post: stores all the information in the this question tree into
   //       the given output file. The value in the branch node will be
   //       specified as question, and the value in the leaf node will be
   //       specified as answer.
   public void write(PrintStream output) {
      writeFile(overallRoot, output);
   }
   
   // post: stores all the information in the question tree into the
   //       given output file. Use the QuestionNode root to keep track
   //       of the type of node (branch or leaf). The value in the branch
   //       node will be specified as question, and the value in the leaf
   //       node will be specified as answer.
   private void writeFile(QuestionNode root, PrintStream output) {
      if (root.left == null) {   // root is a leaf node
         output.println("A:");
         output.println(root.data);
      } else {                   // root is a branch node
         output.println("Q:");
         output.println(root.data);
         writeFile(root.left, output);
         writeFile(root.right, output);
      }
   }
   
   // post: guess the object thought by the user. Make a new pair of question
   //       and answer object if the guessed result is different from that of
   //       the user's. 
   public void askQuestions() {
      overallRoot = explore(overallRoot);
   }
   
   // post: returns the proper node after each guess. No change will be
   //       be made, if the result is the same as what the user thought.
   //       It will ask for and append a new pair of queation and answer
   //       for user's thought object, if the guessed result is different
   //       from that of the user's. Use the given root to check whether
   //       the node is branch node (question) or leaf node (answer).
   private QuestionNode explore(QuestionNode root) {
      if (root != null && root.left == null && root.right == null) {
         if (yesTo("Would your object happen to be " + root.data + "?"))
            System.out.println("Great, I got it right!");
         else {
            System.out.print("What is the name of your object? ");
            String result = console.nextLine();
            System.out.println("Please give me a yes/no question that");
            System.out.println("distinguishes between your object");
            System.out.print("and mine--> ");
            String question = console.nextLine();
            
            if (yesTo("And what is the answer for your object?"))
               root = new QuestionNode(question, new QuestionNode(result), root);
            else
               root = new QuestionNode(question, root, new QuestionNode(result));
         }
      } else if (root != null) {
         if (yesTo(root.data))
            root.left = explore(root.left);
         else
            root.right = explore(root.right);
      }
      return root;
   }
   
   // post: asks the user a question, forcing an answer of "y" or "n";
   //       returns true if the answer was yes, returns false otherwise
   public boolean yesTo(String prompt) {
      System.out.print(prompt + " (y/n)? ");
      String response = console.nextLine().trim().toLowerCase();
      while (!response.equals("y") && !response.equals("n")) {
         System.out.println("Please answer y or n.");
         System.out.print(prompt + " (y/n)? ");
         response = console.nextLine().trim().toLowerCase();
      }
      return response.equals("y");
   }   
}