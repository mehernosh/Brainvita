import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;

class MyButton extends JButton
{
	int x;
	int y;
	int flag;
	void setFlag()
	{
		flag = 1;
	}
	void resetFlag()
	{
		flag = 0;
	}
	int getFlag()
	{
		return flag;
	}
		//CHANGE IMAGE FUNCTION
	void changeImage (ImageIcon img)
	{
		setIcon(img);
	}
	
		//GET X AND Y
		
	int posX ()
	{
		return x;
	}	
		
	int posY()
	{
		return y;
	}	
	MyButton(ImageIcon img,int a,int b)
	{
		super(img);
		x=a;
		y=b;
	}
	MyButton(int a,int b)
	{
		super();
		x=a;
		y=b;
	}
}

class Board extends JFrame implements ActionListener
{
	JPanel p,p1,p2;
	MyButton b[][];
	JButton undo,reset,exit,help,solution;
	JLabel footer1,footer2,time;
	ImageIcon normalButtonIcon =new ImageIcon("images/normal.jpg");
	ImageIcon blankButtonIcon = new ImageIcon("images/blank.jpg");
	ImageIcon selectedButtonIcon = new ImageIcon("images/selected.jpg");
	int xSelected,ySelected;
	File un = new File("files/undo.txt");
	File helpfile=new File("files/Help.txt");
	FileWriter fw;
	String name;
	//CONSTRUCTOR STARTS
	
	Board()
	{	//Initialise the super class---just to get the text on the title bar
	
		super("Brainvita");
		
		//further changes	
		setLayout(new BorderLayout());
		
		footer1 =new JLabel("Hello");
		footer2 =new JLabel("");
		time = new JLabel("00:00"); 
		footer1.setSize(100,10);
		footer2.setSize(100,10);
		
		
		p= new JPanel();
		p1 = new JPanel();
		p2 = new JPanel();
		
		//THE OPTION SECTION
		p1.setLayout(new FlowLayout());
			undo = new JButton("UNDO");
			reset =new JButton("RESET");
			exit = new JButton("EXIT");
			help = new JButton("HELP");
			solution = new JButton("SOLUTION");
			
			undo.addActionListener(this);
			reset.addActionListener(this);
			exit.addActionListener(this);
			help.addActionListener(this);
			solution.addActionListener(this);
			
		p1.add(undo);
		p1.add(reset);
		p1.add(solution);
		p1.add(help);
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
		
		//FILE WRITER
		try{
		fw = new FileWriter (un);
		fw.write("");
		fw.close();
		}
		catch(Exception ex)
		{
			
		}
		//INITIALSE X SELECTED AND Y SELECTED
		initialiseSelected();
		
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setBounds(new Rectangle(275,175,420,500));
	}
	
	//CONSTRUCTOR ENDS
	
	void initialiseSelected()
	{
		xSelected=-1;
		ySelected=-1;
	}
	//CHECKS IF NO BUTTON IS SELECTED
	
	boolean isNoButtonSelected()
	{
		if(xSelected==-1 && ySelected == -1)
			return true;
		return false;
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
	
	//TO COUNT THE TOTAL MARBLES LEFT
	int marbleCount()
	{	
		int k=0;
		for(int i=0;i<7;i++)
			for(int j=0;j<7;j++)
				if(isValidBlock(i,j))
					if(b[i][j].getFlag()==1)
						k++;
		
		return k;	
	}
	
	
	// CHECKS WHETHER THE MOVE IS VALID OR NOT
	boolean isValidJump(int p,int q,int c,int d)
	{	
		if(p>=7||q>=7||c>=7||d>=7)
			return false;
		if(p<0||q<0||c<0||d<0)
			return false;
			
		if(!isValidBlock(p,q)||!isValidBlock(c,d) )
			return false;
		
		if(b[p][q].getFlag()==0)
			return false;
			
		if(b[c][d].getFlag()==1)
			return false;
		if(p!=c&& q!=d)
			return false;
		
		if(p==c&&q==d)
			return false;
			
		if(p==c)
			if(q==d+2)
				if(b[c][d+1].getFlag()==1)
				return true;
			if(q==d-2)
				if(b[p][q+1].getFlag()==1)
				return true;	
		
		if(q==d)
			if(p==c+2)
				if(b[c+1][d].getFlag()==1)
				return true;
			if(p==c-2)
				if(b[p+1][q].getFlag()==1)
				return true;
				
		return false;
	}
	
	//CHECKS WHETHER THE GAME IS OVER OR NOT
	boolean isGameOver()
	{	
		boolean bl = true;
		for(int i=0;i<7;i++)
			for(int j=0;j<7;j++)
			{	if(isValidJump(b[i][j].posX(),b[i][j].posY(),b[i][j].posX()+2,b[i][j].posY()))
					bl = false;
				if(isValidJump(b[i][j].posX(),b[i][j].posY(),b[i][j].posX(),b[i][j].posY()+2))
					bl = false;
				if(isValidJump(b[i][j].posX(),b[i][j].posY(),b[i][j].posX()-2,b[i][j].posY()))
					bl = false;
				if(isValidJump(b[i][j].posX(),b[i][j].posY(),b[i][j].posX(),b[i][j].posY()-2))
					bl = false;
			}
			return bl;	
	}
	
	
	//FUNCTION FOR GAME OVER MESSAGE
	void finalF()
	{
		setFooter2();
				if(marbleCount()>3)
					setFooter1("You Need Practice");
				if(marbleCount()==3)
					setFooter1("You are Average");
				if(marbleCount()==2)
					setFooter1("You are Intelligent");
				if(marbleCount()==1)
					setFooter1("You are Genius");
					
	}
	
	void setPlayer(String player)
	{
		name=player;
		footer1.setText("Hello, "+name);
	}
	
	
	/*Image image = Toolkit.getDefaultToolkit().getImage("cursor.gif");
Cursor changeCursor = Toolkit.createCustomCursor(image, new Point(10,10), "Change Cursor");
setCursor(changeCursor);*/
	void writeFile(int a1,int a2,int a3,int a4) throws Exception
	{
		fw = new FileWriter (un,true);
		fw.write(""+a1+a2+a3+a4);
		fw.close();
	}
	
	void exitF()
	{
		System.exit(0);
	}
	boolean undoF()
	{
		int a1,a2,a3,a4;
		a1=a2=a3=a4=-1;
		int leng;
		leng = (int)(un.length());
		char ch[] = new char[leng];
		StringBuffer sbuf=null;
		
		try{
			
			FileReader fr = new FileReader (un);
			fr.read(ch);
			if(leng>3)
			{
				a4=ch[leng-1]-48;
				a3=ch[leng-2]-48;
				a2=ch[leng-3]-48;
				a1=ch[leng-4]-48;
				//System.out.println(""+a1+a2+a3+a4);
				b[a1][a2].changeImage(normalButtonIcon);
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
				else 
					return false;
				
			}
			fr.close();
			sbuf=new StringBuffer(new String(ch));
               if(leng>3)
                    sbuf=sbuf.delete(leng-4,leng);
               fw=new FileWriter(un,false);
               fw.write(sbuf.toString());
               fw.close();
               
		
		}
		catch(Exception ex)
		{
			return false;
		}
		if(a1<0||a2<0||a3<0||a4<0)
			return false;

				
		return true;
	}
	void resetF()
	{
		//if(!isGameOver())
		while(undoF());
		setPlayer(name);
	}
	void helpF()
	{
		char cbuf=' ';
                    JFrame help=new JFrame("Help-Brainvita");
                    JTextArea ta=new JTextArea();
                    JScrollPane jsp=new JScrollPane(ta);
                    ta.setFont(new Font("Arial",Font.BOLD,17));
                    ta.setFocusable(false);
                    ta.append("Report any bugs or suggestions");
                    ta.setCaretPosition(0);
                    try {
                         FileReader fr = new FileReader(helpfile);
                         while(true)
                         {
                              int temp=fr.read();
                              if(temp==-1)
                                   break;
                              cbuf=(char)(temp);
                              ta.append(cbuf+"");
                         }
                    } catch (Exception ex) {
                         System.out.println(ex);
                    }
                    help.getContentPane().add(jsp);
                    help.setBounds(new Rectangle(275,175,600, 600));
                    help.setVisible(true);
	}
	void solutionF()
	{
		new Solution();
	}
	//THE ACTION PERFORMED FUNCTION
	
	public void actionPerformed (ActionEvent e)
	{
		JButton bt = (JButton)e.getSource();
		if(bt==undo) 
			{if(!isGameOver())
				undoF();	}
		else	
		if(bt==reset)
			resetF();
		else	
		if(bt==help)
			helpF();
		else
		if(bt==solution)
			solutionF();
		else	
		if(bt==exit)
			exitF();
		else	
		{
			MyButton bt1 = (MyButton)e.getSource();
			
			if(!isGameOver())
			{	
				if(isNoButtonSelected())
					{		if( bt1.getFlag()==1)
						{	xSelected = bt1.posX();
							ySelected = bt1.posY();
							bt1.changeImage(selectedButtonIcon);
							
							Toolkit tk = Toolkit.getDefaultToolkit();
							Image image = Toolkit.getDefaultToolkit().getImage("images/circle.gif");
							Cursor changeCursor = tk.createCustomCursor(image, new Point(10,10), "Change Cursor");
							setCursor(changeCursor);
						}
					}
					
				else
					if(xSelected == bt1.posX()&&ySelected == bt1.posY())
					{	
						
						b[xSelected][ySelected].changeImage(normalButtonIcon);
						setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
						initialiseSelected();
						
					}
					else	
						if(bt1.getFlag()==0)	
							if(isValidJump(xSelected,ySelected,bt1.posX(),bt1.posY()))
							{
								b[xSelected][ySelected].changeImage(blankButtonIcon);
								b[xSelected][ySelected].resetFlag();
								bt1.changeImage(normalButtonIcon);
								bt1.setFlag();
								if(xSelected==bt1.posX())
								{	b[xSelected][(ySelected+bt1.posY())/2].changeImage(blankButtonIcon);
									b[xSelected][(ySelected+bt1.posY())/2].resetFlag();
								}
								else if(ySelected==bt1.posY())
								{	b[(xSelected+bt1.posX())/2][ySelected].changeImage(blankButtonIcon);
									b[(xSelected+bt1.posX())/2][ySelected].resetFlag();
								}
								//HERE ADD FUNTION TO WRITE INTO UNDO FILE
							//	Toolkit tk = Toolkit.getDefaultToolkit();
								//Cursor changeCursor = tk.createCustomCursor(Cursor.Cursor.DEFAULT_CURSOR , new Point(10,10), "Change Cursor");
								setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
								//setCursor(Cursor.DEFAULT_CURSOR);
								
								try{
								writeFile(xSelected,ySelected,bt1.posX(),bt1.posY());
								}
								catch(Exception ex)
								{
								}
								
									initialiseSelected();
									if(isGameOver())
										finalF();
							}
							else
							{
								b[xSelected][ySelected].changeImage(normalButtonIcon);
								setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
								initialiseSelected();
							}
						else
						{
							b[xSelected][ySelected].changeImage(normalButtonIcon);
							setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
							initialiseSelected();
						}
			}
			else
			{
				finalF();
			}
		}
		
		setFooter2();
	}
	
	
	//SET TIME
	void setTime (String s)
	{
		time.setText(s);
	}
	
	
	//SET FOOTER TEXT
	void setFooter1 (String s)
	{
		footer1.setText(s);
	}
	
	void setFooter2 ()
	{	
		int k = marbleCount();
		//if(isGameOver())
		footer2.setText("Marbles Left ="+k);
	}
	
	/*public static void main(String args[]) throws Exception
	{
		Board bo=new Board();
		Timer t = new Timer(bo);	
	}*/
}

