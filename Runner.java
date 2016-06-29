
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
        Board board2 = new Board("Hard3.txt");
        System.out.println(board2);
        for (int i = 0; i < 9; i++)
        {
            for (int j = 0; j < 9; j++)
            {
                board2.setPossibles(board2.board[i][j]);
            }
        }
        while(!board2.solved())
        {
            board2.setAll();
        }
        System.out.println(board2);
    }
}
