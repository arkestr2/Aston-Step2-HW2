import com.example.usercrud.app.ConsoleApp;
import com.example.usercrud.config.HibernateUtil;
import com.example.usercrud.dao.UserDao;

void main() {
    ConsoleApp app = new ConsoleApp(
            new Scanner(System.in),
            new UserDao(HibernateUtil.getSessionFactory())
    );

    app.run();
}