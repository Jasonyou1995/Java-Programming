// Shengwei You, CSE 143, Winter 2016
// TA: Melissa Medsker, BO
// Assignment #7: QuestionNode
// Last modified date: 02/28/16
//
// The QuestionNode class represents node objects with the specified value
// with left and right child nodes. It can be contructed either with both
// left and right child nodes, or no child node (i.e. both child nodes are
// null).

public class QuestionNode {
   // stores the specified value
   public String data;
   // extends the left child node to the other node
   public QuestionNode left;
   // extends the right child node to the other node
   public QuestionNode right;
   
   // post: constructs a leaf node contains the value of the given input
   public QuestionNode(String input) {
      this(input, null, null);
   }
   
   // post: constructs a branch node contains the value of the given input.
   //       The yesNode will be the left child node, and the noNode will be
   //       the right child node.
   public QuestionNode(String input, QuestionNode yesNode, QuestionNode noNode) {
      data = input;
      left = yesNode;
      right = noNode;
   }
}