package Util;

import java.util.ArrayList;
import java.util.Random;

public  class CodeUtil {
    public static String getCode() {
        //创建一个集合添加所有大写和小写字母
        ArrayList<Character> list = new ArrayList<>();
        for (int i = 0; i < 26; i++) {
            list.add((char) ('A' + i));
            list.add((char) ('a' + i));
        }
        // 随机取出4个字符
        StringBuilder sb=new StringBuilder( );
        Random random = new Random();
        for (int i = 0; i < 4; i++) {
            int index=random.nextInt(list.size());
            //利用索引获取随机字符
            char c=list.get(index);
//            把随机字符添加到sb中
            sb.append(c);
        }
        //讲一个随机数字添加到末尾
        int number=random.nextInt(10);
        sb.append(number);
        //将数字随机到字符串任意位置
        //现将字符串变成字符数组，在数组中修改之后再创建一个新的字符串
        char []arr=sb.toString().toCharArray();
        //拿最后一个索引和随机索引进行交换
       int  randomindex= random.nextInt(arr.length);
        char temp=arr[arr.length-1];
        arr[arr.length-1]=arr[randomindex];
        arr[randomindex]=temp;
        return new String(arr);

    }
}
