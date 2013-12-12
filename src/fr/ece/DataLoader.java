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
	
	public int run(String[] args) throws Exception{
		/*

		System.out.println("Program has started");
		if(args.length == 0)
	    {
	        System.out.println("Proper Usage is: java -jar program_name directory outputfile");
	        System.exit(0);
	    }
		System.out.println("Analyzing your files");
		
		listOfHTMLFiles = new ArrayList<String>();
		File inputFile;
		FileReader in;
		BufferedReader br;

		//File outputFile = new File("out.txt");
		//FileWriter out;
		BufferedWriter out;

		String textToClean = "";
		String currentLine;

		// Get all HTML files
		displayDirectoryContents(new File(args[0]));
		
		System.out.println("Found " + listOfHTMLFiles.size() + " files");

		JobConf conf = new JobConf(DataLoader.class);

		conf.setJobName("DataLoader");

		for (int j = 0; j < listOfHTMLFiles.size(); j++) {
		//FileInputFormat.addInputPath(conf, new Path(listOfHTMLFiles.get(j)));
		}
		FileInputFormat.addInputPath(conf, new Path(args[0]));
		FileOutputFormat.setOutputPath(conf, new Path(args[1]));

		conf.setMapperClass(DataMapper.class);
		conf.setNumReduceTasks(0);
		//conf.setReducerClass(DataLoaderReducer.class);

		conf.setOutputKeyClass(NullWritable.class);

		conf.setOutputValueClass(Text.class);

		JobClient.runJob(conf);
		*/
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
	/*
	public static void main(String[] args) throws Exception {
	    JobConf conf = new JobConf(DataLoader.class);
	    conf.setJobName("wordcount");

	    conf.setOutputKeyClass(Text.class);
	    conf.setOutputValueClass(IntWritable.class);

	    conf.setMapperClass(DataLoaderMapper.class);
	    conf.setCombinerClass(DataLoaderReducer.class);
	    conf.setReducerClass(DataLoaderReducer.class);

	    conf.setInputFormat(TextInputFormat.class);
	    conf.setOutputFormat(TextOutputFormat.class);
	    
	    String basePath = args[0];
	    List<Path> inputhPaths = new ArrayList<Path>();
	    FileSystem fs = FileSystem.get(conf);
	    FileStatus[] listStatus = fs.globStatus(new Path(basePath + "/*"));
	    for(FileStatus fstat : listStatus)
	    {
	    	if( fstat.isDir() )
	    	{
	    		//todo : recursivity
	    	}
	    	else
	    	{
	    		inputhPaths.add(fstat.getPath());
	    	}
	    }
	    FileInputFormat.setInputPaths(conf, (Path[])inputhPaths.toArray(new Path[inputhPaths.size()]) );

	    FileInputFormat.setInputPaths(conf, new Path(args[0]));
	    FileOutputFormat.setOutputPath(conf, new Path(args[1]));

	    JobClient.runJob(conf);
	  }
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
