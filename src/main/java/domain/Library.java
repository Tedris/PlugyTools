package domain;

import java.util.List;

public class Library {
	private List<Stash> stashes;
	private List<PlayerCharacter> playerCharacters;
	public List<Stash> getStashes() {
		return stashes;
	}
	public void setStashes(List<Stash> stashes) {
		this.stashes = stashes;
	}
	public List<PlayerCharacter> getPlayerCharacters() {
		return playerCharacters;
	}
	public void setPlayerCharacters(List<PlayerCharacter> playerCharacters) {
		this.playerCharacters = playerCharacters;
	}
}
