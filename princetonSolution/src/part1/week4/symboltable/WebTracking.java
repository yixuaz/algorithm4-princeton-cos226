package part1.week4.symboltable;

public class WebTracking {
    private final int webSiteSize;
    private final int userSize;
    private final int[][] tables;
    public WebTracking(int webSiteSize, int userSize) {
        this.webSiteSize = webSiteSize;
        this.userSize = userSize;
        tables = new int[webSiteSize][userSize];
    }

    public void visit(int userId, int webId) {
        tables[webId][userId]++;
    }
    public int count(int userId, int webId) {
        return tables[webId][userId];
    }
}
