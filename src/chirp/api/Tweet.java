package chirp.api;

public class Tweet {
	private final int originatorId;

	private final String content;

	public Tweet(int originatorId, String content) {
		this.originatorId = originatorId;
		this.content = content;
	}

	public Tweet() {
		originatorId = 0;
		content = null;
	}

	public int getOriginatorId() {
		return originatorId;
	}

	public String getContent() {
		return content;
	}
	
	public boolean equals(Object o){
		if(o instanceof Tweet){
			Tweet ot = (Tweet) o;
			return originatorId == ot.originatorId && content.equals(ot.content);
		}
		return false;
	}
	
	public int hashCode(){
		return (int) (13*originatorId) + content.hashCode();
	}
}
