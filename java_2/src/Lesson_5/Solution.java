package Lesson_5;

public class Solution {
    public static void main(String[] args) {
        method1();
        method2();

    }

    public static void method1() {
        final int size = 10000000;
        final int h = size / 2;
        float[] arr = new float[size];
        for (int i = 0; i < arr.length; i++){
            arr[i] = 1;
        }
        long a = System.currentTimeMillis();
                for (int i = 0; i < arr.length; i++) {
            arr[i] = (float) (arr[i] * Math.sin(0.2f + i / 5) * Math.cos(0.2f + i / 5) * Math.cos(0.4f + i / 2));
        }
        System.out.println(System.currentTimeMillis() - a);
    }

    public static void method2() {
        final int size = 1000000;
        final int h = size / 2;
        float[] arr = new float[size];
        for (int i = 0; i < arr.length; i++) {
            arr[i] = 1;
        }
        long a = System.currentTimeMillis();
        float[] a1 = new float[h];
        float[] a2 = new float[h];
        System.arraycopy(arr, 0, a1, 0, h);
        System.arraycopy(arr, h, a2, 0, h);


        //Расчет первой части
        MyThread myThread1 = new MyThread(a1);
        myThread1.start();
        //расчет второй части
        MyThread myThread2 = new MyThread(a2,h);
        myThread2.start();

        //ждем завершение потоков
        try {
            myThread1.join();
            myThread2.join();
        }catch (Exception e){
            System.out.println("не закончены потоки");
        }

        System.arraycopy(a1, 0, arr, 0, h);
        System.arraycopy(a2, 0, arr, h, h);
        System.out.println(System.currentTimeMillis() - a);
    }


    static class MyThread extends Thread{
        float[] arr;
        int master;
        MyThread(float[] arr, int master){
            this.arr = arr;
            this.master = master;
        }
        MyThread(float[] arr){
            this.arr = arr;
            this.master = 0;
        }
        @Override
        public void run() {
            for (int i = master; i < (arr.length + master); i++) {
                arr[i-master] = (float) (arr[i-master] * Math.sin(0.2f + i / 5) * Math.cos(0.2f + i / 5) * Math.cos(0.4f + i / 2));
            }


        }
    }
}
