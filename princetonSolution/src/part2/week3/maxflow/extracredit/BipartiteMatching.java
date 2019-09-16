package part2.week3.maxflow.extracredit;

import edu.princeton.cs.algs4.FlowEdge;
import edu.princeton.cs.algs4.FlowNetwork;
import edu.princeton.cs.algs4.FordFulkerson;

import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class BipartiteMatching {
    private static final double INFINITY = 1000;
    private static final double EPSILON = 1E-9;
    public static Map<String, String> solve(Map<String, Set<String>> student2offer) {
        Map<String, Integer> company2Idx = fetchCompany(student2offer);
        int size = company2Idx.size();
        if (size != student2offer.size()) return Collections.emptyMap();
        String[] id2company = build(company2Idx);
        int v = 2 * size + 2;
        FlowNetwork flowNetwork = new FlowNetwork(v);
        int src = v - 2, tar = v - 1, idx = 0;
        Map<String, Set<FlowEdge>> student2Edge = new HashMap<>();
        for (Map.Entry<String, Set<String>> entry : student2offer.entrySet()) {
            String studentName = entry.getKey();
            student2Edge.put(studentName, new HashSet<>());
            flowNetwork.addEdge(new FlowEdge(src, idx, 1));
            for (String company : entry.getValue()) {
                FlowEdge flowEdge = new FlowEdge(idx, company2Idx.get(company), INFINITY);
                student2Edge.get(studentName).add(flowEdge);
                flowNetwork.addEdge(flowEdge);
            }
            idx++;
        }
        // add edge from company to tar
        for (int i = size; i < src; i++) {
            flowNetwork.addEdge(new FlowEdge(i, tar, 1));
        }
        FordFulkerson ff = new FordFulkerson(flowNetwork, src, tar);
        if (Math.abs(ff.value() - size) > EPSILON) return Collections.emptyMap();
        // perfect matching
        Map<String, String> result = new HashMap<>();
        for (String student : student2offer.keySet()) {
            for (FlowEdge edge : student2Edge.get(student)) {
                if (edge.flow() != 1) continue;
                result.put(student, id2company[edge.to()]);
                break;
            }
        }
        return result;
    }

    private static String[] build(Map<String, Integer> company2Idx) {
        String[] result = new String[2 * company2Idx.size()];
        for (Map.Entry<String, Integer> entry : company2Idx.entrySet()) {
            result[entry.getValue()] = entry.getKey();
        }
        return result;
    }

    private static Map<String, Integer> fetchCompany(Map<String, Set<String>> student2offer) {
        int base = student2offer.size();
        Set<String> companys = new HashSet<>();
        for (Set<String> offer : student2offer.values()) {
            companys.addAll(offer);
        }
        Map<String, Integer> result = new HashMap<>();
        int idx = base;
        for (String company : companys) {
            result.put(company, idx++);
        }
        return result;
    }
}
