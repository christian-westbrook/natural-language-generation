/**
 *	This class takes a single node in a trie of 5-grams.
 *
 *	@author		Christian Westbrook
 *	@version	v1.0 pre-release
 *
 */

 package nlm.data;

 public class Node
 {
	 char c;
	 Node prev;
	 ArrayList<Node> children;

	 public Node(char c, Node prev)
	 {
		 if(c != 0 && prev != null)
		 {
			 this.c = c;
 			 this.prev = prev;
		 }

		 children = new ArrayList<Node>();
	 }

	 /**
	  *	Determines if a trie node is a leaf node.
	  */
	 public boolean isLeaf()
	 {
		 if(children.size() == 0)
		 	return true;
		else
			return false;
	 }
 }
