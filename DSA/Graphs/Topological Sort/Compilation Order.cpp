/*There are a total of n classes labeled with the English alphabet (A,B,C,and so on). Some classes are dependent on other classes for compilation. For example, if class B extends class A, then B has a dependency on A. Therefore,A must be compiled before B.

Given a list of the dependency pairs, find the order in which the classes should be compiled.*/
#include <iostream>

vector<char> FindCompilationOrder(vector<vector<char>> dependencies)
{
  // Replace this placeholder return statement with your code
  std::unordered_map<char, int> inDegree;
  std::unordered_map<char, vector<char>> adj;
  for( int i =0; i< dependencies.size(); i++){
    vector<char> dependency = dependencies[i];
    char src = dependency[1];
    char dest = dependency[0];
    
    adj[src].push_back(dest);
    //cout<<src<<" "<<dest<<endl;
    if(inDegree.find(src) == inDegree.end()){
      inDegree[src] = 0;
    }
    if(inDegree.find(dest) == inDegree.end()){
      inDegree[dest] = 0;
    }
    inDegree[dest]++;
    //cout<<dest<< " "<<inDegree[dest]<<endl;
  }
  
  std::queue<char> q;
  std::vector<char> result={};

  for(auto& itr : inDegree){
    if(itr.second == 0){
      //cout<<itr.first<<" "<<endl;;
      q.push(itr.first);
    }
  }
  
  while(!q.empty()){
    char ch = q.front();
    //cout<<ch<<" "<<endl;
    q.pop();
    result.push_back(ch);
    for( int i =0; i < adj[ch].size(); i++){
      char child = adj[ch][i];
     // cout<<"child-"<<inDegree[child]<<endl;
      inDegree[child]--;
      if(inDegree[child] == 0){
        //cout<<" "<<child<<endl;
        q.push(child);
      }
    }
  }
  if(result.size() != inDegree.size()){
    return {};
  }
  return result;
}