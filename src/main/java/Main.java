import com.example.usercrud.app.ConsoleApp;
import com.example.usercrud.config.HibernateUtil;
import com.example.usercrud.dao.UserDao;
import com.example.usercrud.service.UserService;

void main() {
    ConsoleApp app = null;
    try {
        app = new ConsoleApp(
                new Scanner(System.in),
                new UserService(
                        new UserDao(HibernateUtil.getSessionFactory())
                )
        );
    } catch (ExceptionInInitializerError e) {
        System.out.println("Не удалось подключиться к БД: " + e.getCause());
        return;
    }

    app.run();
}