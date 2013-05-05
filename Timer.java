class Timer implements Runnable
{
	Thread t;
	Board br;
	String s;
	Timer (Board b)
	{
		t = new Thread(this,"timerThread");
		br=b;
		t.start();
	}
	
	public void run()
	{
		try{
			for(int i=0;i<60;i++)
				for(int j=0;j<60;j++)	
				{
					Thread.sleep(1000);
					if(j==60)
						j=0;
					if(!br.isGameOver())
						if(j<10)
							if(i<10)
								br.setTime("0"+i+":"+0+j);
							else
								br.setTime(i+":"+0+j);
						else
							if(i<10)
								br.setTime("0"+i+":"+j);
							else
								br.setTime(i+":"+j);
				}
		}
		
		catch(Exception e)
		{
			
		}
	}
	
}