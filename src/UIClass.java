import Controller.OpenFileDialogHandler;
import Libraries.*;
import Libraries.FileHandler;

import java.io.File;
import java.util.logging.*;
import javax.swing.*;
import org.jaudiotagger.tag.Tag;

public class UIClass extends javax.swing.JFrame implements Runnable {

	public UIClass() {
		Logger.getLogger("org.jaudiotagger").setLevel(Level.OFF);
		initComponents();
	}

	private void initComponents() {
		MainPanel = new javax.swing.JPanel();
		jButton1 = new javax.swing.JButton();
		jButton2 = new javax.swing.JButton();
		statusLabel = new javax.swing.JLabel();

		statusLabel.setText("Ready");
		
		jButton1.setAlignmentX(CENTER_ALIGNMENT);
		jButton2.setAlignmentX(CENTER_ALIGNMENT);
		statusLabel.setAlignmentX(CENTER_ALIGNMENT);
		
		setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
		setTitle("Bollywood Metadata Cleaner");
			
		jButton1.setText("Select Files");
		jButton1.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				jButton1ActionPerformed(evt);
			}
		});

		jButton2.setText("Clean!");
		jButton2.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				jButton2ActionPerformed(evt);
			}
		});
		
		MainPanel.setLayout(new BoxLayout(MainPanel, BoxLayout.PAGE_AXIS));
		MainPanel.setAlignmentX(CENTER_ALIGNMENT);
		
		MainPanel.add(jButton1);
		MainPanel.add(jButton2);
		MainPanel.add(statusLabel);
		
		add(MainPanel);
		setSize(400, 200);
	}

	private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {
		try {
			startMainThread();
		} catch (Exception e) {
			JOptionPane.showMessageDialog(rootPane,
					"Error", "ERROR",
					JOptionPane.ERROR_MESSAGE, null);
		}
	}

	private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {
		try {
			startSecondThread();
		} catch (Exception e) {
			JOptionPane.showMessageDialog(rootPane,
					"Error", "ERROR",
					JOptionPane.ERROR_MESSAGE, null);
		}
	}

	public void startMainThread() throws InterruptedException {
		MainThread = new Thread(this, "MainThread");
		MainThread.start();
	}

	public void startSecondThread() throws InterruptedException {
		SecondThread = new Thread(this, "SecondThread");
		SecondThread.start();
	}

	public static void main(String[] args) {
		java.awt.EventQueue.invokeLater(new Runnable() {
			@Override
			public void run() {
				new UIClass().setVisible(true);
			}
		});
	}

	@Override
	public void run() {

		if (Thread.currentThread().equals(MainThread)) {
			try {
				statusLabel.setText("Opening file(s)");
				JFileChooser filedialog = new JFileChooser();
				filedialog.setFileFilter(new FileFilter());
				filedialog.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
				filedialog.setMultiSelectionEnabled(true);

				int condition = filedialog.showOpenDialog(MainPanel);

				if (condition == JFileChooser.APPROVE_OPTION) {
					files = filedialog.getSelectedFiles();
					files = OpenFileDialogHandler.getFiles(files);
				}
				
				statusLabel.setText(files.length + " mp3 files opened successfully");
			} catch (Exception e) {
				statusLabel.setText("Error");
				System.out.println("Error " + e);
			}
		}
		
		if (Thread.currentThread().equals(SecondThread)) {
			try{
                FileHandler fp = new FileHandler();
                
                for(int row = 0;row<files.length;row++){
                    
//                    tags[row].setField(FieldKey.TITLE,MainTable.getModel().getValueAt(row,1).toString());
                    
                    fp.saveTag(files[row]);
                    
                    int perc = (int) ((double)(row+1)/(double)files.length*100);
                    
                    statusLabel.setText("Saving file "+(row+1)+" of "+files.length+" ("+perc+"% Done)");
                }
                statusLabel.setText("Ready!");
                
			}catch(Exception e){
				statusLabel.setText("Error");
                System.out.println("Error " + e);
            }
		}
	}

	Thread MainThread;
	Thread SecondThread;
	public static File[] files;
	public static Tag[] tags;

	private javax.swing.JPanel MainPanel;
	private javax.swing.JLabel statusLabel;
	private javax.swing.JButton jButton1;
	private javax.swing.JButton jButton2;

}
