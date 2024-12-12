/*There are a total of numCourses courses you have to take, labeled from 0 to numCourses - 1. You are given an array prerequisites where prerequisites[i] = [ai, bi] indicates that you must take course bi first if you want to take course ai.

For example, the pair [0, 1], indicates that to take course 0 you have to first take course 1.
Return the ordering of courses you should take to finish all courses. If there are many valid answers, return any of them. If it is impossible to finish all courses, return an empty array.*/
#include<map>
vector<int> FindOrder(int n, vector<vector<int>> preRequisites) {

   map<int, vector<int>> adjacentList;
   map<int, int> inDegree;

   for ( int i = 0; i < n; i++){
      inDegree[i] = 0;
      adjacentList[i] = {};
   }

   for( int i = 0; i< preRequisites.size(); i++){
      vector<int> pair  = preRequisites[i];
      int c1  = pair[0];
      int c2 = pair[1];

      inDegree[c1]++;
      adjacentList[c2].push_back(c1);
   }

   std::queue<int> q;

   for(auto i : inDegree){
      if ( i.second == 0){
         q.push(i.first);
      }
   }
   vector<int> result;

   while(q.size()){
      int c1 = q.front();
      result.push_back(c1);
      q.pop();

      for ( int i = 0; i < adjacentList[c1].size(); i++){
         int adjC = adjacentList[c1][i];
         inDegree[adjC]--;
         if (inDegree[adjC] == 0)
            q.push(adjC);
      }
   }

   if ( result.size() != inDegree.size()){
      return {};
   }
   return result;
}