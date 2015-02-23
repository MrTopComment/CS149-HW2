* @author: Gabriel Orellana & Olin Wong
  * CS 149, Section 6
  * Homework 2 - Part 2
+ * NEEDS LINE INPUT, PLEASE AFTER COMPILE, execute with input, number of processes, time slice
  */
 import java.util.ArrayList;
+import java.util.Comparator;
+import java.util.PriorityQueue;
+import java.util.Queue;
 public class ProcessScheduling 
 {
     private static int processNumber;
 @@ -26,6 +30,7 @@ public static void main(String[] args)
             float value3 = 0; //completion time
             boolean value4 = false; //flag value for first iteration
             float value5 = 0; //execution time
+            float value6 = 0; //last runtime, only for RR
             
             /* random generator-->arrival time, expected total run time
              * @arrival time, float from 0 - 200 quanta
 @@ -38,7 +43,7 @@ public static void main(String[] args)
             value = random1;
             value2 = random2;
             
-            Process tempProcess = new Process(value,value2,value3,value4,value5);
+            Process tempProcess = new Process(value,value2,value3,value4,value5, value6);
             listOfProcesses.add(tempProcess);
         }
         FCFS(listOfProcesses);
 @@ -55,7 +60,7 @@ public static void FCFS(ArrayList<Process> arrayOG)
         ArrayList<Process> tempSort = new ArrayList<Process>();
         for (int z = 0; z < arrayOG.size(); z++)
         {
-            Process process = new Process(0,0,0,false,0);
+            Process process = new Process(0,0,0,false,0, 0);
             tempSort.add(process);
             tempSort.get(z).arrivalTime = arrayOG.get(z).arrivalTime;
             tempSort.get(z).expectedTotalRunTime = arrayOG.get(z).expectedTotalRunTime;
 @@ -79,18 +84,25 @@ public static void FCFS(ArrayList<Process> arrayOG)
         ArrayList<Process> array = new ArrayList<Process>(); //array = editable queue
         for (int y = 0; y < tempSort.size(); y++)
         {
-            Process process = new Process(0,0,0,false,0);
+            Process process = new Process(0,0,0,false,0, 0);
             array.add(process);
-            array.get(y).arrivalTime = arrayOG.get(y).arrivalTime;
-            array.get(y).expectedTotalRunTime = arrayOG.get(y).expectedTotalRunTime;
-            array.get(y).completionTime = arrayOG.get(y).completionTime;
-            array.get(y).firstIteration = arrayOG.get(y).firstIteration;
-            array.get(y).executionTime = arrayOG.get(y).executionTime;
+            array.get(y).arrivalTime = tempSort.get(y).arrivalTime;
+            array.get(y).expectedTotalRunTime = tempSort.get(y).expectedTotalRunTime;
+            array.get(y).completionTime = tempSort.get(y).completionTime;
+            array.get(y).firstIteration = tempSort.get(y).firstIteration;
+            array.get(y).executionTime = tempSort.get(y).executionTime;
         }
+        //adjust time to first arrival time
+        currentTimeFCFS = currentTimeFCFS + array.get(0).arrivalTime;
         //iterate through the new array---(FCFS logic for scheduling)
         //no CPU scheduler switching necessary for FCFS  
         for (int i = 0; i < array.size(); i++)
         {
+            //gap checker
+            if (array.get(i).arrivalTime > currentTimeFCFS)
+            {
+                currentTimeFCFS = array.get(i).arrivalTime;
+            }
             //check first iteration flag-->if false-->true and set start time--> else if true - ignore
             if (array.get(i).firstIteration == false)
             {
 @@ -101,7 +113,6 @@ public static void FCFS(ArrayList<Process> arrayOG)
             //set current time to @completion time for process = current time + burst time
             currentTimeFCFS = currentTimeFCFS + array.get(i).expectedTotalRunTime;
             array.get(i).completionTime = currentTimeFCFS;
-            array.get(i).expectedTotalRunTime = 0;
             //set each burst time to 0, tempSort still holds info though
             //non-preemptive = no need to exit.
         }
 @@ -144,7 +155,7 @@ public static void SJF(ArrayList<Process> arrayOG)
         ArrayList<Process> tempSort = new ArrayList<Process>();
         for (int z = 0; z < arrayOG.size(); z++)
         {
-            Process process = new Process(0,0,0,false,0);
+            Process process = new Process(0,0,0,false,0, 0);
             tempSort.add(process);
             tempSort.get(z).arrivalTime = arrayOG.get(z).arrivalTime;
             tempSort.get(z).expectedTotalRunTime = arrayOG.get(z).expectedTotalRunTime;
 @@ -152,7 +163,7 @@ public static void SJF(ArrayList<Process> arrayOG)
             tempSort.get(z).firstIteration = arrayOG.get(z).firstIteration;
             tempSort.get(z).executionTime = arrayOG.get(z).executionTime;
         }
-        //keeping track of the current time
+        //keeping track of the current time + others
         float currentTimeSJF = 0;
         //sort array by arrival time --> tempSort = sorted queue
         for (int k = 1; k < tempSort.size(); k++)       //insertion sort
 @@ -168,50 +179,85 @@ public static void SJF(ArrayList<Process> arrayOG)
         ArrayList<Process> array = new ArrayList<Process>(); //array = editable queue
         for (int y = 0; y < tempSort.size(); y++)
         {
-            Process process = new Process(0,0,0,false,0);
+            Process process = new Process(0,0,0,false,0,0);
             array.add(process);
-            array.get(y).arrivalTime = arrayOG.get(y).arrivalTime;
-            array.get(y).expectedTotalRunTime = arrayOG.get(y).expectedTotalRunTime;
-            array.get(y).completionTime = arrayOG.get(y).completionTime;
-            array.get(y).firstIteration = arrayOG.get(y).firstIteration;
-            array.get(y).executionTime = arrayOG.get(y).executionTime;
-        }
-        //iterate through the new array---(SJF logic for scheduling)
-        //need to manage queue for SJF job switching  
-        for (int i = 0; i < array.size(); i++)
-        {
-            //check first iteration flag-->if false-->true and set start time--> else if true - ignore
-            if (array.get(i).firstIteration == false)
-            {
-                array.get(i).firstIteration = true;
-                array.get(i).executionTime = currentTimeSJF; //first iteration time = currentTime
-            }
-            //set completion time = current time(already set as completion time), set burst to zero
-            //set current time to @completion time for process = current time + burst time
-            currentTimeSJF = currentTimeSJF + array.get(i).expectedTotalRunTime;
-            array.get(i).completionTime = currentTimeSJF;
-            array.get(i).expectedTotalRunTime = 0;
-            //set each burst time to 0, tempSort still holds info though
-            //non-preemptive = no need to exit.
+            array.get(y).arrivalTime = tempSort.get(y).arrivalTime;
+            array.get(y).expectedTotalRunTime = tempSort.get(y).expectedTotalRunTime;
+            array.get(y).completionTime = tempSort.get(y).completionTime;
+            array.get(y).firstIteration = tempSort.get(y).firstIteration;
+            array.get(y).executionTime = tempSort.get(y).executionTime;
         }
         
-        //go through array for calculations and do averages [average variables] then print
         float averageTurnAroundTimeSJF = 0;
         float averageWaitingTimeSJF = 0;
         float averageResponseTimeSJF = 0;
         float throughPutSJF = 0;
-        for (int j = 0; j < array.size(); j++)
+        //iterate through the new array---(SJF logic for scheduling)
+        //SJF needs to implement a queue to handle jobs
+        //need comparator to keep queue at SJF order
+        Queue<Process> lineSJF = new PriorityQueue<>(array.size(), new Comparator<Process>() {
+          @Override
+          public int compare(Process previous, Process next)
+          {
+              if (previous.expectedTotalRunTime > next.expectedTotalRunTime)    {
+                  return 1;
+              }
+              else if (previous.expectedTotalRunTime < next.expectedTotalRunTime) {
+                  return -1;
+              }
+              else         {
+                  return 0;
+              }
+          }
+        });
+        
+        //add the first job to the queue--start exeuction/handling--keep track of last job
+        int jobLastAddedIndex = 0;
+        currentTimeSJF = currentTimeSJF + array.get(0).arrivalTime;
+        lineSJF.add(array.get(0));
+        //set states for the queue, empty and has element
+        //keep going while queue is full
+        while (!lineSJF.isEmpty()) 
         {
+            //pull the first process off the ordered queue and execute it
+            Process currentProcess = lineSJF.poll();
+            //check first iteration flag-->if false-->true and set start time--> else if true - ignore
+            if (currentProcess.firstIteration == false)
+            {
+                currentProcess.firstIteration = true;
+                currentProcess.executionTime = currentTimeSJF; //first iteration time = currentTime
+            }
+            currentProcess.completionTime = currentTimeSJF + currentProcess.expectedTotalRunTime;
+            //then update current time with the burst time amount
+            currentTimeSJF = currentTimeSJF + currentProcess.expectedTotalRunTime;
+
             //calculate times and then add them to the total
             averageTurnAroundTimeSJF = averageTurnAroundTimeSJF + //turnaround = completion - arrival
-                    ( (array.get(j).completionTime) - (array.get(j).arrivalTime) ); 
+                    ( ( currentProcess.completionTime) - (currentProcess.arrivalTime) ); 
             averageWaitingTimeSJF = averageWaitingTimeSJF + //wait = completion - OG array(burst time)
-                    ( (array.get(j).completionTime) - (tempSort.get(j).expectedTotalRunTime) -
-                     array.get(j).arrivalTime);
+                    ( (currentProcess.completionTime) - (currentProcess.expectedTotalRunTime) -
+                     currentProcess.arrivalTime);
             averageResponseTimeSJF = averageResponseTimeSJF + //reponse = execution - arrival
-                    ( (array.get(j).executionTime) - (array.get(j).arrivalTime) );
+                    ( ( currentProcess.executionTime) - (currentProcess.arrivalTime) );
             //throughput = processes / total time [here = adding up the burst time total]
-            throughPutSJF = throughPutSJF + (tempSort.get(j).expectedTotalRunTime);
+            throughPutSJF = throughPutSJF + (currentProcess.expectedTotalRunTime);
+
+            //add jobs whose arrival times have already occured to queue, while single core is being used
+            for (int k = jobLastAddedIndex + 1; k < array.size() && array.get(k).arrivalTime 
+                            <= currentTimeSJF; k++) 
+            {
+                lineSJF.add( array.get(k) );
+                jobLastAddedIndex = k;
+            }
+            //when the queue is empty, push next in line array items into it
+            if (lineSJF.isEmpty()) 
+            {
+                if ( jobLastAddedIndex + 1 < array.size() ) 
+                {
+                    lineSJF.add(array.get(jobLastAddedIndex + 1));
+                    jobLastAddedIndex++;
+                }
+            }
         }
         //divide total by size for averages
         averageTurnAroundTimeSJF = averageTurnAroundTimeSJF / processNumber;
 @@ -226,15 +272,131 @@ public static void SJF(ArrayList<Process> arrayOG)
     
     public static void roundRobin(ArrayList<Process> arrayOG, float timeSlice)
     {
-        //convert array <start, stop, arrival>
+        /* input@array <arrival time, expected total run time, completion time, 
+         *              first iteration flag, first iteration time>
+         */
+        //make a SORTED copy for nondestructive editing of main copy
+        ArrayList<Process> tempSort = new ArrayList<Process>();
+        for (int z = 0; z < arrayOG.size(); z++)
+        {
+            Process process = new Process(0,0,0,false,0,0);
+            tempSort.add(process);
+            tempSort.get(z).arrivalTime = arrayOG.get(z).arrivalTime;
+            tempSort.get(z).expectedTotalRunTime = arrayOG.get(z).expectedTotalRunTime;
+            tempSort.get(z).completionTime = arrayOG.get(z).completionTime;
+            tempSort.get(z).firstIteration = arrayOG.get(z).firstIteration;
+            tempSort.get(z).executionTime = arrayOG.get(z).executionTime;
+        }
         //keeping track of the current time
         float currentTimeRR = 0;
-        //need to use timeSlice in conjunction with current time.
-        //array [1]-->array [1] also moves to back, fk i don't know how to do it
-        //
-        //when burst time reaches 0, completion time = current time.
-        //flag for first time, if false turn true and set start time, if true ignore
+        //sort array by arrival time --> tempSort = sorted queue
+        for (int k = 1; k < tempSort.size(); k++)  //insertion sort
+        {
+            float temp = tempSort.get(k).arrivalTime;
+            int m;
+            for (m = k - 1; m >= 0 && temp < tempSort.get(m).arrivalTime; m--)
+            {
+                tempSort.get(m + 1).arrivalTime = tempSort.get(m).arrivalTime;
+                tempSort.get(m + 1).arrivalTime = temp;
+            }
+        }
+        ArrayList<Process> array = new ArrayList<Process>(); //array = editable queue
+        for (int y = 0; y < tempSort.size(); y++)
+        {
+            Process process = new Process(0,0,0,false,0,0);
+            array.add(process);
+            array.get(y).arrivalTime = tempSort.get(y).arrivalTime;
+            array.get(y).expectedTotalRunTime = tempSort.get(y).expectedTotalRunTime;
+            array.get(y).completionTime = tempSort.get(y).completionTime;
+            array.get(y).firstIteration = tempSort.get(y).firstIteration;
+            array.get(y).executionTime = tempSort.get(y).executionTime;
+        }
+        //adjust time to first arrival time
+        currentTimeRR = currentTimeRR + array.get(0).arrivalTime;
+        float averageTurnAroundTimeRR = 0;
+        float averageWaitingTimeRR = 0;
+        float averageResponseTimeRR = 0;
+        float throughPutRR = 0;
+        //since RR breaks up the burst time into chunks this is a seperate throughput calc
+        for (int t = 0; t < array.size(); t++)
+        {
+            throughPutRR = throughPutRR + array.get(t).expectedTotalRunTime;
+        }
+        //iterate through the new array---(RR logic for scheduling)
+        //CPU RR scheduler--while not empty keeps it moving, don't have to append to end anymore, just loop
+        while (!array.isEmpty()) {
+            //gap fixing, bring the process to it's time
+            if (currentTimeRR < array.get(0).arrivalTime) 
+            {
+                //get the first arrival time at first, then gap fixes
+                currentTimeRR = array.get(0).arrivalTime;
+            }
+            //running as RR array
+            for (int i = 0; i < array.size(); i++) {
+                if (array.get(i).arrivalTime <= currentTimeRR) 
+                {
+                    //first iteration loop, checks for first time run
+                    if (!array.get(i).firstIteration) 
+                    {
+                        //check first iteration flag-->if false-->true and set start time--> else if true - ignore
+                        array.get(i).firstIteration = true;
+                        //first iteration time = currentTime
+                        array.get(i).executionTime = currentTimeRR;
+                        //then calculate average waiting and response time for first iteration
+                        averageWaitingTimeRR = averageWaitingTimeRR + currentTimeRR 
+                                - array.get(i).arrivalTime;
+                        averageResponseTimeRR = averageResponseTimeRR + currentTimeRR 
+                                - array.get(i).arrivalTime;
+                        //set the current job the CPU is on aside and set current time to continue
+                        //off from that time
+                        array.get(i).lastRunTime = currentTimeRR;
+                    }
+                    //loop check after first iteration--checks runtime
+                    //case: when burst time exceeds quantum
+                    if (array.get(i).expectedTotalRunTime >= timeSlice) 
+                    {
+                        //needs to minus burst time and then set it aside to run again later
+                        array.get(i).expectedTotalRunTime = array.get(i).expectedTotalRunTime - timeSlice;
+                        //update current time
+                        currentTimeRR = currentTimeRR + timeSlice;
+                        //update average waiting time
+                        averageWaitingTimeRR = averageWaitingTimeRR +currentTimeRR - 
+                                array.get(i).lastRunTime - timeSlice;
+                        //update last run time for this job so that it can be run again
+                        array.get(i).lastRunTime = currentTimeRR;
+                    } else
+                        //loop for if the process finishes within it's quantum
+                    {
+                        //so update time with burst time, since it will be < quantum
+                        currentTimeRR = currentTimeRR + array.get(i).expectedTotalRunTime;
+                        //calculate average turnaround/waiting time--update last run time
+                        averageTurnAroundTimeRR = averageTurnAroundTimeRR + currentTimeRR
+                                - array.get(i).arrivalTime;
+                        averageWaitingTimeRR = averageWaitingTimeRR + currentTimeRR 
+                                - array.get(i).lastRunTime - array.get(i).expectedTotalRunTime;
+                        array.get(i).lastRunTime = currentTimeRR;
+                        //when the job is finished set the burst time to zero
+                        //to signify that it is finished, then it will pass to next if loop
+                        array.get(i).expectedTotalRunTime = (float) 0.00;
+                        //garbage collector- once a processes burst time reaches 0
+                        //job is done, remove it, then decrement counter
+                        array.remove(i);
+                        i--;
+                    }
+                }
+            }
+        }
+        //divide total by size for averages
+        averageTurnAroundTimeRR = averageTurnAroundTimeRR / processNumber;
+        averageWaitingTimeRR = averageWaitingTimeRR / processNumber;
+        averageResponseTimeRR = averageResponseTimeRR / processNumber;
+        throughPutRR = processNumber / throughPutRR;
+        System.out.println("Average turnaround time RR: " + averageTurnAroundTimeRR);
+        System.out.println("Average waiting time RR: " + averageWaitingTimeRR);
+        System.out.println("Average reponse time RR: " + averageResponseTimeRR);
+        System.out.println("Average throughput RR: " + throughPutRR);
     }
+    
     public static class Process
     {
         private float arrivalTime;
 @@ -242,14 +404,16 @@ public static void roundRobin(ArrayList<Process> arrayOG, float timeSlice)
         private float completionTime;
         private boolean firstIteration = false; //just in case
         private float executionTime;
+        private float lastRunTime;
         Process(float arrivalTime, float expectedTotalRunTime, float completionTime, boolean firstIteration,
-                float executionTime)
+                float executionTime, float lastRunTime)
         {
             this.arrivalTime = arrivalTime;
             this.expectedTotalRunTime = expectedTotalRunTime;
             this.completionTime = completionTime;
             this.firstIteration = firstIteration;
             this.executionTime = executionTime;
+            this.lastRunTime = lastRunTime;
         }
     }
 }
