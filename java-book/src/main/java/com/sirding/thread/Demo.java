package com.sirding.thread;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * TODO
 *
 * @author zc.ding
 * @create 2018/12/28
 */
public class Demo {


    public void testTheadDump() {
//        Thread.State
    }
    
    @Test
    public void testSyncLink() {
        List<String> list = Collections.synchronizedList(new ArrayList<>());
        list.size();
        System.out.println(list.get(0));
    }
    
    @Test
    public void testReentrantLock() throws Exception {
        Lock lock = new ReentrantLock();
        new TestReentrantLock(lock, "A").start();
        new TestReentrantLock(lock, "B").start();
        new TestReentrantLock(lock, "C").start();
        Thread.sleep(60000);
    }

    @Test
    public void testReadLock() throws Exception {
        Lock lock = new ReentrantReadWriteLock().readLock();
        new TestReadLock(lock, "A").start();
        new TestReadLock(lock, "B").start();
        new TestReadLock(lock, "C").start();
        Thread.sleep(60000);
    }

    @Test
    public void testWriteLock() throws Exception {
        Lock lock = new ReentrantReadWriteLock().writeLock();
        new TestWriteLock(lock, "A").start();
        new TestWriteLock(lock, "B").start();
        new TestWriteLock(lock, "C").start();
        Thread.sleep(60000);
    }


    @Test
    public void testCombixLock() throws Exception {
        ReentrantReadWriteLock reentrantReadWriteLock = new ReentrantReadWriteLock();
        new TestWriteLock(reentrantReadWriteLock.readLock(), "A").start();
        new TestWriteLock(reentrantReadWriteLock.readLock(), "C").start();
        new TestWriteLock(reentrantReadWriteLock.writeLock(), "B").start();
        Thread.sleep(60000);
    }
    
    
    @Test
    public void testJoin() throws Exception {
        new Thread(() -> {
            System.out.println("I'm thread A");
            try {
                Thread b = new Thread(() -> {
                    System.out.println("I'm thread B");
                    try {
                        Thread.sleep(3000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    System.out.println("I'm thread B, I'm wake!");
                }, "B");
                b.start();
                b.join();
            } catch (Exception e) {
                e.printStackTrace();
            }
            System.out.println("I'm thread A, Ok");
        }, "A").start();
        Thread.sleep(5000);
    }
    
    class TestReentrantLock extends Thread{
        Lock lock;

        public TestReentrantLock(Lock lock, String name) {
            this.lock = lock;
            setName(name);
        }
        
        @Override
        public void run() {
            lock.lock();
            System.out.println("I'm thread " + Thread.currentThread().getName());
            try {
                sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }finally {
                lock.unlock();
            }
        }
    }
    
    
    class TestReadLock extends Thread{
        private Lock lock;

        public TestReadLock(Lock lock, String name) {
            this.lock = lock;
            setName(name);
        }

        @Override
        public void run() {
            lock.lock();
            System.out.println("I'm thread " + Thread.currentThread().getName());
            try {
                sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }finally {
                lock.unlock();
            }
        }
    }
    
    class TestWriteLock extends Thread{
        private Lock lock;

        public TestWriteLock(Lock lock, String name) {
            this.lock = lock;
            setName(name);
        }
        
        @Override
        public void run() {
            lock.lock();
            System.out.println("I'm thread " + Thread.currentThread().getName());
            try {
                sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }finally {
                lock.unlock();
            }
        }
    }
}
