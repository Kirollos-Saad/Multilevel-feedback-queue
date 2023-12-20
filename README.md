# Multi-Level Feedback Queue

This Java program simulates a multi-level feedback queue scheduling algorithm. It uses three different queues with varying time quantum and priorities for task execution.

## Program Explanation

- The program initializes a set of processes with random burst times.
- Processes are inserted into the initial waiting queue.
- The simulation handles three queues with different time quantum and priorities.
- Processes move between queues and waiting queues based on their remaining burst time.
- The program prints the log of process movement and the order in which processes finish.

## Overview

The program is structured as follows:

- `Process` class: Represents a process with attributes such as `processId`, `initialBurstTime`, `RemainingBurstTime`, and `finishedQueue`.
- `MultiLevelFeedbackQueue` class: Contains the main logic for the multi-level feedback queue scheduling.

## Usage

1. Clone the repository:

    ```bash
    git clone https://github.com/yourusername/multi-level-feedback-queue.git
    ```

2. Compile and run the program:

    ```bash
    cd multi-level-feedback-queue
    javac com/mycompany/multilevel/feedback/queue/MultiLevelFeedbackQueue.java
    java com.mycompany.multilevel.feedback.queue.MultiLevelFeedbackQueue
    ```



