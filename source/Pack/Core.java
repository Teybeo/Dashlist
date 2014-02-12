package Pack;

public class Core {

	String test_login = "root";
	String test_pwd = "root";

	public int login(String login, String password) {

		if (login.equals(test_login) == false)
			return 1;

		if (password.equals(test_pwd) == false)
			return 2;

		return 0;
	}
}
