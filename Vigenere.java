import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringReader;
import java.io.UncheckedIOException;
import java.math.BigInteger;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.Arrays;
import java.util.Iterator;
import java.util.Random;
import java.util.Scanner;
import java.util.stream.Stream;

public class Vigenere
{
	private String key;

	/**
	 * Constructs a new encoder/decoder using the Vigenere Cipher.
	 *
	 * @param key the value to use for the key
	 * @author StevesVR4
	 */
	public Vigenere(CharSequence key)
	{
		this.key = sanitizeValue(key);
	}

	/**
	 * Constructs a new encoder/decoder using the Vigenere Cipher with a pseudo-random key.
	 * The generated key will be the same for all instances which use the same seed and length.
	 *
	 * @param seed starting value for the random character generation
	 * @param length the length of key to generate
	 * @author StevesVR4
	 */
	public Vigenere(long seed, int length)
	{
		StringBuilder key = new StringBuilder();
		Random rand = new Random(seed);

		for (int i = 0; i < length; i++)
		{
			int nextValue = rand.nextInt(26);
			key.append((char)(nextValue + 'A'));
		}

		this.key = key.toString();
	}

	private int sanitizeCharacter(char value)
	{
		// a negative one signifies an invalid character that should not be used
		int returnValue = -1;

		// make sure only 'A' through 'Z' are acceptable.
		if (((value >= 'A') && (value <= 'Z')) || ((value >= 'a') && (value <= 'z')))
		{
			returnValue = Character.toUpperCase(value);
		}

		return returnValue;
	}

	private String sanitizeValue(CharSequence value)
	{
		StringBuilder newValue = new StringBuilder();

		// go through and "sanitize" the string.
		// make sure only 'A' through 'Z' are in the string.  strip out any invalid characters.
		for (int i = 0; i < value.length(); i++)
		{
			int newChar = sanitizeCharacter(value.charAt(i));
			if (newChar != -1)
			{
				newValue.append((char)newChar);
			}
		}

		return newValue.toString();
	}

	/**
	 * Returns the key used to encode/decode the text.
	 *
	 * @return the key
	 * @author StevesVR4
	 */
	public String getKey()
	{
		return key;
	}

	private char getKeyIndex(int index)
	{
		int keyLength = key.length();
		int keyOffset = index % keyLength;
		return key.charAt(keyOffset);
	}

	/**
	 * Encodes the string using the Vigenere Cipher.
	 *
	 * @param message the value to encode
	 * @param the encrypted message
	 * @author StevesVR4
	 */
	public String encode(String message)
	{
		return encode(message, 0);
	}

	/**
	 * Encodes the string using the Vigenere Cipher.
	 *
	 * @param message the value to encode
	 * @param groupLength the number of characters to group in the output
	 * @return the encrypted message
	 * @author StevesVR4
	 */
	public String encode(String message, int groupLength)
	{
		String value = null;

		try
		{
			value = encode(new StringReader(message), groupLength);
		}
		catch (IOException ioe)
		{
			// this should NEVER happen with a StringReader
			assert(false);
			ioe.printStackTrace();
		}

		return value;
	}

	/**
	 * Encodes the contents of the input stream using the Vigenere Cipher.
	 *
	 * @param input the stream to encode
	 * @return the encrypted message
	 * @author StevesVR4
	 */
	public String encode(Reader input) throws IOException
	{
		return encode(input, 0);
	}

	/**
	 * Encodes the contents of the input stream using the Vigenere Cipher.
	 *
	 * @param input the stream to encode
	 * @param groupLength the number of characters to group in the output
	 * @return the encrypted message
	 * @author StevesVR4
	 */
	public String encode(Reader input, int groupLength) throws IOException
	{
		return transform(input, groupLength, true);
	}

	/**
	 * Decodes the contents of the input stream using the Vigenere Cipher.
	 *
	 * @param message the string to decode
	 * @return the decrypted message
	 * @author StevesVR4
	 */
	public String decode(String message)
	{
		return decode(message, 0);
	}

	/**
	 * Decodes the contents of the input stream using the Vigenere Cipher.
	 *
	 * @param message the string to decode
	 * @param groupLength the number of characters to group in the output
	 * @return the decrypted message
	 * @author StevesVR4
	 */
	public String decode(String message, int groupLength)
	{
		String value = null;

		try
		{
			value = decode(new StringReader(message), groupLength);
		}
		catch (IOException ioe)
		{
			// this should NEVER happen with a StringReader
			assert(false);
			ioe.printStackTrace();
		}

		return value;
	}

	/**
	 * Decodes the contents of the input stream using the Vigenere Cipher.
	 *
	 * @param input the stream to decode
	 * @return the decrypted message
	 * @author StevesVR4
	 */
	public String decode(Reader input) throws IOException
	{
		return decode(input, 0);
	}

	/**
	 * Decodes the contents of the input stream using the Vigenere Cipher.
	 *
	 * @param input the stream to decode
	 * @param groupLength the number of characters to group in the output
	 * @return the decrypted message
	 * @author StevesVR4
	 */
	public String decode(Reader input, int groupLength) throws IOException
	{
		return transform(input, groupLength, false);
	}

	private String transform(Reader input, int groupLength, boolean encode) throws IOException
	{
		StringBuilder output = new StringBuilder();
		int counter = 0;
		int curChar;

		while ((curChar = input.read()) != -1)
		{
			int newValue = sanitizeCharacter((char)curChar);
			if (newValue != -1)
			{
				int keyChar = getKeyIndex(counter) - 'A';
				int prevChar = newValue - 'A';
				int newChar;
				if (encode)
				{
					newChar = (keyChar + prevChar) % 26;
				}
				else
				{
					newChar = (prevChar - keyChar + 26) % 26;
				}
				output.append((char)(newChar + 'A'));

				counter++;

				// now check to see if a blank space should be added to the string
				// (by waiting until after the counter has been incremented, there is no need
				// for a special check to prevent padding after the first character)
				if ((groupLength > 0) && (counter % groupLength == 0))
				{
					//output.append(' ');
				}
			}
			else {
				output.append((char)(curChar));
			}
			
			
		}

		return output.toString();
	}
	
}
