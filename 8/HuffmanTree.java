// Shengwei You, CSE 143, Winter 2016
// TA: Melissa Medsker, BO
// Assignment #8: HuffmanTree
// Last modified date: 03/11/16
//
// HuffmanTree provides two constructors: a) build a tree by taking a list with
// the count of each character; b) build a tree by reading the input file of the
// standard format. Clients are allowed to write the current tree into an output
// file in standard format. This class also provides method to decode the huffman
// encoded file.



import java.util.*;
import java.io.*;


public class HuffmanTree {
   // stores the reference to the huffman tree
   private HuffmanNode overallRoot;
   
   // post: build a tree based on the given count of each characters. The
   //       tree follows the prefix property for each assigned character. Smaller
   //       frequencies are stored near the top, and the higher near the bottom.
   public HuffmanTree(int[] count) {
      Queue<HuffmanNode> priorityQueue = new PriorityQueue<HuffmanNode>();
      int size = count.length;
      for (int i = 0; i < size; i++)
         if (count[i] > 0)
            priorityQueue.add(new HuffmanNode(count[i], i));
      priorityQueue.add(new HuffmanNode(1, size));   // add the "pseudo-eof" character
      
      // combines the frist two lowest frequency nodes
      while (priorityQueue.size() > 1) {
         HuffmanNode temp1 = priorityQueue.remove();
         HuffmanNode temp2 = priorityQueue.remove();
         priorityQueue.add(new HuffmanNode(temp1.frequency + temp2.frequency,
                           temp1, temp2));
      }
      // stores the tree
      overallRoot = priorityQueue.remove();
   }
   
   // pre : input must be in standard huffman code format
   // post: builds a new tree by reading an input file that stores the integer
   //       value of characters and their code. Using each token of the first line
   //       as the assigned integer value of character, and the second line as the
   //       code for this character.
   public HuffmanTree(Scanner input) {
      while (input.hasNextLine()) {
         int letter = Integer.parseInt(input.nextLine());
         String code = input.nextLine();
         overallRoot = buildTree(overallRoot, letter, code);
      }
   }
   
   // post: returns a tree based on the given code for the letter, and uses the
   //       root to decide where to add the nodes.
   private HuffmanNode buildTree(HuffmanNode root, int letter, String code) {
      if (code.length() == 0)
         return new HuffmanNode(0, letter);
      else {
         if (root == null)
            root = new HuffmanNode(0);
         if (code.charAt(0) == '0')
            root.left = buildTree(root.left, letter, code.substring(1));
         else  // code.charAt(0) == '1'
            root.right = buildTree(root.right, letter, code.substring(1));
         
         return root;
      }
   }
   
   // post: decode the encoded file by read through the given input, utill reaches
   //       the "eof" character, and then write result to the output
   public void decode(BitInputStream input, PrintStream output, int eof) {
      boolean isEof = false;
      while (!isEof) {
         HuffmanNode current = overallRoot;
         while (current != null && current.left != null && current.right != null)
            if (input.readBit() == 0)
               current = current.left;
            else
               current = current.right;
         if (current != null)
            if (current.letter != eof)
               output.write(current.letter);
            else  // current.letter == eof
               isEof = true;
      }
   }
   
   // post: write this huffman tree into the output with the standard format:
   //       prints the integer value of the character in one line, and the code
   //       of this character in the next line.
   public void write(PrintStream output) {
      writeTo(overallRoot, output, "");
   }
   
   // post: prints out the integer value and the code of the character to the
   //       given output in standard format. Using the root to check and find
   //       the character, and the code to store the code for each character.
   private void writeTo(HuffmanNode root, PrintStream output, String code) {
      if (root != null && root.left == null && root.right == null) {
         output.println(root.letter);
         output.println(code);
      } else if (root != null) {
         writeTo(root.left, output, code + 0);
         writeTo(root.right, output, code + 1);
      }
   }
}