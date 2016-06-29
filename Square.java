import java.util.*;
/**
 * An object that represents one square on the Sudoku board
 * 
 * @author (Grace Robbins) 
 */
public class Square
{
    private List<Integer> possibleNums;//a list of all possible numbers that the square might contain
    private int finalNum;//variable that contains the correct number for the square, if known. If not known, is set to zero.
    private int row;//row that the square is in
    private int col;//column that the square is in
    public Square(int row, int col, int num)
    {
        this.row = row;
        this.col = col;
        possibleNums = new ArrayList<Integer>();
        if (num > 0)//if number is known (a nonzero num is passed), it is final and no other possible values need to be set
        {
            setFinalNum(num);
        }
        else//if number is not known, create a list of numbers 1-9 that the square might contain. This will be narrowed down later.
        {
            finalNum = 0;
            for (int i = 1; i <= 9; i++)
            {
                possibleNums.add(i);
            }
        }
    }
    public int getRow()
    {
        return row;
    }
    public int getCol()
    {
        return col;
    }
    public int getFinalNum()
    {
        return finalNum;//finalNum is zero if the number in the square is not yet determined
    }
    public List<Integer> getPossiblesList()
    {
        return possibleNums;
    }
    public boolean has(int num)//returns true if the square may possibly contain num (it's in the list of possibleNums)
    {
        for (int i = 0; i < possibleNums.size(); i++)
        {
            if (possibleNums.get(i) == num)
                return true;
        }
        return false;
    }
    public void setFinalNum(int num)//if the correct number for the square is discovered, set it as finalNum and remove all 
                                    //other potential values from possibleNums
    {
        finalNum = num;
        possibleNums = new ArrayList<Integer>();
        possibleNums.add(num);
    }
    public void removePossNum(int num)//removes a given number from the square's list of possible ones. This helps narrow down
    {                                  //the possibilities of what the square might be.
        if (finalNum > 0)//if the square's number is already known, do nothing
            return;
        if (num == 0)//if the num passed is 0, it's not trying to remove anything, so do nothing
        {
            return;
        }
        for (int i = 0; i < possibleNums.size(); i++)//traverse through the list of possibleNums and remove num if it's there.
        {
            if (possibleNums.get(i).intValue() == num)
            {
                possibleNums.remove(i);
            }
        }
        if (possibleNums.size() == 1)//if there is only one number left that the square could be, that is the square's final number
        {
            finalNum = possibleNums.get(0);
        }
    }
    
    public String getPossNums()//used for debuggins
    {
        String str = "";
        for (int i = 0; i < possibleNums.size(); i++)
        {
            str += possibleNums.get(i);
            str += ", ";
        }
        return str;
    }
    public int possNumsLength()
    {
        return possibleNums.size();
    }
}
