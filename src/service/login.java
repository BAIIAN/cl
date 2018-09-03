package service;
import homework.httphelp;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.http.HttpResponse;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import bean.score;
public class login extends HttpServlet {

	/**
	 * Constructor of the object.
	 */
	public login() {
		super();
	}

	/**
	 * Destruction of the servlet. <br>
	 */
	public void destroy() {
		super.destroy(); // Just puts "destroy" string in log
		// Put your code here
	}

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
			response.sendRedirect("/cl-final/login.jsp");
			httphelp.log("�ض��򵽵�½");
	}


	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String jssionid = httphelp.get();
		httphelp.log("��ǰcookie��"+jssionid);
		httphelp.jsessionid=jssionid;
		request.getSession().setAttribute("jssionid", jssionid);
		Map<String,String> params=new HashMap<String,String>();
		params.put("USERNAME", request.getParameter("userName"));
		params.put("PASSWORD", request.getParameter("userPassword"));
		HttpResponse myresponse = httphelp.doPost("http://jwcxxcx.ccsu.cn/jwxt/Logon.do?method=logon",params,jssionid);
	    //�����¼�ɹ�
		if (myresponse.getStatusLine().getStatusCode() == 200) {
				if(myresponse.getFirstHeader("Transfer-Encoding")!=null){
					httphelp.log("Login Success!");						
					request.getSession().setAttribute("isLogin", "TRUE");
					request.getSession().setAttribute("userID", request.getParameter("userName"));
					int start=Integer.parseInt((String)request.getParameter("userName").subSequence(1, 5));
					//httphelp.log(start);
					//��ȡʱ����
					Calendar c = Calendar.getInstance(); 
					int year = c.get(Calendar.YEAR); 
					int month = c.get(Calendar.MONTH);
					List<String> timeList=new LinkedList<String>();
					
					//StringBuilder timeList=new StringBuilder();
					int cha=year-start;//��ݲ�ֵ
					int xiu=0;//����ָ��
					if(month<2)//��ѧ��һ��û����
						cha-=1;
					else if(month>=3&month<7)//���ڵڶ���
						xiu=-1;
					else//�ڶ��ڿ�ѧ��δ����
						;
					System.out.println(2*cha+xiu);
					for(int i=0;i<2*cha+xiu;i++){
						String s;
						if(i%2==0){
						    s=(start+i/2)+"-"+(start+i/2+1)+"-1";
						}
						else{
							s=(start+(i-1)/2)+"-"+(start+(i-1)/2+1)+"-2";	
						}
						System.out.println("add-"+s+"----"+i);
						timeList.add(s);
					}
					request.getSession().setAttribute("timeList", timeList);
					request.getSession().setAttribute("stid",request.getParameter("userName") );
					response.sendRedirect("/cl-final/index.jsp");
				}
				
			}
		
		else
		{
			httphelp.log("Login defeated");
			return;
		}
		params.clear();
		
		
			
	}

	/**
	 * Initialization of the servlet. <br>
	 *
	 * @throws ServletException if an error occurs
	 */
	public void init() throws ServletException {
		// Put your code here
	}

}
