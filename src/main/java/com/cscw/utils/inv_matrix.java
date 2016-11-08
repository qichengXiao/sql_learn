package com.cscw.utils;
import   java.io.*;
import java.util.Scanner;
import   java.util.StringTokenizer;

import org.junit.Test;
 
public   class   inv_matrix   {
    private static float[][] get_complement(float[][] data, int i, int j) {
 
        /* x和y为矩阵data的行数和列数 */
        int x = data.length;
        int y = data[0].length;
 
        /* data2为所求剩余矩阵 */
        float data2[][] = new float[x - 1][y - 1];
        for (int k = 0; k < x - 1; k++) {
            if (k < i) {
                for (int kk = 0; kk < y - 1; kk++) {
                    if (kk < j) {
                        data2[k][kk] = data[k][kk];
                    } else {
                        data2[k][kk] = data[k][kk + 1];
                    }
                }
 
            } else {
                for (int kk = 0; kk < y - 1; kk++) {
                    if (kk < j) {
                        data2[k][kk] = data[k + 1][kk];
                    } else {
                        data2[k][kk] = data[k + 1][kk + 1];
                    }
                }
            }
        }
        return data2;
 
    }
 
    /* 计算矩阵行列式 */
    private static float cal_det(float[][] data) {
        float ans=0;
        /*若为2*2的矩阵可直接求值并返回*/
        if(data[0].length==2){
             ans=data[0][0]*data[1][1]-data[0][1]*data[1][0];
        }
        else{
            for(int i=0;i<data[0].length;i++){
                /*若矩阵不为2*2那么需求出矩阵第一行代数余子式的和*/
                float[][] data_temp=get_complement(data, 0, i);
                if(i%2==0){
                    /*递归*/
                    ans=ans+data[0][i]*cal_det(data_temp);
                }
                else{
                    ans=ans-data[0][i]*cal_det(data_temp);
                }
            }
        }
        return ans;
 
    }
     
    /*计算矩阵的伴随矩阵*/
    private static float[][] ajoint(float[][] data) {
        int M=data.length;
        int N=data[0].length;
        float data2[][]=new float[M][N];
        for(int i=0;i<M;i++){
            for(int j=0;j<N;j++){
            if((i+j)%2==0){
                data2[i][j]=cal_det(get_complement(data, i, j));
            }
            else{
                data2[i][j]=-cal_det(get_complement(data, i, j));
            }
            }
        }
         
        return trans(data2);
         
 
    }
     
    /*转置矩阵*/
    private static float [][]trans(float[][] data){
        int i=data.length;
        int j=data[0].length;
        float[][] data2=new float[j][i];
        for(int k2=0;k2<j;k2++){
            for(int k1=0;k1<i;k1++){
                data2[k2][k1]=data[k1][k2];
            }
        }
         
        /*将矩阵转置便可得到伴随矩阵*/
        return data2;
         
    }
     
     
     
    /*求矩阵的逆，输入参数为原矩阵*/
    private static float[][] inv(float [][] data){
        int M=data.length;
        int N=data[0].length;
        float data2[][]=new float[M][N];
        float det_val=cal_det(data);
        data2=ajoint(data);
        for(int i=0;i<M;i++){
            for(int j=0;j<N;j++){
                data2[i][j]=data2[i][j]/det_val;
            }
        }
         
        return data2;
    }
     
     
     
    public static void main(String args[]){
         
        /* 输入原矩阵矩阵 */
        Scanner scan = new Scanner(System.in);
        System.out.println("输入矩阵的行数和列数");
        int x = scan.nextInt();
        int y = scan.nextInt();
        float data[][] = new float[x][y];
        System.out.println("输入矩阵");
        for (int k = 0; k < x; k++) {
            for (int kk = 0; kk < y; kk++) {
                data[k][kk] = scan.nextFloat();
            }
        }
         
        /*输出原矩阵的伴随*/
        System.out.println("原矩阵的伴随矩阵为：");
        float data2[][]=new float[x][y];
        data2=ajoint(data);
        for(int i=0;i<x;i++)
        {
            for(int j=0;j<y;j++){
                System.out.print(data2[i][j]+" ");
            }
            System.out.println();
        }
         
        /*输出原矩阵的行列式*/
        System.out.println("原矩阵行列式的值为：   "+cal_det(data));
         
        /*输出原矩阵的逆矩阵*/
        float data3[][]=new float[x][y];
        System.out.println("该矩阵的逆矩阵为：");
        data3=inv(data);
        for(int i=0;i<x;i++)
        {
            for(int j=0;j<y;j++){
                System.out.print(data3[i][j]+" ");
            }
            System.out.println();
        }
 
    }
    @Test
    public void test(){
    	float i = (float) (0.3333*13-0.2843*18-0.3195*11);
    	System.out.println((i+26)%26);
    }
}