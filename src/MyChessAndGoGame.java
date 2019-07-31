import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class MyChessAndGoGame {

	public Game game;
	
	/**
	 * 游戏选择
	 */
	public void chooseGame() {
		String gameType;
		BufferedReader reader = null;
		try {
			reader = new BufferedReader(new InputStreamReader(System.in));
			while (true) {
				System.out.println("请选择游戏:");
				System.out.println("1:国际象棋   2:围棋");
				gameType = reader.readLine().trim();
				if (gameType.equals("1") || gameType.equals("2")) {
					game = new Game(gameType);
					break;
				}
			}
			System.out.println("请输入选手1的名字");
			String player1Name = reader.readLine().trim();
			System.out.println("请输入选手2的名字");
			String player2Name = reader.readLine().trim();
			game.setNames(player1Name, player2Name);
			if (gameType.equals("1")) {
				System.out.println("国际象棋游戏开始!");
				chessGame();
			} else if (gameType.equals("2")) {
				System.out.println("围棋游戏开始!");
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
		System.out.println("1.落子");
		System.out.println("2.提子");
		System.out.println("3.查询棋盘位置占用情况");
		System.out.println("4.查询玩家棋盘棋子总数");
		System.out.println("5.跳过");
		System.out.println("end.结束游戏");
	}

	/**
	 * chess
	 */
	public void chessGameMenu() {
		System.out.println("1.移动");
		System.out.println("2.吃子");
		System.out.println("3.查询棋盘位置占用情况");
		System.out.println("4.查询玩家棋盘棋子总数");
		System.out.println("5.跳过");
		System.out.println("end.结束游戏");
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
		int TURN = 0;// 出手顺序变量
		boolean exitflag = false;
		try {
			reader = new BufferedReader(new InputStreamReader(System.in));
			while (true) {
				System.out.println();
				System.out.println("当前出手:" + game.getPlayer(TURN).getPlayerName());
				goGameMenu();
				String input = reader.readLine().trim();
				switch (input) {
				case "1":// 将尚未在棋盘上的一颗棋子放在棋盘上的指定位置
					System.out.println("你是选手" + game.getPlayer(TURN).getPlayerName() + "，执白，请输入坐标x y：（以空格隔开）");
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
						System.out.println("输入参数不为2个");
					}
					break;
				case "2":// 提子（移除对手棋子）
					System.out.println("输入棋子位置坐标x，y：（以空格隔开）");
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
						System.out.println("输入参数不为2个");
					}
					break;
				case "3":// 查询占用情况
					System.out.println("输入棋子位置坐标x，y：（以空格隔开）");
					inputString = reader.readLine().trim();
					arguments = inputString.split("\\s");
					if (arguments.length == 2) {
						Position P = new Position(Integer.valueOf(arguments[0]), Integer.valueOf(arguments[1]));
						System.out.println(game.checkPos(P));
					}
					break;
				case "4":// 计算两个玩家在棋盘上的棋子总数
					System.out.println(game.countPiece());
					break;
				case "5":// 计算两个玩家在棋盘上的棋子总数
					System.out.println("玩家跳过");
					TURN = (TURN + 1) % 2;
					break;
				case "end":// 结束游戏
					System.out.println("游戏结束!!!");
					exitflag = true;
					break;
				default:
					System.out.println("输入错误");
					break;
				}
				if (exitflag)
					break;
			}
			System.out.println("是否查看操作历史？(y/n)");
			switch (reader.readLine().trim()) {
			case "y":
				game.printHistory();
				break;
			case "n":
				break;
			}
			System.out.println("退出游戏!!!");
		} finally {
			try {
				reader.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * 执行国际象棋的函数
	 * 
	 * @throws IOException
	 */
	public void chessGame() throws IOException {
		String inputString;
		BufferedReader reader = null;
		String[] arguments;
		int TURN = 0;// 出手顺序变量
		boolean exitflag = false;
		try {
			reader = new BufferedReader(new InputStreamReader(System.in));
			while (true) {
				System.out.println();
				System.out.println("当前出手:" + game.getPlayer(TURN).getPlayerName());
				chessGameMenu();
				String input = reader.readLine().trim();
				switch (input) {
				case "1": // 移动棋盘上的某个位置的棋子至新的位置
					System.out.println("你是选手" + game.getPlayer(TURN).getPlayerName() + "，请输入坐标x1 y1 和坐标x2 y2：（以空格隔开）");
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
						System.out.println("输入参数不为4个");
					}
					break;
				case "2":// 吃子（使用己方棋子吃掉对手棋子）
					System.out.println("你是选手" + game.getPlayer(TURN).getPlayerName() + "，请输入坐标x1 y1 和坐标x2 y2：（以空格隔开）");
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
						System.out.println("输入参数不为4个");
					}
					break;
				case "3":// 查询占用情况
					System.out.println("输入棋子位置坐标x，y：（以空格隔开）");
					inputString = reader.readLine().trim();
					arguments = inputString.split("\\s");
					if (arguments.length == 2) {
						Position P = new Position(Integer.valueOf(arguments[0]), Integer.valueOf(arguments[1]));

						System.out.println(game.checkPos(P));
					}
					break;
				case "4":// 计算两个玩家在棋盘上的棋子总数
					System.out.println(game.countPiece());
					break;
				case "5":// 计算两个玩家在棋盘上的棋子总数
					System.out.println("玩家跳过");
					TURN = (TURN + 1) % 2;
					break;
				case "end":// 结束游戏
					System.out.println("游戏结束!!!");
					exitflag = true;
					break;
				default:
					System.out.println("输入错误");
					break;
				}
				if (exitflag)
					break;
			}
			System.out.println("是否查看操作历史？(y/n)");
			switch (reader.readLine().trim()) {
			case "y":
				game.printHistory();
				break;
			case "n":
				break;
			}
			System.out.println("退出游戏!!!");
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