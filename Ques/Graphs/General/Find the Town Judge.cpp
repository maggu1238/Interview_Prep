/*There are n people numbered from 
1 to n in a town. There's a rumor that one of these people is secretly the town judge. If there is a town judge, they must meet these conditions:

There are n people numbered from 1 to n in a town. There’s a rumor that one of these people is secretly the town judge. A town judge must meet the following conditions:
1. The judge doesn’t trust anyone.
2. Everyone else (except the judge) trusts the judge.
3. Only one person in the town can meet both of these conditions.
Given there is n and a two-dimensional array called trust,  each element trust[i] has two elements [a,b], it means that a trusts person b. If there is a judge, return their number, return −1 otherwise.*/

int FindJudge(int n, vector<vector<int>> trust) {
  if(trust.size() < n-1){
    return -1;
  }
  vector<int> outDegree(n+1,0);
  vector<int> inDegree(n+1,0);
  
  for( int i = 0; i < trust.size(); i++){
    int src = trust[i][0];
    int dest = trust[i][1];
    outDegree[src]++;
    inDegree[dest]++;
  }
  
  for( int i = 1; i<=n;i++){
    if(outDegree[i] == 0 && inDegree[i] == n-1){
      return i;
    }
  }
  
  return -1;
}