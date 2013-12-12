package fr.ece;

import java.io.IOException;

import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

public class DataMapper extends Mapper<LongWritable, Text, Text, NullWritable> {


	public void map(LongWritable key, Text value, Context context) throws IOException,
			InterruptedException {
		System.out.println("MAP CALLED");
		String html = new String(value.toString());
		Document doc = Jsoup.parse(html);
		doc.body().select("script, jscript, style").remove();
		String text = doc.body().text();
		
		context.write(new Text(text), NullWritable.get());
		
	}
	
	public static String cleanData(String textToClean) {
		//System.out.println("Removing HTML tags");
		Document doc = Jsoup.parse(textToClean);
		doc.select("script, jscript, style").remove();
		return doc.text();
		//return Jsoup.parse(textToClean).text();
	}

}
