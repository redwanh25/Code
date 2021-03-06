package graph;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.Scanner;

public class MinCut {

	private int[][] residual;
	private int[] parent;
	private boolean[] vis;
	private int maX, miN;
	private int[][] graph;
	
	public MinCut(int size) {
		residual = new int[size][size];
		graph = new int[size][size];
		parent = new int[size];
		vis = new boolean[size];
	}
	
	public boolean bfs(int src, int dst) {
		Arrays.fill(vis, false);
		LinkedList <Integer> queue = new LinkedList<>();
		queue.add(src);
		vis[src] = true;
		while(!queue.isEmpty()) {
			int u = queue.pollFirst();
			for(int v = miN; v <= maX; v++) {
				if(vis[v] == false && residual[u][v] > 0) {
					queue.add(v);
					vis[v] = true;
					parent[v] = u;
					if(vis[dst] == true) {
						return true;
					}
				}
			}
		}
		return false;
	}
	
	public void dfs(int u) {
		vis[u] = true;
		for(int v = miN; v <= maX; v++) {
			if(vis[v] == false && residual[u][v] > 0) {
				dfs(v);
			}
		}
	}
	
	public int minCut(int src, int dst) {
		int Max = 0;
		while(bfs(src, dst)) {
			int flow = Integer.MAX_VALUE;
			for(int v = dst; v != src; v = parent[v]) {
				int u = parent[v];
				flow = Math.min(flow, residual[u][v]);
			}
			for(int v = dst; v != src; v = parent[v]) {
				int u = parent[v];
				residual[u][v] -= flow;
				residual[v][u] += flow;
			}
			Max += flow;
		}
		Arrays.fill(vis, false);
		dfs(src);
		System.out.println("Minimum cut : ");
		for(int u = miN; u <= maX; u++) {
			for(int v = miN; v <= maX; v++) {
				if(vis[u] == true && vis[v] == false && graph[u][v] > 0) {
					System.out.println(u + " --- " + v);
				}
			}
		}
		return Max;
	}
	
	public static void main(String[] args) {
		Scanner cin = new Scanner(System.in);
		int size = 200;
		int ver = cin.nextInt();
		int eg = cin.nextInt();
		MinCut g = new MinCut(size);
		for(int i = 0; i < eg; i++) {
			int u = cin.nextInt();
			int v = cin.nextInt();
			int wt = cin.nextInt();
			g.miN = Math.min(g.miN, Math.min(u, v));
			g.maX = Math.max(g.maX, Math.max(u, v));
			g.residual[u][v] = wt;
			g.graph[u][v] = wt;
		}
		int src , dst;
		src = cin.nextInt();
		dst = cin.nextInt();
		System.out.println("Min Cut : " + g.minCut(src, dst));
		cin.close();
	}
}

/*

6 10
0 1 16
0 2 13
1 2 10
2 1 4
1 3 12
3 2 9
2 4 14
4 3 7
3 5 20
4 5 4
0 5

7 11
1 2 3
5 2 1
2 3 4
3 1 3
1 4 3
3 4 1
3 5 2
4 5 2
6 7 9
5 7 1
4 6 6
1 7

4 5
7 9 2
7 8 2
7 11 1
11 8 2
8 9 4
7 9

maximum bipartite matching
10 13
0 1 1
0 2 1
0 3 1
0 4 1
1 5 1
1 6 1
2 6 1
3 6 1
3 7 1
4 7 1
5 10 1
6 10 1
7 10 1
0 10
Max = 3

*/