/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.vseslab3;

import static java.lang.Math.*;
import java.util.Stack;

/**
 *
 * @author Matt
 */
public class StackMachine {
    private static StackMachine machine;
    private static Stack<String> initialStack;
    private static int variables;
    private StackMachine(Stack<String> init){
        initialStack=init;
    }    
    public static StackMachine getInstance(Stack<String> init){
        machine=new StackMachine(init);
        return machine;
    }
    private static final String operators="+-*/^";
    private static final String[] functions={"sin","cos","tg","ctg","sqrt"};
    private static final String variable="xyzabcdefghigklmnopqrstuvw";
    private static double operation(String token1, String token2, String operator){
        if(operator.charAt(0)==operators.charAt(0)) return Double.parseDouble(token1)+ Double.parseDouble(token2);
        if(operator.charAt(0)==operators.charAt(1)) return  Double.parseDouble(token2)- Double.parseDouble(token1);
        if(operator.charAt(0)==operators.charAt(2)) return  Double.parseDouble(token1)* Double.parseDouble(token2);
        if(operator.charAt(0)==operators.charAt(3)) return Double.parseDouble(token2)/Double.parseDouble(token1);
        /*
        {
            if (Double.parseDouble(token1) == 0)
                    return Double.parseDouble(token2)/Double.parseDouble(token1);
                else
                    return  Double.parseDouble(token2)/Double.parseDouble(token1);
        }
        */
        if(operator.charAt(0)==operators.charAt(4)) return (int)pow(Double.parseDouble(token2), Double.parseDouble(token1));
        return -1;
    }
    private static double countFunction(String func,String arg){
        if(func==functions[0]) return sin(Double.parseDouble(arg));
        if(func==functions[1]) return cos(Double.parseDouble(arg));
        if(func==functions[2]) return tan(Double.parseDouble(arg));
        if(func==functions[3]) return 1/tan(Double.parseDouble(arg));
        if(func==functions[4]) return sqrt(Double.parseDouble(arg));
        return 0;
    }
    private static boolean isNumber(String token) {
	try {
            Double.parseDouble(token);
	} catch (Exception e) {
            return false;
	}
	return true;
    }
    private static boolean isVariable(String token){
            return variable.contains(token);
    }
    private static boolean isFunction(String token) {
	for (String item : functions) {
            if (item.equals(token)) {
		return true;
            }
	}
	return false;
    }
    private static boolean isOperator(String token) {
	return operators.contains(token);
    }
    public double countStack(Stack<String> stack){
        Stack<String> temp=new Stack<>();
        while(!stack.empty()){
            if(isNumber(stack.lastElement())) temp.push(stack.pop());
            if(isOperator(stack.lastElement())) temp.push(String.valueOf(operation(temp.pop(),temp.pop(),stack.pop())));
            
        }
        return Double.parseDouble(temp.pop());
    }
    public double countStack(Stack<String> stack, int[] args){
        Stack<String> temp=new Stack<>();
        while(!stack.empty()){
            if(isNumber(stack.lastElement())) temp.push(stack.pop());
            if(isVariable(stack.lastElement())) temp.push(String.valueOf(args[variable.indexOf(stack.pop())]));
            if(isOperator(stack.lastElement())) temp.push(String.valueOf(operation(temp.pop(),temp.pop(),stack.pop())));
            if(isFunction(stack.lastElement())) temp.push(String.valueOf(countFunction(stack.pop(),stack.pop())));
        }
        return Double.parseDouble(temp.pop());
    }
    public static double countStack(int[] args){
        Stack<String> temp=new Stack<>();
        Stack<String> stack=(Stack<String>)initialStack.clone();
        while(!stack.empty()){
            if(isNumber(stack.lastElement())) temp.push(stack.pop());
            if(isVariable(stack.lastElement())) temp.push(String.valueOf(args[variable.indexOf(stack.pop())]));
            if(isOperator(stack.lastElement())) temp.push(String.valueOf(operation(temp.pop(),temp.pop(),stack.pop())));
            if(!stack.isEmpty()) {if(isFunction(stack.lastElement())) temp.push(String.valueOf(countFunction(stack.pop(),stack.pop())));}
        }
        return Double.parseDouble(temp.pop());
    }
    
}
