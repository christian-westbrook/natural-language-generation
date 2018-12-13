/**
 *	This class represents a single node in a trie of 5-grams.
 *
 *	@author		Christian Westbrook
 *	@version	v1.0 pre-release
 *
 */

package nlm.data;

import java.util.HashMap;
import java.io.Serializable;

public class Node implements Serializable
{
	private char c;
	private Node prev;
	private HashMap<Character, Node> children;

    private int freq;
    private int level;
    private int length;

	public Node(char c, Node prev)
	{
        // If this isn't the root node
		if(c != 0 && prev != null)
        {
            this.c = c;
 			this.prev = prev;
            level = prev.getLevel();
            length = prev.getLength() + 1;
            if(prev.isSpace())
                level++;
        }
        else
        {
            this.c = c;
            level = 1;
            length = 0;
        }

        freq = 0;
        children = new HashMap<Character, Node>();
	}

    /**
	 *	Builds this n-gram
	 */
    public String build()
    {
        StringBuilder ngram = new StringBuilder();

        Node current = this;
        ngram.append(current.getChar());

        while(current.prev != null)
        {
            current = current.prev;
            ngram.append(current.getChar());
        }

        ngram.reverse();
        ngram.deleteCharAt(0);

        return ngram.toString();
    }

    public void placeChild(Node next)
    {
        if(!children.containsKey(next.getChar()))
            children.put(next.getChar(), next);
        else
        {
            System.out.println("Attempted to place a child that already exists.");
            build();
            System.exit(1);
        }
    }

    public void increment()
    {
        if(isSpace())
        {
            freq++;
        }
        else
        {
            System.out.println("Attempted to increment a node that doesn't track frequency.");
            build();
            System.exit(1);
        }
    }

    // Test methods

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

    public boolean isSpace()
    {
        if(c == ' ')
            return true;
        else
            return false;
    }

    // Getters and setters
    public int getFreq()
    {
        if(!isSpace())
        {
            System.out.println("Called for frequency on a node that doesn't track frequency");
            build();
            System.exit(1);
        }

        return freq;
    }

    public Node getPrev()
    {
        return prev;
    }

    public char getChar()
    {
        return c;
    }

    public int getLevel()
    {
        return level;
    }

    public int getLength()
    {
        return length;
    }

    public Node getChild(char c)
    {
        /*if(children.containsKey(c))
            return children.get(c);
        else
            return null;*/

        return children.get(c);
    }

    public HashMap<Character, Node> getChildren()
    {
        return children;
    }
}
