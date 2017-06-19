import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.border.Border;
import javax.swing.border.TitledBorder;
import javax.swing.plaf.ColorUIResource;

public class SudukuGUI extends JFrame implements ActionListener, MouseListener{
	private Container container;
	private Dimension screenSize;
	private JPanel panel;
	private GroupLayout layoutFrame;
	private JTextField[][] reciveGrids;
	private JLabel grid;
	private JLabel[] biggerGrids;
	private int[] position;
	private JLabel timingArea;
	private JLabel scoresArea;
	private JButton startButton;
	private JButton okButton;
	private JButton cancelButton;
	private JLabel levelSelectArea;
	private JLabel digitsButtonArea;
	private String timeString;
	private TitledBorder titleLine;
	private Border blackline;
	private String scoresString;
	private JRadioButton[] radioButton;
	private ButtonGroup  radioButtonGroup;
	private JButton[] digitbutton;
	private int[][] numberGrid;
	private boolean[][] showGrid;
	private int show;
	private int[] focusGrid;
	private int points;
	private long startTime;
	private long endTime;
	private int hourTime;
	private int minuteTime;
	private int secoundTime;
	
	public SudukuGUI(){
		this.container = getContentPane();
		this.screenSize =  Toolkit.getDefaultToolkit().getScreenSize();
		this.reciveGrids = new JTextField[9][9];
		this.biggerGrids = new JLabel[9];
		this.position = new int[4];
		this.radioButton = new JRadioButton[7];
		this.digitbutton = new JButton[9];
		this.numberGrid = new int[9][9];
		this.showGrid = new boolean[9][9];
		this.focusGrid = new int[2];
		this.focusGrid[0] = -1;
		this.focusGrid[1] = -1;
		this.points = 0;
		this.timeString = "";
		this.scoresString = "";
		this.hourTime = 0;
		this.minuteTime = 0;
		this.secoundTime = 0;
	}
	
	protected void start(){
		try{
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		}
		catch(Exception error){
			error.printStackTrace();
		}
		this.panel = new JPanel();
		this.layoutFrame = new GroupLayout(this.panel);
		this.panel.setPreferredSize(new Dimension((int)(this.screenSize.getWidth() * 0.34), 
				(int)(this.screenSize.getHeight() * 0.805)));
		this.panel.setLayout(this.layoutFrame);
		this.layoutFrame.setAutoCreateGaps(true);
		this.layoutFrame.setAutoCreateContainerGaps(true);
		makegrid9x9();
		makeTimeUsed();
		makeScores();
		makeStartButton();
		makeOKButton();
		makeCancleButton();
		makeLevelSelectArea();
		makeDigitArea();
		this.layoutFrame.setHorizontalGroup(this.layoutFrame
				.createParallelGroup(GroupLayout.Alignment.LEADING)
				.addComponent(this.grid, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
				.addGroup(this.layoutFrame
						.createSequentialGroup()
						.addComponent(this.timingArea, 0, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
						.addComponent(this.scoresArea, 0, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
				.addGroup(this.layoutFrame
						.createSequentialGroup()
						.addGroup(this.layoutFrame
								.createParallelGroup(GroupLayout.Alignment.LEADING)
								.addComponent(this.startButton, GroupLayout.PREFERRED_SIZE ,GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
								.addComponent(this.okButton, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
								.addComponent(this.cancelButton, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
						.addComponent(this.levelSelectArea, 0, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
						.addComponent(this.digitsButtonArea, 0, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)));
		this.layoutFrame.setVerticalGroup(this.layoutFrame
				.createSequentialGroup()
				.addComponent(this.grid, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
				.addGroup(this.layoutFrame
						.createParallelGroup(GroupLayout.Alignment.BASELINE)
						.addComponent(this.timingArea, 0, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
						.addComponent(this.scoresArea, 0, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
				.addGroup(this.layoutFrame
						.createParallelGroup(GroupLayout.Alignment.LEADING)
						.addGroup(this.layoutFrame
								.createSequentialGroup()
								.addComponent(this.startButton, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
								.addComponent(this.okButton, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
								.addComponent(this.cancelButton, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
						.addComponent(this.levelSelectArea, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(this.digitsButtonArea, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)));
		this.container.add(this.panel);
		pack();
		setTitle("Suduku");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
		setResizable(false);
		setVisible(true);
	}
	
	private void makeTimeUsed(){
		this.titleLine = BorderFactory.createTitledBorder(this.blackline, "Time Used");
		this.titleLine.setTitleJustification(TitledBorder.LEFT);
		this.timingArea = new JLabel(this.timeString);
		this.timingArea.setBorder(this.titleLine);
	}
	
	private void makeScores(){
		this.titleLine = BorderFactory.createTitledBorder(this.blackline, "Score(s)");
		this.titleLine.setTitleJustification(TitledBorder.LEFT);
		this.scoresArea = new JLabel(this.scoresString);
		this.scoresArea.setBorder(this.titleLine);
	}
	
	private void makeDigitGroup(){
		for(int i = 0; i < 9; i++){
			this.digitbutton[i] = new JButton(Integer.toString(i + 1));
			this.digitbutton[i].addActionListener(this);
			this.digitsButtonArea.add(this.digitbutton[i]);
			this.digitbutton[i].setEnabled(false);
		}
	}
	
	private void makeLavelGroup(){
		this.radioButton[0] = new JRadioButton("Most Easy");
		this.radioButton[1] = new JRadioButton("Very Easy");
		this.radioButton[2] = new JRadioButton("Easy");
		this.radioButton[3] = new JRadioButton("Normal");
		this.radioButton[4] = new JRadioButton("Hard");
		this.radioButton[5] = new JRadioButton("Very Hard");
		this.radioButton[6] = new JRadioButton("Most Hard");
		this.radioButtonGroup = new ButtonGroup();
		for(int i = 0; i < 7; i++){
			this.radioButton[i].setEnabled(false);
			this.radioButton[i].addActionListener(this);
			this.radioButtonGroup.add(this.radioButton[i]);
			this.levelSelectArea.add(this.radioButton[i]);
		}
	}
	
	private void makeStartButton(){
		this.startButton = new JButton("Start");
		this.startButton.setPreferredSize(new Dimension((int)(this.screenSize.getWidth() * 0.06), 
				(int)(this.screenSize.getHeight() * 0.04)));
		this.startButton.addActionListener(this);
		this.startButton.setFocusPainted(false);
		this.startButton.setFocusable(false);
	}
	
	public void actionPerformed(ActionEvent event){
		this.startButton.setFocusPainted(false);
		this.okButton.setFocusPainted(false);
		this.cancelButton.setFocusPainted(false);
		for(int i = 0; i < 7; i++){
			this.radioButton[i].setFocusPainted(false);
		}
		if("Start".equals(event.getActionCommand()) || "Renew".equals(event.getActionCommand())){
			if(this.focusGrid[0] != -1){
				this.reciveGrids[this.focusGrid[1]][this.focusGrid[0]].setBorder(BorderFactory.createLineBorder(Color.BLACK));
			}
			this.startButton.setEnabled(false);
			this.cancelButton.setEnabled(true);
			if("Renew".equals(event.getActionCommand())){
				this.okButton.setEnabled(true);
			}
			for(int i = 0; i < 7; i++){
				this.radioButton[i].setEnabled(true);
			}
			for(int j = 0; j < 9; j++){
				this.digitbutton[j].setEnabled(false);
			}
			for(int k = 0; k < 9; k++){
				for(int l = 0; l < 9; l++){
					this.reciveGrids[k][l].setEnabled(false);
					this.reciveGrids[k][l].setBackground(UIManager.getColor ("this.panel.background"));
					this.reciveGrids[k][l].setText("");
					this.reciveGrids[k][l].removeMouseListener(this);
				}
			}
			this.focusGrid[0] = -1;
			this.focusGrid[1] = -1;
			this.timeString = "N/A";
			this.scoresString = "N/A";
			this.timingArea.setText("");
			this.scoresArea.setText("");
		}
		else if("OK".equals(event.getActionCommand())){
			this.okButton.setEnabled(false);
			this.cancelButton.setEnabled(false);
			this.startButton.setText("Renew");
			this.startButton.setEnabled(true);
			SudukuGen numbergen = new SudukuGen();
			this.numberGrid = numbergen.startGen();
			SudukuRandomDisplay showgen = new SudukuRandomDisplay(this.show);
			this.showGrid = showgen.startRandom();
			for(int i = 0; i < 7; i++){
				this.radioButton[i].setEnabled(false);
			}
			for(int k = 0; k < 9; k++){
				for(int l = 0; l < 9; l++){
					if(this.showGrid[k][l]){
						this.reciveGrids[k][l].setText(Integer.toString(this.numberGrid[k][l]));
						this.reciveGrids[k][l].setName("(-1,-1)");
					}
					else{
						this.reciveGrids[k][l].setName("(" + l + "," + k + ")" );
					}
					this.reciveGrids[k][l].addMouseListener(this);
					this.reciveGrids[k][l].setEnabled(true);
					this.reciveGrids[k][l].setBackground(Color.WHITE);
				}
			}
			this.timeString = "N/A";
			this.scoresString = "N/A";
			this.timingArea.setText(this.timeString);
			this.scoresArea.setText(this.scoresString);
			this.points = 0;
			this.startTime = System.currentTimeMillis();
		}
		else if("Cancel".equals(event.getActionCommand())){
			this.okButton.setEnabled(false);
			this.cancelButton.setEnabled(false);
			this.startButton.setEnabled(true);
			for(int i = 0; i < 7; i++){
				this.radioButton[i].setEnabled(false);
			}
			this.radioButtonGroup.clearSelection();
			this.show = 0;
			this.startButton.setText("Start");
		}
		else if("Most Easy".equals(event.getActionCommand())){
			this.okButton.setEnabled(true);
			this.show = 72;
		}
		else if("Very Easy".equals(event.getActionCommand())){
			this.okButton.setEnabled(true);
			this.show = 63;
		}
		else if("Easy".equals(event.getActionCommand())){
			this.okButton.setEnabled(true);
			this.show = 54;
		}
		else if("Normal".equals(event.getActionCommand())){
			this.okButton.setEnabled(true);
			this.show = 45;
		}
		else if("Hard".equals(event.getActionCommand())){
			this.okButton.setEnabled(true);
			this.show = 36;
		}
		else if("Very Hard".equals(event.getActionCommand())){
			this.okButton.setEnabled(true);
			this.show = 27;
		}
		else if("Most Hard".equals(event.getActionCommand())){
			this.okButton.setEnabled(true);
			this.show = 18;
		}
		else{
			int reciveNumber, currectNumber;
			this.reciveGrids[this.focusGrid[1]][this.focusGrid[0]].setText(event.getActionCommand());
			this.reciveGrids[this.focusGrid[1]][this.focusGrid[0]].setBorder(BorderFactory.createLineBorder(Color.BLACK));
			this.reciveGrids[this.focusGrid[1]][this.focusGrid[0]].removeMouseListener(this);
			for(int i = 0; i < 9; i++){
				this.digitbutton[i].setEnabled(false);
			}
			reciveNumber = Integer.parseInt(this.reciveGrids[this.focusGrid[1]][this.focusGrid[0]].getText());
			currectNumber = this.numberGrid[this.focusGrid[1]][this.focusGrid[0]];
			if(reciveNumber == currectNumber){
				this.reciveGrids[this.focusGrid[1]][this.focusGrid[0]].setBackground(Color.GREEN);
				this.showGrid[this.focusGrid[1]][this.focusGrid[0]] = true;
				addPoint();
				if(checkHasFinnish()){
					this.endTime = System.currentTimeMillis();
					calculusTimeUsed();
					calculusScores();
					if(this.points <= 0){
						this.scoresString = "0";
					}
					else{
						this.scoresString = Integer.toString(this.points);
					}
					this.timingArea.setText(this.timeString);
					this.scoresArea.setText(this.scoresString);
				}
			}
			else{
				this.reciveGrids[this.focusGrid[1]][this.focusGrid[0]].setBackground(Color.RED);
				for(int j = 0; j < 9; j++){
					for(int k = 0; k < 9; k++){
						this.reciveGrids[j][k].removeMouseListener(this);
					}
				}
				this.scoresString = "0";
				this.timeString = "You was not finnished this pizzle.";
				this.timingArea.setText(this.timeString);
				this.scoresArea.setText(this.scoresString);
			}
		}
	}
	
	private void calculusScores(){
		this.points = (100 * this.points) / (81 - this.show);
	}
	
	private void calculusTimeUsed(){
		this.secoundTime = (int)((this.endTime - this.startTime) / 1000);
		this.minuteTime = this.secoundTime / 60;
		this.secoundTime = this.secoundTime % 60;
		this.hourTime = this.minuteTime / 60;
		this.points -= this.minuteTime;
		this.minuteTime = this.minuteTime % 60;
		this.timeString = "";
		if(this.hourTime > 0){
			this.timeString += Integer.toString(this.hourTime) + " hour(s) ";
		}
		if(this.minuteTime > 0){
			this.timeString += Integer.toString(this.minuteTime) + " minute(s) ";
		}
		if(this.secoundTime > 0){
			this.timeString += Integer.toString(this.secoundTime) + " secound(s)";
		}
	}
	
	private boolean checkHasFinnish(){
		for(int i = 0; i < 9; i++){
			for(int j = 0; j < 9; j++){
				if(!this.showGrid[i][j]){
					return false;
				}
			}
		}
		
		return true;
	}
	
	private void addPoint(){
		if(this.show == 72){
			this.points += 1;
		}
		else if(this.show == 63){
			this.points += 2;
		}
		else if(this.show == 54){
			this.points += 3;
		}
		else if(this.show == 45){
			this.points += 4;
		}
		else if(this.show == 36){
			this.points += 5;
		}
		else if(this.show == 27){
			this.points += 6;
		}
		else if(this.show == 18){
			this.points += 7;
		}
	}
	
	public void mouseClicked(MouseEvent event){
		
	}
	
	public void mousePressed(MouseEvent event){
		if(this.focusGrid[0] != -1){
			this.reciveGrids[this.focusGrid[1]][this.focusGrid[0]].setBorder(BorderFactory.createLineBorder(Color.BLACK));
			for(int i = 0; i < 9; i++){
				this.digitbutton[i].setEnabled(false);
			}
		}
		String where = event.getSource().toString();
		where = where.substring(where.indexOf("(") + 1, where.indexOf(")"));
		String[] positon = where.split(",");
		this.focusGrid[0] = Integer.parseInt(positon[0]);
		this.focusGrid[1] = Integer.parseInt(positon[1]);
		if(this.focusGrid[0] != -1){
			this.reciveGrids[this.focusGrid[1]][this.focusGrid[0]].setBorder(BorderFactory.createLineBorder(Color.ORANGE, 5));
			for(int i = 0; i < 9; i++){
				this.digitbutton[i].setEnabled(true);
				this.digitbutton[i].setFocusable(false);
				this.digitbutton[i].setFocusPainted(false);
			}
		}
	}
	
	public void mouseReleased(MouseEvent event){
		
	}
	
	public void mouseEntered(MouseEvent event){
		
	}
	
	public void mouseExited(MouseEvent event){
		
	}
	
	private void makeOKButton(){
		this.okButton = new JButton("OK");
		this.okButton.setPreferredSize(new Dimension((int)(this.screenSize.getWidth() * 0.06), 
				(int)(this.screenSize.getHeight() * 0.04)));
		this.okButton.setEnabled(false);
		this.okButton.addActionListener(this);
	}
	
	private void makeCancleButton(){
		this.cancelButton = new JButton("Cancel");
		this.cancelButton.setPreferredSize(new Dimension((int)(this.screenSize.getWidth() * 0.06), 
				(int)(this.screenSize.getHeight() * 0.04)));
		this.cancelButton.setEnabled(false);
		this.cancelButton.addActionListener(this);
	}
	private void makeLevelSelectArea(){
		this.titleLine = BorderFactory.createTitledBorder(this.blackline, "Levels");
		this.titleLine.setTitleJustification(TitledBorder.LEFT);
		this.levelSelectArea = new JLabel();
		this.levelSelectArea.setLayout(new GridLayout(4, 2));
		makeLavelGroup();
		this.levelSelectArea.setBorder(this.titleLine);
		this.levelSelectArea.setPreferredSize(new Dimension((int)(this.screenSize.getWidth() * 0.014), 
				(int)(this.screenSize.getHeight() * 0.138)));
	}
	
	private void makeDigitArea(){
		this.titleLine = BorderFactory.createTitledBorder(this.blackline, "Digits");
		this.titleLine.setTitleJustification(TitledBorder.LEFT);
		this.digitsButtonArea = new JLabel();
		this.digitsButtonArea.setLayout(new GridLayout(3, 3, (int)(this.screenSize.getWidth() * 0.002), 
				(int)(this.screenSize.getHeight() * 0.004)));
		makeDigitGroup();
		this.digitsButtonArea.setBorder(this.titleLine);
		this.digitsButtonArea.setPreferredSize(new Dimension(0, 
				(int)(this.screenSize.getHeight() * 0.14)));
	}
	
	private void makegrid9x9(){
		this.grid = new JLabel();
		this.grid.setPreferredSize(new Dimension((int)(this.screenSize.getWidth() * 0.3294289897511), 
				(int)(this.screenSize.getHeight() * 0.5859375)));
		this.grid.setBorder(BorderFactory.createLineBorder(Color.BLACK, 3));
		this.grid.setLayout(new GridLayout(3, 3));
		for(int i = 0; i < 9; i++){
			this.biggerGrids[i]= new JLabel();
			this.biggerGrids[i].setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
			this.biggerGrids[i].setLayout(new GridLayout(3, 3));
			this.grid.add(this.biggerGrids[i]);
			position(i);
			for(int j = this.position[1]; j < this.position[3]; j++){
				for(int k = this.position[0]; k < this.position[2]; k++){
					this.reciveGrids[j][k] = new JTextField("");
					this.reciveGrids[j][k].setHorizontalAlignment(SwingConstants.CENTER);
					this.reciveGrids[j][k].setBorder(BorderFactory.createLineBorder(Color.BLACK));
					this.reciveGrids[j][k].setFont(new Font("Arial", Font.BOLD, 25));
					this.reciveGrids[j][k].setEditable(false);
					this.reciveGrids[j][k].setEnabled(false);
					this.biggerGrids[i].add(this.reciveGrids[j][k]);
				}
			}
		}
	}
	
	private void position(int eachgrid){
		if(eachgrid == 0){
			this.position[0] = 0;
			this.position[1] = 0;
			this.position[2] = 3;
			this.position[3] = 3;
		}
		else if(eachgrid == 1){
			this.position[0] = 3;
			this.position[1] = 0;
			this.position[2] = 6;
			this.position[3] = 3;
		}
		else if(eachgrid == 2){
			this.position[0] = 6;
			this.position[1] = 0;
			this.position[2] = 9;
			this.position[3] = 3;
		}
		else if(eachgrid == 3){
			this.position[0] = 0;
			this.position[1] = 3;
			this.position[2] = 3;
			this.position[3] = 6;
		}
		else if(eachgrid == 4){
			this.position[0] = 3;
			this.position[1] = 3;
			this.position[2] = 6;
			this.position[3] = 6;
		}
		else if(eachgrid == 5){
			this.position[0] = 6;
			this.position[1] = 3;
			this.position[2] = 9;
			this.position[3] = 6;
		}
		else if(eachgrid == 6){
			this.position[0] = 0;
			this.position[1] = 6;
			this.position[2] = 3;
			this.position[3] = 9;
		}
		else if(eachgrid == 7){
			this.position[0] = 3;
			this.position[1] = 6;
			this.position[2] = 6;
			this.position[3] = 9;
		}
		else if(eachgrid == 8){
			this.position[0] = 6;
			this.position[1] = 6;
			this.position[2] = 9;
			this.position[3] = 9;
		}
		
	}
	
	public static void main(String[] args){
		SudukuGUI a = new SudukuGUI();
		a.start();
	}
}
