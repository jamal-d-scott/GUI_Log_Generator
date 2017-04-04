import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.JTextField;
import javax.swing.JProgressBar;
import javax.swing.JTextArea;
import javax.swing.SpringLayout;
import java.awt.event.ActionListener;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.awt.event.ActionEvent;
public class DataAdder 
{
	private JFrame data;
	private JTextField partnerNameField, partnerNumberField,userKeys, userRadio, userFlashlight,partnerKeys, partnerRadio, partnerFlashlight, assignmentField;
	public static String getPName = "Partner Name: ", getPNumber = "Partner Spo Number: ";
	private DateFormat timeFormat = new SimpleDateFormat("HH:mm");
	private Date time = new Date();
	/**
	 * Launch the application.
	 */
	public static void dataWin() 
	{
		EventQueue.invokeLater(new Runnable() 
		{
			public void run() 
			{
				try 
				{
					DataAdder window = new DataAdder();
					window.data.setVisible(true);
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
	public DataAdder()
	{
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	public void initialize() 
	{
		data = new JFrame();
		data.setBounds(100, 100, 353, 533);
		data.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		data.setResizable(true);
		data.getContentPane().setLayout(null);
		populateFrame();
		
		JButton logAdditionalInfo = new JButton("Log Info");
		logAdditionalInfo.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent e) 
			{
				AppendToLog();
				data.dispose();
			}
		});
		logAdditionalInfo.setBounds(43, 457, 89, 23);
		data.getContentPane().add(logAdditionalInfo);
		
		JButton btnCancel = new JButton("Cancel");
		btnCancel.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent arg0)
			{
				System.exit(0);
			}
		});
		btnCancel.setBounds(206, 457, 89, 23);
		data.getContentPane().add(btnCancel);
	}
	
	void AppendToLog()
	{	
		Log.to = timeFormat.format(time);
		Log.table.setValueAt("X",0,0);
		Log.table.setValueAt(Log.to,0,1);
		Log.table.setValueAt("Office",0,2);
		
		//Appends the Log writter's information first
		Log.logField.append("\n"+FrontEnd.SPOInformation[2]+" "+FrontEnd.SPOInformation[3]+"\n"+FrontEnd.SPOInformation[4]+"\nIn-time: "+timeFormat.format(time));
		
		if(partnerNameField.getText().equals(""))
		{	
			Log.logField.append("\n\nPartner Name: "+"\nPartner SPO Number: "+"\nIn-time: ");
			Log.logField.append("\n\n"+FrontEnd.SPOInformation[2]+ " "+FrontEnd.SPOInformation[3]+"'s equipment:"+"\nKeys: "+userKeys.getText()+"\t"+"Radio: "+userRadio.getText()+"\t"+"Flashlight: "+userFlashlight.getText());
			Log.logField.append("\n\nPartner equipment:"+"\nKeys: "+partnerKeys.getText()+"\t"+"Radio: "+partnerRadio.getText()+"\t"+"Flashlight: "+partnerFlashlight.getText());
		}
		else
		{
			Log.logField.append("\n\n"+partnerNameField.getText() + "\nSPO"+partnerNumberField.getText()+"\nIn-time: "+timeFormat.format(time));
			Log.logField.append("\n\n"+FrontEnd.SPOInformation[2]+ " "+FrontEnd.SPOInformation[3]+"'s equipment:"+"\nKeys: "+userKeys.getText()+"\t"+"Radio: "+userRadio.getText()+"\t"+"Flashlight: "+userFlashlight.getText());
			Log.logField.append("\n\n"+partnerNameField.getText()+"'s equipment:"+"\nKeys: "+partnerKeys.getText()+"\t"+"Radio: "+partnerRadio.getText()+"\t"+"Flashlight: "+partnerFlashlight.getText());
		}
		Log.logField.append("\n\nPatrol Assignment: "+assignmentField.getText());
	}
	
	//Adds all of the GUI elements to the frame
	void populateFrame()
	{
		partnerNameField = new JTextField();
		partnerNameField.setBounds(140, 202, 166, 20);
		data.getContentPane().add(partnerNameField);
		partnerNameField.setColumns(10);
		
		partnerNumberField = new JTextField();
		partnerNumberField.setBounds(140, 239, 166, 20);
		data.getContentPane().add(partnerNumberField);
		partnerNumberField.setColumns(10);
		
		JLabel lblpartnerName = new JLabel("Partner name:");
		lblpartnerName.setBounds(20, 191, 110, 14);
		data.getContentPane().add(lblpartnerName);
		
		JLabel lblpartnerNumber = new JLabel("Partner number:");
		lblpartnerNumber.setBounds(20, 241, 120, 14);
		data.getContentPane().add(lblpartnerNumber);
		
		JLabel lblEnterAdditionalInformation = new JLabel("Enter Additional information here:");
		lblEnterAdditionalInformation.setBounds(83, 11, 249, 14);
		data.getContentPane().add(lblEnterAdditionalInformation);
		
		JLabel userLable = new JLabel("Your keys:");
		userLable.setBounds(20, 71, 96, 14);
		data.getContentPane().add(userLable);
		
		JLabel lblnameRadioHere = new JLabel("Your radio:");
		lblnameRadioHere.setBounds(20, 108, 109, 14);
		data.getContentPane().add(lblnameRadioHere);
		
		JLabel lblnameFlashlightHere = new JLabel("Your flashlight:");
		lblnameFlashlightHere.setBounds(20, 145, 109, 14);
		data.getContentPane().add(lblnameFlashlightHere);
		
		userKeys = new JTextField();
		userKeys.setBounds(140, 68, 166, 20);
		data.getContentPane().add(userKeys);
		userKeys.setColumns(10);
		
		userRadio = new JTextField();
		userRadio.setBounds(139, 105, 167, 20);
		data.getContentPane().add(userRadio);
		userRadio.setColumns(10);
		
		userFlashlight = new JTextField();
		userFlashlight.setBounds(140, 142, 166, 20);
		data.getContentPane().add(userFlashlight);
		userFlashlight.setColumns(10);
		
		JLabel lblpartnerKeys = new JLabel("Partner keys:");
		lblpartnerKeys.setBounds(20, 279, 109, 14);
		data.getContentPane().add(lblpartnerKeys);
		
		JLabel lblpartnerRadio = new JLabel("Partner radio:");
		lblpartnerRadio.setBounds(20, 316, 109, 14);
		data.getContentPane().add(lblpartnerRadio);
		
		JLabel lblNewLabel = new JLabel("Partner flashlight:");
		lblNewLabel.setBounds(20, 354, 109, 14);
		data.getContentPane().add(lblNewLabel);
		
		partnerKeys = new JTextField();
		partnerKeys.setBounds(140, 276, 166, 20);
		data.getContentPane().add(partnerKeys);
		partnerKeys.setColumns(10);
		
		partnerRadio = new JTextField();
		partnerRadio.setBounds(140, 313, 166, 20);
		data.getContentPane().add(partnerRadio);
		partnerRadio.setColumns(10);
		
		partnerFlashlight = new JTextField();
		partnerFlashlight.setBounds(140, 350, 166, 20);
		data.getContentPane().add(partnerFlashlight);
		partnerFlashlight.setColumns(10);
		
		JLabel lblPatrolAssignment = new JLabel("Patrol Assignment:");
		lblPatrolAssignment.setBounds(20, 414, 109, 14);
		data.getContentPane().add(lblPatrolAssignment);
		
		assignmentField = new JTextField();
		assignmentField.setBounds(140, 411, 166, 20);
		data.getContentPane().add(assignmentField);
		assignmentField.setColumns(10);
		
		JLabel lblFirstLast = new JLabel("(First & Last Name) ");
		lblFirstLast.setBounds(20, 205, 110, 14);
		data.getContentPane().add(lblFirstLast);
	}
}
