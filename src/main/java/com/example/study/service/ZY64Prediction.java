package com.example.study.service;

import org.apache.commons.lang.StringUtils;

import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.UUID;

/**
 * @Author: LongX
 * @Date: 2024/7/17 15:32
 * @Description: ZY64Prediction
 * @Version: 1.0
 **/
public class ZY64Prediction{

    public static String predict(String input) {
        try {

            Map<String,String> eightName = new HashMap<>();
            eightName.put("000","天");
            eightName.put("001","泽");
            eightName.put("010","火");
            eightName.put("011","雷");
            eightName.put("100","风");
            eightName.put("101","水");
            eightName.put("110","山");
            eightName.put("111","地");

            BufferedReader br = new BufferedReader(new FileReader("D:\\个人文件\\64卦.txt"));
            String line;
            Map<String,String> tempMap = new HashMap<>();
            while ((line = br.readLine()) != null) {
                if(StringUtils.isEmpty(line) || line.charAt(0) == '\n'){
                    continue;
                }
                char[] charArray = line.toCharArray();
                for (int i = 0; i < charArray.length; i++) {
                    if(charArray[i] != ' ') continue;
                    i++;
                    if(charArray[i+1] == '为'){
                        tempMap.put(String.valueOf(charArray[i+2])+String.valueOf(charArray[i+2]),line);
                        break;
                    }
                    tempMap.put(String.valueOf(charArray[i])+String.valueOf(charArray[i+1]),line);
                    break;
                }
            }
            br.close();

            Random random = new Random(UUID.randomUUID().toString().replaceAll("-", "").hashCode() % input.hashCode());
            String binaryString = Integer.toBinaryString(random.nextInt(64));
            while (binaryString.length()<6){
                binaryString = "0"+binaryString;
            }
            String top = binaryString.substring(0,3);
            String bottom = binaryString.substring(3);

            return String.format("问:%s\n卦象:%s\n解读:%s",input,eightName.get(top)+eightName.get(bottom),tempMap.get(eightName.get(top)+eightName.get(bottom)));

        }catch (Exception e){

        }

        return null;
    }

    public static void main(String[] args) throws IOException {
        System.out.println(predict(""));
    }


}
