
/**
 * Write a description of class Runner2 here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
import java.io.FileNotFoundException;
public class Runner
{
    public static void main(String args[]) throws FileNotFoundException
    {
        for (int puzzleNum = 1; puzzleNum <= 9; puzzleNum++)//goes through each puzzle in the puzzles file
        {
            Board board = new Board("puzzles/" + puzzleNum + ".txt");
            System.out.println("Puzzle #" + puzzleNum + ": \n");
            System.out.println(board);//print the unsolved board
            for (int i = 0; i < 9; i++)//set the initial possibleNums for the board
            {
                for (int j = 0; j < 9; j++)
                {
                    board.setPossibles(board.board[i][j]);
                }
            }
            while(!board.solved())
            {
                board.setAll();//narrow down the possibleNums until the board is solved
            }
            System.out.println(board + "\n");//print the solved puzzle
        }
    }
}
