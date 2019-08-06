/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package PapagoKoreanNameRomanizer;

/**
 *
 * @author Jihye
 */
// Papago Korean Name Romanizer API 예제
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

// 네이버 한글인명-로마자변환 API 예제
public class APIExamRoman {

    public static void main(String[] args) {
        String clientId = "ofviby208g";//애플리케이션 클라이언트 아이디값";
        String clientSecret = "ai1UZeRDBXSsXfJ2aOVMuaQyCck18wZj1CwWxaKm";//애플리케이션 클라이언트 시크릿값";
        try {
            String text = URLEncoder.encode("이상욱", "UTF-8");
            String apiURL = "https://naveropenapi.apigw.ntruss.com/krdict/v1/romanization?query="+ text;
            URL url = new URL(apiURL);
            HttpURLConnection con = (HttpURLConnection)url.openConnection();
            con.setRequestMethod("GET");
            con.setRequestProperty("X-NCP-APIGW-API-KEY-ID", clientId);
            con.setRequestProperty("X-NCP-APIGW-API-KEY", clientSecret);
            int responseCode = con.getResponseCode();
            BufferedReader br;
            if(responseCode==200) { // 정상 호출
                br = new BufferedReader(new InputStreamReader(con.getInputStream()));
            } else {  // 에러 발생
                br = new BufferedReader(new InputStreamReader(con.getErrorStream()));
            }
            String inputLine;
            StringBuffer response = new StringBuffer();
            while ((inputLine = br.readLine()) != null) {
                response.append(inputLine);
            }
            br.close();
            System.out.println(response.toString());
        } catch (Exception e) {
            System.out.println(e);
        }
    }
}
