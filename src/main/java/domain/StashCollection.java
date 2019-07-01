package domain;

import java.util.List;

public class StashCollection {
	private List<Stash> stashes;
	private String fileName;
	public List<Stash> getStashes() {
		return stashes;
	}
	public void setStashes(List<Stash> stashes) {
		this.stashes = stashes;
	}
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
}
