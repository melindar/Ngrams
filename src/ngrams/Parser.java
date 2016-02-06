package ngrams;

import java.util.ArrayList;
/*import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;*/
import java.util.Scanner;
import java.util.StringTokenizer;

import org.apache.hadoop.io.Text;

public class Parser 
{
	private String date;
	private String author;
	private String title;
	
	/*public static void main(String[] args)
	{
		String file = "../sampleData/77.txt";
		Parser parse = new Parser();
		Scanner scan = parse.openFile(file);
		parse.getHeaderInfo(scan);
		System.out.println(parse.date);
		System.out.println(parse.author);
		System.out.println(parse.title);
	}*/
	
	/*public Scanner openFile(String filename)
	{
		Scanner scan = null;
		try {
			File file = new File(filename);
			scan = new Scanner(new FileInputStream(file));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return scan;
	}*/
	
	/******************************** Getters and Setters ********************************/
	
	public String getDate() 
	{
		return date;
	}

	public String getAuthor() 
	{
		return author;
	}

	public String getTitle() 
	{
		return title;
	}
	
	public String getValue()
	{
		return getDate();
	}

	/******************************** Methods to retrieve header data ********************************/
	
	public Scanner getHeaderInfo(Text header) //***************** Change this to (Text header) for Ngam, (Scanner scan) for testing! *****************
	{
		String stopString = "*** START";
		Scanner scan = new Scanner(header.toString()); //Comment this out for testing
		if(scan.hasNextLine())
		{
			String line = scan.nextLine();
			
			while(!line.startsWith(stopString))
			{
				if(line.startsWith("Title:"))
				{
					this.title = parseTitle(line);
				}
				else if(line.startsWith("Author:"))
				{
					this.author = parseAuthor(line);
				}
				else if(line.startsWith("Release Date:"))
				{
					this.date = parseDate(line);
				}
				
				line = scan.nextLine();
			}
			
		}
		
		return scan;
	}
	
	
	/******************************** Methods to parse header ********************************/
	
	public String parseDate(String containsDate)
	{
		@SuppressWarnings("resource")
		Scanner scan = new Scanner(containsDate);
		String pattern = "\\p{Digit}\\p{Digit}\\p{Digit}\\p{Digit}";
		String date = scan.findInLine(pattern);
		return date;
	}
	
	public String parseAuthor(String containsAuthor)
	{
		String[] tokens = containsAuthor.split("\\p{Blank}");  
		String lastToken = tokens[tokens.length -1];      
		return lastToken; 
	}
	
	public String parseTitle(String containsTitle)
	{
		String title = containsTitle.replaceAll("Title:", "").trim();
		return title;
	}
	
	/******************************** Methods to parse Ngrams ********************************/

	public String removePunctuation(String s)
	{
		s = s.replaceAll("\\p{Punct}+", "");
		return s.toLowerCase();
	}
	
	public ArrayList<String> tokenizeUnigramLine(String line)
	{
		ArrayList<String> tokens = new ArrayList<String>();
		StringTokenizer itr = new StringTokenizer(line);
        while (itr.hasMoreTokens()) 
        {
        	String token = itr.nextToken().trim();
        	String word = removePunctuation(token);
        	if(word.length() > 0) //Make sure the string is not just whitespace
        	{
        		tokens.add(word);
        	}
        }
        
        return tokens;
	}

}
