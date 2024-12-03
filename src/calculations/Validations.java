package calculations;

import java.time.LocalTime;

public class Validations {
	private static final String pattern = "[0-9][0-9][0-9][0-9]-[0-9][0-9][0-9][0-9]";
	
	
	public static boolean checkInputs(String input) {
		if(input.length()!=9)
			return false;
		else {
			if(input.matches(pattern))
				return true;
			else
				return false;						
		}
	}
	public static boolean isTimeRangeContained(LocalTime innerStart, LocalTime innerEnd, LocalTime outerStart, LocalTime outerEnd) {
        if (outerStart.isBefore(outerEnd)) {
            // Outer range does not span midnight
            return !innerStart.isBefore(outerStart) && !innerEnd.isAfter(outerEnd);
        } else {
            // Outer range spans midnight
            boolean innerInFirstPart = !innerStart.isBefore(outerStart) || !innerEnd.isAfter(LocalTime.MAX);
            boolean innerInSecondPart = !innerEnd.isAfter(outerEnd) || !innerStart.isBefore(LocalTime.MIN);
            return innerInFirstPart && innerInSecondPart;
        }
    }

}
