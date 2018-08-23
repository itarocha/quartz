package br.itarocha.quartz;

import org.quartz.DisallowConcurrentExecution;
import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.JobDetail;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.PersistJobDataAfterExecution;

@PersistJobDataAfterExecution
@DisallowConcurrentExecution
public class SimpleJob implements Job{

	private final String EXECUTION_COUNT = "EXECUTION_COUNT"; 

	public void execute(JobExecutionContext context) throws JobExecutionException {
		JobDataMap dataMap = context.getJobDetail().getJobDataMap();
		String jobSays = dataMap.getString("jobSays");
		int count = 0;
		if (dataMap.containsKey(EXECUTION_COUNT)) {
			count = dataMap.getInt(EXECUTION_COUNT);
		}
		count++;
		dataMap.put(EXECUTION_COUNT, count);
		float myFloatValue = dataMap.getFloat("myFloatValue");
		System.out.println("--------------------------------------------------------------------");
		System.out.println("Job says: "+jobSays + ",  e val is: "+myFloatValue);
        System.out.println("   start: " + context.getFireTime());
        System.out.println("   count: " + count);
        JobDetail jobDetail = context.getJobDetail();
        System.out.println("   end: " + context.getJobRunTime() + ", key: " + jobDetail.getKey() + ", TRIGGER: "+context.getTrigger().getKey());
        System.out.println("   next scheduled time: " + context.getNextFireTime());
        System.out.println("--------------------------------------------------------------------");
        
        try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
