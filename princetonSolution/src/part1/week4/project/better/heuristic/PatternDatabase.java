package part1.week4.project.better.heuristic;

import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

public class PatternDatabase implements HeuristicFunction {
    private static final String FILE_SUFFIX = ".db";
    private static final String FILE_PATH = "princetonSolution\\src\\part1\\week4\\project\\better\\db\\";

    private enum PatternDatabaseType {
        PUZZLE15_6_6_1(new byte[]{2, 3, 4}),
        PUZZLE15_6_6_2(new byte[]{1, 5, 6, 9, 10, 13}),
        PUZZLE15_6_6_3(new byte[]{7, 8, 11, 12, 14, 15});
        private static final int NOT_FOUND = -1;
        private byte[] costTable;
        private byte[] tilesPattern;

        private PatternDatabaseType(byte[] tilesInPattern) {
            this.tilesPattern = tilesInPattern;
            costTable = new byte[1 << (tilesInPattern.length << 2)];
            loadCostTable();
        }

        public static int calculateCost(char[] tiles) {
            int cost = 0;
            for (PatternDatabaseType type : values()) {
                cost += type.getCost(tiles);
            }
            return cost;
        }

        private void loadCostTable() {
            try (DataInputStream inputStream = new DataInputStream(new BufferedInputStream(
                    new FileInputStream(new File(FILE_PATH + name() + FILE_SUFFIX))))) {
                int i = 0;
                while (i < costTable.length) {
                    costTable[i++] = inputStream.readByte();
                }
            } catch (IOException e) {
                throw new RuntimeException("Impossible to load databasepattern", e);
            }
        }

        private int getCost(char[] tiles) {
            return costTable[getIndex(tiles)];
        }

        private int getPatternPosition(char value) {
            for (int i = 0; i < tilesPattern.length; i++) {
                if (tilesPattern[i] == value)
                    return i;
            }
            return NOT_FOUND;
        }

        private int getIndex(char[] tiles) {
            int index = 0;
            for (int i = 0; i < tiles.length; i++) {
                int position = getPatternPosition(tiles[i]);
                if (position != NOT_FOUND) {
                    index |= i << (position << 2);
                }
            }
            return index;
        }
    }

    @Override
    public int calculate(char[] tiles, int manhantan) {
        if (tiles.length != 16)
            return manhantan;
        return PatternDatabaseType.calculateCost(tiles);
    }
}
