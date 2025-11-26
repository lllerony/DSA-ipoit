package by.it.group410971.gurinovich.lesson14;

import java.util.*;

public class SitesB {

    static class DSU {
        private Map<String, String> parent;
        private Map<String, Integer> size;

        DSU() {
            parent = new HashMap<>();
            size = new HashMap<>();
        }

        String find(String x) {
            if (!parent.containsKey(x)) {
                parent.put(x, x);
                size.put(x, 1);
                return x;
            }
            
            if (!parent.get(x).equals(x)) {
                parent.put(x, find(parent.get(x)));
            }
            return parent.get(x);
        }

        void union(String x, String y) {
            String rootX = find(x);
            String rootY = find(y);
            
            if (rootX.equals(rootY)) return;
            
            if (size.get(rootX) < size.get(rootY)) {
                parent.put(rootX, rootY);
                size.put(rootY, size.get(rootY) + size.get(rootX));
            } else {
                parent.put(rootY, rootX);
                size.put(rootX, size.get(rootX) + size.get(rootY));
            }
        }
        
        List<Integer> getClusterSizes() {
            Map<String, Integer> clusterSizes = new HashMap<>();
            
            for (String site : parent.keySet()) {
                String root = find(site);
                clusterSizes.put(root, size.get(root));
            }
            
            List<Integer> sizes = new ArrayList<>(clusterSizes.values());
            Collections.sort(sizes, Collections.reverseOrder());
            return sizes;
        }
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        DSU dsu = new DSU();
        
        while (true) {
            String line = scanner.nextLine().trim();
            if (line.equals("end")) break;
            
            String[] sites = line.split("\\+");
            if (sites.length == 2) {
                dsu.union(sites[0], sites[1]);
            }
        }
        
        List<Integer> result = dsu.getClusterSizes();
        
        for (int i = 0; i < result.size(); i++) {
            if (i > 0) System.out.print(" ");
            System.out.print(result.get(i));
        }
        System.out.println();
        
        scanner.close();
    }
}