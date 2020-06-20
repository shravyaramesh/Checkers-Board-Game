import java.util.ArrayList;

public class Testing {
	
	static ArrayList<Integer> multilist=new ArrayList<Integer>();
	static int multiindex=0;
	
	/*//Finding numbers multiple hops away
	static void findMultipleHopsAwayElements(ArrayList<Integer> list, int x){

		if(list.size()>0) {
			
			for(int i=0;i<list.size();i++) {
				
				//Check if element is already present in multihop. then add.
				if((!search(list.get(i), multilist))&&list.get(i)!=x)
				{
					multilist.add(list.get(i));
					multiindex++;
				}
				
				else {
					continue;
				}
				
				//test if element added 
				for(int j=0;j<multilist.size();j++) {
					System.out.print(multilist.get(j)+"\t");
				}
				
				if(findOneHopAwayElements(list.get(i)).size()>0)
					findMultipleHopsAwayElements(findOneHopAwayElements(list.get(i)),x);
				else {
					System.out.println("no more");
					continue;
				}
			}
			
		}

	}*/
	
	static ArrayList<Integer> maxKilledList=new ArrayList<Integer>();
	static ArrayList<Integer> currentlyKilledList=new ArrayList<Integer>();
	static ArrayList<Integer> traversedList=new ArrayList<Integer>();
	static int maxKilledCount=0;
	static int currentlyKilledCount=0;
	
	//AIM: To accept x and y and print the elements to be killed, if the move is possible.
	/*static int findMultipleHopsAwayElements(ArrayList<Integer> list, int x, int y){

		if(list.size()>0) {
			
			for(int i=0;i<list.size();i++) {
				
				if(search(y,list)) {
					//Start adding common elements to currently killed list and increment currently killed count
					return list.get(i);
					
				}

				if(findOneHopAwayElements(list.get(i)).size()>0&&(!search(list.get(i),traversedList))) {
					
					//You are going forward so don't update.
					traversedList.add(list.get(i));
					int ele= findMultipleHopsAwayElements(findOneHopAwayElements(list.get(i)),x,y);
					//Now you're backtracking, so add the elements.
					currentlyKilledCount++;
					currentlyKilledList.add(getCommonSquare(ele,list.get(i)));
					if(currentlyKilledCount>maxKilledCount)
						maxKilledList=(ArrayList<Integer>)currentlyKilledList.clone();
					maxKilledCount=currentlyKilledCount;
				}
				else {
					System.out.println("no more");
					traversedList.clear();
					return list.get(i);
				}
				
			}
			
		}
		return -1;

	}*/
	
	
	static int getCommonSquare(int x,int y) {
		//Find if x and y are on even rows or odd rows.
				int a=isEven(x); int common=-1;
				
				//Generate the adjacent lists for x and y
				ArrayList<Integer> xlist=findAdjacentElements(x,a);
				ArrayList<Integer> ylist=findAdjacentElements(y,a);
				//Find the common adjacent element. This checker is probably the one you have to kill.
				int xsize=xlist.size();
				int ysize=ylist.size();
				if(xsize>=ysize) {
					for(int i=0;i<xsize;i++) {
						for(int j=0;j<ysize;j++) {
							if(xlist.get(i)==ylist.get(i)) {
								common=xlist.get(i);
							}
						}
					}
				}
				
				else {
					for(int i=0;i<ysize;i++) {
						for(int j=0;j<xsize;j++) {
							if(xlist.get(i)==ylist.get(i)) {
								common=xlist.get(i);
							}
						}
					}
				}
				return common;
	}
	
	static int isEven(int n){
		if((int)Math.ceil(n/4.0)%2==0)
			return -1;
		else
			return 1;
	}
	
	
	static ArrayList<Integer> findAdjacentElements(int x, int a){

		ArrayList<Integer> list=new ArrayList<Integer>();
		int m=x+a*4; 
		if(isValid(x,m,1))
		list.add(m);
		
		m=x-a*5;
		if(isValid(x,m,1))
		list.add(m);
		
		m=x+a*3;
		if(isValid(x,m,1))
		list.add(m);
		
		m=x-a*4;
		if(isValid(x,m,1))
		list.add(m);

		return list;
	}

		//Find possible layover element before making the multihop
		 static int find(ArrayList<Integer> list, int n) {
			 for(int i=0;i<list.size();i++) {
				 if(list.get(i)==n) {
					 return n;
				 }
			 }
			 return -1;
		 }
	
	static boolean search(int y, ArrayList<Integer> list) {
		boolean flag=false;
		for(int i=0;i<list.size();i++) {
			if(list.get(i)==y)
				flag=true;
		}
		return flag;
	}
	
	//Finding numbers multiple hops away. To display hints. Trace the path of the search. Keep a traversal list from x to y.
		static void findMultipleHopsAwayElements(ArrayList<Integer> list, int x,int y){
			
			//Contents of the list for every call 
			System.out.println("\n\nNew call");
			System.out.print("List: ");
			for(int i=0;i<list.size();i++) {
				System.out.print(list.get(i)+"\t");
			}
			System.out.println();
			if(list.size()>0) {
				System.out.print("\nEntered\n");
				
				for(int i=0;i<list.size();i++) {
					
					//System.out.println();
					
					//Check if element is already present in multihop. then add.
					if((!search(list.get(i), multilist))&&list.get(i)!=x)
					{
						multilist.add(list.get(i));
						multiindex++;
					}
					
					else {
						continue;
					}
					
					//test if element added 
					System.out.print("Multiple: ");
					for(int j=0;j<multilist.size();j++) {
						System.out.print(multilist.get(j)+"\t");
					}
					
					if(findOneHopAwayElements(list.get(i)).size()>0&&list.get(i)!=x) {
						findMultipleHopsAwayElements(findOneHopAwayElements(list.get(i)),x,y);
					
					}
					else {
						System.out.println("no more");
						continue;
					}
				}
				
			}
			
			//Printing traversal 
			System.out.println("Traversal: ");
			display(traversedList);

		}
		private static void display(ArrayList<Integer> arr) {
			for(int i=0;i<arr.size();i++) {
				System.out.print("\t"+arr.get(i));
			}
			System.out.println();
		}
	
	//Finding the elements a hop away. Returning a list of the necessary elements. Parameters: int x
		static ArrayList<Integer> findOneHopAwayElements(int x){

			ArrayList<Integer> list=new ArrayList<Integer>();
			int m=x+7; 
			if(isValid(x,m,2))
			list.add(m);
			
			m=x-7;
			if(isValid(x,m,2))
			list.add(m);
			
			m=x+9;
			if(isValid(x,m,2))
			list.add(m);
			
			m=x-9;
			if(isValid(x,m,2))
			list.add(m);

			return list;
		}
		
		/*Checking is m is within the range and not on the same line. Non static. 
		 *If validity if being checked for adjElements then 1 else 2 */
		static boolean isValid(int l, int m, int adOrHop) {
				boolean flag=true;
				if(adOrHop==1) {
					if(!isOnAdjacentRow(l,m)) {
						flag=false;
					}
				}
				
				if(adOrHop==2) {
					if(!isOneRowAway(l,m)) {
						flag=false;
					}
				}
				if(!isWithinRange(m))
					flag=false;
				return flag;
		}
		
		//Checking if m is a row is  either a row above or a row below x. Only then m would be adjacent to x. Nonstatic. 
		static boolean isOnAdjacentRow(int l,int m) {
			int lrow=(int)Math.ceil(l/4.0);
			int mrow=(int)Math.ceil(m/4.0);
			if(Math.abs(lrow-mrow)==1)
				return true;
			else
				return false;
		}
		
		//Checking if m is row away from x's row. Non static.
		static boolean isOneRowAway(int l, int m) {
			int lrow=(int)Math.ceil(l/4.0);
			int mrow=(int)Math.ceil(m/4.0);
			if(Math.abs(lrow-mrow)==2)
				return true;
			else
				return false;
		}
		
		//Checking if m is within the board range. Non static.
		static boolean isWithinRange(int m) {
			return m<=32&&m>=1;
		}
		
	public static void main(String args[]) {
		
		System.out.println("Hello");
		int x=30;
		
		ArrayList<Integer> arr=new ArrayList<Integer>();
		//arr=findOneHopAwayElements(x);
		arr.add(x);
		
		System.out.println("From main method");
		for(int i=0;i<arr.size();i++) {
			System.out.print(arr.get(i)+"\t");
		}
		
		System.out.println("wth");
		
		//System.exit(1);
		int y=5;
		findMultipleHopsAwayElements(arr,x,y);
		
		System.out.println("Displaying multiple elements list ");
		for(int i=0;i<multilist.size();i++) {
			System.out.print(multilist.get(i)+"\t");
		}
		
		System.out.println();
	}

}
