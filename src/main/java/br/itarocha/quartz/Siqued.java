package br.itarocha.quartz;

import org.quartz.CronScheduleBuilder;
import org.quartz.CronTrigger;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SchedulerFactory;
import org.quartz.SimpleScheduleBuilder;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.quartz.impl.StdSchedulerFactory;

import br.itarocha.quartz.SimpleJob;

public class Siqued {

	public static void main(String... args) throws SchedulerException {
		System.out.println("oua");
		Siqued s = new Siqued();
		try {
			s.go();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void go() throws SchedulerException, InterruptedException {

		//https://www.baeldung.com/quartz
		//http://www.quartz-scheduler.org/documentation/quartz-2.x/cookbook/MonthlyTrigger.html

		SchedulerFactory sf = new StdSchedulerFactory();
		Scheduler sched = sf.getScheduler();
		
		JobDetail job = JobBuilder.newJob((SimpleJob.class))
				.withIdentity("myJob", "group1")
				.usingJobData("jobSays", "Hello World")
				.usingJobData("myFloatValue",3.141f)
				.build();
		
		CronTrigger tg = TriggerBuilder.newTrigger()
				.withIdentity("trigger1", "group1")
				.withSchedule(CronScheduleBuilder.cronSchedule("0,30 * * * * ?"))
				.build();

		Trigger trigger1 = TriggerBuilder.newTrigger()
			    .withIdentity("Priority1Trigger5SecondRepeat")
			    //.startAt(startTime)
			    .startNow()
			    .withSchedule(SimpleScheduleBuilder
			    		.simpleSchedule()
			    		//.withRepeatCount(100)
			    		.repeatForever()
			    		.withIntervalInSeconds(5))
			    .withPriority(1)
			    .forJob(job)
			    .build();		
		
		sched.scheduleJob(job, tg);
		sched.scheduleJob(trigger1);
		sched.start();
		
		Thread.sleep(300L * 1000L);
		sched.shutdown(true);
		System.out.println("\n\nFIM");
		
	}
}
