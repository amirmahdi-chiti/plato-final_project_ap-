
package Tic_Tac_Toe;

// Java program to find the 
// next optimal move for a player 
public class Computer 
{ 
static class Move 
{ 
	int row, col; 
}; 

static Nut player = Nut.X, opponent = Nut.O; 


static Boolean isMovesLeft(Cell board[][]) 
{ 
	for (int i = 0; i < 3; i++) 
		for (int j = 0; j < 3; j++) 
			if (board[i][j].nut==null) 
				return true; 
	return false; 
} 

// This is the evaluation function as discussed 
// in the previous article ( http://goo.gl/sJgv68 ) 
static int evaluate(Cell b[][]) 
{ 
	// Checking for Rows for X or O victory. 
	for (int row = 0; row < 3; row++) 
	{ 
		if (b[row][0].nut!=null&&b[row][0].nut == b[row][1].nut && 
			b[row][1].nut == b[row][2].nut) 
		{ 
			if (b[row][0].nut.equals(Nut.X)) 
				return +10; 
			else if (b[row][0].nut.equals(Nut.O)) 
				return -10; 
		} 
	} 

	// Checking for Columns for X or O victory. 
	for (int col = 0; col < 3; col++) 
	{ 
		if (b[0][col].nut!=null&&b[0][col].nut == b[1][col].nut && 
			b[1][col].nut == b[2][col].nut) 
		{ 
			if (b[0][col].nut == player) 
				return +10; 

			else if (b[0][col].nut == opponent) 
				return -10; 
		} 
	} 

	// Checking for Diagonals for X or O victory. 
	if (b[0][0].nut != null &&b[0][0].nut == b[1][1].nut && b[1][1].nut == b[2][2].nut) 
	{ 
		if (b[0][0].nut == player) 
			return +10; 
		else if (b[0][0].nut == opponent) 
			return -10; 
	} 

	if (b[1][1].nut != null &&b[0][2].nut == b[1][1].nut && b[1][1].nut == b[2][0].nut) 
	{ 
		if (b[0][2].nut == player) 
			return +10; 
		else if (b[0][2].nut == opponent) 
			return -10; 
	} 

	// Else if none of them have won then return 0 
	return 0; 
} 

// This is the minimax function. It considers all 
// the possible ways the game can go and returns 
// the value of the board 
static int minimax(Cell board[][], 
					int depth, Boolean isMax) 
{ 
	int score = evaluate(board); 

	// If Maximizer has won the game 
	// return his/her evaluated score 
	if (score == 10) 
		return score; 

	// If Minimizer has won the game 
	// return his/her evaluated score 
	if (score == -10) 
		return score; 

	// If there are no more moves and 
	// no winner then it is a tie 
	if (isMovesLeft(board) == false) 
		return 0; 

	// If this maximizer's move 
	if (isMax) 
	{ 
		int best = -1000; 

		// Traverse all cells 
		for (int i = 0; i < 3; i++) 
		{ 
			for (int j = 0; j < 3; j++) 
			{ 
				// Check if cell is empty 
				if (board[i][j].nut==null) 
				{ 
					// Make the move 
					board[i][j].nut = player; 

					// Call minimax recursively and choose 
					// the maximum value 
					best = Math.max(best, minimax(board, 
									depth + 1, !isMax)); 

					// Undo the move 
					board[i][j].nut = null; 
				} 
			} 
		} 
		return best; 
	} 

	// If this minimizer's move 
	else
	{ 
		int best = 1000; 

		// Traverse all cells 
		for (int i = 0; i < 3; i++) 
		{ 
			for (int j = 0; j < 3; j++) 
			{ 
				// Check if cell is empty 
				if (board[i][j].nut == null) 
				{ 
					// Make the move 
					board[i][j].nut = opponent; 

					// Call minimax recursively and choose 
					// the minimum value 
					best = Math.min(best, minimax(board, 
									depth + 1, !isMax)); 

					// Undo the move 
					board[i][j].nut = null; 
				} 
			} 
		} 
		return best; 
	} 
} 

// This will return the best possible 
// move for the player 
static Move findBestMove(Cell board[][]) 
{ 
	int bestVal = -1000; 
	Move bestMove = new Move(); 
	bestMove.row = -1; 
	bestMove.col = -1; 

	// Traverse all cells, evaluate minimax function 
	// for all empty cells. And return the cell 
	// with optimal value. 
	for (int i = 0; i < 3; i++) 
	{ 
		for (int j = 0; j < 3; j++) 
		{ 
			// Check if cell is empty 
			if (board[i][j].nut == null) 
			{ 
				// Make the move 
				board[i][j].nut = player; 

				// compute evaluation function for this 
				// move. 
				int moveVal = minimax(board, 0, false); 

				// Undo the move 
				board[i][j].nut = null; 

				// If the value of the current move is 
				// more than the best value, then update 
				// best/ 
				if (moveVal > bestVal) 
				{ 
					bestMove.row = i; 
					bestMove.col = j; 
					bestVal = moveVal; 
				} 
			} 
		} 
	} 

	System.out.printf("The value of the best Move " + 
							"is : %d\n\n", bestVal); 

	return bestMove; 
} 


    public void play(){
        Move bestMove = findBestMove(Main.Board);
        Logic.click(Main.Board[bestMove.row][bestMove.col]);
    }
} 

// This code is contributed by PrinciRaj1992 

