import java.util.Random;

public class SudukuGen {
	private int grid[][];
	private int randomNumber;
	private Random random;
	private int row;
	private int column;
	
	public SudukuGen(){
		this.grid = new int[9][9];
		for(int i = 0; i < this.grid.length; i++){
			for(int j = 0; j < this.grid[i].length; j++){
				this.grid[i][j] = 0;
			}
		}
		this.random = new Random();
		this.row = 0;
		this.column = 0;
	}
	
	protected void startGen(){
		while(this.row < 9){
			this.randomNumber = this.random.nextInt(9) + 1;
			if(this.column >= 9){
				print();
				this.column = 0;
				this.row++;
			}
			else if(!iteratedInColumn(this.row)){
				if(iteratedInRow(this.column) || 
						iteratedIn3x3(this.row, this.column)){
					clearRow(this.row);
					this.column = 0;
				}
				else{
					this.grid[this.row][this.column] = this.randomNumber;
					this.column++;
				}
			}
		}
	}
	
	private void print(){
		for(int column = 0; column < this.grid[this.row].length; column++){
			System.out.print(this.grid[this.row][column] + " ");
		}
		System.out.println();
	}
	
	private void clearRow(int row){
		for(int column = 0; column < this.grid[row].length; column++){
			this.grid[row][column] = 0;
		}
	}
	
	private boolean iteratedInColumn(int row){
		for(int i = 0; i < this.grid[row].length; i++){
			if(this.randomNumber == this.grid[row][i]){
				return true;
			}
		}
		return false;
	}
	
	private boolean iteratedInRow(int column){
		for(int i = 0; i < this.grid[column].length; i++){
			if(this.randomNumber == this.grid[i][column]){
				return true;
			}
		}
		return false;
	}
	
	private boolean iteratedIn3x3(int row, int column){
		int[] position = maxPosition(row, column);
		int maxPositonX = position[0], 
				maxPositionY = position[1], 
				positionX = maxPositonX - 2, 
				positionY = maxPositionY - 2;
		while(positionY <= maxPositionY){
			if(positionX > maxPositonX){
				positionY++;
				positionX = maxPositonX - 2;
			}
			else{
				if(this.randomNumber == this.grid[positionY][positionX]){
					return true;
				}
				else{
					positionX++;
				}
			}
		}
		return false;
	}
	
	private int[] maxPosition(int row, int column){
		int[] returnPosition = new int[2];
		if(column > 5){
			returnPosition[0] = 8;
		}
		else if(column > 2){
			returnPosition[0] = 5;
		}
		else{
			returnPosition[0] = 2;
		}
		if(row > 5){
			returnPosition[1] = 8;
		}
		else if(row > 2){
			returnPosition[1] = 5;
		}
		else{
			returnPosition[1] = 2;
		}
		return returnPosition;
	}
	
}
