package ngrams;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

public class UnigramYearMapper extends Mapper<NullWritable, Text, Text, Text> 
{
	private Text keyString = new Text();

	public void map(NullWritable key, Text value, Context context) throws IOException, InterruptedException 
	{
		Configuration conf = context.getConfiguration();
		String keyword = conf.get("keyword");
		
		Parser p = new Parser();
		Scanner scan = p.getHeaderInfo(value);

		while (scan.hasNextLine()) 
		{
			String line = scan.nextLine();
			if(line.startsWith("*** END"))
				break;
			ArrayList<String> tokens = p.tokenizeUnigramLine(line);
			for(String word : tokens)
			{
				word = word +  "\t" + p.getValue() + keyword;
				keyString.set(word);
				Text title = new Text(p.getTitle());
				context.write(keyString,title);
			}

		}
		
		scan.close();
	}
}