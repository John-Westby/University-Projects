package combinatorics;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class PermutationImpl_Westby<E> implements Permutation<E>
{
    private final Map<E, E> permutationMap;
    private final Set<E> domain; 
	
	public PermutationImpl_Westby(Set<List<E>> cycles, Set<E> domain)
	{
		this.domain = domain;
		permutationMap = new HashMap<>();

		// ensure my domain contains all cycle elements
		for (List<E> cycle : cycles) {
			for (E element : cycle) {
				if (!domain.contains(element)) {
                    throw new AssertionError("Domain does not contain all cycle elements!!");
                }
            }
        }

        for (List<E> cycle : cycles) {
            if (!cycle.isEmpty() && (!cycle.equals(null))) {
                for (int i = 0; i < cycle.size(); i++) {
                    E current = cycle.get(i);
                    E next = cycle.get((i + 1) % cycle.size());
                    permutationMap.put(current, next);
                }
            }
        }

        // make sure all elements map to themselves
        for (E element : domain) {
            permutationMap.putIfAbsent(element, element);
        }
    }
	
	
    // second constructor including cycles of size 1
    public PermutationImpl_Westby(Set<List<E>> cycles) {
        this.domain = new HashSet<>();
        this.permutationMap = new HashMap<>();

        Set<E> allElements = new HashSet<>();
        for (List<E> cycle : cycles) {
            if (!cycle.isEmpty()) {
                for (int i = 0; i < cycle.size(); i++) {
                    E current = cycle.get(i);
                    E next = cycle.get((i + 1) % cycle.size());
                    permutationMap.put(current, next);
                    allElements.add(current);
                    allElements.add(next);
                }
            }
        }

        // check for overlapping cycles (test)
        for (List<E> cycle : cycles) {
            for (E element : cycle) {
                if (allElements.contains(element)) {
                    throw new AssertionError("Cycles overlap.");
                }
            }
        }

        // set domain based on all elements from cycles
        this.domain.addAll(allElements);

        // map elements to themselves
        for (E element : domain) {
            permutationMap.putIfAbsent(element, element);
        }
    }
	
	@Override
	public E getImage(E e) // permutation
	{
		return permutationMap.get(e);
	}

	@Override
	// iterate through each key checking their value pair
	public E getPreImage(E e) // what maps to an element
	{
		for (E key : permutationMap.keySet()) {
			if (permutationMap.get(key).equals(e)) {
				return key;
			}
		}
		
		return null;
	}

	@Override
	public Set<E> getDomain()
	{
		return this.domain;
	}
	
    @Override
    // stringbuilder to append keys and values
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (Map.Entry<E, E> entry : permutationMap.entrySet()) {
        	// key --> value
            sb.append(entry.getKey()).append(" -> ").append(entry.getValue()).append("\n");
        }
        return sb.toString();
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        PermutationImpl_Westby<?> that = (PermutationImpl_Westby<?>) obj;
        return permutationMap.equals(that.permutationMap) && domain.equals(that.domain);
    }

    @Override
    public int hashCode() {
        return permutationMap.hashCode() * 31 + domain.hashCode();
    }
}