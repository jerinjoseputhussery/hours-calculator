package calculations;

import java.time.Duration;
import java.time.LocalTime;
import java.util.Scanner;

public class Calculate {

	public static void main(String[] args) {
		 Scanner scanner = new Scanner(System.in);  
		 
		 System.out.println("Enter number of days you worked in this pay-period:");
		 int noOfDays = scanner.nextInt(); 
		 String input;
		 String shiftTime="";
		 boolean isValidated;
		 boolean firstExecution;
		 boolean isSunday;
		 boolean isbankHoliday;
		 LocalTime clock_in;
		 LocalTime clock_out;
		 Duration todays_timings;
		 long pay_period_minutes=0;
		 int night_period_hours=0;
		 long sun_period_minutes=0;
		 long bank_period_minutes=0;


		 
		 for (int i = 0; i < noOfDays; i++) {
			 //Loop for days
			 input="";
			 todays_timings=Duration.ZERO;
			 isValidated=false;
			 firstExecution=true;
			 isSunday=false;
			 isbankHoliday=false;
			 do{
				 if(firstExecution) {
					 firstExecution=false;
					 System.out.println("(24 hours format) separated by- (eg:- 0800-1600);\t If sunday prefix sun\t If Repeat input re");
					 System.out.println("Enter day"+(i+1)+" clock-in and clock out timings:");
				 }else {
					 System.out.println("Enter the timing in proper format. See format below:");
					 System.out.println("(24 hours format) separated by- (eg:- 0800-1600)");				 
				 }
				 input = scanner.nextLine(); 
				 if(input.equalsIgnoreCase("re") || input.equalsIgnoreCase("repeat")) {
					 if(shiftTime.equals("")){
						 System.out.println("Repeat doesnot exists. Enter the timing please:");
						 input = scanner.nextLine(); 
						 shiftTime=input;
					 }
				 }
				 else					 
					 shiftTime=input;
				 if(shiftTime.startsWith("SUN") || shiftTime.startsWith("sun") || shiftTime.startsWith("Sun")) {
					 shiftTime=shiftTime.substring(3);
					 isSunday=true;
				 }
//				 else if(shiftTime.startsWith("BAN") || shiftTime.startsWith("ban") || shiftTime.startsWith("Ban")) {
//					 shiftTime=shiftTime.substring(3);
//					 isbankHoliday=true;
//				 }
				 else if(shiftTime.startsWith("BANK") || shiftTime.startsWith("bank") || shiftTime.startsWith("Bank")) {
					 shiftTime=shiftTime.substring(4);
					 isbankHoliday=true;
				 }
				 isValidated = Validations.checkInputs(shiftTime);					 
			 }while(!isValidated);
			 
			 if(isValidated) {
				 clock_in = LocalTime.parse( shiftTime.split("-")[0].substring(0,2)+":"+shiftTime.split("-")[0].substring(2));
				 clock_out = LocalTime.parse( shiftTime.split("-")[1].substring(0,2)+":"+shiftTime.split("-")[1].substring(2));
				 System.out.println(clock_in+"-"+clock_out);
				 
				 todays_timings=Duration.between(clock_in, clock_out);				 
				 todays_timings=todays_timings.abs();
				 
				 if(todays_timings.toMinutes()>=480) { //if the shift hours is greater than 8 hours
					 todays_timings = todays_timings.minusHours(1);			 
				 }else if(todays_timings.toMinutes()>300) { //if the shift hours is greater than 5 hours
					 todays_timings = todays_timings.minusMinutes(30);
				 }else if(todays_timings.toMinutes()>240) { //if the shift hours is greater than 4 hours
					 todays_timings = todays_timings.minusMinutes(15);
				 }
				 //Checks for night
				 if(Validations.isTimeRangeContained(LocalTime.MIDNIGHT, LocalTime.of(6, 0), clock_in, clock_out)) {
					 todays_timings = todays_timings.minusHours(6);
					 night_period_hours+=6;
				 }
				 
				 if(isSunday) 
					 sun_period_minutes+=todays_timings.toMinutes();
				 else if(isbankHoliday)
					 bank_period_minutes+=todays_timings.toMinutes();
				 else
					 pay_period_minutes+=todays_timings.toMinutes();
			 }
			 
		}
		 System.out.println("BASIC: "+pay_period_minutes/60.0+" hours");
		 if(bank_period_minutes>0)
			 System.out.println("BANK HOL: "+bank_period_minutes/60.0+" hours");
		 if(sun_period_minutes>0)
			 System.out.println("SUNDAY PREM: "+sun_period_minutes/60.0+" hours");
		 if(night_period_hours>0)
			 System.out.println("NIGHT PREM: "+night_period_hours+" hours");

		 
			 
	}

}
