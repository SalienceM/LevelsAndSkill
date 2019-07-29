package com.m31.minecraft.forge.levelsandskill.utils;

import java.util.ArrayList;
import java.util.List;

public class Splitor {

    public static List<List<String>> subListByCount(int targetCount,String[] seq){
        List<List<String>> totalList=new ArrayList<>();
        int k=0;
        int topIndex=-1;
       List<String> innerStrList=new ArrayList<String>();
        for(int i=k;i<seq.length;i++){
            if(topIndex==i)continue;
            innerStrList.add(seq[i]);
            if(innerStrList.size()==targetCount){
                totalList.add(innerStrList);
                innerStrList=new ArrayList<String>();
                topIndex=i;
                if(topIndex==seq.length-1){
                    i=k++;

                }else{
                    i=k-1;
                }
            }
        }
        return totalList;
    }

    public static List<List<String>> subListByCountV2(int targetCount,String[] seq){
        List<List<String>> totalList=new ArrayList<>();
        List<String> innerStrList=new ArrayList<String>();
        int k=0;
        int a=k+1;
        for(int i=k;i<seq.length;i++){
            if(i==seq.length-1&& a==i)break;
            if(seq.length==a)break;
            innerStrList.add(seq[i]);
            if(innerStrList.size()==targetCount){
                totalList.add(innerStrList);
                innerStrList=new ArrayList<>();
                continue;
            }
            for(int j=a;j<seq.length;j++){
                innerStrList.add(seq[j]);
                if(innerStrList.size()==targetCount){
                    totalList.add(innerStrList);
                    innerStrList=new ArrayList<>();
                    if(j==seq.length-1){
                        a=i+2;
                        break;
                    }else{
                        i--;
                        a++;
                        break;
                    }
                }
            }
        }
        return totalList;
    }


    public static void main(String[] args) {
        String[] strs={"1","2","3","4","5"};
        System.out.println(subListByCountV2(3,strs));
    }


}
