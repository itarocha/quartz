package br.itarocha.quartz;

import java.math.BigInteger;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class FilaMain {
	
	public static void main(String[] args) {
		FilaMain fm = new FilaMain();
	}
	
	public FilaMain() {
		DateTimeFormatter fmt = DateTimeFormatter.ofPattern("dd/MM/yyyy");
		DateTimeFormatter fmtBD = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		
		LocalDate dBase = LocalDate.parse("12/08/2018", fmt);
		
		Ag ag = new Ag(LocalDate.parse("2018-08-12", fmtBD), LocalDate.parse("2018-08-19", fmtBD));
		
		
		ag.dtIni = LocalDate.parse("01/09/2018", fmt);
		System.out.println(fmt.format(ag.dtIni));
		
		System.out.println(String.format("%s é antes de %s? "+ag.dtIni.isBefore(dBase),fmt.format(dBase), fmt.format(ag.dtIni)));
		dBase = dBase.plusDays(5);
		System.out.println(fmt.format(dBase));
	}
	
	private class Ag {
		public BigInteger id;
		public LocalDate dtIni;
		public LocalDate dtFim;
		private List<Task> tasks = new ArrayList<Task>(); 
		
		public Ag(LocalDate dtIni, LocalDate dtFim) {
			this.dtIni = dtIni;
			this.dtFim = dtFim;
			tasks.add(new Task(dtIni, dtFim));
		} 
	}
	
	private class Task{
		public LocalDate dtIni;
		public LocalDate dtFim;
		
		public Task(LocalDate dtIni, LocalDate dtFim) {
			this.dtIni = dtIni;
			this.dtFim = dtFim;
		}
	}
}

/*
|------------------------|
..........................  início
12345678901234567890123456  

a) 1-26


|------------------------|
.......X.................. seleção do corte
12345678901234567890123456

a) 1-7 (update)


|------------------------|
.......                    anterior -1 => final                     
       ooooooooooooooooooo
12345678901234567890123456

a) 1-7
b) 8-26 (new = 8-1 até o final) 







*
*
*
*
*
*
*
*
*/


