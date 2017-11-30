package sophia2.liveUpdate;

public class TestMain {
	public static void main(String[] args) {

		// ShellControl schellControl = new ShellControl();

		String delimeter = ".";

		Boolean majorUpdate = false;
		String major = null;
		String minor = null;

		VersionDto versionDto = new VersionDto("9.9");
		String version = versionDto.getVersion();
		if (version == null) {
			versionDto.setVersion("1.0");
		} else {
			System.out.println(version);
			major = version.substring(0, version.indexOf(delimeter));
			System.out.println("major: " + major);
			minor = version.substring(version.indexOf(delimeter) + 1);
			System.out.println("minor: " + minor);

			Integer majorInt = Integer.parseInt(major);
			Integer minorInt = Integer.parseInt(minor);

			if (majorUpdate) {
				majorInt++;
				minorInt = 0;
			} else {
				minorInt++;
			}
			versionDto.setVersion(majorInt + "." + minorInt);
		}

		// String command = "ls -al";
		// System.out.println("command: " + command);
		//
		// schellControl.shellCmd(command);

		System.out.println("version: " + versionDto.getVersion());

	}
}
