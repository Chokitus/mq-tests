package br.edu.ufabc.mq.benchmark.generic;

import java.util.ArrayDeque;
import java.util.Deque;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main {

	private static final String ZERO = "0";

	private static boolean bipartido(final String[][] adj, final int src, final int size) {
		final int[] cat = new int[size];
		for (int i = 0; i < size; ++i) {
			cat[i] = -1;
		}

		cat[src] = 1;

		final Deque<Integer> q = new ArrayDeque<>();
		q.add(src);

		while (!q.isEmpty()) {
			final int u = q.poll();
			for (int v = 0; v < size; ++v) {
				final boolean isAdjacent = adj[u][v].equals(ZERO); // Grafo inverso
				if (isAdjacent && cat[v] == -1) {
					cat[v] = 1 - cat[u];
					q.add(v);
				} else if (isAdjacent && cat[v] == cat[u]) {
					return false;
				}
			}
		}
		return true;
	}

	public static void main(final String[] args) throws IOException {
		try (BufferedReader reader = new BufferedReader(new InputStreamReader(System.in))) {
			final int n = Integer.parseInt(reader.readLine().trim());
			final String[][] adj = new String[n][];

			for (int i = 0; i < n; i++) {
				adj[i] = reader.readLine().trim().split(" ");
			}

			System.out.println(bipartido(adj, 0, n) ? "Bazinga!" : "Fail!");
		}
	}
}
