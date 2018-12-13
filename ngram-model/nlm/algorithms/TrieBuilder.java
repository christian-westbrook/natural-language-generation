/**
 *	This class takes an input directory of tokens and builds a trie of 5-grams.
 *
 *	@author		Christian Westbrook
 *	@version	v1.0 pre-release
 *
 */

package nlm.algorithms;

import java.io.*;
import java.util.HashMap;
import nlm.data.Trie;
import nlm.data.Node;

public class TrieBuilder
{
	Trie t;

	public TrieBuilder(File indir)
	{
		// Make sure indir exists
		if(!indir.exists())
		{
			System.out.println("Attempting to build from a directory that doesn't exist.");
			System.exit(1);
		}

		t = new Trie();

		for(File file : indir.listFiles())
		{
			addFile(file);
		}

		try
		{
			FileOutputStream fos   = new FileOutputStream("trie.dat");
			ObjectOutputStream oos = new ObjectOutputStream(fos);
			oos.writeObject(t);
			oos.close();
		}
		catch (IOException ex)
		{
			ex.printStackTrace();
		}
	}

	public void addFile(File in)
	{
		try
		{
			// Make sure in exists
			if(!in.exists())
			{
				System.out.println("Attempting to add a file that doesn't exist.");
				System.exit(1);
			}

			// Objects for reading the file
			FileReader fr = new FileReader(in);
			BufferedReader br = new BufferedReader(fr);

			String[] grams = new String[5];

			// Iterate through tokens
			String token;
			while((token = br.readLine()) != null)
			{

				if(grams[0] != null || (grams[0] == null && grams[1] != null))
				{
					grams[0] = grams[1];
					grams[1] = grams[2];
					grams[2] = grams[3];
					grams[3] = grams[4];
					grams[4] = token;

					t.insert(grams[4]);
					t.insert(grams[3] + " " + grams[4]);
					t.insert(grams[2] + " " + grams[3] + " " + grams[4]);
					t.insert(grams[1] + " " + grams[2] + " " + grams[3] + " " + grams[4]);
					t.insert(grams[0] + " " + grams[1] + " " + grams[2] + " " + grams[3] + " " + grams[4]);
				}
				else if(grams[1] == null && grams[2] != null)
				{
					grams[1] = grams[2];
					grams[2] = grams[3];
					grams[3] = grams[4];
					grams[4] = token;

					t.insert(grams[4]);
					t.insert(grams[3] + " " + grams[4]);
					t.insert(grams[2] + " " + grams[3] + " " + grams[4]);
					t.insert(grams[1] + " " + grams[2] + " " + grams[3] + " " + grams[4]);
				}
				else if(grams[2] == null && grams[3] != null)
				{
					grams[2] = grams[3];
					grams[3] = grams[4];
					grams[4] = token;

					t.insert(grams[4]);
					t.insert(grams[3] + " " + grams[4]);
					t.insert(grams[2] + " " + grams[3] + " " + grams[4]);
				}
				else if(grams[3] == null && grams[4] != null)
				{
					grams[3] = grams[4];
					grams[4] = token;

					t.insert(grams[4]);
					t.insert(grams[3] + " " + grams[4]);
				}
				else
				{
					grams[4] = token;

					t.insert(grams[4]);
				}
			}

			// Close resources
			br.close();
		}
		catch (IOException ex)
		{
			ex.printStackTrace();
		}
	}

	public Trie getTrie()
	{
		return t;
	}

	public static void main(String[] args)
	{
		File indir = new File("training");
		TrieBuilder t = new TrieBuilder(indir);
	}
}
