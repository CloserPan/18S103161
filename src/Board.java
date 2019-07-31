import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

public class Board {
	private int boardSize;
	private final Set<Piece> boardSet = new HashSet<>();
	public String player1;
	public String player2;

	/**
	 * 构造方法
	 * 
	 * @param Type 棋盘类型
	 */
	public Board(String Type) {
		if (Type.equals("1")) {
			boardSize = 7;
		} else {
			boardSize = 18;
		}
	}
	
	/**
	 * @return 返回boardSet方便测试
	 */
	public Set<Piece> getBoardSet() {
		return boardSet;
	}

	/**
	 * @param piece 要放置的棋子
	 * @return 成功返回true 否则 false
	 */
	public boolean putPiece(Piece piece) {
		int px = piece.getPosition().getPx();
		int py = piece.getPosition().getPy();
		if (px < 0 || px > boardSize || py < 0 || py > boardSize) {
			System.out.println("错误：位置超出范围");
			return false;
		}
		Iterator<Piece> it = boardSet.iterator();
		while (it.hasNext()) {
			Piece p = it.next();
			if (p.getPosition().getPx() == px && p.getPosition().getPy() == py) {
				System.out.println("错误：该位置已有元素");
				return false;
			}
		}
		return boardSet.add(piece);
	}

	/**
	 * 确认某位置P是否越界
	 * 
	 * @param P要确认的位置参数
	 * @return 是否有效，有效true 否则 false
	 */
	public boolean checkPosivalid(Position P) {
		if (P.getPx() < 0 || P.getPy() < 0 || P.getPx() > boardSize || P.getPy() > boardSize) {
			return false;
		} else {
			return true;
		}
	}

	/**
	 * 移除某位置元素
	 * 
	 * @param P 位置
	 * @return 成功返回true 否则 false
	 */
	public boolean removePiece(Position P) {
		if (!checkPosivalid(P)) {
			System.out.println("错误：位置超出范围");
			return false;
		}
		Iterator<Piece> it = boardSet.iterator();
		while (it.hasNext()) {
			Piece p = it.next();
			if (p.getPosition().getPx() == P.getPx() && p.getPosition().getPy() == P.getPy()) {
				it.remove();
				System.out.println("移除成功");
				return true;
			}
		}
		System.out.println("错误：该位置无元素");
		return false;
	}

	/**
	 * 返回描述某个位置的string
	 * 
	 * @param P 位置
	 * @return 描述某个位置的字符串
	 */
	public String checkPos(Position P) {
		if (!checkPosivalid(P)) {
			return "位置越界";
		}
		Iterator<Piece> it = boardSet.iterator();
		while (it.hasNext()) {
			Piece p = it.next();
			if (p.getPosition().getPx() == P.getPx() && p.getPosition().getPy() == P.getPy()) {
				if (p.getOwner() == 0) {
					return "该处已被选手" + player1 + "的棋子：" + p.getName() + "占用";
				} else {
					return "该处已被选手" + player2 + "的棋子：" + p.getName() + "占用";
				}
			}
		}
		return "空闲";
	}

	/**
	 * 统计双方含有的棋子数量
	 * 
	 * @return 记录双方棋子数目的字符串
	 */
	public String countPiece() {
		Iterator<Piece> it = boardSet.iterator();
		int count1 = 0;
		int count2 = 0;
		while (it.hasNext()) {
			Piece p = it.next();
			if (p.getOwner() == 0) {
				count1++;
			}
			if (p.getOwner() == 1) {
				count2++;
			}
		}
		return player1 + " has " + count1 + " pieces, and " + player2 + " has " + count2 + " pieces.";
	}

	/**
	 * 查看一个位置是否为空
	 * 
	 * @param P 位置
	 * @return 空返回true 非空返回false
	 */
	public boolean checkPosempty(Position P) {
		Iterator<Piece> it1 = boardSet.iterator();
		while (it1.hasNext()) {
			Piece p = it1.next();
			if (p.getPosition().getPx() == P.getPx() && p.getPosition().getPy() == P.getPy()) {
				return false;
			}
		}
		return true;
	}

	/**
	 * 移动棋子
	 * 
	 * @param player 操作人
	 * @param p1     起始点
	 * @param p2     终止点
	 * @return 成功返回true 否则 false
	 */
	public boolean move(Player player, Position p1, Position p2) {
		if (!checkPosivalid(p1) || !checkPosivalid(p2)) {
			System.out.println("错误：位置超出范围");
			return false;
		}
		if (!checkPosempty(p2)) {
			System.out.println("目标位置已占用");
			return false;
		}
		Iterator<Piece> it = boardSet.iterator();
		while (it.hasNext()) {
			Piece p = it.next();
			if (p.getPosition().getPx() == p1.getPx() && p.getPosition().getPy() == p1.getPy()) {
				if (p.getOwner() != player.getPlayerTurn()) {
					System.out.println("这不是你的棋子");
					return false;
				} else {
					p.setPosition(p2.getPx(), p2.getPy());
					System.out.println("移动成功");
					return true;
				}
			}
		}
		System.out.println("没有棋子");
		return false;
	}

	/**
	 * 吃子
	 * 
	 * @param player 操作人
	 * @param p1     起始点
	 * @param p2     终止点
	 * @return 成功返回true 否则 false
	 */
	public boolean eat(Player player, Position p1, Position p2) {
		Piece target = null;
		Piece start = null;
		if (!checkPosivalid(p1) || !checkPosivalid(p2)) {
			System.out.println("错误：位置超出范围");
			return false;
		}
		if (p1.equals(p2)) {
			System.out.println("错误：前后地址相同");
			return false;
		}
		Iterator<Piece> it1 = boardSet.iterator();
		while (it1.hasNext()) {
			Piece p = it1.next();
			if (p.getPosition().getPx() == p2.getPx() && p.getPosition().getPy() == p2.getPy()) {
				if (p.getOwner() == player.getPlayerTurn()) {
					System.out.println("这是你的棋子不能吃");
					return false;
				} else
					target = p;
			}
			if (p.getPosition().getPx() == p1.getPx() && p.getPosition().getPy() == p1.getPy()) {
				if (p.getOwner() != player.getPlayerTurn()) {
					System.out.println("这不是你的棋子不能动");
					return false;
				} else {
					start = p;
				}
			}
		}
		if (target == null) {
			System.out.println("目标地无元素");
			return false;
		}
		if (start == null) {
			System.out.println("起始地无元素");
			return false;
		}
		boardSet.remove(target);
		start.setPosition(p2.getPx(), p2.getPy());
		System.out.println("吃子成功");
		return true;
	}
}