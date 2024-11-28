package combinatorics;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class PermutationUtils_Westby {
    
    public static <E> List<E> getCycle(Permutation<E> permutation, E e) {
        List<E> cycle = new ArrayList<>();
        Set<E> visited = new HashSet<>();
        E current = e;
        
        while (!visited.contains(current)) {
        	visited.add(current);
        	cycle.add(current);
        	current = permutation.getImage(current);
        }
        
        if (!cycle.get(0).equals(current)) {
        	return new ArrayList<>();
        }
        
        return cycle;
    }
    
    public static <E> Permutation<E> getInverse(Permutation<E> permutation) {
        Map<E, E> inverseMap = new HashMap<>();
        Set<E> domain = permutation.getDomain();

        for (E e : domain) {
            E image = permutation.getImage(e);
            inverseMap.put(image, e);
        }

        // make cycles for inverse permutation
        Set<List<E>> inverseCycles = new HashSet<>();
        Set<E> visited = new HashSet<>();

        for (E element : domain) {
            if (!visited.contains(element)) {
                List<E> cycle = new ArrayList<>();
                E current = element;

                while (!visited.contains(current)) {
                    cycle.add(current);
                    visited.add(current);
                    current = inverseMap.get(current);
                }

                inverseCycles.add(cycle);
            }
        }

        return new PermutationImpl_Westby<>(inverseCycles, domain);
    }
    
    // part of pre: permutation2.getDomain().containsAll(permutation1.getDomain())
    // post: rv = permutation1 * permutation2, that is,
    // rv.getImage(e) = permutation1.getImage(permutation2.getImage(e)) 
    // for all e in permutation2.getDomain()
    public static <E> Permutation<E> compose(Permutation<E> permutation1, Permutation<E> permutation2) {
        Map<E, E> composedMap = new HashMap<>();
        Set<E> domain = permutation2.getDomain();

        for (E e : domain) {
            E imageUnderP2 = permutation2.getImage(e);
            E imageUnderP1 = permutation1.getImage(imageUnderP2);
            composedMap.put(e, imageUnderP1);
        }

        Set<List<E>> composedCycles = new HashSet<>();
        Set<E> visited = new HashSet<>();

        for (E element : domain) {
            if (!visited.contains(element)) {
                List<E> cycle = new ArrayList<>();
                E current = element;

                while (!visited.contains(current)) {
                    cycle.add(current);
                    visited.add(current);
                    current = composedMap.get(current);
                }

                composedCycles.add(cycle);
            }
        }

        return new PermutationImpl_Westby<>(composedCycles, domain);
    }
    
    // part of post: rv = "permutation consists of a single cycle"
    public static <E> boolean isCyclic(Permutation<E> permutation) {
        Set<E> visited = new HashSet<>();
        Set<E> domain = permutation.getDomain();
        E start = domain.iterator().next();
        E current = start;

        while (visited.size() < domain.size()) {
            if (visited.contains(current)) {
                return false; 
            }
            visited.add(current);
            current = permutation.getImage(current);
        }

        return current.equals(start); // return to start to complete cycle
    }
}
