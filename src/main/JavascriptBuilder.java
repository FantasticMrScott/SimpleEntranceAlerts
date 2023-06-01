package main;

public class JavascriptBuilder {
	
	public final String switchOpen = "\t\tComfyJS.onChat = (user, message, flags, self, extra) => {\n"
			+ "\t\t\tswitch(user.toLowerCase()) {";

	public String oneTimeOnly;
	
	public String addToSwitch;
	
	public String howlBuilder;
	
	public String howlBuilder(String userName, String path) {
		return	"\n\t\tvar " + userName + " = new Howl({\n\t\t\t src: ['" + path + "'] \n\t\t});";
	}
	
	public String switchBuilder(String userName) {
		return  "\n\t\t\t\tcase \"" + userName + "\":"
						+ "\n\t\t\t\t\t" + userName + "Here();"
								+ "\n\t\t\t\t\tbreak;";
						
	}
	
	public String onceFunction(String userName) {
		return "\n\t\tvar " + userName + "Here = (function() {"
				+ "\n\t\t\t var executed = false;"
				+ "\n\t\t\t return function() {"
				+ "\n\t\t\t\t if(!executed) {"
				+ "\n\t\t\t\t\texecuted = true;"
				+ "\n\t\t\t\t\t" + userName + ".play();"
				+ "\n\t\t\t\t} \n\t\t\t}; \n\t\t})();";
	}
}
