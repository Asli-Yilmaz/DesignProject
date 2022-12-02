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

public class UserInterface extends JFrame {
	String sorgu;
	DefaultTableModel model=new DefaultTableModel();
	Object[] columns= {"ID","Title","Date","Content"};
	Object[] rows=new Object[4];
	private JTable table_2;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					UserInterface frame = new UserInterface();
					frame.setVisible(true);
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
		getContentPane().setLayout(null);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(70, 78, 729, 291);
		getContentPane().add(scrollPane);
		
		table_2 = new JTable();
		scrollPane.setViewportView(table_2);
		getContentPane().setLayout(null);
		
		JTextArea textArea_1 = new JTextArea();
		textArea_1.setBounds(67, 23, 627, 29);
		getContentPane().add(textArea_1);
		PostgreSqlConnection.connect();
		
		JButton btnNewButton = new JButton("Search");		
		btnNewButton.addMouseListener(new MouseAdapter() {
			@Override
			
			public void mouseClicked(MouseEvent e) {
				String wanted=textArea_1.getText();
				sorgu="SELECT author,dates,title,newscontent FROM haber where document_vectors @@ plainto_tsquery( '"+ wanted +"' ) Order by id";
				model.setColumnCount(0);
				model.setRowCount(0);
				model.setColumnIdentifiers(columns);
				
				ResultSet rs=PostgreSqlConnection.list(sorgu);
				try {
					while(rs.next()) {
						rows[0]=rs.getString("author");
						rows[1]=rs.getString("dates");
						rows[2]=rs.getString("title");
						rows[3]=rs.getString("newscontent");
						model.addRow(rows);
						
					}
					table_2.setModel(model);
					
				} catch (SQLException error) {
					// TODO Auto-generated catch block
					error.printStackTrace();
				}
			}
		});
		btnNewButton.setBounds(704, 23, 95, 28);
		getContentPane().add(btnNewButton);
		
		
		
	}
}