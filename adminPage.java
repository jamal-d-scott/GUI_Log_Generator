import java.awt.EventQueue;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class adminPage 
{

	private JFrame frame;
	private static Object rowData[][] = new Object [20][3];
	private static Object columnNames[] = { "First:", "Last:", "SPO Number:"};
	public static JTable table = new JTable(rowData, columnNames);

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
		
		System.out.print(FrontEnd.listOfFiles[0]);
		
		
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
				RegisterWindow regWin = new RegisterWindow();
				regWin.register();
			}
		});
		btnAddUser.setBounds(113, 410, 100, 23);
		frame.getContentPane().add(btnAddUser);
		
		JButton btnDeleteUser = new JButton("Delete User");
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
}
