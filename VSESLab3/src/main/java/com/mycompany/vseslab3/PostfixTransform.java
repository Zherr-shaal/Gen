/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.vseslab3;

import java.util.Collections;
import java.util.Stack;
import java.util.StringTokenizer;

/**
 *
 * @author Matt
 */
public class PostfixTransform {
    private static PostfixTransform parser;
    private static int vars;
    private PostfixTransform(){
        
    }
    public static PostfixTransform getInstance(){
        if(parser==null) parser=new PostfixTransform();
        return parser;
    }
    public static int getVariables(){
        return vars;
    }
    private final String operators="+-*/^";
    private final String variable="xyzabcdefghigklmnopqrstuvw";
    private final String separator=",";
    private final String[] functions={"sin","cos","tg","ctg","sqrt"};
    private Stack<String> stackOperations = new Stack<String>();
    private Stack<String> stackRevPolNot = new Stack<String>();
    private Stack<String> result2 = new Stack<String>();
    private boolean isNumber(String token) {
		try {
			Double.parseDouble(token);
		} catch (Exception e) {
			return false;
		}
		return true;
	}

	private boolean isFunction(String token) {
		for (String item : functions) {
			if (item.equals(token)) {
				return true;
			}
		}
		return false;
	}
        private boolean isVariable(String token){
            return variable.contains(token);
        }
	private boolean isSeparator(String token) {
		return token.equals(separator);
	}

	private boolean isOpenBracket(String token) {
		return token.equals("(");
	}

	private boolean isCloseBracket(String token) {
		return token.equals(")");
	}

	private boolean isOperator(String token) {
		return operators.contains(token);
	}

	private byte getPrecedence(String token) {
		if (token.equals("+") || token.equals("-")) {
			return 1;
		}
		return 2;
	}
        private void clear_stacks(){
            stackOperations.clear();
            stackRevPolNot.clear();
            result2.clear();
        }
        public void parse(String expression){
            clear_stacks();
            vars=0;
            expression = expression.replace(" ", "").replace("(-", "(0-")
				.replace(",-", ",0-");
            if(expression.charAt(0)=='-') expression="0"+expression;
            StringTokenizer tokenizer=new StringTokenizer(expression,operators+separator+variable+"()",true);
            while(tokenizer.hasMoreTokens()){
                String token=tokenizer.nextToken();
                if(isSeparator(token)){
                    while(!stackOperations.empty()&&!isOpenBracket(stackOperations.lastElement())){
                        stackRevPolNot.push(stackOperations.pop());
                    }
                }
                else if(isOpenBracket(token)) stackOperations.push(token);
                else if(isCloseBracket(token)) {
                    while(!stackOperations.empty()&&!isOpenBracket(stackOperations.lastElement())){
                        stackRevPolNot.push(stackOperations.pop());
                    }
                    stackOperations.pop();
                    if(!stackOperations.empty()&& isFunction(stackOperations.lastElement())){
                        stackRevPolNot.push(stackOperations.pop());
                    }
                }
                else if(isNumber(token)){
                    stackRevPolNot.push(token);
                }
                else if(isVariable(token)){
                    stackRevPolNot.push(token);
                    vars++;
                }
                else if(isOperator(token)){
                    while(!stackOperations.empty()
                            &&isOperator(stackOperations.lastElement())
                            &&getPrecedence(token)<= getPrecedence(stackOperations.lastElement())){
                       stackRevPolNot.push(stackOperations.pop());
                    }
                    stackOperations.push(token);
                    
                }
                else if(isFunction(token)){
                    stackOperations.push(token);
                }
                
                
            }
            while(!stackOperations.isEmpty()){
                    stackRevPolNot.push(stackOperations.pop());
                }
                Collections.reverse(stackRevPolNot);
            
        }
        public String printPolish(){
            String res="";
            while(!stackRevPolNot.empty()) res+=stackRevPolNot.pop();
            return res;
        }
        public Stack<String> getStack(){
            return (Stack<String>)stackRevPolNot.clone();
        }
    
}
