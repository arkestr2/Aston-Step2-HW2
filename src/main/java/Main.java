import com.example.usercrud.app.ConsoleApp;
import com.example.usercrud.config.HibernateUtil;
import com.example.usercrud.dao.UserDao;

void main() {
    ConsoleApp app = null;
    try {
        app = new ConsoleApp(
                new Scanner(System.in),
                new UserDao(HibernateUtil.getSessionFactory())
        );
    } catch (ExceptionInInitializerError e) {
        System.out.println("Не удалось подключиться к БД: " + e.getCause());
        return;
    }

    app.run();
}