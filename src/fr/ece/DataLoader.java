package fr.ece;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.conf.Configured;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.input.TextInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.mapreduce.lib.output.TextOutputFormat;
import org.apache.hadoop.util.Tool;
import org.apache.hadoop.util.ToolRunner;
public class DataLoader extends Configured implements Tool  {
	
	static List<String> listOfHTMLFiles;
	static String cleanText;

	
	public static void main(String[] args) throws Exception {
		int res = ToolRunner.run(new Configuration(), new DataLoader(), args);
		System.exit(res);

	}
	
	/**
	 * Running MapReduceJob
	 */
	public int run(String[] args) throws Exception{

		Configuration conf = new Configuration();
        Job job = new Job(conf, "dataloader");
        job.setJarByClass(DataLoader.class);
        job.setMapperClass(DataMapper.class);
        job.setNumReduceTasks(0);
        job.setOutputKeyClass(Text.class);
        job.setOutputValueClass(NullWritable.class);

        job.setMapOutputKeyClass(NullWritable.class);
        job.setMapOutputValueClass(Text.class);

        job.setInputFormatClass(TextInputFormat.class);
        job.setOutputFormatClass(TextOutputFormat.class);
        FileInputFormat.addInputPath(job, new Path(args[0])); 
        FileOutputFormat.setOutputPath(job, new Path(args[1]));
        boolean result = job.waitForCompletion(true);
        
		return 0;
		
	}
	
	/**
	 * Get HTML files recursively by having the path
	 * @param dir: path of the directory to look into
	 */
	public static void displayDirectoryContents(File dir) {
		try {
			File[] files = dir.listFiles();
			for (File file : files) {
				if (file.isDirectory()) {
					displayDirectoryContents(file);
				} else {
					if (file.getCanonicalPath().endsWith(".html"))
						listOfHTMLFiles.add(file.getCanonicalPath());
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}


}
