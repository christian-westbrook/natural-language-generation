package nlm.algorithms;

import java.util.Scanner;
import java.util.ArrayList;
import java.io.*;
import nlm.data.Trie;
import nlm.data.Node;

public class NLMInteract
{
	Trie t;

	public NLMInteract()
	{
		try
		{
			FileInputStream fis   = new FileInputStream("trie.dat");
			ObjectInputStream ois = new ObjectInputStream(fis);
			t = (Trie) ois.readObject();
			ois.close();
		}
		catch (IOException ex)
		{
			ex.printStackTrace();
			System.exit(1);
		}
		catch (ClassNotFoundException ex)
		{
			ex.printStackTrace();
			System.exit(1);
		}

		Scanner input = new Scanner(System.in);
		String line;

		boolean running = true;
		while(running)
		{
			System.out.println("Please enter a partial sentence.");
			line = input.nextLine();

			if(line.equalsIgnoreCase("exit"))
				System.exit(0);

			System.out.print(line + " ");

			StringBuilder bayesianInference = new StringBuilder();

			String[] splitInput = line.split(" ");
			ArrayList<String> tokens = new ArrayList<String>();

			for(int i = 0; i < splitInput.length; i++)
				tokens.add(splitInput[i]);

			for(int i = 0; i < 10; i++)
			{
				if(tokens.size() < 5)
				{
					for(int j = 0; j < tokens.size(); j++)
					{
						bayesianInference.append(tokens.get(j) + " ");
					}
				}
				else
				{
					for(int j = tokens.size() - 4; j < tokens.size(); j++)
					{
						bayesianInference.append(tokens.get(j) + " ");
					}
				}

				bayesianInference.deleteCharAt(bayesianInference.length() - 1);
				String newToken = t.generate(bayesianInference.toString());
				System.out.print(newToken + " ");
				tokens.add(newToken);
				bayesianInference = new StringBuilder();
			}

			System.out.println();
		}
	}

	public static void main(String[] args)
	{
		new NLMInteract();
	}
}
