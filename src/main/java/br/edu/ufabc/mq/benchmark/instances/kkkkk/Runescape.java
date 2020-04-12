package br.edu.ufabc.mq.benchmark.instances.kkkkk;

import java.util.HashSet;
import java.util.Set;

public class Runescape {

	static Set<Integer> estados = new HashSet<>();

	public static void main(final String[] args) throws Exception {
		//		final Tabuleiro t = Tabuleiro.buildTabuleiro(List.of(0, 1, 2, 3, 4, 5, 6, 7, 8));

		final Tabuleiro tabuleiro = Tabuleiro.getRandomTabuleiro();

		estados.add(tabuleiro.hashCode());
		tabuleiro.prettyPrint();

		bruteForce(tabuleiro);
	}

	private static boolean bruteForce(final Tabuleiro t) {
		if (t.isComplete()) {
			System.out.println("Resolvido!");
			return true;
		}
		boolean moved = true;
		boolean solved = false;

		moved = t.up(1);

		if (estados.add(t.hashCode())) {
			t.prettyPrint();
			solved = bruteForce(t);
		}
		if (solved) {
			return true;
		}
		if (moved) {
			t.down(1);
		}

		moved = t.down(1);
		if (estados.add(t.hashCode())) {
			t.prettyPrint();
			solved = bruteForce(t);
		}
		if (solved) {
			return true;
		}
		if (moved) {
			t.up(1);
		}

		moved = t.left(1);
		if (estados.add(t.hashCode())) {
			t.prettyPrint();
			solved = bruteForce(t);
		}
		if (solved) {
			return true;
		}
		if (moved) {
			t.right(1);
		}

		moved = t.right(1);
		if (estados.add(t.hashCode())) {
			t.prettyPrint();
			solved = bruteForce(t);
		}
		if (solved) {
			return true;
		}
		if (moved) {
			t.left(1);
		}

		return false;
	}
}
