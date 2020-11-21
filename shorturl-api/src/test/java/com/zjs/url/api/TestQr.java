package com.zjs.url.api;


import cn.hutool.core.util.ReUtil;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.codec.binary.Base64;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;

import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.MessageFormat;
import java.util.Stack;

/** @author ZJS @Date: 2020/10/27 11:36 */
public class TestQr {
  private static String uri =
      "http://v6navtest.fetiononline.com/public-group/global/sip:125200019931551864@bfas1axm.gc.rcs2.chinamobile.com/index.xml/~~/public-group/list/entry[@uri=\"tel:+8613802885015\"]/qr-code";

  public static void main(String[] args) throws IOException {
    System.out.println("64进制：" + encode(10000));
    System.out.println("10进制：" + decode(encode(10000)));
  }


  /**
   * 打乱改字符数组的组合顺序，可以得到不同的转换结果
   */
  private static final char[] array = { 'q', 'w', 'e', 'r', 't', 'y', 'u', 'i', 'o', 'p', 'a', 's', 'd', 'f', 'g','h', 'j', 'k', 'l', 'z', 'x', 'c', 'v', 'b', 'n', 'm', '8', '5', '2', '7', '3', '6', '4', '0', '9', '1', 'Q', 'W', 'E', 'R', 'T', 'Y', 'U', 'I', 'O', 'P', 'A', 'S', 'D', 'F', 'G', 'H', 'J', 'K', 'L', 'Z', 'X', 'C', 'V', 'B', 'N', 'M', '+', '-' };

  /**
   * @param number
   *            double类型的10进制数,该数必须大于0
   * @return string类型的64进制数
   */
  public static String encode(int number) {
    int rest = number;
    // 创建栈
    Stack<Character> stack = new Stack<Character>();
    StringBuilder result = new StringBuilder(0);
    while (rest >= 1) {
      // 进栈,
      // 也可以使用(rest - (rest / 64) * 64)作为求余算法
      stack.add(array[new Double(rest % 64).intValue()]);
      rest = rest / 64;
    }
    for (; !stack.isEmpty();) {
      // 出栈
      result.append(stack.pop());
    }
    return result.toString();

  }

  /**
   * 支持范围是A-Z,a-z,0-9,+,-
   *
   * @param str 64进制的数字
   * @return 10进制的数字
   */
  public static int decode(String str) {
    // 倍数
    int multiple = 1;
    int result = 0;
    char c;
    for (int i = 0; i < str.length(); i++) {
      c = str.charAt(str.length() - i - 1);
      result += decodeChar(c) * multiple;
      multiple = multiple * 64;
    }
    return result;
  }

  private static int decodeChar(Character c) {
    for (int i = 0; i < array.length; i++) {
      if (c == array[i]) {
        return i;
      }
    }
    return -1;
  }


  public static  void testBase64(Integer p){
    byte[] result1= Base64.encodeBase64("10000".getBytes());
    System.out.println(new String(result1));
  }

  public static void testString(){
    System.out.println(MessageFormat.format("http://v6navtest.fetiononline.com/public-group/global/{0}/index.xml/~~/public-group/list/entry[@uri=\"{1}\"]/qr-code","大大","莫文蔚无无无"));
  }

  public static boolean testUrl(String url){
    return ReUtil.contains("[a-zA-z]+://[^\\s]*", url);
  }

  public static String getEPDetailById() {
    TestRestTemplate testRestTemplate = new TestRestTemplate();
    try {
      HttpHeaders headers = new HttpHeaders();
      headers.set(
          "Authorization",
          "SHA1 token=\"1a7d148e48ad87192146593125412fb4f552a4be\",nonce=\"123456789013\",appId=\"99537264\",timestamp=\"1603845828761\"");
      headers.set("x-3gpp-intended-identity", "tel:+8613802885015");
      headers.set("clienttype", "APP");
      headers.set("sourcedata", "qixin");
      headers.set("content-type", "application/xml; charset=UTF-8");
      headers.set("Accept","*/*");
      HttpEntity<String> request = new HttpEntity<>("<entry></entry>", headers);
      System.out.println(request.getBody());
      ResponseEntity<String> response =
          testRestTemplate.exchange(uri, HttpMethod.PUT, request, String.class);
      System.out.println("结果-" + response);
      System.out.println("结果body-" + response.getBody());
      JSONObject jsonObject = XmlJsonTool.documentToJsonObject(response.getBody());
      System.out.println("json-" + jsonObject.toJSONString());
      System.out.println("url-check:" + testUrl(jsonObject.getString("qr_code_url")));
    } catch (Exception e) {
      System.out.println("请求异常" + e.toString());
    }
    return "over";
  }

  public static String SHA1(String decript) {
    try {
      MessageDigest digest = MessageDigest.getInstance("SHA-1");
      digest.update(decript.getBytes());
      byte messageDigest[] = digest.digest();
      // Create Hex String
      StringBuffer hexString = new StringBuffer();
      // 字节数组转换为 十六进制 数
      for (int i = 0; i < messageDigest.length; i++) {
        String shaHex = Integer.toHexString(messageDigest[i] & 0xFF);
        if (shaHex.length() < 2) {
          hexString.append(0);
        }
        hexString.append(shaHex);
      }
      return hexString.toString();

    } catch (NoSuchAlgorithmException e) {
      e.printStackTrace();
    }
    return "";
  }
}
