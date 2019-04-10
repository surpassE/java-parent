package com.sirding.thread;

import org.junit.Test;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.*;
import java.util.concurrent.locks.AbstractQueuedSynchronizer;

/**
 * TODO
 *
 * @author zc.ding
 * @create 2019/3/18
 */
public class TestAQS {

    AbstractQueuedSynchronizer abstractQueuedSynchronizer;
    
    
    @Test
    public void testSemaphore() throws Exception{
        final int threadCount = 550;
        ExecutorService executorService = Executors.newFixedThreadPool(300);
        final Semaphore semaphore = new Semaphore(20);
        for (int i = 0; i < threadCount; i++) {
            final int threadNum = i;
            executorService.execute(() -> {
                try{
                    semaphore.acquire();
                    Thread.sleep(1000);
                    System.out.println("threadNum:" + threadNum);
                    Thread.sleep(1000);
                    semaphore.release();
                }catch(Exception e){
                    e.printStackTrace();
                }
            });
            System.out.println("是否停止：" + executorService.isShutdown());
        }
        executorService.shutdown();
        System.out.println("finish.");
        Thread.sleep(30000);
    }


    @Test
    public void testCoundownLatch() throws Exception{
        final int threadCount = 550;
        ExecutorService executorService = Executors.newFixedThreadPool(300);
        final CountDownLatch countDownLatch = new CountDownLatch(threadCount);
        for (int i = 0; i < threadCount; i++) {
            final int threadNum = i;
            executorService.execute(() -> {
                try{
                    Thread.sleep(1000);
                    System.out.println("threadNum:" + threadNum);
                    Thread.sleep(1000);
                }catch(Exception e) {
                    e.printStackTrace();
                }finally {
                    countDownLatch.countDown();
                }
            });
        }
        countDownLatch.await();
        executorService.shutdown();
        System.out.println("finish.");
    }
    
    @Test
    public void testCyclicBarrier() throws Exception{
        final int threadCount = 50;
        final CyclicBarrier cyclicBarrier = new CyclicBarrier(5, () -> {
            System.out.println("我的优先级最高");
        });
        ExecutorService executorService = Executors.newFixedThreadPool(10);
        for (int i = 0; i < threadCount; i++) {
            final int threadNum = i;
            Thread.sleep(1000);
            executorService.execute(() -> {
                try{
                    System.out.println("threadNum:" + threadNum + " is ready.");
//                    cyclicBarrier.await(5000, TimeUnit.MILLISECONDS);
                    cyclicBarrier.await();
                    System.out.println("threadNum:" + threadNum + " is finish.");
                }catch(Exception e){
                    e.printStackTrace();
                }
            });
        }
        executorService.shutdown();
    }
    
    
    @Test
    public void test(){
        Integer state = null;
        System.out.println(state > 0);
    }
    
    @Test
    public void testTP(){
        ExecutorService executorService = Executors.newFixedThreadPool(10);
        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(10, 10,
                0L, TimeUnit.MILLISECONDS,
                new LinkedBlockingQueue<Runnable>());
        threadPoolExecutor.submit(() -> {});

        System.out.println(threadPoolExecutor.getActiveCount());

        System.out.println(Runtime.getRuntime().availableProcessors());
        
        executorService = Executors.newCachedThreadPool();
        executorService = Executors.newSingleThreadExecutor();
        executorService = Executors.newScheduledThreadPool(10);
        executorService = Executors.newWorkStealingPool();


        Map<String, String> map = new HashMap<>();
        map.put("", "");
                
    }
}
