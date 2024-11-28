package combinatorics;

import java.util.Set;

public interface Permutation<E>
{
	//part of pre: getDomain().contains(e)
	//part of post: getDomain().contains(rv)
	public E getImage(E e);

	//part of pre: getDomain().contains(e)
	//part of post: getDomain().contains(rv)
	public E getPreImage(E e);
	
	//part of post: rv != null
	//part of post: !rv.contains(null)
	public Set<E> getDomain();
	
	//STUDENT: NOTE THAT THIS METHOD IS COMMONLY *NOT* 
	//EXPLICITLY INCLUDED IN AN INTERFACE
	public String toString();

	//STUDENT: NOTE THAT THIS METHOD IS COMMONLY *NOT* 
	//EXPLICITLY INCLUDED IN AN INTERFACE
	//rv <==> (this and obj are indistinguishable
	//in the eyes of the client from the perspective
	//of the interface)
	public boolean equals(Object obj);
}