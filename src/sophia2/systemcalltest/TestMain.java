package sophia2.systemcalltest;

public class TestMain {
	public static void main(String[] args) {

		ShellControl schellControl = new ShellControl();

		String command = "ls -al";
		System.out.println("command: " + command);

		schellControl.shellCmd(command);

	}

}
