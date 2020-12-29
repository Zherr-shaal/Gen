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

    public void setFitness(double fitness) {
        this.fitness = fitness;
    }
   

    public int[] getGenes() {
        return genes;
    }

    private Speciemen(int[] gen,double fit){
         genes=gen;
        fitness=fit;
    }
    @Override
    protected Object clone() {
        return new Speciemen(genes,fitness); 
    }
    private int[] genes;

    public double getFitness() {
        return fitness;
    }
    private double fitness;
    public Speciemen(int[] gen){

        genes=gen;
        fitness=StackMachine.countStack(gen);
    }
    @Override
    public String toString() {
        String temp= "Гены особи:{";
        for(int i:genes){
            temp+=String.valueOf(i)+",";
        }
        temp=temp.substring(0,temp.length()-1);
        temp+="} Критерий особи:"+fitness;
        return temp;
    }
    
}
