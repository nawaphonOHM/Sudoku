//Nawaphon Isarathanachaikul 06/19/2017

import java.util.Random;

public class SudukuRandomDisplay {
	
	private boolean[][] displayStatus;
	private Random random;
	private int randomNumberX;
	private int randomNumberY;
	private int count;
	
	public SudukuRandomDisplay(int count){
		this.displayStatus = new boolean[9][9];
		for(int i = 0; i < 9; i++){
			for(int j = 0; j < 9; j++){
				this.displayStatus[i][j] = false;
			}
		}
		this.count = count;
	}
	
	protected boolean[][] startRandom(){
		random = new Random();
		while(count > 0){
			this.randomNumberX = random.nextInt(9);
			this.randomNumberY = random.nextInt(9);
			if(!this.displayStatus[this.randomNumberY][this.randomNumberX]){
				this.displayStatus[this.randomNumberY][this.randomNumberX] = true;
				count--;
			}
			
		}
		return this.displayStatus;
	}

}
