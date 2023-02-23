import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.sql.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

// import javax.swing.border.Border;

class MoneyTracker extends JFrame implements ActionListener
{
	JPanel mainPanel,addMoneyPanel,subtractMoneyPanel,balanceMoneyPanel,balSecPanel;
	JButton addButton,subtractButton,balanceButton,back1,back2,back3,addIncome,subIncome,delData;
	JLabel income,expenditure,iLabel,iNoteLabel,eLabel,eNoteLabel,balanceLabel,balanceNote,total,balanceDate;
	JTextField t1,t2,t3,t4;
	CardLayout cl;
	Container c;
	String sql,url,upSQL,query;
	Connection con;
	Statement stmt;
	ResultSet rs,rs1;
	DateTimeFormatter dtf;
	LocalDate localDate;
	JScrollPane sp;
	int hor,ver;
	GridBagConstraints gbc;

	MoneyTracker()
	{
		c = getContentPane();
		cl = new CardLayout();
		gbc = new GridBagConstraints();
		
		dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		localDate = LocalDate.now();
		//dtf.format(localDate)

		mainPanel = new JPanel();
		addMoneyPanel = new JPanel();
		subtractMoneyPanel = new JPanel();
		balanceMoneyPanel = new JPanel();
		balSecPanel = new JPanel();

		int hor = ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS;
		int ver = ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS;

		addButton = new JButton(new ImageIcon("addition.jpg"));
		subtractButton = new JButton(new ImageIcon("minus.jpg"));
		balanceButton = new JButton(new ImageIcon("green-balance.png"));
		back1 = new JButton(new ImageIcon("back1.jpg"));
		back2 = new JButton(new ImageIcon("back1.jpg"));
		back3 = new JButton(new ImageIcon("back2.jpg"));
		addIncome = new JButton(new ImageIcon("addition.jpg"));
		subIncome = new JButton(new ImageIcon("minus.jpg"));
		delData = new JButton("Clear");

		t1 = new JTextField(30);
		t2 = new JTextField(30);
		t3 = new JTextField(30);
		t4 = new JTextField(30);

		t1.setFont(new Font("Verdana", Font.PLAIN, 20));
		t2.setFont(new Font("Verdana", Font.PLAIN, 20));
		t3.setFont(new Font("Verdana", Font.PLAIN, 20));
		t4.setFont(new Font("Verdana", Font.PLAIN, 20));

		income = new JLabel("Income: 0");
		income.setForeground(new Color(3, 194, 0));
		income.setFont(new Font("Verdana", Font.PLAIN, 20));
		expenditure = new JLabel("Expenditure: 0");
		expenditure.setForeground(new Color(247, 43, 2));
		expenditure.setFont(new Font("Verdana", Font.PLAIN, 20));
		total = new JLabel("Profit: 0 ");
		total.setForeground(new Color(3, 194, 0));
		total.setFont(new Font("Verdana", Font.PLAIN, 30));

		iLabel = new JLabel("Income:");
		iLabel.setForeground(new Color(3, 194, 0));
		iLabel.setFont(new Font("Verdana", Font.PLAIN, 20));
		iNoteLabel = new JLabel("Note:");
		iNoteLabel.setForeground(new Color(3, 194, 0));
		iNoteLabel.setFont(new Font("Verdana", Font.PLAIN, 20));

		eLabel = new JLabel("Expenditure:");
		eLabel.setForeground(new Color(247, 43, 2));
		eLabel.setFont(new Font("Verdana", Font.PLAIN, 20));
		eNoteLabel = new JLabel("Note:");
		eNoteLabel.setForeground(new Color(247, 43, 2));
		eNoteLabel.setFont(new Font("Verdana", Font.PLAIN, 20));

		balanceLabel = new JLabel("Money");
		balanceLabel.setForeground(new Color(1,188,219));
		balanceLabel.setFont(new Font("Verdana", Font.PLAIN, 20));

		balanceNote = new JLabel("Note");
		balanceNote.setForeground(new Color(1,188,219));
		balanceNote.setFont(new Font("Verdana", Font.PLAIN, 20));

		balanceDate = new JLabel("Date");
		balanceDate.setForeground(new Color(1,188,219));
		balanceDate.setFont(new Font("Verdana", Font.PLAIN, 20));


		//main panel 
		mainPanel.setLayout(null);
		mainPanel.setBackground(new Color(66,66,66));

		addButton.setBounds(350,610,100,100);
		addButton.addActionListener(this);
		mainPanel.add(addButton);

		delData.setBounds(190,630,100,100);
		delData.setForeground(new Color(1,188,219));
		delData.setFont(new Font("Verdana", Font.PLAIN, 20));
		delData.setBackground(Color.BLACK);
		delData.addActionListener(this);
		mainPanel.add(delData);

		subtractButton.setBounds(30,610,100,100);
		subtractButton.addActionListener(this);
		mainPanel.add(subtractButton);

		balanceButton.setBounds(125,300,220,70);
		balanceButton.addActionListener(this);
		mainPanel.add(balanceButton);

		total.setBounds(165,450,300,100);
		mainPanel.add(total);

		try{
			url = "jdbc:ucanaccess://ProjectDB.mdb";
			con = DriverManager.getConnection(url);
			stmt = con.createStatement();
			sql = "select * from MoneyTrack";
			query = "select sum(Money) from MoneyTrack";
			rs1 = stmt.executeQuery(query);
			rs1.next();
			int profit = rs1.getInt(1);
			if(profit > 0)
			{
				total.setForeground(new Color(3, 194, 0));
				total.setText("Profit: "+profit);
			}
			else if(profit < 0)
			{
				total.setForeground(new Color(247, 43, 2));//247, 43, 2
				total.setText("Loss: "+profit);
			}
			else
			{
				total.setForeground(new Color(3, 194, 0));
				total.setText("0");
			}
			rs = stmt.executeQuery(sql);
			int totalIncome = 0;
			int totalExp = 0;
			int value;
			
			while(rs.next())
			{
				value = rs.getInt(2);
				if(value > 0)
				{

					totalIncome = totalIncome + value;
				}
				else if(value < 0)
				{
					totalExp = totalExp + value;
				}

			}
			income.setText("Income:"+totalIncome);
			expenditure.setText("Expenditure:"+totalExp);
			con.close();
		}
		catch(Exception e)
		{
			System.out.println(e);
		}
		income.setBounds(150,100,180,50);
		mainPanel.add(income);

		expenditure.setBounds(130,130,250,50);
		mainPanel.add(expenditure);


		//add money panel
		addMoneyPanel.setLayout(null);
		addMoneyPanel.setBackground(new Color(66,66,66));

		back1.setBounds(10,10,50,50);
		back1.addActionListener(this);
		addMoneyPanel.add(back1);

		iLabel.setBounds(50,170,100,40);
		addMoneyPanel.add(iLabel);
		t1.setBounds(150,170,280,40);
		addMoneyPanel.add(t1);

		iNoteLabel.setBounds(50,250,100,40);
		addMoneyPanel.add(iNoteLabel);
		t2.setBounds(150,250,280,40);
		addMoneyPanel.add(t2);

		addIncome.setBounds(200,590,110,110);
		addIncome.addActionListener(this);
		addMoneyPanel.add(addIncome);

		//subtract money panel
		subtractMoneyPanel.setLayout(null);
		// subtractMoneyPanel.setForeground(new Color(255, 110, 110));
		subtractMoneyPanel.setBackground(new Color(66,66,66));//247,198,99 186, 255, 232

		back2.setBounds(10,10,50,50);
		back2.addActionListener(this);
		subtractMoneyPanel.add(back2);

		eLabel.setBounds(10,170,150,40);
		subtractMoneyPanel.add(eLabel);
		t3.setBounds(150,170,280,40);
		subtractMoneyPanel.add(t3);

		eNoteLabel.setBounds(50,250,100,40);
		subtractMoneyPanel.add(eNoteLabel);
		t4.setBounds(150,250,280,40);
		subtractMoneyPanel.add(t4);

		subIncome.setBounds(200,590,110,110);
		subIncome.addActionListener(this);
		subtractMoneyPanel.add(subIncome);

		//balance money panel
		balanceMoneyPanel.setLayout(new GridBagLayout());
		balanceMoneyPanel.setBackground(new Color(66,66,66));
		sp = new JScrollPane(balanceMoneyPanel,ver,hor);
		// balanceMoneyPanel.add(sp);
		gbc.fill = GridBagConstraints.BOTH;
		gbc.gridx = 0;
		gbc.gridy = 0;
		back3.setBounds(0,0,20,50);
		back3.addActionListener(this);
		balanceMoneyPanel.add(back3);

		balanceLabel.setBounds(100,0,100,50);
		gbc.fill = GridBagConstraints.BOTH;
		gbc.gridx = 0;
		gbc.gridy = 1;
		balanceMoneyPanel.add(balanceLabel,gbc);

		balanceNote.setBounds(300,0,100,50);
		gbc.fill = GridBagConstraints.BOTH;
		gbc.gridx = 1;
		gbc.gridy = 1;
		balanceMoneyPanel.add(balanceNote,gbc);

		balanceNote.setBounds(450,0,200,50);
		gbc.fill = GridBagConstraints.BOTH;
		gbc.gridx = 2;
		gbc.gridy = 1;
		balanceMoneyPanel.add(balanceDate,gbc);
		// balanceMoneyPanel.add(sp);
		// balanceMoneyPanel.add(balSecPanel);
		


		mainPanel.setBounds(0,0,300,400);
		addMoneyPanel.setBounds(0,0,300,400);
		subtractMoneyPanel.setBounds(0,0,300,400);
		balanceMoneyPanel.setBounds(0,0,300,400);
		c.setLayout(cl);
		c.add(mainPanel,"main_panel");
		c.add(addMoneyPanel,"add_money");
		c.add(subtractMoneyPanel,"sub_money");
		c.add(balanceMoneyPanel,"balance_panel");
		this.setSize(500,800);
		this.setVisible(true);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	public void actionPerformed(ActionEvent ae)
	{
		if(ae.getSource() == addButton)
		{
			cl.show(c,"add_money");
		}
		else if(ae.getSource() == back1)
		{
			cl.show(c,"main_panel");
			try{
			url = "jdbc:ucanaccess://ProjectDB.mdb";
			con = DriverManager.getConnection(url);
			stmt = con.createStatement();
			sql = "select * from MoneyTrack";
			rs = stmt.executeQuery(sql);
			int totalIncome = 0;
			int totalExp = 0;
			int value;
			query = "select sum(Money) from MoneyTrack";
			rs1 = stmt.executeQuery(query);
			rs1.next();
			int profit = rs1.getInt(1);
			if(profit > 0)
			{
				total.setForeground(new Color(3, 194, 0));
				total.setText("Profit: "+profit);
			}
			else if(profit < 0)
			{
				total.setForeground(new Color(247, 43, 2));//247, 43, 2
				total.setText("Loss: "+profit);
			}
			else
			{
				total.setForeground(new Color(3, 194, 0));
				total.setText("0");
			}
			while(rs.next())
			{
				value = rs.getInt(2);
				if(value > 0)
				{
					totalIncome = totalIncome + value;
				}
				else if(value < 0)
				{
					totalExp = totalExp + value;
				}
			}
			income.setText("Income:"+totalIncome);
			expenditure.setText("Expenditure:"+totalExp);
			con.close();
			}
			catch(Exception e)
			{
				System.out.println(e);
			}
		}
		else if(ae.getSource() == back2)
		{
			cl.show(c,"main_panel");
			try{
			url = "jdbc:ucanaccess://ProjectDB.mdb";
			con = DriverManager.getConnection(url);
			stmt = con.createStatement();
			sql = "select * from MoneyTrack";
			rs = stmt.executeQuery(sql);
			int totalIncome = 0;
			int totalExp = 0;
			int value;
			query = "select sum(Money) from MoneyTrack";
			rs1 = stmt.executeQuery(query);
			rs1.next();
			int profit = rs1.getInt(1);
			if(profit > 0)
			{
				total.setForeground(new Color(3, 194, 0));
				total.setText("Profit: "+profit);
			}
			else if(profit < 0)
			{
				total.setForeground(new Color(247, 43, 2));//247, 43, 2
				total.setText("Loss: "+profit);
			}
			else
			{
				total.setForeground(new Color(3, 194, 0));
				total.setText("0");
			}
			while(rs.next())
			{
				value = rs.getInt(2);
				if(value > 0)
				{
					totalIncome = totalIncome + value;
				}
				else if(value < 0)
				{
					totalExp = totalExp + value;
				}
			}
			income.setText("Income:"+totalIncome);
			expenditure.setText("Expenditure:"+totalExp);
			con.close();
			}
			catch(Exception e)
			{
				System.out.println(e);
			}
		}
		else if(ae.getSource() == back3)
		{
			cl.show(c,"main_panel");
		}
		else if(ae.getSource() == subtractButton)
		{
			cl.show(c,"sub_money");
		}
		else if(ae.getSource() == balanceButton)
		{
			cl.show(c,"balance_panel");
			try{
			url = "jdbc:ucanaccess://ProjectDB.mdb";
			con = DriverManager.getConnection(url);
			stmt = con.createStatement();
			sql = "select * from MoneyTrack";
			rs = stmt.executeQuery(sql);
			String notes;
			String date;
			int value;
			int i=1;
			JLabel[] jlM = new JLabel[100];
			JLabel[] jlN = new JLabel[100]; 
			JLabel[] jlD = new JLabel[100];
			int j=0;
			int y=2;
			while(j<100)
			{
				jlM[j] = new JLabel();
				jlN[j] = new JLabel();
				jlD[j] = new JLabel();
				j++;
			}
			while(rs.next())
			{
				// JLabel jlM[i];
				// JLabel jlN[i];
				value = rs.getInt(2);
				notes = rs.getString(3);
				date = rs.getString(4);
				date = date.substring(0,9);
				jlM[i].setText(""+value);
				jlN[i].setText(""+notes);
				jlD[i].setText(""+date);
				if(value > 0)
				{
					// jlM[i] = new JLabel(""+value);
					// jlN[i] = new JLabel(""+notes);
					// jlM[i].setBounds(100,(50*i),100,50);
					// jlN[i].setBounds(300,(50*i),100,50);
					jlM[i].setForeground(new Color(3, 194, 0));
					jlM[i].setFont(new Font("Verdana", Font.PLAIN, 20));
					jlN[i].setForeground(new Color(3, 194, 0));
					jlN[i].setFont(new Font("Verdana", Font.PLAIN, 20));//247, 43, 2
					jlD[i].setFont(new Font("Verdana", Font.PLAIN, 20));
					jlD[i].setForeground(new Color(3, 194, 0));
					gbc.fill = GridBagConstraints.BOTH;
					gbc.weightx=20;
					gbc.weighty=20;
					gbc.gridx = 0;
					gbc.gridy = y;
					balanceMoneyPanel.add(jlM[i],gbc);
					gbc.fill = GridBagConstraints.BOTH;
					gbc.gridx = 1;
					gbc.gridy = y;
					balanceMoneyPanel.add(jlN[i],gbc);
					gbc.fill = GridBagConstraints.BOTH;
					gbc.gridx = 2;
					gbc.gridy = y;
					balanceMoneyPanel.add(jlD[i],gbc);
				}
				else if(value < 0)
				{
					// jlM[i] = new JLabel(""+value);
					// jlN[i] = new JLabel(""+notes);
					// jlM[i].setBounds(100,(50*i),100,50);
					// jlN[i].setBounds(300,(50*i),100,50);
					jlM[i].setForeground(new Color(247, 43, 2));
					jlM[i].setFont(new Font("Verdana", Font.PLAIN, 20));
					jlN[i].setForeground(new Color(247, 43, 2));
					jlN[i].setFont(new Font("Verdana", Font.PLAIN, 20));
					jlD[i].setForeground(new Color(247, 43, 2));
					jlD[i].setFont(new Font("Verdana", Font.PLAIN, 20));
					gbc.fill = GridBagConstraints.BOTH;
					gbc.gridx = 0;
					gbc.gridy = y;
					balanceMoneyPanel.add(jlM[i],gbc);
					gbc.fill = GridBagConstraints.BOTH;
					gbc.gridx = 1;
					gbc.gridy = y;
					balanceMoneyPanel.add(jlN[i],gbc);
					gbc.fill = GridBagConstraints.BOTH;
					gbc.gridx = 2;
					gbc.gridy = y;
					balanceMoneyPanel.add(jlD[i],gbc);
				}
				y++;
				i++;
			}
			con.close();
			}
			catch(Exception e)
			{
				System.out.println(e);
			}

		}
		else if(ae.getSource() == addIncome)
		{
			try{
				url = "jdbc:ucanaccess://ProjectDB.mdb";
				con = DriverManager.getConnection(url);
				stmt = con.createStatement();
				int income = Integer.parseInt(t1.getText());
				String str = t2.getText();
				String today = dtf.format(localDate);
				upSQL = "insert into MoneyTrack(Money,Note,UploadDate) values("+income+",'"+str+"',#"+today+"#)";
				stmt.executeUpdate(upSQL);
				con.close();
				// System.out.println(upSQL);
				JOptionPane.showMessageDialog(this,"Income added");
				t1.setText("");
				t2.setText("");
			}
			catch(Exception e)
			{
				System.out.println(e);
			}	
		}
		else if(ae.getSource() == delData)
		{
			try{
				url = "jdbc:ucanaccess://ProjectDB.mdb";
				con = DriverManager.getConnection(url);
				stmt = con.createStatement();
				upSQL = "delete from MoneyTrack";
				stmt.executeUpdate(upSQL);
				con.close();
				JOptionPane.showMessageDialog(this,"Data Deleted");
			}
			catch(Exception e)
			{
				System.out.println(e);
			}	
		}
		else if(ae.getSource() == subIncome)
		{
			try{
				url = "jdbc:ucanaccess://ProjectDB.mdb";
				con = DriverManager.getConnection(url);
				stmt = con.createStatement();
				int income = - Integer.parseInt(t3.getText());
				String str = t4.getText();
				String today = dtf.format(localDate);
				upSQL = "insert into MoneyTrack(Money,Note,UploadDate) values("+income+",'"+str+"',#"+today+"#)";
				stmt.executeUpdate(upSQL);
				con.close();
				// System.out.println(upSQL);
				JOptionPane.showMessageDialog(this,"Expenditure added");
				t3.setText("");
				t4.setText("");
			}
			catch(Exception e)
			{
				System.out.println(e);
			}
		}

	}
	public static void main(String[] args) 
	{
		MoneyTracker mt = new MoneyTracker();	
	}
}