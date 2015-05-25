package park_model;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

// Serializable assistance from http://www.tutorialspoint.com/java/java_serialization.htm

/**
 * Provides the abilities to read and write job schedules to and from files.
 * 
 * @author Julia Behnen
 * @version 5/24/2015
 */
public class JobSchedule {
	
	@SuppressWarnings("unchecked")
	static List<Job> getAllJobs(String theFile) {
		List<Job> allJobs = new ArrayList<>();
		
		try {
			FileInputStream fileIn = new FileInputStream(theFile);
			ObjectInputStream in = new ObjectInputStream(fileIn);
			allJobs = (List<Job>) in.readObject();
			in.close();
			fileIn.close();
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return allJobs;
	}
	
	public static List<Job> getAllFutureJobs(String theFile) {
		List<Job> allJobs = getAllJobs(theFile);
		List<Job> futureJobs = new ArrayList<>();
		for (Job job: allJobs) {
			if (!RulesHelp.isDateInPast(job.getFirstDate())) {
				futureJobs.add(job);
			}
		}
		return futureJobs;
	}
	
	public static void saveJobList(Collection<Job> theJobs, String theFile) {
		try {
			FileOutputStream fileOut = new FileOutputStream(theFile);
			ObjectOutputStream out = new ObjectOutputStream(fileOut);
			out.writeObject(theJobs);
			out.close();
			fileOut.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}