package datastructure_java.datastructure.binaryTree;

public class BinaryTree {
	
	//이진트리 
	public class Node {
		public int item;
		public Node left;
		public Node right;
	}
	
	//생성자.
	public BinaryTree() {
		this.root = null;
	}
	
	public Node root; // 루트 노드에 대한 포인터.
	
	//순회
	public void preOrder(Node node) {
		if(null != node) {
			System.out.println("data : "+node.item);
			this.preOrder(node.left);
			this.preOrder(node.right);
		}
	}
	
	public void inOrder(Node node) {
		if(null != node) {
			this.inOrder(node.left);
			System.out.println("data : "+node.item);
			this.inOrder(node.right);
		}
	}
	
	public void postOrder(Node node) {
		if(null != node) {
			this.postOrder(node.left);
			this.postOrder(node.right);
			System.out.println("data : "+node.item);
		}
	}
	
	
	//삽입1 - 재귀적 방법.
	public void insert(int data) {
	     this.root = this.insertItem(this.root, data);
	}
	
	public Node insertItem(Node parent, int data) {
		/*
		 * data와 기존 노드의 대소비교를 통해(탐색)
		 * 적절한 자리를 찾는 과정을 재귀적으로 함.
		 * 이 과정을 통해 기존 left, right는 다시 똑같이 지정됨.
		 */
		if(null == parent) {
			Node newNode = new Node();
			newNode.item = data;
			newNode.left = null;
			newNode.right = null;
			return newNode;
		}else if(data < parent.item) {
			parent.left = this.insertItem(parent.left, data);
			return parent;
		}else {
			parent.right = this.insertItem(parent.right, data);
			return parent;
		}
		
	}
	
	
	//삽입2 - 비재귀적 방법.
	public void insert2(int data) {
		
		Node newNode = new Node();
		newNode.item = data;
		newNode.left = null;
		newNode.right = null;
		
		if(null == this.root) {
			this.root = newNode;
		}else {
			
			Node node = this.root;
			Node parent = null;
			while(null != node) {
				
				parent = node;
				
				if(data < node.item) {
					node = node.left;
				}else {
					node = node.right;
				}
			}
			//반복이 끝난 후 parent가 새 노드의 부모.
			if(data < parent.item) {
				parent.left = newNode;
			}else {
				parent.right = newNode;
			}
		}
	}
	
	//검색. 
	public Node search(Node node, int target) {
		
		if(null == node || node.item == target) {
			return node;
		}else if(target < node.item) {
			return this.search(node.left, target);
		}else {
			return this.search(node.right, target);
		}
		
	}
	
	//삭제.
	public void delete(int target) {	
		
		//1. 삭제할 노드와 그 부모를 구한다.
		Node rootParent = new Node();
		rootParent.left = this.root;
		
		Node parent = rootParent;
		Node child = rootParent.left;
		String direct = "L";
		
		while(null != child) {
			
			if(target == child.item) {
				break;
			}else {
				parent = child;
				if(target < parent.item) {
					child = parent.left;
					direct = "L";
				}else {
					child = parent.right;
					direct = "R";
				}
			}
		}
		//예외처리 - child가 null일 경우(타겟없음)
		if(null == child) {
			System.out.println("item not found");
			return;
		}
		
		/*
		 * 삭제할 노드의 성격에 따른 분기.
		 * 1. 루트 노드인 경우
		 * 2. 루트 노드가 아닌 경우
		 *  1) 삭제할 노드의 자식이 없는 경우
		 *  2) 삭제할 노드의 자식이 1개인 경우
		 *  3) 삭제할 노드의 자식이 2개인 경우 
		 */
		
		if(null == child.left && null == child.right) {
			//이 경우 부모의 레퍼런스를 해제한다.
			if("L".equals(direct)) {
				parent.left = null;
			}else {
				parent.right = null;
			}		
			//루트인 경우.
			if(this.root == child) {
				this.root = null;
			}
			
		}else if( (null != child.left && null == child.right) || (null == child.left && null != child.right) ) {
			
			//삭제할 노드의 자식을 삭제할 노드의 부모에 연결.
			if("L".equals(direct)) {
				parent.left = (null != child.left)? child.left : child.right;
			}else {
				parent.right = (null != child.left)? child.left : child.right;
			}	
			//루트인 경우
			if(this.root == child) {
				this.root = (null != child.left)? child.left : child.right;
			}
			
		}else {
			//자식이 2개인 경우.
			//대체할 노드를 찾아야 함. 왼쪽 자식 중 가장 큰 노드 혹은 오른쪽 자식 중 가장 작은 노드.
		    
			Node replaceNodeParent = child;
			Node replaceNode = replaceNodeParent.right;
			
			//탐색.
			while(null != replaceNode.left) {
				
				replaceNodeParent = replaceNode;
				replaceNode = replaceNodeParent.left;
				
			}
			
			//삭제할 노드와, 대체노드의 부모가 다른 경우에만 적용할 사항. => 같은경우에는 적용할 필요가 없음.
			if(child != replaceNodeParent) {
				//만약 replaceNode에 자식이 있는 경우(논리상 왼쪽 자식은 없다) -> replaceNodeParent의 왼쪽에 달아줌.
				if(null != replaceNode.right) {
					replaceNodeParent.left = replaceNode.right;
				}else {
					//자식이 없는 경우기에 대체노드 부모의 대체노드 레퍼런스를 삭제. -> 대체노드는 항상 대체노드 부모의 왼쪽.
					replaceNodeParent.left = null;
				}
			}
			
			
			//삭제할 노드와 대체할 노드를 교환한다. - 값만 바꿔도 됨.
			if("L".equals(direct)) {
				parent.left = replaceNode;
			}else {
				parent.right = replaceNode;
			}
			
			replaceNode.left = child.left; //논리상 자가참조 X
			
			//논리상 자가참조를 할 가능성이 있음.
			if(replaceNode != child.right) { // replaceNodeParent == child 인 경우.
				replaceNode.right = child.right;
			}
			
			//이제 child는 참조가 없으므로 메모리에서 해제된다.
			
			//만약 루트인 경우
			if(this.root == child) {
				this.root = replaceNode;
			}
		}
	}
	
	
	//삭제. 재귀적 버전
	public void delete2(int target) {
		this.deleteItem(this.root, target);	
	}
	
	//삭제할 노드를 탐색하는 함수.
	public Node deleteItem(Node node, int target) {
		if(null == node) {
			return null;
		}else {
			if(target == node.item) {
				node = this.deleteNode(node); //삭제할 값이 있다면 언젠가 여기가 실행된다.
			}else if(target < node.item) {
				node.left = this.deleteItem(node.left, target);
			}else {
				node.right = this.deleteItem(node.right, target);
			} 
			return node;
		}
	}
	
	//삭제할 노드의 대체 노드를 리턴하는 함수.
	public Node deleteNode(Node node) {
		
		/*
		 * 삭제할 노드의 자식 유무에 따른 분기 처리.
		 */
		
		if(null == node.left && null == node.right) {
			return null;
		}else if(null == node.left) {
			return node.right;
		}else if(null == node.right) {
			return node.left;
		}else {
			//노드를 실제 삭제하지는 않고, 노드의 값만 대체.
			ReplaceNodeInfo rp = this.deleteMinItem(node.right); //대체값 탐색 오른쪽 한칸 이동, 그리고 왼쪽으로 계속 이동.
		    node.item = rp.item;
		    node.right = rp.node; //원래 오른쪽 자식이 있으면 의미가 없지만, 오른쪽 자식이 대체노드가 되면 null값이 들어감.
			return node;
		}
		
	}

	
	//특정 트리노드의 오른쪽 가장 작은 값과 노드 리턴.(대체노드 탐색 과정)
	public ReplaceNodeInfo deleteMinItem(Node node) {
		if(null == node.left) { 
			return new ReplaceNodeInfo(node.item, node.right); //대체 노드가 만약 오른쪽 서브트리를 가지고 있는 경우.
		}else {
			ReplaceNodeInfo rp = this.deleteMinItem(node.left);
			node.left = rp.node; //=> 대체노드의 부모의 left(원래 대체노드의 자리)에는 대체노드의 자식이 온다.
			rp.node = node; //node => 대체 노드의 부모노드.
			
			return rp;
		}
	}
	
	//대체 노드의 정보 객체.
	public class ReplaceNodeInfo {
		public int item;
		public Node node;
		public ReplaceNodeInfo(int item, Node node) {
			this.item = item;
			this.node = node;
		}
	}
	
	
	//************** 복습 ****************
	/*
	    ex)
	           10
	       5       15
             7	13    20
                 14  19    
	                17
	    ex) 15 삭제.
	   1) 15탐색.
	   2) 15의 오른쪽 한칸, 이후 왼쪽으로 이동하며 삭제할 노드 탐색. (재귀)
	   3) 17 발견. 17은 더이상 왼쪽 자식이 없으므로 오른쪽 자식과 함께 리턴.여기서는 null)
	   4) 19의 왼쪽에 17의 오른쪽 자식 붙임(여기서는 null), 이후 본인(19) 리턴.
	   5) 20의 왼쪽에 19붙임.(그대로임) 이후 본인(20) 리턴.
	   6) 15의 값 대체 -> 17, 이후 오른쪽에 20 붙임.(그대로임. 만약 대체노드가 20인 경우 null이 리턴될 것임.)
	                
	 */
	
	
	
	 /*
	 * 
	 * 대체할 노드를 찾아서 리턴하며, 대체할 노드의 자식노드를 리턴하여 대체노드의 부모와 연결하고
	 * 부모노드를 리턴하는 작업을 재귀적으로 수행. -> 이를 통해 노드 재구성.
	 * 
	 */
	public ReplaceNodeInfo getSmallest(Node node) {
		
		if(null == node.left) {
			return new ReplaceNodeInfo(node.item, node.right);
		}else {
			ReplaceNodeInfo rp = this.getSmallest(node.left);
			node.left = rp.node;
			rp.node = node;
			return rp;
		}	
	}
	//자식 노드의 유무에 따른 분기처리.
	public Node deleteNode2(Node node) {
		
		if(null == node.left && null == node.right) {
			return null;
		}else if(null == node.left) {
			return node.right;
		}else if(null == node.right) {
			return node.left;
		}else {
			ReplaceNodeInfo rp = this.getSmallest(node.right);
			node.item = rp.item;
			node.right = rp.node;
			return node;
		}
	}
	
	//재귀호출을 통한 삭제가 되도록 함.
	public void deleteItem2(Node node, int target) {
		
		if(null == node) {
			return;
		}
		if(target == node.item) {
			return;
		}else if(target < node.item) {
		     node.left = this.deleteNode2(node.left); 
		}else {
			node.right = this.deleteNode2(node.right);
		}
		
		
	}
	
	//최초 호출함수.
	public void delete3(int target) {
		this.deleteItem2(this.root, target);
	}
	
	
	
	


}
