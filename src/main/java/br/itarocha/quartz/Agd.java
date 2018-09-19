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

public class Agd {

	private static SchedulerFactory sf;
	private static Scheduler sched;
	private static JobDetail job;
	private static CronTrigger tg;
	private static Trigger trigger1; 
	
	public static void start() throws SchedulerException, InterruptedException {

		//https://www.baeldung.com/quartz
		//http://www.quartz-scheduler.org/documentation/quartz-2.x/cookbook/MonthlyTrigger.html

		sf = new StdSchedulerFactory();
		sched = sf.getScheduler();
		
		job = JobBuilder.newJob((SimpleJob.class))
				.withIdentity("myJob", "group1")
				.usingJobData("jobSays", "Hello World")
				.usingJobData("myFloatValue",3.141f)
				.build();
		
		tg = TriggerBuilder.newTrigger()
				.withIdentity("trigger1", "group1")
				.withSchedule(CronScheduleBuilder.cronSchedule("0,30 * * * * ?"))
				.build();

		trigger1 = TriggerBuilder.newTrigger()
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
		
	}
	
	public static boolean stop() throws Exception {
		try {
			boolean retorno = true;
			if (sched != null) {
				sched.shutdown(true);
			}
			return retorno;
		} catch (Exception ignored) {
		}
		return false;
	}
}
