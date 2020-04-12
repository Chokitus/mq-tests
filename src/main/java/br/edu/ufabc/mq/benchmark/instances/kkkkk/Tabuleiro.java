package br.edu.ufabc.mq.benchmark.instances.kkkkk;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import lombok.ToString;

@ToString
public class Tabuleiro {

	public static final int SIZE = 3;
	public static final int SQUARE = SIZE * SIZE;

	int[] matrix = new int[SQUARE];
	int currentPos;

	public static Tabuleiro getRandomTabuleiro() {
		final List<Integer> list = new ArrayList<>();
		for (int i = 0; i < Tabuleiro.SQUARE; i++) {
			list.add(i);
		}
		Collections.shuffle(list);

		return buildTabuleiro(list);
	}

	public static Tabuleiro buildTabuleiro(final List<Integer> list) {
		final Tabuleiro tabuleiro = new Tabuleiro();
		for (int i = 0; i < list.size(); i++) {
			if(list.get(i) == Tabuleiro.SQUARE - 1) {
				tabuleiro.currentPos = i;
			}
			tabuleiro.matrix[i] = list.get(i);
		}
		return tabuleiro;
	}

	public boolean left(final int times) {
		if (times == 0 || currentPos % SIZE == 0) {
			return times == 0;
		}
		matrix[currentPos] = matrix[currentPos - 1];
		currentPos--;
		matrix[currentPos] = SQUARE - 1;
		return left(times - 1);
	}

	public boolean right(final int times) {
		if (times == 0 || (currentPos + 1) % SIZE == 0) {
			return times == 0;
		}
		matrix[currentPos] = matrix[currentPos + 1];
		currentPos++;
		matrix[currentPos] = SQUARE - 1;
		return right(times - 1);
	}

	public boolean up(final int times) {
		if (times == 0 || currentPos < SIZE) {
			return times == 0;
		}
		matrix[currentPos] = matrix[currentPos - SIZE];
		currentPos-=SIZE;
		matrix[currentPos] = SQUARE - 1;
		return up(times - 1);
	}

	public boolean down(final int times) {
		if (times == 0 || currentPos >= SQUARE - SIZE) {
			return times == 0;
		}
		matrix[currentPos] = matrix[currentPos + SIZE];
		currentPos+=SIZE;
		matrix[currentPos] = SQUARE - 1;
		return down(times - 1);
	}

	public boolean isComplete() {
		for (int i = 0; i < matrix.length; i++) {
			if(matrix[i] != i) {
				return false;
			}
		}
		return true;
	}

	public void prettyPrint() {
		for(int y = 0; y < SIZE; y++) {
			final StringBuilder b = new StringBuilder();
			for(int x = 0; x < SIZE; x++) {
				b.append(matrix[y*SIZE + x]).append(" ");
			}
			System.out.println(b.toString());
		}
		System.out.println("--------------------------");
	}

	@Override
	public int hashCode() {
		final StringBuilder builder = new StringBuilder();
		for (final int i : matrix) {
			builder.append(i);
		}
		return builder.toString().hashCode();
	}
}