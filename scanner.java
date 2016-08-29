import java.io.*;
import java.util.*;

class Error extends Exception{
	String msg;
	Error(String msg){
		this.msg=msg;//this is pointer to current object
		//so msg string are diffrentiate
	}
	void printError(){
		System.out.println("Error: "+msg);
	}
}
class MyScanner{
	List<String> input = new ArrayList<String>();
	Stack<String> stk=new Stack<String>();
		String keywords[]={"<define>","</define>","<input>","</input>","<state>","</state>","<accept>","</accept>","<dump>","</dump>","<FATable>","</FATable>"};
		
		MyScanner(){
			getInput();
		}
	void getInput(){
		
        Scanner s = null;
 
        try {
            s = new Scanner(new BufferedReader(new FileReader("input.txt")));
 
            while (s.hasNext()) {
                input.add(s.next());
			}
        } catch(FileNotFoundException fnfe) { 
            System.out.println(fnfe.getMessage());
        } finally {
            if (s != null) {
                s.close();
            }
        }
		
		scanInput();
		
		//System.out.println(" the languge elements are: ");
		//System.out.print(input);
	}
	void handleKeyword(String token) throws Error{
		if(stk.search(token)==-1){//keyword not available in stack
				if(stk.empty()){
					if(token.equals(keywords[0])){
						stk.push(token);
					}else{
						throw new Error("first keyword must be <define>");
					}
				}else if(token.equals(keywords[1]) & stk.peek().equals(keywords[0])){
						stk.pop();
					}
				
				
				//checkin all opening and closing keyword with maintained stack;
				for(int i=2;i<keywords.length;i+=2){
				//opening of keyword
				
					if(token.equals(keywords[i])){
						//System.out.println(token+" = "+keyword[i]);
						if(!stk.empty()){
							stk.push(token);
							break;
						}else{
							throw new Error("\nunstable scaning something wrong in program of java\nthe "+token+" keyword is used without use container <define>\n first keyword must be <define>");
						}
					}
				//closing of keyword
					else if(token.equals(keywords[i+1]) ){
					//System.out.println(token+" = "+keyword[i+1]);
						if(stk.peek().equals(keywords[i])){
						stk.pop();
						break;
						}else{
							throw new Error("please first close "+stk.peek()+" before close "+token);
						}
						
					}
				}
				
			}
			else{//the keyword already avsailable in stack
				throw new Error("you can not create same block twice\nyou are trying to create another block with "+token);
			}
			
	}
	void handleValue(String token) throws Error{
		//checking which tag was last opened
		if(stk.peek().equals(keywords[0])){//<define>
			throw new Error("value can not be valid after <define> you must use <state> or <input> tag");
		}else if(stk.peek().equals(keywords[2])){//<input> now declaring input variable
		
		}else if(stk.peek().equals(keywords[4])){//<state> now declaring state variable
		
		
		}else if(stk.peek().equals(keywords[6])){//<accept> now declaring input variable
		
		
		}else if(stk.peek().equals(keywords[8])){//<dump> now declaring input variable
		
		
		}else if(stk.peek().equals(keywords[10])){//<FATable> now declaring input variable
		
		}
	}
	void scanInput(){
		
		
		for(String token:input){
			if(token.charAt(0)=='<'){//that means the item is keyword
				try{
					handleKeyword(token);
				}catch(Error e){
				e.printError();
				System.exit(0);
				}
			}else {
				try{
					handleValue(token);
				}catch(Error e){
					e.printError();
					System.exit(0);
				}
			}
			//System.out.print(token+" ");
				
		}
	}
	
}
class Main{
	public static void main(String s[]){
		MyScanner m= new MyScanner();
	}
}