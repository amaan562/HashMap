public class HashMap<T,U>{
	
	private int max_size;
	private int size;
	private int prime;
	private T [] key;
	private U [] value;
	private boolean [] primeArr;
	
	public HashMap(){
		this(13);
	}
	public HashMap(int max_size){
		this.max_size = max_size;
		key = (T[])new Object[max_size];
		value = (U[])new Object[max_size];
		sieve();
		prime = getPrime();
	}
	
	private void sieve(){
		primeArr = new boolean[max_size+1];
		for(int i=0;i<=max_size;i++)	primeArr[i]=true;
		
		for (int p = 2; p * p <= max_size; p++)
			if (primeArr[p] == true)
				for (int i = p * p; i <= max_size; i += p)
					primeArr[i] = false;
	}
	private int getPrime(){
		int idx = max_size-1;
		while(primeArr[idx]==false)	idx--;
		return idx;
	}
	private int pow(int a, int b){
		int ans = 1;
		while(b!=0){
			if((b&1)!=0)	ans=(ans*a)%1000000007;
			b>>=1;
			a=(a*a)%1000000007;
		}
		return ans;
	}
	private int hashCode(T obj){
		String s = String.valueOf(obj);
		int code = 0;
		for(int i=0;i<s.length();i++)
			code = (code+(pow(prime,i+1)*(int)s.charAt(i))%1000000007)%1000000007;
		return code;
	}
	private int hash1(T x){
		int hashCode = hashCode(x);
		return hashCode % max_size;
	}
	private int hash2(T x){
		int hashCode = hashCode(x) ; 
		return prime - (hashCode % prime) ;
	}
	private int hash(T x){
		int idx = hash1(x);
		int h1 = hash1(x),h2 = hash2(x);
		int i=1;
		while(key[idx]!=null){
			if(key[idx].equals(x))	return idx;
			idx = (h1 + i*h2)%max_size;
			i++;
		} 
		return idx;
	}
	private void rehash(){
		T keyTemp [] = (T[]) new Object[max_size];
		U valTemp [] = (U[]) new Object[max_size];
		for(int i=0;i<max_size;i++){
			keyTemp[i] = key[i];
			valTemp[i] = value[i];
		}
		
		max_size*=2;
		sieve();
		prime = getPrime();
		
		key = (T[]) new Object[max_size];
		value = (U[]) new Object[max_size];
		
		size=0;
		for(int i=0;i<keyTemp.length;i++)	if(keyTemp[i]!=null)	put(keyTemp[i],valTemp[i]);
	}
	private int searchKey(T x){
		int idx = hash1(x);
		int i=1;
		while(key[idx]!=null){
			if(key[idx].equals(x))	return idx;
			idx = (hash1(x) + i*hash2(x))%max_size;
			i++;
		}
		return -1;
	}
	
	public boolean containsKey(T x){
		int idx = searchKey(x);
		if(idx==-1)	return false;
		return true;
	}
	public boolean containsValue(U v){
		for(int i=0;i<max_size;i++)	if(value[i].equals(v))	return true;
		return false;
	}
	public void put(T key, U value){
		int idx = hash(key);
		if(this.key[idx]==null){
			size++;
			double lf = getLoadFactor();
			if(lf>0.75)	rehash();
		}
		idx=hash(key);
		this.key[idx] = key;
		this.value[idx] = value;
		
	}
	public U get(T key){
		int idx = searchKey(key);
		if(idx==-1)	return null;
		return value[idx];
	}
	public double getLoadFactor(){
		return (double)size/max_size;
	}
	public void print(){
		for(int i=0;i<max_size;i++)	System.out.println(key[i]+" : "+value[i]);
		System.out.println();
	}
}
