package part2.week3.project;

import edu.princeton.cs.algs4.FlowEdge;
import edu.princeton.cs.algs4.FlowNetwork;
import edu.princeton.cs.algs4.FordFulkerson;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BaseballElimination {
    private final Map<String, Integer> teamNameToId;
    private final int[] wins;
    private final int[] losses;
    private final int[] remainings;
    private final int[][] matches;
    private final String[] teamNames;
    private final int s, t, flowVertexs, teamCnt;
    private final String maxTeamName;
    private final int maxWins;

    // create a baseball division from given filename in format specified below
    public BaseballElimination(String filename) {
        In in = new In(filename);
        teamNameToId = new HashMap<>();
        teamCnt = in.readInt();
        int n = teamCnt - 1;
        flowVertexs = 2 + n * (n - 1) / 2 + n;
        s = flowVertexs - 2;
        t = flowVertexs - 1;
        wins = new int[teamCnt];
        losses = new int[teamCnt];
        remainings = new int[teamCnt];
        teamNames = new String[teamCnt];
        matches = new int[teamCnt][teamCnt];
        int id = 0, curMaxWins = 0;
        String curMaxTeam = null;
        for (int j = 0; j < teamCnt; j++) {
            teamNames[id] = in.readString();
            wins[id] = in.readInt();
            if (curMaxWins < wins[id]) {
                curMaxWins = wins[id];
                curMaxTeam = teamNames[id];
            }
            losses[id] = in.readInt();
            remainings[id] = in.readInt();
            for (int i = 0; i < teamCnt; i++) {
                matches[id][i] = in.readInt();
            }
            teamNameToId.put(teamNames[id], id++);
        }
        maxTeamName = curMaxTeam;
        maxWins = curMaxWins;
    }

    // number of teams
    public int numberOfTeams() {
        return teamCnt;
    }

    // all teams
    public Iterable<String> teams() {
        return Collections.unmodifiableSet(teamNameToId.keySet());
    }

    private void validTeam(String team) {
        if (!teamNameToId.containsKey(team))
            throw new IllegalArgumentException("invalid team " + team);
    }

    // number of wins for given team
    public int wins(String team) {
        validTeam(team);
        return wins[teamNameToId.get(team)];
    }

    // number of losses for given team
    public int losses(String team) {
        validTeam(team);
        return losses[teamNameToId.get(team)];
    }

    // number of remaining games for given team
    public int remaining(String team) {
        validTeam(team);
        return remainings[teamNameToId.get(team)];
    }

    // number of remaining games between team1 and team2
    public int against(String team1, String team2) {
        validTeam(team1);
        validTeam(team2);
        return matches[teamNameToId.get(team1)][teamNameToId.get(team2)];
    }

    // is given team eliminated?
    public boolean isEliminated(String team) {
        validTeam(team);
        if (trivialElimination(teamNameToId.get(team)))
            return true;
        return nonTrivialElimination(teamNameToId.get(team));
    }

    private boolean nonTrivialElimination(int tarId) {
        FlowNetwork flowNetwork = buildFlowNetwork(tarId);
        int expectMaxFlow = 0;
        for (FlowEdge e : flowNetwork.adj(s)) {
            expectMaxFlow += (int) e.capacity();
        }
        FordFulkerson fordFulkerson = new FordFulkerson(flowNetwork, s, t);
        return (int) fordFulkerson.value() != expectMaxFlow;
        // return Math.abs(expectMaxFlow - fordFulkerson.value()) > 0.000001;
    }

    private FlowNetwork buildFlowNetwork(int tarId) {
        FlowNetwork flowNetwork = new FlowNetwork(flowVertexs);
        int idx = teamCnt - 1;
        for (int i = 0; i < teamCnt; i++) {
            if (i == tarId) continue;
            int teamIIndex = i > tarId ? i - 1 : i;
            for (int j = i + 1; j < teamCnt; j++) {
                if (j == tarId) continue;
                int teamJIndex = j > tarId ? j - 1 : j;
                // set up s -> matches vertex
                flowNetwork.addEdge(new FlowEdge(s, idx, matches[i][j]));
                // set up matches vertex -> team vertex
                flowNetwork.addEdge(new FlowEdge(idx, teamIIndex, Double.POSITIVE_INFINITY));
                flowNetwork.addEdge(new FlowEdge(idx++, teamJIndex, Double.POSITIVE_INFINITY));
            }
        }
        for (int i = 0; i < teamCnt; i++) {
            if (i == tarId) continue;
            int capacity = wins[tarId] + remainings[tarId] - wins[i];
            if (capacity < 0) continue;
            int teamIIndex = i > tarId ? i - 1 : i;
            flowNetwork.addEdge(new FlowEdge(teamIIndex, t, capacity));
        }
        return flowNetwork;
    }

    private boolean trivialElimination(int id) {
        return wins[id] + remainings[id] < maxWins;
    }

    // subset R of teams that eliminates given team; null if not eliminated
    public Iterable<String> certificateOfElimination(String team) {
        validTeam(team);
        int tarId = teamNameToId.get(team);
        if (trivialElimination(tarId)) return Collections.singletonList(maxTeamName);
        FordFulkerson fordFulkerson = new FordFulkerson(buildFlowNetwork(tarId), s, t);
        List<String> teams = new ArrayList<>();
        for (int i = 0; i < numberOfTeams(); i++) {
            if (tarId == i) continue;
            int realIdx = i < tarId ? i : i - 1;
            if (fordFulkerson.inCut(realIdx))
                teams.add(teamNames[i]);
        }
        return teams.isEmpty() ? null : teams;
    }

    public static void main(String[] args) {
        BaseballElimination division = new BaseballElimination(args[0]);
        for (String team : division.teams()) {
            if (division.isEliminated(team)) {
                StdOut.print(team + " is eliminated by the subset R = { ");
                for (String t : division.certificateOfElimination(team)) {
                    StdOut.print(t + " ");
                }
                StdOut.println("}");
            } else {
                StdOut.println(team + " is not eliminated");
            }
        }
    }
}
