package com.m31.minecraft.forge.levelsandskill.utils;

import scala.actors.threadpool.Arrays;

import java.util.ArrayList;
import java.util.List;

public class Splitor {


    /**非重复顺序排列组合算法
     *
     * @param slot  位数
     * @param args  当前
     * @return
     */
    public static List<String> getCombies(int slot,String[] args,String[] conflicts){
        if (null==args)return new ArrayList<>();
        //获取全排列
        List<String> list=new ArrayList<>();
        for(int slotIndex=0;slotIndex<slot;slotIndex++){
            if(list.isEmpty()){
                for(int x=0;x<args.length;x++){
                    list.add(String.valueOf(x));
                }
            }else{
                List<String> temp=new ArrayList<>();
                for( int index=0;index<list.size();index++){
                    for(int seq=0;seq<args.length;seq++){
                        temp.add(String.format("%s,%s",list.get(index),String.valueOf(seq)));
                    }
                }
                list=temp;
            }
        }
        List<String> afterFilter=new ArrayList<>();
        //筛选
        for(String index:list){
            List<String> caches=new ArrayList<>();
            String[] attrs=index.split(",");
            long last=-1L;
            boolean skip=false;
            for(String strtemp:attrs){
                if(caches.contains(strtemp)||//剔重
                        Long.valueOf(strtemp)<last){//剔逆
                    skip=true;
                    break;
                }else{
                    caches.add(strtemp);
                    last=Long.valueOf(strtemp);
                }
            }
            if(!skip){
                String indexArgs=null;//result
                List<String> indexArgsList=new ArrayList<>();
                for(String attr:attrs){
                    if(null==indexArgs){
                        indexArgs=args[Math.toIntExact(Long.valueOf(attr))];
                        indexArgsList.add(indexArgs);
                        continue;
                    }
                    indexArgs=String.format("%s,%s",indexArgs,args[Math.toIntExact(Long.valueOf(attr))]);
                    indexArgsList.add(args[Math.toIntExact(Long.valueOf(attr))]);
                }
                boolean isNeedSkipByConflict=false;
                if(null!=conflicts)
                for(String single:conflicts){
                    String[] subInners=single.split(",");
                     List<String> strings=new ArrayList<>(Arrays.asList(subInners));
                     if(indexArgsList.containsAll(strings)){
                         isNeedSkipByConflict=true;
                         break;
                     }
                }
                if(!isNeedSkipByConflict)afterFilter.add(indexArgs);
            }
        }
        return afterFilter;
    }





    public static void main(String[] args) {
        int max=5;
        List<String> str=new ArrayList<>();
        for(int i=1;i<max;i++){
            str.add(i+"");
        }
        long time=System.currentTimeMillis();
        String[] seq={"1,2","1,5"};
        System.out.println(getCombies(3, str.toArray(new String[str.size()]),seq));
        System.out.println(System.currentTimeMillis()-time);
    }


}
