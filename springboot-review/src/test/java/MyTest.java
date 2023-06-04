/**
 * @author eric
 * @date 4/6/2023
 */
public class MyTest {

    public static void main(String[] args) {
        int[][] grid = new int[][]{{0,0,0,1,1,1,0,0,0,1,0,0,0,0,0,0,0,1,1,1,0,0,1,1,1,1,0,0,1,0},
                {1,1,0,1,1,1,0,1,1,1,1,1,0,1,0,1,0,1,1,0,1,0,1,1,1,1,0,0,1,0},
        };
        int m = grid.length, n = grid[0].length;
        for(int i = 0; i < m; i++){
            for(int j = 0; j < n; j++){
                if((i * j == 0 || j == n - 1 || i == m - 1) && grid[i][j] == 0){
                    dfs(grid, i, j);
                }
            }
        }
        int count = 0;
        for(int i = 0; i < m; i++){
            for(int j = 0; j < n; j++){
                if(grid[i][j] == 0){
                    dfs(grid, i, j);
                    count++;
                }
            }
        }

        System.out.println(count);

    }
    private static void dfs(int[][] grid, int i, int j){
        int m = grid.length, n = grid[0].length;
        if(i < 0 || i >= m || j >= n || j < 0 || grid[i][j] == 1)return;

        int[][] direction = new int[][]{{1, 0}, {-1, 0}, {0, 1}, {0, -1}};
        grid[i][j] = 1;
        for(int k = 0; k < 4; k++){
            dfs(grid, i + direction[k][0], j + direction[k][1]);
        }
    }

}
