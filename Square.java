import java.util.*;
/**
 * Write a description of class Square here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Square
{
    private List<Integer> possibleNums;//a list of all possible numbers that the square might contain
    private int finalNum;//has the correct number, if known. If not known, is set to zero.
    private int row;//row that the square is in
    private int col;//column that the square is in
    public Square(int row, int col, int num)
    {
        this.row = row;
        this.col = col;
        possibleNums = new ArrayList<Integer>();
        if (num > 0)//if number is known, it is final and no other possible values need to be set
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
        return finalNum;
    }
    public List<Integer> getPossiblesList()
    {
        return possibleNums;
    }
    public boolean has(int num)
    {
        for (int i = 0; i < possibleNums.size(); i++)
        {
            if (possibleNums.get(i) == num)
                return true;
        }
        return false;
    }
    public void setFinalNum(int num)//if the correct number is discovered, set it as finalNum and remove all 
                                    //other potential values from possibleNums
    {
        finalNum = num;
        possibleNums = new ArrayList<Integer>();
        possibleNums.add(num);
    }
    public void removePossNum(int num)//removes a given number from the list of possible ones 
    {
        if (finalNum > 0)
            return;
        if (num == 0)
        {
            return;
        }
        for (int i = 0; i < possibleNums.size(); i++)
        {
            if (possibleNums.get(i).intValue() == num)
            {
                possibleNums.remove(i);
            }
        }
        if (possibleNums.size() == 1)
        {
            finalNum = possibleNums.get(0);
        }
    }
    
    public String getPossNums()
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
