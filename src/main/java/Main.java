import app.ConsoleApp;
import config.HibernateUtil;
import dao.UserDao;

void main() {
    ConsoleApp app = new ConsoleApp(
            new Scanner(System.in),
            new UserDao(HibernateUtil.getSessionFactory())
    );

    app.run();
}