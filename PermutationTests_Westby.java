package test;

import static org.junit.Assert.assertEquals;

import java.util.List;
import java.util.Set;

import org.junit.Test;

import combinatorics.Permutation;
import combinatorics.PermutationImpl_Westby;
import combinatorics.PermutationUtils_Westby;

public class PermutationTests_Westby
{
	private enum Vowel
	{
		A, E, I, O, U;
	}
	
	protected static <E> Permutation<E> getPermutation(Set<List<E>> cycles, Set<E> domain)
	{
		return new PermutationImpl_Westby<E>(cycles, domain);
	}
	
	@Test
	public void vowelTest()
	{
		List<Vowel> cycle_UIAE = List.of(Vowel.U, Vowel.I, Vowel.A, Vowel.E);
		
		Set<List<Vowel>> cycles = Set.of(cycle_UIAE);
		
		Set<Vowel> domain = Set.of(Vowel.A, Vowel.E, Vowel.I, Vowel.O, Vowel.U);
		
		Permutation<Vowel> p = getPermutation(cycles, domain);
		
		assertEquals(Vowel.E, p.getImage(Vowel.A));
		assertEquals(Vowel.U, p.getImage(Vowel.E));
		assertEquals(Vowel.A, p.getImage(Vowel.I));
		assertEquals(Vowel.O, p.getImage(Vowel.O));
		assertEquals(Vowel.I, p.getImage(Vowel.U));
		
		assertEquals(domain, p.getDomain());
	}
	
	@Test
	public void vowelTest2()
	{
		List<Vowel> cycle_UIAE = List.of(Vowel.U, Vowel.I, Vowel.A, Vowel.E);
		List<Vowel> cycle_O = List.of(Vowel.O);
		
		Set<List<Vowel>> cycles = Set.of(cycle_UIAE, cycle_O);
		
		Set<Vowel> domain = Set.of(Vowel.A, Vowel.E, Vowel.I, Vowel.O, Vowel.U);
		
		Permutation<Vowel> p = getPermutation(cycles, domain);
		
		assertEquals(Vowel.E, p.getImage(Vowel.A));
		assertEquals(Vowel.U, p.getImage(Vowel.E));
		assertEquals(Vowel.A, p.getImage(Vowel.I));
		assertEquals(Vowel.O, p.getImage(Vowel.O));
		assertEquals(Vowel.I, p.getImage(Vowel.U));
		
		assertEquals(domain, p.getDomain());

		List<Vowel> cycle_UIAE2 = List.of(Vowel.A, Vowel.E, Vowel.U, Vowel.I);
		
		Set<List<Vowel>> cycles2 = Set.of(cycle_UIAE2);
		
		Permutation<Vowel> p2 = getPermutation(cycles2, domain);
		
		assertEquals(Vowel.E, p2.getImage(Vowel.A));
		assertEquals(Vowel.U, p2.getImage(Vowel.E));
		assertEquals(Vowel.A, p2.getImage(Vowel.I));
		assertEquals(Vowel.O, p2.getImage(Vowel.O));
		assertEquals(Vowel.I, p2.getImage(Vowel.U));
		
		assertEquals(domain, p2.getDomain());
		
		assertEquals(p, p2);
	}
	
	@Test(expected=AssertionError.class)
	public void vowelTest_DomainDoesntContainEachElementInEachCycle()
	{
		List<Vowel> cycle_UIAE = List.of(Vowel.U, Vowel.I, Vowel.A, Vowel.E);

		Set<List<Vowel>> cycles = Set.of(cycle_UIAE);
		
		Set<Vowel> domain = Set.of(Vowel.A);
		
		getPermutation(cycles, domain);
	}
	
	@Test(expected=AssertionError.class)
	public void vowelTest_CyclesOverlap()
	{
		List<Vowel> cycle_UIAE = List.of(Vowel.U, Vowel.I, Vowel.A, Vowel.E);
		List<Vowel> cycle_EU = List.of(Vowel.E, Vowel.U);

		Set<List<Vowel>> cycles = Set.of(cycle_UIAE, cycle_EU);

		Set<Vowel> domain = Set.of(Vowel.A, Vowel.E, Vowel.I, Vowel.O, Vowel.U);
		
		getPermutation(cycles, domain);
	}
	
	@Test
	public void testGetInverse() {
	    List<Integer> cycle123 = List.of(1, 2, 3);
	    Set<List<Integer>> cycles = Set.of(cycle123);
	    Set<Integer> domain = Set.of(1, 2, 3, 4);

	    Permutation<Integer> p = getPermutation(cycles, domain);
	    Permutation<Integer> inverseP = PermutationUtils_Westby.getInverse(p);

	    assertEquals(Integer.valueOf(3), inverseP.getImage(1));
	    assertEquals(Integer.valueOf(1), inverseP.getImage(2));
	    assertEquals(Integer.valueOf(2), inverseP.getImage(3));
	    assertEquals(Integer.valueOf(4), inverseP.getImage(4)); // Should map to itself
	}
	
	@Test
	public void testCompose() {
	    List<Integer> cycle12 = List.of(1, 2);
	    List<Integer> cycle34 = List.of(3, 4);
	    Set<List<Integer>> cycles1 = Set.of(cycle12);
	    Set<List<Integer>> cycles2 = Set.of(cycle34);

	    Permutation<Integer> p1 = getPermutation(cycles1, Set.of(1, 2, 3, 4));
	    Permutation<Integer> p2 = getPermutation(cycles2, Set.of(1, 2, 3, 4));

	    Permutation<Integer> composedP = PermutationUtils_Westby.compose(p1, p2);

	    // Composition should apply p2 first, then p1
	    assertEquals(Integer.valueOf(2), composedP.getImage(1));
	    assertEquals(Integer.valueOf(1), composedP.getImage(2));
	    assertEquals(Integer.valueOf(4), composedP.getImage(3));
	    assertEquals(Integer.valueOf(3), composedP.getImage(4));
	}
	
	@Test
	public void testIsCyclicTrue() {
	    List<Integer> cycle123 = List.of(1, 2, 3);
	    Set<List<Integer>> cycles = Set.of(cycle123);

	    Permutation<Integer> p = getPermutation(cycles, Set.of(1, 2, 3));

	    assertEquals(true, PermutationUtils_Westby.isCyclic(p));
	}

	@Test
	public void testIsCyclicFalse() {
	    List<Integer> cycle12 = List.of(1, 2);
	    List<Integer> cycle3 = List.of(3);
	    Set<List<Integer>> cycles = Set.of(cycle12, cycle3);

	    Permutation<Integer> p = getPermutation(cycles, Set.of(1, 2, 3));

	    assertEquals(false, PermutationUtils_Westby.isCyclic(p));
	}


	@Test
	public void testGetCycle() {
	    List<Integer> cycle123 = List.of(1, 2, 3);
	    Set<List<Integer>> cycles = Set.of(cycle123);

	    Permutation<Integer> p = getPermutation(cycles, Set.of(1, 2, 3, 4));

	    List<Integer> cycle = PermutationUtils_Westby.getCycle(p, 1);
	    assertEquals(List.of(1, 2, 3), cycle);

	    List<Integer> cycle4 = PermutationUtils_Westby.getCycle(p, 4);
	    assertEquals(List.of(4), cycle4); // 4 maps to itself
	}

	
	@Test(expected = AssertionError.class)
	public void testInvalidCycle() {
	    List<Integer> cycle = List.of(1, 2, 5); // 5 is not in the domain
	    Set<List<Integer>> cycles = Set.of(cycle);

	    getPermutation(cycles, Set.of(1, 2, 3, 4));
	}

	@Test(expected = AssertionError.class)
	public void testOverlappingCycles() {
	    List<Integer> cycle1 = List.of(1, 2);
	    List<Integer> cycle2 = List.of(2, 3); // 2 appears in both cycles
	    Set<List<Integer>> cycles = Set.of(cycle1, cycle2);

	    getPermutation(cycles, Set.of(1, 2, 3, 4));
	}

	
	
	


	
}
