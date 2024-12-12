/*Given a binary tree having n nodes. Check whether all of its nodes have a value equal to the sum of their child nodes. Return 1 if all the nodes in the tree satisfy the given properties, else it returns 0. For every node, the data value must be equal to the sum of the data values in the left and right children. Consider the data value 0 for a NULL child. Also, leaves are considered to follow the property.*/

class Solution{
    public:
    //Function to check whether all nodes of a tree have the value 
    //equal to the sum of their child nodes.
    int isSumProperty(Node *root)
    {
     // Add your code here
        if(root == NULL)
            return true;
        if( root -> left == NULL && root -> right == NULL){
            return true;
        }
        
        int left = 0, right = 0;
        if( root -> left){
            left = root -> left -> data;
        }
        
        if( root -> right){
            right = root -> right -> data;
        }
        
        if( (root -> data == left + right) 
        && isSumProperty(root -> left) && isSumProperty(root -> right)){
            return true;
        }
        return false;
        
    }
};