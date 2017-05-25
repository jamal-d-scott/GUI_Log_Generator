/*==================================
 * Jamal D'eShaun Scott            =
 * Campus Security				   =	
 * Student Log Software v1.0       =
 * 11/15/16						   =	
 *=================================*/

import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.io.*;
public class FrontEnd 
{
	private JFrame frame;
	public static String username = "";
	private String password = "";
	private String firstName;
	private String lastName;
	private String ModSPONumber;
	private String SAnswer;
	private JTextField userNameField;
	private JPasswordField passwordField;
	private String[] securityQuestions = {"What was your first pet's name?", "What city were you born in?", "What is your favorite color?", "What is your email address?" };
	
	/*SPO Information containes the information from the .SOFiles, it is public so we can use across
	*multiple classes without having to make new arrays.
	*/
	public static String[] SPOInformation = new String[7];
	public static File[] listOfFiles;
	private boolean loggingIn = false;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) 
	{
		EventQueue.invokeLater(new Runnable() 
		{
			public void run() 
			{
				try 
				{
					FrontEnd window = new FrontEnd();
					window.frame.setVisible(true);
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
	public FrontEnd() 
	{
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() 
	{
		/*
		 * Creates the frame that will display the the login, register, and account recovery options.
		 */
		frame = new JFrame("SPO Logger Pro");
		frame.setBounds(100, 100, 412, 191);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		frame.setResizable(false);
		
		JButton btnLogin = new JButton("Login");
		btnLogin.addActionListener(new ActionListener() 
		{
			@SuppressWarnings("deprecation")
			public void actionPerformed(ActionEvent e) 
			{
				try 
				{
				    /*
					* Stores the password and user name entered into their respective variables.
					* passwordField.getText(); (scratched) hides the password.
					*/
					SecureHash256 SHA256 = new SecureHash256();
					username = userNameField.getText();
					password = passwordField.getText();
					/*
					 * Lets the program know that the user is logging in, this helps the program
					 * to determine what functions to use in terms of reading in.
					 */
					loggingIn = true;
					
					/*
					 * Calls the methods to determine if the user exists and that the
					 * given password matches the user name. If both conditions are true
					 * execute statements inside the 'if'.
					 */
					if(CheckUserNamePW()&&ReadIn())
					{
						/*
						 * Close the main window application to open up another
						 * Tell the program that we're not logging in anymore.
						 * Open up the log window.
						 */
						
						if(username.equals("admin"))
						{
							loggingIn = false;
							adminPage adminP = new adminPage();
							adminP.administration();
						}
						else
						{
							frame.dispose();
							loggingIn = false;
							Log log = new Log();
							log.Log();
						}
					}
					else
					{
						/*
						 * If the file does not exist (determined by the 'ReadIn()' function or the program found that
						 * the user name and password do not match, then give an error message and tell the program
						 * that we're not logging in anymore.
						 */
						JOptionPane.showMessageDialog(frame, "Error logging in. \n\nUser/Password combination is incorrect.");
						loggingIn = false;
					}
				} 
				catch (IOException e1) 
				{
					e1.printStackTrace();
				}
			}
		});
		btnLogin.setBounds(94, 73, 89, 23);
		frame.getContentPane().add(btnLogin);
		
		JButton btnRegister = new JButton("Register");
		btnRegister.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent arg0) 
			{
				/*
				 * When the register button is clicked, open up the register window.
				 */
				RegisterWindow regWin = new RegisterWindow();
				regWin.register();
			}
		});
		btnRegister.setBounds(208, 73, 89, 23);
		frame.getContentPane().add(btnRegister);
		
		userNameField = new JTextField();
		userNameField.setBounds(94, 11, 203, 20);
		frame.getContentPane().add(userNameField);
		userNameField.setColumns(10);
		
		passwordField = new JPasswordField();
		passwordField.setBounds(94, 42, 203, 20);
		frame.getContentPane().add(passwordField);
		passwordField.setColumns(10);
		
		JLabel lblUsername = new JLabel("Username: ");
		lblUsername.setBounds(20, 14, 65, 14);
		frame.getContentPane().add(lblUsername);
		
		JLabel lblPassword = new JLabel("Password: ");
		lblPassword.setBounds(20, 45, 65, 14);
		frame.getContentPane().add(lblPassword);
		
		JButton btnForgotPassword = new JButton("Forgot User/Password");
		btnForgotPassword.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent arg0) 
			{
				firstName = JOptionPane.showInputDialog(frame, "What is your first name?");
				lastName = JOptionPane.showInputDialog(frame, "What is your last name?");
				ModSPONumber = JOptionPane.showInputDialog(frame, "What is your SPO number?");
				
				if(CheckInput())
				{
					try 
					{
						if(ReadIn())
						{
							//String toto = SPOInformation[5];
							int recoveryAnswer = Integer.parseInt(SPOInformation[5]);
							SAnswer = JOptionPane.showInputDialog(frame, "Answer the security question that you selected upon registration: "+securityQuestions[recoveryAnswer]);
							
							if(SPOInformation[2].equals(firstName)&&SPOInformation[3].equals(lastName)&&SPOInformation[4].equals(ModSPONumber)&&SPOInformation[6].equals(SAnswer))
							{
								JOptionPane.showMessageDialog(frame, "Account recovered:\n\nUsername: "+SPOInformation[0]+"\n\nPassword: "+SPOInformation[1]);
							}
							else
							{
								JOptionPane.showMessageDialog(frame, "Sorry, we could not verify your account.");
							}
						}
						else
						{
							JOptionPane.showMessageDialog(frame, "Sorry, we could not verify your account.");
						}
					} 
					catch (IOException e) 
					{
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
			
		});
		btnForgotPassword.setBounds(94, 116, 203, 23);
		frame.getContentPane().add(btnForgotPassword);
	}
	
	private boolean CheckInput()
	{
		if(firstName == null ||firstName.contains(" ")||firstName.isEmpty())
		{
			JOptionPane.showMessageDialog(frame, "Invalid Entry on 'first name'!");
			return false;
		}
		if(lastName == null ||lastName.contains(" ")||lastName.isEmpty())
		{
			JOptionPane.showMessageDialog(frame, "Invalid Entry on 'last name'!");
			return false;
		}
		if(ModSPONumber == null || ModSPONumber.contains(" ")||ModSPONumber.isEmpty())
		{
			JOptionPane.showMessageDialog(frame, "Invalid Entry on 'SPO Number'!");
			return false;
		}
		else
		{
			try
			{
				//int num = Integer.parseInt(ModSPONumber);
				ModSPONumber = "SPO"+ModSPONumber;
				return true;
			}
			catch(NumberFormatException e)
			{
				JOptionPane.showMessageDialog(frame, "Error! Invalid SPO number.");
				return false;
			}
		}
	}
	
	@SuppressWarnings("resource")
	private boolean ReadIn() throws IOException
	{
		SecureHash256 SHA256 = new SecureHash256();
		
		BufferedReader in;
		char c;
		String fileContents = "";
		
		//Reads in all of the names of files in the directory
		File folder = new File(System.getProperty("user.dir")+"\\Users\\");
		listOfFiles = folder.listFiles();
		
		//Loops through the list of the file names
		for (int i = 0; i < listOfFiles.length; i++) 
		{
			//Opens each file and puts the file contents into a string
			in = new BufferedReader(new FileReader(listOfFiles[i]));
			while(( c = (char)in.read() ) != (char)-1 )
			{
				fileContents += c;
			}
			//Sees if the string of file contents contains the information we're looking for
			//If it does contain the information, then it is the file/ account that we need to recover.
			if(loggingIn&&fileContents.contains(username))
			{
				PopulateInformation(fileContents);
				
				System.out.println("FILE PASS: "+SHA256.Hash(SPOInformation[1]));
				System.out.println("UIN+ PASS: "+SHA256.Hash(password));
				
				if(SPOInformation[0].equals(username)&&SPOInformation[1].equals(password))
				{
					JOptionPane.showMessageDialog(frame, "Log in successful.");
					loggingIn = false;
					return true;
				}
				else
				{
					SPOInformation = new String[7];
				}
			}
			else if(!(loggingIn)&&fileContents.contains(firstName)&&fileContents.contains(lastName)&&fileContents.contains(ModSPONumber))
			{
				PopulateInformation(fileContents);
				//Confirms that this is the file that we need to recover and all contents are filled in.
				return true;
			}
			else
			//Resets the contents in case we didn't find the file.
			fileContents = "";
		}
		return false;
	}
	
	private void PopulateInformation(String fileContents)
	{
		char currentChar;
		String currentWord = "";
		int j = 0;
		
		while(j < SPOInformation.length)
		{
			//Seperates the contents of the file (which is stored in a string) into their respective array cells
			//Arr[0] = username Arr[1] = password Arr[2] Firstname Arr[3]  = Lastname.....
			for(int k = 0; k <fileContents.length(); k++)
			{
				currentChar = fileContents.charAt(k);
				currentWord += currentChar;
				if(currentChar == ' ' ||currentChar == '\n')
				{
					SPOInformation[j] = currentWord.replaceAll("\\s","");
					j++;
					currentWord = "";
				}
			}
		}
	}
	
	private boolean CheckUserNamePW()
	{
		if(username == null ||username.contains(" ")||username.isEmpty())
		{
			JOptionPane.showMessageDialog(frame, "Invalid Entry on 'username'!");
			return false;
		}
		if(password == null ||password.contains(" ")||password.isEmpty())
		{
			JOptionPane.showMessageDialog(frame, "Invalid Entry on 'password'!");
			return false;
		}
		return true;
	}
}
