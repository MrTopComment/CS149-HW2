/**
 * @author: Gabriel Orellana & Olin Wong
 * CS 149, Section 6
 * Homework 2 - Part 2
 */
import java.lang.reflect.Array;
import java.util.ArrayList;
public class ProcessScheduling {
	ArrayList<Process> listOfProcesses = new ArrayList<Process>();
	
	public static void main(String[] args)
	{
		
	}
	public void FCFS(ArrayList<Process> array)
	{
		
	}
	public void SJF(ArrayList<Process> array)
	{
		
	}
	public void RoundRobin(ArrayList<Process> array)
	{
		
	}
	public class Process
	{
		private float arrivalTime;
		private float expectedTotalRunTime;
		Process(float arrivalTime, float burstTime)
		{
			this.arrivalTime = arrivalTime;
			this.expectedTotalRunTime = burstTime;
		}
	}
}
