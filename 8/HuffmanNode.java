// Shengwei You, CSE 143, Winter 2016
// TA: Melissa Medsker, BO
// Assignment #8: HuffmanNode
// Last modified date: 03/11/16
//
// Constructs a node that can stores the integer value of the given character
// and the value of frequency. Client is allowed to compare the nodes based on
// their frequencies.


public class HuffmanNode implements Comparable<HuffmanNode> {
   // integer value of the assigned letter
   public int letter;
   // stores the frequency
   public int frequency;
   // left reference
   public HuffmanNode left;
   // right reference
   public HuffmanNode right;
   
   // constructs a huffman leaf node with the assigned frequency
   public HuffmanNode(int frequency) {
      this(frequency, null, null);
   }
   
   // constructs a huffman leaf node with the given frequency and the integer
   // value of the given letter
   public HuffmanNode(int frequency, int letter) {
      this.frequency = frequency;
      this.letter = letter;
   }
   
   // constructs a huffman node with the given frequency, and refers to the
   // passed left and right references
   public HuffmanNode(int frequency, HuffmanNode left, HuffmanNode right) {
      this.frequency = frequency;
      this.left = left;
      this.right = right;
   }
   
   // returns positive value when this frequency is greater then that of the other,
   // and negative when it is smaller, and zero when they are equal
   public int compareTo(HuffmanNode other) {
      return this.frequency - other.frequency;
   }
}