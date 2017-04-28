import java.awt.*;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.table.JTableHeader;

import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.text.*;
import java.util.*;

public class Log 
{

	private static JFrame logFrame;
	public String[] locations = {"" ,"Office", "ResLife", "Royer", "Schlosser","Myer", "Ober", "Brinser", "Founders", "Apts", "Quads","Stadium",
									  "Solar", "Brown","BSC","Library", "Disc", "Sec Checks", "RA Checks"};
	private boolean setRaCheck = true, locationCheck = false;
	public static String from, to;
	public static JTextArea logField = new JTextArea();
	private int column = 1, index = 0;
	
	private static String start = "", end = "";
	private static Object rowData[][] = new Object [30][3];
	private static Object columnNames[] = { "From:", "To:", "Activity:" };
	public static JTable table = new JTable(rowData, columnNames);
	
	private DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
	private Date date = new Date();
	private DateFormat timeFormat = new SimpleDateFormat("HH:mm");
	private Date time = new Date();
	private JComboBox[] boxes = new JComboBox[24]; 

	/**
	 * Launch the application.
	 */
	public static void Log() 
	{
		EventQueue.invokeLater(new Runnable() 
		{
			public void run() 
			{
				try 
				{
					
					Log.logFrame.setVisible(true);
					DataAdder.dataWin();
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
	public Log() 
	{
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() 
	{
		for(int i = 0; i < 3; i++)
		{	
	        if (i == 2) 
	        	table.getColumnModel().getColumn(i).setPreferredWidth(400); 
	        else 
	        	table.getColumnModel().getColumn(i).setPreferredWidth(50); 
		}
		start = "Office";
		createLog();
		placeLables();
		
		JButton bttnCheck = new JButton("Check");
		bttnCheck.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent arg0) 
			{
				if(column != 1)
				{
					int place = column-1;
					String location = start + " check";
					table.setValueAt(location, place,2);
				}
			}
		});
		bttnCheck.setBounds(10, 6, 310, 40);
		logFrame.getContentPane().add(bttnCheck);
		 ButtonGroup group = new ButtonGroup();
		 
		int j = 120;
		int k = 120;
		for(int i  = 0; i < 24; i++)
		{
			boxes[i]= new JComboBox(locations);	
			
			boxes[i].addActionListener(new ActionListener() 
			{
				public void actionPerformed(ActionEvent arg0) 
				{
			
						end = boxes[index].getSelectedItem().toString();
						if(end.equals("RA Checks") ||end.equals("Sec Checks"))
						{
							end = "Founders";
							from = to;
							table.setValueAt(from, column,0);
							table.setValueAt(boxes[index].getSelectedItem().toString(), column,2);
							start = end;
							to = timeFormat.format(time = new Date());
							table.setValueAt(to, column,1);
							boxes[index].setEnabled(false);
							column++;
						}
						else{
						ShortestPath pathFinder = new ShortestPath();
						String path = pathFinder.find(start, end);
						from = to;
						end = start;
						table.setValueAt(from, column,0);
						table.setValueAt(path, column,2);
						start = boxes[index].getSelectedItem().toString();
						to = timeFormat.format(time = new Date());
						table.setValueAt(to, column,1);
						boxes[index].setEnabled(false);
						column++;
						}
					if(index <= 22)
					{
						index++;
						boxes[index].setEnabled(true);
					}
				}
			});
			
			if(i == 0)
				boxes[i].setEnabled(true);
			else
				boxes[i].setEnabled(false);
		
			if(i < 12)
			{
				boxes[i].setBounds(10, j, 175, 20);
				j+=60;
			}
			else
			{
				boxes[i].setBounds(400, k, 175, 20);
				k+=60;
			}
			logFrame.getContentPane().add(boxes[i]);
		}
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setLocation(711, 344);
		scrollPane.setSize(440, 470);
		scrollPane.setViewportView(table);
		logFrame.getContentPane().add(scrollPane);
		
		/*Writes the elements in the table into a file*/
		JButton generatePDF = new JButton("Generate PDF");
		generatePDF.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent arg0) 
			{
				JPanel p = new JPanel(new BorderLayout());
		        p.add(logField,BorderLayout.SOUTH);
		        p.add(scrollPane,BorderLayout.NORTH);
		        JOptionPane.showMessageDialog(null, p);

		        BufferedImage bi = new BufferedImage(
		            (int)p.getSize().getWidth(),
		            (int)p.getSize().getHeight(),
		            BufferedImage.TYPE_INT_RGB
		            );

		        Graphics g = bi.createGraphics();
		        p.paint(g);
		        JTableHeader h = table.getTableHeader();
		        h.paint(g);
		      
		        JOptionPane.showMessageDialog(null, new JLabel(new ImageIcon(bi)));
		        //Paint the jpg and exit. (Log completion) May change, do not exit on log generation.
		        try
		        {
					ImageIO.write(bi,"png",new File(System.getProperty("user.dir")+"\\UserLogs\\"+FrontEnd.username+"'s generated log.png"));
					JOptionPane.showMessageDialog(null, "Your log has been generated! Check your folder!");
					System.exit(0);
				}catch(IOException e) 
		        {
					e.printStackTrace();
				}
			}
		});
		generatePDF.setBounds(367, 3, 310, 43);
		logFrame.getContentPane().add(generatePDF);
	}
	
	private void createLog()
	{
		logFrame = new JFrame("Log v1.0");
		logFrame.setBounds(100, 100, 1181, 885);
		logFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		logFrame.getContentPane().setLayout(null);
		logFrame.setResizable(true);
		logField.setBounds(711, 28, 440, 289);
		logField.setText("SPO Information: "+"\t\t\t"+dateFormat.format(date));
		logFrame.getContentPane().add(logField);
	}
	
	private void placeLables()
	{
		JLabel lblLocations = new JLabel("Locations");
		lblLocations.setBounds(10, 57, 94, 14);
		logFrame.getContentPane().add(lblLocations);
		
		JLabel lblLogPreview = new JLabel("Log Preview:");
		lblLogPreview.setBounds(711, 3, 113, 14);
		logFrame.getContentPane().add(lblLogPreview);
	}
}
