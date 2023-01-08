package pkt;

import java.awt.EventQueue;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.JTable;
import javax.swing.JScrollPane;
import javax.swing.JFormattedTextField;
import javax.swing.JTextArea;
import javax.swing.JButton;
import java.awt.Color;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.FlowLayout;
import javax.swing.SpringLayout;
import javax.swing.JSplitPane;
import javax.swing.JTextField;
import java.awt.Font;
import javax.swing.UIManager;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.SystemColor;
import javax.swing.JLabel;
import javax.swing.ImageIcon;
import java.awt.Toolkit;
import java.awt.Window.Type;
import javax.swing.ScrollPaneConstants;

public class UserInterface extends JFrame {
	String query;
	
	DefaultTableModel model=new DefaultTableModel();
	Object[] columns= {"Category","Product Name","About Product","Price","Poduct URL"};
	Object[] rows= new Object[5];
	/**
	 * @wbp.nonvisual location=-50,34
	 */
	private final JSplitPane splitPane = new JSplitPane();
	private JTextField textField;
	private JTable table_1;
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					UserInterface frame = new UserInterface();
					frame.setVisible(true);
					frame.setBounds(200, 200, 1350, 600);
					frame.setResizable(false);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public UserInterface() {
		
		splitPane.setResizeWeight(1.0);
		splitPane.setOneTouchExpandable(true);
		splitPane.setBackground(SystemColor.text);
		splitPane.setContinuousLayout(true);
		setTitle("Amazon Products");
		setAlwaysOnTop(true);
		setIconImage(Toolkit.getDefaultToolkit().getImage("C:\\Users\\ADMIN\\DesignProject\\DesignProject\\icon\\amazon-store-icon.png"));
		getContentPane().setBackground(new Color(112, 128, 144));
		setBackground(new Color(255, 255, 255));
		getContentPane().setLayout(null);
		
		JTextArea textArea = new JTextArea();
		textArea.setFont(new Font("Tahoma", Font.PLAIN, 15));
		textArea.setBackground(Color.WHITE);
		textArea.setBounds(20, 94, 1147, 21);
		getContentPane().add(textArea);
		
		JButton btnNewButton = new JButton("Search");
		btnNewButton.setBackground(SystemColor.window);
		btnNewButton.setFont(new Font("Tahoma", Font.BOLD, 12));
		btnNewButton.setBounds(1177, 95, 96, 22);
		getContentPane().add(btnNewButton);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		scrollPane.setBounds(20, 150, 1253, 349);
		getContentPane().add(scrollPane);
		
		table_1 = new JTable();
		table_1.setFont(new Font("Tahoma", Font.PLAIN, 15));
		table_1.setFillsViewportHeight(true);
		table_1.setCellSelectionEnabled(true);
		table_1.setColumnSelectionAllowed(true);
		scrollPane.setViewportView(table_1);
		
		JLabel lblNewLabel_1 = new JLabel("Amazon Products");
		lblNewLabel_1.setForeground(SystemColor.window);
		lblNewLabel_1.setFont(new Font("Tahoma", Font.BOLD, 22));
		lblNewLabel_1.setIcon(new ImageIcon("C:\\Users\\ADMIN\\DesignProject\\DesignProject\\images\\amazon-store-icon.png"));
		lblNewLabel_1.setBounds(10, 0, 286, 84);
		getContentPane().add(lblNewLabel_1);
		
		JLabel lblNewLabel = new JLabel("");
		lblNewLabel.setForeground(Color.BLACK);
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 15));
		lblNewLabel.setBounds(20, 125, 869, 21);
		
		PostgreSqlConnection.connect();
		
		btnNewButton.addMouseListener(new MouseAdapter() {
			@Override
			
			public void mouseClicked(MouseEvent e) {
				lblNewLabel.setText("");
				String wanted=textArea.getText();
				query="SELECT category, product_name,about_product, price,url FROM searching WHERE searching_item @@ plainto_tsquery( '"+wanted+"')"
						+ "order by ts_rank( searching_item, plainto_tsquery( '"+wanted+"')) desc;";
								
				ResultSet rs=PostgreSqlConnection.listQuery(query);
				 
				try {
					model.setColumnCount(0);
					model.setRowCount(0);
					model.setColumnIdentifiers(columns);
					if(rs.next()==false) {lblNewLabel.setText("Could not found any results for "+wanted+".");getContentPane().add(lblNewLabel);}
					else {
						do{
							rows[0]=rs.getString("category");
							rows[1]=rs.getString("product_name");
							rows[2]=rs.getString("about_product");
							rows[3]=rs.getString("price");
							rows[4]=rs.getString("url");
							model.addRow(rows);								
						}while(rs.next()==true);
						table_1.setModel(model);
					}
				}catch (Exception e2) {
					// TODO: handle exception
				}
			}
		});
	}
}
						
			
		
		
		
	
		
		

