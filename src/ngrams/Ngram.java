package ngrams;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;


public class Ngram 
{

	public static void main(String[] args) throws Exception 
	{
		Configuration conf = new Configuration();
		conf.set("keyword","Unigram");
		Job job = Job.getInstance(conf, "Ngram");
		job.setJarByClass(Ngram.class);
		job.setMapperClass(NgramrMapper.class);
		job.setCombinerClass(NgramReducer.class);
		job.setReducerClass(NgramReducer.class);
		job.setOutputKeyClass(Text.class);
		job.setOutputValueClass(Text.class);
		job.setInputFormatClass(WholeFileInputFormat.class);
		FileInputFormat.addInputPath(job, new Path(args[0]));
		FileOutputFormat.setOutputPath(job, new Path(args[1]));
		System.exit(job.waitForCompletion(true) ? 0 : 1);
	}
}
