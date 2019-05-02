import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.Scanner;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSlider;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class HammingDistance
{
	private static final int FRAME_WIDTH = 800;
	private static final int FRAME_HEIGHT = 900;
	
	private JFrame window;
	private JPanel hammingDistPanel;
	
	private String[] stationsArray;
	private ArrayList<String> stations = new ArrayList<String>();
	private ArrayList<String> allCities;
	
	private Scanner scnr = new Scanner(new File("Mesonet.txt"));

	private Scanner scnr2 = new Scanner(new File("Mesonet.txt"));
	
	private int selectedDistance;
	JComboBox<String> stationDropDown;
	private String selectedStation;
	private JLabel message;
		
	public static void main(String[] args) throws FileNotFoundException
	{
		new HammingDistance();
	}
	
	public HammingDistance() throws FileNotFoundException
	{
		/**
		 * Window
		 */
		window = new JFrame();
		window.setSize(FRAME_WIDTH,FRAME_HEIGHT);
	
		/**
		 * Main Panel
		 */
		hammingDistPanel = new JPanel();
		hammingDistPanel.setLayout(new GridLayout(0, 2));
		hammingDistPanel.setBorder(new EmptyBorder(new Insets(10, 10, 10, 10)));
		
		/**
		 * Sub Panels
		 */
		JPanel leftSide = new JPanel();
		leftSide.setLayout(new BoxLayout(leftSide, BoxLayout.Y_AXIS));
		
		JPanel rightSide = new JPanel();
		rightSide.setLayout(new BoxLayout(rightSide, BoxLayout.Y_AXIS));
		
		JPanel enter = new JPanel();
		enter.setLayout(new BoxLayout(enter, BoxLayout.X_AXIS));
		
		JPanel slider = new JPanel();
		slider.setLayout(new BoxLayout(slider, BoxLayout.X_AXIS));
		
		JPanel showButton = new JPanel();
		showButton.setLayout(new BoxLayout(showButton, BoxLayout.X_AXIS));

		JPanel compare = new JPanel();;
		compare.setLayout(new GridLayout(0, 2));
		
		JPanel distanceRects = new JPanel();
		distanceRects.setLayout(new GridLayout(0, 2));

		JPanel botLeft = new JPanel();
		botLeft.setLayout(new GridLayout(0, 2));
		
		
		/**
		 * Components
		 */
		
		JLabel enterHammDist = new JLabel("Enter Hamming Distance:         ");
		
		
		JSlider hammDistSlider = new JSlider(1,4);
		hammDistSlider.setMajorTickSpacing(1);
		hammDistSlider.setPaintTicks(true);
		hammDistSlider.setPaintLabels(true);
		Hashtable<Integer, JLabel> position = new Hashtable<Integer, JLabel>();
		position.put(1, new JLabel("1"));
		position.put(2, new JLabel("2"));
		position.put(3, new JLabel("3"));
		position.put(4, new JLabel("4"));
		hammDistSlider.setLabelTable(position);
		
		
		int val = hammDistSlider.getValue();
		JLabel hammDistBox = new JLabel(Integer.toString(val));
		hammDistSlider.addChangeListener(new ChangeListener() {
	        @Override
	        public void stateChanged(ChangeEvent ce) {
	        	selectedDistance = (((JSlider) ce.getSource()).getValue());
	        	String selectedDist = Integer.toString(selectedDistance);
	        	hammDistBox.setText(selectedDist);
	        }
	    });
		
		JButton showStation = new JButton("Show Station");
		JTextArea stationList = new JTextArea(15, 20);
	
		JScrollPane stationsField = new JScrollPane(stationList);
		stationsField.setBounds(10,60,780,500);
		stationsField.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		stationsField.setBorder(new LineBorder(Color.BLACK));
		window.getContentPane().add(stationsField);
		
		JLabel compareWith = new JLabel("Compare with:");
		
	
		String stationID;
		
		while (scnr2.hasNext())
		{
		    stationID = scnr2.next();
		    stations.add(stationID);
		    scnr2.nextLine();
		}
		scnr2.close();
		stationsArray = new String[stations.size()];
		for(int i = 0; i < stationsArray.length; i++)
		{
		    stationsArray[i] = stations.get(i);
		}
		stationDropDown = new JComboBox<String>(stationsArray);
		
		stationDropDown.addActionListener(new ActionListener() {

            @SuppressWarnings("unchecked")
            @Override
            public void actionPerformed(ActionEvent e) {
                stationDropDown = (JComboBox<String>) e.getSource();
                selectedStation = (String) stationDropDown.getSelectedItem();
            }
        });
		
		
		
		
		JButton calc = new JButton("Calculate HD");
		
		JLabel empty = new JLabel("");
		
		JLabel dist0 = new JLabel("Distance 0");
		JLabel distance0 = new JLabel("-");
		
		JLabel dist1 = new JLabel("Distance 1");
		JLabel distance1 = new JLabel("-");
		
		JLabel dist2 = new JLabel("Distance 2");
		JLabel distance2 = new JLabel("-");
		
		JLabel dist3 = new JLabel("Distance 3");
		JLabel distance3 = new JLabel("-");
		
		JLabel dist4 = new JLabel("Distance 4");
		JLabel distance4 = new JLabel("-");
		
		JButton add = new JButton("Add Station");
		
		JTextField addStation = new JTextField();
		
		add.addActionListener((e) -> {
			
			String stationName = addStation.getText();
			stationDropDown.addItem(stationName);
			
		});
		
		showStation.addActionListener((e) -> {
			
			if (selectedDistance == 1)
			{
				try {
					allCities = null;
					stationList.setText(null);
					allCities = compareCities(1);
					for (int i = 0; i < allCities.size(); ++i)
					{
						stationList.append(allCities.get(i) + "\n");
					}
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}
			if (selectedDistance == 2)
			{
				try {
					allCities = null;
					stationList.setText(null);
					allCities = compareCities(2);
					for (int i = 0; i < allCities.size(); ++i)
					{
						stationList.append(allCities.get(i) + "\n");
					}
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}
			if (selectedDistance == 3)
			{
				try {
					allCities = null;
					stationList.setText(null);
					allCities = compareCities(3);
					for (int i = 0; i < allCities.size(); ++i)
					{
						stationList.append(allCities.get(i) + "\n");
					}
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}
			if (selectedDistance == 4)
			{
				try {
					allCities = null;
					stationList.setText(null);
					allCities = compareCities(4);
					for (int i = 0; i < allCities.size(); ++i)
					{
						stationList.append(allCities.get(i) + "\n");
					}
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}
			
		});
		
		calc.addActionListener((e) -> {
			
			ArrayList<Integer> nodes = new ArrayList<Integer>();
			try {
				nodes = compareAll();
				distance0.setText(nodes.get(0).toString());
				distance1.setText(nodes.get(1).toString());
				distance2.setText(nodes.get(2).toString());
				distance3.setText(nodes.get(3).toString());
				distance4.setText(nodes.get(4).toString());
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		});
		
		/**
		 * Add Components to their Panels
		 */
		enter.add(enterHammDist);
		slider.add(hammDistSlider);
		enter.add(hammDistBox);
		showButton.add(showStation);
		
		compare.add(compareWith);
		compare.add(stationDropDown);
		
		distanceRects.add(calc);
		distanceRects.add(empty);
		distanceRects.add(dist0);
		distanceRects.add(distance0);
		distanceRects.add(dist1);
		distanceRects.add(distance1);
		distanceRects.add(dist2);
		distanceRects.add(distance2);
		distanceRects.add(dist3);
		distanceRects.add(distance3);
		distanceRects.add(dist4);
		distanceRects.add(distance4);
		
		botLeft.add(add);
		botLeft.add(addStation);

		/**
		 * Add Panels to the Left Side
		 */
		leftSide.add(enter);
		leftSide.add(slider);
		leftSide.add(showButton);
		leftSide.add(stationsField);
		leftSide.add(compare);
		leftSide.add(distanceRects);
		leftSide.add(botLeft);
		
		/**
		 * Free Zone (rightSide)
		 */
		rightSide.setBorder(new EmptyBorder(new Insets(10, 20, 10, 10)));
		rightSide.setLayout(new BoxLayout(rightSide, BoxLayout.Y_AXIS));
		
		
		JButton showList = new JButton("Show the Original List of Stations from Mesonet.txt");
		rightSide.add(showList);
		
		
		showList.addActionListener((e) -> {
			while(scnr.hasNext())
			{
				String stationIds = scnr.next();
				stationList.append(stationIds + "\n");	
			}
		});
		
		JScrollPane fullList = new JScrollPane(stationList);
		stationsField.setBounds(10,60,780,500);
		fullList.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		fullList.setBorder(new LineBorder(Color.BLACK));
		
		
		rightSide.add(fullList);
		
		
		
		hammingDistPanel.add(leftSide);
		hammingDistPanel.add(rightSide);

		window.add(hammingDistPanel);

		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setVisible(true);
		
	}
	
	public ArrayList<Integer> compareAll() throws IOException
	{
			
			FileInputStream theFile = new FileInputStream("Mesonet.txt");
			Scanner keyboard = new Scanner(theFile);
			ArrayList<Integer> nodes = new ArrayList<Integer>();
			
			int node0 = 0;
			int node1 = 0;
			int node2 = 0;
			int node3 = 0;
			int node4 = 0;
			
			while (keyboard.hasNextLine())
			{
				
				int distance = 0;
				String compareCity = keyboard.nextLine();
				
				if (!selectedStation.equals(compareCity))
				{
					for (int j = 0; j < 4; ++j)
					{
						if (selectedStation.charAt(j) != compareCity.charAt(j))
						{
							++distance;
						}
					}
				}
				if (distance == 0)
				{
					++node0;
				}
				if (distance == 1) 
				{
					++node1;
				}
				else if (distance == 2)
				{
					++node2;
				}
				else if (distance == 3)
				{
					++node3;
				}
				else if (distance == 4)
				{
					++node4;
				}
				
			}
			
			theFile.close();
			keyboard.close();
			
			nodes.add(0, node0);
			nodes.add(1, node1);
			nodes.add(2, node2);
			nodes.add(3, node3);
			nodes.add(4, node4);
			
			return nodes;
		}
		
		public ArrayList<String> compareCities(int distWanted) throws IOException
		{
			FileInputStream theFile = new FileInputStream("Mesonet.txt");
			
			Scanner keyboard = new Scanner(theFile);
			
			ArrayList<String> cities = new ArrayList<String>();
			
			while (keyboard.hasNextLine())
			{
				int distance = 0;
				String compareCity = keyboard.nextLine();
				
				if (!selectedStation.equals(compareCity))
				{
					for (int j = 0; j < 4; ++j)
					{
						if (selectedStation.charAt(j) != compareCity.charAt(j))
						{
							++distance;
						}
					}
				}
				if (distance == distWanted)
				{
					cities.add(compareCity);
				}
				
			}
			theFile.close();
			keyboard.close();
			
			return cities;
		}
		
}
