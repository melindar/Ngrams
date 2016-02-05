package ngrams;

import java.io.IOException;
import java.util.Scanner;
import java.util.StringTokenizer;

import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

public class UnigramYearMapper extends Mapper<NullWritable, Text, Text, Text> 
{
  private Text keyString = new Text();

  public void map(NullWritable key, Text value, Context context) throws IOException, InterruptedException 
  {
    Parser p = new Parser();
    Scanner scan = p.getHeaderInfo(value);
    
    while (scan.hasNextLine()) 
    {
    	String line = scan.nextLine();
    	StringTokenizer itr = new StringTokenizer(line);
        while (itr.hasMoreTokens()) 
        {
        	String token = itr.nextToken();
        	String word = p.removePunctuation(token) + "\t" + p.getDate();
        	keyString.set(word);
        	Text title = new Text(p.getTitle());
        	context.write(keyString,title);
        }
    	
    }
  }
}