
/**
 * Write a description of class Board2 here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;
public class Board
{
    public Square[][] board;
    public Board(String fileName) throws FileNotFoundException//creates a grid out of numbers from a file
    {
        Scanner reader = new Scanner(new File(fileName));
        board = new Square[9][9];
        for (int i = 0; i < 9; i++)
        {
            for (int j = 0; j < 9; j++)
            {
                int num = reader.nextInt();
                board[i][j] = new Square(i, j, num);
            }
        }
    }
    
    public void setPossibles(Square sq)//narrows down the possible values that a specific square might have
    {
        //removes potential values for one square based on what else is in that row and column
        int row = sq.getRow();
        int col = sq.getCol();
        for (int i = 0; i < 9; i++)
        {
            if (i != row)
            {
                sq.removePossNum(board[i][col].getFinalNum());
            }
            if (i != col)
            {
                sq.removePossNum(board[row][i].getFinalNum());
            }
        }
        //removes potential values for one square based on what else is in that nonet
        removeInNonet(sq);
    }
    private void removeInNonet(Square sq)//removes potential values for one square based on what else is in that nonet
    {
        int row = sq.getRow();
        int col = sq.getCol();
        int startRow = (int)((int)(row / 3) * 3);
        int startCol = (int)((int)(col / 3) * 3);
        for (int i = startRow; i < startRow + 3; i++)
        {
            for (int j = startCol; j < startCol + 3; j++)
            {
                if (!board[i][j].equals(sq))
                    sq.removePossNum(board[i][j].getFinalNum());
            }
        }
    }
    public void checkUniquesVertically(Square sq)//check to see if an individual square has a unique possible number
    {
        List<Integer> possUniques = new ArrayList<Integer>(sq.getPossiblesList());
        int squareRow = sq.getRow();
        int squareCol = sq.getCol();
        for (int i = 0; i < possUniques.size(); i++)
        {
            int num = possUniques.get(i);
            for (int row = 0; row < 9; row++)
            {
                if (row!= squareRow && board[row][squareCol].has(num) && possUniques.indexOf(num) != -1)
                {
                    possUniques.remove(possUniques.indexOf(num));
                    i--;
                }
            }
        }
        if (possUniques.size() == 1)
        {
            sq.setFinalNum(possUniques.get(0));
        }
    }
    public void checkUniquesHorizontally(Square sq)//check to see if an individual square has a unique possible number
    {
        List<Integer> possUniques = new ArrayList<Integer>(sq.getPossiblesList());
        int squareRow = sq.getRow();
        int squareCol = sq.getCol();
        for (int i = 0; i < possUniques.size(); i++)
        {
            int num = possUniques.get(i);
            for (int col = 0; col < 9; col++)
            {
                if (col!= squareCol && board[squareRow][col].has(num) && possUniques.indexOf(num) != -1)
                {
                    possUniques.remove(possUniques.indexOf(num));
                    i--;
                }
            }
        }
        if (possUniques.size() == 1)
        {
            sq.setFinalNum(possUniques.get(0));
        }
    }
    public void checkUniquesNonet(Square sq)//check to see if an individual square has a unique possible number
    {
        List<Integer> possUniques = new ArrayList<Integer>(sq.getPossiblesList());
        int squareRow = sq.getRow();
        int squareCol = sq.getCol();
        for (int i = 0; i < possUniques.size(); i++)
        {
            int num = possUniques.get(i);
            int startRow = (int)((int)(squareRow / 3) * 3);
            int startCol = (int)((int)(squareCol / 3) * 3);
            for (int row = startRow; row < startRow + 3; row++)
            {
                for (int col = startCol; col < startCol + 3; col++)
                {
                    if ((!(row == squareRow && col == squareCol)) && board[row][col].has(num) && possUniques.indexOf(num) != -1)
                    {
                        possUniques.remove(possUniques.indexOf(num));
                        i--;
                    }
                }
            }
        }
        if (possUniques.size() == 1)
        {
            sq.setFinalNum(possUniques.get(0));
        }
    }
    public void setAll()//uses setPossibles, which sets one square, for every square in the grid
    {
        for (int i = 0; i < 9; i++)
        {
            for (int j = 0; j < 9; j++)
            {
                setPossibles(board[i][j]);
                checkUniquesVertically(board[i][j]);
                checkUniquesHorizontally(board[i][j]);
                checkUniquesNonet(board[i][j]);
            }
        }
    }
    
    public String toString()
    {
        String grid = "";
        for (int i = 0; i < 9; i++)
        {
            for (int j = 0; j < 9; j++)
            {
                grid += (new Integer(board[i][j].getFinalNum()).toString());
                grid += "  ";
                if (j % 3 == 2)
                    grid += "|  ";
            }
            grid += "\n";
            if (i % 3 == 2)
                grid += "---------------------------------" + "\n";
        }
        return grid;
    }
    public String deBugToString()
    {
        String str = "";
        int numSolved = 0;
        for (int i = 0; i < 9; i++)
        {
            for (int j = 0; j < 9; j++)
            {
                str += " square " + ((i*9) + j) + "\t" + (board[i][j].getPossNums()) + "\n";
                if (board[i][j].possNumsLength() == 1)
                    numSolved++;
            }
        }
        str += " number of squares solved: " + numSolved + "\n";
        return str;
    }
    
    public boolean solved()//returns whether or not the whole board has been filled
    {
        for (int i = 0; i < 9; i++)
        {
            for (int j = 0; j < 9; j++)
            {
                if (board[i][j].getFinalNum() == 0)
                {
                    return false;
                }
            }
        }
        return true;
    }
}
