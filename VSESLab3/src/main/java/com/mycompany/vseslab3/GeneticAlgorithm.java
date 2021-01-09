/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.vseslab3;

import java.util.ArrayList;
import java.util.Random;

/**
 *
 * @author Matt
 */
public class GeneticAlgorithm {
    private String log="";
    private int iterNum;        //итераций
    private int crossNum;       //скрещивается
    private int n;              //особей в популяции
    private final int min=0;    //нижняя граница
    private int max;            //верхняя граница
    private boolean smin;       //решаем на минимум
    private boolean smax;       //решаем на максисмум
    private double Pm;          //вероятность мутации
    private boolean red;        //уменьшать?
    private boolean delInf;     //без бесконечного критерия

    private Speciemen[] population;
    private int getMaxBinaryLength(){
        String temp=Integer.toBinaryString(max);
        return temp.length();
    }
    private String PadLeft(String str, int length, char symbol){
        String add="";
        for(int i=0;i<length-str.length();i++){
            add+=symbol;
        }
        return add+str;
    }
    public GeneticAlgorithm(int iterNum, int crossNum, int n, int max, boolean smin, boolean smax, double Pm, boolean red, boolean delInf) {
        this.iterNum = iterNum;
        this.crossNum = crossNum;
        this.max = max;
        this.n = n;
        this.smin = smin;
        this.smax = smax;
        this.Pm = Pm;
        this.red = red;
        this.delInf = delInf;
    }
    public String Run(){
        log="";
        if(smin){ 
            log+="РЕШЕНИЕ НА МИНИМУМ\n"+SolveMin()+"\n-------------------------------------------------------------------------------------------\n";
            log+="НАИБОЛЕЕ ПРИСПОСОБЛЕННАЯ ОСОБЬ\n"+population[0].toString()+"\n-------------------------------------------------------------------------------------------\n";
        }
        if(smax){
            log+="РЕШЕНИЕ НА МАКСИМУМ\n"+SolveMax()+"\n-------------------------------------------------------------------------------------------\n";
            log+="НАИБОЛЕЕ ПРИСПОСОБЛЕННАЯ ОСОБЬ\n"+population[0].toString()+"\n-------------------------------------------------------------------------------------------\n\n";
        }
        return log;
    }
    private Speciemen GetNotInfSpecimen(Speciemen[] popul){
        for(int j=0;j<popul.length;j++){
            if (!Double.isInfinite(popul[j].getFitness())){
                return popul[j];
            }
        }
        return null;
    }
    private  Speciemen[] GetStartPopulatonExcInf(){
        Speciemen[] temp_spec_arr = null;
        Speciemen temp_spec = null;
        do {
            temp_spec_arr = getStartPopulation();
            temp_spec = GetNotInfSpecimen(temp_spec_arr);
        } while (temp_spec == null);

        for(int j=0;j<temp_spec_arr.length;j++){
            if (Double.isInfinite(temp_spec_arr[j].getFitness())){
                temp_spec_arr[j] = temp_spec;
            }
        }
        return  temp_spec_arr;
    }

    private String SolveMin(){

        if (delInf)
            population = GetStartPopulatonExcInf();
        else
            population=getStartPopulation();

        String templog="";

        Speciemen[] crossing=new Speciemen[crossNum];

        for(int i=0;i<iterNum;i++){
            templog+="----| Итерация № "+(i+1)+" Вероятность мутации: "+Pm+" |----\n";


            templog+="Популяция:\n";

            int h =0;
            if (i != 0)
                for (int j = population.length - crossNum; j < population.length; j++){
                    population[j] = crossing[h];
                    h++;
                }

            population=sortPopulation(population, false);
            for(int j=0;j<population.length;j++){
                templog+=j+1 + " " + population[j].toString()+"\n";
            }
            

            for(int j=0;j<crossNum;j++){
                crossing[j]=population[j];
            }
            templog+="\nСкрещиваются:\n";
            for(int j=0;j<crossing.length;j++){
                templog+=j + 1 + " " + crossing[j].toString()+"\n";
            }
            Speciemen[] newSpeciemen=CrossAndMutate(crossing);
            newSpeciemen=sortPopulation(newSpeciemen, false);
            templog+="\nНовые особи:\n";

            for(int j=0;j<newSpeciemen.length;j++){
                templog+= j + 1 + " " + newSpeciemen[j].toString();

                if (delInf && Double.isInfinite(newSpeciemen[j].getFitness())){
                    templog+= " : особь удалёна (бесконечный критерий)";
                    newSpeciemen[j] = crossing[crossNum-1];
                }

                templog+="\n";
            }

            if(newSpeciemen.length<n){
                for(int j=0;j<n-newSpeciemen.length;j++){
                    population[newSpeciemen.length+j]=population[j];
                }
            }
            for(int j=0;j<newSpeciemen.length&&j<population.length;j++){
                population[j]=newSpeciemen[j];
            }
            if(red) Pm-=0.01;
            templog+="\n";
        }
        return templog;
    }

     private String SolveMax(){

         if (delInf)
             population = GetStartPopulatonExcInf();
         else
             population=getStartPopulation();

        String templog="";

         Speciemen[] crossing=new Speciemen[crossNum];

        for(int i=0;i<iterNum;i++){
            templog+="----| Итерация № "+(i+1)+" Вероятность мутации: "+Pm+" |----\n";
            
           

            templog+="Популяция:\n";

            int h =0;
            if (i != 0)
            for (int j = population.length - crossNum; j < population.length; j++){
                population[j] = crossing[h];
                h++;
            }
            population=sortPopulation(population, true);
            for(int j=0;j<population.length;j++){
                templog+=j+1 + " " + population[j].toString()+"\n";
            }
            


            for(int j=0;j<crossNum;j++){
                crossing[j]=population[j];
            }
            templog+="\nСкрещиваются:\n";
            for(int j=0;j<crossing.length;j++){
                templog+=j + 1 + " " + crossing[j].toString()+"\n";
            }
            Speciemen[] newSpeciemen=CrossAndMutate(crossing);
            newSpeciemen=sortPopulation(newSpeciemen, true);
            templog+="\nНовые особи:\n";
            for(int j=0;j<newSpeciemen.length;j++){
                templog+= j + 1 + " " + newSpeciemen[j].toString();

                if (delInf && Double.isInfinite(newSpeciemen[j].getFitness())){
                    templog+= " : особь удалёна (бесконечный критерий)";
                    newSpeciemen[j] = crossing[crossNum-1];
                }

                templog+="\n";
            }
            if(newSpeciemen.length<n){
                for(int j=0;j<n-newSpeciemen.length;j++){
                    population[newSpeciemen.length+j]=population[j];
                }
            }
            for(int j=0;j<newSpeciemen.length&&j<population.length;j++){
                population[j]=newSpeciemen[j];
            }
            if(red) Pm-=0.01;
            templog+="\n";
        }
        return templog;
    }
     private Speciemen[] getStartPopulation(){
         Random rand=new Random(System.currentTimeMillis());
         Speciemen [] start=new Speciemen[n];
         for(int i=0;i<n;i++){
             int[] temp=new int[PostfixTransform.getVariables()];
             for(int j=0;j<PostfixTransform.getVariables();j++){
                 temp[j]=rand.nextInt(max);
             }
             start[i]=new Speciemen(temp);
         }
         return start;
     }
     private Speciemen[] CrossAndMutate(Speciemen[] crossing){
            ArrayList<Speciemen> newSpeciemen=new ArrayList<>();

            Random random = new Random(System.currentTimeMillis());
            int vars=PostfixTransform.getVariables();
            int max_binary_length=getMaxBinaryLength();
            for (int i = 0; i < crossNum; ++i)
            {
                int[] gens_i=population[i].getGenes();
                for (int j = i; j < crossNum; ++j)
                {
                    int[] gens_j=population[j].getGenes();
                    int[] newSpeciemen1 = new int[vars];

                    int[] newSpeciemen2 = new int[vars];

                    for (int k = 0; k < vars; ++k)
                    {
                        int z = random.nextInt(max_binary_length)-1;
                        if(z<=0) z+=2;

                        String gens1 =
                           PadLeft(Integer.toBinaryString(gens_i[k]),max_binary_length, '0').substring(0, z)
                            +
                            PadLeft(Integer.toBinaryString(gens_j[k]),max_binary_length, '0').substring(z);
                        String gens2 =
                            PadLeft(Integer.toBinaryString(gens_j[k]),max_binary_length, '0').substring(0, z)
                            +
                            PadLeft(Integer.toBinaryString(gens_i[k]),max_binary_length, '0').substring(z);

                        for (int m = 0; m < gens1.length(); m++)
                        {
                            if (random.nextDouble() <= Pm)
                            {
                                if (gens1.charAt(m) == '1')
                                {
                                    gens1 = gens1.substring(0, m)+'0'+gens1.substring(m+1, gens1.length());
                                }
                                else 
                                {
                                    gens1 = gens1.substring(0, m)+'1'+gens1.substring(m+1, gens1.length());
                                }
                            }
                            if (random.nextDouble() <= Pm)
                            {
                                if (gens2.charAt(m) == '1')
                                {
                                    gens2 = gens2.substring(0, m)+'0'+gens2.substring(m+1, gens2.length());
                                }
                                else
                                {
                                    gens2 = gens2.substring(0, m)+'1'+gens2.substring(m+1, gens2.length());
                                }
                            }
                        }

                        newSpeciemen1[k] = Integer.parseInt(gens1, 2);

                        newSpeciemen2[k] = Integer.parseInt(gens2, 2);

                    }
                    newSpeciemen.add(new Speciemen(newSpeciemen1));
                    newSpeciemen.add(new Speciemen(newSpeciemen2));
                    

                }
            }

            Speciemen[] temp=new Speciemen[newSpeciemen.size()];
            newSpeciemen.toArray(temp);
            return temp;
     }
    private Speciemen[] sortPopulation(Speciemen[] popul, Boolean maxToMin){
        if (maxToMin)
            for(int i=0;i<popul.length;i++){
                for(int j=i+1;j<popul.length;j++){
                    if(popul[i].getFitness()<popul[j].getFitness()){
                        Speciemen a=(Speciemen)popul[i].clone();
                        popul[i]=popul[j];
                        popul[j]=a;
                    }
                }
            }
        else
            for(int i=0;i<popul.length;i++){
                for(int j=i+1;j<popul.length;j++){
                    if(popul[i].getFitness()>popul[j].getFitness()){
                        Speciemen a=(Speciemen)popul[i].clone();
                        popul[i]=popul[j];
                        popul[j]=a;
                    }
                }
            }
        return popul;
        
    }
    
}
