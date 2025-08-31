public class Solution {
    public static boolean isPrime(long a){
        if(a==1 || a==0 || a==4){
            return false;
        }
        if(a==2 || a==3){
            return true;
        }
        for(long i=2;i<=(long)Math.sqrt(a);i++){
            if(a%i==0){
                return false;
            }
        }
        return true;
    }
    
    public long sumOfLargestPrimes(String s) {
        int n=s.length();
        long p1=0;
        long p2=0;
        long p3=0;
        
        for(int i=0;i<n;i++){
            for(int j=i;j<n;j++){
                String sub=s.substring(i,j+1);
                if(s.charAt(j)%2==0 && i!=j){
                    continue;
                }
                if(isPrime(Long.parseLong(sub))){
                    long l=Long.parseLong(sub);
                    if(l>p1){
                        p3=p2;
                        p2=p1;
                        p1=l;
                    }
                    else if(l>p2 && l<p1){
                        p3=p2;
                        p2=l;
                    }
                    else if(l>p3 && l<p2 && l<p1){
                        p3=l;
                    }
                }
            }
        }
        return p1+p2+p3;
    }
}