import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionListener;
import java.util.Random;
import java.util.Scanner;
import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class Suduku extends JFrame implements ActionListener{

  private Font fontCell;
  private JPanel gridPanel;
  private int showValue;
  private Scanner input;
  private Random rand;
  private int randomNumber;
  private int height;
  private int width;
  private JTextField[][] cell;
  private String[][] valueSet = {{"0", "0", "0"},
                                 {"0", "0", "0"},
                                 {"0", "0", "0"}};
 private boolean[][] showStatus = {{false, false, false},
                                   {false, false, false},
                                   {false, false, false}};

  public Suduku(){
    super("Suduku Assignment");
    startup();
    settingNumber();
    settingShowNumber();
    setupGUIValue();
    createMainGUI();
  }


  private void setupGUIValue(){
    this.fontCell = new Font("Comic Sans MS", Font.BOLD, 20);
  }

  private void startup(){
    showValue = -1;
    while(true){
      System.out.print("Insert many digit which need to occur(3 - 6): ");
      this.input = new Scanner(System.in);
      this.showValue = input.nextInt();
      if(this.showValue < 3 || this.showValue > 6){
        System.out.println("You must insert digit between 3 - 6");
      }
      else{
        break;
      }
    }
    this.height = 3;
    this.width = 3;
  }

  private void settingShowNumber(){

    int xPosition = 0, yPosition = 0;
    rand = new Random();

    while(this.showValue > 0){

       xPosition = rand.nextInt(3);
       yPosition = rand.nextInt(3);

       if(!(showStatus[yPosition][xPosition])){

         showStatus[yPosition][xPosition] = true;

         this.showValue--;
       }
    }
  }

  private void settingNumber(){
    int xAxis = 0, yAxis = 0;
    this.rand = new Random();

    System.out.println(this.height);
    while(yAxis < this.height){

      while(xAxis < this.width){

        this.randomNumber = rand.nextInt(9) + 1;

        if(IsIterateInHeight() || IsIterateInWidth() || IsIterateInColumn()){
          this.randomNumber = rand.nextInt(9) + 1;
        }
        else{
          valueSet[yAxis][xAxis] = Integer.toString(this.randomNumber);
          this.randomNumber = rand.nextInt(9) + 1;
          xAxis++;
        }
      }

      yAxis++;
      xAxis = 0;
    }
  }

  private boolean IsIterateInColumn(){

    for(int yAxis = 0; yAxis < this.height; yAxis++){
      for(int xAxis = 0; xAxis < this.width; xAxis++){
        if(this.randomNumber == Integer.parseInt(valueSet[yAxis][xAxis]))
          return true;
      }
    }

    return false;
  }

  private boolean IsIterateInHeight(){

    for(int yAxis = 0; yAxis < this.height; yAxis++){
      if(this.randomNumber == Integer.parseInt(valueSet[yAxis][this.width - 1])){
        return true;
      }
    }

    return false;
  }

  private boolean IsIterateInWidth(){

    for(int xAxis = 0; xAxis < this.width; xAxis++){
      if(this.randomNumber == Integer.parseInt(valueSet[this.height - 1][xAxis])){
        return true;
      }
    }

    return false;
  }

  private void checkIsvalid(String input, int[] position){

    if(input == valueSet[position[1]][position[0]]){
      this.cell[position[1]][position[0]].setBackground(Color.GREEN);
    }
    else{
      this.cell[position[1]][position[0]].setBackground(Color.RED);
    }
  }

  private void createMainGUI(){
    Container controler = getContentPane();
    controler.setLayout(new GridLayout(3, 3));
    cell = new JTextField[3][3];

    for (int yPosition = 0; yPosition < this.height; yPosition++) {

      for(int xPosition = 0; xPosition < this.width; xPosition++){

        if(showStatus[yPosition][xPosition]){
          this.cell[yPosition][xPosition] =
          new JTextField(valueSet[yPosition][xPosition]);
          this.cell[yPosition][xPosition].setEditable(false);
        }
        else{
          this.cell[yPosition][xPosition] = new JTextField("");
          this.cell[yPosition][xPosition].setEditable(true);
        }

        this.cell[yPosition][xPosition].setBackground(Color.WHITE);
        this.cell[yPosition][xPosition].setFont(this.fontCell);
        this.cell[yPosition][xPosition].setHorizontalAlignment(JTextField.CENTER);
        this.cell[yPosition][xPosition].addActionListener(this);
        controler.add(this.cell[yPosition][xPosition]);
      }
    }

    controler.setPreferredSize(new Dimension(180, 180)); //60px per cell.
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    pack();
    setVisible(true);
    setLocationRelativeTo(null);
  }

  public void actionPerformed(ActionEvent event){

    int selectXPosition = -1, selectYPosition = -1;
    JTextField input = (JTextField)event.getSource();
    boolean isfound = false;

    for(int yPosition = 0; yPosition < this.height && !(isfound); yPosition++){

      for(int xPosition = 0; xPosition < this.width && !(isfound); xPosition++){

        if(cell[yPosition][xPosition] == input){
          selectXPosition = xPosition;
          selectYPosition = yPosition;
          isfound = true;
        }
      }
    }

    if(Integer.parseInt(this.cell[selectYPosition][selectXPosition].getText()) == Integer.parseInt(valueSet[selectYPosition][selectXPosition])){

      this.cell[selectYPosition][selectXPosition].setBackground(Color.GREEN);
    }
    else{

      this.cell[selectYPosition][selectXPosition].setBackground(Color.RED);
    }
  }

  public static void main(String[] args) {
    new Suduku();
  }
}
