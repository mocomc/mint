/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pep.per.mint.batch.job;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * 배치 Lock 샘플
 * 2018.04
 * GSSP(GS스마트 포스에서 배치가 늦게 돌아 이후 하나의 쓰레드가 추가 실행되어 동시에 두개의 배치가 실행됨.
 * 이를 막기위한 테스트 소스 작성중 일정 문제로 프링징 해 둠.
 * 나중에 개발하도록 하자.
 * @author whoana
 */
public class JobControl {
    
    public static Lock control = new ReentrantLock();
    
    public JobControl(long sleep) throws InterruptedException{
         
        if(control.tryLock()){
            try{
                System.out.println(Thread.currentThread().getName() + " Lock 얻음.");
                Thread.sleep(sleep);
            }finally{ 
               control.unlock();
            }
        }else{
            System.out.println(Thread.currentThread().getName() + " Lock을 얻지 못해 종료함 .");
        }
          
    }
    
    static class Job implements Runnable{

        @Override
        public void run() {
            try {
                new JobControl(10000);
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }
        }
        
    } 
    
    public static void main(String[]args){
        
       
        try {
            
            
            new Thread(new Job()).start();
            Thread.sleep(5000);
            new Thread(new Job()).start();
            Thread.sleep(5000);
            new Thread(new Job()).start();
            Thread.sleep(5000);
            new Thread(new Job()).start();
            Thread.sleep(5000);
            new Thread(new Job()).start();
            Thread.sleep(5000);
            
            
        } catch (InterruptedException ex) {
            Logger.getLogger(JobControl.class.getName()).log(Level.SEVERE, null, ex);
        }

        
        
    }
    
}
