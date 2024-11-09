package wit;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;
import java.util.ArrayList;

@WebServlet("/adminserv")
public class adminserv extends HttpServlet{
    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("表单已提交");
        String formtype = req.getParameter("formtype");
        req.setCharacterEncoding("GBK");
        try{
            System.out.println(req.getParameter("formtype"));
        }catch(Exception e){
            System.out.println(e.getMessage());
        }
        resp.setCharacterEncoding("GBK");
        String url="jdbc:mysql://localhost:3306/librarymis";
        resp.setContentType("text/html;charset=GBK");
        PreparedStatement st;
        Connection cn= null;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            cn = DriverManager.getConnection(url,"root","123456");
        } catch (ClassNotFoundException | SQLException e) {
            throw new RuntimeException(e);
        }
        if(formtype.equals("searchbook")) {

            ArrayList<Book> booklist = new ArrayList<>();
            try {
                if (req.getParameter("submitinfo").equals("all")) {
                    st = cn.prepareStatement("select * from books");
                    System.out.println(req.getParameter("submitinfo"));
                } else {
                    st = cn.prepareStatement("select * from books where isbn=? or name=? ");
                    st.setString(1, req.getParameter("submitinfo"));
                    st.setString(2, req.getParameter("submitinfo"));
                }
                ResultSet rs = st.executeQuery();
                while (rs.next()) {
                    Book book = new Book();
                    book.setIsbn(rs.getString("isbn"));
                    book.setName(rs.getString("name"));
                    book.setQuantity(rs.getInt("quantity"));
                    book.setPubish(rs.getString("publish"));
                    book.setPrice(rs.getInt("price"));
                    booklist.add(book);
                }
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
            PrintWriter out = resp.getWriter();
            out.println("<!DOCTYPE html>");
            out.println("<html lang=\"en\">");
            out.println("<head>");
            out.println("    <meta charset=\"utf-8\">");
            out.println("    <title>Title</title>");
            out.println("</head>");
            out.println("<link rel=\"stylesheet\" href=\"style.css\">");
            out.println("<nav class=\"navbar\">");
            out.println("    <ul>");
            out.println("        <li class=\"nav-item\"><a href=\"admin-usermanagement.html\">用户管理</a></li>");
            out.println("        <li class=\"nav-item\"><a href=\"admin-bookmanagement.html\">图书管理</a></li>");
            out.println("        <li class=\"nav-item\"><a href=\"admin-borrowinfo.html\">借阅记录</a></li>");
            out.println("    </ul>");
            out.println("</nav>");
            out.println("<body>");
            out.println("<div >");
            out.println("    <div>");
            out.println("        <form action=\"adminserv\" class=\"form-group-search\" method=\"post\">");
            out.println("            <input type=\"text\" name=\"submitinfo\"  placeholder=\"ISBN,书名或all\">");
            out.println("            <input type=\"hidden\" name=\"formtype\" value=\"searchbook\">");
            out.println("            <input type=\"submit\" value=\"查询\">");
            out.println("        </form>");
            out.println("    </div>");
            //
            out.println("\n" +
                    "        <button class=\"mybutton1\" onclick=\"adddialog.show()\">添加</button>\n" +
                    "        <dialog id=\"adddialog\">\n" +
                    "            <div class=\"form-container\">\n" +
                    "                <h2>修改</h2>\n" +
                    "                <form action=\"adminserv\" method=\"post\">\n" +
                    "                    <div class=\"form-group\">\n" +
                    "                        <label for=\"addbookname\">书名</label>\n" +
                    "                        <input type=\"text\" id=\"addbookname\" name=\"submitbookname\" placeholder=\"书名不可修改\" required>\n" +
                    "                    </div>\n" +
                    "                    <div class=\"form-group\">\n" +
                    "                        <label for=\"addisbn\">ISBN</label>\n" +
                    "                        <input type=\"text\" id=\"addisbn\" name=\"submitISBN\" placeholder=\"ISBN不可修改\" required>\n" +
                    "                    </div>\n" +
                    "                    <div class=\"form-group\">\n" +
                    "                        <label for=\"addquantity\">数量</label>\n" +
                    "                        <input type=\"text\" id=\"addquantity\" name=\"submitquantity\" required>\n" +
                    "                    </div>\n" +
                    "                    <div class=\"form-group\">\n" +
                    "                        <label for=\"addprice\">价格</label>\n" +
                    "                        <input type=\"text\" id=\"addprice\" name=\"submitprice\" required>\n" +
                    "                    </div>\n" +
                    "                    <input type=\"hidden\" name=\"formtype\" value=\"editbook\">\n" +
                    "                    <div class=\"form-group\">\n" +
                    "                        <input type=\"submit\" value=\"修改\">\n" +
                    "                        <button onclick=\"adddialog.close()\">取消</button>\n" +
                    "                    </div>\n" +
                    "                </form>\n" +
                    "            </div>\n" +
                    "        </dialog>\n" +
                    "        <button class=\"mybutton2\" value=\"\" onclick=\"editdialog.show()\">修改</button>\n" +
                    "        <dialog id=\"editdialog\">\n" +
                    "            <div class=\"form-container\">\n" +
                    "                <h2>修改</h2>\n" +
                    "                <form action=\"adminserv\" method=\"post\">\n" +
                    "                    <div class=\"form-group\">\n" +
                    "                        <label for=\"editname\">书名</label>\n" +
                    "                        <input type=\"text\" id=\"editname\" name=\"submitbookname\" placeholder=\"书名不可修改\" required>\n" +
                    "                    </div>\n" +
                    "                    <div class=\"form-group\">\n" +
                    "                        <label for=\"isbn\">ISBN</label>\n" +
                    "                        <input type=\"text\" id=\"editisbn\" name=\"submitISBN\" placeholder=\"ISBN不可修改\" required>\n" +
                    "                    </div>\n" +
                    "                    <div class=\"form-group\">\n" +
                    "                        <label for=\"editquantity\">数量</label>\n" +
                    "                        <input type=\"text\" id=\"editquantity\" name=\"submitquantity\" required>\n" +
                    "                    </div>\n" +
                    "                    <div class=\"form-group\">\n" +
                    "                        <label for=\"editprice\">价格</label>\n" +
                    "                        <input type=\"text\" id=\"editprice\" name=\"submitprice\" required>\n" +
                    "                    </div>\n" +
                    "                    <input type=\"hidden\" name=\"formtype\" value=\"editbook\">\n" +
                    "                    <div class=\"form-group\">\n" +
                    "                        <input type=\"submit\" value=\"修改\">\n" +
                    "                        <button onclick=\"editdialog.close()\">取消</button>\n" +
                    "                    </div>\n" +
                    "                </form>\n" +
                    "            </div>\n" +
                    "        </dialog>\n" +
                    "        <button class=\"mybutton3\" onclick=\"deldialog.show()\">删除</button>\n" +
                    "        <dialog id=\"deldialog\">\n" +
                    "            <div class=\"form-container\">\n" +
                    "                <h2>删除</h2>\n" +
                    "                <form action=\"adminserv\" method=\"post\">\n" +
                    "                    <div class=\"form-group\">\n" +
                    "                        <label for=\"isbn\">ISBN</label>\n" +
                    "                        <input type=\"text\" id=\"isbn\" name=\"submitISBN\" required>\n" +
                    "                    </div>\n" +
                    "                    <input type=\"hidden\" name=\"formtype\" value=\"deletebook\">\n" +
                    "                    <div class=\"form-group\">\n" +
                    "                        <input type=\"submit\" value=\"删除\">\n" +
                    "                        <button onclick=\"deldialog.close()\">取消</button>\n" +
                    "                    </div>\n" +
                    "                </form>\n" +
                    "            </div>\n" +
                    "        </dialog>\n");
            out.println("    <div class=\"container\">");
            out.println("        <table>");
            out.println("            <thead>");
            out.println("            <tr>");
            out.println("                <th>ISBN</th>");
            out.println("                <th>书名</th>");
            out.println("                <th>剩余数量</th>");
            out.println("                <th>出版社</th>");
            out.println("                <th>价格</th>");
            out.println("            </tr>");
            out.println("            </thead>");
            out.println("            <tbody>");
            for (Book b : booklist) {
                out.println("            <tr>");
                out.println("                <td>" + b.getIsbn() + "</td>");
                out.println("                <td>" + b.getName() + "</td>");
                out.println("                <td>" + b.getQuantity() + "</td>");
                out.println("                <td>" + b.getPubish() + "</td>");
                out.println("                <td>" + b.getPrice() + "</td>");
                out.println("            </tr>");
            }
            out.println("            </tbody>");
            out.println("        </table></div>");
            out.println("</div>");
            out.println("</body>");
            out.println("</html>");
        }
        else if(formtype.equals("searchuser"))
        {
            ArrayList<User> userlist = new ArrayList<>();
            try{
                if(req.getParameter("submitinfo").equals("all"))
                {
                    st=cn.prepareStatement("select * from users");
                }
                else{
                    st=cn.prepareStatement("select * from users where username=?");
                    st.setString(1,req.getParameter("submitinfo"));
                }
                ResultSet rs=st.executeQuery();
                while(rs.next())
                {
                    User u=new User();
                    u.setUsername(rs.getString("username"));
                    u.setName(rs.getString("name"));
                    u.setPassword(rs.getString("password"));
                    userlist.add(u);
                }
                PrintWriter out=resp.getWriter();
                out.println("<!DOCTYPE html>");
                out.println("<html lang=\"en\">");
                out.println("<head>");
                out.println("    <meta charset=\"utf-8\">");
                out.println("    <title>Title</title>");
                out.println("</head>");
                out.println("<link rel=\"stylesheet\" href=\"style.css\">");
                out.println("<nav class=\"navbar\">");
                out.println("    <ul>");
                out.println("        <li class=\"nav-item\"><a href=\"admin-usermanagement.html\">用户管理</a></li>");
                out.println("        <li class=\"nav-item\"><a href=\"admin-bookmanagement.html\">图书管理</a></li>");
                out.println("        <li class=\"nav-item\"><a href=\"admin-borrowinfo.html\">借阅记录</a></li>");
                out.println("    </ul>");
                out.println("</nav>");
                out.println("<body>");
                out.println("<div >");
                out.println("    <div>");
                out.println("        <form action=\"userserv\" class=\"form-group-search\" method=\"post\">");
                out.println("            <input type=\"text\" name=\"submitinfo\"  placeholder=\"账号,姓名或all\"> ");
                out.println("            <input type=\"hidden\" name=\"formtype\" value=\"searchuser\">");
                out.println("            <input type=\"submit\" value=\"查询\">");
                out.println("        </form>");
                out.println("    </div>");
                out.println("\n" +
                        "        <button class=\"mybutton1\" onclick=\"adddialog.show()\">添加</button>\n" +
                        "        <dialog id=\"adddialog\">\n" +
                        "            <div class=\"form-container\">\n" +
                        "                <h2>修改</h2>\n" +
                        "                <form action=\"adminserv\" method=\"post\">\n" +
                        "                    <div class=\"form-group\">\n" +
                        "                        <label for=\"addbookname\">书名</label>\n" +
                        "                        <input type=\"text\" id=\"addbookname\" name=\"submitbookname\" placeholder=\"书名不可修改\" required>\n" +
                        "                    </div>\n" +
                        "                    <div class=\"form-group\">\n" +
                        "                        <label for=\"addisbn\">ISBN</label>\n" +
                        "                        <input type=\"text\" id=\"addisbn\" name=\"submitISBN\" placeholder=\"ISBN不可修改\" required>\n" +
                        "                    </div>\n" +
                        "                    <div class=\"form-group\">\n" +
                        "                        <label for=\"addquantity\">数量</label>\n" +
                        "                        <input type=\"text\" id=\"addquantity\" name=\"submitquantity\" required>\n" +
                        "                    </div>\n" +
                        "                    <div class=\"form-group\">\n" +
                        "                        <label for=\"addprice\">价格</label>\n" +
                        "                        <input type=\"text\" id=\"addprice\" name=\"submitprice\" required>\n" +
                        "                    </div>\n" +
                        "                    <input type=\"hidden\" name=\"formtype\" value=\"editbook\">\n" +
                        "                    <div class=\"form-group\">\n" +
                        "                        <input type=\"submit\" value=\"修改\">\n" +
                        "                        <button onclick=\"adddialog.close()\">取消</button>\n" +
                        "                    </div>\n" +
                        "                </form>\n" +
                        "            </div>\n" +
                        "        </dialog>\n" +
                        "        <button class=\"mybutton2\" value=\"\" onclick=\"editdialog.show()\">修改</button>\n" +
                        "        <dialog id=\"editdialog\">\n" +
                        "            <div class=\"form-container\">\n" +
                        "                <h2>修改</h2>\n" +
                        "                <form action=\"adminserv\" method=\"post\">\n" +
                        "                    <div class=\"form-group\">\n" +
                        "                        <label for=\"editname\">书名</label>\n" +
                        "                        <input type=\"text\" id=\"editname\" name=\"submitbookname\" placeholder=\"书名不可修改\" required>\n" +
                        "                    </div>\n" +
                        "                    <div class=\"form-group\">\n" +
                        "                        <label for=\"isbn\">ISBN</label>\n" +
                        "                        <input type=\"text\" id=\"editisbn\" name=\"submitISBN\" placeholder=\"ISBN不可修改\" required>\n" +
                        "                    </div>\n" +
                        "                    <div class=\"form-group\">\n" +
                        "                        <label for=\"editquantity\">数量</label>\n" +
                        "                        <input type=\"text\" id=\"editquantity\" name=\"submitquantity\" required>\n" +
                        "                    </div>\n" +
                        "                    <div class=\"form-group\">\n" +
                        "                        <label for=\"editprice\">价格</label>\n" +
                        "                        <input type=\"text\" id=\"editprice\" name=\"submitprice\" required>\n" +
                        "                    </div>\n" +
                        "                    <input type=\"hidden\" name=\"formtype\" value=\"editbook\">\n" +
                        "                    <div class=\"form-group\">\n" +
                        "                        <input type=\"submit\" value=\"修改\">\n" +
                        "                        <button onclick=\"editdialog.close()\">取消</button>\n" +
                        "                    </div>\n" +
                        "                </form>\n" +
                        "            </div>\n" +
                        "        </dialog>\n" +
                        "        <button class=\"mybutton3\" onclick=\"deldialog.show()\">删除</button>\n" +
                        "        <dialog id=\"deldialog\">\n" +
                        "            <div class=\"form-container\">\n" +
                        "                <h2>删除</h2>\n" +
                        "                <form action=\"adminserv\" method=\"post\">\n" +
                        "                    <div class=\"form-group\">\n" +
                        "                        <label for=\"isbn\">ISBN</label>\n" +
                        "                        <input type=\"text\" id=\"isbn\" name=\"submitISBN\" required>\n" +
                        "                    </div>\n" +
                        "                    <input type=\"hidden\" name=\"formtype\" value=\"deletebook\">\n" +
                        "                    <div class=\"form-group\">\n" +
                        "                        <input type=\"submit\" value=\"删除\">\n" +
                        "                        <button onclick=\"deldialog.close()\">取消</button>\n" +
                        "                    </div>\n" +
                        "                </form>\n" +
                        "            </div>\n" +
                        "        </dialog>\n");
                out.println("    <div class=\"container\">");
                out.println("        <table>");
                out.println("            <thead>");
                out.println("            <tr>");
                out.println("                <th>账号</th>");
                out.println("                <th>姓名</th>");
                out.println("                <th>密码</th>");
                out.println("            </tr>");
                out.println("            </thead>");
                out.println("            <tbody>");
                for(User u:userlist)
                {
                    out.println("            <tr>");
                    out.println("                <td>"+u.getUsername()+"</td>");
                    out.println("                <td>"+u.getName()+"</td>");
                    out.println("                <td>"+u.getPassword()+"</td>");
                    out.println("            </tr>");
                }
                out.println("            </tbody>");
                out.println("        </table></div>");
                out.println("</div>");
                out.println("</body>");
                out.println("</html>");
            }catch(Exception e){
                System.out.println(e.getMessage());
            }

        }
        else if(formtype.equals("searchborr"))
        {
            ArrayList<borrow> borrowlist = new ArrayList<>();
            try{
                st=cn.prepareStatement("select * from borrows");
                ResultSet rs=st.executeQuery();
                while(rs.next())
                {
                    borrow bor=new borrow();
                    bor.setUsername(rs.getString("username"));
                    bor.setBookname(rs.getString("bookname"));
                    bor.setBorrowtime(rs.getString("borrowtime"));
                    bor.setBorrowtime(rs.getString("returntime"));
                    borrowlist.add(bor);
                }
            }catch(Exception e){
                System.out.println(e.getMessage());
            }
            PrintWriter out=resp.getWriter();
            out.println("<!DOCTYPE html>");
            out.println("<html lang=\"en\">");
            out.println("<head>");
            out.println("    <meta charset=\"utf-8\">");
            out.println("    <title>Title</title>");
            out.println("</head>");
            out.println("<link rel=\"stylesheet\" href=\"style.css\">");
            out.println("<nav class=\"navbar\">");
            out.println("    <ul>");
            out.println("        <li class=\"nav-item\"><a href=\"admin-usermanagement.html\">用户管理</a></li>");
            out.println("        <li class=\"nav-item\"><a href=\"admin-bookmanagement.html\">图书管理</a></li>");
            out.println("        <li class=\"nav-item\"><a href=\"admin-borrowinfo.html\">借阅记录</a></li>");
            out.println("    </ul>");
            out.println("</nav>");
            out.println("<body>");
            out.println("<div >");
            out.println("    <div>");
            out.println("        <form action=\"adminserv\" class=\"form-group-search\" method=\"post\">");
            out.println("            <input type=\"hidden\" name=\"formtype\" value=\"searchbook\">");
            out.println("            <input type=\"submit\" value=\"查询\">");
            out.println("        </form>");
            out.println("    </div>");
            out.println("    <div class=\"container\">");
            out.println("        <table>");
            out.println("            <thead>");
            out.println("            <tr>");
            out.println("                <th>借书人</th>");
            out.println("                <th>书名</th>");
            out.println("                <th>借出时间</th>");
            out.println("                <th>归还时间</th>");
            out.println("            </tr>");
            out.println("            </thead>");
            out.println("            <tbody>");
            for(borrow b:borrowlist)
            {
                out.println("            <tr>");
                out.println("                <td>"+b.getUsername()+"</td>");
                out.println("                <td>"+b.getBookname()+"</td>");
                out.println("                <td>"+b.getBorrowtime()+"</td>");
                out.println("                <td>"+b.getReturntime()+"</td>");
                out.println("            </tr>");
            }
            out.println("            </tbody>");
            out.println("        </table></div>");
            out.println("</div>");
            out.println("</body>");
            out.println("</html>");
        }
        else if(formtype.equals("adduser"))
        {
            String name=req.getParameter("submitusername");
            String password=req.getParameter("submituserpassword");
            String id=req.getParameter("submituserid");
            //insert into users (username,password,name) values ('nami','nami','娜美');
            try {
                st=cn.prepareStatement("insert into users (username,password,name) values (?,?,?);");
                st.setString(1,id);
                st.setString(2,password);
                st.setString(3,name);
                st.executeUpdate();
                System.out.println("add user success");
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
        else if(formtype.equals("edituser"))
        {
            String name=req.getParameter("submitusername");
            String password=req.getParameter("submituserpassword");
            String id=req.getParameter("submituserid");
            //insert into users (username,password,name) values ('nami','nami','娜美');
            try {
                st=cn.prepareStatement("update users set password=?,name=? where username=?");
                st.setString(1,password);
                st.setString(2,name);
                st.setString(3,id);
                st.executeUpdate();
                System.out.println("edit user success");
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }

        }
        else if(formtype.equals("deluser"))
        {
            String id=req.getParameter("submitusername");
            //insert into users (username,password,name) values ('nami','nami','娜美');
            try {
                st=cn.prepareStatement("delete from users where username=?");
                st.setString(1,id);
                st.executeUpdate();
                System.out.println("delete user success");
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
        else if(formtype.equals("addbook"))
        {
            String name=req.getParameter("submitbookname");
            String isbn=req.getParameter("submitISBN");
            String publish=req.getParameter("submitpublish");
            int quantity= Integer.parseInt(req.getParameter("submitquantity"));
            int price= Integer.parseInt(req.getParameter("submitprice"));
            //INSERT INTO books (isbn, name, quantity, publish, price) VALUES ('9780123456780', '风之影', 15, '译林出版社', 39);
            try {
                st=cn.prepareStatement("insert into books (isbn, name, quantity, publish, price) VALUES (?,?,?,?,?)");
                st.setString(1,isbn);
                st.setString(2,name);
                st.setInt(3,quantity);
                st.setString(4,publish);
                st.setInt(4,price);
                st.executeUpdate();
                System.out.println("add book success");
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
        else if(formtype.equals("editbook"))
        {
            String name=req.getParameter("submitbookname");
            String isbn=req.getParameter("submitISBN");
            String publish=req.getParameter("submitpublish");
            int quantity= Integer.parseInt(req.getParameter("submitquantity"));
            int price= Integer.parseInt(req.getParameter("submitprice"));
            //INSERT INTO books (isbn, name, quantity, publish, price) VALUES ('9780123456780', '风之影', 15, '译林出版社', 39);
            try {
                st=cn.prepareStatement("update books set quantity=?,price=?,name=?,publish=? where isbn=?");
                st.setInt(1,quantity);
                st.setInt(2,price);
                st.setString(3,name);
                st.setString(4,publish);
                st.setString(5,isbn);
                st.executeUpdate();
                System.out.println("edit book success");
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
        else if(formtype.equals("deletebook"))
        {
            String isbn=req.getParameter("submitISBN");
            try {
                st=cn.prepareStatement("delete from books where isbn=?");
                st.setString(1,isbn);
                st.executeUpdate();
                System.out.println("delete book success");
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }

        }
    }
}
