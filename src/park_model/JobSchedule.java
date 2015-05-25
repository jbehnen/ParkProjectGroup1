package park_model;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Collections;
import java.util.GregorianCalendar;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import config_files.Config;

public class JobSchedule {
	
	/**
	 * The list of all jobs scheduled in the future.
	 */
	@Deprecated
	private List<Job> myJobList;
	
	public static List<Job> getAllFutureJobs(String theFile) {
		List<Job> jobList = new ArrayList<>(); 
		String line;
		InputStream is = JobSchedule.class.getClassLoader().getResourceAsStream(theFile);
		BufferedReader fileReader = new BufferedReader(new InputStreamReader(is));
		try {

			while((line = fileReader.readLine()) != null) {
				Job job = Job.parseDelimitedString(line);
				if (!RulesHelp.isDateInPast(job.getFirstDate())) { // elimiates jobs from past
					jobList.add(job);
				}
			}

			fileReader.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return jobList;
	}
	
	public static void saveJobList(Collection<Job> theJobs, String theFile) {
		File file = new File(theFile);
		try {
			if (!file.exists()) {
				file.createNewFile();
			}
			
			FileWriter fileWriter = new FileWriter(file.getAbsoluteFile(), false);
			
			BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
			
			for(Job job: theJobs) {
				bufferedWriter.write(job.createDelimitedString());
				bufferedWriter.newLine();
			}
			
			bufferedWriter.close();
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}