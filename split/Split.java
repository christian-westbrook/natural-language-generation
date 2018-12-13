package split;

import java.io.*;
import java.util.Random;

public class Split
{
	public static void main(String[] args)
	{
		File indir = new File("tokens");
		Random r = new Random();

		int id = 0;
		int training = 0;

		for(File file : indir.listFiles())
		{
			int n = r.nextInt(100) + 1;

			if(n <= 80 && training < 1500)
			{
				file.renameTo(new File("./training/" + id + ".txt"));
				training++;
			}
			else
			{
				file.renameTo(new File("./testing/" + id + ".txt"));
			}

			id++;
		}
	}
}
