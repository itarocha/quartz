package br.itarocha.quartz;

import org.quartz.SchedulerException;

public class Main {

	public static void main(String... args) throws SchedulerException {
		System.out.println("oua");
		try {
		
			Agd.start();
			Thread.sleep(60L * 1000L);
			Agd.stop();
			System.out.println("***********************************************");			
			System.out.println("\n\n\n\nParei, mestre!");			
			Thread.sleep(60 * 1000L);
			Agd.start();
			System.out.println("\n\n\n\nEstou voltando a trabalhar, mestre!");			
			Thread.sleep(60 * 1000L);
			Agd.stop();
			System.out.println("***********************************************");			
			System.out.println("\n\n\n\nParei, mestre!");			
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}
