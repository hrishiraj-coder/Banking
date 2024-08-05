package com.apigateway;

public class GrumpyOwner {
    public static void main(String[] args) {

       /* int[] customers = {1, 0, 1, 2, 1, 1, 7, 5};
        int[] grumpy = {0, 1, 0, 1, 0, 1, 0, 1};
        int minutes = 3;   ans:16
*/
        int[] customers = {4, 10, 10};
        int[] grumpy = {1, 1, 0};
        int minutes = 2; // ans:24

        System.out.println(noOfSatisfied(customers, grumpy, minutes));
    }

    public static int noOfSatisfied(int[] customers, int[] grumpy, int min) {

        int storeOpenTime = customers.length;

        int satisfied = 0;


        for (int i = 0; i < customers.length; i++) {
            if (grumpy[i] == 0) {
                satisfied += customers[i];
            }
        }
        int extraSatisfied =0;
        for (int i = 0; i < customers.length; i++) {
            if (i >= grumpy.length - min) {
                extraSatisfied += customers[i];
            }
        }

        int maxES=extraSatisfied;

        for(int i=min;i< customers.length;i++){
            if(grumpy[i]==1){
                extraSatisfied+=customers[i];
            }
            if(grumpy[i-min]==1){
                extraSatisfied-=customers[i-min];
            }

            maxES=Math.max(maxES,extraSatisfied);
        }


        return satisfied+maxES;


    }
}
