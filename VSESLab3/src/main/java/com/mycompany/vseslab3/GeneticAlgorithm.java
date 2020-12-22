/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.vseslab3;

import java.util.Random;

/**
 *
 * @author Matt
 */
public class GeneticAlgorithm {
    private String log;
    private int iterNum;//итераций
    private int crossNum;//скрещивается
    private int parentNum;//родителей на след уровень
    private int n;//особей в популяции
    private int min;//нижняя граница
    private int max;//верхняя граница
    private boolean smin;//решаем на минимум
    private boolean smax;//решаем на максисмум
    private double Pm;//вероятность мутации
    private boolean red;//уменьшать?
    private Speciemen[] population;

    public GeneticAlgorithm(int iterNum, int crossNum, int parentNum, int n, int min, int max, boolean smin, boolean smax, double Pm, boolean red) {
        this.iterNum = iterNum;
        this.crossNum = crossNum;
        this.parentNum = parentNum;
        this.min = min;
        this.max = max;
        this.smin = smin;
        this.smax = smax;
        this.Pm = Pm;
        this.red=red;
    }
    public String Run(){
        log="";
        if(smin) log+=SolveMin();
        if(smax) log+=SolveMax();
        return log;
    }
    private String SolveMin(){
        return "";
    }
     private String SolveMax(){
        population=getStartPopulation();
        String templog="";
        for(int i=0;i<iterNum;i++){
            templog+="Итерация№"+(i+1)+" Вероятность мутации: "+Pm+"\n";
            population=sortPopulation(population);
            Speciemen[] newSpeciemen=CrossAndMutate();
        }
        return templog;
    }
     private Speciemen[] getStartPopulation(){
         Random rand=new Random(System.currentTimeMillis());
         Speciemen [] start=new Speciemen[n];
         for(int i=0;i<n;i++){
             int[] temp=new int[PostfixTransform.getVariables()];
             for(int j=0;j<PostfixTransform.getVariables();j++){
                 temp[j]=rand.nextInt()%31;
             }
             start[i]=new Speciemen(i,temp);
         }
         return start;
     }
     private Speciemen[] CrossAndMutate(){
         
     }
    private Speciemen[] sortPopulation(Speciemen[] popul){
        for(int i=0;i<popul.length;i++){
            for(int j=i+1;j<popul.length;j++){
                if(popul[i].getFitness()<popul[j].getFitness()){
                    Speciemen a=(Speciemen)popul[i].clone();
                    popul[i]=popul[j];
                    popul[j]=a;
                }
            }
        }
        return popul;
    }
    
}
