/**
 * Class Monitor
 * To synchronize dining philosophers.
 *
 * @author Serguei A. Mokhov, mokhov@cs.concordia.ca
 */
public class Monitor
{
	/*
	 * ------------
	 * Data members
	 * ------------
	 */
	private int numberChopstick;
	private boolean [] chopstick;
	private boolean [] WantEat;
	private int request=0;
	
	


	/**
	 * Constructor
	 */
	public Monitor(int piNumberOfPhilosophers)
	{
		// TODO: set appropriate number of chopsticks based on the # of philosophers
		
		numberChopstick=piNumberOfPhilosophers;
		
		WantEat=new boolean[piNumberOfPhilosophers];
		chopstick=new boolean[piNumberOfPhilosophers];
		for(int i=0;i<numberChopstick;i++) {
			WantEat[i]=false;
			chopstick[i]=true;
			}
		
	}

	/*
	 * -------------------------------
	 * User-defined monitor procedures
	 * -------------------------------
	 */

	/**
	 * Grants request (returns) to eat when both chopsticks/forks are available.
	 * Else forces the philosopher to wait()
	 */
	public synchronized void pickUp(final int piTID)
	{while(true) {
		
		if(!(WantEat[(piTID+1)%numberChopstick]==true && WantEat[(piTID-1)%numberChopstick]==true) && chopstick[(piTID+1)%numberChopstick]==true && chopstick[(piTID)%numberChopstick]==true) {
			chopstick[(piTID+1)%numberChopstick]=false;
			chopstick[(piTID)%numberChopstick]=false;
			
			
			WantEat[(piTID)%numberChopstick]=false;
			
			
			break;
		}
		else
			try {
				WantEat[(piTID)%numberChopstick]=true;
                wait();
            } catch (InterruptedException e)  {
                Thread.currentThread().interrupt(); 
               
            }}
	}

	/**
	 * When a given philosopher's done eating, they put the chopstiks/forks down
	 * and let others know they are available.
	 */
	public synchronized void putDown(final int piTID)
	{
		chopstick[(piTID+1)%numberChopstick]=true;
		chopstick[(piTID)%numberChopstick]=true;
		notifyAll();
	}

	/**
	 * Only one philopher at a time is allowed to philosophy
	 * (while she is not eating).
	 */
	public synchronized void requestTalk()
	{
		if(request==0)
			request++;
			
		else {
			try {
                wait();
            } catch (InterruptedException e)  {
                Thread.currentThread().interrupt(); 
               
            }
		}
	}

	/**
	 * When one philosopher is done talking stuff, others
	 * can feel free to start talking.
	 */
	public synchronized void endTalk()
	{
		request--;
		notify();
	}
}

// EOF
