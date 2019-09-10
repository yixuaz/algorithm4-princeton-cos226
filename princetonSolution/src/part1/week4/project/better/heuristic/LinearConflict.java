package part1.week4.project.better.heuristic;

public class LinearConflict implements HeuristicFunction {
    @Override
    public int calculate(char[] tiles, int manhantan) {
        int n = (int) Math.sqrt(tiles.length);
        manhantan += linearConflict(tiles, n, true);
        manhantan += linearConflict(tiles, n, false);
        return manhantan;
    }

    private int linearConflict(char[] tiles, int n, boolean isRowOrCol) {
        int result = 0;
        for (int i = 0; i < n; i++) { // isRow -> i = row, j = col, isCol -> i = col, j = row
            char seenMaxCellValue = 0;
            for (int j = 0; j < n; j++) {
                char cellValue = tiles[isRowOrCol ? i * n + j : j * n + i];
                int tarRowOrCol = isRowOrCol ? (cellValue - 1) / n : (cellValue - 1) % n;
                if (cellValue == 0 || tarRowOrCol != i) // non-zero tile is not in its goal column or row
                    continue;
                if (cellValue > seenMaxCellValue) {
                    seenMaxCellValue = cellValue;
                } else { // tar value samller than pre value
                    result += 2;
                }
            }
        }
        return result;
    }
}
