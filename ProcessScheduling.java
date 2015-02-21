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
            float value5 = 0; //execution time
            
            /* random generator-->arrival time, expected total run time
             * @arrival time, float from 0 - 200 quanta
             * @ expected total run time, float from 0.1 - 10 quanta
             */
            Random r = new Random();
            //have an equally random chance to choose between values 0-200
            //have an equally random chance to choose between values 0.1 - 10
            //increments of 0.1
            //here, the random arrival time and completion time will be added to process
            
            Process tempProcess = new Process(value,value2,value3,value4, value5);
            listOfProcesses.add(tempProcess);
        }
        FCFS(listOfProcesses);
        SJF(listOfProcesses);
        roundRobin(listOfProcesses, quantaRR); //takes in time slice 
    }

    public static void FCFS(ArrayList<Process> array)
    {
        /* input@array <arrival time, expected total run time, completion time, 
         *              first iteration flag, first iteration time>
         */
        //keeping track of the current time
        float currentTimeFCFS = 0;
        //iterate through the new array---use currentTime as the time
        for (int i = 0; i < array.size() ; i++)
        {
            //check first iteration flag-->if false-->true and set start time--> else if true - ignore
            if (array.get(i).firstIteration == false)
            {
                array.get(i).firstIteration = true;
                array.get(i).executionTime = currentTimeFCFS; //first iteration time = currentTime
            }
            //completion time = current time + burst time -->set burst to zero (FCFS logic)
            array.get(i).completionTime = currentTimeFCFS + array.get(i).expectedTotalRunTime;
            array.get(i).expectedTotalRunTime = 0;
            //when to exit-->if last value has burst time zero [for FCFS, exits when array is traversed]
        }
        
        //go through array for calculations and do averages [average variables] then print
        float averageTurnAroundTimeFCFS = 0;
        float averageWaitingTimeFCFS = 0;
        float averageResponseTimeFCFS = 0;
        for (int j = 0; j < array.size(); j++)
        {
            //calculate times and then add them to the total
            averageTurnAroundTimeFCFS = averageTurnAroundTimeFCFS + 
                    ( (array.get(j).completionTime) - (array.get(j).arrivalTime) ); 
            averageWaitingTimeFCFS = averageWaitingTimeFCFS + 
                    ( () - () );
            averageResponseTimeFCFS = averageResponseTimeFCFS + 
                    ( (array.get(j).executionTime) - (array.get(j).arrivalTime) );
        }
        //divide total by size for averages
        averageTurnAroundTimeFCFS = averageTurnAroundTimeFCFS / array.size();
        averageWaitingTimeFCFS = averageWaitingTimeFCFS / array.size();
        averageResponseTimeFCFS = averageResponseTimeFCFS / array.size();
        System.out.println(averageTurnAroundTimeFCFS);
        System.out.println(averageWaitingTimeFCFS);
        System.out.println(averageResponseTimeFCFS);
    }
    
    public static void SJF(ArrayList<Process> array)
    {
        /* input@array <arrival time, expected total run time, completion time, 
         *              first iteration flag, first iteration time>
         */
        //keeping track of the current time
        float currentTimeSJF = 0;
        //iterate through the new array---use currentTime as the time
        for (int i = 0; i < array.size() ; i++)
        {
            //check first iteration flag-->if false-->true and set start time--> else if true - ignore
            if (array.get(i).firstIteration == false)
            {
                array.get(i).firstIteration = true;
                array.get(i).executionTime = currentTimeSJF; //first iteration time = currentTime
            }
            //completion time = current time + burst time -->set burst to zero (SJF logic)
            array.get(i).completionTime = currentTimeSJF + array.get(i).expectedTotalRunTime;
            array.get(i).expectedTotalRunTime = 0;
            //need to do ordering logic 
            //when to exit-->if last value has burst time zero [for SJF, ]
        }
        
        //go through array for calculations and do averages [average variables] then print
        float averageTurnAroundTimeSJF = 0;
        float averageWaitingTimeSJF = 0;
        float averageResponseTimeSJF = 0;
        for (int j = 0; j < array.size(); j++)
        {
            //calculate times and then add them to the total
            averageTurnAroundTimeSJF = averageTurnAroundTimeSJF + 
                    ( (array.get(j).completionTime) - (array.get(j).arrivalTime) ); 
            averageWaitingTimeSJF = averageWaitingTimeSJF + 
                    ( () - () );
            averageResponseTimeSJF = averageResponseTimeSJF + 
                    ( (array.get(j).executionTime) - (array.get(j).arrivalTime) );
        }
        //divide total by size for averages
        averageTurnAroundTimeSJF = averageTurnAroundTimeSJF / array.size();
        averageWaitingTimeSJF = averageWaitingTimeSJF / array.size();
        averageResponseTimeSJF = averageResponseTimeSJF / array.size();
        System.out.println(averageTurnAroundTimeSJF);
        System.out.println(averageWaitingTimeSJF);
        System.out.println(averageResponseTimeSJF);
    }
    
    public static void roundRobin(ArrayList<Process> array, float timeSlice)
    {
        //convert array <start, stop, arrival>
        //keeping track of the current time
        float currentTimeRR = 0;
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
        private float executionTime;
        Process(float arrivalTime, float expectedTotalRunTime, float completionTime, boolean firstIteration,
                float executionTime)
        {
            this.arrivalTime = arrivalTime;
            this.expectedTotalRunTime = expectedTotalRunTime;
            this.completionTime = completionTime;
            this.firstIteration = firstIteration;
            this.executionTime = executionTime;
        }
    }
}
