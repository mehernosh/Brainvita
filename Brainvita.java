import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

class Brainvita extends JFrame implements ActionListener
{
	JPanel p1,p2,p3;
	JTextField t;
	JButton b,h;
	JLabel l,la,imgl;
	ImageIcon img = new ImageIcon("images/Brainvita.jpg");
	
	
	Brainvita()
	{
		super("Brainvita");
			
		p1 = new JPanel();
		p2 = new JPanel();
		p3 = new JPanel();
		setLayout(new BorderLayout());
		
		b = new JButton("Play Now");
		b.addActionListener(this);
		
		l = new JLabel ("ENTER YOUR NAME");
		t = new JTextField(18);
		la = new JLabel ("@copyleft all wrongs reserved");
		imgl = new JLabel(img);
		imgl.setPreferredSize(new Dimension(400,200));

		p1.add(imgl);
		
		p2.setLayout(new FlowLayout());
		p2.add(l);
		p2.add(t);
		p2.add(b);
		
		
		p3.add(la);
		
		add(p1,BorderLayout.NORTH);
		add(p2,BorderLayout.CENTER);
		add(p3,BorderLayout.SOUTH);
		setSize(420,500);
		setVisible(true);
		setBounds(new Rectangle(275,175,420,500));
		setDefaultCloseOperation(EXIT_ON_CLOSE);
	}
	
	public void actionPerformed (ActionEvent e)
	{
		
		setVisible(false);
		Board b = new Board();
		Timer ti = new Timer(b);
		b.setPlayer(t.getText());
		
	}
	
	public static void main (String args[])
	{
		new Brainvita();
	}
}