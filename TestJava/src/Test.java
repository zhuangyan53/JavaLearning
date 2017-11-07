import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by ecfgikd on 2017/6/28.
 */
public class Test {
    public int solution(int[] A) {
        // write your code in Java SE 8

        if(A==null ||A.length==0){
            return 0;
        }
        int index =0, size=0, tempSize=1;
        for (int i=1; i< A.length; i++){
            if(A[i]- A[i-1]>=1){
                tempSize++;
            }else{
                if(tempSize> size){
                    size = tempSize;
                    index = i-tempSize;
                    tempSize=1;
                }
            }

        }
        return index;
    }

    public int solution1(int[] A) {
      Map<Integer,Integer> map = new HashMap<>();
      for(int i:A) map.compute(i,(k,v) -> v == null? 1: v+1);
      int max =0;
      for (Map.Entry<Integer,Integer> e :map.entrySet()){
          int t = e.getValue() + map.getOrDefault(e.getKey()+1,0);
          if(t>max) max =t;

      }
        return max;
    }
    public int solution1_1(int[]A){
        if(A.length==0){
            return 0;
        }
//      List<Integer> list = Arrays.stream(A).sorted().boxed().collect(Collectors.toList());
        Arrays.sort(A);
        System.out.println(Arrays.toString(A));
        int length=1, tempLen=1, start = A[0], equalCount = 1;
        for(int i=1; i< A.length; i++){
            if(A[i] == start  ){
                tempLen++;
                equalCount++;
                if(i==A.length-1&&tempLen > length){
                    length = tempLen;
                }
            }else if(A[i] - start ==1 ){
                tempLen++;
                if(i==A.length-1&&tempLen > length){
                    length = tempLen;
                }
                equalCount = 1;
            }
            else{
                if(tempLen > length ){
                    length = tempLen;
                }
                start = A[i];
                tempLen = equalCount;
            }
        }
        return length;
    }

    public int solution3(int[] A){

            // write your code in Java SE 8
            if(A.length==0){
                return 0;
            }
            int length = 0;
            for(int i=0; i<A.length; i++){
                for(int j=i+1; j<A.length; j++){
                    if(A[i]==A[j]){
                        length++;
                    }
                }

            }
            return length;


    }
    public static void main(String args[]){
//        Test t1= new Test();
//        int[] array = {1,2,1,2,4,4,4};
//        System.out.println(t1.solution3(array));
        String crlf="\r\n";
        char[] crlfChars = crlf.toCharArray();
        for(char ch : crlfChars){
            System.out.println(Integer.toHexString((int)ch));
        }

    }
}
