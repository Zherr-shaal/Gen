/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.vseslab3;

/**
 *
 * @author Matt
 */
public class Speciemen {
    private int number;

    @Override
    protected Object clone() {
        return new Speciemen(number,genes); 
    }
    private int[] genes;

    public double getFitness() {
        return fitness;
    }
    private double fitness;
    public Speciemen(int num,int[] gen){
        number=num;
        genes=gen;
        fitness=StackMachine.countStack(gen);
    }
    @Override
    public String toString() {
        String temp= "Особь №"+number+"Гены:{";
        for(int i:genes){
            temp+=String.valueOf(i)+",";
        }
        temp=temp.substring(0,temp.length()-1);
        temp+="} Критерий особи:"+fitness;
        return temp;
    }
    
}
