package ngrams;

import java.util.Scanner;

import org.apache.hadoop.io.Text;

public class Parser 
{
	private String date;
	private String author;
	private String title;
	
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

	/******************************** Methods to retrieve data ********************************/
	
	public Scanner getHeaderInfo(Text header)
	{
		String stopString = "*** START OF THIS PROJECT GUTENBERG EBOOK";
		Scanner scan = new Scanner(header.toString());
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
		
		return scan;
	}
	
	
	/******************************** Methods to parse data ********************************/
	
	public String removePunctuation(String s)
	{
		s = s.replaceAll("\\p{Punct}+", "");
		return s.toLowerCase();
	}
	
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

}
