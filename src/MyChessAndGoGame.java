import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class MyChessAndGoGame {

	public Game game;
	
	/**
	 * ��Ϸѡ��
	 */
	public void chooseGame() {
		String gameType;
		BufferedReader reader = null;
		try {
			reader = new BufferedReader(new InputStreamReader(System.in));
			while (true) {
				System.out.println("��ѡ����Ϸ:");
				System.out.println("1:��������   2:Χ��");
				gameType = reader.readLine().trim();
				if (gameType.equals("1") || gameType.equals("2")) {
					game = new Game(gameType);
					break;
				}
			}
			System.out.println("������ѡ��1������");
			String player1Name = reader.readLine().trim();
			System.out.println("������ѡ��2������");
			String player2Name = reader.readLine().trim();
			game.setNames(player1Name, player2Name);
			if (gameType.equals("1")) {
				System.out.println("����������Ϸ��ʼ!");
				chessGame();
			} else if (gameType.equals("2")) {
				System.out.println("Χ����Ϸ��ʼ!");
				goGame();
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				reader.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * go
	 */
	public void goGameMenu() {
		System.out.println("1.����");
		System.out.println("2.����");
		System.out.println("3.��ѯ����λ��ռ�����");
		System.out.println("4.��ѯ���������������");
		System.out.println("5.����");
		System.out.println("end.������Ϸ");
	}

	/**
	 * chess
	 */
	public void chessGameMenu() {
		System.out.println("1.�ƶ�");
		System.out.println("2.����");
		System.out.println("3.��ѯ����λ��ռ�����");
		System.out.println("4.��ѯ���������������");
		System.out.println("5.����");
		System.out.println("end.������Ϸ");
	}

	/**
	 * go
	 * 
	 * @throws IOException
	 */
	public void goGame() throws IOException {
		String inputString;
		String[] colorStrings = new String[2];
		colorStrings[0] = "white";
		colorStrings[1] = "black";
		BufferedReader reader = null;
		String[] arguments;
		int TURN = 0;// ����˳�����
		boolean exitflag = false;
		try {
			reader = new BufferedReader(new InputStreamReader(System.in));
			while (true) {
				System.out.println();
				System.out.println("��ǰ����:" + game.getPlayer(TURN).getPlayerName());
				goGameMenu();
				String input = reader.readLine().trim();
				switch (input) {
				case "1":// ����δ�������ϵ�һ�����ӷ��������ϵ�ָ��λ��
					System.out.println("����ѡ��" + game.getPlayer(TURN).getPlayerName() + "��ִ�ף�����������x y�����Կո������");
					inputString = reader.readLine().trim();
					arguments = inputString.split("\\s");
					if (arguments.length == 2) {
						Piece newPiece = new Piece(colorStrings[TURN], TURN);
						Position P = new Position(Integer.valueOf(arguments[0]), Integer.valueOf(arguments[1]));
						if (game.addnewPiece(game.getPlayer(TURN), newPiece, P)) {
							TURN = (TURN + 1) % 2;
							System.out.println("Done");
						} else {
							continue;
						}
					} else {
						System.out.println("���������Ϊ2��");
					}
					break;
				case "2":// ���ӣ��Ƴ��������ӣ�
					System.out.println("��������λ������x��y�����Կո������");
					inputString = reader.readLine().trim();
					arguments = inputString.split("\\s");
					if (arguments.length == 2) {
						Position P = new Position(Integer.valueOf(arguments[0]), Integer.valueOf(arguments[1]));
						if (game.removePiece(game.getPlayer(TURN), P)) {
							TURN = (TURN + 1) % 2;
							System.out.println("Done");
						} else {
							continue;
						}
					} else {
						System.out.println("���������Ϊ2��");
					}
					break;
				case "3":// ��ѯռ�����
					System.out.println("��������λ������x��y�����Կո������");
					inputString = reader.readLine().trim();
					arguments = inputString.split("\\s");
					if (arguments.length == 2) {
						Position P = new Position(Integer.valueOf(arguments[0]), Integer.valueOf(arguments[1]));
						System.out.println(game.checkPos(P));
					}
					break;
				case "4":// ������������������ϵ���������
					System.out.println(game.countPiece());
					break;
				case "5":// ������������������ϵ���������
					System.out.println("�������");
					TURN = (TURN + 1) % 2;
					break;
				case "end":// ������Ϸ
					System.out.println("��Ϸ����!!!");
					exitflag = true;
					break;
				default:
					System.out.println("�������");
					break;
				}
				if (exitflag)
					break;
			}
			System.out.println("�Ƿ�鿴������ʷ��(y/n)");
			switch (reader.readLine().trim()) {
			case "y":
				game.printHistory();
				break;
			case "n":
				break;
			}
			System.out.println("�˳���Ϸ!!!");
		} finally {
			try {
				reader.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * ִ�й�������ĺ���
	 * 
	 * @throws IOException
	 */
	public void chessGame() throws IOException {
		String inputString;
		BufferedReader reader = null;
		String[] arguments;
		int TURN = 0;// ����˳�����
		boolean exitflag = false;
		try {
			reader = new BufferedReader(new InputStreamReader(System.in));
			while (true) {
				System.out.println();
				System.out.println("��ǰ����:" + game.getPlayer(TURN).getPlayerName());
				chessGameMenu();
				String input = reader.readLine().trim();
				switch (input) {
				case "1": // �ƶ������ϵ�ĳ��λ�õ��������µ�λ��
					System.out.println("����ѡ��" + game.getPlayer(TURN).getPlayerName() + "������������x1 y1 ������x2 y2�����Կո������");
					inputString = reader.readLine().trim();
					arguments = inputString.split("\\s");
					if (arguments.length == 4) {
						Position P1 = new Position(Integer.valueOf(arguments[0]), Integer.valueOf(arguments[1]));
						Position P2 = new Position(Integer.valueOf(arguments[2]), Integer.valueOf(arguments[3]));
						if (game.move(game.getPlayer(TURN), P1, P2)) {
							TURN = (TURN + 1) % 2;
							System.out.println("Done");
						}
					} else {
						System.out.println("���������Ϊ4��");
					}
					break;
				case "2":// ���ӣ�ʹ�ü������ӳԵ��������ӣ�
					System.out.println("����ѡ��" + game.getPlayer(TURN).getPlayerName() + "������������x1 y1 ������x2 y2�����Կո������");
					inputString = reader.readLine().trim();
					arguments = inputString.split("\\s");
					if (arguments.length == 4) {
						Position P1 = new Position(Integer.valueOf(arguments[0]), Integer.valueOf(arguments[1]));
						Position P2 = new Position(Integer.valueOf(arguments[2]), Integer.valueOf(arguments[3]));
						if (game.eat(game.getPlayer(TURN), P1, P2)) {
							TURN = (TURN + 1) % 2;
							System.out.println("Done");
						}
					} else {
						System.out.println("���������Ϊ4��");
					}
					break;
				case "3":// ��ѯռ�����
					System.out.println("��������λ������x��y�����Կո������");
					inputString = reader.readLine().trim();
					arguments = inputString.split("\\s");
					if (arguments.length == 2) {
						Position P = new Position(Integer.valueOf(arguments[0]), Integer.valueOf(arguments[1]));

						System.out.println(game.checkPos(P));
					}
					break;
				case "4":// ������������������ϵ���������
					System.out.println(game.countPiece());
					break;
				case "5":// ������������������ϵ���������
					System.out.println("�������");
					TURN = (TURN + 1) % 2;
					break;
				case "end":// ������Ϸ
					System.out.println("��Ϸ����!!!");
					exitflag = true;
					break;
				default:
					System.out.println("�������");
					break;
				}
				if (exitflag)
					break;
			}
			System.out.println("�Ƿ�鿴������ʷ��(y/n)");
			switch (reader.readLine().trim()) {
			case "y":
				game.printHistory();
				break;
			case "n":
				break;
			}
			System.out.println("�˳���Ϸ!!!");
		} finally {
			try {
				reader.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	public static void main(String[] args) {
		new MyChessAndGoGame().chooseGame();
	}
	
}