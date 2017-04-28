public class SHA256 
{
	private static int[] H = {0x6a09e667, 0xbb67ae85, 0x3c6ef372, 0xa54ff53a, 0x510e527f, 0x9b05688c, 0x1f83d9ab, 0x5be0cd19};
	private static int[] k = {0x428a2f98, 0x71374491, 0xb5c0fbcf, 0xe9b5dba5, 0x3956c25b, 0x59f111f1, 0x923f82a4, 0xab1c5ed5,
		0xd807aa98, 0x12835b01, 0x243185be, 0x550c7dc3, 0x72be5d74, 0x80deb1fe, 0x9bdc06a7, 0xc19bf174,
		0xe49b69c1, 0xefbe4786, 0x0fc19dc6, 0x240ca1cc, 0x2de92c6f, 0x4a7484aa, 0x5cb0a9dc, 0x76f988da,
		0x983e5152, 0xa831c66d, 0xb00327c8, 0xbf597fc7, 0xc6e00bf3, 0xd5a79147, 0x06ca6351, 0x14292967,
		0x27b70a85, 0x2e1b2138, 0x4d2c6dfc, 0x53380d13, 0x650a7354, 0x766a0abb, 0x81c2c92e, 0x92722c85,
		0xa2bfe8a1, 0xa81a664b, 0xc24b8b70, 0xc76c51a3, 0xd192e819, 0xd6990624, 0xf40e3585, 0x106aa070,
		0x19a4c116, 0x1e376c08, 0x2748774c, 0x34b0bcb5, 0x391c0cb3, 0x4ed8aa4a, 0x5b9cca4f, 0x682e6ff3,
		0x748f82ee, 0x78a5636f, 0x84c87814, 0x8cc70208, 0x90befffa, 0xa4506ceb, 0xbef9a3f7, 0xc67178f2};

	public static String Hash(String pass) 
	{
		byte[] padded = padMessage(pass);

		byte[][] parsed = new byte[padded.length/64][64];

		for(int i = 0; i < parsed.length; i++)
			for(int j = 0; j < 64; j++)
				parsed[i][j] = padded[j + i*64];


		byte[][] M = new byte[16][4];
		byte[][] W = new byte[64][4];
		

		for(byte[] block: parsed)
		{
			for(int i = 0; i < 16; i++)
				for(int j = 0; j < 4; j++)
					M[i][j] = block[j + 4*i];

			for(int i = 0; i < 16; i++)
				for(int j = 0; j < 4; j++)
					W[i][j] = M[i][j];

			for(int i = 16; i < 64; i++)
				W[i] = intToByteArray(littleSigma1(byteArrayToInt(W[i-2]))
						^byteArrayToInt(W[i-7])
						^littleSigma0(byteArrayToInt(W[i-15]))
						^byteArrayToInt(W[i-16]));

			int a = H[0];
			int b = H[1];
			int c = H[2];
			int d = H[3];
			int e = H[4];
			int f = H[5];
			int g = H[6];
			int h = H[7];
			int T, T2;

			for(int i = 0; i < 64; i++)
			{
				T = h + bigSigma1(e)^ch(e,f,g)^k[i]^byteArrayToInt(W[i]);
				T2 = bigSigma0(a)^maj(a,b,c);
				h = g;
				g = f;
				f = e;
				e = d + T;
				d = c;
				c = b;
				b = a;
				a = T + T2;
			}
			H[0] = a + H[0];
			H[1] = b + H[1];
			H[2] = c + H[2];
			H[3] = d + H[3];
			H[4] = e + H[4];
			H[5] = f + H[5];
			H[6] = g + H[6];
			H[7] = h + H[7];
			
		}
		
		byte[] last = new byte[32];
		
		for(int i = 0; i < 8; i++)
			for(int j = 0; j < 4; j++)
				last[j + i*4] = intToByteArray(H[i])[j];
		
		return toHexString(last);

	}
	
	private static String toHexString(byte[] bytes)
	{
		char[] hexArray = {'0','1','2','3','4','5','6','7','8','9','a','b','c','d','e','f'};
		char[] hexChars = new char[bytes.length * 2];
		int v;
		for ( int j = 0; j < bytes.length; j++ ) 
		{
			v = bytes[j] & 0xFF;
			hexChars[j*2] = hexArray[v/16];
			hexChars[j*2 + 1] = hexArray[v%16];
		}
		return new String(hexChars);
	}

	public static int byteArrayToInt(byte[] b) 
	{
		return   b[3] & 0xFF |
				(b[2] & 0xFF) << 8 |
				(b[1] & 0xFF) << 16 |
				(b[0] & 0xFF) << 24;
	}

	public static  byte[] intToByteArray(int value) 
	{
		return new byte[] {
				(byte)(value >>> 24),
				(byte)(value >>> 16),
				(byte)(value >>> 8),
				(byte)value};
	}

	private static byte[] padMessage(String message)
	{
		byte[] mes = message.getBytes();

		int c = (mes.length + 1) % 64;
		int buffer = 0;
		if(c < 60)
			buffer = 60 - c;
		else if(c > 60)
			buffer = 64 - c + 60;

		byte[] len = intToByteArray(mes.length);

		byte[] result = new byte[mes.length + 1 + buffer + 4]; //Final padded is length of original message + 1 bit + padding up to 54%64 + message length

		for(int i = 0; i < mes.length; i++)
			result[i] = mes[i];

		result[mes.length] = (byte) 0x80; // "10000000"

		for(int i = 0; i < 4; i++)
			result[result.length - i - 1] = len[len.length - i - 1];

		return result;
	}

	private static int ch(int x, int y, int z)
	{
		return (x&y)^(~x&z);
	}

	private static int maj(int x, int y, int z)
	{
		return (x&y)^(x&z)^(y^z);
	}

	private static int bigSigma0(int x)
	{
		return (x >> 2)|(x >> 32 - 2)^(x >> 13)|(x >> 32 - 13)^(x >> 22)|(x >> 32 - 22);
	}

	private static int bigSigma1(int x)
	{
		return (x >> 6)|(x >> 32 - 6)^(x >> 11)|(x >> 32 - 11)^(x >> 25)|(x >> 32 - 25);
	}

	private static int littleSigma0(int x)
	{
		return (x >> 7)|(x >> 32 - 7)^(x >> 18)|(x >> 32 - 18)^(x >>> 3);
	}

	private static int littleSigma1(int x)
	{
		return (x >> 17)|(x >> 32 - 17)^(x >> 9)|(x >> 32 - 9)^(x >>> 10);
	}

}
