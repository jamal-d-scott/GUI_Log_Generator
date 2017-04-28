import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.io.*;
import java.util.*;
public class RegisterWindow 
{
	private JFrame frmRegister;
	private JTextField usernameField, lastNameField,firstNameField, spoNumberField, securityAnswer; 
	private JPasswordField passwordField;
	private String username = "", password = "", firstName, lastName, SAnswer;
	private int SQuestion, SPONumber;
	private String[] securityQuestions = {"What was your first pet's name?", "What city were you born in?", "What is your favorite color?", "What is your email address?" };
	/**
	 * Launch the application.
	 */
	public static void register()
	{
		EventQueue.invokeLater(new Runnable() 
		{
			public void run() 
			{
				try 
				{
					RegisterWindow window = new RegisterWindow();
					window.frmRegister.setVisible(true);
				} 
				catch (Exception e) 
				{
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public RegisterWindow() 
	{
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() 
	{
		frmRegister = new JFrame();
		frmRegister.setTitle("Register");
		frmRegister.setBounds(200, 200, 449, 364);
		frmRegister.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmRegister.getContentPane().setLayout(null);
		frmRegister.setResizable(false);
		
		usernameField = new JTextField();
		usernameField.setBounds(133, 73, 220, 20);
		frmRegister.getContentPane().add(usernameField);
		usernameField.setColumns(10);
		
		passwordField = new JPasswordField();
		passwordField.setBounds(133, 104, 220, 20);
		frmRegister.getContentPane().add(passwordField);
		passwordField.setColumns(10);
		
		JButton btnRegister = new JButton("Register");
		btnRegister.setBounds(133, 285, 89, 23);
		frmRegister.getContentPane().add(btnRegister);
		
		JButton btnCancel = new JButton("Cancel");
		btnCancel.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent e) 
			{
				frmRegister.dispose();
			}
		});
		btnCancel.setBounds(253, 285, 89, 23);
		frmRegister.getContentPane().add(btnCancel);
		
		JLabel lblUsername = new JLabel("Username");
		lblUsername.setBounds(40, 76, 83, 14);
		frmRegister.getContentPane().add(lblUsername);
		
		JLabel lblPassword = new JLabel("Password");
		lblPassword.setBounds(40, 107, 73, 14);
		frmRegister.getContentPane().add(lblPassword);
		
		JLabel lblFirst = new JLabel("First");
		lblFirst.setBounds(40, 17, 46, 14);
		frmRegister.getContentPane().add(lblFirst);
		
		firstNameField = new JTextField();
		firstNameField.setBounds(133, 11, 220, 20);
		frmRegister.getContentPane().add(firstNameField);
		firstNameField.setColumns(10);
		
		lastNameField = new JTextField();
		lastNameField.setBounds(134, 42, 219, 20);
		frmRegister.getContentPane().add(lastNameField);
		lastNameField.setColumns(10);
		
		JLabel lblLast = new JLabel("Last");
		lblLast.setBounds(40, 42, 46, 14);
		frmRegister.getContentPane().add(lblLast);
		
		JComboBox comboBox = new JComboBox(securityQuestions);
		//Default
		SQuestion = 0;
		comboBox.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent arg0)
			{
				SQuestion = Arrays.asList(securityQuestions).indexOf((String) comboBox.getSelectedItem());
			}
		});
		comboBox.setBounds(122, 203, 220, 30);
		frmRegister.getContentPane().add(comboBox);
		
		JLabel lblSelectASecurity = new JLabel("Select a security question for account recovery: ");
		lblSelectASecurity.setBounds(102, 154, 286, 63);
		frmRegister.getContentPane().add(lblSelectASecurity);
		
		spoNumberField = new JTextField();
		spoNumberField.setBounds(133, 135, 220, 20);
		frmRegister.getContentPane().add(spoNumberField);
		spoNumberField.setColumns(10);
		
		JLabel lblSpoNumber = new JLabel("SPO Number");
		lblSpoNumber.setBounds(40, 141, 83, 14);
		frmRegister.getContentPane().add(lblSpoNumber);
		
		securityAnswer = new JTextField();
		securityAnswer.setBounds(122, 244, 220, 20);
		frmRegister.getContentPane().add(securityAnswer);
		securityAnswer.setColumns(10);
		
		JLabel lblAnswer = new JLabel("Answer");
		lblAnswer.setBounds(67, 247, 46, 14);
		frmRegister.getContentPane().add(lblAnswer);
		btnRegister.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent arg0) 
			{	
				try 
				{
					if(CheckUser()&&CheckPassword()&&CheckFirstLastName()&&CheckSPONumber()&&CheckSecurityAnswer()&&isValidUser())
					{
						RegisterUser(username,password);
						JOptionPane.showMessageDialog(frmRegister, "Your account has been created.");
						frmRegister.dispose();
					}
				} 
				catch (HeadlessException | IOException e) 
				{
					e.printStackTrace();
				}
			}
		});
		
	}
	
	private boolean CheckPassword()
	{
		password = passwordField.getText();
		if(password.isEmpty())
		{
			JOptionPane.showMessageDialog(frmRegister, "Password cannot be empty!");
			return false;
		}
		if(password.contains(" "))
		{
			JOptionPane.showMessageDialog(frmRegister, "Password cannot contain spaces!");
			return false;
		}
		if(password.equals(username))
		{
			JOptionPane.showMessageDialog(frmRegister, "Password must differ from your username.");
			return false;
		}
		return true;
	}
	
	private boolean CheckFirstLastName()
	{
		firstName = firstNameField.getText();
		if(firstName.isEmpty())
		{
			JOptionPane.showMessageDialog(frmRegister, "First Name cannot be empty!");
			return false;
		}
		if(firstName.contains(" "))
		{
			JOptionPane.showMessageDialog(frmRegister, "First Name cannot contain spaces!");
			return false;
		}
		
		lastName = lastNameField.getText();
		if(lastName.isEmpty())
		{
			JOptionPane.showMessageDialog(frmRegister, "Last Name cannot be empty!");
			return false;
		}
		if(lastName.contains(" "))
		{
			JOptionPane.showMessageDialog(frmRegister, "Last Name cannot contain spaces!");
			return false;
		}
			
		return true;
	}
	
	private boolean CheckSPONumber()
	{
		try
		{
			if(spoNumberField.equals("") || spoNumberField == null)
			{
				JOptionPane.showMessageDialog(frmRegister, "Error! Invalid SPO number.");
				return false;
			}
			SPONumber = Integer.parseInt(spoNumberField.getText());
			return true;
		}
		catch(NumberFormatException e)
		{
			JOptionPane.showMessageDialog(frmRegister, "Error! Invalid SPO number.");
			return false;
		}
	}
	
	private boolean CheckSecurityAnswer()
	{
		SAnswer = securityAnswer.getText();
		if(SAnswer.isEmpty())
		{
			JOptionPane.showMessageDialog(frmRegister, "Security Answer may not be blank.");
			return false;
		}
		return true;
	}
	
	@SuppressWarnings("resource")
	private boolean CheckUser() throws IOException
	{ 
		username = usernameField.getText();
		if(username.isEmpty())
		{
			JOptionPane.showMessageDialog(frmRegister, "Username cannot be empty!");
			return false;
		}
		if(username.contains(" "))
		{
			JOptionPane.showMessageDialog(frmRegister, "Username cannot contain spaces!");
			return false;
		}
		
		File folder = new File(System.getProperty("user.dir")+"\\Users\\");
		File[] listOfFiles = folder.listFiles();
		BufferedReader in;
		String fileContents = "";
		char c;
		if(CheckSPONumber())
		{
			SPONumber = Integer.parseInt(spoNumberField.getText());
		}
		else
			return false;
		
		String ModSPONumber = "SPO"+Integer.toString(SPONumber);

		if(listOfFiles != null)
		{
			for (int i = 0; i < listOfFiles.length; i++) 
			{
				if(listOfFiles[i].toString().equals(System.getProperty("user.dir")+"\\Users\\"+username +".SPOFile"))
				{
					JOptionPane.showMessageDialog(frmRegister, "ERROR: This account already exists!");
					return false;
				}
				
				in = new BufferedReader(new FileReader(listOfFiles[i]));
				while(( c = (char)in.read() ) != (char)-1 )
				{
					fileContents += c;
				}
				if(fileContents.contains(ModSPONumber))
				{
					JOptionPane.showMessageDialog(frmRegister, "ERROR: This SPO number already exists!");
					return false;
				}
			}
		}
		else
			RegisterUser(username, password);
		return true;
	}
	
	private void RegisterUser(String username, String password)
	{
		try
		{
			File file = new File(System.getProperty("user.dir")+"\\Users\\"+username +".SPOFile");
			FileWriter out = new FileWriter(file,true); //the true will append the new data
			String ModSPONumber = "SPO"+Integer.toString(SPONumber);
			out.write(username+" "+password+" "+firstName+" "+lastName+" "+ModSPONumber+" "+SQuestion+" "+SAnswer+System.getProperty("line.separator"));
			file.setReadOnly();
			out.close();
		}
		catch(IOException ioe)
		{
		    System.err.println("IOException: " + ioe.getMessage());
		}
	}
	private boolean isValidUser()
	{
		String account = "";
		BufferedReader in;
		char c;
		
		if(username.equals("admin"))
			return true;
		
		try
		{
			File file = new File(System.getProperty("user.dir")+"\\VerifiedUsers\\Validated Users.vuf");
			if(file.exists() == false)
			{
				JOptionPane.showMessageDialog(frmRegister, "Error, could not initialize registration, the system admin needs to set up accounts for access.");
				frmRegister.dispose();
				return false;
			}
			
			in = new BufferedReader(new FileReader(file));
			while(( c = (char)in.read() ) != (char)-1 )
			{
				account += c;
			}
		}
		catch(IOException ioe){}
		
		String searchString = firstName+" "+lastName+" "+"SPO"+SPONumber;
		if(account.contains(searchString))
			return true;
		
		if(account.contains("#Open-Beta/ALL"))
			return true;
		
		JOptionPane.showMessageDialog(frmRegister, "Error, you're not authorized to register for this application");
		return false;
	}
}
