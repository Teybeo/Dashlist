package Core;

public class CurrentUser {

	private static User current = null;

	public static User getInstance() {

		if (current == null)
			new CurrentUser();

		return current;
	}

	private CurrentUser() {

	}

    public static void setCurrentUser(User user) {
        current = user;
    }

}
