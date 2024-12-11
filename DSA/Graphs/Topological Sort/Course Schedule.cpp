/*There are a total of numCourses courses you have to take. The courses are labeled from 0 to numCourses - 1. You are also given a prerequisites array, where prerequisites[i] = [a[i], b[i]] indicates that you must take course b[i] first if you want to take the course a[i]. 

Return TRUE if all of the courses can be finished. Otherwise, return FALSE.*/

bool CanFinish(int numCourses, vector<vector<int>> preRequisites) {

   map<int, vector<int>> adjacentList;
   map<int, int> indegre;

   for( int i = 0; i < numCourses; i++){
      indegre[i] = 0;
      adjacentList[i] = {};
   }

   for ( int i = 0; i < preRequisites.size(); i++){
      vector<int> pair = preRequisites[i];

      int c1 = pair[0];
      int c2 = pair[1];

      adjacentList[c2].push_back(c1);
      indegre[c1]++;
   }

   std::queue<int> q;

   for ( auto i = indegre.begin(); i != indegre.end(); i++){
      if( i -> second == 0){
         q.push(i -> first);
      }
   }

   int count = 0;
   while(q.size()){

      int c1 = q.front();
      q.pop();
      count++;

      for( int i = 0; i<adjacentList[c1].size(); i++){
         int adj_c1 = adjacentList[c1][i];
         indegre[adj_c1]--;
         if ( indegre[adj_c1] == 0){
            q.push(adj_c1);
         }
      }
   }


   if (count != indegre.size()){
      return false;
   }



   // Replace this placeholder return statement with your code
   return true;
}