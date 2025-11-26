package by.it.group410971.gurinovich.lesson14;

import java.util.Scanner;

public class StatesHanoiTowerC {
    static class DSU {
        int[] parent;
        int[] size;

        DSU(int n) {
            parent = new int[n];
            size = new int[n];
            for (int i = 0; i < n; i++) {
                parent[i] = i;
                size[i] = 1;
            }
        }

        int find(int x) {
            if (parent[x] != x)
                parent[x] = find(parent[x]);
            return parent[x];
        }

        void union(int a, int b) {
            int ra = find(a);
            int rb = find(b);
            if (ra == rb) return;
            if (size[ra] < size[rb]) {
                int t = ra;
                ra = rb;
                rb = t;
            }
            parent[rb] = ra;
            size[ra] += size[rb];
        }

        int[] getClusterSizes(int n) {
            boolean[] seen = new boolean[n];
            int[] temp = new int[n];
            int count = 0;
            for (int i = 0; i < n; i++) {
                int root = find(i);
                if (!seen[root]) {
                    seen[root] = true;
                    temp[count++] = size[root];
                }
            }
            int[] result = new int[count];
            System.arraycopy(temp, 0, result, 0, count);
            return result;
        }
    }

    static int step = 0;
    static int[][] states;

    static void hanoi(int n, int from, int to, int aux, int[] rods) {
        if (n == 0) return;

        hanoi(n - 1, from, aux, to, rods);

        rods[from]--;
        rods[to]++;
        step++;
        states[step][0] = rods[0];
        states[step][1] = rods[1];
        states[step][2] = rods[2];

        hanoi(n - 1, aux, to, from, rods);
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int N = sc.nextInt();

        int totalSteps = (1 << N) - 1;
        states = new int[totalSteps + 1][3];
        int[] rods = {N, 0, 0};

        hanoi(N, 0, 1, 2, rods);

        DSU dsu = new DSU(totalSteps + 1);

        for (int i = 1; i <= totalSteps; i++) {
            int maxI = Math.max(states[i][0], Math.max(states[i][1], states[i][2]));
            for (int j = i + 1; j <= totalSteps; j++) {
                int maxJ = Math.max(states[j][0], Math.max(states[j][1], states[j][2]));
                if (maxI == maxJ)
                    dsu.union(i, j);
            }
        }

        int[] sizes = dsu.getClusterSizes(totalSteps + 1);

        for (int i = 0; i < sizes.length; i++) {
            for (int j = i + 1; j < sizes.length; j++) {
                if (sizes[i] > sizes[j]) {
                    int t = sizes[i];
                    sizes[i] = sizes[j];
                    sizes[j] = t;
                }
            }
        }

        for (int i = 0; i < sizes.length; i++) {
            if (sizes[i] == 0) continue;
            if (i > 0) System.out.print(" ");
            System.out.print(sizes[i]);
        }
        System.out.println();
    }
}