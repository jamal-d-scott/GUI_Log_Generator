import java.awt.EventQueue;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.awt.event.ActionEvent;

public class adminPage 
{

	private JFrame frame;
	private static Object rowData[][] = new Object [20][3];
	private static Object columnNames[] = { "First:", "Last:", "SPO Number:"};
	private static JTable table = new JTable(rowData, columnNames);
	private static String accounts = "";

	/**
	 * Launch the application.
	 */
	public static void administration() 
	{
		EventQueue.invokeLater(new Runnable() 
		{
			public void run() 
			{
				try 
				{
					adminPage window = new adminPage();
					window.frame.setVisible(true);
				} catch (Exception e) 
				{
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public adminPage() 
	{
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() 
	{
		for(int i = 0; i < 2; i++)
		{	
	        if (i == 0 || i == 1) 
	        	table.getColumnModel().getColumn(i).setPreferredWidth(200); 
	        else 
	        	table.getColumnModel().getColumn(i).setPreferredWidth(300); 
		}
		
		populateTable();
			
		frame = new JFrame();
		frame.setBounds(100, 100, 698, 494);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JLabel lblRegistered = new JLabel("Registered Users");
		lblRegistered.setBounds(21, 21, 122, 14);
		frame.getContentPane().add(lblRegistered);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setLocation(21, 45);
		scrollPane.setSize(630, 348);
		scrollPane.setViewportView(table);
		frame.getContentPane().add(scrollPane);
		
		JButton btnAddUser = new JButton("Add User");
		btnAddUser.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent arg0) 
			{
				addUser();
			}
		});
		btnAddUser.setBounds(113, 410, 100, 23);
		frame.getContentPane().add(btnAddUser);
		
		JButton btnDeleteUser = new JButton("Delete User");
		btnDeleteUser.addActionListener(new ActionListener() 
		{
			//TODO DELETE USER.SPOFILE***********
			public void actionPerformed(ActionEvent arg0) 
			{
				int row = table.getSelectedRow();
				String toDelete =  table.getModel().getValueAt(row, 0).toString();
				toDelete +=  table.getModel().getValueAt(row, 1).toString();
				toDelete +=  table.getModel().getValueAt(row, 2).toString();
				accounts = accounts.replace(toDelete,"");
				
				try
				{
					FileWriter out = new FileWriter(System.getProperty("user.dir")+"\\VerifiedUsers\\Validated Users.vuf",false);
					out.write(accounts);
					out.close();
					frame.dispose();
					JOptionPane.showMessageDialog(frame, "User entry :"+toDelete+" has been deleted.");
					administration();
				}
				catch(IOException e)
				{
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
		btnDeleteUser.setBounds(223, 410, 108, 23);
		frame.getContentPane().add(btnDeleteUser);
		
		JButton btnCancel = new JButton("Cancel");
		btnCancel.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent e) 
			{
				frame.dispose();
			}
		});
		btnCancel.setBounds(462, 410, 112, 23);
		frame.getContentPane().add(btnCancel);
		
		JButton btnResetPassword = new JButton("Reset Password");
		btnResetPassword.setBounds(340, 410, 112, 23);
		frame.getContentPane().add(btnResetPassword);
	}
	
	private void addUser()
	{
		try
		{
			FileWriter out = new FileWriter(System.getProperty("user.dir")+"\\VerifiedUsers\\Validated Users.vuf",true); //the true will append the new data
			String first= JOptionPane.showInputDialog(frame, "What is the user's first name?");
			String last = JOptionPane.showInputDialog(frame, "What is the user's last name?");
			String number = JOptionPane.showInputDialog(frame, "What is the user's SPO number?");
			out.write(first +" "+last+" SPO"+number+"\n");
			out.close();
			populateTable();
		}
		catch(IOException ioe){}
	}
	
	private void populateTable()
	{
		BufferedReader in;
		String data = "", account = "";
		int currentUser = 0, location = 0;
		char c;
		
		try
		{
			File file = new File(System.getProperty("user.dir")+"\\VerifiedUsers\\Validated Users.vuf");
			if(file.exists() == false)
			{
				return;
			}
			in = new BufferedReader(new FileReader(file));
			while(( c = (char)in.read() ) != (char)-1 )
			{
				account += c;
			}
			
			if(account.contains("#Open-Beta/ALL"));
				account = account.replace("#Open-Beta/ALL\n","");
			accounts = account;
			
			for(int i = 0; i < account.length(); i++)
			{
				c = account.charAt(i);
				data+=c;
				
				if(c == ' ')
				{
					table.setValueAt(data,currentUser,location);
					location++;
					data = "";
				}
				if(c == '\n' || c == '\r')
				{
					table.setValueAt(data,currentUser,location);
					data = "";
					currentUser++;
					location = 0;
				}
			}
		}
		catch(IOException ioe){}
	}
}
