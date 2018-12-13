/**
 *	This class represents a Trie of 5-grams.
 *
 *	@author		Christian Westbrook
 *	@version	v1.0 pre-release
 *
 */

package nlm.data;

import java.util.HashMap;
import java.io.Serializable;

public class Trie implements Serializable
{
	private Node root;
    private int n;
	private int v;

	public Trie()
	{
		root = new Node((char) 0, null);
        n = 0;
	}

    /**
     * Insert a word into the trie.
     */
	public void insert(String word)
	{
        // Start at the root
        Node current = root;

        String[] grams = word.split(" ");

        if(grams.length == 1)
            n++;

        for(int i = 0; i < word.length(); i++)
        {
            char c = word.charAt(i);

            // If the next letter already exists
            if(current.getChild(c) != null)
            {
                current = current.getChild(c);
            }
            // If the next letter does not exist
            else
            {
                Node n = new Node(c, current);
                current.placeChild(n);
                current = current.getChild(c);
            }

            // If this is the last iteration (we need to add to the frequency of this word)
            if(i == word.length() - 1)
            {
                // If the frequency node for this word exists
                if(current.getChildren().containsKey(' '))
                {
                    current.getChild(' ').increment();
                }
                else
                {
                    Node n = new Node(' ', current);
                    current.placeChild(n);
                    current.getChild(' ').increment();

					if(grams.length == 1)
						v++;
                }
            }
        }
	}

    public int getFreq(String word)
    {
        // Start at the root
        Node current = root;
        int freq = 0;

        for(int i = 0; i < word.length(); i++)
        {
            char c = word.charAt(i);

            // If the next letter already exists
            if(current.getChild(c) != null)
            {
                current = current.getChild(c);
            }
            // If the next letter does not exist
            else
            {
                return 0;
            }

            // If this is the last iteration
            if(i == word.length() - 1)
            {
                // If the frequency node for this word doesn't exist
                if(current.getChild(' ') == null)
                {
                    return 0;
                }
                else
                {
                    freq = current.getChild(' ').getFreq();
                }
            }
        }

        return freq;
    }

    public HashMap<String, Integer> getSubgramFreqMap(String word)
    {
        HashMap<String, Integer> subgramFreqMap = new HashMap<String, Integer>();

        // Find the input word

        // Start at the root
        Node current = root;

        for(int i = 0; i < word.length(); i++)
        {
            char c = word.charAt(i);

            // If the next letter already exists
            if(current.getChild(c) != null)
            {
                current = current.getChild(c);
            }
            // If the next letter does not exist
            else
            {
                return null;
            }

            // If this is the last iteration
            if(i == word.length() - 1)
            {
                // If the frequency node for this word doesn't exist
                if(current.getChild(' ') == null)
                {
                    return null;
                }
                else
                {
                    // Get all subgrams and their frequencies
                    current = current.getChild(' ');

                    for(char key : current.getChildren().keySet())
                    {
                        generateSubgramFreqMap(subgramFreqMap, current.getChildren().get(key));
                    }

                    return subgramFreqMap;
                }
            }
        }

        return null;
    }

    public HashMap<String, Node> getSubgramMap(String word)
    {
        HashMap<String, Node> subgramMap = new HashMap<String, Node>();

        // Find the input word

        // Start at the root
        Node current = root;

        for(int i = 0; i < word.length(); i++)
        {
            char c = word.charAt(i);

            // If the next letter already exists
            if(current.getChild(c) != null)
            {
                current = current.getChild(c);
            }
            // If the next letter does not exist
            else
            {
                return null;
            }

            // If this is the last iteration
            if(i == word.length() - 1)
            {
                // If the frequency node for this word doesn't exist
                if(current.getChild(' ') == null)
                {
                    return null;
                }
                else
                {
                    // Get all subgram nodes
                    current = current.getChild(' ');

                    for(char key : current.getChildren().keySet())
                    {
                        generateSubgramMap(subgramMap, current.getChildren().get(key));
                    }

                    return subgramMap;
                }
            }
        }

        return null;
    }

    public HashMap<String, Integer> generateSubgramFreqMap(HashMap<String, Integer> subgramFreqMap, Node current)
    {
        if(current.isSpace())
        {
            subgramFreqMap.put(current.build(), current.getFreq());
            return subgramFreqMap;
        }
        else
        {
            for(char key : current.getChildren().keySet())
            {
                generateSubgramFreqMap(subgramFreqMap, current.getChildren().get(key));
            }

            return subgramFreqMap;
        }
    }

    public HashMap<String, Node> generateSubgramMap(HashMap<String, Node> subgramMap, Node current)
    {
        if(current.isSpace())
        {
            subgramMap.put(current.getPrev().build(), current.getPrev());
            return subgramMap;
        }
        else
        {
            for(char key : current.getChildren().keySet())
            {
                generateSubgramMap(subgramMap, current.getChildren().get(key));
            }

            return subgramMap;
        }
    }

    public float probability(String ngram)
    {
        String[] grams = ngram.split(" ");

        if(grams.length == 1)
        {
            float unigramCount = getFreq(grams[0]);
            System.out.println(unigramCount);
            System.out.println(n);

            return (unigramCount + (float) 1 / ((float) n + (float) v));
        }
        else
        {
            StringBuilder prevGram = new StringBuilder();

            for(int i = 0; i < grams.length - 1; i++)
            {
                prevGram.append(" ");
                prevGram.append(grams[i]);
            }
            prevGram.deleteCharAt(0);

            HashMap<String, Integer> subgramFreqMap = getSubgramFreqMap(prevGram.toString());

            float prevGramCount = 0;
            for(String key : subgramFreqMap.keySet())
            {
                prevGramCount += subgramFreqMap.get(key);
            }

            float ngramCount = getFreq(ngram);
            return ((ngramCount) + (float) 1 / (prevGramCount + (float) v));
        }
    }

    public String generate(String sentence)
    {
        String next = null;
        float nextProbability = 0;

        String[] grams = sentence.split(" ");

        if(grams.length > 4)
        {
            System.out.println("Attempting to generate a word from a sentence with more than four words.");
            System.exit(1);
        }

        HashMap<String, Node> subgramMap = getSubgramMap(sentence);
		if(subgramMap != null)
		{
        	for(String key : subgramMap.keySet())
        	{
            	float probability = probability(key);

            	if(probability > nextProbability)
            	{
                	next = key;
                	nextProbability = probability;
            	}
        	}

			String[] nextTokens = next.split(" ");
        	return nextTokens[nextTokens.length - 1];
		}
		else
		{
			System.out.println("0 probability");
			return null;
		}
    }
}
