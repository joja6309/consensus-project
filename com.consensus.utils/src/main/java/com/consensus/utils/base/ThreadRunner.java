package com.consensus.utils.base;

public  class ThreadRunner {

    private ThreadRunner(){
    }
    public static  void runThread(Thread thread,String threadName){
        thread.run();

//        System.out.println(String.format("%s Thread::%s",threadName,thread.getState()));
//        System.out.println(String.format("Thread Name::%s",thread.getName()));
//        System.out.println(String.format("Thread ID::%s",thread.getId()));
//        System.out.println(String.format(thread.toString()));
    }
}
