package ngrams;

import java.io.IOException;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FSDataInputStream;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.io.IOUtils;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.mapreduce.InputSplit;
import org.apache.hadoop.mapreduce.RecordReader;
import org.apache.hadoop.mapreduce.TaskAttemptContext;
import org.apache.hadoop.mapreduce.lib.input.FileSplit;

public class WholeFileRecordReader extends RecordReader<NullWritable, Text> 

/*
 * 
 * Implementation for WholeFileRecordReader class borrowed from:
 * http://stackoverflow.com/questions/30649590/using-wholefileinputformat-with-hadoop-mapreduce-still-results-in-mapper-process
 * as linked from the CS435 course website help section
 * 
 */
{

	private FileSplit fileSplit;
	private Configuration conf;
	private Text value = new Text();
	private boolean processed = false;
	
	@Override
	public void close() throws IOException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public NullWritable getCurrentKey() throws IOException, InterruptedException 
	{
		return NullWritable.get();
	}

	@Override
	public Text getCurrentValue() throws IOException, InterruptedException 
	{
		return value;
	}

	@Override
	public float getProgress() throws IOException, InterruptedException 
	{
		return processed ? 1.0f : 0.0f;
	}

	@Override
	public void initialize(InputSplit split, TaskAttemptContext context) throws IOException, InterruptedException 
	{
		this.fileSplit = (FileSplit) split;
	    this.conf = context.getConfiguration();
	}

	@Override
	public boolean nextKeyValue() throws IOException, InterruptedException 
	{
		if (!processed)
		{
	        byte[] contents = new byte[(int) fileSplit.getLength()];
	        Path file = fileSplit.getPath();
	        FileSystem fs = file.getFileSystem(conf);
	        FSDataInputStream in = null;
	        try
	        {
	            in = fs.open(file);
	            IOUtils.readFully(in, contents, 0, contents.length);
	            value.set(contents, 0, contents.length);
	        }
	        finally
	        {
	            IOUtils.closeStream(in);
	        }
	        processed = true;
	        return  true;
	    }
	    return false;
	}
	
}