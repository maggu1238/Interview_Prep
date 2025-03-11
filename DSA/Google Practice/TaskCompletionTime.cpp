/**
There will be some tasks, and each task can have one or more subtasks,
 by default all tasks have completion time 1 unit, but the comp_time of a parent 
 task is the x if all subtasks have time x, 
otherwise it is their sum, we have to find the total completion time
*/

/**
We have a set of tasks, each of which:

May have subtasks or be an independent task.
Completion time for a task:
If it has no subtasks → 1 unit (default).
If all its subtasks have the same completion time → max of their time.
Otherwise → sum of all subtasks' times.
The goal is to find the total completion time for the given set of tasks.
*/

#include <iostream>
#include <vector>
#include <unordered_map>
#include <unordered_set>
using namespace std;

unordered_map<int, vector<int>> adj;  // Adjacency list
unordered_map<int, int> comp_time;    // Stores computed completion times

// DFS to calculate completion time of a task
int dfs(int task) {
    if (comp_time.count(task)) return comp_time[task];  // Already computed

    if (adj[task].empty()) return comp_time[task] = 1;  // Independent task

    vector<int> sub_times;
    for (int subtask : adj[task]) {
        sub_times.push_back(dfs(subtask));
    }

    // Check if all subtasks have the same time
    bool all_same = true;
    for (int i = 1; i < sub_times.size(); i++) {
        if (sub_times[i] != sub_times[i - 1]) {
            all_same = false;
            break;
        }
    }

    // If all subtasks have the same time, take max; otherwise sum
    return comp_time[task] = all_same ? sub_times[0] : accumulate(sub_times.begin(), sub_times.end(), 0);
}

int totalCompletionTime(vector<int>& tasks) {
    int total_time = 0;
    unordered_set<int> visited;

    for (int task : tasks) {
        if (!visited.count(task)) {
            total_time += dfs(task);
            visited.insert(task);
        }
    }

    return total_time;
}

int main() {
    adj = {
        {1, {2, 3}},   // Task 1 has subtasks 2, 3
        {2, {}},       // Task 2 has no subtasks
        {3, {}},       // Task 3 has no subtasks
        {4, {5, 6}},   // Task 4 has subtasks 5, 6
        {5, {7}},      // Task 5 has subtask 7
        {6, {7}},      // Task 6 has subtask 7
        {7, {}}        // Task 7 has no subtasks
    };

    vector<int> tasks = {1, 4}; // Compute time for task 1 and task 4

    cout << "Total Completion Time: " << totalCompletionTime(tasks) << endl;
    return 0;
}


