package ann;

public class Unit
{
	float b;		// Bias term
	float[] W;		// Array of weights

	public Unit(float[] W, float b)
	{
		this.W = W;
		this.b = b;
	}

	// Computes the output of this Unit given the input values
	public float activate(float[] X)
	{
		return y(X);
	}

	private void y(float[] X)
	{
		return tanh(z(X));
	}

	private float z(float[] X)
	{
		float z = 0;

		for(int i = 0; i < X.length; i++)
		{
			z += X[i] * W[i];
		}

		return z + b;
	}

	private float sigmoid(float z)
	{
		return 1 / (1 + Math.exp(-(z)));
	}

	private float tanh(float z)
	{
		return (Math.exp(z) - Math.exp(-(z))) / (Math.exp(z) + Math.exp(-(z)));
	}
}
