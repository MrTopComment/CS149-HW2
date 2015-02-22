/*
 * @author: Gabriel Orellana & Olin Wong
 * CS 149, Section 6
 * Homework 2 - Part 2
 */
import java.util.ArrayList;
public class ProcessScheduling 
{
    private static int processNumber;
    private static ArrayList<Process> listOfProcesses = new ArrayList<Process>();

    public static void main(String[] args)
    {
        /* parse line input for--> number of processes, time slice
         * @processNumber, number of processes, can be 50 or 100, technically positive int values
         * @quantaRR,time slice, can be 1 -2 quanta of time
         */
        processNumber = Integer.parseInt(args[0]);
        int quantaRR = Integer.parseInt(args[1]);
        
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
             * one instance of each to map to each process
             */
            float random1 = (Math.abs((int) System.nanoTime())%101); 
            random1 = (random1/100)*200;
            float random2 = (Math.abs((int) System.nanoTime())%101); 
            random2 = (random2/100)*10;
            value = random1;
            value2 = random2;
            
            Process tempProcess = new Process(value,value2,value3,value4,value5);
            listOfProcesses.add(tempProcess);
        }
        FCFS(listOfProcesses);
        SJF(listOfProcesses);
        roundRobin(listOfProcesses, quantaRR); //takes in time slice 
    }

    public static void FCFS(ArrayList<Process> arrayOG)
    {
        /* input@array <arrival time, expected total run time, completion time, 
         *              first iteration flag, first iteration time>
         */
        //make a SORTED copy for nondestructive editing
        ArrayList<Process> tempSort = new ArrayList<Process>(arrayOG);
        //keeping track of the current time
        float currentTimeFCFS = 0;
        //sort array by arrival time --> tempSort = sorted queue
        for (int k = 1; k < tempSort.size(); k++)
        {
            //insertion sort
            float temp = tempSort.get(k).arrivalTime;
            int m;
            for (m = k - 1; m >= 0 && temp < tempSort.get(m).arrivalTime; m--)
            {
                tempSort.get(m + 1).arrivalTime = tempSort.get(m).arrivalTime;
                tempSort.get(m + 1).arrivalTime = temp;
            }
        }
        ArrayList<Process> array = new ArrayList<Process>(tempSort); //array = editable queue
        //iterate through the new array---(FCFS logic for scheduling)
        //no CPU scheduler switching necessary for FCFS  
        for (int i = 0; i < array.size(); i++)
        {
            //check first iteration flag-->if false-->true and set start time--> else if true - ignore
            if (array.get(i).firstIteration == false)
            {
                array.get(i).firstIteration = true;
                array.get(i).executionTime = currentTimeFCFS; //first iteration time = currentTime
            }
            //set current time to @completion time for process = current time + burst time
            //set completion time = current time(already set as completion time), set burst to zero
            currentTimeFCFS = currentTimeFCFS + array.get(i).expectedTotalRunTime;
            array.get(i).completionTime = currentTimeFCFS + array.get(i).expectedTotalRunTime;
            array.get(i).expectedTotalRunTime = 0;
            //set each burst time to 0, tempSort still holds info though
            //non-preemptive = no need to exit.
        }
        
        //go through array for calculations and do averages [average variables] then print
        float averageTurnAroundTimeFCFS = 0;
        float averageWaitingTimeFCFS = 0;
        float averageResponseTimeFCFS = 0;
        float throughPutFCFS = 0;
        for (int j = 0; j < array.size(); j++)
        {
            System.out.println(tempSort.get(j).expectedTotalRunTime);
            //calculate times and then add them to the total
            averageTurnAroundTimeFCFS = averageTurnAroundTimeFCFS + //turnaround = completion - arrival
                    ( (array.get(j).completionTime) - (array.get(j).arrivalTime) ); 
            averageWaitingTimeFCFS = averageWaitingTimeFCFS + //wait = completion - OG array(burst time)
                    ( (array.get(j).completionTime) - (tempSort.get(j).expectedTotalRunTime) );
            averageResponseTimeFCFS = averageResponseTimeFCFS + //reponse = execution - arrival
                    ( (array.get(j).executionTime) - (array.get(j).arrivalTime) );
            //throughput = processes / total time [here = adding up the burst time total]
            throughPutFCFS = throughPutFCFS + (tempSort.get(j).expectedTotalRunTime);
        }
        //divide total by size for averages
        averageTurnAroundTimeFCFS = averageTurnAroundTimeFCFS / processNumber;
        averageWaitingTimeFCFS = averageWaitingTimeFCFS / processNumber;
        averageResponseTimeFCFS = averageResponseTimeFCFS / processNumber;
        throughPutFCFS = processNumber / throughPutFCFS;
        System.out.println("Average turnaround time FCFS: " + averageTurnAroundTimeFCFS);
        System.out.println("Average waiting time FCFS: " + averageWaitingTimeFCFS);
        System.out.println("Average reponse time FCFS: " + averageResponseTimeFCFS);
        System.out.println("Average throughput FCFS: " + throughPutFCFS);
    }
    
    public static void SJF(ArrayList<Process> arrayOG)
    {
        
    }
    
    public static void roundRobin(ArrayList<Process> arrayOG, float timeSlice)
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
