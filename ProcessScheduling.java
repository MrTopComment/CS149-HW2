/*
 * @author: Gabriel Orellana & Olin Wong
 * CS 149, Section 6
 * Homework 2 - Part 2
 * NEEDS LINE INPUT, PLEASE AFTER COMPILE, execute with input, number of processes, time slice
 */
import java.util.ArrayList;
import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.Queue;
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
<<<<<<< HEAD
        processNumber = Integer.parseInt(args[0]);
        int quantaRR = Integer.parseInt(args[1]);
=======
        processNumber = 50;
        int quantaRR = 2;
>>>>>>> 8a28c5880f9c55de4161b916a4a0c0f3e7b76658
        
        //make processes
        for(int i= 0 ;i< processNumber ;i++)
        {
            float value = 0; //arrival time, geneated
            float value2 = 0; //expected total run time, generated
            float value3 = 0; //completion time
            boolean value4 = false; //flag value for first iteration
            float value5 = 0; //execution time
            float value6 = 0; //last runtime, only for RR
            
            /* random generator-->arrival time, expected total run time
             * @arrival time, float from 0 - 200 quanta
             * @ expected total run time, float from 0.1 - 10 quanta
             */
            float random1 = (Math.abs((int) System.nanoTime())%101); 
            random1 = (random1/100)*200;
            float random2 = (Math.abs((int) System.nanoTime())%101); 
            random2 = (random2/100)*10;
            value = random1;
            value2 = random2;
            
            Process tempProcess = new Process(value,value2,value3,value4,value5, value6);
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
        //make a SORTED copy for nondestructive editing of main copy
        ArrayList<Process> tempSort = new ArrayList<Process>();
        for (int z = 0; z < arrayOG.size(); z++)
        {
            Process process = new Process(0,0,0,false,0, 0);
            tempSort.add(process);
            tempSort.get(z).arrivalTime = arrayOG.get(z).arrivalTime;
            tempSort.get(z).expectedTotalRunTime = arrayOG.get(z).expectedTotalRunTime;
            tempSort.get(z).completionTime = arrayOG.get(z).completionTime;
            tempSort.get(z).firstIteration = arrayOG.get(z).firstIteration;
            tempSort.get(z).executionTime = arrayOG.get(z).executionTime;
        }
        //keeping track of the current time
        float currentTimeFCFS = 0;
        //sort array by arrival time --> tempSort = sorted queue
        for (int k = 1; k < tempSort.size(); k++)  //insertion sort
        {
            float temp = tempSort.get(k).arrivalTime;
            int m;
            for (m = k - 1; m >= 0 && temp < tempSort.get(m).arrivalTime; m--)
            {
                tempSort.get(m + 1).arrivalTime = tempSort.get(m).arrivalTime;
                tempSort.get(m + 1).arrivalTime = temp;
            }
        }
        ArrayList<Process> array = new ArrayList<Process>(); //array = editable queue
        for (int y = 0; y < tempSort.size(); y++)
        {
            Process process = new Process(0,0,0,false,0, 0);
            array.add(process);
            array.get(y).arrivalTime = tempSort.get(y).arrivalTime;
            array.get(y).expectedTotalRunTime = tempSort.get(y).expectedTotalRunTime;
            array.get(y).completionTime = tempSort.get(y).completionTime;
            array.get(y).firstIteration = tempSort.get(y).firstIteration;
            array.get(y).executionTime = tempSort.get(y).executionTime;
        }
        //adjust time to first arrival time
        currentTimeFCFS = currentTimeFCFS + array.get(0).arrivalTime;
        //iterate through the new array---(FCFS logic for scheduling)
        //no CPU scheduler switching necessary for FCFS  
        for (int i = 0; i < array.size(); i++)
        {
            //gap checker
            if (array.get(i).arrivalTime > currentTimeFCFS)
            {
                currentTimeFCFS = array.get(i).arrivalTime;
            }
            //check first iteration flag-->if false-->true and set start time--> else if true - ignore
            if (array.get(i).firstIteration == false)
            {
                array.get(i).firstIteration = true;
                array.get(i).executionTime = currentTimeFCFS; //first iteration time = currentTime
            }
            //set completion time = current time(already set as completion time), set burst to zero
            //set current time to @completion time for process = current time + burst time
            currentTimeFCFS = currentTimeFCFS + array.get(i).expectedTotalRunTime;
            array.get(i).completionTime = currentTimeFCFS;
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
            //calculate times and then add them to the total
            averageTurnAroundTimeFCFS = averageTurnAroundTimeFCFS + //turnaround = completion - arrival
                    ( (array.get(j).completionTime) - (array.get(j).arrivalTime) ); 
            averageWaitingTimeFCFS = averageWaitingTimeFCFS + //wait = completion - OG array(burst time)
                    ( (array.get(j).completionTime) - (tempSort.get(j).expectedTotalRunTime) -
                     array.get(j).arrivalTime);
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
        /* input@array <arrival time, expected total run time, completion time, 
         *              first iteration flag, first iteration time>
         */
        //make a SORTED copy for nondestructive editing of main copy
        ArrayList<Process> tempSort = new ArrayList<Process>();
        for (int z = 0; z < arrayOG.size(); z++)
        {
            Process process = new Process(0,0,0,false,0, 0);
            tempSort.add(process);
            tempSort.get(z).arrivalTime = arrayOG.get(z).arrivalTime;
            tempSort.get(z).expectedTotalRunTime = arrayOG.get(z).expectedTotalRunTime;
            tempSort.get(z).completionTime = arrayOG.get(z).completionTime;
            tempSort.get(z).firstIteration = arrayOG.get(z).firstIteration;
            tempSort.get(z).executionTime = arrayOG.get(z).executionTime;
        }
        //keeping track of the current time + others
        float currentTimeSJF = 0;
        //sort array by arrival time --> tempSort = sorted queue
        for (int k = 1; k < tempSort.size(); k++)       //insertion sort
        {
            float temp = tempSort.get(k).arrivalTime;
            int m;
            for (m = k - 1; m >= 0 && temp < tempSort.get(m).arrivalTime; m--)
            {
                tempSort.get(m + 1).arrivalTime = tempSort.get(m).arrivalTime;
                tempSort.get(m + 1).arrivalTime = temp;
            }
        }
<<<<<<< HEAD
        ArrayList<Process> array = new ArrayList<Process>(); //array = editable queue
        for (int y = 0; y < tempSort.size(); y++)
        {
            Process process = new Process(0,0,0,false,0,0);
            array.add(process);
            array.get(y).arrivalTime = tempSort.get(y).arrivalTime;
            array.get(y).expectedTotalRunTime = tempSort.get(y).expectedTotalRunTime;
            array.get(y).completionTime = tempSort.get(y).completionTime;
            array.get(y).firstIteration = tempSort.get(y).firstIteration;
            array.get(y).executionTime = tempSort.get(y).executionTime;
        }
        
        float averageTurnAroundTimeSJF = 0;
        float averageWaitingTimeSJF = 0;
        float averageResponseTimeSJF = 0;
        float throughPutSJF = 0;
        //iterate through the new array---(SJF logic for scheduling)
        //SJF needs to implement a queue to handle jobs
        //need comparator to keep queue at SJF order
        Queue<Process> lineSJF = new PriorityQueue<>(array.size(), new Comparator<Process>() {
          @Override
          public int compare(Process previous, Process next)
          {
              if (previous.expectedTotalRunTime > next.expectedTotalRunTime)    {
                  return 1;
              }
              else if (previous.expectedTotalRunTime < next.expectedTotalRunTime) {
                  return -1;
              }
              else         {
                  return 0;
              }
          }
        });
        
        //add the first job to the queue--start exeuction/handling--keep track of last job
        int jobLastAddedIndex = 0;
        currentTimeSJF = currentTimeSJF + array.get(0).arrivalTime;
        lineSJF.add(array.get(0));
        //set states for the queue, empty and has element
        //keep going while queue is full
        while (!lineSJF.isEmpty()) 
        {
            //pull the first process off the ordered queue and execute it
            Process currentProcess = lineSJF.poll();
            //check first iteration flag-->if false-->true and set start time--> else if true - ignore
            if (currentProcess.firstIteration == false)
            {
                currentProcess.firstIteration = true;
                currentProcess.executionTime = currentTimeSJF; //first iteration time = currentTime
            }
            currentProcess.completionTime = currentTimeSJF + currentProcess.expectedTotalRunTime;
            //then update current time with the burst time amount
            currentTimeSJF = currentTimeSJF + currentProcess.expectedTotalRunTime;

            //calculate times and then add them to the total
            averageTurnAroundTimeSJF = averageTurnAroundTimeSJF + //turnaround = completion - arrival
                    ( ( currentProcess.completionTime) - (currentProcess.arrivalTime) ); 
            averageWaitingTimeSJF = averageWaitingTimeSJF + //wait = completion - OG array(burst time)
                    ( (currentProcess.completionTime) - (currentProcess.expectedTotalRunTime) -
                     currentProcess.arrivalTime);
            averageResponseTimeSJF = averageResponseTimeSJF + //reponse = execution - arrival
                    ( ( currentProcess.executionTime) - (currentProcess.arrivalTime) );
            //throughput = processes / total time [here = adding up the burst time total]
            throughPutSJF = throughPutSJF + (currentProcess.expectedTotalRunTime);

            //add jobs whose arrival times have already occured to queue, while single core is being used
            for (int k = jobLastAddedIndex + 1; k < array.size() && array.get(k).arrivalTime 
                            <= currentTimeSJF; k++) 
            {
                lineSJF.add( array.get(k) );
                jobLastAddedIndex = k;
            }
            //when the queue is empty, push next in line array items into it
            if (lineSJF.isEmpty()) 
            {
                if ( jobLastAddedIndex + 1 < array.size() ) 
                {
                    lineSJF.add(array.get(jobLastAddedIndex + 1));
                    jobLastAddedIndex++;
                }
=======
        ArrayList<Process> array = new ArrayList<Process>(tempSort);
        float currentTimeSJF = 0;
        float averageTurnAroundTimeSJF = 0;
        float averageWaitingTimeSJF = 0;
        float averageResponseTimeSJF = 0;
        float throughPutSJF = 0;
        
        for(int i = 0; i < array.size(); i++)
        {
        	if(array.get(i).firstIteration == false)
        	{
        		array.get(i).firstIteration = true;
        		array.get(i).executionTime = currentTimeSJF;
        	}
        	//set current time to @completion time for process = current time + burst time
            //set completion time = current time(already set as completion time), set burst to zero
            currentTimeSJF = currentTimeSJF + array.get(i).expectedTotalRunTime;
            array.get(i).completionTime = currentTimeSJF + array.get(i).expectedTotalRunTime;
            array.get(i).expectedTotalRunTime = 0;
            //set each burst time to 0, tempSort still holds info though
            //non-preemptive = no need to exit.
            
            
            //***SJF Algorithm***
            for(int j = i+1; j < array.size(); j++)
            {
            	if(i == 0) //calculations for first process
            	{
            		averageTurnAroundTimeSJF = averageTurnAroundTimeSJF + //turnaround = completion - arrival
                            ( (array.get(0).completionTime) - (array.get(0).arrivalTime) ); 
                    averageWaitingTimeSJF = averageWaitingTimeSJF + //wait = completion - OG array(burst time)
                            ( (array.get(0).completionTime) - (tempSort.get(0).expectedTotalRunTime) );
                    averageResponseTimeSJF = averageResponseTimeSJF + //reponse = execution - arrival
                            ( (array.get(0).executionTime) - (array.get(0).arrivalTime) );
                    //throughput = processes / total time [here = adding up the burst time total]
                    throughPutSJF = throughPutSJF + (tempSort.get(0).expectedTotalRunTime);
            	}
            	if(array.get(j).arrivalTime <= array.get(i).completionTime && array.get(j).firstIteration == false) //Rest of the processes
            	{
            		averageTurnAroundTimeSJF = averageTurnAroundTimeSJF + //turnaround = completion - arrival
                            ( (array.get(j).completionTime) - (array.get(j).arrivalTime) ); 
                    averageWaitingTimeSJF = averageWaitingTimeSJF + //wait = completion - OG array(burst time)
                            ( (array.get(j).completionTime) - (tempSort.get(j).expectedTotalRunTime) );
                    averageResponseTimeSJF = averageResponseTimeSJF + //reponse = execution - arrival
                            ( (array.get(j).executionTime) - (array.get(j).arrivalTime) );
                    //throughput = processes / total time [here = adding up the burst time total]
                    throughPutSJF = throughPutSJF + (tempSort.get(j).expectedTotalRunTime);
                    array.get(j).firstIteration = true;
            	}
>>>>>>> 8a28c5880f9c55de4161b916a4a0c0f3e7b76658
            }
        }
        //divide total by size for averages
        averageTurnAroundTimeSJF = averageTurnAroundTimeSJF / processNumber;
        averageWaitingTimeSJF = averageWaitingTimeSJF / processNumber;
        averageResponseTimeSJF = averageResponseTimeSJF / processNumber;
        throughPutSJF = processNumber / throughPutSJF;
<<<<<<< HEAD
=======
        
        System.out.println("");
>>>>>>> 8a28c5880f9c55de4161b916a4a0c0f3e7b76658
        System.out.println("Average turnaround time SJF: " + averageTurnAroundTimeSJF);
        System.out.println("Average waiting time SJF: " + averageWaitingTimeSJF);
        System.out.println("Average reponse time SJF: " + averageResponseTimeSJF);
        System.out.println("Average throughput SJF: " + throughPutSJF);
    }
    
    public static void roundRobin(ArrayList<Process> arrayOG, float timeSlice)
    {
        /* input@array <arrival time, expected total run time, completion time, 
         *              first iteration flag, first iteration time>
         */
        //make a SORTED copy for nondestructive editing of main copy
        ArrayList<Process> tempSort = new ArrayList<Process>();
        for (int z = 0; z < arrayOG.size(); z++)
        {
            Process process = new Process(0,0,0,false,0,0);
            tempSort.add(process);
            tempSort.get(z).arrivalTime = arrayOG.get(z).arrivalTime;
            tempSort.get(z).expectedTotalRunTime = arrayOG.get(z).expectedTotalRunTime;
            tempSort.get(z).completionTime = arrayOG.get(z).completionTime;
            tempSort.get(z).firstIteration = arrayOG.get(z).firstIteration;
            tempSort.get(z).executionTime = arrayOG.get(z).executionTime;
        }
        //keeping track of the current time
        float currentTimeRR = 0;
        //sort array by arrival time --> tempSort = sorted queue
        for (int k = 1; k < tempSort.size(); k++)  //insertion sort
        {
            float temp = tempSort.get(k).arrivalTime;
            int m;
            for (m = k - 1; m >= 0 && temp < tempSort.get(m).arrivalTime; m--)
            {
                tempSort.get(m + 1).arrivalTime = tempSort.get(m).arrivalTime;
                tempSort.get(m + 1).arrivalTime = temp;
            }
        }
        ArrayList<Process> array = new ArrayList<Process>(); //array = editable queue
        for (int y = 0; y < tempSort.size(); y++)
        {
            Process process = new Process(0,0,0,false,0,0);
            array.add(process);
            array.get(y).arrivalTime = tempSort.get(y).arrivalTime;
            array.get(y).expectedTotalRunTime = tempSort.get(y).expectedTotalRunTime;
            array.get(y).completionTime = tempSort.get(y).completionTime;
            array.get(y).firstIteration = tempSort.get(y).firstIteration;
            array.get(y).executionTime = tempSort.get(y).executionTime;
        }
        //adjust time to first arrival time
        currentTimeRR = currentTimeRR + array.get(0).arrivalTime;
        float averageTurnAroundTimeRR = 0;
        float averageWaitingTimeRR = 0;
        float averageResponseTimeRR = 0;
        float throughPutRR = 0;
        //since RR breaks up the burst time into chunks this is a seperate throughput calc
        for (int t = 0; t < array.size(); t++)
        {
            throughPutRR = throughPutRR + array.get(t).expectedTotalRunTime;
        }
        //iterate through the new array---(RR logic for scheduling)
        //CPU RR scheduler--while not empty keeps it moving, don't have to append to end anymore, just loop
        while (!array.isEmpty()) {
            //gap fixing, bring the process to it's time
            if (currentTimeRR < array.get(0).arrivalTime) 
            {
                //get the first arrival time at first, then gap fixes
                currentTimeRR = array.get(0).arrivalTime;
            }
            //running as RR array
            for (int i = 0; i < array.size(); i++) {
                if (array.get(i).arrivalTime <= currentTimeRR) 
                {
                    //first iteration loop, checks for first time run
                    if (!array.get(i).firstIteration) 
                    {
                        //check first iteration flag-->if false-->true and set start time--> else if true - ignore
                        array.get(i).firstIteration = true;
                        //first iteration time = currentTime
                        array.get(i).executionTime = currentTimeRR;
                        //then calculate average waiting and response time for first iteration
                        averageWaitingTimeRR = averageWaitingTimeRR + currentTimeRR 
                                - array.get(i).arrivalTime;
                        averageResponseTimeRR = averageResponseTimeRR + currentTimeRR 
                                - array.get(i).arrivalTime;
                        //set the current job the CPU is on aside and set current time to continue
                        //off from that time
                        array.get(i).lastRunTime = currentTimeRR;
                    }
                    //loop check after first iteration--checks runtime
                    //case: when burst time exceeds quantum
                    if (array.get(i).expectedTotalRunTime >= timeSlice) 
                    {
                        //needs to minus burst time and then set it aside to run again later
                        array.get(i).expectedTotalRunTime = array.get(i).expectedTotalRunTime - timeSlice;
                        //update current time
                        currentTimeRR = currentTimeRR + timeSlice;
                        //update average waiting time
                        averageWaitingTimeRR = averageWaitingTimeRR +currentTimeRR - 
                                array.get(i).lastRunTime - timeSlice;
                        //update last run time for this job so that it can be run again
                        array.get(i).lastRunTime = currentTimeRR;
                    } else
                        //loop for if the process finishes within it's quantum
                    {
                        //so update time with burst time, since it will be < quantum
                        currentTimeRR = currentTimeRR + array.get(i).expectedTotalRunTime;
                        //calculate average turnaround/waiting time--update last run time
                        averageTurnAroundTimeRR = averageTurnAroundTimeRR + currentTimeRR
                                - array.get(i).arrivalTime;
                        averageWaitingTimeRR = averageWaitingTimeRR + currentTimeRR 
                                - array.get(i).lastRunTime - array.get(i).expectedTotalRunTime;
                        array.get(i).lastRunTime = currentTimeRR;
                        //when the job is finished set the burst time to zero
                        //to signify that it is finished, then it will pass to next if loop
                        array.get(i).expectedTotalRunTime = (float) 0.00;
                        //garbage collector- once a processes burst time reaches 0
                        //job is done, remove it, then decrement counter
                        array.remove(i);
                        i--;
                    }
                }
            }
        }
        //divide total by size for averages
        averageTurnAroundTimeRR = averageTurnAroundTimeRR / processNumber;
        averageWaitingTimeRR = averageWaitingTimeRR / processNumber;
        averageResponseTimeRR = averageResponseTimeRR / processNumber;
        throughPutRR = processNumber / throughPutRR;
        System.out.println("Average turnaround time RR: " + averageTurnAroundTimeRR);
        System.out.println("Average waiting time RR: " + averageWaitingTimeRR);
        System.out.println("Average reponse time RR: " + averageResponseTimeRR);
        System.out.println("Average throughput RR: " + throughPutRR);
    }
    
    public static class Process
    {
        private float arrivalTime;
        private float expectedTotalRunTime;
        private float completionTime;
        private boolean firstIteration = false; //just in case
        private float executionTime;
        private float lastRunTime;
        Process(float arrivalTime, float expectedTotalRunTime, float completionTime, boolean firstIteration,
                float executionTime, float lastRunTime)
        {
            this.arrivalTime = arrivalTime;
            this.expectedTotalRunTime = expectedTotalRunTime;
            this.completionTime = completionTime;
            this.firstIteration = firstIteration;
            this.executionTime = executionTime;
            this.lastRunTime = lastRunTime;
        }
    }
}
