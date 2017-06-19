package com.sirding.timer;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

public class RunTimer {

	public static Timer timer = new Timer(true);
	
	static class MyTask extends TimerTask{

		@Override
		public void run() {
			System.out.println("定时任务已经运行了...." + "当前时间：" + new Date());
		}
		
	}
	
	public static void main(String[] args) throws Exception{
		MyTask task = new MyTask();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String t = "2016-12-22 21:44:45";
		timer.schedule(task, sdf.parse(t));
		Thread.sleep(1000);
		System.out.println("over......");
	}
}
