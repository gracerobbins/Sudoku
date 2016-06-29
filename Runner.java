
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
        for (int puzzleNum = 1; puzzleNum <= 9; puzzleNum++)
        {
            Board board = new Board("puzzles/" + puzzleNum + ".txt");
            System.out.println("Puzzle #" + puzzleNum + ": \n");
            System.out.println(board);
            for (int i = 0; i < 9; i++)
            {
                for (int j = 0; j < 9; j++)
                {
                    board.setPossibles(board.board[i][j]);
                }
            }
            while(!board.solved())
            {
                board.setAll();
            }
            System.out.println(board + "\n");
        }
    }
}
