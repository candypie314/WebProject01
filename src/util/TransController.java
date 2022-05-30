package util;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.net.URLEncoder;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/trans")
public class TransController extends HttpServlet {

	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request, response);
	
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String text = request.getParameter("text");
		String lang = request.getParameter("lang");
		System.out.println("text >>> " + text);
		String from, to = "";
		if("ko".equals(lang)) {
			from = "ko";
			to = "en";
		}else {
			from = "en";
			to = "ko";
		}
		String result = trans(text, from, to);
		System.out.println("result >>>>>>> " + result);		
		response.setContentType("application/json");
		response.setCharacterEncoding("utf8");
		response.getWriter().write(result);
	}

	public String trans(String text, String from, String to) {

		String clientId = "RmYkYZyDXWOoz0MckcGa";// 애플리케이션 클라이언트 아이디값";
		String clientSecret = "FSRPr4FxOr";// 애플리케이션 클라이언트 시크릿값";
		
		String apiURL = "https://openapi.naver.com/v1/papago/n2mt";
		URL url= null;
		System.out.println(">>>>>>>>>> "+ text);
		try {
			url = new URL(apiURL);
			text = URLEncoder.encode(text, "UTF-8");
		} catch (MalformedURLException e1) {
			e1.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		String s = "source=" + from + "&target=" + to + "&text=" + text;
		System.out.println(">>>>>>>>>> "+ s);
		HttpURLConnection hConnection=null;
	
		try {
			hConnection = (HttpURLConnection) url.openConnection();
			hConnection.setRequestMethod("POST");
			hConnection.setRequestProperty("X-Naver-Client-Id", clientId);
			hConnection.setRequestProperty("X-Naver-Client-Secret", clientSecret);

			hConnection.setDoOutput(true);
			DataOutputStream d = new DataOutputStream(hConnection.getOutputStream());
			d.write(s.getBytes());
			d.flush();
			d.close();
			
			int responseCode = hConnection.getResponseCode();
			if (responseCode == HttpURLConnection.HTTP_OK) { // 정상 응답
				BufferedReader bufferedReader = 
						new BufferedReader(new InputStreamReader(hConnection.getInputStream()));
				text = bufferedReader.readLine();
			} else { // 에러 응답
				System.out.println("error");
			}
		
		} catch (ProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return text;
	}
}
