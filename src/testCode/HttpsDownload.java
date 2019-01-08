/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package testCode;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

/**
 *
 * @author kyokuto
 */
public class HttpsDownload {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws MalformedURLException, IOException {
        /*
        URL url = new URL("https://bbs.naccscenter.com/naccs/dfw/web/data/code/hozei/hozei.csv");
URLConnection connection = url.openConnection();
InputStream is = connection.getInputStream();
       byte buf[] = new byte[256];
             int n;
    
            System.out.println("Content-length: " + connection.getContentLength()); 
            System.out.println("Content-type: " + connection.getContentType());     
     
            while((n = is.read(buf, 0, buf.length)) != -1){
               System.out.write(buf,0,n);
           }
            
         */
        URL url = null;

        try {
            //	URL作成
            url = new URL("https://bbs.naccscenter.com/naccs/dfw/web/data/code/hozei/hozei.csv");

            //	URLからコネクションを取得して確立
            HttpURLConnection urc
                    = (HttpURLConnection) url.openConnection();
            urc.connect();

            //	接続語、コンテンツタイプを取得する
            System.out.println("Content-Type -> "
                    + urc.getContentType() + "\n");

            //	文字コードを指定した読み込み
            InputStreamReader isr
                    = new InputStreamReader(urc.getInputStream(), "Shift-JIS"); 
            BufferedReader br = new BufferedReader(isr);
            String line;
            while ((line = br.readLine()) != null) {
                System.out.println(line);
            }

            //	終了処理
            isr.close();
            br.close();
            urc.disconnect();

        } catch (MalformedURLException e) {
            System.err.println("MalformedURLException");
            e.printStackTrace();
        } catch (IOException e) {
            System.err.println("IOExeption");
            e.printStackTrace();
        }

    }

}
