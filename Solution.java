

import java.awt.*;
import java.awt.event.*;
import java.io.*;
import javax.swing.*;


public class Solution extends JFrame implements ActionListener
{
	JPanel p,p1,p2;
	MyButton b[][];
	JButton next,prev,exit;
	JLabel footer1,footer2,time;
	ImageIcon normalButtonIcon =new ImageIcon("images/normal.jpg");
	ImageIcon blankButtonIcon = new ImageIcon("images/blank.jpg");
	ImageIcon selectedButtonIcon = new ImageIcon("images/selected.jpg");
	//int xSelected,ySelected;
	int mark;
	File sol = new File("files/solution.txt");
	FileReader fr;
	
	
	//CONSTRUCTOR STARTS
	Solution()
	{
		//Initialise the super class---just to get the text on the title bar
	
		super("Brainvita-Solution");
		
		//further changes	
		setLayout(new BorderLayout());
		
		footer1 =new JLabel("This is the");
		footer2 =new JLabel("Solution");
		time = new JLabel("00:00"); 
		footer1.setSize(100,10);
		footer2.setSize(100,10);
		
		
		p= new JPanel();
		p1 = new JPanel();
		p2 = new JPanel();
		
		
		//THE OPTION SECTION
		p1.setLayout(new FlowLayout());
			next = new JButton("NEXT STEP");
			prev =new JButton("PREVIOUS STEP");
			exit = new JButton("EXIT");
						
			next.addActionListener(this);
			prev.addActionListener(this);
			exit.addActionListener(this);
		
			
		p1.add(next);
		p1.add(prev);
		p1.add(exit);
		
		add(p1,BorderLayout.NORTH);
		
		// THE MAIN BOARD SECTION
		b=new MyButton[7][];
		for(int i=0;i<7;i++)
			b[i]=new MyButton[7];
		p.setLayout(new GridLayout(7,7));
		for(int i=0;i<7;i++)
			for(int j=0;j<7;j++)
			{	
				
				if(isValidBlock(i,j))
					if(i==3&&j==3)
						{	b[i][j] = new MyButton(blankButtonIcon,i,j);
							b[i][j].resetFlag();
							b[i][j].addActionListener(this);
						}
					else
						{	b[i][j] = new MyButton(normalButtonIcon,i,j);
							b[i][j].setFlag(); 
							b[i][j].addActionListener(this);
						}
					else 
							b[i][j]= new MyButton(i,j);		
				
				p.add(b[i][j]);
				if(!isValidBlock(i,j))
					b[i][j].setEnabled(false);
 
			}
			
		add(p,BorderLayout.CENTER);
		
		
		//THIS IS FOOTER TO DISPLAY MESSAGES
		p2.setLayout(new GridLayout(0,3));
		p2.add(footer1);
		p2.add(footer2);
		p2.add(time);
		//p2.setSize(50,50);
		add(p2,BorderLayout.SOUTH);
		
		
		setSize(420,500);
		setVisible(true);
		setResizable(false);
		setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);

		
		mark=0;
		
		
	}
	
		boolean isValidNumber(int a)
	{
		if(a==2||a==3||a==4)
			return true;
		else
			return false;
	}
	
	//CHECKS IF THE SELECTED BLOCK IS VALID
	boolean isValidBlock(int a ,int b)
	{
		if(isValidNumber(a)||isValidNumber(b))
			return true;
		else
			return false;
	}
	
	void nextF()
	{
		int a1,a2,a3,a4,m;
		char ch[]=new char[4];
		a1=a2=a3=a4=-1;
		m=mark;
		try{
		
		fr = new FileReader(sol);
		
		while(m>-4)
		{
			fr.read(ch,0,4);
            m-=4;
		}
		if(mark<=sol.length())
        {
            a1=ch[0]-48;
            a2=ch[1]-48;
            a3=ch[2]-48;
            a4=ch[3]-48;
            	b[a1][a2].changeImage(blankButtonIcon);
				b[a1][a2].resetFlag();
				b[a3][a4].changeImage(normalButtonIcon);
				b[a3][a4].setFlag();
				if(a2==a4)
				{	b[(a1+a3)/2][a4].changeImage(blankButtonIcon);
					b[(a1+a3)/2][a4].resetFlag();
				}
				else
				if(a1==a3)
					{	b[a1][(a2+a4)/2].changeImage(blankButtonIcon);
						b[a1][(a2+a4)/2].resetFlag();
					}
					
				if(mark<sol.length())
            	mark=mark+4;
        }
            fr.close();
		}
		catch(Exception ex)
		{
		}
			
		
	}
	
	void prevF()
	{
		int a1,a2,a3,a4,leng;
		a1=a2=a3=a4=-1;
		leng=mark;
		char ch[]=new char[leng];
		StringBuffer sbuf=null;
                    try {
                         fr = new FileReader(sol);
                         fr.read(ch);
                         if(leng>3)
                         {
                              a1=ch[leng-4]-48;
                              a2=ch[leng-3]-48;
                              a3=ch[leng-2]-48;
                              a4=ch[leng-1]-48;
                              
                              
                              {	b[a1][a2].changeImage(normalButtonIcon);
								b[a1][a2].setFlag();
								b[a3][a4].changeImage(blankButtonIcon);
								b[a3][a4].resetFlag();
								if(a2==a4)
								{	b[(a1+a3)/2][a4].changeImage(normalButtonIcon);
									b[(a1+a3)/2][a4].setFlag();
								}
								else
								if(a1==a3)
								{	b[a1][(a2+a4)/2].changeImage(normalButtonIcon);
									b[a1][(a2+a4)/2].setFlag();
								}
								mark = mark - 4;
                              }
                         }
                         fr.close();
                    } catch (IOException ex) {
                         System.out.println(ex);
                    }
                    
         
	}
	
	public void actionPerformed (ActionEvent e)
	{
		JButton bt = (JButton)e.getSource();
		if(bt==next)
			nextF();
		if(bt==prev)
			prevF();
		if(bt==exit)
			setVisible(false);
			
	}
	
	/*public static void main(String args[])
	{
		new Solution();
	}*/
}     