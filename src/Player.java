public class Player {
	private String playerName; // �������
	private Integer turn;// ��ҳ���˳��
	
	/**
	 * @return �����������
	 */
	public String getPlayerName() {
		return playerName;
	}

	/**
	 * @param playerName �����������
	 */
	public void setPlayerName(String playerName) {
		this.playerName = playerName;
	}
	
	/**
	 * @return ������ҳ���˳��
	 */
	public Integer getPlayerTurn() {
		return turn;
	}

	/**
	 * @param turn ������ҳ���˳��
	 */
	public void setPlayerTurn(Integer turn) {
		this.turn = turn;
	}
}
