import java.util.Arrays;

import org.w3c.dom.Node;

/**
 *
 * WAVLTree_nechamagans_ellas2_ratson
 * ellas2 - 316986660
 * nechamagans - 314718024
 * ratson - 307839092
 * An implementation of a WAVL Tree with
 * distinct integer keys and info
 *
 */

public class WAVLTree_nechamagans_ellas2_ratson {
	
	int size;
	WAVLNode root;
	WAVLNode minNode;
	WAVLNode maxNode;

  /**
   * public boolean empty()
   *
   * returns true if and only if the tree is empty
   *
   */
	public boolean empty() {
		boolean isEmpty = false;
		if (this.size == 0){
			isEmpty = true;
		}
		return isEmpty; 
	}

	//check the difference of a node from his right son
	public int diffRightSon(WAVLNode parent){
		int difference = 0;
		if (parent.right != null){
			difference = parent.rank - parent.right.rank;
		}
		else{
			difference = parent.rank + 1; 
		}
		return difference;
	}
	
	//check the difference of a node from his left son
	public int diffLeftSon(WAVLNode parent){
		int difference = 0;
		if (parent.left != null){
			difference = parent.rank - parent.left.rank;
		}
		else{
			difference = parent.rank + 1; 
		}
		return difference;
	}
 /**
   * public String search(int k)
   *
   * returns the info of an item with key k if it exists in the tree
   * otherwise, returns null
   */
	public String search(int k)
	{
		return searchRec(this.root, k);  // to be replaced by student code
	}
	
	//Recursive function of search
	public String searchRec(WAVLNode currNode, int k){
		if (currNode.key == k){
			return currNode.info;
		}
		else if (currNode.key > k){
			if (currNode.left == null){
				return null;
			}
			else{
				return searchRec(currNode.left, k);
			}
		}
		else if (currNode.key < k){
			if (currNode.right == null){
				return null;
			}
			else{
				return searchRec(currNode.right, k);
			}
		}
		return null;
	}
	
        

           
  /**
   * public int insert(int k, String i)
   *
   * inserts an item with key k and info i to the WAVL tree.
   * the tree must remain valid (keep its invariants).
   * returns the number of rebalancing operations, or 0 if no rebalancing operations were necessary.
   * returns -1 if an item with key k already exists in the tree.
   */
	public int insert(int k, String i) {
		
		int count = 0;
		WAVLNode newNode = new WAVLNode (i, k);
		//if the tree is empty we need to set the root as the new node
		if (this.empty()){
			this.root = newNode;
		}
		//if we already have this node return -1
		else if (this.contains(this.keysToArray(), k)){
			return -1;
		}

		else{
		WAVLNode parent = this.parentOfNew(root, newNode, k);
		if (this.caseInsert(parent).equals("a")){
			WAVLNode node = newNode;
			//Rebalance up until problem is fixed or we reach the root
				while (node.parent != null && (this.diffRightSon(node.parent) == 0 || this.diffLeftSon(node.parent) == 0)){
					if (this.diffRightSon(node.parent) == 0 && this.diffLeftSon(node.parent) == 1){
						node.parent.rank += 1;
						count += 1;
						node = node.parent;
					}
					else if (this.diffRightSon(node.parent) == 1 && this.diffLeftSon(node.parent) == 0){
						node.parent.rank += 1;
						count += 1;
						node = node.parent;
					}
					else if (this.diffRightSon(node.parent) == 2 && this.diffLeftSon(node.parent) == 0){
						if (this.diffLeftSon(node) == 1 && this.diffRightSon(node) == 2){
							this.rotateRight(node);
							count += 1;
							if (node.parent == null){
								this.root = node;
							}
							break;
						}
						else if (this.diffLeftSon(node) == 2 && this.diffRightSon(node) == 1){
							
							this.rotateLeftThenRight(node.right);
							count += 2;
							if (node.parent.parent == null){
								this.root = node.parent;
							}
							break;
						}
						
					}
					else if (this.diffRightSon(node.parent) == 0 && this.diffLeftSon(node.parent) == 2){
						if (this.diffLeftSon(node) == 2 && this.diffRightSon(node) == 1){
							this.rotateLeft(node);
							count += 1;
							if (node.parent == null){
								this.root = node;
							}
							break;
						}
						else if (this.diffLeftSon(node) == 1 && this.diffRightSon(node) == 2){
							this.rotateRightThenLeft(node.left);
							count += 2;
							if (node.parent.parent == null){
								this.root = node.parent;
							}
							break;
						}
					}
				}
				
		}
	
			
		}
		//Change the minimum if the newly inserted node is the new minNode
		if (this.minNode == null || k < this.minNode.key){
			this.minNode = newNode;
		}
		//Change the maximum if the newly inserted node is the new maxNode
		if (this.maxNode == null || k > this.maxNode.key){
			this.maxNode = newNode;
		}
		this.size += 1;
	   return count;	
		
}
	//Rotate right - Single rotate
	public void rotateRight(WAVLNode node){
		
		node.parent.left = node.right;
		if (node.right != null){
		node.right.parent = node.parent;
		}
		node.right = node.parent;
		node.parent = node.parent.parent;
		node.right.parent = node;
		if (node.parent != null && (node.key < node.parent.key)){
			node.parent.left = node;
		}
		else if (node.parent != null && (node.key > node.parent.key)){
		node.parent.right = node;
		}
		node.right.rank -= 1;
	}
	
	//Rotate left - Single rotate
	public void rotateLeft(WAVLNode node){
		
		node.parent.right = node.left;
		if (node.left != null){
		node.left.parent = node.parent;
		}
		node.left = node.parent;
		node.parent = node.parent.parent;
		node.left.parent = node;
		
		if (node.parent != null && (node.key < node.parent.key)){
			node.parent.left = node;
		}
		else if (node.parent != null && (node.key > node.parent.key)){
		node.parent.right = node;
		}
		
		node.left.rank -= 1;
	}
	
	//Double rotate - left then right
	public void rotateLeftThenRight(WAVLNode node){
		//Rotate left
		
		node.parent.right = node.left;
		if (node.left != null){
		node.left.parent = node.parent;
		}
		node.left = node.parent;
		node.parent = node.parent.parent;
		node.left.parent = node;
		int tempRank = node.rank;
		node.rank = node.left.rank;
		node.left.rank = tempRank;
		
		//Rotate right
		node.parent.left = node.right;
		if (node.right != null){
		node.right.parent = node.parent;
		}
		node.right = node.parent;
		
		
		node.parent = node.parent.parent;
		node.right.parent = node;
		
		if (node.parent != null && (node.key < node.parent.key)){
			node.parent.left = node;
		}
		else if (node.parent != null && (node.key > node.parent.key)){
		node.parent.right = node; 
		}
		
		node.right.rank -= 1;
	}
	
	//Double rotate - right then left
	public void rotateRightThenLeft(WAVLNode node){
		//Rotate right
				node.parent.left = node.right;
				if (node.right != null){
					node.right.parent = node.parent;
					}
				
				node.right = node.parent;
				node.parent = node.parent.parent;
				node.right.parent = node;
				int tempRank = node.rank;
				node.rank = node.right.rank;
				node.right.rank = tempRank;
				
		//Rotate left
				node.parent.right = node.left;
				
				if (node.left != null){
					node.left.parent = node.parent;
					}
				
				node.left = node.parent;
				node.parent = node.parent.parent;
				node.left.parent = node;
				
				if (node.parent != null && (node.key < node.parent.key)){
					node.parent.left = node;
				}
				else if (node.parent != null && (node.key > node.parent.key)){
				node.parent.right = node;
				}
				
				node.left.rank -= 1;
	}

	//Check to which node we need to attach the new node as a son and attach the node as a son
	public WAVLNode parentOfNew (WAVLNode node, WAVLNode newNode, int k){
		if (node.key > k){
			if (node.left == null){
				node.left = newNode;
				newNode.parent = node;
				newNode.rank = 0;
			}
			else{
				this.parentOfNew(node.left, newNode, k);
			}
		}
		else if (node.key < k){
			if (node.right == null){
				node.right = newNode;
				newNode.parent = node;
				newNode.rank = 0;
			}
			
			else{
				this.parentOfNew(node.right, newNode, k);
			}
		}
		return newNode.parent;
	}
	
		public String caseInsert (WAVLNode node){
			if (node.right == null || node.left == null){
				return "a";
			}
			//change to &&
			else if (node.right != null && node.left != null){
				return "b";
			}
			return null;
				
		}
	
	
  /**
   * public int delete(int k)
   *
   * deletes an item with key k from the binary tree, if it is there;
   * the tree must remain valid (keep its invariants).
   * returns the number of rebalancing operations, or 0 if no rebalancing operations were needed.
   * returns -1 if an item with key k was not found in the tree.
   */
	   public int delete(int k)
	   {
		   int count = 0;
		   // If the tree is empty there is no node to delete
		   if (this.empty()){
			   return -1;
		   }
		   
		  WAVLNode nodeForDeletion = this.searchForDeletion(k);
		  
		  // If the node is not in the tree return -1
		  if (nodeForDeletion == null){
			  return -1;
		  }
		  if (this.size() == 1){
			   this.root = null;
			   this.size -= 1;
			   this.minNode = null;
			   this.maxNode = null;
			   return count;
		   }
		  //We are going to delete the minimun - Update it accordingly, find the successor.
		  if (this.minNode.key == k){
			  if (nodeForDeletion.isLeaf())
				  this.minNode = nodeForDeletion.parent;
			  else if (nodeForDeletion.right!=null){
				  WAVLNode tempMin=nodeForDeletion.right;
				  if (tempMin.left==null)
					  this.minNode=tempMin;
				  else {
					  while (tempMin.left!=null){
						  tempMin=tempMin.left;
					  }
					  this.minNode=tempMin;
				  }
			  }
		  }
		  //We are going to delete the maximum - Update it accordingly, find the predecessor.
		  if (this.maxNode.key == k){
			  if (this.maxNode.isLeaf())
				  this.maxNode = nodeForDeletion.parent;
			  else if (nodeForDeletion.left!=null){
				  WAVLNode tempMax=nodeForDeletion.left;
				  if (tempMax.right==null)
					  this.maxNode=tempMax;
				  else {
					  while (tempMax.right!=null){
						  tempMax=tempMax.right;
					  }
					  this.maxNode=tempMax;
				  }
			  }
		  }
		  boolean right = false;
		  if (nodeForDeletion != this.root && nodeForDeletion.parent.key < nodeForDeletion.key){
			  right = true;
		  }
		  WAVLNode currNode = nodeForDeletion.parent;
		  if (this.root == nodeForDeletion && this.size() == 2){
				if (this.root.left != null){
					this.root = this.root.left;
					this.root.parent = null;
					count += 1;
					}
				else{
					this.root = this.root.right;
					this.root.parent = null;
					count += 1;
				}
			}
		  //We are deleting a binary node - Replace the node's key and info with predeccessor's key and info and delete the predecessor
		  else if (nodeForDeletion.left != null && nodeForDeletion.right != null){
			  WAVLNode node = nodeForDeletion.left;
			  while (node.right != null){
				  node = node.right;
			  }
			  int key = node.key;
			  String info = node.info;
			  this.delete(key);
			  this.size += 1;
			  nodeForDeletion.key = key;
			  nodeForDeletion.info = info;
		  }
		  //We are deleting a left son
		  else if (!right){
			  //We are deleting a Leaf
		  if (nodeForDeletion.isLeaf()){
			  String caseDeleteLeft = this.caseDeleteleft(nodeForDeletion);
			  if (caseDeleteLeft.equals("a")){
					  nodeForDeletion.parent.left = null;
					  nodeForDeletion.parent = null;
					  //problem solved
			  }
			  else if (caseDeleteLeft.equals("b")){
					  nodeForDeletion.parent.rank -= 1;
					  count += 1;
					  nodeForDeletion.parent.left = null;
					  nodeForDeletion.parent = null;
					  currNode = currNode.parent;
					  //problem might have been moved up
				  }
			  else if (caseDeleteLeft.equals("c")){
				  nodeForDeletion.parent.left = null;
				  nodeForDeletion.parent = null;
				  //problem might have been moved up
			  }
				  }
		  //We are deleting an unary node
		  else if ((nodeForDeletion.right == null & nodeForDeletion.left != null)|| (nodeForDeletion.right != null & nodeForDeletion.left == null)){
			  String caseDeleteLeft = this.caseDeleteleft(nodeForDeletion);
			  if (caseDeleteLeft.equals("a")){
					  if (nodeForDeletion.left != null){
					 nodeForDeletion.parent.left = nodeForDeletion.left;
					 nodeForDeletion.left.parent = nodeForDeletion.parent;
					 nodeForDeletion.parent = null;
					 //problem solved
					  }
					  else{
						  nodeForDeletion.parent.left = nodeForDeletion.right;
						  nodeForDeletion.right.parent = nodeForDeletion.parent;
						  nodeForDeletion.parent = null;
						  //problem solved
					  }
				  }
					  else if (caseDeleteLeft.equals("b")){
					  if (nodeForDeletion.left != null){
						  nodeForDeletion.parent.left = nodeForDeletion.left;
						  nodeForDeletion.left.parent = nodeForDeletion.parent;
						  nodeForDeletion.parent = null;
						  //problem solved
					  }
					  else{
						  nodeForDeletion.parent.left = nodeForDeletion.right;
						  nodeForDeletion.right.parent = nodeForDeletion.parent;
						  nodeForDeletion.parent = null; 
						  //problem solved
					  }
				  }
					  else if (caseDeleteLeft.equals("c")){
					  if (nodeForDeletion.left != null){
						  nodeForDeletion.parent.left = nodeForDeletion.left;
						  nodeForDeletion.left.parent = nodeForDeletion.parent;
						  nodeForDeletion.parent = null;
						  //problem might have been moved up
					  }
					  else{
						  nodeForDeletion.parent.left = nodeForDeletion.right;
						  nodeForDeletion.right.parent = nodeForDeletion.parent;
						  nodeForDeletion.parent = null; 
						  //problem might have been moved up
					  }
				  }
			  }
			  }
		  
		  //We are deleting a right son
		  else if (right){
			  //We are deleting a Leaf
			  if (nodeForDeletion.isLeaf()){
				  String caseDeleteRight = this.caseDeleteright(nodeForDeletion);
				  if (caseDeleteRight.equals("a")){
					  nodeForDeletion.parent.right = null;
					  nodeForDeletion.parent = null;
					  //problem solved
			  }
				  else if (caseDeleteRight.equals("b")){
					  nodeForDeletion.parent.rank -= 1;
					  count += 1;
					  nodeForDeletion.parent.right = null;
					  nodeForDeletion.parent = null;
					  currNode = currNode.parent;
					  //problem might have been moved up
				  }
				  else if (caseDeleteRight.equals("c")){
				  nodeForDeletion.parent.right = null;
				  nodeForDeletion.parent = null;
				  //problem might have been moved up
			  }
			  }
			  //We are deleting an unary node
			  else if ((nodeForDeletion.right == null & nodeForDeletion.left != null)|| (nodeForDeletion.right != null & nodeForDeletion.left == null)){
				  String caseDeleteRight = this.caseDeleteright(nodeForDeletion);
				  if (caseDeleteRight.equals("a")){
					  if (nodeForDeletion.right != null){
					 nodeForDeletion.parent.right = nodeForDeletion.right;
					 nodeForDeletion.right.parent = nodeForDeletion.parent;
					 nodeForDeletion.parent = null;
					 //problem solved
					  }
					  else{
						  nodeForDeletion.parent.right = nodeForDeletion.left;
						  nodeForDeletion.left.parent = nodeForDeletion.parent;
						  nodeForDeletion.parent = null;
						  //problem solved
					  }
				  }
					  else if (caseDeleteRight.equals("b")){
					  if (nodeForDeletion.right != null){
						  nodeForDeletion.parent.right = nodeForDeletion.right;
							 nodeForDeletion.right.parent = nodeForDeletion.parent;
							 nodeForDeletion.parent = null;
							 //problem might have been moved up
							 currNode = currNode.parent;
					  }
					  else{
						  nodeForDeletion.parent.right = nodeForDeletion.left;
						  nodeForDeletion.left.parent = nodeForDeletion.parent;
						  nodeForDeletion.parent = null; 
						//problem might have been moved up
					  }
				  }
					  else if (caseDeleteRight.equals("c")){
					  if (nodeForDeletion.right != null){
						  nodeForDeletion.parent.right = nodeForDeletion.right;
						  nodeForDeletion.right.parent = nodeForDeletion.parent;
						  nodeForDeletion.parent = null;
						  //problem might have been moved up
					  }
					  else{
						  nodeForDeletion.parent.right = nodeForDeletion.left;
						  nodeForDeletion.left.parent = nodeForDeletion.parent;
						  nodeForDeletion.parent = null;
						  //problem solved
					  }
				  }
			  }
			  }
		  
		//Rebalance up until problem is fixed or we reach the root
		  while (currNode != null && (this.diffRightSon(currNode) == 3 || this.diffLeftSon(currNode) == 3)){
			  if (diffRightSon(currNode) == 2 && diffLeftSon(currNode) == 3){
				  currNode.rank -= 1;
				  count += 1;
				  currNode = currNode.parent;
			  }
			  else if (diffRightSon(currNode) == 3 && diffLeftSon(currNode) == 2){
					  currNode.rank -= 1;
					  count += 1;
					  currNode = currNode.parent;
			  }
			  else if (diffRightSon(currNode) == 1 && diffLeftSon(currNode) == 3){
				  if (diffRightSon(currNode.right) == 2 && diffLeftSon(currNode.right) == 2){
					  currNode.rank -= 1;
					  currNode.right.rank -= 1;
					  count += 2;
					  currNode = currNode.parent;
				  }
				  else if (diffRightSon(currNode.right) == 1 && (diffLeftSon(currNode.right) == 2 || diffLeftSon(currNode.right) == 1)){
					  //rotateLeft
					  this.rotateLeft(currNode.right);
					  currNode.parent.rank += 1;
					  count += 1;
					  if (currNode.isLeaf()){
						  currNode.rank -= 1;
						  count += 1;
					  }
					  if (currNode.parent.parent == null){
							this.root = currNode.parent;
						}
					  break;
				  }
				  else if (diffRightSon(currNode.right) == 2 && diffLeftSon(currNode.right) == 1){
					  //double rotate - right then left
					  this.rotateRightThenLeft(currNode.right.left);
					  currNode.parent.rank += 1;
					  currNode.rank -= 1;
					  count += 2;
					  if (currNode.parent.parent == null){
							this.root = currNode.parent;
						}
					  break;
				  }
			  }
			  else if (diffRightSon(currNode) == 3 && diffLeftSon(currNode) == 1){
				  if (diffRightSon(currNode.left) == 2 && diffLeftSon(currNode.left) == 2){
					  currNode.rank -= 1;
					  currNode.left.rank -= 1;
					  count += 2;
					  currNode = currNode.parent;
				  }
				  else if (diffLeftSon(currNode.left) == 1 && (diffRightSon(currNode.left) == 2 || diffRightSon(currNode.left) == 1)){
					  //rotateRight
					  this.rotateRight(currNode.left);
					  currNode.parent.rank += 1;
					  count += 1;
					  if (currNode.isLeaf()){
						  currNode.rank -= 1;
						  count += 1;
					  }
					  if (currNode.parent.parent == null){
							this.root = currNode.parent;
						}
					  break;
				  }
				  else if (diffRightSon(currNode.left) == 1 && diffLeftSon(currNode.left) == 2){
					  //double rotate - left then right
					  this.rotateLeftThenRight(currNode.left.right);
					  currNode.parent.rank += 1;
					  currNode.rank -= 1;
					  count += 2;
					  if (currNode.parent.parent == null){
							this.root = currNode.parent;
						}
					  break;
				  }
			  }
		  }
		  this.size -= 1;
			  return count;
		  
	   }

	   //Checks the case when deleting a left son
	   public String caseDeleteleft(WAVLNode node){
		   int diffLeft = this.diffLeftSon(node.parent);
		   int diffRight = this.diffRightSon(node.parent);
		   if (diffLeft == 1 && diffRight == 1){
			   return "a";
		   }
		   if (diffLeft == 1  && diffRight == 2){ 
			   return "b";
		   }
		   if (diffRight == 1  && diffLeft== 2){
			   return "c";	
			   }
		   if (diffRight == 2  && diffLeft== 2){
			   return "c";	
		   }
		 return null;  
	   }
	   
	 //Checks the case when deleting a right son
	   public String caseDeleteright(WAVLNode node){
		   int diffLeft = this.diffLeftSon(node.parent);
		   int diffRight = this.diffRightSon(node.parent);
		   if (diffLeft == 1 && this.diffRightSon(node.parent) == 1){
			   return "a";
		   }
		   if (diffLeft == 2  && this.diffRightSon(node.parent)== 1){ 
			   return "b";
		   }
		   if (diffRight == 2  && diffLeft == 1){
			   return "c";	
			   }
		   if (diffRight == 2  && diffLeft == 2){
			   return "c";	
		   }
		 return null;  
	   }
	   
	   
	   //Searches for the node we are deleting
	   public WAVLNode searchForDeletion(int k)
		{
			return searchForDeletionRec(this.root, k);  
		}
		
	   //Recursive function of searchForDeletion
		public WAVLNode searchForDeletionRec(WAVLNode currNode, int k){
			if (currNode.key == k){
				return currNode;
			}
			else if (currNode.key > k){
				if (currNode.left == null){
					return null;
				}
				else{
					return searchForDeletionRec(currNode.left, k);
				}
			}
			else if (currNode.key < k){
				if (currNode.right == null){
					return null;
				}
				else{
					return searchForDeletionRec(currNode.right, k);
				}
			}
			return null;
		}
   /**
    * public String min()
    *
    * Returns the iîfo of the item with the smallest key in the tree,
    * or null if the tree is empty
    */
	   public String min()
	   {
		   return this.minNode.info;
	   }

   /**
    * public String max()
    *
    * Returns the info of the item with the largest key in the tree,
    * or null if the tree is empty
    */
	  
	   public String max()
	   {
		   return this.maxNode.info;
	   }
	   
	   //Check whether an array contains k by going over it and comparing the items to k 
	   public boolean contains(int[] arr, int k){
		   for (int i=0; i < arr.length; i++){
			   if (arr[i] == k){
				   return true;
			   }
		   }
		   return false;
	   }
  /**
   * public int[] keysToArray()
   *
   * Returns a sorted array which contains all keys in the tree,
   * or an empty array if the tree is empty.
   */
	   public int[] keysToArray()
	   {
		   if (this.empty()){
			   return new int[0];
		   }
		   int[] intArr = new int[this.size()]; 
		   String strKeys = "";
		   String str = this.keysToArrayRec(this.root, strKeys); 
		   String[] strArr = str.split(" ");
		   for (int i=0; i < this.size(); i++){
			   intArr[i] =  Integer.parseInt(strArr[i]);
		   }
		   return intArr;
		   
	   }
	   //Recursive function of keysToArray
	   public String keysToArrayRec(WAVLNode currNode, String strKeys){
		   if (currNode != null){
			   strKeys = this.keysToArrayRec (currNode.left, strKeys) + (String.valueOf(currNode.key) + " ") + this.keysToArrayRec (currNode.right, strKeys);
		   }
		   return strKeys;
	   }
	   
	   

  /**
   * public String[] infoToArray()
   *
   * Returns an array which contains all info in the tree,
   * sorted by their respective keys,
   * or an empty array if the tree is empty.
   */
	   public String[] infoToArray()
	   {
		   if (this.empty()){
			   return new String[0];
		   }
		   String strInfos = "";
		   String str = this.infoToArrayRec(this.root, strInfos); 
		   String[] strArr = str.split(" ");
		   return strArr;  
	   }
	   
	   //recursive function of infoToArray
	   public String infoToArrayRec(WAVLNode currNode, String strInfos){
		   if (currNode != null){

			   strInfos = this.infoToArrayRec (currNode.left, strInfos) + (currNode.info + " ") + this.infoToArrayRec(currNode.right, strInfos);
		   }
		   return strInfos;
	   }

   /**
    * public int size()
    *
    * Returns the number of nodes in the tree.
    *
    * precondition: none
    * postcondition: none
    */
	   public int size()
	   {
		   return this.size; 
	   }
	   

  /**
   * public class WAVLNode
   *
   * If you wish to implement classes other than WAVLTree
   * (for example WAVLNode), do it in this file, not in 
   * another file.
   * This is an example which can be deleted if no such classes are necessary.
   */
	   public class WAVLNode{
		   String info;
		   int key;
		   WAVLNode parent;
		   WAVLNode left;
		   WAVLNode right;
		   int rank;
		   
		   
		   WAVLNode(String info, int key){
			   this.info = info;
			   this.key = key;
		   }
		   
		   //Check whether a node is a leaf by checking its right and left son
		   public boolean isLeaf(){
		   if (this.left == null && this.right == null){
			   return true;
		   }
			   return false;
		   }
		  

}
}