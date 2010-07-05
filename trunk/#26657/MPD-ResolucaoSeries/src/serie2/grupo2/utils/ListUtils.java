package serie2.grupo2.utils;

import java.util.Iterator;
import java.util.List;

public class ListUtils {
	public static <E> void removeOccurrences(List<E> elems, E item){
		for(Iterator<E> iter = elems.iterator(); iter.hasNext();){
			if(iter.next().equals(item))
				iter.remove();
		}
	}
	public static <E> void removeRepetitions(List<E> elems){
		for(int i = 0; i < elems.size(); i++){
			E e = elems.get(i);
			for(int j = i + 1 ; j < elems.size(); j++){
				if(elems.get(j).equals(e)){
					elems.remove(j);
                    //j-=1;
				}
			}
		}
	}
}
