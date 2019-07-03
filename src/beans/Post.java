package beans;

public class Post {
	private int id = 0;
	private String name = "";

	public static final int NAME_MAX_SIZE = 50;

	public Post() {}

	public Post(String name) {
		this.name = name;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
