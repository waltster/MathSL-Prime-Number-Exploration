package walterpach.exploration;

import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

public class MapManager {
	private ConcurrentHashMap<Integer, Byte> primeMap;
	
	public MapManager(List<Integer> initialPrimes) {
		primeMap = new ConcurrentHashMap<Integer, Byte>();
		
		if(initialPrimes != null) {
			for(Integer x : initialPrimes) {
				primeMap.put(x, (byte)0x01);
			}
		}
	}
	
	public boolean isPrime(int x) {
		if(primeMap.get(x) == (byte)0x01) {
			return true;
		}
		
		return false;
	}
	
	public void putPrime(int x) {
		primeMap.put(x, (byte)0x01);
	}
}
