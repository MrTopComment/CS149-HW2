/**
 * @author: Gabriel Orellana & Olin Wong
 * CS 149, Section 6
 * Homework 2 - Part 2
 */
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Random;
public class ProcessScheduling {
	static ArrayList<Process> listOfProcesses = new ArrayList<Process>();
	Random r = new Random();
	
	public static void main(String[] args)
	{
		//parse command line = get # of processes/quantum = variable
		int processNumber = 0;
		for(int i= 0 ;i< processNumber ;i++)
		{
			float value = 0; //arrival time
			float value2 = 0; //expected total run time
			float value3 = 0; //completion time
			boolean value4 = false; //flag value for first iteration
			//randomgenerator = 2 sets of numbers
			
			Process tempProcess = new Process(value,value2,value3, value4);
			listOfProcesses.add(tempProcess);
		}
		FCFS(listOfProcesses);
		SJF(listOfProcesses);
		roundRobin(listOfProcesses);
	}
	
	public static void FCFS(ArrayList<Process> array)
	{
		//convert array <start, stop, arrival>
		//keeping track of the current time
		//array [1]-->array [1]
		//when burst time reaches 0, completion time = current time.
		//flag for first time, if false turn true and set start time, if true ignore
		
	}
	public static void SJF(ArrayList<Process> array)
	{
		//convert array <start, stop, arrival>
		//keeping track of the current time
		//array [1]-->array [1]
		//alogirthm for SJF-->1 and then 
		//when burst time reaches 0, completion time = current time.
		//flag for first time, if false turn true and set start time, if true ignore
	}
	public static void roundRobin(ArrayList<Process> array)
	{
		//convert array <start, stop, arrival>
		//keeping track of the current time
		//array [1]-->array [1] also moves to back, fk i don't know how to do it
		//
		//when burst time reaches 0, completion time = current time.
		//flag for first time, if false turn true and set start time, if true ignore
	}
	public static class Process
	{
		private float arrivalTime;
		private float expectedTotalRunTime;
		private float completionTime;
		private boolean firstIteration = false; //just in case
		Process(float arrivalTime, float expectedTotalRunTime, float completionTime, boolean firstIteration)
		{
			this.arrivalTime = arrivalTime;
			this.expectedTotalRunTime = expectedTotalRunTime;
			this.completionTime = completionTime;
			this.firstIteration = firstIteration;
		}
		
	}
}
