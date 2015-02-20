/*
 * @author: Gabriel Orellana & Olin Wong
 * CS 149, Section 6
 * Homework 2 - Part 2
 */
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Random;
public class ProcessScheduling 
{
    static ArrayList<Process> listOfProcesses = new ArrayList<Process>();

    public static void main(String[] args)
    {
        /* parse line input for--> number of processes, time slice
         * @processNumber, number of processes, can be 50 or 100, technically positive int values
         * @quantaRR,time slice, can be 1 -2 quanta of time
         */
        float[] stringTo_int = new float[args.length];
        float processNumber = Float.parseFloat(args[0]);
        float quantaRR = Float.parseFloat(args[1]);
        
        //make processes
        for(int i= 0 ;i< processNumber ;i++)
        {
            float value = 0; //arrival time, geneated
            float value2 = 0; //expected total run time, generated
            float value3 = 0; //completion time
            boolean value4 = false; //flag value for first iteration
            
            /* random generator-->arrival time, expected total run time
             * @arrival time, float from 0 - 200 quanta
             * @ expected total run time, float from 0.1 - 10 quanta
             */
            Random r = new Random();
            //here, the random arrival time and completion time will be added to process
            
            Process tempProcess = new Process(value,value2,value3,value4);
            listOfProcesses.add(tempProcess);
        }
        FCFS(listOfProcesses);
        SJF(listOfProcesses);
        roundRobin(listOfProcesses, quantaRR); //takes in time slice 
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
    public static void roundRobin(ArrayList<Process> array, float timeSlice)
    {
        //convert array <start, stop, arrival>
        //keeping track of the current time
        //need to use timeSlice in conjunction with current time.
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
