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

@WebServlet("/userserv")
public class userserv extends HttpServlet {
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
        if(formtype.equals("usersearchbook")){
            ArrayList<Book> booklist = new ArrayList<>();
            try {
                String submitinfo=req.getParameter("submitinfo");
                System.out.println(submitinfo);
                if(!submitinfo.equals("all")){
                    st=cn.prepareStatement("select * from books where name=? or isbn=?");
                    st.setString(1,submitinfo);
                    st.setString(2,submitinfo);
                }
                else{
                    st=cn.prepareStatement("select * from books");
                }

                ResultSet rs=st.executeQuery();
                while(rs.next())
                {
                    Book book=new Book();
                    book.setIsbn(rs.getString("isbn"));
                    book.setName(rs.getString("name"));
                    book.setQuantity(rs.getInt("quantity"));
                    book.setPubish(rs.getString("publish"));
                    book.setPrice(rs.getInt("price"));
                    booklist.add(book);
                }
                PrintWriter out=resp.getWriter();
                out.println("<!DOCTYPE html>\n" +
                        "<html lang=\"en\">\n" +
                        "<head>\n" +
                        "    <meta charset=\"utf-8\">\n" +
                        "    <title>Title</title>\n" +
                        "</head>\n" +
                        "<link rel=\"stylesheet\" href=\"style.css\">\n" +
                        "<nav class=\"navbar\">\n" +
                        "    <ul>\n" +
                        "        <li class=\"nav-item\" ><button onclick=\"UserBorrowbookDialog.show()\"> 借书</button></li>\n" +
                        "        <li class=\"nav-item\" ><button onclick=\"UserReturnbookDialog.show()\"> 还书</button></li>\n" +
                        "    </ul>\n" +
                        "\n" +
                        "    <dialog id=\"UserBorrowbookDialog\">\n" +
                        "        <div class=\"form-container\">\n" +
                        "            <h2>借书</h2>\n" +
                        "            <form action=\"userserv\" method=\"post\">\n" +
                        "                <div class=\"form-group\">\n" +
                        "                    <label for=\"booknameorisbn\">书名或ISBN</label>\n" +
                        "                    <input type=\"text\" id=\"booknameorisbn\" name=\"submitbooknameorisbn\" required>\n" +
                        "                </div>\n" +
                        "                <input type=\"hidden\" name=\"formtype\" value=\"userborrowbook\">\n" +
                        "                <div class=\"form-group\">\n" +
                        "                    <input type=\"submit\" value=\"借书\">\n" +
                        "                    <button onclick=\"UserBorrowbookDialog.close()\">取消</button>\n" +
                        "                </div>\n" +
                        "            </form>\n" +
                        "        </div>\n" +
                        "    </dialog>\n" +
                        "    <dialog id=\"UserReturnbookDialog\">\n" +
                        "        <div class=\"form-container\">\n" +
                        "            <h2>还书</h2>\n" +
                        "            <form action=\"userserv\" method=\"post\">\n" +
                        "                <div class=\"form-group\">\n" +
                        "                    <label for=\"returnbook\">书名或ISBN</label>\n" +
                        "                    <input type=\"text\" id=\"returnbook\" name=\"submitbookname\"  required>\n" +
                        "                </div>\n" +
                        "                <input type=\"hidden\" name=\"formtype\" value=\"userreturnbook\">\n" +
                        "                <div class=\"form-group\">\n" +
                        "                    <input type=\"submit\" value=\"还书\">\n" +
                        "                    <button onclick=\"UserReturnbookDialog.close()\">取消</button>\n" +
                        "                </div>\n" +
                        "            </form>\n" +
                        "        </div>\n" +
                        "    </dialog>\n" +
                        "</nav>\n" +
                        "\n" +
                        "<body>\n" +
                        "<div >\n" +
                        "    <div>\n" +
                        "        <form action=\"userserv\" class=\"form-group-search\" method=\"post\">\n" +
                        "            <input type=\"text\" name=\"submitinfo\"  placeholder=\"ISBN,书名或all\">\n" +
                        "            <input type=\"hidden\" name=\"formtype\" value=\"usersearchbook\">\n" +
                        "            <input type=\"submit\" value=\"查询\">\n" +
                        "        </form>\n" +
                        "    </div>\n" +
                        "    <div class=\"container\">\n" +
                        "        <table>\n" +
                        "            <thead>\n" +
                        "            <tr>\n" +
                        "                <th>ISBN</th>\n" +
                        "                <th>书名</th>\n" +
                        "                <th>剩余数量</th>\n" +
                        "                <th>出版社</th>\n" +
                        "                <th>价格</th>\n" +
                        "            </tr>\n" +
                        "            </thead>\n" +
                        "            <tbody>\n" );
                for(Book b:booklist)
                {
                    out.println("            <tr>");
                    out.println("                <td>"+b.getIsbn()+"</td>");
                    out.println("                <td>"+b.getName()+"</td>");
                    out.println("                <td>"+b.getQuantity()+"</td>");
                    out.println("                <td>"+b.getPubish()+"</td>");
                    out.println("                <td>"+b.getPrice()+"</td>");
                    out.println("            </tr>");
                }
                        out.println("        </table></div>\n" +
                        "</div>\n" +
                        "\n" +
                        "\n" +
                        "</body>\n" +
                        "</html>");
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
        else if(formtype.equals("userborrowbook")){
            String submitinfo=req.getParameter("submitinfo");
            try{
                //update student set stuid=3307196846 where id=2;
                st=cn.prepareStatement("update books set quantity=quantity-1 where isbn=? or name=?");
                st.setString(1,submitinfo);
                st.setString(2,submitinfo);
                int out=st.executeUpdate();
                if(out!=0)
                {
                    System.out.println("book update done");
                }
                resp.sendRedirect("user-home.html");
            }
            catch(Exception e){
                System.out.println(e.getMessage());
            }
        }
        else if(formtype.equals("userreturnbook")){
            String submitinfo=req.getParameter("submitinfo");
            try{
                //update student set stuid=3307196846 where id=2;
                st=cn.prepareStatement("update books set quantity=quantity+1 where isbn=? or name=?");
                st.setString(1,submitinfo);
                st.setString(2,submitinfo);
                int out=st.executeUpdate();
                if(out!=0)
                {
                    System.out.println("book update done");
                }
                resp.sendRedirect("user-home.html");
            }
            catch(Exception e){
                System.out.println(e.getMessage());
            }
        }
        }
}
