package servlet;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.thegreshams.firebase4j.error.FirebaseException;
import net.thegreshams.firebase4j.error.JacksonUtilityException;
import net.thegreshams.firebase4j.model.FirebaseResponse;
import net.thegreshams.firebase4j.service.Firebase;
import net.thegreshams.firebase4j.util.JacksonUtility;


public class Firebase01 extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private Firebase firebase;
	private FirebaseResponse firebaseRes;
	private String contextPathTeam01;
	private String reqURI;
	private String firebase_baseUrl;
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		setting(request);
		
		try {
			firebaseRes = firebase.get(reqURI);
		} catch (FirebaseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		response.setContentType("application/json");
		response.setCharacterEncoding("utf-8");
		response.getWriter().write(firebaseRes.getRawBody());
	}

	private void setting(HttpServletRequest request) throws UnsupportedEncodingException {
		contextPathTeam01 = request.getContextPath()+"/FirebaseTeam01";
		reqURI = request.getRequestURI().substring(contextPathTeam01.length()+1);
		System.out.println(reqURI);
		
		firebase_baseUrl = "https://first-test-1214.firebaseio.com";
		try {
			firebase = new Firebase( firebase_baseUrl );
		} catch (FirebaseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	protected void doPut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		setting(request);
		
		BufferedReader reader = request.getReader();
		StringBuffer buffer = new StringBuffer();
		String s;
		while((s=reader.readLine()) != null) {
			buffer.append(s);
		}
		
		Map<String, Object> jsonMap = null;
		try {
			jsonMap = JacksonUtility.GET_JSON_STRING_AS_MAP(buffer.toString());
		} catch (JacksonUtilityException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		try {
			FirebaseResponse res = firebase.put(reqURI , jsonMap );
			System.out.println( "\n\nResult of PUT (for the test-PUT):\n" + res );
			System.out.println("\n");
		} catch (FirebaseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		catch (JacksonUtilityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
