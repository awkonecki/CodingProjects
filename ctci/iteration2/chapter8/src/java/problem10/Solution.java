import java.util.Queue;
import java.util.LinkedList;

public class Solution {
    // [F]uncational

    // Would have to know if diagonal would also be subjected to the fill.
    public static void fill(int [][] matrix, int pointX, int pointY, int newValue) {
        // input validation
        if (matrix == null || matrix.length <= 0 || matrix[pointX][pointY] == newValue) {
            // No work, invalid input.
            return;
        }

        int [][] copy = new int [matrix.length][matrix[0].length];
        int oldColor = matrix[pointX][pointY];

        for (int row = 0; row < matrix.length; row++) {
            for (int col = 0; col < matrix[row].length; col++) {
                copy[row][col] = matrix[row][col];
            }
        }

        fill(copy, pointX, pointY, oldColor, newValue);

        Queue<Integer> xQueue = new LinkedList<Integer>();
        Queue<Integer> yQueue = new LinkedList<Integer>();
        xQueue.add(pointX);
        yQueue.add(pointY);

        while (!xQueue.isEmpty() && !yQueue.isEmpty()) {
            pointX = xQueue.remove();
            pointY = yQueue.remove();

            if (matrix[pointX][pointY] == oldColor) {
                matrix[pointX][pointY] = newValue;

                if (pointX + 1 < matrix.length && matrix[pointX + 1][pointY] == oldColor) {
                    xQueue.add(pointX + 1);
                    yQueue.add(pointY);
                }

                if (pointX - 1 >= 0 && matrix[pointX - 1][pointY] == oldColor) {
                    xQueue.add(pointX - 1);
                    yQueue.add(pointY);    
                }

                if (pointY + 1 < matrix[pointX].length && matrix[pointX][pointY + 1] == oldColor) {
                    xQueue.add(pointX);
                    yQueue.add(pointY + 1);
                }

                if (pointY - 1 >= 0 && matrix[pointX][pointY - 1] == oldColor) {
                    xQueue.add(pointX);
                    yQueue.add(pointY - 1);
                }
            }
        }

        for (int row = 0; row < matrix.length; row++) {
            for (int col = 0; col < matrix[row].length; col++) {
                assert (matrix[row][col] == copy[row][col]);
            }
        }
    }

    private static void fill(int [][] matrix, int pointX, int pointY, int oldValue, int newValue) {
        if (pointX < 0 || pointY < 0 || 
            pointX >= matrix.length || pointY >= matrix[pointX].length || 
            matrix[pointX][pointY] != oldValue) 
        {
            return;
        }

        matrix[pointX][pointY] = newValue;
        fill(matrix, pointX + 1, pointY, oldValue, newValue);
        fill(matrix, pointX - 1, pointY, oldValue, newValue);
        fill(matrix, pointX, pointY + 1, oldValue, newValue);
        fill(matrix, pointX, pointY - 1, oldValue, newValue);
    }

    // Analysis 
    // 1. Does the problem have optimal substructure
    // Yes each level of the recursive call is self contained.
    // 2. Does the problem have reoccurring problems?
    // No A single point in the matrix does not reoccur with the same matrix
    // state.

    // Performance
    // Memory 
    // O(M*N) 
    // due to case of the whole picture being filled, worse case stack space.

    // Time 
    // O(M*N) - have to visit each location once.

    public static void main(String [] args) {
        int [][] matrix = new int [][] {
            {1,1,1,1},
            {1,2,3,4},
            {1,2,1,1}
        };

        fill(matrix, 2, 3, 4);

        for (int row = 0; row < matrix.length; row++) {
            for (int col = 0; col < matrix[row].length; col++) {
                System.out.print(matrix[row][col]);
            }
            System.out.println("");
        }
        System.out.println("");
    }
}