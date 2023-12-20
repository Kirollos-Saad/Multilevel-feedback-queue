package com.mycompany.multilevel.feedback.queue;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Random;

class Process {
    int processId;
    int initialBurstTime;
    int RemainingBurstTime;
    int finishedQueue = 0; // The queue number that the process will finish at it
}

public class MultiLevelFeedbackQueue {

    // Inserting processes into the initial waiting queue
    public static void processInsertion(Queue<Process> q, ArrayList<ArrayList<String>> movementLog) {
        Random rand = new Random();
        for (int i = 0; i < 100; i++) {
            Process p = new Process();
            p.processId = i + 1;
            p.initialBurstTime = rand.nextInt(100) + 1; // Random burst time for each process ranges from 1 to 100 ms
            p.RemainingBurstTime = p.initialBurstTime;
            q.add(p); // At first, each process is inserted in the first waiting queue
            ArrayList<String> v = new ArrayList<>();
            movementLog.add(v);
        }
    }

    public static void main(String[] args) {
        ArrayList<Process> finishedProcesses = new ArrayList<>();
        ArrayList<ArrayList<String>> movementLog = new ArrayList<>();
        Queue<Process> queue1 = new LinkedList<>();
        Queue<Process> queue2 = new LinkedList<>();
        Queue<Process> queue3 = new LinkedList<>();
        Queue<Process> waitingQueue1 = new LinkedList<>(); // Waiting queue for processes that will enter queue 1
        Queue<Process> waitingQueue2 = new LinkedList<>(); // Waiting queue for processes that will enter queue 2
        Queue<Process> waitingQueue3 = new LinkedList<>(); // Waiting queue for processes that will enter queue 3
        int queue1MaxSize = 10; // This queue will use Round Robin algorithm
        int queue2MaxSize = 20; // This queue will also use Round Robin algorithm
        int queue3MaxSize = 30; // This queue will use First Come First Served algorithm
        int queue1TimeQuantum = 8;
        int queue2TimeQuantum = 16;
        int remainingQueue1TimeQuantum = queue1TimeQuantum;
        int remainingQueue2TimeQuantum = queue2TimeQuantum;
        String message;
        processInsertion(waitingQueue1, movementLog); // Creating 100 processes and storing them initially in the first waiting queue

        for (int i = 0; i < queue1MaxSize; i++) { // Popping 10 processes from the first waiting queue and pushes them in queue 1
            Process p = waitingQueue1.poll();
            queue1.add(p);
        }

        System.out.println("Processes remaining time and movement details:");
        System.out.println("-------------------------------------------");

        // The following loop works as long as there is a process left in any of the three queues or waiting queues
        while (!queue1.isEmpty() || !queue2.isEmpty() || !queue3.isEmpty() || !waitingQueue1.isEmpty() || !waitingQueue2.isEmpty() || !waitingQueue3.isEmpty()) {
            if (!queue1.isEmpty() || !waitingQueue1.isEmpty()) { // This section handles Processes for queue 1
                for (int i = 0; i < 5 && !queue1.isEmpty(); i++) { 
                    Process p = queue1.peek();
                    p.RemainingBurstTime -= 1;
                    remainingQueue1TimeQuantum -= 1;

                    if (remainingQueue1TimeQuantum == 0) { // Process either finished or headed to queue 2
                        queue1.poll();
                        remainingQueue1TimeQuantum = queue1TimeQuantum;

                        if (p.RemainingBurstTime <= 0) { 
                            message = " process " + p.processId + " from queue 1 finished at queue 1";
                            movementLog.get(p.processId - 1).add(message);
                            System.out.println(message);

                            p.finishedQueue = 1;
                            finishedProcesses.add(p);
                        } else { // Process is inserted into the waiting 2
                            message = " process " + p.processId + " from queue 1 to waitingQueue 2";
                            movementLog.get(p.processId - 1).add(message);
                            System.out.println(message);

                            waitingQueue2.add(p);
                        }
                    }
                }

               
                if (queue1.size() < queue1MaxSize && !waitingQueue1.isEmpty()) {
                    while (queue1.size() < queue1MaxSize && !waitingQueue1.isEmpty()) {
                        Process newP = waitingQueue1.poll();
                        queue1.add(newP);

                        message = " process " + newP.processId + " from waitingQueue 1 to queue 1";
                        movementLog.get(newP.processId - 1).add(message);
                        System.out.println(message);
                    }
                }
            }

            if (!queue2.isEmpty() || !waitingQueue2.isEmpty()) { // This section handles Processes for queue 2
                for (int i = 0; i < 3 && !queue2.isEmpty(); i++) { 
                    Process p = queue2.peek();
                    p.RemainingBurstTime -= 1;
                    remainingQueue2TimeQuantum -= 1;

                    if (remainingQueue2TimeQuantum == 0) { // Process either finished or headed  to queue 1 or  headed to queue 3
                        queue2.poll();
                        remainingQueue2TimeQuantum = queue2TimeQuantum;

                        if (p.RemainingBurstTime <= 0) { 
                            message = " process " + p.processId + " from queue 2 finished at queue 2";
                            movementLog.get(p.processId - 1).add(message);
                            System.out.println(message);

                            p.finishedQueue = 2;
                            finishedProcesses.add(p);
                        } else { // Process is inserted randomly into waiting queue1 or  waiting queue2
                            Random random = new Random();
                            int randomValue = random.nextInt(2);
                            if (randomValue == 0) {
                                message = " process " + p.processId + " from queue 2 to waitingQueue 3";
                                movementLog.get(p.processId - 1).add(message);
                                System.out.println(message);
                                waitingQueue3.add(p);
                            } else {
                                message = " process " + p.processId + " from queue 2 to waitingQueue 1";
                                movementLog.get(p.processId - 1).add(message);
                                System.out.println(message);
                                waitingQueue1.add(p);
                            }
                        }
                    }
                }

                
                if (queue2.size() < queue2MaxSize && !waitingQueue2.isEmpty()) {
                    while (queue2.size() < queue2MaxSize && !waitingQueue2.isEmpty()) {
                        Process newP = waitingQueue2.poll();
                        queue2.add(newP);

                        message = " process " + newP.processId + " from waitingQueue 2 to queue 2";
                        movementLog.get(newP.processId - 1).add(message);
                        System.out.println(message);
                    }
                }
            }

            if (!queue3.isEmpty() || !waitingQueue3.isEmpty()) { // This section handles Processes for queue 3
                for (int i = 0; i < 2 && !queue3.isEmpty(); i++) { 
                    Process p = queue3.peek();
                    p.RemainingBurstTime -= 1;

                    if (p.RemainingBurstTime <= 0) { 
                        queue3.poll();

                        message = " process " + p.processId + " from queue 3 finished at queue 3";
                        movementLog.get(p.processId - 1).add(message);
                        System.out.println(message);

                        p.RemainingBurstTime = 0;
                        p.finishedQueue = 3;
                        finishedProcesses.add(p);
                    }
                }

                
                if (queue3.size() < queue3MaxSize && !waitingQueue3.isEmpty()) {
                    while (queue3.size() < queue3MaxSize && !waitingQueue3.isEmpty()) {
                        Process newP = waitingQueue3.poll();
                        queue3.add(newP);
                        message = " process " + newP.processId + " from waitingQueue 3 to queue 3";
                        movementLog.get(newP.processId - 1).add(message);
                        System.out.println(message);
                    }
                }
            }
        }

        System.out.println("------------------------------------------");
        System.out.println("\n");
        System.out.println("Processes order that finished first :");
        System.out.println("------------------------------------------");

        for (int i = 0; i < finishedProcesses.size(); i++) {
            Process p = finishedProcesses.get(i);
            System.out.println((i + 1) + "- p" + p.processId +" exited from queue: " + p.finishedQueue+ " with burst time: " + p.initialBurstTime + " ms" );
        }
    }
}

