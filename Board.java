
/**
 * Holds a 9x9 array of Square objects, which represents the Sudoku board.
 * 
 * @author Grace Robbins 
 */
import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;
public class Board
{
    public Square[][] board;
    
    /**
    *   Reads numbers from a puzzle file and created a grid of Square objects with those numbers. 
    *   Zeros represent spaces that aren't filled in yet.
    */
    public Board(String fileName) throws FileNotFoundException
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
    
    /**
    *  Takes a Square sq. Find every other nonzero number in that row and column and remove it from sq's 
    *  list of possible numbers.
    *  For example, if another Square in that column is a 6, sq cannot possibly be a 6 as well. So 6 is removed
    *  from sq's list of possibles. 
    */
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
    
    /**
    *  A nonet as set of 9 squares that a Sudoku board is divided into. Does the same thing as the above method but in a nonet
    *  pattern, instead of a row and column pattern.
    */
    private void removeInNonet(Square sq)
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
    
    /**
    *  Sometimes narrowing Squares down to one value is impossible, so a different approach must be taken. Say, for example
    *  a Square has the possible numbers 3, 4, and 8, and a Square in the same column has the possibles 4 and 8. Though neither
    *  Square can be narrowed down to one possible number using the existing methods, we know that the first Square must be
    *  a 3 because it's the only Square that can be a 3.
    *  checkUniquesVertically checks for numerbers in sq's possibles list that aren't in any other Square in the column.
    */
    public void checkUniquesVertically(Square sq)
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
    
    /**
    *  Does the same as checkUniquesVertically but in rows instead of columns.
    */
    public void checkUniquesHorizontally(Square sq)
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
    
    /**
    *  Does the same as checkUniquesVertically but in nonets instead of columns.
    */
    public void checkUniquesNonet(Square sq)
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
    
    /**
    *  Goes through each Square in the board and uses all of the above methods to narrow down it's possibleNums list
    */
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
    
    
    /**
    *  returns true if the whole board has been filled
    */
    public boolean solved()
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
