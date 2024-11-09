package wit;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

@WebServlet("/loginserv")
public class loginserv extends HttpServlet {
    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String user = req.getParameter("submitusername");
        String pswd = req.getParameter("submitpassword");
        String formtype = req.getParameter("formtype");
        req.setCharacterEncoding("GBK");
        String url="jdbc:mysql://localhost:3306/librarymis";
        resp.setContentType("text/html;charset=GBK");
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection conn=null;
            PreparedStatement stmt=null;
            ResultSet rs=null;
            conn= DriverManager.getConnection(url,"root","123456");
            if(formtype.equals("userlogin")){
                stmt=conn.prepareStatement("select * from users where username=? and password=?");
            }
            else if(formtype.equals("adminlogin")){
                stmt=conn.prepareStatement("select * from admins where username=? and password=?");
            }
            stmt.setString(1,user);
            stmt.setString(2,pswd);
            rs=stmt.executeQuery();
            while(rs.next()){
                if(rs.getString("password").equals(pswd)&&rs.getString("username").equals(user)){
                    if(formtype.equals("adminlogin")){
                        resp.sendRedirect("admin-usermanagement.html");
                        System.out.println(user+" login success");
                    }
                    else if(formtype.equals("userlogin")){
                        resp.sendRedirect("user-home.html");
                        System.out.println(user+" login success");
                    }
                }
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
