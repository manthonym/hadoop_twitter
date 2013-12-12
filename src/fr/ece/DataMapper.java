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
		String html = new String(value.toString());
		Document doc = Jsoup.parse(html);
		doc.body().select("script, jscript, style").remove();
		String text = doc.body().text();
		
		context.write(new Text(text), NullWritable.get());
		
	}
	
	/**
	 * Clean data by removing html tags
	 * @param textToClean
	 * @return
	 */
	public static String cleanData(String textToClean) {
		Document doc = Jsoup.parse(textToClean);
		doc.select("script, jscript, style").remove();
		return doc.text();
	}

}
