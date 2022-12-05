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
	String sorgu;
	String sorgu1;
	DefaultTableModel model=new DefaultTableModel();
	Object[] columns= {"Author","Publish Date","Title","Content"};
	Object[] rows= new Object[4];
	/**
	 * @wbp.nonvisual location=-50,34
	 */
	private final JSplitPane splitPane = new JSplitPane();
	private JTextField textField;
	private JTable table;
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
		setTitle("News Archive");
		setAlwaysOnTop(true);
		setIconImage(Toolkit.getDefaultToolkit().getImage("C:\\Users\\ADMIN\\DesignProject\\DesignProject\\icon\\News-icon.png"));
		getContentPane().setBackground(SystemColor.textInactiveText);
		setBackground(new Color(255, 255, 255));
		getContentPane().setLayout(null);
		
		JTextArea textArea = new JTextArea();
		textArea.setFont(new Font("Tahoma", Font.PLAIN, 15));
		textArea.setBackground(Color.WHITE);
		textArea.setBounds(20, 94, 1175, 21);
		getContentPane().add(textArea);
		
		JButton btnNewButton = new JButton("Search");
		btnNewButton.setBackground(SystemColor.window);
		btnNewButton.setFont(new Font("Tahoma", Font.BOLD, 12));
		btnNewButton.setBounds(1205, 93, 96, 22);
		getContentPane().add(btnNewButton);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		scrollPane.setBounds(20, 150, 1281, 349);
		getContentPane().add(scrollPane);
		
		table_1 = new JTable();
		table_1.setFillsViewportHeight(true);
		table_1.setCellSelectionEnabled(true);
		table_1.setColumnSelectionAllowed(true);
		scrollPane.setViewportView(table_1);
		
		JLabel lblNewLabel_1 = new JLabel("News Archive");
		lblNewLabel_1.setForeground(SystemColor.window);
		lblNewLabel_1.setFont(new Font("Tahoma", Font.BOLD, 20));
		lblNewLabel_1.setIcon(new ImageIcon("C:\\Users\\ADMIN\\DesignProject\\DesignProject\\icon\\News-icon(1).png"));
		lblNewLabel_1.setBounds(20, 10, 233, 64);
		getContentPane().add(lblNewLabel_1);
		
		 
		PostgreSqlConnection.connect();
		
		btnNewButton.addMouseListener(new MouseAdapter() {
			@Override
			
			public void mouseClicked(MouseEvent e) {

				String wanted=textArea.getText();
				sorgu="select author, dates, title, newscontent\r\n"
						+ "from haber where document_vectors @@ plainto_tsquery( '"+wanted+"')\r\n"
						+ "order by ts_rank( document_vectors, plainto_tsquery( '"+wanted+"')) desc;";
				model.setColumnCount(0);
				model.setRowCount(0);
				model.setColumnIdentifiers(columns);
				
				ResultSet rs=PostgreSqlConnection.listQuery(sorgu);
				 
				try {
					if(rs.next()==false){
						sorgu1= "select author, dates, title, newscontent\r\n"
								+ "from haber where document_vectors @@ to_tsquery( '"+wanted+":*')\r\n"
								+ "order by ts_rank( document_vectors, to_tsquery( '"+wanted+":*')) desc;";
						rs=PostgreSqlConnection.listQuery(sorgu1);
						
					}
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				try {
					while(rs.next()) {
						rows[0]=rs.getString("author");
						rows[1]=rs.getString("dates");
						rows[2]=rs.getString("title");
						rows[3]=rs.getString("newscontent");
						model.addRow(rows);
						
					}
					table_1.setModel(model);
					
					
				} catch (SQLException error) {
					// TODO Auto-generated catch block
					error.printStackTrace();
				}
			}
		});
		
		
	
		
		
	}
}