package blockchain;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;


public class FileDate {
	private static String file = "data.txt";

	public static void writeVote(String vote) {
		try {
			BufferedWriter out=new BufferedWriter(new FileWriter("data.txt",true));
			out.write(vote);
			out.newLine();
			out.close();
		    
		}catch (IOException e) {
		}
	}

	public static String getData() {
		StringBuilder sb = new StringBuilder();
		try {
			BufferedReader br = new BufferedReader(new FileReader(file));			
			String line = br.readLine();
			while (line != null) {
				sb.append(line);
				sb.append("\n");
				line = br.readLine();
			}
			br.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return sb.toString();
	}
	
	public static void truncate() {
		try {
		    Files.write(Paths.get(file), "".getBytes(), StandardOpenOption.TRUNCATE_EXISTING);
		}catch (IOException e) {
		}
	}
	public static int NLines() {
		int count=0;
		try {
			
			BufferedReader br = new BufferedReader(new FileReader(file));	
			while(br.ready())
			{
				br.readLine();
				count++;
			}
			br.close();
			}catch (IOException e) {
		}
		return count-2;
	}
}
