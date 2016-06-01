package uk.co.cherrygoose.delayedrespawn.systems;

public class Utils
{
	public static String toReadable(double rawHours)
	{
		// Defines integers for hours and minutes
		int dHours = (int)Math.floor(rawHours);
		int dMinutes = (int)((rawHours - dHours) *60);
		
		// Declares var for output
		String output = "";
		
		// If Hours are greater than 0
		if(dHours > 0) 
		{
			// Adds Hours to output
			output += (dHours + " " + sortPlural("Hour", dHours));
			
			// If Minutes are greater than 0
			if(dMinutes > 0)
			{
				// Adds Minutes to output
				output += (" and " + dMinutes + " " + sortPlural("Minute", dMinutes));
			}
		}
		// Else it's less than an hour
		else 
		{
			// If Minutes are greater than 0
			if(dMinutes > 0)
			{
				// Adds Minutes to output
				output += (dMinutes + " " + sortPlural("Minute", dMinutes));
			}
			// Else it's less than a minute
			else
			{
				// Adds message to output
				output += ("less than a minute");
			}
		}
		
		// Return output
		return output;
	}
	
	public static String sortPlural(String str, double d)
	{
		// If value is not 1
		if(d != 1)
		{
			// Returns string as plural
			return str + "s";
		}
		
		// Returns string
		return str;
	}
}
